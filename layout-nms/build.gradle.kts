plugins {
    `java-library`
    id("com.gradleup.shadow")
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")
    api(project(":layout-api"))
    api(project(":item-api"))
}
