package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileSnippetName
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory

class ElementFactory {
    companion object {
        private fun createFile(project: Project, text: String): CaddyfileFile {
            val name = "dummy.Caddyfile"
            return PsiFileFactory.getInstance(project)
                .createFileFromText(name, CaddyfileFileType.INSTANCE, text) as CaddyfileFile
        }

        fun createMatcherDeclaration(project: Project, text: String): CaddyfileMatcherDeclaration {
            if (!text.startsWith("@")) {
                throw IllegalArgumentException("text of matcher declaration must start with `@`")
            }
            return createFile(project, text).firstChild.firstChild as CaddyfileMatcherDeclaration
        }

        fun createSnippetName(project: Project, text: String): CaddyfileSnippetName {
            return createFile(project, "($text)").firstChild.firstChild.children[0] as CaddyfileSnippetName
        }

        fun createArg(project: Project, text: String): PsiElement {
            return createFile(project, "directive $text").firstChild.lastChild
        }

        fun createComment(project: Project, comment: String): PsiComment {
            return createFile(project, "# $comment").firstChild as PsiComment
        }

        fun createWhiteSpace(project: Project): PsiElement {
            return createFile(project, " ").firstChild
        }
    }
}