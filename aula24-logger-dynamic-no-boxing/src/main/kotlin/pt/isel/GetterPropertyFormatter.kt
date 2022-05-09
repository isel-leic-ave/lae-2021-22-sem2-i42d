package pt.isel

import kotlin.reflect.KProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation

class GetterPropertyFormatter(val out: Printer, val prop: KProperty<*>) : Getter {
    val formatter: Formatter = prop
        .findAnnotation<ToLog>()
        ?.formatter
        ?.createInstance() as Formatter

    override fun readAndPrint(target: Any) {
        val v = prop.call(target)
        val res = formatter?.let {
            "\"${it.format(v)}\""
        }
        out.print("${prop.name} = $res,")
    }
}
