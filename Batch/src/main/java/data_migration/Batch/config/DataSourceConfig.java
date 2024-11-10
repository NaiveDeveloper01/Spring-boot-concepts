package data_migration.Batch.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {


    @Bean(name = "sourceDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.source")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.target")
    public DataSource targetDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "sourceEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sourceEntityManagerFactory(
            @Qualifier("sourceDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("data_migration.Batch.entities.sourceEntities");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        em.getJpaPropertyMap().put("show-sql", "true");
        em.getJpaPropertyMap().put("hibernate.format_sql", "true");
        return em;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean targetEntityManagerFactory(
            @Qualifier("dataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("data_migration.Batch.entities.targetEntities");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        em.getJpaPropertyMap().put("hibernate.ddl-auto", "update");
        em.getJpaPropertyMap().put("show-sql", "true");
        em.getJpaPropertyMap().put("hibernate.format_sql", "true");
        return em;
    }

    @Bean(name = "sourceTransactionManager")
    public PlatformTransactionManager sourceTransactionManager(
            @Qualifier("sourceEntityManagerFactory") LocalContainerEntityManagerFactoryBean sourceEntityManagerFactory) {
        return new JpaTransactionManager(sourceEntityManagerFactory.getObject());
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager targetTransactionManager(
            LocalContainerEntityManagerFactoryBean targetEntityManagerFactory) {
        return new JpaTransactionManager(targetEntityManagerFactory.getObject());
    }
}
