plugins {
    // Applies the correct Loom variant based on the active Minecraft version
    id("dev.kikugie.loom-back-compat")
    id("maven-publish")
}

version = "${property("mod.version")}+${sc.current.version}"
base.archivesName = property("mod.id") as String

// Stonecutter would default this to the mod id; the real Maven group lives in the toml.
group = property("mod.group") as String

// Every supported Minecraft version (26.1+) requires Java 25.
val requiredJava = JavaVersion.VERSION_25

dependencies {
    minecraft("com.mojang:minecraft:${sc.current.version}")
    // 26.1+ ships unobfuscated, so this is a no-op here. It only matters if an
    // older Minecraft version is added to `stonecutter { versions(...) }`.
    loomx.applyMojangMappings()

    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
}

loom {
    splitEnvironmentSourceSets()

    mods {
        register("intelibuild") {
            // `client` is created at runtime by splitEnvironmentSourceSets(), so it has
            // no type-safe accessor and has to be looked up by name.
            sourceSet(sourceSets["main"])
            sourceSet(sourceSets["client"])
        }
    }

    runConfigs.all {
        preferGradleTask = true
        generateRunConfig = true
        runDirectory = rootProject.file("run") // Shares the run directory between versions
    }
}

fabricApi {
    configureDataGeneration {
        client.set(true)
    }
}

java {
    withSourcesJar()
    sourceCompatibility = requiredJava
    targetCompatibility = requiredJava

    toolchain {
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion.toInt())
    }
}

tasks {
    // withType() so the client source set (processClientResources) is covered too
    withType<ProcessResources>().configureEach {
        fun MutableMap<String, String>.register(key: String, value: String) {
            inputs.property(key, value)
            set(key, value)
        }

        val props = buildMap {
            register("id", sc.properties["mod.id"])
            register("name", sc.properties["mod.name"])
            register("version", project.version.toString())
            register("minecraft", sc.properties["mod.mc_compat"])
            register("loader", Regex("\\d+\\.\\d+").find(sc.properties.get<String>("deps.fabric_loader"))!!.value)
        }

        filesMatching("fabric.mod.json") { expand(props) }
        filesMatching("*.mixins.json") { expand("java" to "JAVA_${requiredJava.majorVersion}") }
    }

    // Includes the license file in the built mod
    withType<Jar> {
        val modId = project.property("mod.id") as String
        inputs.property("mod_id", modId)
        from(rootProject.file("LICENSE")) { rename { "$it-$modId" } }
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds the mod jars and copies the results to build/libs/{mod version}/"

        val modVersion = project.property("mod.version") as String
        inputs.property("version", modVersion)
        // loomx.mod(Sources)Jar returns the jar task for the applied Loom variant
        from(loomx.modJar.flatMap { it.archiveFile }, loomx.modSourcesJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs/$modVersion"))
    }
}

// configure the maven publication
publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = property("mod.id") as String
            from(components["java"])
        }
    }
    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
}