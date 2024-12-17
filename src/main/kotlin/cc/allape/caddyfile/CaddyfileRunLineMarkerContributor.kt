package cc.allape.caddyfile

import cc.allape.caddyfile.execution.NewRunConfigurationAction
import cc.allape.caddyfile.execution.RunConfigurationAction
import cc.allape.caddyfile.execution.findCaddyfileRunConfiguration
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiElement

class CaddyfileRunLineMarkerContributor : DumbAware, RunLineMarkerContributor() {
    override fun getInfo(element: PsiElement): Info? {
        if (element !is CaddyfileFile) {
            return null
        }

        val actions: MutableList<AnAction> = mutableListOf()

        val config = findCaddyfileRunConfiguration(element.project, element)

        if (config == null) {
            actions.add(NewRunConfigurationAction().let {
                it.caddyfile = element
                it
            })
        } else {
            actions.add(RunConfigurationAction().let {
                it.config = config
                it
            })
        }

        return Info(AllIcons.Actions.Execute, actions.toTypedArray()) { "Run Caddyfile" }
    }
}