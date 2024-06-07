package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileProperty
import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.spellchecker.inspections.CommentSplitter
import com.intellij.spellchecker.inspections.IdentifierSplitter
import com.intellij.spellchecker.inspections.PlainTextSplitter
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy
import com.intellij.spellchecker.tokenizer.TokenConsumer
import com.intellij.spellchecker.tokenizer.Tokenizer
import org.jetbrains.annotations.NotNull


internal class CaddyfileSpellcheckingStrategy : SpellcheckingStrategy() {
    @NotNull
    override fun getTokenizer(element: PsiElement): Tokenizer<*> {
        if (element is PsiComment) {
            return CaddyfileCommentTokenizer()
        }

        if (element is CaddyfileProperty) {
            return CaddyfilePropertyTokenizer()
        }

        return EMPTY_TOKENIZER
    }

}

private class CaddyfileCommentTokenizer : Tokenizer<PsiComment?>() {
    override fun tokenize(@NotNull element: PsiComment, @NotNull consumer: TokenConsumer) {
        var startIndex = 0
        for (c in element.textToCharArray()) {
            if (c == '#' || Character.isWhitespace(c)) {
                startIndex++
            } else {
                break
            }
        }
        consumer.consumeToken(
            element, element.text, false, 0,
            TextRange.create(startIndex, element.textLength),
            CommentSplitter.getInstance()
        )
    }
}

private class CaddyfilePropertyTokenizer : Tokenizer<CaddyfileProperty?>() {
    override fun tokenize(@NotNull element: CaddyfileProperty, @NotNull consumer: TokenConsumer) {
        val key: ASTNode? = element.node.findChildByType(CaddyfileTypes.HOSTNAME)
        if (key != null && key.textLength > 0) {
            val keyPsi: PsiElement = key.psi
            val text: String = key.text
            consumer.consumeToken(
                keyPsi, text, true, 0,
                TextRange.allOf(text), IdentifierSplitter.getInstance()
            )
        }

        val value: ASTNode? = element.node.findChildByType(CaddyfileTypes.FILEPATH)
        if (value != null && value.textLength > 0) {
            val valuePsi: PsiElement = value.psi
            val text = valuePsi.text
            consumer.consumeToken(
                valuePsi, text, false, 0,
                TextRange.allOf(text), PlainTextSplitter.getInstance()
            )
        }
    }
}
