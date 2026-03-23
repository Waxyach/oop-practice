plugins {
    `java-library`
    id("com.diffplug.spotless")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    withSourcesJar()
    withJavadocJar()
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
            "de",
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

    register("openDocs") {
        group = "documentation"
        dependsOn("javadoc")
    }

    withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
}