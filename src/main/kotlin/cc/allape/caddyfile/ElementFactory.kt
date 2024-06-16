package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileProperty
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import org.junit.Assert

class ElementFactory {
    companion object {
        fun createMatcherDeclaration(project: Project, text: String): CaddyfileMatcherDeclaration {
            Assert.assertTrue("text of matcher declaration must start with `@`", text.startsWith("@"))
            return createFile(project, text).firstChild.firstChild as CaddyfileMatcherDeclaration
        }

        fun createProperty(project: Project, text: String): CaddyfileProperty {
            return createFile(project, text).firstChild as CaddyfileProperty
        }

        fun createFile(project: Project, text: String): CaddyfileFile {
            val name = "dummy.Caddyfile"
            return PsiFileFactory.getInstance(project).createFileFromText(name, CaddyfileFileType.INSTANCE, text) as CaddyfileFile
        }
    }
}