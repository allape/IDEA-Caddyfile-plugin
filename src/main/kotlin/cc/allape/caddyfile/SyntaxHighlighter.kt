package cc.allape.caddyfile

import cc.allape.caddyfile.language.psi.CaddyfileTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import javax.swing.Icon


class CaddyfileSyntaxHighlighter : SyntaxHighlighterBase() {
    @NotNull
    override fun getHighlightingLexer(): Lexer {
        return CaddyfileLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): @NotNull Array<TextAttributesKey?> {
        when (tokenType) {
            CaddyfileTypes.TLS,
            CaddyfileTypes.REDIR,
            CaddyfileTypes.RESPOND,
            CaddyfileTypes.REVERSE_PROXY,
            CaddyfileTypes.BINDING_HOSTNAME,
            CaddyfileTypes.HOSTNAME -> {
                return HOSTNAME_KEYS
            }
            CaddyfileTypes.STATUS_CODE,
            CaddyfileTypes.COLON,
            CaddyfileTypes.PORT,
            CaddyfileTypes.PORT_WITH_COLON -> {
                return PORT_KEYS
            }
            CaddyfileTypes.RIGHT_CURLY_BRACE,
            CaddyfileTypes.LEFT_CURLY_BRACE -> {
                return BRACKETS_KEYS
            }
            CaddyfileTypes.PROTOCOL -> {
                return PROTOCOL_KEYS
            }
            CaddyfileTypes.FILEPATH -> {
                return VALUE_KEYS
            }
            CaddyfileTypes.COMMENT -> {
                return COMMENT_KEYS
            }
            TokenType.BAD_CHARACTER -> {
                return BAD_CHAR_KEYS
            }
            else -> return EMPTY_KEYS
        }
    }

    companion object {
        val HOSTNAME: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_HOSTNAME", DefaultLanguageHighlighterColors.KEYWORD)
        val BRACKETS: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS)
        val PORT: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_PORT", DefaultLanguageHighlighterColors.NUMBER)
        val PROTOCOL: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_PROTOCOL", DefaultLanguageHighlighterColors.STRING)
        val VALUE: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_VALUE", DefaultLanguageHighlighterColors.STRING)
        val COMMENT: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER: TextAttributesKey =
            createTextAttributesKey("CADDYFILE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)


        private val BAD_CHAR_KEYS = arrayOf<TextAttributesKey?>(BAD_CHARACTER)
        private val BRACKETS_KEYS = arrayOf<TextAttributesKey?>(BRACKETS)
        private val HOSTNAME_KEYS = arrayOf<TextAttributesKey?>(HOSTNAME)
        private val PORT_KEYS = arrayOf<TextAttributesKey?>(PORT)
        private val PROTOCOL_KEYS = arrayOf<TextAttributesKey?>(PROTOCOL)
        private val VALUE_KEYS = arrayOf<TextAttributesKey?>(VALUE)
        private val COMMENT_KEYS = arrayOf<TextAttributesKey?>(COMMENT)
        private val EMPTY_KEYS = arrayOfNulls<TextAttributesKey>(0)
    }
}

internal class CaddyfileSyntaxHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        return CaddyfileSyntaxHighlighter()
    }
}

@Suppress("CompanionObjectInExtension")
internal class CaddyfileColorSettingsPage : ColorSettingsPage {
    override fun getIcon(): Icon {
        return CaddyfileIcons.FILE
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return CaddyfileSyntaxHighlighter()
    }

    override fun getDemoText(): String {
        return """
        :80 {
            redir https://{host}{uri}
        }
        
        # https
        localhost:443 {
            tls /etc/ssl/certs/localhost.crt /etc/ssl/private/localhost.key
            reverse_proxy http://localhost:80
        }

        :443 {
            respond 404
        }
        """.trimIndent()
    }

    @Nullable
    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? {
        return null
    }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return DESCRIPTORS
    }

    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "Caddyfile"
    }

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Brackets", CaddyfileSyntaxHighlighter.BRACKETS),
            AttributesDescriptor("Comment", CaddyfileSyntaxHighlighter.COMMENT),
            AttributesDescriptor("Protocol", CaddyfileSyntaxHighlighter.PROTOCOL),
            AttributesDescriptor("Hostname", CaddyfileSyntaxHighlighter.HOSTNAME),
            AttributesDescriptor("Port", CaddyfileSyntaxHighlighter.PORT),
            AttributesDescriptor("Value", CaddyfileSyntaxHighlighter.VALUE),
            AttributesDescriptor("Bad value", CaddyfileSyntaxHighlighter.BAD_CHARACTER)
        )
    }
}