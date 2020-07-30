import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-kubernetes-discovery")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    implementation("com.auth0:java-jwt:3.10.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.github.javafaker:javafaker:1.0.2")
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
    imageName = "com.github.maly7/microservices/${project.name}:latest"
}
