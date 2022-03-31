package pt.isel

import java.net.URL
import java.net.URLClassLoader
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties

const val poetJar = "https://repo1.maven.org/maven2/com/squareup/javapoet/1.13.0/javapoet-1.13.0.jar"

fun main() {
    listClasses(URL(poetJar))
            .forEach { println(it) }
}

fun listClasses(url: URL) : List<KClass<*>> {
    URLClassLoader(arrayOf(url)).use { loader ->
        ZipInputStream(url.openStream()).use { zip ->
            val res = mutableListOf<KClass<*>>()
            var entry = zip.nextEntry
            while(entry != null) {
                if(!entry.isDirectory && entry.name.contains(".class")) {
                    val clazz: Class<*> = loader.loadClass(qualifiedName(entry));
                    res.add(clazz.kotlin)
                }
                entry = zip.nextEntry
            }
            return res
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
