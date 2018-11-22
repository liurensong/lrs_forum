<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>找回密码</title>
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
								<span class="icon-envelope icon"></span>
								<input type="text" class="original" id="email" name="email" placeholder="请输入邮箱" value="" autocomplete="off"/>
							</div>
							<div class="fielditem">
								<span class="icon-verified_user icon"></span>
								<input type="text" class="original" id="identifying_code" placeholder="请输入验证码" value="" autocomplete="off"/>
								<div class="btcode">
									<div id="get_code" class="moe-bottom-button" onclick="getCode()">获取验证码</div>
								</div>
							</div>
							<div class="fielditem">
								<span class="icon-lock icon"></span>
								<input type="password" class="original" id="password" name="password" placeholder="请输入新密码" value="" autocomplete="off"/>
							</div>
						</div>
						<div class="tags">
							<a class="r" href="${pageContext.request.contextPath}/forum/user_info/login.action">已有账号？现在登录</a>
						</div>
						<div class="btbar">
							<img src="${pageContext.request.contextPath}/static/default/images/button_find_password.png" onclick="resetPassword()" />
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
	var second = 30;
	
	// 获取邮箱验证码
	function getCode() {
		$("#error-msg").text("");
		var email = $("#email").val();
		if (!email) {
			$("#error-msg").text("邮箱不能为空");
			return;
		}
		var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (!reg.test(email)) {
			$("#error-msg").text("邮箱格式错误");
			return;
		}
		
		setTimer();
		
		$.ajax({
			url : "${pageContext.request.contextPath}/forum/user_info/find_pwd_email.json",
			type : "POST",
			dataType : "json",
			data : {
				"email" : email
			},
			success : function(rtn) {
				if (rtn.code=="000000") {
					javaex.optTip({
						content : "邮件已发出，请查看",
						type : "success"
					});
				} else {
					$("#error-msg").text(rtn.message);
				}
			}
		});
	}
	
	var timerId = "";
	function setTimer() {
		// 设置按钮禁用
		$("#get_code").css({
			"pointer-events" : "none",
			"background-color" : "#a8a8a8"
		});
		// 启动定时器
		// 实时刷新时间单位为毫秒
		timerId = setInterval('timer()', 1000);
	}
	function timer() {
		second = second - 1;
		if (second>0) {
			$("#get_code").text(second + "秒后重试");
		} else {
			// 解禁
			$("#get_code").css({
				"pointer-events" : "unset",
				"background-color" : "#7ac43b"
			});
			$("#get_code").text("获取验证码");
			second = 30;
			clearInterval(timerId);
		}
	}
	
	// 重置密码
	function resetPassword() {
		$("#error-msg").text("");
		$.ajax({
			url : "${pageContext.request.contextPath}/forum/user_info/update_password.json",
			type : "POST",
			dataType : "json",
			data : {
				"email" : $("#email").val(),
				"identifyingCode" : $("#identifying_code").val(),
				"password" : $("#password").val()
			},
			success : function(rtn) {
				if (rtn.code=="000000") {
					javaex.optTip({
						content : "密码修改成功，即将为您跳转到登录页",
						type : "success"
					});
					// 建议延迟加载
					setTimeout(function() {
						// 跳转到登录页
						window.location.href = "${pageContext.request.contextPath}/forum/user_info/login.action";
					}, 2000);
				} else {
					$("#error-msg").text(rtn.message);
				}
			}
		});
	}
</script>
</html>
