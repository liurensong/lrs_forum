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
							<li class="active"><span class="icon"><i class="icon-chevron-right"></i></span> 帐号信息</li>
							<li><a href="${pageContext.request.contextPath}/forum/user_info/space_thread.action?userId=${spaceUserInfo.id}"><span class="icon"><i class="icon-chevron-right"></i></span> 帖子</a></li>
						</ul>
					</div>
				</div>
				<div class="w-right">
					<div class="u-box-r">
						<div class="t"><span>账号信息</span></div>
						<div class="info"><span class="lable">UID:</span><span>${spaceUserInfo.id}</span></div>
						<div class="info"><span class="lable">昵称:</span><span>${spaceUserInfo.loginName}</span></div>
						<div class="info"><span class="lable">Email:</span><span>${spaceUserInfo.email}</span> <span class="pipe">|</span> ${spaceUserInfo.isEmailChecked=='1'?'已验证':'未验证'}</div>
						<div class="info"><span class="lable">主题数:</span><span>${spaceUserInfo.userCount.posts}</span></div>
						<div class="info"><span class="lable">回帖数:</span><span>${spaceUserInfo.userCount.threads}</span></div>
						<div class="info"><span class="lable">精华数:</span><span>${spaceUserInfo.userCount.digestposts}</span></div>
						<div class="info"><span class="lable">注册时间:</span><span>${spaceUserInfo.registerTime}</span></div>
						<div class="info"><span class="lable">总积分:</span><span>${spaceUserInfo.userCount.point}</span></div>
						<div class="info">
							<span class="lable">论坛币:</span>
							<ul class="equal-5" style="float: left;width: 800px;">
								<c:forEach items="${pointUseList}" var="pointInfo">
									<li>
										${pointInfo.name}：
										<c:choose>
											<c:when test="${pointInfo.varName=='extcredits1'}">
												${spaceUserInfo.userCount.extcredits1}
											</c:when>
											<c:when test="${pointInfo.varName=='extcredits2'}">
												${spaceUserInfo.userCount.extcredits2}
											</c:when>
											<c:when test="${pointInfo.varName=='extcredits3'}">
												${spaceUserInfo.userCount.extcredits3}
											</c:when>
											<c:when test="${pointInfo.varName=='extcredits4'}">
												${spaceUserInfo.userCount.extcredits4}
											</c:when>
											<c:when test="${pointInfo.varName=='extcredits5'}">
												${spaceUserInfo.userCount.extcredits5}
											</c:when>
											<c:when test="${pointInfo.varName=='extcredits6'}">
												${spaceUserInfo.userCount.extcredits6}
											</c:when>
										</c:choose>
									</li>
								</c:forEach>
								<span class="clearfix"></span>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--底部-->
	<c:import url="../../common/footer.jsp"></c:import>
</body>
</html>
