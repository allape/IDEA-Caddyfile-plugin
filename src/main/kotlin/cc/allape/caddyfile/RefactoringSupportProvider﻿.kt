package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileSnippetName
import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement

internal class CaddyfileRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(
        elementToRename: PsiElement, context: PsiElement?
    ): Boolean {
        return when (elementToRename) {
            is CaddyfileMatcherDeclaration -> true
            is CaddyfileSnippetName -> true
            else -> false
        }
    }
}
