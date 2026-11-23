// AGP 9 trae Kotlin integrado; estas lineas fijan Kotlin 2.3.20 y el plugin de KSP.
buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.20")
        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.3.14")
    }
}

plugins {
    id("com.android.application") version "9.0.1" apply false
}
