<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <!DOCTYPE html>
    <html lang="ko">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>글 자세히 보기 </title>
    </head>
    <body>
            <h1>글 자세히 보기</h1>
            
            <form action="/update.do" method="post">
                        <!--글 수정시 필요-->
				
				<!-- 글 수정 기능 추가시 hidden을 이용해 수정할 num 값을 파라미터로 넘김  -->
                <input type="hidden" name="num" value="${retrieve.num}">
                
                <!--EL 표기법을 사용하여 BoardRetrieveCommand에서 "retrieve"값으로 바인딩 된 데이터 출력 -->
                글번호: ${retrieve.num} &nbsp;&nbsp;&nbsp;&nbsp;
                조회수:${retrieve.readcnt} <br />

                제목<input type="text" name="title" value="${retrieve.title}"><br>
                작성자<input type="text" name="author" value="${retrieve.author}"><br>
                내용<textarea name="content" rows="10" >${retrieve.content} </textarea><br>
                <input type="submit" value="수정">
            </form>

            <a href="/list.do">목록</a>
            <!-- 글 삭제 기능 추가시 delete.do로 요청하면 get방식으로 선택한 num 값을 파라미터로 같이 전송  -->
            <a href="/delete.do?num=${retrieve.num} ">삭제</a>
            <!--답변글 기능 추가시, replyui.do 로 요청하면서 GET방식으로 선택한 원글의 num 값을 파라미터로 같이 전송  -->
            <a href="/replyui.do?num=${retrieve.num} ">답변달기</a>
    </body>
    </html>