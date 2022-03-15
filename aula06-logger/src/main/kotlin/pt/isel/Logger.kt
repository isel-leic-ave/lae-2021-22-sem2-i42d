package pt.isel

import pt.isel.LoggerKind.FIELDS
import pt.isel.LoggerKind.PROPERTIES
import kotlin.reflect.full.memberProperties

class Logger(val out: Printer = PrinterConsole(), val kind: LoggerKind = PROPERTIES) {
    fun log(target: Any) = when(kind) {
        PROPERTIES ->  logProperties(target)
        FIELDS -> logFields(target)
    }

    private fun logFields(target: Any) {

    }

    /**
     * Prints to console the object's state,
     * corresponding to the value of its properties.
     */
    private fun logProperties(target: Any) {
        out.print(target::class.qualifiedName)
        out.print(" {")
        target::class
                .memberProperties
                .joinToString(",") { "${it.name} = ${it.call(target)}"}
                .let { out.print(it) }
        out.println("}")
    }
}
