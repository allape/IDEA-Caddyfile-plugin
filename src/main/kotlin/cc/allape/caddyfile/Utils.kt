package cc.allape.caddyfile

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.VirtualFile
import java.nio.file.Files
import java.nio.file.Paths

class Utils {
    companion object {
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

        fun getWorkingDir(): String {
            return getCurrentProject()?.basePath ?: ""
        }

        fun exists(filename: String): Boolean {
            return Files.exists(Paths.get(filename))
        }
    }
}