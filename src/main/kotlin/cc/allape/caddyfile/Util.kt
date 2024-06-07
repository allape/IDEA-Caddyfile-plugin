//package cc.allape.caddyfile
//
//import cc.allape.caddyfile.language.psi.CaddyfileProperty
//import com.google.common.collect.Lists
//import com.intellij.openapi.project.Project
//import com.intellij.openapi.util.text.StringUtil
//import com.intellij.psi.PsiComment
//import com.intellij.psi.PsiElement
//import com.intellij.psi.PsiManager
//import com.intellij.psi.PsiWhiteSpace
//import com.intellij.psi.search.FileTypeIndex
//import com.intellij.psi.search.GlobalSearchScope
//import com.intellij.psi.util.PsiTreeUtil
//import org.jetbrains.annotations.NotNull
//import java.util.*
//import kotlin.collections.ArrayList
//
//
//object CaddyfileUtil {
//    /**
//     * Searches the entire project for Caddyfile language files with instances of the Caddyfile property with the given key.
//     *
//     * @param project current project
//     * @param key     to check
//     * @return matching properties
//     */
//    fun findProperties(project: Project, key: String): List<CaddyfileProperty> {
//        val result: MutableList<CaddyfileProperty> = ArrayList()
//        val virtualFiles =
//            FileTypeIndex.getFiles(CaddyfileFileType.INSTANCE, GlobalSearchScope.allScope(project))
//        for (virtualFile in virtualFiles) {
//            val file = PsiManager.getInstance(project).findFile(virtualFile!!) as CaddyfileFile?
//            if (file != null) {
//                val properties: Array<CaddyfileProperty>? = PsiTreeUtil.getChildrenOfType(
//                    file,
//                    CaddyfileProperty::class.java
//                )
//                if (properties != null) {
//                    for (property in properties) {
//                        if (key == property.getKey()) {
//                            result.add(property)
//                        }
//                    }
//                }
//            }
//        }
//        return result
//    }
//
//    fun findProperties(project: Project): List<CaddyfileProperty> {
//        val result = ArrayList<CaddyfileProperty>()
//        val virtualFiles =
//            FileTypeIndex.getFiles(CaddyfileFileType.INSTANCE, GlobalSearchScope.allScope(project))
//        for (virtualFile in virtualFiles) {
//            val file: CaddyfileFile? = PsiManager.getInstance(project).findFile(virtualFile!!) as CaddyfileFile?
//            if (file != null) {
//                val properties: Array<CaddyfileProperty>? = PsiTreeUtil.getChildrenOfType(
//                    file,
//                    CaddyfileProperty::class.java
//                )
//                if (properties != null) {
//                    result.addAll(properties)
//                }
//            }
//        }
//        return result
//    }
//
//    /**
//     * Attempts to collect any comment elements above the Caddyfile key/value pair.
//     */
//    @NotNull
//    fun findDocumentationComment(property: CaddyfileProperty): String {
//        val result: MutableList<String> = LinkedList()
//        var element: PsiElement = property.prevSibling
//        while (element is PsiComment || element is PsiWhiteSpace) {
//            if (element is PsiComment) {
//                val commentText = element.getText().replaceFirst("[!# ]+".toRegex(), "")
//                result.add(commentText)
//            }
//            element = element.prevSibling
//        }
//        return StringUtil.join(Lists.reverse(result), "\n ")
//    }
//}