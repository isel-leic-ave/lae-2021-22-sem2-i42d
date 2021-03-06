/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package pt.isel

import pt.isel.sample.toStudent
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Queries2Test {
    private val lae2022uri = ClassLoader.getSystemClassLoader().getResource("lae2022.txt").toURI()


    @Test fun `First surname of a Student with number greater than 4700 and first letter A`() {
        val actual = File(lae2022uri)
            .readLines()
            .parseCsv(';')
            .convert { it.toStudent() }
            .where { it.nr > 47000 }
            .convert { it.name.split(" ").last() }
            .where { it.startsWith("A") }
            .iterator()
            .next()
        assertEquals("Almeida", actual)
    }

    @Test fun `With Collection Extension select first surname of a Student with number greater than 4700 and first letter A`() {
        val actual = File(lae2022uri)
            .readLines()
            .parseCsv(';')
            .map { it.toStudent() }
            .filter { it.nr > 47000 }
            .map { it.name.split(" ").last() }
            .filter { it.startsWith("A") }
            .iterator()
            .next()
        assertEquals("Almeida", actual)
    }

    @Test fun `Concat two sequences of strings`() {
        val first = listOf("Portugal", "Football", "Teams")
        val other = listOf("FCP", "The", "Champion")
        val actual = first.concat(other)
        val expected = listOf("Portugal", "Football", "Teams", "FCP", "The", "Champion")
        assertEquals(expected, actual)
    }
}
