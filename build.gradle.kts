import com.github.zafarkhaja.semver.Version

plugins {
    id("java-library")
    alias(libs.plugins.axion)
    id("my-conventions")
}

group = "me.waxyach"

scmVersion {
    tag { prefix.set("v") }
    checks { snapshotDependencies = false }
    val strategy: String = project.findProperty("release.incrementer")?.toString() ?: "incrementPatch"
    versionIncrementer.set { Version.valueOf(strategy) }
    hooks { pre("push") }
}
project.version = scmVersion.version

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testImplementation(libs.bundles.junit.testing)

    testImplementation(platform(libs.junit.bom))
}

tasks {
    // patch-release (0.0.x)
    register("releasePatch") {
        group = "release"
        dependsOn("release")
        project.extensions.extraProperties["release.incrementer"] = "incrementPatch"
    }

    // minor-release (0.x.0)
    register("releaseMinor") {
        group = "release"
        dependsOn("release")
        project.extensions.extraProperties["release.incrementer"] = "incrementMinor"
    }

    // major-release (x.0.0)
    register("releaseMajor") {
        group = "release"
        dependsOn("release")
        project.extensions.extraProperties["release.incrementer"] = "incrementMajor"
    }
}

tasks.test {
    useJUnitPlatform()
}