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
}
project.version = scmVersion.version

tasks {
    register("releasePatch") {
        group = "release"
        dependsOn("release")
        project.extensions.extraProperties["release.incrementer"] = "incrementPatch"
    }

    register("releaseMinor") {
        group = "release"
        dependsOn("release")
        project.extensions.extraProperties["release.incrementer"] = "incrementMinor"
    }

    register("releaseMajor") {
        group = "release"
        dependsOn("release")
        project.extensions.extraProperties["release.incrementer"] = "incrementMajor"
    }
}