<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>用户注册 - ${webInfo.name}</title>
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
								<input type="text" class="original" name="loginName" placeholder="请输入账号" value="" autocomplete="off"/>
							</div>
							<div class="fielditem">
								<span class="icon-lock icon"></span>
								<input type="password" class="original" name="password" placeholder="请输入密码" value="" autocomplete="off"/>
							</div>
							<div class="fielditem">
								<span class="icon-envelope icon"></span>
								<input type="text" class="original" id="email" name="email" placeholder="请输入邮箱" value="" autocomplete="off"/>
							</div>
							<c:if test="${registerSetting.isCheckEmail=='1'}">
								<div class="fielditem">
									<span class="icon-verified_user icon"></span>
									<input type="text" class="original" id="identifying_code" placeholder="请输入验证码" value="" autocomplete="off"/>
									<div class="btcode">
										<div id="get_code" class="moe-bottom-button" onclick="getCode()">获取验证码</div>
									</div>
								</div>
							</c:if>
						</div>
						<div class="tags">
							<a class="r" href="${pageContext.request.contextPath}/forum/user_info/login.action">已有账号？现在登录</a>
						</div>
						<div class="btbar">
							<img src="${pageContext.request.contextPath}/static/default/images/button_register.png" onclick="register()" />
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
	var isCheckEmail = "${registerSetting.isCheckEmail}";
	var index = 0;
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
		
		if (index==0) {
			// 第一次发送
			$.ajax({
				url : "${pageContext.request.contextPath}/forum/user_info/register.json",
				type : "POST",
				dataType : "json",
				data : $("#form").serialize(),
				success : function(rtn) {
					if (rtn.code=="000000") {
						var info = rtn.data.info;
						
						delCookie("userToken");
						setCookie("userToken", info.userToken);
						
						// 注册成功，发送验证码
						index = index +1;
						sendEmailCode();
					} else {
						$("#error-msg").text(rtn.message);
					}
				}
			});
		} else {
			// 重新发送邮箱验证码
			sendEmailCode();
		}
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
	
	// 发送邮件验证码
	function sendEmailCode() {
		$("#error-msg").text("");
		$.ajax({
			url : "${pageContext.request.contextPath}/forum/user_info/register_email.json",
			type : "POST",
			dataType : "json",
			data : {
				"userToken" : getCookie("userToken")
			},
			success : function(rtn) {
				if (rtn.code=="000000") {
					javaex.optTip({
						content : "邮件发送成功，请前往查收验证码",
						type : "success"
					});
				} else {
					$("#error-msg").text(rtn.message);
				}
			}
		});
	}
	
	// 点击注册
	function register() {
		$("#error-msg").text("");
		if (isCheckEmail=="0") {
			// 不需要验证码
			$.ajax({
				url : "${pageContext.request.contextPath}/forum/user_info/register.json",
				type : "POST",
				dataType : "json",
				data : $("#form").serialize(),
				success : function(rtn) {
					if (rtn.code=="000000") {
						var info = rtn.data.info;
						
						delCookie("userToken");
						setCookie("userToken", info.userToken);
						
						// 注册成功，自动跳转到首页
						javaex.optTip({
							content : "注册成功，即将为您跳转到首页",
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
		} else {
			// 需要验证码
			validate();
		}
	}
	
	// 激活账号
	function validate() {
		$("#error-msg").text("");
		$.ajax({
			url : "${pageContext.request.contextPath}/forum/user_info/update_user_status.json",
			type : "POST",
			dataType : "json",
			data : {
				"identifyingCode" : $("#identifying_code").val(),
				"userToken" : getCookie("userToken")
			},
			success : function(rtn) {
				if (rtn.code=="000000") {
					javaex.optTip({
						content : "注册成功，即将为您跳转到首页",
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
