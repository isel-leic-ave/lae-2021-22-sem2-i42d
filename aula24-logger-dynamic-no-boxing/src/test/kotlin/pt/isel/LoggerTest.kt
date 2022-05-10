package pt.isel

import pt.isel.Student.Address
import kotlin.test.Test
import kotlin.test.assertEquals

class PrinterStringBuffer : Printer {
    val buffer = StringBuffer()
    override fun print(msg: Any?) {
        buffer.append(msg)
    }

    override fun print(msg: Byte) {
        buffer.append(msg)
    }

    override fun print(msg: Short) {
        buffer.append(msg)
    }

    override fun print(msg: Int) {
        buffer.append(msg)
    }

    override fun print(msg: Long) {
        buffer.append(msg)
    }

    override fun print(msg: Float) {
        buffer.append(msg)
    }

    override fun print(msg: Double) {
        buffer.append(msg)
    }
}

class LoggerTest {
    @Test fun testLogPointPropertiesReflect() {
        val out = PrinterStringBuffer()
        val logger = LoggerReflect(out)
        logPointProperties(logger, out)
    }

    @Test fun testLogAccountPropertiesReflect() {
        val out = PrinterStringBuffer()
        val logger = LoggerReflect(out, LoggerKind.PROPERTIES)
        logAccountProperties(logger, out)
    }

    @Test fun testLogAccountPropertiesAndFunctionsReflect() {
        val out = PrinterStringBuffer()
        val logger = LoggerReflect(out, LoggerKind.FUNCTIONS)
        logAccountPropertiesAndFunctions(logger, out)
    }

    @Test fun testLogPointPropertiesDynamic() {
        val out = PrinterStringBuffer()
        val logger = LoggerDynamic(out)
        logPointProperties(logger, out)
    }

    @Test fun testLogAccountPropertiesDynamic() {
        val out = PrinterStringBuffer()
        val logger = LoggerDynamic(out, LoggerKind.PROPERTIES)
        logAccountProperties(logger, out)
    }

    @Test fun testLogAccountPropertiesAndFunctionsDynamic() {
        val out = PrinterStringBuffer()
        val logger = LoggerDynamic(out, LoggerKind.FUNCTIONS)
        logAccountPropertiesAndFunctions(logger, out)
    }

    fun logPointProperties(logger: AbstractLogger, out: PrinterStringBuffer) {
        val expected = "pt.isel.Point {x = 5,y = 7,}${System.lineSeparator()}"
        val p = Point(5, 7)
        logger.log(p)
        assertEquals(expected, out.buffer.toString())
    }

    fun logAccountProperties(logger: AbstractLogger, out: PrinterStringBuffer) {
        val expected = "pt.isel.SavingsAccount {balance = 1000,}${System.lineSeparator()}"
        val a = SavingsAccount(1000, 2.5)
        logger.log(a)
        assertEquals(expected, out.buffer.toString())
    }

    fun logAccountPropertiesAndFunctions(logger: AbstractLogger, out: PrinterStringBuffer) {
        val expected = "pt.isel.SavingsAccount {balance() = 1000,monthlyInterest() = 208,}${System.lineSeparator()}"
        val a = SavingsAccount(1000, 2.5)
        logger.log(a)
        assertEquals(expected, out.buffer.toString())
    }

    @Test fun testLogPointToDefaultOutput() {
        LoggerReflect().log(Student(7613, "Ze Manel", Address(67, "Rua das Papoilas")))
    }
}

