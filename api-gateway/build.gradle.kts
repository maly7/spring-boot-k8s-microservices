import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-kubernetes-discovery")
    implementation("org.springframework.cloud:spring-cloud-kubernetes-config")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
    imageName = "com.github.maly7/microservices/${project.name}:latest"
}
