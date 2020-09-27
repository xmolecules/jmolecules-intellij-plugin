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
                annotation.nameReferenceElement?.canonicalText
            }
        }.flatten()

    private fun decorateByAnnotations(annotations: List<String>, data: PresentationData) {
        when {
            annotations.contains("org.jddd.core.annotation.AggregateRoot") -> {
                data.locationString = "Aggregate Root"
            }
            annotations.contains("org.jddd.core.annotation.Entity") -> {
                data.locationString = "Entity"
            }
            annotations.contains("org.jddd.core.annotation.Factory") -> {
                data.locationString = "Factory"
            }
            annotations.contains("org.jddd.core.annotation.Service") -> {
                data.locationString = "Service"
            }
            annotations.contains("org.jddd.core.annotation.ValueObject") -> {
                data.locationString = "Value Object"
            }
            annotations.contains("org.jddd.architecture.layered.ApplicationLayer") -> {
                data.locationString = "Application Layer"
            }
            annotations.contains("org.jddd.architecture.layered.DomainLayer") -> {
                data.locationString = "Domain Layer"
            }
            annotations.contains("org.jddd.architecture.layered.InfrastructureLayer") -> {
                data.locationString = "Infrastructure Layer"
            }
            annotations.contains("org.jddd.architecture.layered.InterfaceLayer") -> {
                data.locationString = "Interface Layer"
            }
            annotations.contains("org.jddd.event.annotation.DomainEvent") -> {
                data.locationString = "Domain Event"
            }
        }
    }

    override fun decorate(node: PackageDependenciesNode?, cellRenderer: ColoredTreeCellRenderer?) {}
}