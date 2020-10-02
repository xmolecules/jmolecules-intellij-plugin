package nexos.intellij.jddd

import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.scope.packageSet.AbstractPackageSet
import com.intellij.psi.search.scope.packageSet.NamedScope
import com.intellij.psi.search.scope.packageSet.NamedScopesHolder

class JDDDPackageSet(private val info:Info):
        AbstractPackageSet(info.displayName, 1) {
    override fun contains(file: VirtualFile, holder: NamedScopesHolder?): Boolean {
        val project = holder?.project
        if(project != null && file.fileType == JavaFileType.INSTANCE) {
            val psi = PsiManager.getInstance(project).findFile(file)
            if (psi is PsiJavaFile) {
                return contains(findPackageAnnotations(psi)) || contains(findTopLevelClassAnnotations(psi))
            }
        }
        return false
    }

    override fun createCopy() = JDDDPackageSet(info)

    private fun contains(annos: List<String>) =  annos.contains(info.fqName)
}

fun createNamedScope(info: Info) = NamedScope(info.displayName, JDDDPackageSet(info))