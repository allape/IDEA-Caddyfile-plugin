package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import org.jetbrains.annotations.NotNull


internal class CaddyfileCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(CaddyfileTypes.COMMENT),
            object : CompletionProvider<CompletionParameters?>() {
                override fun addCompletions(
                    @NotNull parameters: CompletionParameters,
                    @NotNull context: ProcessingContext,
                    @NotNull resultSet: CompletionResultSet
                ) {
                    resultSet.addElement(LookupElementBuilder.create("http://"))
                    resultSet.addElement(LookupElementBuilder.create("https://"))
                }
            }
        )
    }
}