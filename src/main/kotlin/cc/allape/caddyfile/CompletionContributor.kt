package cc.allape.caddyfile

import cc.allape.caddyfile.documentation.DIRECTIVES
import cc.allape.caddyfile.documentation.PLACEHOLDERS
import cc.allape.caddyfile.language.psi.CaddyfileMatcherDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileMatcherThr
import cc.allape.caddyfile.language.psi.CaddyfileProperty
import cc.allape.caddyfile.language.psi.CaddyfileSnippetDeclaration
import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext

internal class CaddyfileCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(CaddyfileTypes.DIRECTIVE),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet
                ) {
                    DIRECTIVES.forEach { resultSet.addElement(LookupElementBuilder.create("${it.name} ")) }
                }
            })

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
            })

        extend(
            CompletionType.BASIC, PlatformPatterns.psiElement(CaddyfileTypes.AT_MATCHER_NAME).withParents(
                    CaddyfileMatcherDeclaration::class.java,
                    CaddyfileMatcherThr::class.java,
                ), object : CompletionProvider<CompletionParameters?>() {
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
            })

        extend(
            CompletionType.BASIC, PlatformPatterns.psiElement(CaddyfileTypes.VARIABLE_NAME), object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet
                ) {
                    PLACEHOLDERS.forEach { resultSet.addElement(LookupElementBuilder.create(it)) }
                }
            })
    }
}