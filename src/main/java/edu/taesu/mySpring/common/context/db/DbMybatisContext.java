package edu.taesu.mySpring.common.context.db;

import java.io.Serializable;
import java.util.Properties;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DbMybatisContext {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean(name = "dataSource")
	public HikariDataSource getHikariDataSource() throws Exception {
		this.logger.info("dataSource :: init");
		final HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setAutoCommit(true);
		hikariDataSource.setMaximumPoolSize(2);
		hikariDataSource.setMinimumIdle(2);
		hikariDataSource.setConnectionTimeout(30000);
		hikariDataSource.setIdleTimeout(600000);
		hikariDataSource.setMaxLifetime(1800000);
		hikariDataSource.setDataSourceClassName("oracle.jdbc.pool.OracleDataSource");

		final Properties dsProperties = new Properties();
		dsProperties.put("user", "system");
		dsProperties.put("password", "kimteasu2");
		dsProperties.put("serverName", "127.0.0.1");
		dsProperties.put("portNumber", "1521");
		dsProperties.put("databaseName", "orcl");
		dsProperties.put("driverType", "thin");
		hikariDataSource.setDataSourceProperties(dsProperties);
		return hikariDataSource;
	}

	@Bean(name = "sqlSession", destroyMethod = "clearCache")
	@Autowired
	@Primary
	public SqlSessionTemplate getSkmnsSqlSessionTemplate(final ApplicationContext applicationContext) throws Exception {
		return new SqlSessionTemplate(this.getSqlSessionFactoryBean(applicationContext).getObject());
	}

	@Bean(name = "transactionManager")
	@Autowired
	@Primary
	public PlatformTransactionManager getPlatformTransactionManager(final ApplicationContext applicationContext) throws Exception {
		return new DataSourceTransactionManager(this.getHikariDataSource());
	}

	private SqlSessionFactoryBean getSqlSessionFactoryBean(final ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(this.getHikariDataSource());
		sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		sqlSessionFactory.setTypeAliasesPackage("edu.taesu");
		sqlSessionFactory.setTypeAliasesSuperType(Serializable.class);
		sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath*:**/*Mapper.xml"));
		return sqlSessionFactory;
	}
}