package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory

class ElementFactory {
    companion object {
        fun createMatcherDeclaration(project: Project, text: String): CaddyfileMatcherDeclaration {
            if (!text.startsWith("@")) {
                throw IllegalArgumentException("text of matcher declaration must start with `@`")
            }
            return createFile(project, text).firstChild.firstChild as CaddyfileMatcherDeclaration
        }

        fun createFile(project: Project, text: String): CaddyfileFile {
            val name = "dummy.Caddyfile"
            return PsiFileFactory.getInstance(project).createFileFromText(name, CaddyfileFileType.INSTANCE, text) as CaddyfileFile
        }
    }
}