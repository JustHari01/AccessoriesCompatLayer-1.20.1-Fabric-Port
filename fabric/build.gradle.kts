import io.wispforest.helpers.Utils

plugins {
    id("multiloader-base")
    id("multiloader-publishing")
    id("com.gradleup.shadow") version "9.2.2"
}

architectury {
    platformSetupLoomIde()
    fabric {
        platformPackage = "fabric"
    }
}

val common by configurations.creating
val shadowCommon by configurations.creating

configurations {
    common
    shadowCommon // Don't use shadow from the shadow plugin since it *excludes* files.
    compileClasspath { extendsFrom(common) }
    runtimeClasspath { extendsFrom(common) }
    //"developmentFabric" { extendsFrom(common) }
}

repositories {
    mavenCentral()
    gradlePluginPortal()

    // oωo (owo-lib) and Endec Lib
    maven("https://maven.wispforest.io/releases")
    // --

    // REI Item Viewer
    maven("https://maven.shedaniel.me/")
    maven("https://maven.architectury.dev/")
    // --

    // EMI Item Viewer
    maven("https://maven.terraformersmc.com/releases")
    // --

    maven("https://maven.ladysnake.org/releases")

    // Mixin Squard
    maven("https://maven.bawnorton.com/releases")
    maven ("https://maven.florens.be/releases")
}

dependencies {
    "common"(project(":common", "namedElements")) { this.setTransitive(false) }
    "shadowCommon"(project(":common", "transformProductionFabric")) { this.setTransitive(false) }

    // Core Libs
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    // --

    // General Libs
    modCompileOnly(libs.modmenu)
    modLocalRuntime(libs.modmenu)
    //--

    modImplementation(libs.trinkets)

    modImplementation(libs.accessories.fabric)

    // Accessories 1.20.1 requires Cloth Config at runtime. Keep it in the
    // development client classpath without bundling it into this mod.
    modLocalRuntime("maven.modrinth:9s6osm5g:11.1.136+fabric")

    // The official Trinkets Modrinth artifact does not declare its Cardinal
    // Components dependency in Modrinth's version metadata.
    modLocalRuntime("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:5.2.0")

    annotationProcessor(libs.mixin.squared.fabric)
    implementation(libs.mixin.squared.fabric)
    include(libs.mixin.squared.fabric)

    // Compatibility integrations are optional. Keep them out of the default
    // dev classpath so the 1.20.1 port does not resolve 1.21-only test mods.
}

loom {
    accessWidenerPath = file("src/main/resources/${rootProject.property("mod_id")}-fabric.accesswidener")
    runs (Utils.getSetupRunsAction(project))
}

tasks.shadowJar {
    exclude("architectury.common.json")
    from(rootProject.file("LICENSE"))

    configurations = listOf(project.configurations["shadowCommon"])
    archiveClassifier.set("dev-shadow")
}

tasks.remapJar {
    injectAccessWidener = true
    inputFile.set(tasks.shadowJar.get().archiveFile)
    dependsOn(tasks.shadowJar)
    archiveClassifier.set("")
}

tasks.sourcesJar {
    val commonSources = project(":common").tasks.sourcesJar
    dependsOn(commonSources)
    from(commonSources.get().archiveFile.map { zipTree(it) })
    from(rootProject.file("LICENSE"))
    from(rootProject.file("README.md"))
}

shadow {
    addShadowVariantIntoJavaComponent = false
}
