import io.gitlab.arturbosch.detekt.Detekt

val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val postgres_version: String by project
val ktor_version: String by project
val mockk_version: String by project
val koin_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    id("io.ktor.plugin") version "3.1.0"
}

group = "com.yugyd.quiz"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config.setFrom("$projectDir/detekt/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
    baseline = file("$projectDir/detekt/baseline.xml") // a way of suppressing issues before introducing detekt
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        txt.required.set(true)

        sarif.required.set(false)
        xml.required.set(false)
        md.required.set(false)
    }
}

ktor {
    fatJar {
        archiveFileName.set("quiz-platform.jar")
    }
}

dependencies {
    // Features
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-resources-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    // Validation
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")
    // Performance
    implementation("io.ktor:ktor-server-rate-limit:$ktor_version")
    // Headers
    implementation("io.ktor:ktor-server-default-headers:$ktor_version")
    implementation("io.ktor:ktor-server-caching-headers:$ktor_version")
    // Monitoring
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")
    implementation("io.ktor:ktor-server-metrics:$ktor_version")
    // Client
    implementation("io.ktor:ktor-client-content-negotiation-jvm:$ktor_version")

    // Core
    implementation("io.ktor:ktor-server-config-yaml-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")

    // Database
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.postgresql:postgresql:$postgres_version")

    // Serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Error handling
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")

    // SSL
    implementation("io.ktor:ktor-network-tls-certificates:$ktor_version")

    // DI
    implementation(platform("io.insert-koin:koin-bom:$koin_version"))
    implementation("io.insert-koin:koin-core")
    implementation("io.insert-koin:koin-ktor")

    // Test
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("io.insert-koin:koin-test-junit4")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
