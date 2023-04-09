package org.zerock.myapp.semiproject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardUpdateCommand implements BoardCommand{

	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		//retrieve.jsp 에서 넘어온 4개의 파라미터를 얻는다. 
		String num = req.getParameter("num");
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String content = req.getParameter("content");
		
		//BoardDAO 클래스의 update 메소드로 넘긴다.
		BoardDAO dao = new BoardDAO();
		dao.update(num, title, author, content);
		
	}//execute
}//BoardUpdateCommand
