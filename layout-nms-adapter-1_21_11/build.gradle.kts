plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version ("2.0.0-beta.21")
}

dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")
    api(project(":layout-nms-common"))
}

// See layout-nms-common/build.gradle.kts: paperweight-userdev wires the published `java` component
// to reobfJar's output, which this library doesn't want. Publish the plain jar instead.
publishing.publications.named<MavenPublication>(project.name) {
    artifacts.clear()
    artifact(tasks.named("jar"))
}