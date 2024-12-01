package cc.allape.caddyfile.editor

import cc.allape.caddyfile.DEFAULT_SPACE_COUNT
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiFile

class BracketEnterHandlerDelegate : EnterHandlerDelegate {
    override fun preprocessEnter(
        file: PsiFile,
        editor: Editor,
        caretOffset: Ref<Int>,
        caretAdvance: Ref<Int>,
        dataContext: DataContext,
        originalHandler: EditorActionHandler?
    ): EnterHandlerDelegate.Result {
        val document = editor.document
        val text = document.text
        val offset = editor.caretModel.offset

        val lineNumber = document.getLineNumber(offset)
        val line = text.substring(document.getLineStartOffset(lineNumber), document.getLineEndOffset(lineNumber))

        val tabs = line.takeWhile { it == ' ' || it == '\t' }
        val nextLineTabs = " ".repeat(DEFAULT_SPACE_COUNT)

        if (offset > 0 && text[offset - 1] == '{') {
            document.insertString(offset, "\n$tabs$nextLineTabs\n$tabs}")
            editor.caretModel.moveToOffset(offset + tabs.length + nextLineTabs.length + 1)
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