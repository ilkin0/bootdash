plugins {
    id("java")
}

group = "com.ilkinmhd"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jakarta.persistence.api)
    implementation(libs.slf4j.api)
    implementation(libs.jackson.databind)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.assertj.core)
    testImplementation(libs.junit.jupiter)
}

configurations.all {
    exclude(group = "org.springframework")
    exclude(group = "org.springframework.boot")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(
        listOf(
            "-Xlint:all",
            "-Werror"
        )
    )
}

tasks.named<Jar>("jar") {
    archiveBaseName.set("bootdash-core")
    manifest {
        attributes(
            "Implementation-Title" to "BootDash Core",
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to "com.ilkinmhd"
        )
    }
}

//publishing {
//    publications {
//        named<MavenPublication>("maven") {
//            artifactId = "bootdash-core"
//
//            pom {
//                name.set("BootDash Core")
//                description.set("Framework-agnostic core domain logic for BootDash admin panel generator")
//            }
//        }
//    }
//}

tasks.test {
    useJUnitPlatform()
}