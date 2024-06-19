package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileProperty
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext


internal class CaddyfileMatcherReference(element: PsiElement, textRange: TextRange) :
    PsiReferenceBase<PsiElement?>(element, textRange), PsiPolyVariantReference {

    private val key = element.text

    companion object {
        fun getMatcherRefs(
            scopedElement: PsiElement,
            name: String,
        ): List<CaddyfileMatcherDeclaration> {
            return PsiTreeUtil.findChildrenOfType(scopedElement, CaddyfileMatcherDeclaration::class.java).filter {
                it.text == name && it.parent is CaddyfileProperty
            }
        }
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return getMatcherRefs(element.containingFile, key).map { PsiElementResolveResult(it) }.toTypedArray()
    }

    override fun resolve(): PsiElement? {
        val matcherDeclarations = getMatcherRefs(element.containingFile, key)
        return if (matcherDeclarations.size == 1) matcherDeclarations[0] else null
    }

    override fun getVariants(): Array<Any> {
        return getMatcherRefs(element.containingFile, key).map {
            LookupElementBuilder
                .create(it).withIcon(CaddyfileIcons.FILE)
                .withTypeText(it.containingFile.name)
        }.toTypedArray()
    }
}

internal class CaddyfileReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(CaddyfileMatcherDeclaration::class.java),
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
