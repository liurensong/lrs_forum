<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>用户登录 - ${webInfo.name}</title>
<c:import url="../common/common.jsp"></c:import>
<style>
*, :before, :after {
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}
body {
    background: transparent;
}
.main img {
	margin-right: 0;
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
				<div class="login_box">
					<div class="logo"><img src="${pageContext.request.contextPath}/static/default/images/wlogin_logo.png"></div>
					<form id="form" style="text-align: left;" autocomplete="off">
						<span id="error-msg" style="color:red;font-size:14px;margin-left: 34px;"></span>
						<div class="field">
							<div class="fielditem">
								<span class="icon-user icon"></span>
								<input type="text" class="original" name="loginName" placeholder="请输入账号" value="">
							</div>
							<div class="fielditem">
								<span class="icon-lock icon"></span>
								<input type="password" class="original" name="password" maxlength="20" placeholder="请输入密码" value="">
							</div>
						</div>
						<div class="tags">
							<a class="l" href="${pageContext.request.contextPath}/forum/user_info/register.action">注册账号</a>
							<a class="r" href="${pageContext.request.contextPath}/forum/user_info/find_password.action">忘记密码？</a>
						</div>
						<div class="btbar">
							<img src="${pageContext.request.contextPath}/static/default/images/button_login.png" onclick="login()" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!--底部-->
	<c:import url="../common/footer.jsp"></c:import>
</body>
<script>
	function login() {
		$("#error-msg").text("");
		$.ajax({
			url : "${pageContext.request.contextPath}/forum/user_info/login.json",
			type : "POST",
			dataType : "json",
			data : $("#form").serialize(),
			success : function(rtn) {
				if (rtn.code=="000000") {
					var info = rtn.data.info;
					
					delCookie("userToken");
					setCookie("userToken", info.userToken);
					
					javaex.optTip({
						content : "登录成功，即将为您跳转到首页",
						type : "success"
					});
					// 建议延迟加载
					setTimeout(function() {
						// 跳转到首页
						window.location.href = "${pageContext.request.contextPath}/";
					}, 2000);
				} else {
					$("#error-msg").text(rtn.message);
				}
			}
		});
	}
</script>
</html>
