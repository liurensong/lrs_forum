<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>注册已关闭 - ${webInfo.name}</title>
<c:import url="../common/common.jsp"></c:import>
<style>
	body {
		background: #fafafa;
		color: #333;
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
			<div class="uscat-content">
				<img src="${pageContext.request.contextPath}/static/default/images/no-register.png" />
				<br/><br/>
				<div style="width:900px;margin:0 auto;">
					<p class="tip danger" style="text-align: center;">${registerSetting.closeRegisterMessage}</p>
				</div>
			</div>
		</div>
		<div class="notfound-btn-container">
			<a class="button post-send" href="${pageContext.request.contextPath}/">返回首页</a>
		</div>
	</div>
	<!--底部-->
	<c:import url="../common/footer.jsp"></c:import>
</body>
</html>
