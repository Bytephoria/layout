pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "layout"

include(
    "common",
    "item-api",
    "bukkit-item",
    "layout-api",
    "layout-bukkit",
    "layout-nms",
    "example-plugin"
)
