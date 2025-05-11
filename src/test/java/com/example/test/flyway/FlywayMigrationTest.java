package com.example.test.flyway;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

class FlywayMigrationTest {

    @Test
    void testMigration() {
        MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
                .withUsername("test")
                .withPassword("test")
                .withDatabaseName("testdb");
        mysqlContainer.start();

        Flyway flyway = Flyway.configure()
                .dataSource(mysqlContainer.getJdbcUrl(), mysqlContainer.getUsername(), mysqlContainer.getPassword())
                .locations("classpath:db/migration")
                .load();
        
        System.out.println("Number of migrated files: " + flyway.info().all().length);
    }
}
