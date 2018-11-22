<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>uscat论坛系统安装</title>
<c:import url="../common/common.jsp"></c:import>
<style>
.bg-wrap, body, html {
	height: 100%;
}
.bg-wrap {
	background: url(${pageContext.request.contextPath}/static/common/images/login_bg.png) top left no-repeat;
	background-size: cover;
	overflow: hidden;
}
.main-cont-wrap {
	z-index: 1;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-left: -190px;
	margin-top: -255px;
	box-sizing: border-box;
	width: 380px;
	padding: 30px 30px 40px;
	background: #fff;
	box-shadow: 0 20px 30px 0 rgba(63,63,65,.06);
	border-radius: 10px;
}
.g-mb40 {
	margin-bottom: 40px;
}
.g-mb30 {
	margin-bottom: 30px;
}
.s-txt-c, .ui-protocol a:hover {
	color: #2589ff;
}
.s-fs20 {
	font-size: 20px;
}
input {
	line-height: normal;
}
input[type="text"], input[type="password"], textarea {
	border: 0;
	outline: 0;
}
input {
	-webkit-appearance: textfield;
	background-color: white;
	-webkit-rtl-ordering: logical;
	cursor: text;
	padding: 1px;
	border-width: 2px;
	border-style: inset;
	border-color: initial;
	border-image: initial;
}
input, textarea, select, button {
	text-rendering: auto;
	color: initial;
	letter-spacing: normal;
	word-spacing: normal;
	text-transform: none;
	text-indent: 0px;
	text-shadow: none;
	display: inline-block;
	text-align: start;
	margin: 0em;
	font: 400 13.3333px Arial;
}
.ui-form-item {
	position: relative;
	height: 40px;
	line-height: 40px;
	border-bottom: 1px solid #e3e3e3;
	box-sizing: border-box;
}
.ui-form-txt {
	display: inline-block;
	width: 70px;
	color: #595961;
	font-size: 14px;
	margin-right: 10px;
}
.ui-form-input {
	width: 155px;
}
.ui-form-input, .ui-form-input1 {
	border: 0;
	outline: 0;
	font-size: 14px;
	color: #595961;
}
.ui-form-btn {
	margin-top: 40px;
}
.ui-button {
	display: block;
	width: 320px;
	height: 50px;
	text-align: center;
	color: #fff;
	background: #2589ff;
	border-radius: 6px;
	font-size: 16px;
	border: 0;
	outline: 0;
}
input[type=button] {
	-webkit-appearance: button;
	cursor: pointer;
}
</style>
</head>

<body>
	<div class="bg-wrap">
		<div class="main-cont-wrap login-model">
			<form id="form">
				<div class="form-title g-mb40">
					<span class="s-txt-c s-fs20">uscat论坛系统安装</span>
				</div>
				<div class="ui-form-item g-mb30">
					<span class="ui-form-txt">账号</span>
					<input type="text" class="ui-form-input original" name="loginName" placeholder="请输入账号" data-type="必填" error-pos="32" />
				</div>
				<div class="ui-form-item g-mb30">
					<span class="ui-form-txt">密码</span>
					<input type="password" class="ui-form-input original" name="password" placeholder="请输入密码" data-type="必填" error-pos="32" />
				</div>
				<div class="ui-form-item g-mb30">
					<span class="ui-form-txt">邮箱</span>
					<input type="text" class="ui-form-input original" name="email" placeholder="请输入邮箱" data-type="邮箱" error-msg="邮箱格式错误" error-pos="32" />
				</div>
				<div class="ui-form-btn">
					<input class="ui-button login-btn" type="button" id="save" value="创建管理员账号" />
				</div>
			</form>
		</div>
	</div>
</body>
<script>
	$("#save").click(function() {
		// 表单验证函数
		if (javaexVerify()) {
			$.ajax({
				url : "${pageContext.request.contextPath}/forum/user_info/create_admin.json",
				type : "POST",
				dataType : "json",
				data : $("#form").serialize(),
				success : function(rtn) {
					if (rtn.code=="000000") {
						var info = rtn.data.info;
						
						delCookie("userToken");
						setCookie("userToken", info.userToken);
						
						javaex.optTip({
							content : "管理员账号创建成功，即将为您跳转到后台首页",
							type : "success"
						});
						// 建议延迟加载
						setTimeout(function() {
							window.location.href = "${pageContext.request.contextPath}/admin/index.action";
						}, 2000);
					} else {
						javaex.optTip({
							content : rtn.message,
							type : "error"
						});
					}
				}
			});
		}
	});
</script>
</html>