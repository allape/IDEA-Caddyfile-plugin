package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileProperty
import cc.allape.caddyfile.language.psi.CaddyfileTypes

object CaddyfilePsiImplUtil {
    @JvmStatic
    fun getKey(element: CaddyfileProperty): String? {
        return element.node.findChildByType(CaddyfileTypes.HOST)?.text
    }

    @JvmStatic
    fun getValue(element: CaddyfileProperty): String? {
        return element.node.findChildByType(CaddyfileTypes.GROUP)?.text
    }
}
