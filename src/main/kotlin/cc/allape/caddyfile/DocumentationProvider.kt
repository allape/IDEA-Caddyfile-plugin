package cc.allape.caddyfile

import cc.allape.caddyfile.documentation.DIRECTIVES
import cc.allape.caddyfile.documentation.DirectiveDocURLPrefix
import cc.allape.caddyfile.documentation.MarcherDocURL
import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.elementType
import com.intellij.psi.util.siblings


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
            var comment = ""

            val dir = element.text
            DIRECTIVES.find{it.name == dir}?.let {
                val docURL = "$DirectiveDocURLPrefix${dir}"
                // language=html
                comment = """
                    <a href="$docURL">$docURL</a>
                    <hr>
                    <h1>${it.name}</h1>
                    <div>${it.description}</div>
                    <h2>Syntax</h2>
                    <div>${it.syntax}</div>
                    <h2>Examples</h2>
                    <div>${it.examples}</div>
                """.trimIndent()
            }

            val userComment = getUserComment(element)

            comment = if (comment.isEmpty()) {
                userComment
            } else {
                "$comment<hr>$userComment"
            }

            return comment.ifEmpty {
                null
            }
        }

        if (element.elementType == CaddyfileTypes.MATCHER_DECLARATION) {
            return "<b>${element.text}</b>" +
                    "<br>${getUserComment(element)}" +
                    "<br><a href=\"$MarcherDocURL\">$MarcherDocURL</a>"
        }

        return null
    }

    /**
     * @copy https://plugins.jetbrains.com/docs/intellij/documentation-provider.html#extract-documentation-comments-from-keyvalue-definitions
     */
    private fun getUserComment(element: PsiElement): String {
        val comments = ArrayList<String>()
        var textLength = 0

        element.parent?.siblings(forward = false, withSelf = false)?.let { siblings ->
            var whitespaceCount = 0
            for (sibling in siblings) {
                if (sibling is PsiWhiteSpace) {
                    // only count new lines
                    if (sibling.text.contains("\n")) {
                        whitespaceCount += 1
                        if (whitespaceCount >= 2) {
                            break
                        }
                    }
                    continue
                }

                whitespaceCount = 0
                if (sibling is PsiComment) {
                    val comment = sibling.text.replaceFirst(Regex("[^#]*#"), "")
                    comments.add(comment)
                    textLength += comment.length
                    continue
                }

                break
            }
        }

        if (comments.isEmpty()) {
            return ""
        }

        return comments.reversed().joinToString("<br>") + "<br>"
    }
}