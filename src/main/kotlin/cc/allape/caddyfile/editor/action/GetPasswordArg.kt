package cc.allape.caddyfile.editor.action

import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.util.elementType

fun getPasswordArg(editor: Editor?, file: PsiFile?): PsiElement? {
    if (editor == null || file == null) {
        return null
    }

    var ele = file.findElementAt(editor.caretModel.offset)

    if (ele?.elementType == TokenType.WHITE_SPACE) {
        val prevProperty = ele?.prevSibling
        if (prevProperty?.elementType == CaddyfileTypes.PROPERTY) {
            ele = prevProperty?.lastChild
        }
    }

    if (ele == null) {
        return null
    }

    // property:
    //   basic_auth
    //   matcher?
    //   hash_algorithm?
    //   block:
    //     property:
    //        username
    //        password

    val property = ele.parent
    val block = property?.parent
    val topProperty = block?.parent

    if (
        ele.elementType != CaddyfileTypes.ARG ||
        property?.elementType != CaddyfileTypes.PROPERTY ||
        topProperty?.firstChild?.text != "basic_auth"
    ) {
        return null
    }

    return ele
}