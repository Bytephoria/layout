plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version ("2.0.0-beta.21")
}

dependencies {
    paperweight.paperDevBundle("26.1.2.build.+")
    api(project(":layout-nms-common"))
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}