plugins {
    kotlin("jvm") version Dependencies.Versions.kotlinPluginVersion
    id("io.ktor.plugin") version Dependencies.Versions.ktorPluginVersion
    id("org.jetbrains.kotlin.plugin.serialization") version Dependencies.Versions.kotlinSerializationPluginVersion
    id("com.github.johnrengelman.shadow") version Dependencies.Versions.shadowJarVersion
    application
}

group = AppConfig.group
version = AppConfig.versionName

repositories {
    maven {
        mavenCentral()
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.ktor:ktor-serialization-kotlinx-json")

    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-call-logging")
    implementation("io.ktor:ktor-server-cors")
    implementation("io.ktor:ktor-server-default-headers")

    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-client-logging")
    implementation("io.ktor:ktor-client-cio")
    implementation("io.ktor:ktor-client-serialization")
    implementation("io.ktor:ktor-client-content-negotiation")

    implementation("ch.qos.logback:logback-classic:${Dependencies.Versions.logbackVersion}")

    implementation("com.apurebase:kgraphql:${Dependencies.Versions.kGraphQlVersion}")
    implementation("com.apurebase:kgraphql-ktor:${Dependencies.Versions.kGraphQlVersion}")
}


application {
    mainClass.set("com.lange.biblioteque.ApplicationKt")
}



