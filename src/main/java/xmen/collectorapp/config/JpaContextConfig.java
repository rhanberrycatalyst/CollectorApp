package xmen.collectorapp.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { SettingsConfig.PROJECT_ROOT_PACKAGE })
@EnableTransactionManagement
public class JpaContextConfig {

	@Bean
	public PersistenceAnnotationBeanPostProcessor getPersistenceAnnotationBeanPostProcessor() {
		PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor = new PersistenceAnnotationBeanPostProcessor();
		return persistenceAnnotationBeanPostProcessor;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() throws SQLException {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		localContainerEntityManagerFactoryBean
				.setPackagesToScan(new String[] { SettingsConfig.PROJECT_ROOT_PACKAGE });
		localContainerEntityManagerFactoryBean.setPersistenceUnitName("punit");
		localContainerEntityManagerFactoryBean
				.setDataSource(getDriverManagerDataSource());
		localContainerEntityManagerFactoryBean
				.setJpaVendorAdapter(getHibernateJpaVendorAdapter());

		Map<String, Object> jpaPropertyMap = new HashMap<String, Object>();
		jpaPropertyMap.put("hibernate.dialect",
				SettingsConfig.HIBERNATE_DIALECT);
		jpaPropertyMap.put("hibernate.hbm2ddl.auto", SettingsConfig.HIBERNATE_SCHEMA_DLL_STRATEGY);
		jpaPropertyMap.put("hibernate.format_sql", SettingsConfig.HIBERNATE_FORMAT_SQL_IN_CONSOLE);
		jpaPropertyMap.put("hibernate.show_sql", SettingsConfig.HIBERNATE_SHOW_SQL_IN_CONSOLE);
		

		localContainerEntityManagerFactoryBean
				.setJpaPropertyMap(jpaPropertyMap);

		return localContainerEntityManagerFactoryBean;
	}

	@Bean
	public DriverManagerDataSource getDriverManagerDataSource() throws SQLException {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(SettingsConfig.DATABASE_DRIVER_CLASS_NAME);
		driverManagerDataSource
				.setUrl(SettingsConfig.DATABASE_CONNECTION_URL);
		driverManagerDataSource.setUsername(SettingsConfig.DATABASE_LOGIN_USERNAME);
		driverManagerDataSource.setPassword(SettingsConfig.DATABASE_LOGIN_PASSWORD);
		return driverManagerDataSource;
	}

	@Bean
	public HibernateJpaVendorAdapter getHibernateJpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

		hibernateJpaVendorAdapter.setShowSql(true);
		return hibernateJpaVendorAdapter;
	}

	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager(
			EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

		return jpaTransactionManager;
	}

}
