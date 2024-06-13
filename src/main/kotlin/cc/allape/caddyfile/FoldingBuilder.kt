package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileProperty
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.FoldingGroup
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.toArray
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable


class CaddyfilePropertyVisitor(private val descriptors: MutableList<FoldingDescriptor>) : PsiElementVisitor() {
    companion object {
        private const val REGION_PREFIX = "# region"
        private const val ENDREGION_PREFIX = "# endregion"
    }

    override fun visitElement(@NotNull ele: PsiElement) {
        if (ele !is CaddyfileFile) {
            return
        }

        this.visitProperty(ele)

        val comments = PsiTreeUtil.getChildrenOfType(ele, PsiComment::class.java)
        if (comments != null) {
            this.visitComments(comments)
        }
    }

    private fun visitProperty(ele: PsiElement) {
        val directives = PsiTreeUtil.getChildrenOfType(ele, CaddyfileProperty::class.java) ?: return

        for (directive in directives) {
            if (directive.text.trim().endsWith("}")) {
                this.descriptors.add(
                    FoldingDescriptor(
                        directive.node,
                        directive.textRange,
                        FoldingGroup.newGroup(directive.toString()),
                        directive.firstChild.text
                    )
                )

                this.visitProperty(directive)
            }
        }
    }

    private fun visitComments(comments: Array<PsiComment>) {
        var i = 0
        while (i < comments.size) {
            val comment = comments[i ++]

            if (!comment.text.trim().startsWith(REGION_PREFIX)) {
                continue
            }

            val endText = "${comment.text.split("#")[0]}${ENDREGION_PREFIX}"
            while (i < comments.size) {
                val endComment = comments[i++]
                if (endComment.text.trim().startsWith(endText)) {
                    this.descriptors.add(
                        FoldingDescriptor(
                            comment.node,
                            TextRange(comment.textRange.startOffset, endComment.textRange.endOffset),
                            FoldingGroup.newGroup(comment.toString()),
                            comment.text.substringAfter(REGION_PREFIX).trim().ifBlank { "Click to expand" },
                        )
                    )
                    break
                }
            }
        }
    }
}

internal class CaddyfileFoldingBuilder : FoldingBuilderEx(), DumbAware {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        val descriptors: MutableList<FoldingDescriptor> = ArrayList()

        root.accept(CaddyfilePropertyVisitor(descriptors))

        return descriptors.toArray(FoldingDescriptor.EMPTY_ARRAY)
    }

    @Nullable
    override fun getPlaceholderText(@NotNull node: ASTNode): String? {
        if (node.psi is CaddyfileProperty) {
            return node.psi.firstChild.text
        }

        return null
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return false
    }
}