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
				<!-- 左侧 -->
				<c:import url="menu.jsp"></c:import>
				
				<!-- 右侧 -->
				<div class="w-right">
					<div class="u-box-r">
						<div class="t"><span>账号信息</span></div>
						<div class="info"><span class="lable">UID:</span><span>${userInfo.id}</span></div>
						<div class="info"><span class="lable">昵称:</span><span>${userInfo.loginName}</span></div>
						<c:choose>
							<c:when test="${userInfo.isEmailChecked=='1'}">
								<div class="info"><span class="lable">Email:</span><span>${userInfo.email}</span> <span class="pipe">|</span> <font color="green">已验证</font></div>
							</c:when>
							<c:otherwise>
								<div class="info"><span class="lable">Email:</span><span>${userInfo.email}</span> <span class="pipe">|</span> <font color="red">未验证</font></div>
								<div class="info"><span class="lable">&nbsp;</span><p class="hint">可在密码安全菜单修改密码，以激活邮箱</p></div>
							</c:otherwise>
						</c:choose>
						<div class="info"><span class="lable">注册时间:</span><span>${userInfo.registerTime}</span></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--底部-->
	<c:import url="../../common/footer.jsp"></c:import>
</body>
</html>
