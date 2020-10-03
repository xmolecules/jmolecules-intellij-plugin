package nexos.intellij.jddd

import com.intellij.psi.PsiJavaFile

fun findPackageAnnotations(psiFile: PsiJavaFile) =
        psiFile.packageStatement
                ?.annotationList
                ?.annotations
                ?.mapNotNull { annotationsByFQName[it.qualifiedName] }
                ?: listOf()

fun findTopLevelClassAnnotations(psiFile: PsiJavaFile) = psiFile.classes.map { clazz ->
    clazz.annotations.mapNotNull { annotationsByFQName[it.qualifiedName] }
}.flatten()