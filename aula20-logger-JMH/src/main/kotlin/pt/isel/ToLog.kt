package pt.isel

import kotlin.reflect.KClass

/**
 * formatter must be a Class that implements Formatter
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
annotation class ToLog(val formatter: KClass<*> = Unit::class)
