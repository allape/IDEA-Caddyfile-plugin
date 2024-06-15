package cc.allape.caddyfile.element

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry

interface CaddyfileMatcherNamedElement : PsiNameIdentifierOwner

abstract class CaddyfileMatcherNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node),
    CaddyfileMatcherNamedElement {
    override fun setName(name: String): PsiElement {
        // TODO
        return this
    }

    override fun getNameIdentifier(): PsiElement? {
        // TODO
        return node.psi
    }

    override fun getReferences(): Array<PsiReference> {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this)
//        return arrayOf(
//            CaddyfileMatcherDeclarationReference(
//                this,
//                TextRange(0, this.text.length)
//            )
//        )
    }
}