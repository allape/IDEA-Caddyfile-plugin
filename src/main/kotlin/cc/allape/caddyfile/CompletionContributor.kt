package cc.allape.caddyfile

import cc.allape.caddyfile.documentation.*
import cc.allape.caddyfile.language.psi.*
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
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
                    val ele = parameters.originalFile.findElementAt(parameters.offset - 1) ?: return
                    val parentDir = ele.parent?.parent?.parent?.firstChild

                    // global options
                    if (ele.parent?.parent?.parent == parameters.originalFile) {
                        parentDir?.takeIf { it.elementType == CaddyfileTypes.DIRECTIVE }?.text?.let {
                            GLOBAL_SUB_DIRECTIVES[it]?.forEach { subdir ->
                                resultSet.addElement(LookupElementBuilder.create("$subdir "))
                            }
                        }
                        GLOBAL_DIRECTIVES.forEach { resultSet.addElement(LookupElementBuilder.create("$it ")) }
                    } else {
                        parentDir?.takeIf { it.elementType == CaddyfileTypes.DIRECTIVE }?.text?.let {
                            SUB_DIRECTIVES[it]?.forEach { subdir ->
                                resultSet.addElement(LookupElementBuilder.create("$subdir "))
                            }
                        }
                        DIRECTIVES.forEach { resultSet.addElement(LookupElementBuilder.create("${it.name} ")) }
                    }
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

        // arg completion
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(CaddyfileTypes.ARG),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet
                ) {
                    val ele = parameters.originalFile.findElementAt(parameters.offset - 1) ?: return

                    val prevArg = ele.prevSibling?.prevSibling

                    if (prevArg != null) {
                        val text = prevArg.text

                        // fill MIME
                        if (MIME_HEADERS.contains(text)) {
                            COMMON_MIME.forEach { mime -> resultSet.addElement(LookupElementBuilder.create(mime)) }
                            return
                        }

                        // try_files.policy
                        // https://caddyserver.com/docs/caddyfile/directives/try_files#syntax
                        if (text == "policy") {
                            resultSet.addElement(LookupElementBuilder.create("first_exist"))
                            resultSet.addElement(LookupElementBuilder.create("smallest_size"))
                            resultSet.addElement(LookupElementBuilder.create("largest_size"))
                            resultSet.addElement(LookupElementBuilder.create("most_recently_modified"))
                            return
                        }
                    }

                    val dir = ele.parent?.firstChild

                    // http header
                    if (dir?.text?.contains("header") == true) {
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