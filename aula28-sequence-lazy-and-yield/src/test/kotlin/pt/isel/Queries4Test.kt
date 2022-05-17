/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package pt.isel

import pt.isel.sample.Student
import pt.isel.sample.toStudent
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Queries4Test {
    private val lae2022uri = ClassLoader.getSystemClassLoader().getResource("lae2022.txt").toURI()


    @Test fun `First surname of a Student with number greater than 4700 and first letter A`() {
        val actual = File(lae2022uri)
            .readLines()
            .parseCsv(';')
            .convertLazy { it.toObject<Student>() }
            .whereLazy { it.nr > 47000 }
            .convertLazy { it.name.split(" ").last() }
            .whereLazy { it.startsWith("A") }
            .iterator()
            .next()
        assertEquals("Almeida", actual)
    }
}
