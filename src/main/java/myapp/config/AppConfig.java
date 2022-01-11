package myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(value = "myapp")
@PropertySource(value = "classpath:db.properties")
public class AppConfig {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(environment.getProperty("db.driver"));
        driverManagerDataSource.setUrl(environment.getProperty("db.url"));
        driverManagerDataSource.setUsername(environment.getProperty("db.username"));
        driverManagerDataSource.setPassword(environment.getProperty("db.password"));
        return driverManagerDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(getDataSource());

        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));


        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        localContainerEntityManagerFactoryBean.setPackagesToScan(new String[] {"myapp.model"});
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager getPlatformTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(getLocalContainerEntityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }
}
