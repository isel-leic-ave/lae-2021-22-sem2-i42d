package pt.isel

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier


/**
 * Build a class MyDynamic equivalent to:
 * public final class MyDynamic {
     final int nr;
     public MyDynamic(int nr) {
        this.nr = nr;
     }
     public int mul(int other) {
        return this.nr * other;
     }
}
 */
fun buildMyDynamic(): JavaFile {
    val fieldNr = FieldSpec
        .builder(Int::class.java, "number")
        .addModifiers(Modifier.FINAL, Modifier.PRIVATE)
        .build()

    val init = MethodSpec
        .constructorBuilder()
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Int::class.java, "nr")
        .addStatement("this.\$N = nr", fieldNr)
        .build()

    val mul = MethodSpec
        .methodBuilder("mul")
        .addModifiers(Modifier.PUBLIC)
        .returns(Int::class.java)
        .addParameter(Int::class.java, "other")
        .addStatement("return this.\$N * other", fieldNr)
        .build()

    val myType = TypeSpec
        .classBuilder("MyDynamic")
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(Multiplier::class.java)
        .addField(fieldNr)
        .addMethod(init)
        .addMethod(mul)
        .build()
    return JavaFile.builder("", myType).build()
}
