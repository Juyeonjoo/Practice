package org.zerock.myapp.semiproject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.zerock.myapp.semiproject.domain.BoardDTO;
import org.zerock.myapp.semiproject.mapper.BoardMapper;

public class BoardListCommand implements BoardCommand{
	
	private SqlSessionFactory factory;
	private SqlSession sqlSession;
	
	SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	
	
	public void execute(HttpServletRequest req, HttpServletResponse res) throws IOException {

		
	InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
			
	this.factory = builder.build(is);
	this.sqlSession = this.factory.openSession();
	BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
	
	List<BoardDTO> list = mapper.list(); //비지니스 로직의 수행 결과 데이터 = 모델 
	
	//우리가 개발할 때는 직접 공유속성에 바인딩하는데 스프링에서는 모델상자를 제공해서 
	//거기에 넣어서 전달 (모델이란 상자에 넣는 것만으로 이미 전달되는 것) 
	
	//비지니스 데이터를 뷰에 전달하는 역할 
	
	//리턴받은 데이터는 list.jsp에서 보여주기 위하여 request scope에 바인딩 
	req.setAttribute("list", list);
		
	}

}//BoardListCommand
