package cc.allape.caddyfile

import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull

class CaddyfileTokenType(@NotNull @NonNls debugName: String) :
    IElementType(debugName, CaddyfileLanguage.INSTANCE) {
    override fun toString(): String {
        return "CaddyfileTokenType." + super.toString()
    }
}

class CaddyfileElementType(@NonNls debugName: String) :
    IElementType(debugName, CaddyfileLanguage.INSTANCE)