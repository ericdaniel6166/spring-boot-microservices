package com.example.springbootmicroservicesframework.config.flyway;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.flyway.enabled", havingValue = "true")
public class FlywayConfig {

    static final String SCHEMA_HISTORY = "schema_history";

    static final String FLYWAY_BASELINE_VERSION_DEFAULT = "0.0";

    static final String FLYWAY_LOCATION_DEFAULT = "classpath:db/migration/";

    final FlywayProperties flywayProperties;

    @Bean
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(flywayProperties.getBaselineOnMigrate())
                .baselineVersion(MigrationVersion.fromVersion(
                        Optional.ofNullable(flywayProperties.getBaselineVersion())
                                .orElse(FLYWAY_BASELINE_VERSION_DEFAULT)))
                .table(Optional.ofNullable(flywayProperties.getTable())
                        .orElse(SCHEMA_HISTORY))
                .locations(Optional.ofNullable(flywayProperties.getLocations())
                        .orElse(FLYWAY_LOCATION_DEFAULT))
                .validateOnMigrate(flywayProperties.getValidateOnMigrate())
                .load();
    }

    @Bean
    public FlywayMigrationInitializer flywayMigrationInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway);
    }
}
