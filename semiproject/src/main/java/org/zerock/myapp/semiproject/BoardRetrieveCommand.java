package org.zerock.myapp.semiproject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.myapp.semiproject.domain.BoardDTO;

public class BoardRetrieveCommand implements BoardCommand {

	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		//num값에 해당하는 파라미터값 얻어서 BoardDTO에 저장하여 리턴받는다.
		String num = req.getParameter("num");
		BoardDAO dao = new BoardDAO();
		BoardDTO data = dao.retrieve(num);
		
		//request scope 에 BoardDTO데이터를 "retrieve" 키값으로 바인딩한다.
		req.setAttribute("retrieve", data);
	}//execute
}//BoardRetrieveCommand
