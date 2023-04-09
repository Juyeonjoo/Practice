package org.zerock.myapp.semiproject.mapper;

import java.util.List;

import org.zerock.myapp.semiproject.domain.BoardDTO;

public interface BoardMapper {

	public abstract List<BoardDTO> list();
	
	public abstract List<BoardDTO> write(String _title, String _author, String _content);
	
	public abstract List<BoardDTO> readCount(String _num);
	
	public abstract List<BoardDTO> update(String _num, String _title, String _author, String _content); 
	
	public abstract List<BoardDTO> delete(String _num);
	
	public abstract List<BoardDTO> search(String _searchName, String _searchValue);
	
	public abstract List<BoardDTO> replyui(String _num);
	
	public abstract List<BoardDTO> makeReply(String _root, String _step);
	
	public abstract List<BoardDTO> reply(String _num, String _title, String _author, String _content, String _repRoot, String _repStep, String _repIndent);
	
	public abstract Integer totalCount();
	
	
}//end interface
