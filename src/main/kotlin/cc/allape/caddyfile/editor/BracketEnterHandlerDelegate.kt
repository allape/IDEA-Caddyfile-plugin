package cc.allape.caddyfile.editor

import cc.allape.caddyfile.CaddyfileFileType
import cc.allape.caddyfile.DEFAULT_SPACE_COUNT
import cc.allape.caddyfile.language.psi.CaddyfileBlock
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement
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
        if (file.fileType !is CaddyfileFileType) {
            return EnterHandlerDelegate.Result.Continue
        }

        val document = editor.document
        val text = document.text
        val offset = editor.caretModel.offset

        val block = getBlock(file.findElementAt(offset)) ?: return EnterHandlerDelegate.Result.Continue

        val lineNumber = document.getLineNumber(block.textOffset)
        val line = text.substring(document.getLineStartOffset(lineNumber), document.getLineEndOffset(lineNumber))

        val leadingIndents = line.takeWhile { it == ' ' }
        val defaultIndent = " ".repeat(DEFAULT_SPACE_COUNT)

        val newLine = "\n$leadingIndents$defaultIndent"

        var suffix = ""

        if (offset < text.length && text[offset] == '}') {
            suffix = "\n$leadingIndents"
        }

        document.insertString(offset, "$newLine$suffix")
        editor.caretModel.moveToOffset(offset + newLine.length)

        return EnterHandlerDelegate.Result.Stop
    }

    override fun postProcessEnter(
        file: PsiFile,
        editor: Editor,
        dataContext: DataContext
    ): EnterHandlerDelegate.Result {
        return EnterHandlerDelegate.Result.Continue
    }

    private fun getBlock(ele: PsiElement?): CaddyfileBlock? {
        var parent = ele?.parent
        while (parent != null) {
            if (parent is CaddyfileBlock) {
                return parent
            }
            parent = parent.parent
        }
        return null
    }
}