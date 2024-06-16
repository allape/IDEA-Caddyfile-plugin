package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileMatcherThr
import cc.allape.caddyfile.language.psi.CaddyfileProperty
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext


internal class CaddyfileMatcherReference(element: PsiElement, textRange: TextRange) :
    PsiReferenceBase<PsiElement?>(element, textRange) {

    private val key = element.text

    companion object {
        fun getMatchersByName(
            element: PsiElement,
            name: String,
            isDeclaration: Boolean
        ): List<CaddyfileMatcherDeclaration> {
            val res = ArrayList<CaddyfileMatcherDeclaration>()
            val matchers: Collection<CaddyfileMatcherDeclaration> =
                PsiTreeUtil.findChildrenOfType(element.containingFile, CaddyfileMatcherDeclaration::class.java)
            for (matcher in matchers.reversed()) {
                if (matcher.text == name) {
                    if (isDeclaration) {
                        if (matcher.parent is CaddyfileProperty) {
                            res.add(matcher)
                        }
                    } else {
                        if (matcher.parent !is CaddyfileProperty) {
                            res.add(matcher)
                        }
                    }
                }
            }
            return res
        }
    }

    override fun resolve(): PsiElement? {
        val matcherDeclarations = getMatchersByName(element, key, true)
        return if (matcherDeclarations.isNotEmpty()) matcherDeclarations[0] else null
    }

    override fun getVariants(): Array<Any> {
        val matchers: List<CaddyfileMatcherDeclaration> = getMatchersByName(element, key, true)
        val variants: MutableList<LookupElement> = ArrayList()
        for (m in matchers) {
            variants.add(
                LookupElementBuilder
                    .create(m).withIcon(CaddyfileIcons.FILE)
                    .withTypeText(m.containingFile.name)
            )
        }
        return variants.toTypedArray()
    }
}

internal class CaddyfileReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(CaddyfileMatcherDeclaration::class.java)
                .withParent(CaddyfileMatcherThr::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement,
                    context: ProcessingContext
                ): Array<PsiReference> {
                    return arrayOf(
                        CaddyfileMatcherReference(
                            element,
                            TextRange(0, element.text.length),
                        )
                    )
                }
            },
        )
    }
}
