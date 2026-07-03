plugins {
    // Lets Gradle auto-provision the JDK 21/25 toolchains this build needs (e.g. on CI hosts like
    // JitPack that only ship a single preinstalled JDK).
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "layout"

include(
    "common",
    "item-api",
    "bukkit-item",
    "layout-api",
    "layout-bukkit",
    "layout-nms-common",
    "layout-nms-adapter-1_21_11",
    "layout-nms-adapter-26_1",
    "example-plugin"
)
