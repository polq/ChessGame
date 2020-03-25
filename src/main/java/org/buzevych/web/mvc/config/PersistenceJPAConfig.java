package org.buzevych.web.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("org.buzevych.web.mvc")
@PropertySource("classpath:db.properties")
public class PersistenceJPAConfig {

  @Autowired Environment environment;

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource());
    factory.setPackagesToScan("org.buzevych.web.mvc");

    JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    factory.setJpaVendorAdapter(jpaVendorAdapter);
    factory.setJpaProperties(additionalHibernateProperties());

    return factory;
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(
        Objects.requireNonNull(environment.getProperty("MYSQL_DB_DRIVER")));
    dataSource.setUrl(environment.getProperty("MYSQL_DB_URL"));
    dataSource.setUsername(environment.getProperty("MYSQL_DB_USERNAME"));
    dataSource.setPassword(environment.getProperty("MYSQL_DB_PASSWORD"));
    return dataSource;
  }

  @Bean
  public PlatformTransactionManager manager(EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  Properties additionalHibernateProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
    properties.setProperty("hibernate.hbm2ddl.auto", "update");
    return properties;
  }
}
