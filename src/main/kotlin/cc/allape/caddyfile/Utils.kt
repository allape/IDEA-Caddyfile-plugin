package cc.allape.caddyfile

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiInvalidElementAccessException
import java.nio.file.Files
import java.nio.file.Paths

class Utils {
    companion object {
        @Suppress("MemberVisibilityCanBePrivate")
        fun getCurrentProject(): Project? {
            val projects = ProjectManager.getInstance().openProjects
            for (project in projects) {
                val fileEditorManager = FileEditorManager.getInstance(project)
                val selectedFiles: Array<VirtualFile> = fileEditorManager.selectedFiles
                if (selectedFiles.isNotEmpty()) {
                    return project
                }
            }
            return null
        }

        fun getCurrentOpenFile(document: Document): PsiFile? {
            return try {
                getCurrentProject()?.let {
                    /**
                     * @throws PsiInvalidElementAccessException
                     */
                    PsiDocumentManager.getInstance(it).getPsiFile(document)
                }
            } catch (e: PsiInvalidElementAccessException) {
                null
            }
        }

        fun getWorkingDir(): String {
            return getCurrentProject()?.basePath ?: ""
        }

        fun exists(filename: String): Boolean {
            return Files.exists(Paths.get(filename))
        }
    }
}