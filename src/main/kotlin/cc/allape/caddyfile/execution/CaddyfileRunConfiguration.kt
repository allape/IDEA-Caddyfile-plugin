package cc.allape.caddyfile.execution

import cc.allape.caddyfile.CaddyfileIcons
import cc.allape.caddyfile.Utils
import com.intellij.execution.ExecutionException
import com.intellij.execution.Executor
import com.intellij.execution.configuration.EnvironmentVariablesTextFieldWithBrowseButton
import com.intellij.execution.configurations.*
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.components.BaseState
import com.intellij.openapi.components.StoredProperty
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.NotNullLazyValue
import com.intellij.util.ui.FormBuilder
import javax.swing.Icon
import javax.swing.JComponent
import javax.swing.JPanel


internal class CaddyfileRunConfigurationType : ConfigurationTypeBase(
    ID, "Caddyfile", "Caddyfile run configuration type", NotNullLazyValue.createValue { CaddyfileIcons.FILE }) {
    init {
        addFactory(CaddyfileRunConfigurationFactory(this))
    }

    override fun getIcon(): Icon {
        return CaddyfileIcons.FILE
    }

    companion object {
        const val ID: String = "CaddyfileRunConfiguration"
    }
}

open class CaddyfileRunConfigurationFactory(type: ConfigurationType?) : ConfigurationFactory(type!!) {
    override fun getId(): String {
        return CaddyfileRunConfigurationType.ID
    }

    override fun createTemplateConfiguration(
        project: Project,
    ): RunConfiguration {
        return CaddyfileRunConfiguration(project, this, "Caddyfile")
    }

    override fun getOptionsClass(): Class<out BaseState>? {
        return CaddyfileRunConfigurationOptions::class.java
    }
}

class CaddyfileRunConfigurationOptions : RunConfigurationOptions() {
    private val myCaddyExec: StoredProperty<String?> = string("caddy").provideDelegate(
        this, "CaddyExecutable"
    )

    private val myCaddyfile: StoredProperty<String?> = string("Caddyfile").provideDelegate(
        this, "Caddyfile"
    )

    private val myWorkingDir: StoredProperty<String?> = string(Utils.getWorkingDir()).provideDelegate(
        this, "WorkingDirectory"
    )

    private val myEnvFiles: StoredProperty<String?> = string("").provideDelegate(
        this, "EnvFiles"
    )

    private val myEnvironmentVariables: StoredProperty<MutableMap<String, String>> =
        map<String, String>().provideDelegate(
            this, "EnvironmentVariables"
        )

    var caddyExec: String?
        get() = myCaddyExec.getValue(this)
        set(caddyExec) {
            myCaddyExec.setValue(this, caddyExec)
        }

    var caddyfile: String?
        get() = myCaddyfile.getValue(this)
        set(caddyfile) {
            myCaddyfile.setValue(this, caddyfile)
        }

    var workingDir: String?
        get() = myWorkingDir.getValue(this)
        set(workingDir) {
            myWorkingDir.setValue(this, workingDir)
        }

    var envFiles: String?
        get() = myEnvFiles.getValue(this)
        set(envFiles) {
            myEnvFiles.setValue(this, envFiles)
        }

    var environmentVariables: MutableMap<String, String>
        get() = myEnvironmentVariables.getValue(this)
        set(environmentVariables) {
            myEnvironmentVariables.setValue(this, environmentVariables)
        }
}

