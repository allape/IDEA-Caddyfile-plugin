package cc.allape.caddyfile.execution

import cc.allape.caddyfile.CaddyfileIcons
import cc.allape.caddyfile.Utils
import com.intellij.execution.ExecutionException
import com.intellij.execution.Executor
import com.intellij.execution.configuration.EnvironmentVariablesTextFieldWithBrowseButton
import com.intellij.execution.configurations.*
import com.intellij.execution.process.*
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.NotNullLazyValue
import com.intellij.util.ui.FormBuilder
import org.jdom.Element
import javax.swing.Icon
import javax.swing.JComponent
import javax.swing.JPanel


val ProcessDataKey = Key<String>("caddyfile.run-config")

val SubCommands = mapOf(
    "run" to "run",
    "adapt" to "adapt"
)

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

//    override fun getOptionsClass(): Class<out BaseState>? {
//        return CaddyfileRunConfigurationOptions::class.java
//    }
}

// Not working well, always failed to save the configuration
//class CaddyfileRunConfigurationOptions : RunConfigurationOptions() {
//    private val myCaddyExec: StoredProperty<String?> = string("caddy").provideDelegate(
//        this, "CaddyExecutable"
//    )
//
//    private val myCaddyfile: StoredProperty<String?> = string("Caddyfile").provideDelegate(
//        this, "Caddyfile"
//    )
//
//    private val myWorkingDir: StoredProperty<String?> = string(Utils.getWorkingDir()).provideDelegate(
//        this, "WorkingDirectory"
//    )
//
//    private val myEnvFiles: StoredProperty<String?> = string("").provideDelegate(
//        this, "EnvFiles"
//    )
//
//    private val myEnvironmentVariables: StoredProperty<MutableMap<String, String>> =
//        map<String, String>().provideDelegate(
//            this, "EnvironmentVariables"
//        )
//
//    var caddyExec: String?
//        get() = myCaddyExec.getValue(this)
//        set(caddyExec) {
//            myCaddyExec.setValue(this, caddyExec)
//        }
//
//    var caddyfile: String?
//        get() = myCaddyfile.getValue(this)
//        set(caddyfile) {
//            myCaddyfile.setValue(this, caddyfile)
//        }
//
//    var workingDir: String?
//        get() = myWorkingDir.getValue(this)
//        set(workingDir) {
//            myWorkingDir.setValue(this, workingDir)
//        }
//
//    var envFiles: String?
//        get() = myEnvFiles.getValue(this)
//        set(envFiles) {
//            myEnvFiles.setValue(this, envFiles)
//        }
//
//    var environmentVariables: MutableMap<String, String>
//        get() = myEnvironmentVariables.getValue(this)
//        set(environmentVariables) {
//            myEnvironmentVariables.setValue(this, environmentVariables)
//        }
//}

