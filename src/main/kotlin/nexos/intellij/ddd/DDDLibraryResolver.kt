package nexos.intellij.ddd

import com.intellij.codeInsight.daemon.quickFix.ExternalLibraryResolver
import com.intellij.openapi.module.Module
import com.intellij.util.ThreeState
import com.intellij.util.ThreeState.NO
import java.util.*

/**
 * Text autocompletion on annotation names.
 *
 * providing full qualified names and group id and artifact id to import missing library from maven repository.
 *
 * Example: Type "@service" Auto completion to "@Service", adding import statement and asking to import library.
 */
class DDDLibraryResolver : ExternalLibraryResolver() {
    companion object {
        //FIXME create fqName.typename => Info
        val names by lazy { all.associateBy { it.concept.name.lowercase(Locale.getDefault()) } }
    }

    override fun resolveClass(shortClassName: String, isAnnotation: ThreeState, contextModule: Module): ExternalClassResolveResult? {
        if (isAnnotation != NO) {
            return names[shortClassName.lowercase(Locale.getDefault())]?.let { result(it) }
        }
        return null
    }

    private fun result(info: Info) = ExternalClassResolveResult(info.fqName, info.library.externalLibrary)
}
