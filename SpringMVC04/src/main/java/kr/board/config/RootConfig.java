package kr.board.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = {"kr.board.mapper"})	//매퍼가 여러개일경우 , 찍고 써주면됨
@PropertySource({ "classpath:persistence-mysql.properties"})	/*외부의 properties 파일을 불러옴
 		src/main/resources -> 마우스오른쪽 -> File ->  persistence-mysql.properties 명으로 생성
		ㄴ파일내용에 *공백*있으면 안됨 	*/
public class RootConfig {	//database 설정
	
	@Autowired
	private Environment env;	//properties 참조하려면 Environment 클래스가 필요해서 임포트해줌

	@Bean	//빈으로 자동생성
	public DataSource myDataSource() {	//sql
		HikariConfig hikariConfig=new HikariConfig();	//히카리
		hikariConfig.setDriverClassName(env.getProperty("jdbc.driver"));
		hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
		hikariConfig.setUsername(env.getProperty("jdbc.user"));
		hikariConfig.setPassword(env.getProperty("jdbc.password"));
		
		HikariDataSource myDataSource=new HikariDataSource(hikariConfig);
		return myDataSource;
	}	
	/*	아래 SqlSessionFactoryBean이 HikariDataSource을 받아서 mybatis용으로 "커넥션풀(둘을 내부적으로 연결)" 생성
			SqlSessionFactory로 넘겨주면 crud를 내부적으로 가능하게됨 */
	@Bean
	public SqlSessionFactory sessionFactory() throws Exception{
		SqlSessionFactoryBean sessionFactory=new SqlSessionFactoryBean();
		sessionFactory.setDataSource(myDataSource());
		return (SqlSessionFactory)sessionFactory.getObject();
	}

	
}
