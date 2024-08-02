package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext

internal class CaddyfileCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(CaddyfileTypes.DIRECTIVE),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    resultSet: CompletionResultSet
                ) {
                    for (directive in DIRECTIVES) {
                        resultSet.addElement(LookupElementBuilder.create(directive))
                    }
                }
            }
        )
    }
}