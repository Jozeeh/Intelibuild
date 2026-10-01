pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
    }
}

plugins {
    // See https://stonecutter.kikugie.dev/blog/changes/0.9
    id("dev.kikugie.stonecutter") version "0.9.8"

    // Applies the correct Loom variant for the active Minecraft version.
    // Intelibuild only targets 26.1+, which uses the plain (non-remapping) Loom,
    // but keeping this makes older versions a one-line change later on.
    id("dev.kikugie.loom-back-compat") version "0.4.3"

    // Needed on machines without a matching JDK installed, so it doesn't hurt to have.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        // 26.1's patch releases (26.1.1, 26.1.2) share the same API and are covered
        // by the `~26.1` compatibility range declared in stonecutter.properties.toml.
        versions("26.1", "26.2")
        vcsVersion = "26.2"
    }
}

rootProject.name = "intelibuild"