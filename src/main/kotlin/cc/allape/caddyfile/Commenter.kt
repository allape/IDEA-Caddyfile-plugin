package cc.allape.caddyfile

import com.intellij.lang.Commenter
import org.jetbrains.annotations.Nullable


internal class CaddyfileCommenter : Commenter {
    override fun getLineCommentPrefix(): String {
        return "#"
    }

    override fun getBlockCommentPrefix(): String {
        return ""
    }

    @Nullable
    override fun getBlockCommentSuffix(): String? {
        return null
    }

    @Nullable
    override fun getCommentedBlockCommentPrefix(): String? {
        return null
    }

    @Nullable
    override fun getCommentedBlockCommentSuffix(): String? {
        return null
    }
}