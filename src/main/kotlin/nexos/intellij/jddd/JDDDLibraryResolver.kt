package nexos.intellij.jddd

import com.intellij.codeInsight.daemon.quickFix.ExternalLibraryResolver
import com.intellij.openapi.module.Module
import com.intellij.util.ThreeState
import com.intellij.util.ThreeState.NO

/**
 * Text autocompletion on annotation names.
 *
 * providing full qualified names and group id and artifact id to import missing library from maven repository.
 *
 * Example: Type "@service" Auto completion to "@Service", adding import statement and asking to import library.
 */
class JDDDLibraryResolver : ExternalLibraryResolver() {
    companion object {
        val names by lazy { Info.all.associateBy { it.displayName.toLowerCase() } }
    }

    override fun resolveClass(shortClassName: String, isAnnotation: ThreeState, contextModule: Module): ExternalClassResolveResult? {
        if (isAnnotation != NO) {
            return names[shortClassName.toLowerCase()]?.let { result(it) }
        }
        return null
    }

    private fun result(info: Info) = ExternalClassResolveResult(info.fqName, info.lib)
}