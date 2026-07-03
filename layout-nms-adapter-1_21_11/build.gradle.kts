plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version ("2.0.0-beta.21")
}

dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")
    api(project(":layout-nms-common"))
}