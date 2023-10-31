package nexos.intellij.ddd

import com.intellij.icons.AllIcons
import org.xmolecules.ide.intellij.Concept
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.search.scope.packageSet.AbstractPackageSet
import com.intellij.psi.search.scope.packageSet.CustomScopesProvider
import com.intellij.psi.search.scope.packageSet.NamedScope
import com.intellij.psi.search.scope.packageSet.NamedScopesHolder
import org.xmolecules.ide.intellij.All
import org.xmolecules.ide.intellij.Concepts

private class DDDPackageSet(private val concept: Concept): AbstractPackageSet(concept.name, 1) {

    override fun contains(file: VirtualFile, project: Project, holder: NamedScopesHolder?): Boolean {
        if (holder != null && file.fileType == JavaFileType.INSTANCE) {
            val psi = getPsiFile(file, holder.project)
            if (psi is PsiJavaFile) {
                return Concepts.getConcepts(psi).any {it == concept}
            }
        }
        return false
    }

    override fun createCopy() = this

    override fun getText(): String = concept.name

    override fun equals(other: Any?): Boolean {
        if(other is DDDPackageSet) {
            return concept == other.concept
        }
        return false
    }

    override fun hashCode() = concept.hashCode()
}

private class DDDNamedScope(private val concept: Concept):
        NamedScope(concept.name, AllIcons.Ide.LocalScope, DDDPackageSet(concept)) {
    override fun getDefaultColorName(): String = concept.defaultColorName

    override fun createCopy() = this

    override fun equals(other: Any?): Boolean {
        if (other is DDDNamedScope) {
            return concept == other.concept
        }
        return false
    }

    override fun hashCode(): Int = concept.hashCode()
}

class Scopes : CustomScopesProvider, DumbAware {
    companion object {
        private val scopes: MutableList<NamedScope> by lazy { All.ALL.groupBy { it.concept }.map { DDDNamedScope(it.key) }.toMutableList() }
    }

    override fun getCustomScopes() = scopes
}
