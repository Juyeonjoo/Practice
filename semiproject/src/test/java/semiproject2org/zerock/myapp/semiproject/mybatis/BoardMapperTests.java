package semiproject2org.zerock.myapp.semiproject.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.zerock.myapp.semiproject.domain.BoardDTO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardMapperTests {

	// 마이바티스의 핵심객체 2가지: (1) SqlSessionFactory (2) SqlSession
	private SqlSessionFactory factory;
	private SqlSession sqlSession;
	
	@BeforeAll
	void beforeAll() throws IOException {
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		
		InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
		
		this.factory = builder.build(is);
		log.info("\t+ this.factory: {}", this.factory);
	}
	
	@BeforeEach
	void beforeEach() {
		// SqlSessionFactory 공장에서, SqlSession이란 핵심객체를 생성
		this.sqlSession = this.factory.openSession();
		log.info("\t1. this.sqlSesssion: {}", this.sqlSession);		
	}//beforeEach()
	
//	@Disabled
	@Test
	@Order(1)
	@DisplayName("테스트1: list 테스트 수행")
	@Timeout(value=10, unit=TimeUnit.MINUTES)
	void list() {
		String namespace = "org.zerock.myapp.semiproject.mapper.BoardMapper";
		String sqlId = "list";
		
		String mappedStatement = namespace + "." + sqlId;
		log.info("\t2. mappedStatement: {}", mappedStatement);
				
		List<BoardDTO> list = this.sqlSession.selectList(mappedStatement);
		list.forEach(log::info);
		
		this.sqlSession.close(); 	// 자원해제
		
	}
}//end class 
