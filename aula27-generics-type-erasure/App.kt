class Point(val x: Int, val y: Int) // : Any -> Implicit on top of hierarchy

class Student(val name: String)

fun main() {
    val objs: List<*> = listOf(Point(2,3), Point(3,4)) // List<Object> at JVM level
    val obj0 = objs[0] as Point
    obj0.x // x is not accesible without the cast because the variable is Any
    /**
     * Expressividade. O tipo de lista expressa o tipo de elementos.
     * == Eficiencia => ao nivel da JVM continua a ser uma List de Object que requer o cast no acesso.
     */
    val pts: List<Point> = listOf(Point(2,3), Point(3,4)) // List<Object> at JVM level => Point is erased
    val pt0: Point = pts[0] // Implicit Cast
    val pt0coordX = pt0.x


}

