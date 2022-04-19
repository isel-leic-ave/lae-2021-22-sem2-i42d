package pt.isel

import pt.isel.LoggerKind.FUNCTIONS
import pt.isel.LoggerKind.PROPERTIES
import kotlin.reflect.KClass
import kotlin.reflect.full.*

abstract class AbstractLogger(
    protected val out: Printer = PrinterConsole(),
    private val kind: LoggerKind = PROPERTIES)
{
    fun log(target: Any) {
        out.print(target::class.qualifiedName)
        out.print(" {")
        loadGetters(target::class).forEach{ it.readAndPrint(target) }
        out.println("}")
    }
    private fun loadGetters(klass: KClass<*>) : List<Getter> {
        return when(kind) {
            PROPERTIES ->  loadGetterProperties(klass)
            FUNCTIONS -> loadGetterFunctions(klass)
        }
    }

    private val getters : MutableMap<KClass<*>, List<Getter>> = mutableMapOf()

    private fun loadGetterFunctions(klass: KClass<*>) : List<Getter> {
        return getters.computeIfAbsent(klass, ::createGetterFunctions)
    }

    private fun loadGetterProperties(klass: KClass<*>): List<Getter> {
        return getters.computeIfAbsent(klass, ::createGetterProperties)
    }

    abstract fun createGetterFunctions(klass: KClass<*>) : List<Getter>

    abstract fun createGetterProperties(klass: KClass<*>) : List<Getter>
}
