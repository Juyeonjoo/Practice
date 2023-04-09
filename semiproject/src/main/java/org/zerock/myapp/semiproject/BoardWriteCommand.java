package org.zerock.myapp.semiproject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardWriteCommand implements BoardCommand {
	
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		//write.jsp 의 입력 파라미터값 얻기
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String content = req.getParameter("content");
		
		//BoardDA 클래스의 write메소드를 이용하여 파라미터값 넘김 
		BoardDAO dao = new BoardDAO();
		
		dao.write(title,author,content);
		
	}//execute
}//BoardWriteCommand
