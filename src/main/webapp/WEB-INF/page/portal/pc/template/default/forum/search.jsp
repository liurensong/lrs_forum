<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${title}</title>
<c:import url="../common/common.jsp"></c:import>
<!--关键词高亮-->
<script src="${pageContext.request.contextPath}/static/common/js/highlight.js"></script>
<style>
.highlight {
	font-style: normal;
	color: #c00;
}
</style>
</head>

<body>
	<!-- 头部和导航 -->
	<c:import url="../common/header.jsp"></c:import>
	
	<div class="list-content-0 content-img">
		<div class="w-full img">
			<div class="vuscat-content">
				<div class="v2-t-menu-t"></div>
			</div>
		</div>
		<div class="w-full">
			<div class="uscat-content" style="text-align: left;">
				<div class="nums">
					<span class="nums_text">${webInfo.name}为您找到相关结果约${pageInfo.total}个</span>
				</div>
				
				<ul>
					<c:forEach items="${pageInfo.list}" var="threadInfo">
						<li class="result c-container">
							<h3 class="t"><a href="${pageContext.request.contextPath}/forum/viewthread.action?tid=${threadInfo.tid}" target="_blank">${threadInfo.title}</a></h3>
							<p class="xg1">${threadInfo.replyCount} 个回复 - ${threadInfo.viewCount} 次查看</p>
							<p class="c-abstract">${fn:substring(threadInfo.contentText, 0, 200)}</p>
							<div class="f13">
								<span class="c-showurl">${threadInfo.createTime}</span>
								&nbsp;-&nbsp;
								<a href="${pageContext.request.contextPath}/forum/user_info/space_profile.action?userId=${threadInfo.createUserId}" target="_blank" class="m">${threadInfo.loginName}</a>
								&nbsp;-&nbsp;
								<a href="${pageContext.request.contextPath}/forum/forumdisplay.action?fid=${threadInfo.fid}" target="_blank" class="m">${threadInfo.boardName}</a>
							</div>
						</li>
					</c:forEach>
				</ul>
				
				<div class="page" style="margin-top: 20px;">
					<ul id="page" class="pagination"></ul>
				</div>
			</div>
		</div>
	</div>
	<!--底部-->
	<c:import url="../common/footer.jsp"></c:import>
</body>
<script>
var keyword = "${keyword}";
var currentPage = "${pageInfo.pageNum}";
var pageCount = "${pageInfo.pages}";
</script>
<script src="${pageContext.request.contextPath}/static/common/js/forum/search.js"></script>
</html>
