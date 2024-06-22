package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration

class Util {
    companion object {
        fun renameMatcherDeclaration(old: CaddyfileMatcherDeclaration, newName: String): CaddyfileMatcherDeclaration {
            val newDec = ElementFactory.createMatcherDeclaration(old.project, "@${newName}")
            old.replace(newDec)
            return old
        }
    }
}