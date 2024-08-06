package cc.allape.caddyfile

import com.intellij.psi.tree.IElementType

class CaddyfileTokenType(debugName: String) :
    IElementType(debugName, CaddyfileLanguage.INSTANCE) {
    override fun toString(): String {
        return "CaddyfileTokenType." + super.toString()
    }
}

class CaddyfileElementType(debugName: String) :
    IElementType(debugName, CaddyfileLanguage.INSTANCE)