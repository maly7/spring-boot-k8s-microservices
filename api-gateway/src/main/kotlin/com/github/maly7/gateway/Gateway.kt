package com.github.maly7.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class Gateway

fun main(args: Array<String>) {
    runApplication<Gateway>(*args)
}
