import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    kotlin("jvm") version "1.3.72" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.72" apply false
    id("org.springframework.boot") version "2.3.2.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

allprojects {
    repositories {
        jcenter()
    }
    apply(plugin = "io.spring.dependency-management")

    val springCloudVersion by extra("Hoxton.SR6")

    configure<DependencyManagementExtension> {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
        }
    }
}

subprojects {
    version = "1.0"
}
