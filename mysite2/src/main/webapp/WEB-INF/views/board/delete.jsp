<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
		
		<c:if test="${authUser.no == boardVo.userNo }">
			<div id="guestbook" class="delete-form">
				<form action="${pageContext.servletContext.contextPath }/board" method="post" >
					<input type="hidden" name="a" value="delete">
					<input type="hidden" name="gNo" value="${gNo }">
					<input type='hidden' name="no" value="${boardVo.no }">
					<input type='hidden' name="userno" value="${boardVo.userNo }">
					<label>비밀번호</label>
					<input type="password" name="password">
					<input type="submit" value="확인">
				</form>
				<a href="${pageContext.servletContext.contextPath }/board">게시판 리스트</a>
			</div>
			</c:if>
			<c:if test = "${authUser.no != boardVo.userNo }">
				<div id="guestbook" class="notdelete-form">
				<table border="1" cellpadding="5" cellspacing="2">
				<td align=center width="300">삭제는 본인만 가능합니다</td>
				</table>
		</div>
		</c:if>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>