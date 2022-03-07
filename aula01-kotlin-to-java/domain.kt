import kotlin.math.sqrt

data class Student(val nr: Int, val name: String, val addr: Address) {
    data class Address(val nr: Int, val road: String)
}

data class Account(val balance: Double, val id: String)

data class Point(val x: Int, val y: Int) {
    val module = sqrt((x*x + y*y).toDouble())
}