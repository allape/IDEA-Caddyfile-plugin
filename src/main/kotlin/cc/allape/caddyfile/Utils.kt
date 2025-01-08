package cc.allape.caddyfile

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.findPsiFile
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

        fun reanalyze(filepath: String): Boolean {
            val app = ApplicationManager.getApplication()
            ProjectManager.getInstance().openProjects.forEach { project ->
                FileEditorManager.getInstance(project).openFiles.find { it.path == filepath }?.let {
                    app.runReadAction {
                        it.findPsiFile(project)?.let { psiFile ->
                            DaemonCodeAnalyzer.getInstance(project).restart(psiFile)
                        }
                    }
                    return true
                }
            }
            return false
        }
    }
}