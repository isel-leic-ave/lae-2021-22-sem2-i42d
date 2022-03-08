

import kotlin.math.sqrt

data class Student(val nr: Int, val name: String, val addr: Address) {
    data class Address(val nr: Int, val road: String)
}

data class Account(val balance: Double, val id: String)

/*
 * Gerado: 3 funções, 2 campos (x, y)
 * O corpo do getModule() tem os bytecodes: 
 * getfield  // Field x:I
 * dup
 * mul
 * getfield  // Field y:I
* ...
 */
/*
data class Point(val x: Int, val y: Int) {
    val module: Double
        get() { return sqrt((x*x + y*y).toDouble()) }
}
 */

/*
 * Gerado: 3 funções, 3 campos (x, y, module)
 */
data class Point(val x: Int, val y: Int) : Any() {
    /**
     * Os bytecodes de inicialização do module estão no construtor de Point.
     */
    val module = sqrt((x*x + y*y).toDouble())
}
