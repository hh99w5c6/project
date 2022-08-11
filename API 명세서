API 명세서 입니다.

로그인/회원가입 : /api/member
회원가입 POST : /signup
로그인 GET: /login


게시물 : /api
전체 게시물 조회 GET: /posts
선택 게시글 조회 GET: /posts/{postId}
게시글 작성 POST: /post
게시글 수정 PUT: /post/{postId}
게시글 삭제 DELETE: /post/{postId}
선택 게시글의 todo 삭제 DELETE: /post/{postId}/todo/{todoId}

댓글 : /api
댓글 작성 POST: /post/{postId}/comment
대댓글 작성 POST: /comment/{commentId}/comment
댓글 수정 PUT: "/comment/{commentId}
대댓글 수정 PUT: /subComment/{subCommentId}
댓글 삭제 DELETE: /comment/{commentId}
대댓글 삭제 DELETE: /subComment/{subCommentId}

좋아요 POST: /api/likes
게시글 좋아요 POST: /post/{postId}
댓글 좋아요 POST: /comment/{commentId}
대댓글 좋아요 POST: /subComment/{subCommentId}


마이페이지 : /api/myPage
내가쓴글 조회하기 GET: /posts
내가쓴 댓글 조회하기 GET: /comments
내가 좋아요 한 게시글 조회하기 GET: /likes/posts
내가 좋아요 한 댓글 조회하기 GET: /likes/comments









API 요청 폼입니다.(JSON)(Postman application 기준)
회원가입(body,row):
{
    "nickname" : "아이디",
    "password" : "패스워드",
    "passwordConfirm" : "패스워드 재확인"
}

로그인(body,row):
{
    "nickname" : "아이디",
    "password" : "패스워드"
}





전체 게시물 조회:
그냥 GET 요청

선택 게시물 조회:
그냥 GET 요청

게시글 작성(인증필요)(body,form-data,header):
<body 부분>
key(Text) : postRequestDto
value : 
{"title" : "제목",
"todoList" : [
{"content" : "해야 할 일1",
"done" : true},
{"content" : "해야 할 일2",
"done" : false},
{"content" : "해야 할 일3",
"done" : true}
]}

key(File) : 그림파일업로드


<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.



게시글 수정(인증필요)(body,row):
<body 부분>
{"title" : "제목",
"todoList" : [
{"content" : "해야 할 일1",
"done" : true},
{"content" : "해야 할 일2",
"done" : false},
{"content" : "해야 할 일3",
"done" : true}
]}

<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.





게시글 삭제(인증필요)(header):
<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.




선택 게시글의 todo 삭제(인증필요)(header):
<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.

















댓글 작성(인증필요)(body,row,header):
<body 부분>
{
    "content" : "댓글 내용"
}

<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.



대댓글 작성(인증필요)(body,row,header):
<body 부분>
{
    "content" : "댓글 내용"
}

<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.



댓글 수정(인증필요)(body,row,header):
<body 부분>
{
    "content" : "댓글 수정 내용"
}

<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.


대댓글 수정(인증필요)(body,row,header):
<body 부분>
{
    "content" : "대댓글 수정 내용"
}

<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.




댓글 삭제(인증필요)(header):
<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.


대댓글 삭제(인증필요)(header):
<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.















게시글 좋아요(인증필요)(header):
<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.


댓글 좋아요(인증필요)(header):
<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.


대댓글 좋아요(인증필요)(header):
<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.

























내가쓴 글 조회하기(인증필요)(header):
<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.




내가쓴 댓글 조회하기(인증필요)(header):
<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.





내가 좋아요한 게시글 조회하기(인증필요)(header):
<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.




내가 좋아요한 댓글 조회하기(인증필요)(header):
<header 부분>
key : Authorization
value : Bearer -----------------     <- 로그인을 하면 리스폰스 헤더에 Authorization 부분에 있는걸 복사해서 붙여넣기.

key : Refresh-Token
vlaue : ------------------------     <- 로그인을 하면 리스폰스 헤더에 Refresh-Token 부분에 있는걸 복사해서 붙여넣기.


