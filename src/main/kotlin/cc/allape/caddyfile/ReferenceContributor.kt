package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileProperty
import cc.allape.caddyfile.language.psi.CaddyfileSnippet
import cc.allape.caddyfile.language.psi.CaddyfileSnippetDeclaration
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext

internal abstract class BaseReference(element: PsiElement) :
    PsiReferenceBase<PsiElement?>(element, TextRange(0, element.text.length)), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return getRefs().map { PsiElementResolveResult(it) }.toTypedArray()
    }

    override fun resolve(): PsiElement? {
        return getRefs().takeIf { it.size == 1 }?.get(0)
    }

    override fun getVariants(): Array<Any> {
        return getRefs().map {
            LookupElementBuilder.create(it).withIcon(CaddyfileIcons.FILE).withTypeText(it.containingFile.name)
        }.toTypedArray()
    }

    abstract fun getRefs(): List<PsiElement>
}

internal class CaddyfileMatcherReference(element: PsiElement) : BaseReference(element) {
    override fun getRefs(): List<PsiElement> {
        val name = element.text
        return PsiTreeUtil.findChildrenOfType(element.containingFile, CaddyfileMatcherDeclaration::class.java).filter {
            it.text == name && it.parent is CaddyfileProperty
        }
    }
}

internal class CaddyfileSnippetReference(element: PsiElement) : BaseReference(element) {
    private val name = "(${element.text})"
    override fun getRefs(): List<PsiElement> {
        return PsiTreeUtil.findChildrenOfType(element.containingFile, CaddyfileSnippetDeclaration::class.java).filter {
            it.text == name
        }
    }
}

internal class CaddyfileReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(CaddyfileMatcherDeclaration::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement, context: ProcessingContext
                ): Array<PsiReference> {
                    if (element.parent is CaddyfileProperty) {
                        return emptyArray()
                    }
                    return arrayOf(CaddyfileMatcherReference(element))
                }
            },
        )

        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(CaddyfileSnippet::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement, context: ProcessingContext
                ): Array<PsiReference> {
                    return arrayOf(CaddyfileSnippetReference(element))
                }
            },
        )
    }
}
