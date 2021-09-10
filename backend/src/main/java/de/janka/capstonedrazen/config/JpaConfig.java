package de.janka.capstonedrazen.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = "de.janka.capstonedrazen.model")
@EnableJpaRepositories(basePackages = "de.janka.capstonedrazen.repo")
@EnableTransactionManagement
public class JpaConfig {
}
