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
    // Emit Java 21 bytecode so consumers on older JDKs (e.g. the 1.21.11 adapter, or a plugin that
    // only ends up loading this adapter on newer servers) can still compile/load these classes.
    disableAutoTargetJvm()
}

tasks.compileJava {
    options.release.set(21)
}

// See layout-nms-common/build.gradle.kts: paperweight-userdev wires the published `java` component
// to reobfJar's output, which this library doesn't want, and Paper 26.1.2 has no published reobf
// mappings anyway. Publish the plain jar instead.
publishing.publications.named<MavenPublication>(project.name) {
    artifacts.clear()
    artifact(tasks.named("jar"))
}