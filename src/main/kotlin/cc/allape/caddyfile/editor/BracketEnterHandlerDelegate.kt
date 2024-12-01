package cc.allape.caddyfile.editor

import cc.allape.caddyfile.DEFAULT_SPACE_COUNT
import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiFile
import com.intellij.psi.util.elementType

class BracketEnterHandlerDelegate : EnterHandlerDelegate {
    override fun preprocessEnter(
        file: PsiFile,
        editor: Editor,
        caretOffset: Ref<Int>,
        caretAdvance: Ref<Int>,
        dataContext: DataContext,
        originalHandler: EditorActionHandler?
    ): EnterHandlerDelegate.Result {
        val offset = editor.caretModel.offset - 1

        if (offset <= 0) {
            return EnterHandlerDelegate.Result.Continue
        }

        val document = editor.document
        val text = document.text

        if (text[offset] == '{') {
            val ele = file.findElementAt(offset)

            if (ele == null || ele.elementType != CaddyfileTypes.LCB) {
                return EnterHandlerDelegate.Result.Continue
            }

            val alreadyInPair = ele.parent?.text?.endsWith("}") == true

            val lineNumber = document.getLineNumber(offset)
            val line = text.substring(document.getLineStartOffset(lineNumber), document.getLineEndOffset(lineNumber))

            val spaces = "\n" + line.takeWhile { it == ' ' || it == '\t' }
            val defaultSpaces = " ".repeat(DEFAULT_SPACE_COUNT)

            document.insertString(offset + 1, "$spaces$defaultSpaces${if (alreadyInPair) "" else "$spaces}"}")
            editor.caretModel.moveToOffset(offset + 1 + spaces.length + defaultSpaces.length)
            return EnterHandlerDelegate.Result.Stop
        }

        return EnterHandlerDelegate.Result.Continue
    }

    override fun postProcessEnter(
        file: PsiFile,
        editor: Editor,
        dataContext: DataContext
    ): EnterHandlerDelegate.Result {
        return EnterHandlerDelegate.Result.Continue
    }
}