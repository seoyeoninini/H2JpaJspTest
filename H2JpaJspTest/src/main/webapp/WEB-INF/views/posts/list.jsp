<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
  
<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/forms.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/board.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/paginate.css" type="text/css">
 
<style type="text/css">
  .body-container { margin: 30px auto; width: 700px; }
</style>

<script type="text/javascript" src="${pageContext.request.contextPath}/dist/js/paging.js"></script>

<c:url var="listUrl" value="/posts/list"/>

<script type="text/javascript">
window.addEventListener('load', function(){
	let page = '${page}';
	let pageSize = '${size}';
	let dataCount = parseInt('${dataCount}');
	let url = '${listUrl}';

	let total_page = pageCount(dataCount, pageSize);
	let paging = pagingUrl(page, total_page, url);

	document.querySelector('.dataCount').innerHTML = dataCount + '개 (' + page + '/' + total_page + ' 페이지)';
	document.querySelector('.page-navigation').innerHTML = dataCount === 0 ? '등록된 게시물이 없습니다.' : paging;
}, false);

function searchList() {
	const f = document.searchForm;
	f.submit();
}
</script>

</head>
<body>

<div class="body-container">
	<div class="body-title">
	    <h2>▢ 게시판</h2>
	</div>
	
	<div class="body-main">
		<table class="table">
			<tr>
				<td width="50%" class="dataCount">&nbsp;</td>
				<td align="right">&nbsp;</td>
			</tr>
		</table>
		
		<table class="table table-border table-list">
			<thead>
				<tr>
					<th width="60">번호</th>
					<th>제목</th>
					<th width="100">작성자</th>
					<th width="100">작성일</th>
					<th width="70">조회수</th>
				</tr>
			</thead>
	
			<tbody>
				<c:forEach var="dto" items="${list}" varStatus="status">
					<tr>
						<td>${dataCount - (page-1) * size - status.index}</td>
						<td class="left">
							<c:url var="url" value="/posts/article/${dto.num}">
								<c:param name="page" value="${page}"/>
							</c:url>						
							<a href="${url}">${dto.title}</a>
						</td>
						<td>${dto.writer}</td>
						<td>${dto.reg_date}</td>
						<td>${dto.hitCount}</td>
					</tr>
				</c:forEach>
			</tbody>
			
		</table>
		
		<div class="page-navigation"></div>
		
		<table class="table">
			<tr>
				<td width="100">
					&nbsp;
				</td>
				<td align="center">
					&nbsp;
				</td>
				<td align="right" width="100">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/posts/write';">글올리기</button>
				</td>
			</tr>
		</table>
	</div>
</div>

</body>
</html>