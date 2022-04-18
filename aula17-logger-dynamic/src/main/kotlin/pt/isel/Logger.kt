package pt.isel

import pt.isel.LoggerKind.FUNCTIONS
import pt.isel.LoggerKind.PROPERTIES
import kotlin.reflect.KClass
import kotlin.reflect.full.*

class Logger(
    private val out: Printer = PrinterConsole(),
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


    private val unitType = Unit::class.createType()
    private val getters : MutableMap<KClass<*>, List<Getter>> = mutableMapOf()

    private fun loadGetterFunctions(klass: KClass<*>) : List<Getter> {
        return getters.computeIfAbsent(klass, ::createGetterFunctions)
    }

    private fun loadGetterProperties(klass: KClass<*>): List<Getter> {
        return getters.computeIfAbsent(klass, ::createGetterProperties)
    }

    private fun createGetterFunctions(klass: KClass<*>) : List<Getter> {
         return klass
            .declaredMembers
            .filter{ (it.parameters.size == 1) && it.returnType != unitType }
            .filter { it.hasAnnotation<ToLog>() }
            .map { prop -> object : Getter {
                    val formatter: KClass<*>? =
                        if(prop.findAnnotation<ToLog>()?.formatter == Unit::class) null
                        else prop.findAnnotation<ToLog>()?.formatter
                    override fun readAndPrint(target: Any) {
                        val v = prop.call(target)
                        val res = formatter?.let {
                            val f = it.createInstance() as Formatter
                            "\"${f.format(v)}\""
                        }
                        ?: v
                        out.print("${prop.name}() = $res,")
                    }
            }}
    }

    private fun createGetterProperties(klass: KClass<*>) : List<Getter> {
        return klass
                .memberProperties
                .filter { it.hasAnnotation<ToLog>() }
                .map { prop ->
                    val formatter: KClass<*>? =
                        if(prop.findAnnotation<ToLog>()?.formatter == Unit::class) null
                        else prop.findAnnotation<ToLog>()?.formatter

                    object : Getter {
                        override fun readAndPrint(target: Any) {
                            val v = prop.call(target)
                            val res = formatter?.let {
                                val f = it.createInstance() as Formatter
                                "\"${f.format(v)}\""
                            }
                            ?: v
                            out.print("${prop.name} = $res,")
                        }
                    }
                }
    }
}
