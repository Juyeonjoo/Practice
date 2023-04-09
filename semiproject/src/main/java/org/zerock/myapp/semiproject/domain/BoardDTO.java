package org.zerock.myapp.semiproject.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class BoardDTO { //DTO: 
	int num;
	String author;
	String title;
	String content;
	int readcnt;
	String writeday;
	int repRoot;
	int repStep;
	int repIndent;
	
	//input data , 화면에서 오는 전송 파라미터 수집용 값 객체 value object 
	public BoardDTO(int num, String author, String title, String
			content, int readcnt, String writeday, int repRoot, int repStep,
			int repIndent) {
		
		this.num = num;
		this.author = author;
		this.title = title;
		this.content = content;
		this.readcnt = readcnt;
		this.writeday = writeday;
		this.repRoot = repRoot;
		this.repStep = repStep;
		this.repIndent = repIndent;
	 
	}
}//BoardDTO
