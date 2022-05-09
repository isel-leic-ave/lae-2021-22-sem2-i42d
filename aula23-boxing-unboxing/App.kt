class Point(val x: Int, val y: Int) // : Any -> Implicit on top of hierarchy

class Student(val name: String)

fun main() {
    val p = Point(9,7)
    val nr = 731
    val obj : Any = p // upcasting
    val s = Student("Maria")
    foo(obj)
    foo(s)
    foo(nr)
}

fun foo(obj : Any) {
    // val p : Point = obj as Point // bytecode -> checkcast
    // println("(${p.x}, ${p.y})")
    println(obj::class)
}
