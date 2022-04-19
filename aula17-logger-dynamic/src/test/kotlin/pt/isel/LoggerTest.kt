package pt.isel

import pt.isel.Student.Address
import kotlin.test.Test
import kotlin.test.assertEquals

class PrinterStringBuffer : Printer {
    val buffer = StringBuffer()
    override fun print(msg: Any?) {
        buffer.append(msg)
    }
}

class LoggerTest {
    @Test fun testLogPointProperties() {
        val expected = "pt.isel.Point {x = 5,y = 7,}${System.lineSeparator()}"
        val p = Point(5, 7)
        val out = PrinterStringBuffer()
        val logger = LoggerReflect(out)
        logger.log(p)
        assertEquals(expected, out.buffer.toString())
    }

    @Test fun testLogAccountProperties() {
        val expected = "pt.isel.SavingsAccount {balance = 1000,}${System.lineSeparator()}"
        val a = SavingsAccount(1000, 2.5)
        val out = PrinterStringBuffer()
        val logger = LoggerReflect(out, LoggerKind.PROPERTIES)
        logger.log(a)
        assertEquals(expected, out.buffer.toString())
    }

    @Test fun testLogAccountPropertiesAndFunctions() {
        val expected = "pt.isel.SavingsAccount {balance() = 1000,monthlyInterest() = 208,}${System.lineSeparator()}"
        val a = SavingsAccount(1000, 2.5)
        val out = PrinterStringBuffer()
        val logger = LoggerReflect(out, LoggerKind.FUNCTIONS)
        logger.log(a)
        assertEquals(expected, out.buffer.toString())
    }

    @Test fun testLogPointToDefaultOutput() {
        LoggerReflect().log(Student(7613, "Ze Manel", Address(67, "Rua das Papoilas")))
    }
}

