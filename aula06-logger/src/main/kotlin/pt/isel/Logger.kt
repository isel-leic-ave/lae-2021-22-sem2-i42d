package pt.isel

import pt.isel.LoggerKind.FUNCTIONS
import pt.isel.LoggerKind.PROPERTIES
import kotlin.reflect.KFunction
import kotlin.reflect.full.*

class Logger(
    private val out: Printer = PrinterConsole(),
    private val kind: LoggerKind = PROPERTIES)
{
    fun log(target: Any) {
        out.print(target::class.qualifiedName)
        out.print(" {")
        logMembers(target)
        out.println("}")
    }
    private fun logMembers(target: Any) = when(kind) {
        PROPERTIES ->  logProperties(target)
        FUNCTIONS -> logFunctions(target)
    }

    private fun logFunctions(target: Any) {
    }

    /**
     * Prints to console the object's state,
     * corresponding to the value of its properties.
     */
    private fun logProperties(target: Any) {
        target::class
                .memberProperties
                .joinToString(",") { "${it.name} = ${it.call(target)}" }
                .let { out.print(it) }
    }
}
