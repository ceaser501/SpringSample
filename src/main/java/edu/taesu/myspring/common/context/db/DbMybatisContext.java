package edu.taesu.myspring.common.context.db;

import java.io.Serializable;
import java.util.Properties;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DbMybatisContext {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Value("#{jdbcProperties['jdbc.first.driverClassName']}")
    private String driverClassName;
    @Value("#{jdbcProperties['jdbc.first.url']}")
    private String url;
    @Value("#{jdbcProperties['jdbc.first.username']}")
    private String username;
    @Value("#{jdbcProperties['jdbc.first.password']}")
    private String password;
    @Value("#{jdbcProperties['jdbc.first.autoCommit']}")
    private Boolean autoCommit;
    @Value("#{jdbcProperties['jdbc.first.connectionTimeoutMs']}")
    private Long connectionTimeoutMs;
    @Value("#{jdbcProperties['jdbc.first.idleTimeoutMs']}")
    private Long idleTimeoutMs;
    @Value("#{jdbcProperties['jdbc.first.maxLifetimeMs']}")
    private Long maxLifetimeMs;
    @Value("#{jdbcProperties['jdbc.first.minIdle']}")
    private Integer minIdle;
    @Value("#{jdbcProperties['jdbc.first.maxPoolSize']}")
    private Integer maxPoolSize;
    
    @Bean(name = "dataSource")
    public HikariDataSource getHikariDataSource() {
        this.logger.info("dataSource :: init");
        return new HikariDataSource(
                getHikariConfig(this.driverClassName, this.url, this.username, this.password, this.autoCommit, this.connectionTimeoutMs, this.idleTimeoutMs, this.maxLifetimeMs
                        , this.minIdle, this.maxPoolSize));
    }


    public static HikariConfig getHikariConfig(final String driverClassName, final String url, final String username, final String password, final Boolean autoCommit, final Long connectionTimeoutMs,
            final Long idleTimeoutMs, final Long maxLifetimeMs, final Integer minIdle, final Integer maxPoolSize) {
		
//		final HikariDataSource hikariDataSource = new HikariDataSource();
//		hikariDataSource.setAutoCommit(true);
//		hikariDataSource.setMaximumPoolSize(2);
//		hikariDataSource.setMinimumIdle(2);
//		hikariDataSource.setConnectionTimeout(30000);
//		hikariDataSource.setIdleTimeout(600000);
//		hikariDataSource.setMaxLifetime(1800000);
//		//hikariDataSource.setDataSourceClassName("oracle.jdbc.pool.OracleDataSource");
//		hikariDataSource.setDriverClassName("org.jdbcdslog.DriverLoggingProxy");
//		hikariDataSource.setJdbcUrl("jdbc:jdbcdslog:oracle:thin:@127.0.0.1:1521/orcl;targetDriver=oracle.jdbc.pool.OracleDataSource");

//		final Properties dsProperties = new Properties();
//		dsProperties.put("user", "system");
//		dsProperties.put("password", "kimteasu2");
//		dsProperties.put("serverName", "127.0.0.1");
//		dsProperties.put("portNumber", "1521");
//		dsProperties.put("databaseName", "orcl");
//		hikariDataSource.setDataSourceProperties(dsProperties);
//		return hikariDataSource;
//		
		
		
		final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(url);
        // hikariConfig.setUsername(AES.decrypt128(username));
        hikariConfig.setUsername(username);
        // hikariConfig.setPassword(AES.decrypt128(password));
        hikariConfig.setPassword(password);
        hikariConfig.setAutoCommit(autoCommit);
        hikariConfig.setConnectionTimeout(connectionTimeoutMs);
        hikariConfig.setIdleTimeout(idleTimeoutMs);
        hikariConfig.setMaxLifetime(maxLifetimeMs);
        hikariConfig.setMinimumIdle(minIdle);
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        return hikariConfig;
	}

	@Bean(name = "sqlSession", destroyMethod = "clearCache")
	@Autowired
	@Primary
	public SqlSessionTemplate getMySqlSessionTemplate(final ApplicationContext applicationContext) throws Exception {
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