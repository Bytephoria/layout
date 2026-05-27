plugins {
    `java-library`
    id("io.papermc.paperweight.userdev")
    id("com.gradleup.shadow") version ("9.4.1")
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")
    api(project(":layout-bukkit"))
    api(project(":layout-nms"))

}
