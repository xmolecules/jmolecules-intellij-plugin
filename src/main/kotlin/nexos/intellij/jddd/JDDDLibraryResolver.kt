package nexos.intellij.jddd

import com.intellij.codeInsight.daemon.quickFix.ExternalLibraryResolver
import com.intellij.openapi.module.Module
import com.intellij.util.ThreeState

class JDDDLibraryResolver : ExternalLibraryResolver() {
    companion object {
        val names by lazy { Info.all.associateBy { it.displayName.toLowerCase() } }
    }

    override fun resolveClass(shortClassName: String, isAnnotation: ThreeState, contextModule: Module): ExternalClassResolveResult? {
        if (isAnnotation == ThreeState.YES) {
            return names[shortClassName.toLowerCase()]?.let { result(it) }
        }
        return null
    }

    private fun result(info: Info) = ExternalClassResolveResult(info.fqName, info.lib)
}