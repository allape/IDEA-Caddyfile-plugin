package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileProperty

class Util {
    companion object {
        fun renameMatcherDeclaration(old: CaddyfileMatcherDeclaration, newName: String): CaddyfileMatcherDeclaration {
            val newDec = ElementFactory.createMatcherDeclaration(old.project, "@${newName}")
            old.lastChild.replace(newDec.lastChild)
            return old
        }
    }
}