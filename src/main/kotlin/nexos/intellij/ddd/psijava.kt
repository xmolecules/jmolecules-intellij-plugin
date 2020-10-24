package nexos.intellij.ddd

import com.intellij.psi.PsiJavaFile
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValueProvider.Result
import com.intellij.psi.util.CachedValuesManager

private fun findPackageAnnotations(psiFile: PsiJavaFile, annotationsByFQName: Map<String, Info>) =
        psiFile.packageStatement
                ?.annotationList
                ?.annotations
                ?.mapNotNull { annotationsByFQName[it.qualifiedName] }
                ?: listOf()

private fun findTopLevelClassAnnotations(psiFile: PsiJavaFile, annotationsByFQName: Map<String, Info>)
        = psiFile.classes.map { it.annotations.mapNotNull { annotationsByFQName[it.qualifiedName] }}.flatten()

private class Cached(private val file: PsiJavaFile): CachedValueProvider<List<Info>> {
    companion object {
        val annotationsByFQName by lazy { all.associateBy { it.fqName }}
    }

    override fun compute() = Result(
                findPackageAnnotations(file, annotationsByFQName)
                        + findTopLevelClassAnnotations(file, annotationsByFQName)
                , file)
}

fun cached(file: PsiJavaFile): List<Info> = CachedValuesManager.getCachedValue(file, Cached(file))