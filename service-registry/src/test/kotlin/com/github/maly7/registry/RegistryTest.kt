package com.github.maly7.registry

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [Registry::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class RegistryTest {
    @Test
    fun `the context loads`() {
    }
}