open class CaddyfileRunConfiguration(
    project: Project,
    factory: ConfigurationFactory?,
    name: String?,
) : RunConfigurationBase<Element>(project, factory, name) {

    companion object {
        const val CADDY_EXEC: String = "caddy-exec"
        const val SUB_COMMAND: String = "sub-command"
        const val CADDYFILE: String = "caddyfile"
        const val WORKING_DIR: String = "working-dir"
        const val ENV_FILES: String = "env-files"
        const val ENV_VARS: String = "env-vars"
    }

    var caddyExec: String = "caddy"

    var subCommand: String = SubCommands.values.first()

    var caddyfile: String = "Caddyfile"

    var workingDir: String = Utils.getWorkingDir()

    var envFiles: String = ""

    var environmentVariables: MutableMap<String, String> = mutableMapOf()

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
                    caddyExec, subCommand, "--adapter", "caddyfile", "--config", caddyfile
                )

                when (subCommand) {
                    SubCommands["run"] -> {
                        baseCommand.add("--environ")

                        val envFiles = envFiles.split(",")
                        for (envFile in envFiles) {
                            if (envFile.isEmpty()) continue
                            baseCommand.add("--envfile")
                            baseCommand.add(envFile)
                        }
                    }

                    SubCommands["adapt"] -> {
                        baseCommand.add("--validate")
                    }
                }

                val commandLine = GeneralCommandLine(baseCommand)
                commandLine.environment.putAll(environmentVariables)
                commandLine.setWorkDirectory(workingDir)

                val processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine)
                ProcessTerminatedListener.attach(processHandler)
                processHandler.putUserData(ProcessDataKey, caddyfile)
                processHandler.addProcessListener(object : ProcessAdapter() {
                    override fun processTerminated(event: ProcessEvent) {
                        Utils.reanalyze(caddyfile)
                    }

                    override fun startNotified(event: ProcessEvent) {
                        Utils.reanalyze(caddyfile)
                    }
                })

                return processHandler
            }
        }
    }

    override fun checkConfiguration() {
        if (caddyExec.isEmpty()) {
            throw RuntimeConfigurationError("Caddy executable is not set")
        }
        if (caddyfile.isEmpty()) {
            throw RuntimeConfigurationError("Caddyfile is not set")
        }

//        if (!Utils.isExecutable(caddyExec)) {
//            throw RuntimeConfigurationError("Caddy executable is not executable")
//        }
        if (!Utils.exists(caddyfile)) {
            throw RuntimeConfigurationError("Caddyfile does not exist")
        }

        envFiles.split(",").forEach {
            if (!Utils.exists(it)) {
                throw RuntimeConfigurationError("Env file does not exist")
            }
        }
    }

    override fun readExternal(element: Element) {
        this.caddyExec = element.getAttributeValue(CADDY_EXEC) ?: "caddy"
        this.subCommand = element.getAttributeValue(SUB_COMMAND) ?: "run"
        this.caddyfile = element.getAttributeValue(CADDYFILE) ?: "Caddyfile"
        this.workingDir = element.getAttributeValue(WORKING_DIR) ?: Utils.getWorkingDir()
        this.envFiles = element.getAttributeValue(ENV_FILES) ?: ""

        element.getChild(ENV_VARS)?.children?.forEach {
            this.environmentVariables[it.name] = it.text
        }

        super.readExternal(element)
    }

    override fun writeExternal(element: Element) {
        element.setAttribute(CADDY_EXEC, caddyExec)
        element.setAttribute(SUB_COMMAND, subCommand)
        element.setAttribute(CADDYFILE, caddyfile)
        element.setAttribute(WORKING_DIR, workingDir)
        element.setAttribute(ENV_FILES, envFiles)

        element.getOrCreateChild(ENV_VARS).let {
            for ((key, value) in environmentVariables) {
                it.getOrCreateChild(key).setText(value)
            }
        }

        super.writeExternal(element)
    }
}

class CaddyfileSettingsEditor : SettingsEditor<CaddyfileRunConfiguration>() {
    private val myPanel: JPanel

    private val caddyExec = TextFieldWithBrowseButton()
    private val subCommand = ComboBox(SubCommands.values.toTypedArray())
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
            .addLabeledComponent("Sub command", subCommand)
            .addLabeledComponent("Caddyfile", caddyfile)
            .addLabeledComponent("Working directory", workingDir)
            .addLabeledComponent("Env files", envFiles)
            .addLabeledComponent("Environment variables", environmentVariables).panel
    }

    override fun resetEditorFrom(caddyfileRunConfiguration: CaddyfileRunConfiguration) {
        caddyExec.text = caddyfileRunConfiguration.caddyExec
        subCommand.selectedItem = caddyfileRunConfiguration.subCommand
        caddyfile.text = caddyfileRunConfiguration.caddyfile
        workingDir.text = caddyfileRunConfiguration.workingDir
        envFiles.text = caddyfileRunConfiguration.envFiles
        environmentVariables.envs = caddyfileRunConfiguration.environmentVariables
    }

    override fun applyEditorTo(caddyfileRunConfiguration: CaddyfileRunConfiguration) {
        caddyfileRunConfiguration.caddyExec = caddyExec.text
        caddyfileRunConfiguration.subCommand = subCommand.selectedItem as String
        caddyfileRunConfiguration.caddyfile = caddyfile.text
        caddyfileRunConfiguration.workingDir = workingDir.text
        caddyfileRunConfiguration.envFiles = envFiles.text
        caddyfileRunConfiguration.environmentVariables = environmentVariables.envs
    }

    override fun createEditor(): JComponent {
        return myPanel
    }
}