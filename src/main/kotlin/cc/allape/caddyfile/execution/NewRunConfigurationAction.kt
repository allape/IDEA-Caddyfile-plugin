package cc.allape.caddyfile.execution

import cc.allape.caddyfile.CaddyfileFile
import com.intellij.execution.*
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.impl.RunDialog
import com.intellij.execution.process.ProcessHandler
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ex.ActionUtil
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
            if (myCaddyfile == null) null else findCaddyfileRunConfiguration(myCaddyfile)

        val app = ApplicationManager.getApplication()

        if (found == null) {
            val config = runManager.createConfiguration(
                "Run Caddyfile #${runManager.allConfigurationsList.size}",
                CaddyfileRunConfigurationType::class.java
            )

            val cc = config.configuration as CaddyfileRunConfiguration
            cc.workingDir = project.basePath ?: ""
            cc.caddyfile = myCaddyfile?.virtualFile?.path ?: ""

            app.invokeLater {
//            runManager.addConfiguration(config)
//                app.invokeLater {
//                    EditRunConfigurationsAction().actionPerformed(e)
//                }
                val ok = RunDialog.editConfiguration(project, config, "Run Caddyfile")
                if (!ok) {
                    return@invokeLater
                }
                runConfigNow(e, config)
            }

            return
        }

        runConfigNow(e, found)
    }
}

fun runConfigNow(e: AnActionEvent, config: RunnerAndConfigurationSettings) {
    val project = e.project ?: return
    val runManager = RunManager.getInstance(project)
    runManager.selectedConfiguration = config
    ApplicationManager.getApplication().invokeLater {
        ActionUtil.invokeAction(RunConfigurationAction().let {
            it.config = config
            it
        }, e.dataContext, e.place, null, null)
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

        val app = ApplicationManager.getApplication()

        runManager.selectedConfiguration = config

        app.invokeLater {
            if (!restartRunningProcess(project, config)) {
                app.invokeLater {
                    ProgramRunnerUtil.executeConfiguration(config, getExecutor())
                }
            }
        }
    }
}

fun getExecutor(): Executor {
    return DefaultRunExecutor.getRunExecutorInstance()
}

fun getExecuteTarget(project: Project, config: RunConfiguration): ExecutionTarget {
    return ExecutionTargetManager.getInstance(project).findTarget(config)
}

fun findCaddyfileRunConfiguration(caddyfile: CaddyfileFile?): RunnerAndConfigurationSettings? {
    if (caddyfile == null) {
        return null
    }
    val runManager = RunManager.getInstance(caddyfile.project)
    return runManager.getConfigurationSettingsList(CaddyfileRunConfigurationType::class.java)
        .find {
            if (it.configuration is CaddyfileRunConfiguration) {
                val c = it.configuration as CaddyfileRunConfiguration
                return@find c.caddyfile == caddyfile.virtualFile.path
            }
            return@find false
        }
}

fun getRunningProcess(project: Project, config: CaddyfileRunConfiguration): ProcessHandler? {
    ExecutionManager.getInstance(project).getRunningProcesses().forEach {
        if (it.getUserData(ProcessDataKey) == config.caddyfile) {
            return it
        }
    }
    return null
}

fun restartRunningProcess(project: Project, config: RunnerAndConfigurationSettings): Boolean {
    getRunningProcess(project, config.configuration as CaddyfileRunConfiguration)?.let {
        ExecutionManager.getInstance(project)
            .restartRunProfile(project, getExecutor(), getExecuteTarget(project, config.configuration), config, it)
        return true
    }
    return false
}
