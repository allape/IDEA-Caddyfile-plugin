package cc.allape.caddyfile

import cc.allape.caddyfile.documentation.*
import cc.allape.caddyfile.language.psi.*
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext

internal class CaddyfileCompletionContributor : CompletionContributor() {
    init {
        // directive completion
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(CaddyfileTypes.DIRECTIVE),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet
                ) {
                    DIRECTIVES.forEach { resultSet.addElement(LookupElementBuilder.create("${it.name} ")) }
                }
            }
        )

        // snippet completion
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(CaddyfileTypes.SNIPPET_NAME_TEXT),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet
                ) {
                    PsiTreeUtil.findChildrenOfType(parameters.originalFile, CaddyfileSnippetDeclaration::class.java)
                        .forEach { snippet ->
                            resultSet.addElement(LookupElementBuilder.create(snippet.snippetName))
                        }
                }
            }
        )

        // matcher completion
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(CaddyfileTypes.AT_MATCHER_NAME).withParents(
                CaddyfileMatcherDeclaration::class.java,
                CaddyfileMatcherThr::class.java,
            ),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet
                ) {
                    PsiTreeUtil.findChildrenOfType(parameters.originalFile, CaddyfileMatcherDeclaration::class.java)
                        .forEach { md ->
                            if (md.parent is CaddyfileProperty) {
                                resultSet.addElement(LookupElementBuilder.create(md))
                            }
                        }
                }
            }
        )

        // variable completion
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(CaddyfileTypes.VARIABLE_NAME),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet
                ) {
                    PLACEHOLDERS.forEach { resultSet.addElement(LookupElementBuilder.create(it)) }
                }
            }
        )

        // http header completion
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(CaddyfileTypes.ARG),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet
                ) {
                    val ele = parameters.originalFile.findElementAt(parameters.offset - 1) ?: return

                    ele.prevSibling?.prevSibling?.text?.let {
                        it.lowercase().let { text ->
                            if (MIME_HEADERS.contains(text)) {
                                COMMON_MIME.forEach { mime -> resultSet.addElement(LookupElementBuilder.create(mime)) }
                                return
                            }
                        }
                    }

                    if (ele.parent?.firstChild?.text?.contains("header") == true) {
                        HTTP_HEADERS.forEach {
                            resultSet.addElement(
                                LookupElementBuilder.create("${it.key} ").withLookupString(it.key.lowercase())
                            )
                        }
                        return
                    }
                }
            }
        )
    }
}