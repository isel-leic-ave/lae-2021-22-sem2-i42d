package pt.isel

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

fun buildGetterProperty(klass: KClass<*>, prop: KProperty<*>) : JavaFile {
    // Object v = ((SavingsAccount) target).getBalance();
    // out.print("balance() = " + v + ",");

    val fieldOut = FieldSpec
        .builder(Printer::class.java, "out")
        .build()

    // TPC Constructor to initialize out field.

    val readAndPrint = MethodSpec
        .methodBuilder("readAndPrint")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Any::class.java, "target")
        // .addStatement() TPC
        // .addStatement()
        .build()

    val getter = TypeSpec
        .classBuilder("Getter_Property_${klass.simpleName}_${prop.name}")
        .addModifiers(Modifier.PUBLIC)
        .addMethod(readAndPrint)
        .build()

    return JavaFile.builder("", getter).build()
}
