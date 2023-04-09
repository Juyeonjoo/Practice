package org.zerock.myapp.semiproject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardDeleteCommand implements BoardCommand{

	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		//num에 해당되는 파라미터를 얻어서 BoardDAO 클래스의 delete 메소드에 넘겨준다. 
		String num = req.getParameter("num");
		
		BoardDAO dao = new BoardDAO();
		dao.delete(num);
	}//execute
}//BoardDeleteCommand
