<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>注册设置</title>
<c:import url="../common/common.jsp"></c:import>
<style>
.unit .left {
	width: 120px !important;
}
</style>
</head>

<body>
	<!-- 面包屑导航和主体内容 -->
	<div class="content">
		<!--面包屑导航-->
		<div class="content-header">
			<div class="breadcrumb">
				<span>全局</span>
				<span class="divider">/</span>
				<span class="active">注册设置</span>
			</div>
		</div>
		
		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--修饰块元素名称-->
				<div class="banner">
					<p class="tab fixed">注册设置</p>
				</div>
				<!--正文-->
				<div class="main">
					<!--表单-->
					<form id="form">
						<!--开放注册-->
						<div class="unit">
							<div class="left"><p class="subtitle">开放注册</p></div>
							<div class="right">
								<ul class="equal-8">
									<c:choose>
										<c:when test="${registerSetting.isAllowRegister=='0'}">
											<li><input type="radio" class="fill" name="isAllowRegister" value="1" />是</li>
											<li><input type="radio" class="fill" name="isAllowRegister" value="0" checked/>否</li>
										</c:when>
										<c:otherwise>
											<li><input type="radio" class="fill" name="isAllowRegister" value="1" checked/>是</li>
											<li><input type="radio" class="fill" name="isAllowRegister" value="0" />否</li>
										</c:otherwise>
									</c:choose>
									<span class="clearfix"></span>
								</ul>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--关闭注册提示信息-->
						<div class="unit">
							<div class="left">
								<p class="subtitle">关闭注册信息</p>
							</div>
							<div class="right">
								<textarea name="closeRegisterMessage" class="desc">${registerSetting.closeRegisterMessage}</textarea>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--验证邮箱-->
						<div class="unit">
							<div class="left"><p class="subtitle">验证邮箱</p></div>
							<div class="right">
								<ul class="equal-8">
									<c:choose>
										<c:when test="${registerSetting.isCheckEmail=='0'}">
											<li><input type="radio" class="fill" name="isCheckEmail" value="1" />是</li>
											<li><input type="radio" class="fill" name="isCheckEmail" value="0" checked/>否</li>
										</c:when>
										<c:otherwise>
											<li><input type="radio" class="fill" name="isCheckEmail" value="1" checked/>是</li>
											<li><input type="radio" class="fill" name="isCheckEmail" value="0" />否</li>
										</c:otherwise>
									</c:choose>
									<span class="clearfix"></span>
								</ul>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--提交按钮-->
						<div class="unit" style="width: 800px;">
							<div style="text-align: center;">
								<!--表单提交时，必须是input元素，并指定type类型为button，否则ajax提交时，会返回error回调函数-->
								<input type="button" id="save" class="button yes" value="保存" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	javaex.loading();
	
	// 点击保存按钮事件
	$("#save").click(function() {
		if (javaexVerify()) {
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "save.json",
				type : "POST",
				dataType : "json",
				data : $("#form").serialize(),
				success : function(rtn) {
					if (rtn.code=="000000") {
						javaex.optTip({
							content : rtn.message,
							type : "success"
						});
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