import io.papermc.paperweight.userdev.ReobfArtifactConfiguration

plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version ("2.0.0-beta.21")
}

dependencies {
    // Only used as a compile-time source of stable NMS types (AbstractContainerMenu, MenuType,
    // Inventory, ServerPlayer, MenuProvider, ...) that this module needs but never touches the part
    // that actually differs across versions (AbstractContainerMenu#clicked's parameter type). Any
    // dev bundle in the supported range works here; 1.21.11 is picked because it has published
    // reobf mappings, unlike some newer builds.
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")
    api(project(":layout-api"))
    api(project(":item-api"))
}

paperweight {
    // This library only targets modern Paper (paper-plugin.yml), which loads mojang-mapped plugins
    // directly, so nothing here should ever be spigot-reobfuscated.
    reobfArtifactConfiguration.set(ReobfArtifactConfiguration.MOJANG_PRODUCTION)
}

// paperweight-userdev unconditionally points the apiElements/runtimeElements variants it publishes
// (and, by extension, the `java` component published to Maven) at reobfJar's output. This library
// is meant to be consumed as a plain jar, not reobfuscated, so the Maven publication is repointed
// at the plain `jar` task instead.
publishing.publications.named<MavenPublication>(project.name) {
    artifacts.clear()
    artifact(tasks.named("jar"))
}
