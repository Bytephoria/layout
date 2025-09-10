plugins {
    `java-library`
    `maven-publish`
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    publishing {
        publications {
            create<MavenPublication>(project.name) {
                from(components["java"])

                groupId = "${project.group}.${rootProject.name}"
                artifactId = project.name
                version = rootProject.version.toString()
            }
        }

        repositories {
            maven {
                name = "bytephoriaRepository"
                url = uri("https://repo.bytephoria.team/releases")

                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}