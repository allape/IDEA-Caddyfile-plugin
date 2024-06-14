package cc.allape.caddyfile

import cc.allape.caddyfile.language.parser.CaddyfileParser
import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.jetbrains.annotations.NotNull


interface CaddyfileTokenSets {
    companion object {
        val IDENTIFIERS: TokenSet = TokenSet.create(CaddyfileTypes.MATCHER_DECLARATION)
        val COMMENTS: TokenSet = TokenSet.create(CaddyfileTypes.COMMENT)
    }
}

class CaddyfileFile(@NotNull viewProvider: FileViewProvider?) :
    PsiFileBase(viewProvider!!, CaddyfileLanguage.INSTANCE) {
    @NotNull
    override fun getFileType(): FileType {
        return CaddyfileFileType.INSTANCE
    }

    override fun toString(): String {
        return "Caddyfile File"
    }
}

@Suppress("CompanionObjectInExtension")
internal class CaddyfileParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer {
        return CaddyfileLexerAdapter()
    }

    override fun getCommentTokens(): TokenSet {
        return CaddyfileTokenSets.COMMENTS
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createParser(project: Project?): PsiParser {
        return CaddyfileParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return CaddyfileFile(viewProvider)
    }

    override fun createElement(node: ASTNode?): PsiElement {
        return CaddyfileTypes.Factory.createElement(node)
    }

    companion object {
        val FILE: IFileElementType = IFileElementType(CaddyfileLanguage.INSTANCE)
    }
}