package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.elementType


internal class CaddyfileDocumentationProvider : AbstractDocumentationProvider() {
    override fun getCustomDocumentationElement(
        editor: Editor,
        file: PsiFile,
        contextElement: PsiElement?,
        targetOffset: Int
    ): PsiElement? {
        if (file.fileType is CaddyfileFileType) {
            if (contextElement != null && contextElement.elementType == CaddyfileTypes.DIRECTIVE) {
                return contextElement
            }
        }
        return super.getCustomDocumentationElement(editor, file, contextElement, targetOffset)
    }

    override fun generateHoverDoc(element: PsiElement, originalElement: PsiElement?): String? {
        return this.generateDoc(element, originalElement)
    }

    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        if (element == null) {
            return null
        }

        if (element.elementType == CaddyfileTypes.DIRECTIVE) {
            val dir = element.text
            if (DIRECTIVES.contains(dir)) {
                val docURL = "https://caddyserver.com/docs/caddyfile/directives/${dir}"
                return "Official Documentation for <b>${dir}</b>: <a href=\"$docURL\">$docURL</a>"
            }
            return null
        }

        if (element.elementType == CaddyfileTypes.MATCHER_DECLARATION) {
            return "<b>${element.text}</b>" +
                    "<br>${findDocumentationComment(element.parent)}" +
                    "<br><a href=\"https://caddyserver.com/docs/caddyfile/matchers\">https://caddyserver.com/docs/caddyfile/matchers</a>"
        }

        return null
    }

    /**
     * @copy https://plugins.jetbrains.com/docs/intellij/documentation-provider.html#extract-documentation-comments-from-keyvalue-definitions
     */
    private fun findDocumentationComment(property: PsiElement): String? {
        val result = ArrayList<String>()
        var element: PsiElement? = property.prevSibling ?: return null

        var continuesWhiteSpaceCount = 0
        while (element is PsiComment || element is PsiWhiteSpace) {
            if (element is PsiWhiteSpace && element.text.contains("\n")) {
                continuesWhiteSpaceCount++
            }
            if (element is PsiComment) {
                val commentText = element.getText().replaceFirst("[!# ]+".toRegex(), "")
                result.add(commentText)
                continuesWhiteSpaceCount = 0
            }
            if (continuesWhiteSpaceCount >= 2) {
                break
            }
            element = element.prevSibling
        }
        return result.reversed().joinToString("<br>")
    }
}