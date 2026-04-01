plugins {
    application
    id("java-library")
    id("my-conventions")
    id(libs.plugins.javafx.get().pluginId).version(libs.plugins.javafx.get().version.displayName)
}

javafx {
    version = "23"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.graphics")
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

    testImplementation("org.testfx:testfx-core:4.0.17")
    testImplementation("org.testfx:testfx-junit5:4.0.17")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.assertj:assertj-core:3.24.2")
}