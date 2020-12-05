package nexos.intellij.ddd

import com.intellij.lang.Language.findLanguageByID
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiJavaFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PSITest : BasePlatformTestCase() {
    @BeforeEach
    override fun setUp() {
        super.setUp()
    }

    @AfterEach
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun foo() = runTestRunnable {
        val psiFile = PsiFileFactory.getInstance(project)
            .createFileFromText(
                findLanguageByID("JAVA")!!,
                """
                    @org.jmolecules.ddd.annotation.Entity
                        class EntityTest {}
                    """.trimIndent()
            )
        if (psiFile is PsiJavaFile) {
            val result = findTopLevelClassAnnotations(psiFile, Cached.annotationsByFQName)
             assertTrue(result.isNotEmpty())
        } else {
            throw IllegalStateException()
        }
    }

    override fun isWriteActionRequired() = true
}