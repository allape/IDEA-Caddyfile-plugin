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
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.util.elementType
import kotlin.random.Random

class HashPasswordIntentionAction : IntentionAction {
    private fun getArgElementOrNull(editor: Editor?, file: PsiFile?): PsiElement? {
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

    override fun startInWriteAction(): Boolean {
        return true
    }

    override fun getFamilyName(): String {
        return "Caddyfile password hashing"
    }

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {
        return getArgElementOrNull(editor, file) != null
    }

    override fun getText(): String {
        return "Hash to password"
    }

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        if (editor == null || file == null) {
            return
        }

        val ele = getArgElementOrNull(editor, file) ?: return
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