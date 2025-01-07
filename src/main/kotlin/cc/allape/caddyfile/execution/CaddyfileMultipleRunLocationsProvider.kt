package cc.allape.caddyfile.execution

import cc.allape.caddyfile.CaddyfileFile
import com.intellij.execution.Location
import com.intellij.execution.actions.MultipleRunLocationsProvider

class CaddyfileMultipleRunLocationsProvider : MultipleRunLocationsProvider() {
    override fun getAlternativeLocations(originalLocation: Location<*>): List<Location<*>> {
        if (originalLocation.psiElement !is CaddyfileFile) {
            return emptyList()
        }
        return listOf(originalLocation)
    }

    override fun getLocationDisplayName(locationCreatedFrom: Location<*>, originalLocation: Location<*>): String? {
        if (originalLocation.psiElement !is CaddyfileFile) {
            return null
        }
        return "Caddyfile"
    }
}