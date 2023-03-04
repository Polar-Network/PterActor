plugins {
    id("java")
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
    implementation("com.mattmalec:Pterodactyl4J:v2.BETA_140")
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")

    group = "net.polar"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
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