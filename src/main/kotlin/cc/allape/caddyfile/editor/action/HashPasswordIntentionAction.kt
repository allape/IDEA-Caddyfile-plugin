package cc.allape.caddyfile.editor.action

import at.favre.lib.crypto.bcrypt.BCrypt
import cc.allape.caddyfile.ElementFactory
import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.preview.IntentionPreviewInfo
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiFile
import kotlin.random.Random

class HashPasswordIntentionAction : IntentionAction {
    override fun startInWriteAction(): Boolean {
        return true
    }

    override fun getFamilyName(): String {
        return "Caddyfile password hashing"
    }

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {
        return getPasswordArg(editor, file) != null
    }

    override fun getText(): String {
        return "Hash password"
    }

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        if (editor == null || file == null) {
            return
        }

        val ele = getPasswordArg(editor, file) ?: return
        val property = ele.parent
        val block = property?.parent
        val topProperty = block?.parent

        val hashAlgorithm = topProperty?.node?.findChildByType(CaddyfileTypes.ARG)?.text ?: "bcrypt"
        val password = ele.text.trim()

        when (hashAlgorithm) {
            "bcrypt" -> {
                val hashed = BCrypt.withDefaults().hashToString(Random.nextInt(8, 15), password.toCharArray())
                ele.replace(ElementFactory.createArg(project, hashed))
                property.add(ElementFactory.createWhiteSpace(project))
                property.add(ElementFactory.createComment(project, password))
            }

            else -> {
                ApplicationManager.getApplication().invokeLater {
                    Messages.showWarningDialog(project, "Unsupported hash algorithm: $hashAlgorithm", "Warning")
                }
            }
        }
    }

    override fun generatePreview(project: Project, editor: Editor, file: PsiFile): IntentionPreviewInfo {
        return IntentionPreviewInfo.EMPTY
    }
}