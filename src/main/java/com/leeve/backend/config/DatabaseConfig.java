package com.leeve.backend.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.leeve.backend.entity")
@EnableJpaRepositories("com.leeve.backend.repository")
public class DatabaseConfig {
}
