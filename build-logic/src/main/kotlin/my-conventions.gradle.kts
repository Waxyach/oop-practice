plugins {
    `java-library`
    id("com.diffplug.spotless")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    compileOnly(libs.findLibrary("lombok").get())
    annotationProcessor(libs.findLibrary("lombok").get())

    testImplementation(platform(libs.findLibrary("junit-bom").get()))
    testImplementation(libs.findBundle("junit-testing").get())
}

spotless {
    java {
        palantirJavaFormat("2.39.0")
        removeUnusedImports()
        importOrder(
            "java",
            "javax",
            "lombok",
            "org",
            "io",
            "net",
            "com",
            "me.waxyach",
        )
        targetExclude("build/generated/**")
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(21)
        options.isFork = true
    }

    withType<Javadoc>().configureEach {
        destinationDir = layout.buildDirectory.dir("docs/javadoc").get().asFile

        onlyIf {
            gradle.startParameter.taskNames.any {
                it.contains("publish") || it.contains("release") || it.contains("javadoc")
            }
        }

        options {
            encoding = "UTF-8"
            (this as StandardJavadocDocletOptions).apply {
                charSet = "UTF-8"
                docEncoding = "UTF-8"
                addStringOption("Xdoclint:none", "-quiet")
            }
        }
    }

    withType<Test>().configureEach {
        useJUnitPlatform()

        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
}