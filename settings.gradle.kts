pluginManagement {
  plugins {
        id("org.jetbrains.kotlin.jvm") version "1.6.10"
    }
}

rootProject.name = "lae-i42d"
include("aula04-reflect")
include("aula05-reflect-instance-and-func-call")
include("aula06-sample-domain")
include("aula10-annotations-intro")
// include("aula10-logger-with-fornatter")
include("aula16-javapoet")
// include("aula17-logger-dynamic")
// include("aula20-logger-JMH")
include("aula24-logger-dynamic-no-boxing")
// include("aula26-iterable-and-generics")
// include("aula27-iterable-reified-type-parameter")
include("aula28-sequence-lazy-and-yield")