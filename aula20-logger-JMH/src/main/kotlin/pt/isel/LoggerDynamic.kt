package pt.isel

import pt.isel.LoggerKind.FUNCTIONS
import pt.isel.LoggerKind.PROPERTIES
import kotlin.reflect.KClass
import kotlin.reflect.full.*

class LoggerDynamic(
    out: Printer = PrinterConsole(),
    kind: LoggerKind = PROPERTIES) : AbstractLogger(out, kind)
{
    private val unitType = Unit::class.createType()

    override fun createGetterFunctions(klass: KClass<*>) : List<Getter> {
         return klass
            .declaredMembers
            .filter{ (it.parameters.size == 1) && it.returnType != unitType }
            .filter { it.hasAnnotation<ToLog>() }
            .map { func ->
                val getterFile = buildGetterFunc(klass, func)
                loadAndCreateInstance(getterFile, out) as Getter
            }
    }

    override fun createGetterProperties(klass: KClass<*>) : List<Getter> {
        return klass
            .memberProperties
            .filter { it.hasAnnotation<ToLog>() }
            .map { prop ->
                val getterFile = buildGetterProperty(klass, prop)
                loadAndCreateInstance(getterFile, out) as Getter
            }
    }
}
