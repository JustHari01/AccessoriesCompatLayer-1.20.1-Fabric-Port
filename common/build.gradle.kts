import io.wispforest.helpers.Extensions.fabricModule

plugins {
    id("multiloader-mojmap")
    id("multiloader-publishing")
}

architectury {
    common((rootProject.property("enabled_platforms") as String).split(","))
}

loom {
    val awPath = "src/main/resources/${rootProject.property("mod_id")}.accesswidener"
    val awFile = file(awPath);

    if (!awFile.exists()) {
        throw IllegalStateException("Unable to locate the given AccessWidener File at the given path: $awPath")
    }

    accessWidenerPath = awFile;

    mixin {
        defaultRefmapName = "${rootProject.property("mod_id")}-common-common-refmap.json"
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()

    // Fabric Loader Lib
    maven("https://maven.fabricmc.net/")
    //--

    // REI Item Viewer
    maven("https://maven.shedaniel.me/")
    maven("https://maven.architectury.dev/")
    // --

    // EMI Item Viewer
    maven("https://maven.terraformersmc.com/releases")
    // --

    maven("https://maven.terraformersmc.com/")
    maven("https://maven.ladysnake.org/releases")

    maven("https://maven.theillusivec4.top/")

    // Mixin Squard
    maven("https://maven.bawnorton.com/releases")
}

dependencies {
    // Core Libs
    modImplementation(libs.fabric.loader)
    compileOnly(libs.mixin.extras.common)
    annotationProcessor(libs.mixin.extras.common)
    // --

    fabricModule(this::modCompileOnlyApi, "fabric-api-base")
    // --

    modCompileOnly(libs.accessories.fabric)

    modCompileOnly(libs.trinkets)
    modCompileOnly("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:5.2.0")
    fabricModule(this::modCompileOnly, "fabric-resource-loader-v0", "fabric-events-interaction-v0")

    annotationProcessor(libs.mixin.squared.common)
    implementation(libs.mixin.squared.common)
}

sourceSets {
    main {
        java {
            exclude("io/wispforest/accessories_compat/curios/**")
            exclude("io/wispforest/accessories_compat/mixin/AccessoriesForgeMixin.java")
            exclude("io/wispforest/accessories_compat/mixin/AccessoriesNetworkingMixin.java")
            exclude("io/wispforest/accessories_compat/mixin/client/AccessoriesNetworkingMixin.java")
            exclude("io/wispforest/accessories_compat/mixin/client/AccessoriesExperimentalScreenMixin.java")
            exclude("io/wispforest/accessories_compat/mixin/owo/ConfigWrapperMixin.java")
            exclude("io/wispforest/accessories_compat/networking/AccessoriesCompatNetworking.java")
            exclude("io/wispforest/accessories_compat/trinkets/mixin/TrinketsAttributeModifiersComponentMixin.java")
            exclude("io/wispforest/accessories_compat/trinkets/mixin/events/TrinketEquipCallbackMixin.java")
            exclude("io/wispforest/accessories_compat/trinkets/mixin/events/TrinketUnequipCallbackMixin.java")
        }
        resources.srcDirs.add(File("src/generated"))
    }
}
