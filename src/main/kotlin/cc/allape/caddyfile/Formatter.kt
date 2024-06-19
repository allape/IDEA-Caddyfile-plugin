package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock

const val DEFAULT_SPACE_COUNT = 4

open class CaddyfileBlock(
    node: ASTNode, wrap: Wrap?, alignment: Alignment?,
    private val spacingBuilder: SpacingBuilder,
) : AbstractBlock(node, wrap, alignment) {
    override fun buildChildren(): List<Block> {
        val blocks: MutableList<Block> = ArrayList()
        var child = myNode.firstChildNode
        while (child != null) {
            if (child.elementType !== TokenType.WHITE_SPACE) {
                val block: Block = CaddyfileBlock(
                    child, Wrap.createWrap(WrapType.NONE, false), Alignment.createAlignment(),
                    spacingBuilder
                )
                blocks.add(block)
            }
            child = child.treeNext
        }
        return blocks
    }

    private fun isParentAProperty(): Boolean {
        return myNode.treeParent?.elementType == CaddyfileTypes.PROPERTY
    }

    override fun getIndent(): Indent? {
        when (myNode.elementType) {
            CaddyfileTypes.COMMENT,
            CaddyfileTypes.PROPERTY -> {
                if (this.isParentAProperty()) {
                    return Indent.getIndent(Indent.Type.NORMAL, DEFAULT_SPACE_COUNT, false, false)
                }
            }
            CaddyfileTypes.RCB -> {
                if (this.isParentAProperty()) {
                    Indent.getIndent(Indent.Type.NONE, true, false)
                }
            }
        }
        return Indent.getIndent(Indent.Type.NONE, false, false)
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        return if (isParentAProperty()) {
            ChildAttributes(Indent.getIndent(Indent.Type.NORMAL, DEFAULT_SPACE_COUNT, false, false), null)
        } else {
            ChildAttributes(Indent.getIndent(Indent.Type.NONE, false, false), null)
        }
    }

    override fun isLeaf(): Boolean {
        return myNode.firstChildNode == null
    }
}

internal class CaddyfileFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val codeStyleSettings = formattingContext.codeStyleSettings
        return FormattingModelProvider
            .createFormattingModelForPsiFile(
                formattingContext.containingFile,
                CaddyfileBlock(
                    formattingContext.node,
                    Wrap.createWrap(WrapType.NONE, false),
                    Alignment.createAlignment(),
                    createSpaceBuilder(codeStyleSettings),
                ),
                codeStyleSettings
            )
    }
}

private fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
    return SpacingBuilder(settings, CaddyfileLanguage.INSTANCE)
        .before(CaddyfileTypes.LCB)
        .spaces(1)
        .before(CaddyfileTypes.PROPERTY)
        .none()
        .before(CaddyfileTypes.ARG)
        .spaces(1)
        .before(CaddyfileTypes.MATCHER)
        .spaces(1)
        .before(CaddyfileTypes.VARIABLE)
        .spaces(1)
}