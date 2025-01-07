package cc.allape.caddyfile.execution

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement

class CaddyfileRunConfigurationProducer : LazyRunConfigurationProducer<CaddyfileRunConfiguration>() {
    override fun setupConfigurationFromContext(
        configuration: CaddyfileRunConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>,
    ): Boolean {
        configuration.isFromContext = true
        configuration.name = "Run Caddyfile"
        configuration.caddyfile = sourceElement.get().containingFile.virtualFile.path
        configuration.workingDir = sourceElement.get().containingFile.virtualFile.parent.path
        return true
    }

    override fun isConfigurationFromContext(
        configuration: CaddyfileRunConfiguration,
        context: ConfigurationContext,
    ): Boolean {
        return configuration.isFromContext
    }

    override fun getConfigurationFactory(): ConfigurationFactory {
        return CaddyfileRunConfigurationFactory(CaddyfileRunConfigurationType())
    }
}