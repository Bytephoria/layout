plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21" apply false
    id("com.gradleup.shadow") version "9.4.1" apply false
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

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}