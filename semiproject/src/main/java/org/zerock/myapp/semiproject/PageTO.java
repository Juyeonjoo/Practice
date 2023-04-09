package org.zerock.myapp.semiproject;

import java.util.ArrayList;

import org.zerock.myapp.semiproject.domain.BoardDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageTO {
	
	//페이징 처리에 필요한 데이터를 저장하기 위한 변수 선언 
	ArrayList<BoardDTO> list; //목록리스트 저장
	int curPage;			//현재 페이지 번호
	int perPage = 5;		//페이지당 보여줄 레코드 개수
	int totalCount;			//전체 레코드 개수
	
} //PageTO
