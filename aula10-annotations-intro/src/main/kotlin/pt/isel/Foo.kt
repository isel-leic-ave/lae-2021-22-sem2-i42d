package pt.isel

// Compilation Error: this annotation is not applicable to target class
// @Fancy
class Foo(@Fancy("ola") val dummy: Int) {
    @Fancy("isel") fun bar() {
    }
}
