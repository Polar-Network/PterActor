repositories {
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}