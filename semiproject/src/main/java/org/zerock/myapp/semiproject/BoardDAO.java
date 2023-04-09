package org.zerock.myapp.semiproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.zerock.myapp.semiproject.domain.BoardDTO;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardDAO {
		
	private DataSource dataFactory;
	
	public BoardDAO() {
		
		log.trace("Default constructor invoked.");
			
		//JNDI Tree에서 공유된 데이터 소스 자원을 이름으로 찾아내어 필드에 저장
		String prefix = "java:comp/env/";
		String name="jdbc/OracleCloudATPWithDriverSpy";
		
		//Step.1 JNDI tree에 접근하게 해주는 Context 객체를 생성
		try {
			Context ctx = new InitialContext();
			//InitialContext는 Context인터페이스의 구현객체
			log.info("\t + ctx:{}", ctx);
			
//			this.dataFactory = (DataSource)ctx.lookup("java:comp/env/jdbc/OracleCloudATPWithDriverSpy");
			
			this.dataFactory = (DataSource)ctx.lookup(prefix+name);
			log.info("\t + dataFactory:{}", dataFactory);
		
		}catch (NamingException e) {
				e.printStackTrace();
			} //try-catch

	}//default constructor
	
		//조회하여 리스트로 반환(순서 보장필요)
		public ArrayList<BoardDTO> list(){
//			final String jdbcOracleCloudUrl = "jdbc:oracle:thin:@iabobij3bs5jnh34_high?TNS_ADMIN=/opt/OracleCloudWallet/VFX";
//			 final String user = "scott";
//			 final String passForCloud = "Oracle12345678";
			 
			ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
			
			Connection conn=null;
			PreparedStatement pstmt =null; 
			ResultSet rs = null;

			try {
				conn = this.dataFactory.getConnection();
//				conn = DriverManager.getConnection(jdbcOracleCloudUrl,user,passForCloud);
				String sql = 
						"SELECT num, author,title,content,to_char(writeday, 'YYYY/MM/DD') writeday, readcnt , repRoot, repStep, repIndent FROM board order by repRoot desc , repStep asc";
								
				 pstmt = conn.prepareStatement(sql);
				 rs = pstmt.executeQuery();		
 
				while(rs.next()) {
					
					int num = rs.getInt( "num" );
					String author = rs.getString( "author" ); 
					String title = rs.getString( "title" );
					String content = rs.getString( "content" ); 
					String writeday = rs.getString( "writeday" );
					int readcnt = rs.getInt( "readcnt" );
					int repRoot = rs.getInt( "repRoot");
					int repStep = rs.getInt( "repStep" );
					int repIndent = rs.getInt( "repIndent" );
					
					BoardDTO data = new BoardDTO();
					data.setNum( num ); 
					data.setAuthor( author ); 
					data.setTitle( title ); 
					data.setContent( content ); 
					data.setWriteday( writeday ); 
					data.setReadcnt( readcnt ); 
					data.setRepRoot( repRoot); 
					data.setRepStep( repStep ); 
					data.setRepIndent( repIndent ); 
					
					list.add( data );

				}//while
			
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(rs!=null)rs.close();
					if(pstmt!=null)pstmt.close();
					if(conn!=null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}//end finally
		return list;
			
			}//end list()
		
		//글쓰기 
		public void write(String _title, String _author, String _content){
//			final String jdbcOracleCloudUrl = "jdbc:oracle:thin:@iabobij3bs5jnh34_high?TNS_ADMIN=/opt/OracleCloudWallet/VFX";
//			 final String user = "scott";
//			 final String passForCloud = "Oracle12345678";
			 
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			try {			
			//글번호: board_seq 시퀀스 이용해 값 설정
			//답변글 기능 repRoot:board_seq.currval 현재 글번호값 지정
			//repStep과 repIndent 컬럼은 '0'으로 지정 

//				Connection conn = DriverManager.getConnection(jdbcOracleCloudUrl,user,passForCloud);
				conn = this.dataFactory.getConnection();
				String sql = 
						"INSERT INTO board(num, title, author, content, repRoot, repStep, repIndent) values(board_seq.nextval,?,?,?,board_seq.currval,0,0)";

				pstmt = conn.prepareStatement(sql);
		
				pstmt.setString(1, _title);
				pstmt.setString(2, _author);
				pstmt.setString(3, _content);
				
				//Executes the SQL statement in thpstmt.is PreparedStatement object, 
				//which must be an SQL Data Manipulation Language (DML) statement, 
				//such as INSERT, UPDATE or DELETE; 
				int n = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(pstmt!=null) pstmt.close();
					if(conn!=null) conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}//end finally
		}//end write
		
		//조회수 1 증가
		//_num 값에 해당하는 readcnt 컬럼값 1 증가 
		public void readCount(String _num) {

			try {
				@Cleanup Connection conn = this.dataFactory.getConnection();
				String sql = 
						"UPDATE board SET readcnt = readcnt+1 WHERE num="+_num;
				
					@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
					int n = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}//try-catch
		}//readcount
		
		//글 자세히 보기
		//_num 값에 해당되는 레코드를 찾아서 BoardDTO에 저장하고 리턴함. 
		public BoardDTO retrieve(String _num) {
			//	조회수 증가
			readCount(_num);
			
			BoardDTO data = new BoardDTO();
			try {
				
				@Cleanup Connection conn = this.dataFactory.getConnection();
				String sql = "SELECT*FROM board WHERE num=?";
				@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
				//The driver converts this to an SQL INTEGER value when it sends it to the database.
				pstmt.setInt(1,  Integer.parseInt(_num));
				@Cleanup ResultSet rs = pstmt.executeQuery();
				
				//This code is reading data from a ResultSet object retrieved by executing a SQL query, 
				//and setting the retrieved data into a Java object called "data".
				if(rs.next()) {
					int num = rs.getInt("num");
					String title = rs.getString("title");
					String author = rs.getString("author");
					String content = rs.getString("content");
					String writeday = rs.getString("writeday");
					int readcnt = rs.getInt("readcnt");
					
					data.setNum(num);
					data.setTitle(title);
					data.setAuthor(author);
					data.setContent(content);
					data.setWriteday(writeday);
					data.setReadcnt(readcnt);
					
				}//end if
			}catch(Exception e) {
				e.printStackTrace();
			}
			return data;
		}//retrieve
		
		public void update(String _num, String _title, String _author, String _content) {
			
			try {
				@Cleanup Connection conn = this.dataFactory.getConnection();
				String sql = "UPDATE board SET title=?, author=?,content=? WHERE num=?";
				@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
				
				//_num에 해당되는 레코드의 title, author, content 컬럼값을 수정
				pstmt.setString(1, _title);
				pstmt.setString(2, _author);
				pstmt.setString(3, _content);
				pstmt.setInt(4, Integer.parseInt(_num));
				
				//Executes the SQL statement in this PreparedStatement object
				int n = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}//try-catch
		}//update
		
		//글 삭제하기
		//_num에 해당되는 레코드를 삭제한다. 
		public void delete(String _num) {
			
			try {
				@Cleanup Connection conn = this.dataFactory.getConnection();
				String sql = "DELETE FROM board WHERE num=?";
				@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(_num));
				
				int n = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}//delete
		
		//글 검색하기
		public ArrayList<BoardDTO> search(String _searchName, String _searchValue){
			ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
			
			try {
				@Cleanup Connection conn = this.dataFactory.getConnection();
				
				String sql = "SELECT num, author, title, content, to_char(writeday, 'YYYY/MM/DD')writeday, readcnt FROM board";
				
				//_searchName 값에 따라서 title과 author 컬럼 중에서 어떤 검색 킷값을 사용할지 결정됨
				//SQL 문의 LIKE 연산자 사용해서 문자 하나만 일치해도 모두 검색되도록 구현 
				if (_searchName.equals("title")) {
					sql += " WHERE title LIKE ?";
				}else {
					sql += " WHERE author LIKE ?";
				}//if-else
				
				@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+_searchValue+"%");
				
				@Cleanup ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int num = rs.getInt("num");
					String title = rs.getString("title");
					String author = rs.getString("author");
					String content = rs.getString("content");
					String writeday = rs.getString("writeday");
					int readcnt = rs.getInt("readcnt");
					
					BoardDTO data = new BoardDTO();
					data.setNum(num);
					data.setTitle(title);
					data.setAuthor(author);
					data.setContent(content);
					data.setWriteday(writeday);
					data.setReadcnt(readcnt);
					
					list.add(data);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}//try-catch
			return list;
		}//search
		
		//답변글 입력폼 보기
		public BoardDTO replyui(String _num) {
			//_num값에 해당되는 레코드를 선택하여, BoardDTO에 저장후에 리턴 
			BoardDTO data = new BoardDTO();
			try {
				@Cleanup Connection conn = this.dataFactory.getConnection();
				String sql = "SELECT * FROM board WHERE num =?";
				
				@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(_num));
				@Cleanup ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					
					data.setNum(rs.getInt("num"));
					data.setTitle(rs.getString("title"));
					data.setAuthor(rs.getString("author"));
					data.setContent(rs.getString("content"));
					data.setWriteday(rs.getString("writeday"));
					data.setReadcnt(rs.getInt("readcnt"));
					//답변글 기추가에는 반드시 repRoot, repStep, repIndent 컬럼 필요 
					data.setRepRoot(rs.getInt("repRoot"));
					data.setRepStep(rs.getInt("repStep"));
					data.setRepIndent(rs.getInt("repIndent"));
						
				}//if
					
			}catch(Exception e) {
				e.printStackTrace();
			}//try-catch
			return data;
		}//replyui
		
		//답변글의 기존 repStep 1 증가
		public void makeReply(String _root, String _step) {
			
			try {
				@Cleanup Connection conn = this.dataFactory.getConnection();
				String sql = "UPDATE board SET repStep =repStep+1 WHERE repRoot =? AND repStep > ? ";
				
				@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, Integer.parseInt(_root));
				pstmt.setInt(2, Integer.parseInt(_step));
				int n = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}//try-catch
		}//makeReply
		
		//답변달기
		public void reply(String _num, String _title, String _author, String _content, String _repRoot, String _repStep, String _repIndent) {
			
			//repStep + 1
			makeReply(_repRoot, _repStep);
			
			try {
				@Cleanup Connection conn = this.dataFactory.getConnection();
				String sql = "INSERT into board(num, title, author, content, repRoot, repStep, repIndent) values (board_seq.nextVal,?,?,?,?,?,?)";
				
				@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, _title);
				pstmt.setString(2, _author);
				pstmt.setString(3, _content);
				pstmt.setInt(4, Integer.parseInt(_repRoot));
				//부모글의 repStep과 repIndent 값에 1씩 증가시켜 저장한다. 
				pstmt.setInt(5, Integer.parseInt(_repStep)+1);
				pstmt.setInt(6, Integer.parseInt(_repIndent)+1);
				
				int n = pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}//reply
		
		//페이징 처리 : 전체 레코드 개수 구하기 
		public int totalCount() {
			int count=0;
			try {
				@Cleanup Connection conn = this.dataFactory.getConnection();
				String sql = "SELECT count(*) FROM board";
				
				@Cleanup PreparedStatement pstmt=conn.prepareStatement(sql);
				@Cleanup ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					count=rs.getInt(1);
				}//if
			}catch(Exception e) {
				e.printStackTrace();
			}
			return count;
		}//totalCount
		
		//페이지 구현 
		//페이징 처리시 필요한 데이터를 저장하는 PageTO를 리턴하는 page 메소드 작성
		//인자값으로 현재 페이지 번호를 저장하는 curPage 변수 사용 
		
		public PageTO page(int curPage) {
			PageTO to = new PageTO();
			int totalCount= totalCount();
			
			ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
			try {
				@Cleanup Connection conn = this.dataFactory.getConnection();
				String sql = "SELECT num, author, title, content, to_char(writeday, 'YYYY/MM/DD') writeday, readcnt, repRoot, repStep, repIndent FROM board order by repRoot desc, repStep asc";
				
				//기본적으로 결과셋인 ResultSet 객체는 next()메소드를 사용하여 단방향으로만 접근 가능 
				// PreparedStatement 객체를 생성시 ResultSet 타입을 소스코드처럼 지정하면 
				// 양방향 및 랜덤액세스도 사용 가능 
				@Cleanup PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				ResultSet rs = pstmt.executeQuery();
				
				int perPage = to.getPerPage();//5
				
				//전체 레코드에서 skip해야 할 개수를 이용하여  aboslute(skip)메소드를 사용하여 랜덤 접근 
				int skip = (curPage-1)*perPage;
				if(skip>0){
					//If the row number is positive, 
					//the cursor moves to the given row number with respect to the beginning of the result set. 
					rs.absolute(skip);
					}//if
				for(int i =0; i<perPage && rs.next(); i++) {
					
					int num = rs.getInt("num");
					String author = rs.getString("author");
					String title = rs.getString("title");
					String content = rs.getString("content");
					String writeday = rs.getString("writeday");
					
					int readcnt = rs.getInt("readcnt");
					int repRoot = rs.getInt("repRoot");
					int repStep = rs.getInt("repStep");
					int repIndent = rs.getInt("repIndent");
					
					BoardDTO data = new BoardDTO();
					data.setNum(num);
					data.setTitle(title);
					data.setContent(content);
					data.setWriteday(writeday);
					data.setReadcnt(readcnt);
					data.setRepRoot(repRoot);
					data.setRepStep(repStep);
					data.setRepIndent(repIndent);
					
					list.add(data);

				}//for
				
				//페이징처리에 필요한 데이터를 PageTO에 최종적으로 저장 
				to.setList(list);//ArrayList에 저장 
				to.setTotalCount(totalCount);//전체레코드개
				to.setCurPage(curPage);//현재 페이지 
				
				}catch(Exception e) {
				e.printStackTrace();
			}
			//PageTO 리턴 
			return to;
		}//page 
}//end BoardDAO class