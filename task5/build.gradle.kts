plugins {
    id("java-library")
    id("my-conventions")
}

dependencies {
    implementation(project(":task1"))
    implementation(project(":task2"))
    implementation(project(":task3"))
    implementation(project(":task4"))
}