package by.karpovich.security.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Configuration
public class PostgresTestConfiguration {
    private final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:12.9");

    @Bean
    public DataSource dataSource() {
        postgreSQLContainer.start();
        return DataSourceBuilder.create()
                .password(postgreSQLContainer.getPassword())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .build();
    }
}
