plugins {
    application
    id("java-library")
    id("my-conventions")
    id("org.openjfx.javafxplugin") version("0.1.0")
}

javafx {
    version = "23"
    modules = listOf("javafx.controls", "javafx.graphics")
}

application {
    mainClass.set("me.waxyach.practice.gui.Main")
}

dependencies {
    implementation(project(":task1"))
    implementation(project(":task2"))
    implementation(project(":task3"))
    implementation(project(":task4"))
    implementation(project(":task5"))

    testImplementation(libs.testfx.core)
    testImplementation(libs.testfx.junit)
    testImplementation(libs.hamcrest)
    testImplementation(libs.assertj)
}