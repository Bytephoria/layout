plugins {
    `java-library`
    `maven-publish`
}

subprojects {
    apply {
        plugin("java-library")
        plugin("maven-publish")
    }

    publishing {
        publications {
            create<MavenPublication>(project.name) {
                from(components["java"])
                groupId = project.group.toString()
                artifactId = project.name
                version = rootProject.version.toString()
            }
        }
    }

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}