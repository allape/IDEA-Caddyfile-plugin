package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileSnippet
import cc.allape.caddyfile.language.psi.CaddyfileSnippetDeclaration
import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement

internal class CaddyfileRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(
        elementToRename: PsiElement, context: PsiElement?
    ): Boolean {
        return when (elementToRename) {
            is CaddyfileMatcherDeclaration -> true
            is CaddyfileSnippetDeclaration -> true
            is CaddyfileSnippet -> true
            else -> false
        }
    }
}
