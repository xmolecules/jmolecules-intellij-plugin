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
            if (virtualFile != null && virtualFile.fileType == JavaFileType.INSTANCE) {
                val project = node.project
                if (project != null) {
                    val psiFile = PsiManager.getInstance(project).findFile(virtualFile)
                    if (psiFile is PsiJavaFile) {
                        decorateByAnnotations(findTopLevelClassAnnotations(psiFile), data)
                    }
                }
            }
        }
    }

    private fun findTopLevelClassAnnotations(psiFile: PsiJavaFile): List<String>
        = psiFile.classes.map { clazz ->
            clazz.annotations.mapNotNull { annotation ->
                annotation.nameReferenceElement?.canonicalText
            }
        }.flatten()

    private fun decorateByAnnotations(annotations: List<String>, data: PresentationData) {
       if (annotations.contains("org.jddd.core.annotation.AggregateRoot")) {
           data.locationString = "Aggregate Root"
       }
    }

    override fun decorate(node: PackageDependenciesNode?, cellRenderer: ColoredTreeCellRenderer?) {}
}