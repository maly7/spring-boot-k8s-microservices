import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.cloud:spring-cloud-kubernetes-discovery")

    implementation("com.auth0:java-jwt:3.10.3")

    implementation("org.flywaydb:flyway-core")
    runtimeOnly("mysql:mysql-connector-java")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.github.javafaker:javafaker:1.0.2")

    testImplementation("org.testcontainers:testcontainers:1.14.3")
    testImplementation("org.testcontainers:mysql:1.14.3")
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
    imageName = "com.github.maly7/microservices/${project.name}:latest"
}
