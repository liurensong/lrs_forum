<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${title}</title>
<meta name="keywords" content="${keywords}" />
<meta name="description" content="${description}" />
<c:import url="../common/common.jsp"></c:import>
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
		<div class="list-content" style="width: 1200px;margin:0 auto 20px auto;">
			<div class="main-0">
				<div class="tab-bar">
					<div class="title">
						<a href="${pageContext.request.contextPath}/"><span class="icon-home2"></span> 论坛</a> / <span>${zoneInfo.name}</span>
					</div>
				</div>
				
				<!-- 分区信息 -->
				<div class="block no-shadow">
					<div class="main" style="margin-bottom:-20px;">
						<h2><span style="color:${zoneInfo.color};">${zoneInfo.name}</span></h2>
						<div class="moderator">超级版主</div>
						<ul class="equal-8" style="margin-top: 10px;">
							<c:choose>
								<c:when test="${fn:length(zoneInfo.zoneModeratorList)==0}">
									<li>
										超级版主空缺中
									</li>
								</c:when>
								<c:otherwise>
									<c:forEach items="${zoneInfo.zoneModeratorList}" var="zoneModerator">
										<li>
											<a class="moderator-avatar" href="#">
												<img src="${zoneModerator.avatar}"/>
											</a>
											<p class="moderator-name">${zoneModerator.loginName}</p>
										</li>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							<span class="clearfix"></span>
						</ul>
					</div>
				</div>
				
				<!-- 板块信息 -->
				<div class="block no-shadow">
					<div class="main" style="margin-bottom:-10px;">
						<ul class="equal-${zoneInfo.row}">
							<c:forEach items="${boardList}" var="boardInfo">
								<li>
									<h3>
										<a href="${pageContext.request.contextPath}/forum/forumdisplay.action?fid=${boardInfo.fid}" target="_blank">
											<c:choose>
												<c:when test="${empty boardInfo.icon}">
													<img src="${pageContext.request.contextPath}/static/default/images/forum.gif" width="${boardInfo.width}" height="${boardInfo.height}" />${boardInfo.name}
												</c:when>
												<c:otherwise>
													<img src="${boardInfo.icon}" width="${boardInfo.width}" height="${boardInfo.height}" />${boardInfo.name}
												</c:otherwise>
											</c:choose>
										</a>
										<c:if test="${boardInfo.threadCountToday!='0'}">
											<em><strong>(${boardInfo.threadCountToday})</strong></em>
										</c:if>
									</h3>
									<p style="margin-left: ${boardInfo.width+10}px;font-size: 12px;">${boardInfo.remark}</p>
									<p style="margin-left: ${boardInfo.width+10}px;font-size: 12px;">主题:${boardInfo.threadCount}, 帖数:${boardInfo.threadReplyCount}</p>
								</li>
							</c:forEach>
							<span class="clearfix"></span>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!--底部-->
	<c:import url="../common/footer.jsp"></c:import>
</body>
</html>
