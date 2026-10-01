plugins {
    id("dev.kikugie.stonecutter")

    // Declared (not applied) so loom-back-compat can pick the right Loom variant for
    // the active Minecraft version. Loom is applied through loom-back-compat itself.
    id("net.fabricmc.fabric-loom") version "1.18.2" apply false
}

// Version used by default when running Gradle tasks without a `-Pstonecutter.version`.
// Change this to build/test the other target.
stonecutter active "26.2"