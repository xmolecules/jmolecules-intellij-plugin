package nexos.intellij.jddd

import org.junit.Test
import kotlin.test.assertEquals


class CreateLocationStringTest {
    @Test
    fun root () {
        assertEquals(filterAnnotations(listOf("org.jddd.core.annotation.AggregateRoot"))
                .joinToString(separator = " ") { it.displayName }, "Aggregate Root")
    }

    @Test
    fun rootAndEntity () {
        assertEquals(filterAnnotations(listOf("org.jddd.core.annotation.AggregateRoot", "org.jddd.core.annotation.Entity"))
                .joinToString(separator = " ") { it.displayName }, "Aggregate Root Entity")
    }
}