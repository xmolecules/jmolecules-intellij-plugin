package nexos.intellij.jddd

import com.intellij.psi.PsiJavaFile

fun findPackageAnnotations(psiFile: PsiJavaFile) =
        psiFile.packageStatement
                ?.annotationList
                ?.annotations
                ?.mapNotNull { it.qualifiedName }
                ?: listOf()

fun findTopLevelClassAnnotations(psiFile: PsiJavaFile): List<String>
        = psiFile.classes.map { clazz ->
    clazz.annotations.mapNotNull { annotation ->
        annotation.nameReferenceElement?.qualifiedName
    }
}.flatten()