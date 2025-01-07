package cc.allape.caddyfile

import cc.allape.caddyfile.execution.*
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

        var icon = AllIcons.Actions.Execute

        val actions: MutableList<AnAction> = mutableListOf()

        val config = findCaddyfileRunConfiguration(element)

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

            getRunningProcess(element.project, config.configuration as CaddyfileRunConfiguration)?.let {
                icon = AllIcons.Actions.Restart
            }
        }

        return Info(icon, actions.toTypedArray()) { "Run Caddyfile" }
    }
}