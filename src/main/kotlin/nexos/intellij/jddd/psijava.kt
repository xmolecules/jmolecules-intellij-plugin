package nexos.intellij.jddd

import com.intellij.psi.PsiJavaFile
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValueProvider.Result
import com.intellij.psi.util.CachedValuesManager

private fun findPackageAnnotations(psiFile: PsiJavaFile) =
        psiFile.packageStatement
                ?.annotationList
                ?.annotations
                ?.mapNotNull { annotationsByFQName[it.qualifiedName] }
                ?: listOf()

private fun findTopLevelClassAnnotations(psiFile: PsiJavaFile) = psiFile.classes.map { clazz ->
    clazz.annotations.mapNotNull { annotationsByFQName[it.qualifiedName] }
}.flatten()

private class Cached(private val file: PsiJavaFile): CachedValueProvider<List<Info>> {
    override fun compute(): Result<List<Info>>? {
        return Result(findPackageAnnotations(file) + findTopLevelClassAnnotations(file), file)
    }
}

fun cached(file: PsiJavaFile): List<Info> = CachedValuesManager.getCachedValue(file, Cached(file))