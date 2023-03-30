package ru.javarush.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Enumerated;
import org.hibernate.cfg.Environment;
import org.springframework.cglib.proxy.Factory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class AppConfig {
    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("ru.javarush.entity");
        sessionFactoryBean.setHibernateProperties(properties());
        return sessionFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/todo");
        dataSource.setUsername("root");
        dataSource.setPassword("postgres");
        dataSource.setMaximumPoolSize(10);
        return dataSource;
    }

    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }
}
