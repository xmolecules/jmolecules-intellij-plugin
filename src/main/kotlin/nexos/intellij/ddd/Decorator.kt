package nexos.intellij.ddd

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
                    decorateByAnnotations(cached(psiFile), data)
                }
            }
        }
    }

    override fun decorate(node: PackageDependenciesNode?, cellRenderer: ColoredTreeCellRenderer?) {}
}

private fun decorateByAnnotations(anno: List<Info>, data: PresentationData) {
    if (anno.isNotEmpty()) {
        data.locationString = anno.joinToString(separator = " ", ) { '\u00AB' + it.concept.name + '\u00BB' }
    }
}
