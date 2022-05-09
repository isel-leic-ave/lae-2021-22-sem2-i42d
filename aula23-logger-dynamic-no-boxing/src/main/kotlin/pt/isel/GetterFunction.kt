package pt.isel

import kotlin.reflect.KCallable

class GetterFunction(val out: Printer, val func: KCallable<*>) : Getter {
    override fun readAndPrint(target: Any) {
        val v = func.call(target)
        out.print("${func.name}() = $v,")
    }
}
