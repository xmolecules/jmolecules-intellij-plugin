package nexos.intellij.jddd

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.psi.PsiManager
import com.intellij.psi.search.scope.packageSet.NamedScopeManager

class Startup : StartupActivity {
    override fun runActivity(project: Project) {
        val scopeManager = NamedScopeManager.getInstance(project)
        val psiManager = PsiManager.getInstance(project)
        val scopesToAdd = annotations.associateBy { it.displayName }.toMutableMap()
        scopeManager.scopes.forEach {
            val name = it.name
            if (scopesToAdd.containsKey(name)) {
                scopesToAdd.remove(name)
            }
        }
        scopesToAdd.forEach {
            scopeManager.addScope(createNamedScope(psiManager, it.value))
        }
    }
}