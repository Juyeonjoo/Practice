package org.zerock.myapp.semiproject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardPageCommand implements BoardCommand {
	
	public void execute(HttpServletRequest req, HttpServletResponse res) {
		
		//맨 처음 요청시 보여줄  페이지 값으로 기본 페이지 1 저장 
		int curPage =1;			//기본 페이지 
		
		//curPage 파라미터를 지정하지 않으면 기본값 1로 설정, 
		//파라미터가 존재하면 파라미터값으로 현재 페이지 설정하여 PageTO 저장후 리턴 
		if(req.getParameter("curPage")!=null) {
			curPage = Integer.parseInt(req.getParameter("curPage"));
		}//if
		
		//listPage.jsp 보여줄 목록 리스트 데이터 저장 
		BoardDAO dao = new BoardDAO();
		PageTO list = dao.page(curPage);
		
		//listPage.jsp에서 목록 리스트 데이터 저장 
		//page.jsp 에서 페이징 처리시 필요한 데이터 저장 
		req.setAttribute("list", list.getList());
		
		//page.jsp 에서 페이징 처리 데이터 저장 
		req.setAttribute("page", list);
	}//execute
}//BoardPageCommand
