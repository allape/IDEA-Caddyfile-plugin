package cc.allape.caddyfile.editor

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

class BraceTypedHandlerDelegate : TypedHandlerDelegate() {
    override fun charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result {
        var suffix = ""

        val document = editor.document
        val text = document.text
        val offset = editor.caretModel.offset

        when (c) {
            '(' -> suffix = ")"
            '[' -> suffix = "]"
            '{' -> {
                if (offset - 2 >= 0 && document.text[offset - 2] == '$') {
                    suffix = "}"
                }
            }
        }

        if (suffix != "") {
            if (offset < text.length && text[offset] == suffix[0]) {
                return Result.DEFAULT
            }
            document.insertString(offset, suffix)
            editor.caretModel.moveToOffset(offset)
            return Result.DEFAULT
        }

        return super.charTyped(c, project, editor, file)
    }
}