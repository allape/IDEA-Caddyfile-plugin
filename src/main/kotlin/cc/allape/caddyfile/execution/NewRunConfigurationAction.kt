package cc.allape.caddyfile.execution

import cc.allape.caddyfile.CaddyfileFile
import com.intellij.execution.ProgramRunnerUtil
import com.intellij.execution.RunManager
import com.intellij.execution.RunnerAndConfigurationSettings
import com.intellij.execution.actions.EditRunConfigurationsAction
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project


class NewRunConfigurationAction : AnAction() {
    private var myCaddyfile: CaddyfileFile? = null

    var caddyfile: CaddyfileFile?
        get() = myCaddyfile
        set(value) {
            myCaddyfile = value
        }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val runManager = RunManager.getInstance(project)

        val found: RunnerAndConfigurationSettings? =
            if (myCaddyfile == null) null else findCaddyfileRunConfiguration(project, myCaddyfile)

        if (found == null) {
            val config = runManager.createConfiguration("Run Caddyfile", CaddyfileRunConfigurationType::class.java)
            val cc = config.configuration as CaddyfileRunConfiguration
            cc.workingDir = project.basePath ?: ""
            cc.caddyfile = myCaddyfile?.virtualFile?.path ?: ""
            runManager.addConfiguration(config)
            runManager.selectedConfiguration = config
            ApplicationManager.getApplication().invokeLater {
                EditRunConfigurationsAction().actionPerformed(e)
            }
        } else {
            runManager.selectedConfiguration = found
            ApplicationManager.getApplication().invokeLater {
                RunConfigurationAction().let {
                    it.config = found
                    it
                }.actionPerformed(e)
            }
        }
    }
}

class RunConfigurationAction : AnAction() {
    private var myConfig: RunnerAndConfigurationSettings? = null

    var config: RunnerAndConfigurationSettings?
        get() = myConfig
        set(value) {
            myConfig = value
        }

    override fun actionPerformed(e: AnActionEvent) {
        val config = myConfig ?: return
        val project = e.project ?: return
        val runManager = RunManager.getInstance(project)

        runManager.selectedConfiguration = config
        val executor = DefaultRunExecutor.getRunExecutorInstance()
        ProgramRunnerUtil.executeConfiguration(config, executor)
    }
}

fun findCaddyfileRunConfiguration(project: Project, caddyfile: CaddyfileFile?): RunnerAndConfigurationSettings? {
    if (caddyfile == null) {
        return null
    }
    val runManager = RunManager.getInstance(project)
    return runManager.getConfigurationSettingsList(CaddyfileRunConfigurationType::class.java)
        .find {
            if (it.configuration is CaddyfileRunConfiguration) {
                val c = it.configuration as CaddyfileRunConfiguration
                return@find c.caddyfile == caddyfile.virtualFile.path
            }
            return@find false
        }
}
