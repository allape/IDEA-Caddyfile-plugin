package cc.allape.caddyfile

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader.getIcon
import javax.swing.Icon

class CaddyfileLanguage : Language("Caddyfile") {
    companion object {
        val INSTANCE: CaddyfileLanguage by lazy { CaddyfileLanguage() }
    }
}

class CaddyfileIcons {
    companion object {
        val FILE: Icon by lazy { getIcon("/META-INF/pluginIcon.svg", CaddyfileIcons::class.java) }
    }
}

@Suppress("CompanionObjectInExtension")
class CaddyfileFileType : LanguageFileType(CaddyfileLanguage.INSTANCE) {
    override fun getName(): String {
        return "Caddyfile"
    }

    override fun getDescription(): String {
        return "Caddyfile"
    }

    override fun getDefaultExtension(): String {
        return "Caddyfile"
    }

    override fun getIcon(): Icon {
        return CaddyfileIcons.FILE
    }

    companion object {
        @JvmField
        val INSTANCE: CaddyfileFileType = CaddyfileFileType()
    }
}
