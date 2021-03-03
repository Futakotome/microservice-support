package io.futakotome.MHDatasource.config.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "multiple.mysql", name = "enabled", havingValue = "true")
public class MysqlDatasourcesRouterConfiguration extends AbstractMysqlDatasourcesConfiguration {

    public MysqlDatasourcesRouterConfiguration(MysqlDynamicDatasourceProperties dynamicDatasourceProperties) {
        super(dynamicDatasourceProperties);
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        DynamicDatasource datasource = new DynamicDatasource();
        datasource.setTargetDataSources(new HashMap<Object, Object>() {{
            putAll(mysqlDatasources());
        }});
        datasource.setDefaultTargetDataSource(getFirstDataSource());
        return datasource;
    }

    @Primary
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Primary
    @Bean
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(entityManagerFactory(builder).getObject()).createEntityManager();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        String[] packages = packages();
        log.info("Scanning the jpa entity location:" + Arrays.toString(packages));
        return builder.dataSource(dataSource())
                .packages(packages)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory(builder).getObject()));
    }
}
