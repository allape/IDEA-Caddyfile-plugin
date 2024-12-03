package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileSnippetName
import com.intellij.psi.PsiElement


object CaddyfilePsiImplUtil {

    @JvmStatic
    fun getName(element: PsiElement): String {
        return element.text
    }

    @JvmStatic
    fun setName(element: PsiElement, newName: String?): PsiElement {
        if (newName == null) {
            return element
        }

        return when (element) {
            is CaddyfileMatcherDeclaration -> {
                if (!newName.startsWith("@")) {
                    return element
                }
                val newEle = ElementFactory.createMatcherDeclaration(element.project, newName)
                element.replace(newEle)
                return newEle
            }

            is CaddyfileSnippetName -> {
                val newEle = ElementFactory.createSnippetName(element.project, newName)
                element.replace(newEle)
                return newEle
            }

            else -> {
                element
            }
        }
    }

    @JvmStatic
    fun getNameIdentifier(element: PsiElement): PsiElement {
        return element
    }

}
