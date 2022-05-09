package pt.isel

import pt.isel.LoggerKind.FUNCTIONS
import pt.isel.LoggerKind.PROPERTIES
import kotlin.reflect.KClass
import kotlin.reflect.full.*

class LoggerReflect(
    out: Printer = PrinterConsole(),
    kind: LoggerKind = PROPERTIES) : AbstractLogger(out, kind)
{
    private val unitType = Unit::class.createType()

    override fun createGetterFunctions(klass: KClass<*>) : List<Getter> {
         return klass
            .declaredMembers
            .filter{ (it.parameters.size == 1) && it.returnType != unitType }
            .filter { it.hasAnnotation<ToLog>() }
            .map { func -> if(func.findAnnotation<ToLog>()?.formatter == Unit::class)
                GetterFunction(out, func)
                else GetterFunctionFormatter(out, func)
            }
    }

    override fun createGetterProperties(klass: KClass<*>) : List<Getter> {
        return klass
            .memberProperties
            .filter { it.hasAnnotation<ToLog>() }
            .map { prop -> if(prop.findAnnotation<ToLog>()?.formatter == Unit::class)
                GetterProperty(out, prop)
                else GetterPropertyFormatter(out, prop)
            }
    }
}
