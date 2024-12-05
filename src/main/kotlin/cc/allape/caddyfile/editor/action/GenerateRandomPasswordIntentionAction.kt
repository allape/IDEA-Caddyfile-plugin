package cc.allape.caddyfile.editor.action

import cc.allape.caddyfile.ElementFactory
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.preview.IntentionPreviewInfo
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import kotlin.random.Random

const val CharSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-"

class GenerateRandomPasswordIntentionAction : IntentionAction {
    override fun startInWriteAction(): Boolean {
        return true
    }

    override fun getFamilyName(): String {
        return "Caddyfile password generation"
    }

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {
        return getPasswordArg(editor, file) != null
    }

    override fun getText(): String {
        return "Create a URL-safe random password"
    }

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        if (editor == null || file == null) {
            return
        }

        val ele = getPasswordArg(editor, file) ?: return
        val password = ele.text.trim()

        val randomPassword =
            (1..password.length).map { Random.nextInt(CharSet.length) }.map(CharSet::get).joinToString("")

        ele.replace(ElementFactory.createArg(project, randomPassword))
    }

    override fun generatePreview(project: Project, editor: Editor, file: PsiFile): IntentionPreviewInfo {
        return IntentionPreviewInfo.EMPTY
    }
}