plugins {
    id("com.gradleup.shadow") version ("9.1.0")
    id("de.eldoria.plugin-yml.paper") version ("0.8.0")
}

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    api(project(":layout"))
    api(project(":item"))
}

paper {
    name = rootProject.name
    main = "${rootProject.group}.layout.plugin.PaperPlugin"
    description = "Example plugin demonstrating how to create customizable inventories using the 'Layout'."
    version = rootProject.version.toString()
    author = "Bytephoria"
    website = "https://bytephoria.team"
    apiVersion = "1.20"
}

tasks.shadowJar {
    archiveBaseName.set("${rootProject.name}-${project.name}")
    archiveClassifier.set("all")
}