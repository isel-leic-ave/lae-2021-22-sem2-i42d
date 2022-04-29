package pt.isel

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation

class GetterFunctionFormatter(val out: Printer, val func: KCallable<*>) : Getter {
    private val formatter: Formatter = func
        .findAnnotation<ToLog>()
        ?.formatter
        ?.createInstance() as Formatter

    override fun readAndPrint(target: Any) {
        val v = func.call(target)
        val res = formatter.let {
            "\"${it.format(v)}\""
        }
        out.print("${func.name}() = $res,")
    }
}
