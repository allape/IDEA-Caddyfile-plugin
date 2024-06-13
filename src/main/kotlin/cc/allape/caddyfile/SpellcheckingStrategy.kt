package cc.allape.caddyfile

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.spellchecker.inspections.CommentSplitter
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
