package com.github.maly7.auth.helpers

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.lifecycle.Startables

class ContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        Startables.deepStart(listOf(postgres)).join()
        val env = applicationContext.environment
        env.propertySources.addFirst(MapPropertySource("containers", properties()))
    }

    companion object {
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer<Nothing>().withReuse(true)

        fun properties() = mapOf(
            "spring.datasource.url" to postgres.getJdbcUrl(),
            "spring.datasource.username" to postgres.getUsername(),
            "spring.datasource.password" to postgres.getPassword()
        )
    }
}
