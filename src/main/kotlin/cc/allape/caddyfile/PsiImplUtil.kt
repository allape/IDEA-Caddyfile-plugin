package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType


object CaddyfilePsiImplUtil {

    @JvmStatic
    fun getName(element: PsiElement): String {
        return element.text
    }

    @JvmStatic
    fun setName(element: PsiElement, newName: String?): PsiElement {
        return when (element.elementType) {
            CaddyfileTypes.MATCHER_DECLARATION -> {
                val newEle = ElementFactory.createMatcherDeclaration(element.project, newName ?: "@???")
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
