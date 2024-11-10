package data_migration.Batch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "data_migration.Batch.repository.target"
)
public class TargetJpaConfig {
}
