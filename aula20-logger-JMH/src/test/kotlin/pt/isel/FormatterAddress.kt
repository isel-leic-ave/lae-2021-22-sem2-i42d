package pt.isel

class FormatterAddress : Formatter {
    override fun format(v: Any?): Any? {
        val addr = v as Student.Address
        return "${addr.road}, ${addr.nr}"
    }
}
