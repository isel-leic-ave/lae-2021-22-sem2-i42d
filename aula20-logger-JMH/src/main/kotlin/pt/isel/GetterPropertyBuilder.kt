package pt.isel

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaMethod

/**
 * Generate a class that implements Getter similar to:
 *
 * class Getter_Prop_SavingsAccount_balance implements Getter {
 *    private final Printer out;
 *    public Getter_Prop_SavingsAccount_balance(Printer out) {
 *      this.out = out;
 *    }
 *    public void readAndPrint(Object target) {
 *      Object v = ((SavingsAccount) target).getBalance();
 *      out.print("balance = " + v + ",");
 *    }
 *  }
 */
fun buildGetterProperty(klass: KClass<*>, prop: KProperty<*>) : JavaFile {

    val fieldOut = FieldSpec
        .builder(Printer::class.java, "out")
        .addModifiers(Modifier.FINAL)
        .build()

    val init = MethodSpec
        .constructorBuilder()
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Printer::class.java, "out" )
        .addStatement("this.\$N = out", fieldOut)
        .build()

    val readAndPrint = MethodSpec
        .methodBuilder("readAndPrint")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Any::class.java, "target")
        .addStatement("Object v =((${klass.qualifiedName}) target).${prop.getter.javaMethod?.name}()")
        .addStatement("out.print(\"${prop.name} = \" + v + \",\")")
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

fun buildGetterFunc(klass: KClass<*>, func: KCallable<*>) : JavaFile {

    val fieldOut = FieldSpec
        .builder(Printer::class.java, "out")
        .addModifiers(Modifier.FINAL)
        .build()

    val init = MethodSpec
        .constructorBuilder()
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Printer::class.java, "out" )
        .addStatement("this.\$N = out", fieldOut)
        .build()

    val funcName = if(func is KProperty) func.getter.javaMethod?.name
        else func.name

    val readAndPrint = MethodSpec
        .methodBuilder("readAndPrint")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Any::class.java, "target")
        .addStatement("Object v =((${klass.qualifiedName}) target).${funcName}()")
        .addStatement("out.print(\"${func.name}() = \" + v + \",\")")
        .build()

    val getter = TypeSpec
        .classBuilder("Getter_Function_${klass.simpleName}_${func.name}")
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(Getter::class.java)
        .addField(fieldOut)
        .addMethod(readAndPrint)
        .addMethod(init)
        .build()

    return JavaFile.builder("", getter).build()
}
