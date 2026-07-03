plugins {
    `java-library`
    `maven-publish`
}

// example-plugin is a demonstrative consumer, not a library artifact: it must never be published.
val publishedProjects = subprojects.filter { it.name != "example-plugin" }

subprojects {
    apply {
        plugin("java-library")
    }

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

configure(publishedProjects) {
    apply {
        plugin("maven-publish")
    }

    publishing {
        publications {
            create<MavenPublication>(project.name) {
                from(components["java"])

                groupId = "${project.group}.${rootProject.name}"
                artifactId = project.name
                version = rootProject.version.toString()
            }
        }
    }
}