package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import com.intellij.lang.refactoring.RefactoringSupportProvider
import com.intellij.psi.PsiElement


internal class CaddyfileRefactoringSupportProvider : RefactoringSupportProvider() {
    override fun isMemberInplaceRenameAvailable(
        elementToRename: PsiElement,
        context: PsiElement?
    ): Boolean {
        return (elementToRename is CaddyfileMatcherDeclaration)
    }
}