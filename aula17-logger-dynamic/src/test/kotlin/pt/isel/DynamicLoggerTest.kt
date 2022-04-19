package pt.isel

import org.junit.Test
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties

class DynamicLoggerTest {

    @Test fun testDynamicGetter() {
        val bal: KProperty<*> = SavingsAccount::class.declaredMemberProperties.find { it.name == "balance" }!!
        buildGetterProperty(SavingsAccount::class, bal).writeTo(System.out)
    }
}
