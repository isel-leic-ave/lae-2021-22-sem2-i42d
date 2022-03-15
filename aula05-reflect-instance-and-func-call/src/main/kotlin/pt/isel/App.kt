package pt.isel

import java.net.URL
import java.net.URLClassLoader
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KParameter.Kind
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties

const val poetJar = "https://repo1.maven.org/maven2/com/squareup/javapoet/1.13.0/javapoet-1.13.0.jar"

fun main() {
    listClasses(URL(poetJar))
            .filter { it.qualifiedName != null }
            .forEach { inspectKlass(it) }
}

fun inspectKlass(klass: KClass<*>){
    println(klass.qualifiedName)
    println("## Properties ## ")
    klass.memberProperties.forEach { println("  + " + it.name ) }
    println("## Member Functions ##")
    val receiver: Any? = klass
        .constructors
        .singleOrNull { it.parameters.all(KParameter::isOptional) }
        .let { try {
            if(it != null) klass.createInstance() else null
        } catch(e: Exception) { null }} // DON'T USE IT
    klass
        .memberFunctions
        .forEach { inspectFunc(receiver, it)}
}

fun inspectFunc(receiver: Any?, func: KFunction<*>) {
    var argsInfo = func
            .parameters
            .filter { it.kind != KParameter.Kind.INSTANCE }
            .map { it.name } // + ": " + it.type }
            .joinToString(",")
    print("  - ${func.name}($argsInfo): ${func.returnType}")
    if (receiver != null
        && func.parameters.size == 1
        && func.instanceParameter?.kind == Kind.INSTANCE
    ) {
        print(" ===> ${func.call(receiver)}")
    }
    println()
}


fun listClasses(url: URL) : List<KClass<*>> {
    return URLClassLoader(arrayOf(url)).use { loader ->
        ZipInputStream(url.openStream()).use { zip -> zip
                .iterable()
                .filter { !it.isDirectory && it.name.contains(".class") }
                .map { loader.loadClass(qualifiedName(it)).kotlin }
        }
    }
}

fun ZipInputStream.iterable() = object : Iterable<ZipEntry> {
    override fun iterator() = object: Iterator<ZipEntry> {
        var entry = this@iterable.nextEntry
        override fun hasNext() = entry != null
        override fun next(): ZipEntry {
            val curr = entry
            entry = this@iterable.nextEntry
            return curr
        }
    }
}

fun qualifiedName(entry: ZipEntry) = entry
        .name
        .replace('/', '.') // including ".class"
        .let { name -> name.substring(0, name.length - ".class".length) }


fun introReflection() {
    val klassPoint: KClass<Point> = Point::class
    val p = Point(5,7)
    println("The same KClass reference: ${klassPoint == p::class}")
    println(klassPoint.qualifiedName)
    println("##### Properties")
    klassPoint.memberProperties.forEach { println(it.name) }
    println("##### Member Functions")
    klassPoint.memberFunctions.forEach { println(it.name) }
    println("--------------------------------")
    val classPoint = klassPoint.java
    println("The same reference: ${classPoint.kotlin == klassPoint}")
    println(classPoint.name)
    println("##### Fields")
    classPoint.declaredFields.forEach { println(it.name) }
    println("##### Methods")
    classPoint.methods.forEach { println(it.name) }
}
