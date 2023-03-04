plugins {
    id("java")
    id("java-library")
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "net.polar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.mattmalec.com/repository/releases")
}

dependencies {
    api("com.mattmalec:Pterodactyl4J:2.BETA_140")
    compileOnly("net.kyori:adventure-api:4.12.0")
    implementation("net.kyori:adventure-text-minimessage:4.12.0")
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")

    group = "net.polar"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://repo.mattmalec.com/repository/releases")
    }

    dependencies {
        implementation(rootProject)
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"
        }
        shadowJar {
            archiveClassifier.set("")
            archiveFileName.set("PterActor-${project.name}") // Minestom / Paper
        }
    }

}