package nexos.intellij.jddd

import com.intellij.icons.AllIcons
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.search.scope.packageSet.AbstractPackageSet
import com.intellij.psi.search.scope.packageSet.CustomScopesProvider
import com.intellij.psi.search.scope.packageSet.NamedScope
import com.intellij.psi.search.scope.packageSet.NamedScopesHolder

class JDDDPackageSet(private val info: Info):
        AbstractPackageSet(info.displayName, 1) {

    override fun contains(file: VirtualFile, holder: NamedScopesHolder?): Boolean {
        if (holder != null && file.fileType == JavaFileType.INSTANCE) {
            val psi = getPsiFile(file, holder.project)
            if (psi is PsiJavaFile) {
                return findPackageAnnotations(psi).contains(info)
                   || findTopLevelClassAnnotations(psi).contains(info)
            }
        }
        return false
    }

    override fun createCopy() = JDDDPackageSet(info)

    override fun getText() = info.displayName
}

class JDDDNamedScope(private val info:Info): NamedScope(info.displayName, AllIcons.Ide.LocalScope, JDDDPackageSet(info)) {
    override fun getDefaultColorName() = info.defaultColorName
}

class Scopes : CustomScopesProvider {
    override fun getCustomScopes() = annotations.map { JDDDNamedScope(it) }.toMutableList()
}