open class CaddyfileRunConfiguration(
    project: Project,
    factory: ConfigurationFactory?,
    name: String?,
) : RunConfigurationBase<CaddyfileRunConfigurationOptions>(project, factory, name) {

    override fun getOptions(): CaddyfileRunConfigurationOptions {
        return super.getOptions() as CaddyfileRunConfigurationOptions
    }

    var caddyExec: String
        get() = options.caddyExec ?: ""
        set(caddyExec) {
            options.caddyExec = caddyExec
        }

    var caddyfile: String
        get() = options.caddyfile ?: ""
        set(caddyfile) {
            options.caddyfile = caddyfile
        }

    var workingDir: String
        get() = options.workingDir ?: ""
        set(workingDir) {
            options.workingDir = workingDir
        }

    var envFiles: String
        get() = options.envFiles ?: ""
        set(envFiles) {
            options.envFiles = envFiles
        }

    var environmentVariables: MutableMap<String, String>
        get() = options.environmentVariables
        set(environmentVariables) {
            options.environmentVariables = environmentVariables
        }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration?> {
        return CaddyfileSettingsEditor()
    }

    override fun getState(
        executor: Executor,
        environment: ExecutionEnvironment,
    ): RunProfileState {
        return object : CommandLineState(environment) {
            @Throws(ExecutionException::class)
            override fun startProcess(): ProcessHandler {
                val baseCommand = mutableListOf(
                    options.caddyExec, "run", "--environ", "--adapter", "caddyfile", "--config", options.caddyfile
                )
                val envFiles = options.envFiles?.split(",") ?: emptyList()
                for (envFile in envFiles) {
                    baseCommand.add("--envfile")
                    baseCommand.add(envFile)
                }
                val commandLine = GeneralCommandLine(baseCommand)
                commandLine.environment.putAll(options.environmentVariables)
                commandLine.setWorkDirectory(workingDir)
                val processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine)
                ProcessTerminatedListener.attach(processHandler)
                return processHandler
            }
        }
    }
}

class CaddyfileSettingsEditor : SettingsEditor<CaddyfileRunConfiguration>() {
    private val myPanel: JPanel

    private val caddyExec = TextFieldWithBrowseButton()
    private val caddyfile = TextFieldWithBrowseButton()
    private val workingDir = TextFieldWithBrowseButton()
    private val envFiles = TextFieldWithBrowseButton()
    private val environmentVariables = EnvironmentVariablesTextFieldWithBrowseButton()

    init {
        caddyExec.addBrowseFolderListener(
            "Select Caddy Executable", null, null, FileChooserDescriptorFactory.createSingleFileDescriptor()
        )
        caddyfile.addBrowseFolderListener(
            "Select Caddyfile", null, null, FileChooserDescriptorFactory.createSingleFileDescriptor()
        )
        workingDir.addBrowseFolderListener(
            "Select Working Directory", null, null, FileChooserDescriptorFactory.createSingleFolderDescriptor()
        )
        envFiles.addBrowseFolderListener(
            "Select Env Files", null, null, FileChooserDescriptorFactory.createMultipleFilesNoJarsDescriptor()
        )
        myPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent("Caddy executable", caddyExec)
            .addLabeledComponent("Caddyfile", caddyfile)
            .addLabeledComponent("Working directory", workingDir)
            .addLabeledComponent("Env files", envFiles)
            .addLabeledComponent("Environment variables", environmentVariables).panel
    }

    override fun resetEditorFrom(caddyfileRunConfiguration: CaddyfileRunConfiguration) {
        caddyExec.text = caddyfileRunConfiguration.caddyExec
        caddyfile.text = caddyfileRunConfiguration.caddyfile
        workingDir.text = caddyfileRunConfiguration.workingDir
        envFiles.text = caddyfileRunConfiguration.envFiles
        environmentVariables.envs = caddyfileRunConfiguration.environmentVariables
    }

    override fun applyEditorTo(caddyfileRunConfiguration: CaddyfileRunConfiguration) {
        caddyfileRunConfiguration.caddyExec = caddyExec.text
        caddyfileRunConfiguration.caddyfile = caddyfile.text
        caddyfileRunConfiguration.workingDir = workingDir.text
        caddyfileRunConfiguration.envFiles = envFiles.text
        caddyfileRunConfiguration.environmentVariables = environmentVariables.envs
    }

    override fun createEditor(): JComponent {
        return myPanel
    }
}