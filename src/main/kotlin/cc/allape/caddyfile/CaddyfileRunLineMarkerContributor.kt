package cc.allape.caddyfile

import cc.allape.caddyfile.execution.NewRunConfigurationAction
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

        actions.add(NewRunConfigurationAction().let {
            it.caddyfile = element
            it
        })

//        Auto changing icon based on running status is too complex with many listeners, therefore I give up
//        var icon = AllIcons.Actions.Execute
//        val config = findCaddyfileRunConfiguration(element)
//        if (config != null) {
//            getRunningProcess(element.project, config.configuration as CaddyfileRunConfiguration)?.let {
//                icon = AllIcons.Actions.Restart
//            }
//        }

        return Info(AllIcons.Actions.Execute, actions.toTypedArray()) { "Run Caddyfile" }
    }
}