package pt.isel

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaMethod

fun buildGetterProperty(klass: KClass<*>, prop: KProperty<*>) : JavaFile {

    val fieldOut = FieldSpec
        .builder(Printer::class.java, "out")
        .build()

    // TPC Constructor to initialize out field.
    val init = MethodSpec
        .constructorBuilder()
        // ..... // TPC TODO
        .build()
    //
    // Object v = ((SavingsAccount) target).getBalance();
    // out.print("balance = " + v + ",");
    //
    val readAndPrint = MethodSpec
        .methodBuilder("readAndPrint")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Any::class.java, "target")
        // .addStatement() // TODO: Object v = ((SavingsAccount) target).getBalance();
        // .addStatement() // TODO: out.print("balance = " + v + ",");
        .build()

    val getter = TypeSpec
        .classBuilder("Getter_Property_${klass.simpleName}_${prop.name}")
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(Getter::class.java)
        .addField(fieldOut)
        .addMethod(readAndPrint)
        .addMethod(init)
        .build()

    return JavaFile.builder("", getter).build()
}
