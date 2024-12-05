package cc.allape.caddyfile

import cc.allape.caddyfile.documentation.DIRECTIVES
import com.intellij.lang.refactoring.NamesValidator
import com.intellij.openapi.project.Project

class CaddyfileLanguageNamesValidator : NamesValidator {

    override fun isKeyword(name: String, project: Project): Boolean {
        return DIRECTIVES.find { it.name == name } != null
    }

    override fun isIdentifier(name: String, project: Project): Boolean {
        return true
    }

}