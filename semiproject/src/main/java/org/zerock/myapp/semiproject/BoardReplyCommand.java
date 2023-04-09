package org.zerock.myapp.semiproject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardReplyCommand implements BoardCommand{
	
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		//reply.jsp에서 넘어온 파라미터값 얻는다. 
		String num = req.getParameter("num");
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String content = req.getParameter("content");
		String repRoot = req.getParameter("repRoot");
		String repStep = req.getParameter("repStep");
		String repIndent = req.getParameter("repIndent");

		//BoardDAO클래스의 reply 메소드를 사용하여 파라미터값을 전달한다 .
		BoardDAO dao = new BoardDAO();
		
		dao.reply(num,title,author,content,repRoot,repStep,repIndent);
		
	}//execute
}//BoardReplyCommand
