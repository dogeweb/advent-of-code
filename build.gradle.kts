import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.0"
}

sourceSets {
    main {
        kotlin.srcDir("src")
        dependencies {
//            implementation("io.arrow-kt:arrow-core:1.2.4")
//            implementation("org.jetbrains.kotlinx:multik-core-jvm:0.2.3")
//            implementation("org.jetbrains.kotlinx:multik-default:0.2.3")
//            implementation("org.apache.commons:commons-math3:3.6.1")
//            implementation("org.choco-solver:choco-solver:4.10.17")
        }
    }
}

repositories {
    mavenCentral()
}



tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xwhen-guards"))
}