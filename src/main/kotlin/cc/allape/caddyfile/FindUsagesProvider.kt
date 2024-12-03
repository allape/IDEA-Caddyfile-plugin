package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileProperty
import cc.allape.caddyfile.language.psi.CaddyfileSnippetDeclaration
import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet


internal class CaddyfileFindUsagesProvider : FindUsagesProvider {
    override fun getWordsScanner(): WordsScanner {
        return DefaultWordsScanner(
            CaddyfileLexerAdapter(), CaddyfileTokenSets.IDENTIFIERS, CaddyfileTokenSets.COMMENTS, TokenSet.EMPTY
        )
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        return when (psiElement) {
            is CaddyfileMatcherDeclaration -> psiElement.parent is CaddyfileProperty
            is CaddyfileSnippetDeclaration -> true
            else -> false
        }
    }

    override fun getHelpId(psiElement: PsiElement): String? {
        return null
    }

    override fun getType(element: PsiElement): String {
        return when (element) {
            is CaddyfileMatcherDeclaration -> "Caddyfile Matcher Declaration"
            is CaddyfileSnippetDeclaration -> "Caddyfile Snippet Declaration"
            else -> ""
        }
    }

    override fun getDescriptiveName(element: PsiElement): String {
        return when (element) {
            is CaddyfileMatcherDeclaration -> element.text
            is CaddyfileSnippetDeclaration -> element.text
            else -> ""
        }
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return when (element) {
            is CaddyfileMatcherDeclaration -> element.text
            is CaddyfileSnippetDeclaration -> element.text
            else -> ""
        }
    }
}