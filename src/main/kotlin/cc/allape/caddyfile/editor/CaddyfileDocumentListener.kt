package cc.allape.caddyfile.editor

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener


class CaddyfileDocumentListener : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        val document = event.document
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
                EditorFactory.getInstance().getEditors(document).firstOrNull()?.project?.let {
                    WriteCommandAction.runWriteCommandAction(it) {
                        document.deleteString(offset, offset + 1)
                    }
                }
            }
        }
    }
}