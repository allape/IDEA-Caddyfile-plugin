package cc.allape.caddyfile.editor

import cc.allape.caddyfile.CaddyfileFileType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.testFramework.utils.vfs.getPsiFile


class CaddyfileDocumentListener : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        val document = event.document
        val editor = EditorFactory.getInstance().getEditors(document).firstOrNull() ?: return
        val project = editor.project ?: return
        val file = editor.virtualFile?.getPsiFile(project) ?: return

        if (file.fileType !is CaddyfileFileType) {
            return
        }

        val text = document.text
        val oldText = event.oldFragment
        val newText = event.newFragment
        val offset = event.offset

        // only watch for one char deletion
        if (newText != "" || oldText.length != 1) {
            return
        }

        if (offset >= text.length) {
            return
        }

        val charAfterOldText = text[offset]

        val shouldDeletePairedBrace = when (oldText[0]) {
            '(' -> charAfterOldText == ')'
            '[' -> charAfterOldText == ']'
            '{' -> charAfterOldText == '}'
            else -> {
                false
            }
        }

        if (shouldDeletePairedBrace) {
            ApplicationManager.getApplication().invokeLater {
                WriteCommandAction.runWriteCommandAction(project) {
                    document.deleteString(offset, offset + 1)
                }
            }
        }
    }
}