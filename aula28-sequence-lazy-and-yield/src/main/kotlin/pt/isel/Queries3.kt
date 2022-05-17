package pt.isel

import pt.isel.sample.Student
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

inline fun <reified T : Any> Map<String, String>.toObject() : T {
    return this.toObject(T::class)
}

/**
 * Creates an instance of T initialized with the values of the Map receiver object.
 *
 * @param Map<String, String> Name value pairs corresponding to T properties.
 */
fun <T : Any> Map<String, String>.toObject(destination: KClass<T>) : T {
   /**
     * Look for constructor KParameters
     */
    val initArgs: Map<String?, KParameter>? = destination
        .primaryConstructor
        ?.parameters
        ?.associate { it.name to it}
    /**
     * Fill args Map to be passed on callBy() to constructor
     */
    val args = mutableMapOf<KParameter, Any>()
    this.forEach { (k, v) ->
        val kParam: KParameter = initArgs?.get(k) ?: throw Exception("There is no constructor argument for $k")
        val v: Any = basicParser[kParam.type.jvmErasure]?.let { it(v) } ?: throw Exception("Argument $k type not supported!")
        args[kParam] = v
    }
    return destination.primaryConstructor?.callBy(args) ?: throw Exception("Missing primary constructor")
}

private val basicParser: Map<KClass<*>, (String) -> Any> = mapOf(
        String::class to { it },
        Byte::class to { it.toByte() },
        Short::class to { it.toShort() },
        Int::class to { it.toInt() },
        Long::class to { it.toLong() },
        Float::class to { it.toFloat() },
        Double::class to { it.toDouble() },
        Boolean::class to { it.toBoolean() }
)
