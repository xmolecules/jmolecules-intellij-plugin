package nexos.intellij.ddd

import com.intellij.icons.AllIcons
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.search.scope.packageSet.AbstractPackageSet
import com.intellij.psi.search.scope.packageSet.CustomScopesProvider
import com.intellij.psi.search.scope.packageSet.NamedScope
import com.intellij.psi.search.scope.packageSet.NamedScopesHolder

private class DDDPackageSet(private val concept: Concept): AbstractPackageSet(concept.name, 1) {

    override fun contains(file: VirtualFile, project: Project, holder: NamedScopesHolder?): Boolean {
        if (holder != null && file.fileType == JavaFileType.INSTANCE) {
            val psi = getPsiFile(file, holder.project)
            if (psi is PsiJavaFile) {
                return cached(psi).any {it.concept == concept}
            }
        }
        return false
    }

    override fun createCopy() = this

    override fun getText() = concept.name

    override fun equals(other: Any?): Boolean {
        if(other is DDDPackageSet) {
            return concept == other.concept
        }
        return false
    }

    override fun hashCode(): Int = concept.hashCode()

    @Deprecated("see com.intellij.psi.search.scope.packageSet.PackageSetBase", ReplaceWith("false"))
    override fun contains(file: VirtualFile, holder: NamedScopesHolder?) = false
}

private class DDDNamedScope(private val concept: Concept):
        NamedScope(concept.name, AllIcons.Ide.LocalScope, DDDPackageSet(concept)) {
    override fun getDefaultColorName() = concept.defaultColorName

    override fun createCopy() = this

    override fun equals(other: Any?): Boolean {
        if (other is DDDNamedScope) {
            return concept == other.concept
        }
        return false
    }

    override fun hashCode(): Int = concept.hashCode()
}

class Scopes : CustomScopesProvider {
    companion object {
        private val scopes: MutableList<NamedScope> by lazy { all.groupBy {it.concept}.map { DDDNamedScope(it.key) }.toMutableList() }
    }

    override fun getCustomScopes() = scopes
}