package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import org.junit.Assert

class ElementFactory {
    companion object {
        fun createMatcherDeclaration(project: Project, text: String): CaddyfileMatcherDeclaration {
            Assert.assertTrue("text of matcher declaration must start with `@`", text.startsWith("@"))
            return createFile(project, text).firstChild.firstChild as CaddyfileMatcherDeclaration
        }

        fun createFile(project: Project, text: String): CaddyfileFile {
            val name = "dummy.Caddyfile"
            return PsiFileFactory.getInstance(project).createFileFromText(name, CaddyfileFileType.INSTANCE, text) as CaddyfileFile
        }
    }
}