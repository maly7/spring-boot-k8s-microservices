import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

plugins {
    id("com.moowork.node")
    id("com.bmuschko.docker-remote-api")
}

tasks.create("buildImage", DockerBuildImage::class) {
    dependsOn(tasks.findByName("yarn_build"))
    dockerFile.set(File("Dockerfile"))
    inputDir.set(File("./"))
    images.add("com.github.maly7/microservices/${project.name}:latest")
}
