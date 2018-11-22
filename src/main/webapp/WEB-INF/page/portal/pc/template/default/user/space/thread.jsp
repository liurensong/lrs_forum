<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${title}</title>
<c:import url="../../common/common.jsp"></c:import>
<style>
*, :before, :after {
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}
.tbmu {
	padding: 8px 10px 8px 0;
	border-bottom: 1px dashed #CDCDCD;
}
.tbmu a {
	color: #369;
}
.tbmu .a {
	color: #333;
	font-weight: 700;
}

.table td{
	padding: 4px 16px;
}
.table a {
	color: #2366A8;
}
.table a:hover{
	text-decoration: underline;
}
a.recy:link {
	text-decoration: line-through;
}
.content-img .page {
	margin: 0;
	margin-top: 20px;
}
.xg1, .xg1 a {
	color: #999 !important;
}
td.xg1 {
	padding: 5px 0;
	border-bottom: 1px solid #C2D5E3;
}
</style>
</head>

<body>
	<!-- 头部和导航 -->
	<c:import url="../../common/header.jsp"></c:import>
	
	<div class="list-content-0 content-img">
		<div class="w-full img">
			<div class="vuscat-content">
				<div class="v2-t-menu-t"></div>
			</div>
		</div>
		<div class="w-full">
			<div class="uscat-content" style="text-align:left;position: unset;">
				<div class="w-left">
					<div class="u-box-l">
						<div class="u-face">
							<c:choose>
								<c:when test="${empty spaceUserInfo.avatar}">
									<img src="${pageContext.request.contextPath}/static/common/images/noavatar.png" />
								</c:when>
								<c:otherwise>
									<img src="${spaceUserInfo.avatar}" />
								</c:otherwise>
							</c:choose>
							<div>${spaceUserInfo.loginName}</div>
						</div>
						<ul>
							<li><a href="${pageContext.request.contextPath}/forum/user_info/space_profile.action?userId=${spaceUserInfo.id}"><span class="icon"><i class="icon-chevron-right"></i></span> 帐号信息</a></li>
							<li class="active"><span class="icon"><i class="icon-chevron-right"></i></span> 帖子</li>
						</ul>
					</div>
				</div>
				<div class="w-right">
					<div class="u-box-r">
						<div class="t"><span>帖子</span></div>
						<div class="main">
							<p class="tbmu">
								<a href="${pageContext.request.contextPath}/forum/user_info/space_thread.action?userId=${spaceUserInfo.id}" class="a">主题</a>
								<span class="pipe">|</span>
								<a href="${pageContext.request.contextPath}/forum/user_info/space_thread.action?userId=${spaceUserInfo.id}&type=reply">回复</a>
							</p>
							<table class="table no-vertical-line no-border-line">
								<thead>
									<tr>
										<th>主题</th>
										<th style="width:120px;">板块</th>
										<th style="width:120px;">回复/查看</th>
										<th style="width:180px;">最后发帖</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pageInfo.list}" var="threadInfo">
										<tr>
											<td>
												<c:choose>
													<c:when test="${threadInfo.status=='2' || threadInfo.userStatus!='1'}">
														<a href="javascript:;" class="recy">${fn:substring(threadInfo.title, 0, 30)}</a>
													</c:when>
													<c:otherwise>
														<a href="${pageContext.request.contextPath}/forum/viewthread.action?tid=${threadInfo.tid}" target="_blank">${fn:substring(threadInfo.title, 0, 30)}</a>
													</c:otherwise>
												</c:choose>
											</td>
											<td><a href="${pageContext.request.contextPath}/forum/forumdisplay.action?fid=${threadInfo.fid}" target="_blank">${threadInfo.boardName}</a></td>
											<td>${threadInfo.replyCount} / ${threadInfo.viewCount}</td>
											<td>
												<a href="${pageContext.request.contextPath}/forum/user_info/space_profile.action?userId=${threadInfo.threadReplyInfo.replyUserId}" target="_blank">${threadInfo.threadReplyInfo.loginName}</a>
												<br />
												${threadInfo.threadReplyInfo.replyTime}
											</td>
										</tr>
										<c:forEach items="${threadInfo.threadReplyList}" var="threadReplyInfo">
											<tr>
												<td colspan="4" class="xg1">
													<img src="${pageContext.request.contextPath}/static/common/images/icon_quote_m_s.gif" style="vertical-align:middle;">
													${fn:substring(threadReplyInfo.contentText, 0, 30)}
													<img src="${pageContext.request.contextPath}/static/common/images/icon_quote_m_e.gif" style="vertical-align:middle;">
												</td>
											</tr>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
							
							<div class="page">
								<ul id="page" class="pagination"></ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--底部-->
	<c:import url="../../common/footer.jsp"></c:import>
</body>
<script>
	var userId = "${spaceUserInfo.id}";
	var type = "${type}";
	var currentPage = "${pageInfo.pageNum}";
	var pageCount = "${pageInfo.pages}";
	javaex.page({
		id : "page",
		pageCount : pageCount,	// 总页数
		currentPage : currentPage,// 默认选中第几页
		callback:function(rtn) {
			jump(rtn.pageNum);
		}
	});
	function jump(pageNum) {
		if (!pageNum) {
			pageNum = 1;
		}
		window.location.href = "${pageContext.request.contextPath}/forum/user_info/space_thread.action"
				+ "?userId="+userId
				+ "&type="+type
				+ "&pageNum="+pageNum
				;
	}
</script>
</html>
