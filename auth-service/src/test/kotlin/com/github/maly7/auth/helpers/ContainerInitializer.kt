package com.github.maly7.auth.helpers

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.MapPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.lifecycle.Startables

class ContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        Startables.deepStart(listOf(mysql)).join()
        val env = applicationContext.environment
        env.propertySources.addFirst(MapPropertySource("containers", properties()))
    }

    companion object {
        val mysql: MySQLContainer<*> = MySQLContainer<Nothing>().withReuse(true)

        fun properties() = mapOf(
            "spring.datasource.url" to mysql.getJdbcUrl(),
            "spring.datasource.username" to mysql.getUsername(),
            "spring.datasource.password" to mysql.getPassword()
        )
    }
}
