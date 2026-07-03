plugins {
    `java-library`
    id("com.gradleup.shadow") version ("9.4.1")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation(project(":layout-bukkit"))

    implementation(project(":layout-nms-adapter-1_21_11"))
    implementation(project(":layout-nms-adapter-26_1"))
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(25))

tasks.jar {
    enabled = false
}