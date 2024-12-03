package cc.allape.caddyfile.element

import cc.allape.caddyfile.language.psi.CaddyfileSnippetName
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.LocalSearchScope
import com.intellij.psi.search.SearchScope

interface CaddyfileSnippetNamedElement : PsiNameIdentifierOwner

abstract class CaddyfileSnippetNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node),
    CaddyfileSnippetNamedElement {
    override fun getReferences(): Array<PsiReference> {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this)
    }

    override fun getUseScope(): SearchScope {
        return LocalSearchScope(containingFile)
    }

    override fun getResolveScope(): GlobalSearchScope {
        return GlobalSearchScope.fileScope(containingFile)
    }
}

class CaddyfileSnippetNameManipulator : AbstractElementManipulator<CaddyfileSnippetName>() {
    override fun handleContentChange(
        element: CaddyfileSnippetName,
        range: TextRange,
        newContent: String?
    ): CaddyfileSnippetName {
        return element.setName(newContent) as CaddyfileSnippetName
    }
}
