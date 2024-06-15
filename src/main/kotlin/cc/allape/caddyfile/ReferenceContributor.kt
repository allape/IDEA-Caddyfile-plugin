package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileProperty
import com.intellij.codeInsight.lookup.LookupElement
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
        fun getMatchersByName(element: PsiElement, name: String, isDeclaration: Boolean): List<CaddyfileMatcherDeclaration> {
            val res = ArrayList<CaddyfileMatcherDeclaration>()
            val matchers: Collection<CaddyfileMatcherDeclaration> = PsiTreeUtil.findChildrenOfType(element.containingFile, CaddyfileMatcherDeclaration::class.java)
            for (matcher in matchers) {
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

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val matchers: List<CaddyfileMatcherDeclaration> = getMatchersByName(element, key, true)
        val results: ArrayList<ResolveResult> = ArrayList()
        for (matcher in matchers) {
            results.add(PsiElementResolveResult(matcher))
        }
        return results.toArray(arrayOfNulls<ResolveResult>(0))
    }

    override fun resolve(): PsiElement? {
        val resolveResults: Array<ResolveResult> = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
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

internal class CaddyfileMatcherDeclarationReference(element: PsiElement, textRange: TextRange, private val caller: PsiElement) :
    PsiReferenceBase<PsiElement?>(element, textRange), PsiPolyVariantReference {

    private val key = element.text

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return arrayOf(PsiElementResolveResult(caller))
    }

    override fun resolve(): PsiElement {
        return caller
    }

    override fun getVariants(): Array<Any> {
        val matchers = CaddyfileMatcherReference.getMatchersByName(element, key, true)
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
            PlatformPatterns.psiElement(CaddyfileMatcherDeclaration::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement,
                    context: ProcessingContext
                ): Array<PsiReference> {
                    if (element.parent is CaddyfileProperty) {
                        val callers = CaddyfileMatcherReference.getMatchersByName(element, element.text, false)
                        val refs = ArrayList<PsiReference>()
                        for (caller in callers) {
                            refs.add(
                                CaddyfileMatcherDeclarationReference(
                                    element,
                                    TextRange(0, element.text.length),
                                    caller
                                )
                            )
                        }
                        return refs.toTypedArray()
                    } else {
                        return arrayOf(
                            CaddyfileMatcherReference(
                                element,
                                TextRange(0, element.text.length),
                            )
                        )
                    }
                }
            })
    }
}
