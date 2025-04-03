package reactive_backend.technology.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;
import reactive_backend.technology.application.jpa.adapter.TechnologyJpaAdapter;
import reactive_backend.technology.application.jpa.mapper.ITechnologyEntityMapper;
import reactive_backend.technology.application.jpa.repository.ITechnologyRepository;
import reactive_backend.technology.domain.api.ITechnologyServicePort;
import reactive_backend.technology.domain.spi.ITechnologyPersistencePort;
import reactive_backend.technology.domain.usecase.TechnologyCase;

@Configuration
@AllArgsConstructor
public class BeanConfiguration {
    private final ITechnologyEntityMapper technologyEntityMapper;
    private final ITechnologyRepository technologyRepository;

    @Bean
    public ITechnologyServicePort technologyServicePort() {
        return new TechnologyCase( technologyPersistencePort());
    }
    @Bean
    public ITechnologyPersistencePort technologyPersistencePort() {
        return new TechnologyJpaAdapter(technologyRepository,technologyEntityMapper);
    }
    @Bean
    public ApplicationRunner initializer(DatabaseClient client) {
        return args -> client.sql("""
        CREATE TABLE IF NOT EXISTS technology_entity (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            description TEXT,
            CONSTRAINT uk_technology_name UNIQUE (name)
        )
        """).fetch().rowsUpdated().subscribe();
    }


}
