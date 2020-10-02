package nexos.intellij.jddd

import com.intellij.ide.highlighter.JavaFileType
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.packageDependencies.ui.PackageDependenciesNode
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import com.intellij.ui.ColoredTreeCellRenderer
import com.intellij.ui.SimpleTextAttributes.fromTextAttributes

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
                        decorateByAnnotations(findPackageAnnotations(psiFile), data)
                    }
                }
            }
        }
    }

    private fun findPackageAnnotations(psiFile: PsiJavaFile) =
            psiFile.packageStatement
                    ?.annotationList
                    ?.annotations
                    ?.mapNotNull { it.qualifiedName }
                    ?: listOf()

    private fun findTopLevelClassAnnotations(psiFile: PsiJavaFile): List<String>
        = psiFile.classes.map { clazz ->
            clazz.annotations.mapNotNull { annotation ->
                annotation.nameReferenceElement?.qualifiedName
            }
        }.flatten()

    private fun decorateByAnnotations(anno: List<String>, data: PresentationData) {
        val filterAnnotations = filterAnnotations(anno)
        if (filterAnnotations.isNotEmpty()) {
            data.locationString = filterAnnotations.joinToString(separator = " ") { it.displayName }
            filterAnnotations.forEach {
               // data.addText(it.displayName, fromTextAttributes( it.attributesDescriptor))
                data.setAttributesKey(it.textAttributesKey)
            }
        }
    }

    override fun decorate(node: PackageDependenciesNode?, cellRenderer: ColoredTreeCellRenderer?) {}
}

fun filterAnnotations(anno: List<String>) = annotations.filter { anno.contains(it.fqName) }