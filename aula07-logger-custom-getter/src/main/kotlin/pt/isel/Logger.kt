package pt.isel

import pt.isel.LoggerKind.FUNCTIONS
import pt.isel.LoggerKind.PROPERTIES
import kotlin.reflect.KClass
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.memberProperties

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
            .map { prop -> object : Getter {
                    override fun readAndPrint(target: Any) {
                        val v = prop.call(target)
                        out.print("${prop.name}() = $v,")
                    }
            }}
    }

    private fun createGetterProperties(klass: KClass<*>) : List<Getter> {
        return klass
                .memberProperties
                .map { prop ->
                object : Getter {
                    override fun readAndPrint(target: Any) {
                        val v = prop.call(target)
                        out.print("${prop.name} = $v,")
                    }
                }}
    }
}
