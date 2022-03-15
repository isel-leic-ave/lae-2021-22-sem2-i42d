package pt.isel

import kotlin.test.Test
import kotlin.test.assertEquals

class PrinterStringBuffer : Printer {
    val buffer = StringBuffer()
    override fun print(msg: Any?) {
        buffer.append(msg)
    }
}

class LoggerTest {
    @Test fun testLogPoint() {
        val expected = "pt.isel.Point {x = 5,y = 7}${System.lineSeparator()}"
        val p = Point(5, 7)
        val out = PrinterStringBuffer()
        val logger = Logger(out)
        logger.log(p)
        assertEquals(expected, out.buffer.toString())
    }

    @Test fun testLogPointToDefaultOutput() {
        Logger().log(Point(5,7))
    }
}

