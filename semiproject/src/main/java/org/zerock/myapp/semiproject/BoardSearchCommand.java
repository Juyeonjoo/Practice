package org.zerock.myapp.semiproject;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.myapp.semiproject.domain.BoardDTO;

public class BoardSearchCommand implements BoardCommand{

	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		//searchName과 searchValue 파라미터 값 얻는다. 
		String searchName = req.getParameter("searchName");
		String searchValue = req.getParameter("searchValue");
			
		//BoardDAO의 search 메소드를 사용하여 일치하는 데이터를 ArrayList로 리턴 받음 
		BoardDAO dao = new BoardDAO();
		ArrayList<BoardDTO> list = dao.search(searchName, searchValue);
		
		//request scope에 "list" 키값으로 바인딩 
		req.setAttribute("list", list);
	}//execute
}//BoardSearchCommand
