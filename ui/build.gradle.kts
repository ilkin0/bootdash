
plugins {
    id("java-library")
    id("maven-publish")
    id("com.github.node-gradle.node") version "7.1.0"
}

description = "JTE templates, Tailwind CSS, and frontend assets for BootDash admin panel"

//node {
//    version.set("18.19.0")
//    npmVersion.set("10.2.3")
//    ResourceOperation.Type.download.set(true)
//    workDir.set(file("${project.projectDir}"))
//    npmWorkDir.set(file("${project.projectDir}"))
//}
//
//dependencies {
//}
//
//// Frontend build tasks
//tasks.register<com.github.gradle.node.npm.task.NpmTask>("npmInstall") {
//    description = "Install Node.js dependencies"
//    args.set(listOf("install"))
//    inputs.file("package.json")
//    inputs.file("package-lock.json")
//    outputs.dir("node_modules")
//}
//
//tasks.register<com.github.gradle.node.npm.task.NpmTask>("buildCSS") {
//    description = "Build Tailwind CSS"
//    dependsOn("npmInstall")
//    args.set(listOf("run", "build:css"))
//
//    inputs.files("src/styles/main.css", "tailwind.config.js")
//    inputs.dir("src/templates")
//    outputs.file("dist/bootdash.css")
//}
//
//tasks.register<com.github.gradle.node.npm.task.NpmTask>("buildJS") {
//    description = "Build JavaScript assets"
//    dependsOn("npmInstall")
//    args.set(listOf("run", "build:js"))
//
//    inputs.dir("src/js")
//    outputs.file("dist/bootdash.js")
//}
//
//// Copy built assets to resources directory
//tasks.register<Copy>("copyFrontendAssets") {
//    description = "Copy built frontend assets to resources"
//    dependsOn("buildCSS", "buildJS")
//
//    from("dist")
//    into("${buildDir}/resources/main/static/bootdash")
//
//    inputs.dir("dist")
//    outputs.dir("${buildDir}/resources/main/static/bootdash")
//}
//
//// Copy templates to resources directory
//tasks.register<Copy>("copyTemplates") {
//    description = "Copy JTE templates to resources"
//
//    from("src/templates")
//    into("${buildDir}/resources/main/templates")
//
//    inputs.dir("src/templates")
//    outputs.dir("${buildDir}/resources/main/templates")
//}
//
//// Ensure frontend assets are built before jar
//tasks.named("processResources") {
//    dependsOn("copyFrontendAssets", "copyTemplates")
//}
//
//tasks.named("compileJava") {
//    // Skip Java compilation since this module has no Java sources
//    enabled = false
//}
//
//// Clean task to remove frontend build artifacts
//tasks.named("clean") {
//    doLast {
//        delete("dist")
//        delete("node_modules")
//        delete(".npm")
//    }
//}
//
//tasks.named<Jar>("jar") {
//    archiveBaseName.set("bootdash-ui")
//
//    // Include built assets and templates
//    from("${buildDir}/resources/main") {
//        include("**/*")
//    }
//
//    manifest {
//        attributes(
//            "Implementation-Title" to "BootDash UI",
//            "Implementation-Version" to project.version,
//            "Implementation-Vendor" to "com.ilkinmhd"
//        )
//    }
//
//    dependsOn("copyFrontendAssets", "copyTemplates")
//}
//
//// Publishing configuration specific to UI
//publishing {
//    publications {
//        named<MavenPublication>("maven") {
//            artifactId = "bootdash-ui"
//
//            pom {
//                name.set("BootDash UI")
//                description.set("JTE templates, Tailwind CSS, and frontend assets for BootDash admin panel")
//            }
//        }
//    }
//}
//
//// Development tasks
//tasks.register<com.github.gradle.node.npm.task.NpmTask>("dev") {
//    description = "Start development server with CSS watching"
//    args.set(listOf("run", "dev"))
//}
//
//tasks.register<com.github.gradle.node.npm.task.NpmTask>("watchCSS") {
//    description = "Watch CSS changes and rebuild"
//    args.set(listOf("run", "watch:css"))
//}