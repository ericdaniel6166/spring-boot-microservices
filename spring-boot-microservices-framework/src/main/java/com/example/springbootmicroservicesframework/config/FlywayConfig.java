package com.example.springbootmicroservicesframework.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlywayConfig {

    @Value("${spring.flyway.baseline-on-migrate:true}")
    boolean baselineOnMigrate;

    @Value("${spring.flyway.baseline-version}")
    String baselineVersion;

    @Value("${spring.flyway.locations}")
    String locations;

    @Value("${spring.flyway.validate-on-migrate:true}")
    boolean validateOnMigrate;

    @Value("${spring.flyway.table}")
    String table;

    static final String SCHEMA_HISTORY = "schema_history";

    static final String FLYWAY_BASELINE_VERSION_DEFAULT = "0.0";

    static final String FLYWAY_LOCATION_DEFAULT = "classpath:db/migration/";

    @Bean
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(baselineOnMigrate)
                .baselineVersion(MigrationVersion.fromVersion(Optional.ofNullable(baselineVersion).orElse(FLYWAY_BASELINE_VERSION_DEFAULT)))
                .table(Optional.ofNullable(table).orElse(SCHEMA_HISTORY))
                .locations(Optional.ofNullable(locations).orElse(FLYWAY_LOCATION_DEFAULT))
                .validateOnMigrate(validateOnMigrate)
                .load();
    }

    @Bean
    public FlywayMigrationInitializer flywayMigrationInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway);
    }
}
