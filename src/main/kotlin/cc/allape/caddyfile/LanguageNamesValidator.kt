package cc.allape.caddyfile

import com.intellij.lang.refactoring.NamesValidator
import com.intellij.openapi.project.Project

class CaddyfileLanguageNamesValidator : NamesValidator {

    override fun isKeyword(name: String, project: Project): Boolean {
        return false
    }

    override fun isIdentifier(name: String, project: Project): Boolean {
        return name.startsWith("@") || (name.startsWith("(") && name.endsWith(")"))
    }

}