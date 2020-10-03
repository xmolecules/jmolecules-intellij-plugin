package nexos.intellij.jddd

import com.intellij.ide.highlighter.JavaFileType
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.packageDependencies.ui.PackageDependenciesNode
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import com.intellij.ui.ColoredTreeCellRenderer

class Decorator : ProjectViewNodeDecorator {
    override fun decorate(node: ProjectViewNode<*>?, data: PresentationData?) {
        if (data != null && node != null) {
            val virtualFile = node.virtualFile;
            val project = node.project
            if (project != null && virtualFile != null && virtualFile.fileType == JavaFileType.INSTANCE) {
                val psiFile = PsiManager.getInstance(project).findFile(virtualFile)
                if (psiFile is PsiJavaFile) {
                    decorateByAnnotations(findTopLevelClassAnnotations(psiFile), data)
                    decorateByAnnotations(findPackageAnnotations(psiFile), data)
                }
            }
        }
    }

    override fun decorate(node: PackageDependenciesNode?, cellRenderer: ColoredTreeCellRenderer?) {}
}

private fun decorateByAnnotations(anno: List<String>, data: PresentationData) {
    val filterAnnotations = filterAnnotations(anno)
    if (filterAnnotations.isNotEmpty()) {
        data.locationString = filterAnnotations.joinToString(separator = " ", ) { '\u00AB' + it.displayName + '\u00BB' }
    }
}

private fun filterAnnotations(anno: List<String>) = annotations.filter { anno.contains(it.fqName) }
