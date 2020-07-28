package com.github.maly7.registry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class Registry

fun main(args: Array<String>) {
    runApplication<Registry>(*args)
}
