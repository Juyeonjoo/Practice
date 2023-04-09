package org.zerock.myapp.semiproject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.myapp.semiproject.domain.BoardDTO;

public class BoardReplyUICommand implements BoardCommand {

	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		//num파라미터값에 해당되는 얻
		String num = req.getParameter("num");
		
		//BoardDAO 객체 얻음 
		BoardDAO dao = new BoardDAO();
		
		//This method likely retrieves the necessary data 
		//from the database and returns it in the form of a BoardDTO object. 
		BoardDTO data = dao.replyui(num);
		
		//request scope에 replyui 키 값으로 바인딩 
		req.setAttribute("replyui", data);
	}//execute
	
}//BoardReplyUICommand
