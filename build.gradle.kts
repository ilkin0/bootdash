plugins {
    id("java-library") apply true
}

val javaVersion: String by project

allprojects {
    group = "com.ilkinmhd"
    version = "0.1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {

    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.valueOf("VERSION_$javaVersion")
        targetCompatibility = JavaVersion.valueOf("VERSION_$javaVersion")

        withSourcesJar()
        withJavadocJar()
    }

    dependencies {

//        implementation("org.slf4j:slf4j-api:2.0.16")
//
//        testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
//        testImplementation("org.mockito:mockito-core:5.8.0")
//        testImplementation("org.mockito:mockito-junit-jupiter:5.8.0")
//        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    // Test configuration
    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
}
