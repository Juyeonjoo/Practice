package org.zerock.myapp.semiproject;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet("*.do")
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.trace("service(req,res) invoked", req,res);

		//returns the part of this request's URL from the protocol name up to the query string in the first line of the HTTP request.
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String com = requestURI.substring(contextPath.length());
		
		BoardCommand command = null;
		String nextPage = null; 
		
		//list.do 인 경우 목록보기 요청을 구현한 BoardListC1ommand 객체를 생성하고 
		//execute 메소드를 호출한다. nextPage 변수에는 웹 브라우저에 보여줄 페이지인 list.jsp를 지정
		//목록 보기 
//		if(com.equals("/list.do")){
//			command = new BoardListCommand();
//			command.execute(req, res);
//			nextPage="/list.jsp";
//			}//if
		
		//글쓰기 폼 
		//writeui.do 요청시에는 글쓰기 폼인 write.jsp로 포워드 
		if(com.equals("/writeui.do")) {
			nextPage="/write.jsp";
		}//if
		
		//글쓰기
		//write.do 요청인 경우에는 BoardWriteCommand로 넘겨서 글쓰기 작업을 처리 
		if(com.equals("/write.do")){
			command = new BoardWriteCommand();
			command.execute(req, res);
			//현재 작성한 글을 포함하여 보여주기 위해서는 
			//”/list.do" 형식으로 요청해야 되기 때문에 nextPage 변수에 “list.do" 값을 설정
			nextPage="/list.do";
			
			// 게시판목록으로 자동이동시킴
			res.sendRedirect(nextPage); 	// 302 응답코드를가지는 응답이 전송 
			
			return;
		}//if
		
		//글 자세히 보기
		if(com.equals("/retrieve.do")) {
			command = new BoardRetrieveCommand();
			command.execute(req, res);
			nextPage="/retrieve.jsp";
		}//if
		
		//글 수정하기 
		//update.do로 요청하면, BoardUpdateCommand 수행 후에 목록보기로 포워드 시킴 
		if(com.equals("/update.do")) {
			command = new BoardUpdateCommand();
			command.execute(req, res);
			nextPage="/list.do";
			
			return;
		}//if
		
		//글 삭제하기
		//delete.do 로 요청하면 BoardDeleteCommand클래스를 수행시키고, list.do로 포워
		if(com.equals("/delete.do")) {
			command = new BoardDeleteCommand();
			command.execute(req, res);
			nextPage = "/list.do";
			
			return;
		}//if
		
		//글 검색하기 
		if(com.equals("/search.do")) {
			command = new BoardSearchCommand();
			command.execute(req, res);
			nextPage= "/list.jsp";
		}//if
		
		//답변글 입력폼 보기
		//replyui.do 로 요청하면, BoardReplyUiCommand 클래스 실행하고
		//reply.jsp 로 포워드 시킨다. 
		if(com.equals("/replyui.do")) {
			command = new BoardReplyUICommand();
			command.execute(req, res);
			nextPage="/reply.jsp";
		}//if
		
		//답변 글쓰기
		//reply.do로 요청하면, BoardReplyCommand 실행시키고 list.do로 포워드한다. 
		if(com.equals("/reply.do")) {
			command = new BoardReplyCommand();
			command.execute(req, res);
			nextPage="/list.do";
			
		}//if
		
		//페이징 처리
		if(com.equals("/list.do")) {
			command = new BoardPageCommand();
			command.execute(req, res);
			nextPage = "/listPage.jsp";
		}//if 
		
//		log.trace("requestURI:{}", requestURI);
//		log.trace("contextPath:{}", contextPath);
//		log.trace("com:{}",com);
//		log.trace("nextPage:{}",nextPage);
		//요청 포워드로 JSP를 호출
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(nextPage);
		dispatcher.forward(req, res);	

//		log.trace("dispatcher:{}",dispatcher);

	}//service

}//BoardFrontController
