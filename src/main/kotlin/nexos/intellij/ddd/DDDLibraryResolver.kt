package nexos.intellij.ddd

import com.intellij.codeInsight.daemon.quickFix.ExternalLibraryResolver
import com.intellij.openapi.module.Module
import com.intellij.util.ThreeState
import com.intellij.util.ThreeState.NO
import java.util.*
import org.xmolecules.ide.intellij.All
import org.xmolecules.ide.intellij.ConceptImplementation
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
        val names by lazy { All.ALL.associateBy { it.concept.name.lowercase(Locale.getDefault()) } } //TODO Locale from IDEA environment
    }

    override fun resolveClass(shortClassName: String, isAnnotation: ThreeState, contextModule: Module): ExternalClassResolveResult? {
        return names[shortClassName.lowercase(Locale.getDefault())]?.let { result(it) } //TODO find Locale
    }

    private fun result(info: ConceptImplementation) = ExternalClassResolveResult(info.fqName, info.library.externalLibrary)
}
