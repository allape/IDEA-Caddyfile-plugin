package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileProperty
import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet


internal class CaddyfileFindUsagesProvider : FindUsagesProvider {
    override fun getWordsScanner(): WordsScanner {
        return DefaultWordsScanner(
            CaddyfileLexerAdapter(),
            CaddyfileTokenSets.IDENTIFIERS,
            CaddyfileTokenSets.COMMENTS,
            TokenSet.EMPTY
        )
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        if (psiElement is PsiNamedElement) {
            if (psiElement is CaddyfileMatcherDeclaration) {
                return psiElement.parent is CaddyfileProperty
            }
            return true
        }
        return false
    }

    override fun getHelpId(psiElement: PsiElement): String? {
        return null
    }

    override fun getType(element: PsiElement): String {
        if (element is CaddyfileMatcherDeclaration) {
            return "Caddyfile Matcher Declaration"
        }
        return ""
    }

    override fun getDescriptiveName(element: PsiElement): String {
        if (element is CaddyfileMatcherDeclaration) {
            return element.text
        }
        return ""
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        if (element is CaddyfileMatcherDeclaration) {
            return element.text
        }
        return ""
    }
}