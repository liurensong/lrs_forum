<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>基本设置</title>
<c:import url="../common/common.jsp"></c:import>
<style>
.unit .left {
	width: 180px !important;
}
</style>
</head>

<body>
	<!-- 面包屑导航和主体内容 -->
	<div class="content">
		<!--面包屑导航-->
		<div class="content-header">
			<div class="breadcrumb">
				<span>防灌水</span>
				<span class="divider">/</span>
				<span class="active">基本设置</span>
			</div>
		</div>
		
		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--修饰块元素名称-->
				<div class="banner">
					<p class="tab fixed">基本设置</p>
				</div>
				<!--正文-->
				<div class="main">
					<!--表单-->
					<form id="form">
						<!--两次发表时间间隔(秒)-->
						<div class="unit">
							<div class="left">
								<span class="required">*</span><p class="subtitle">两次发表时间间隔(秒)</p>
							</div>
							<div class="right">
								<input type="text" class="text" name="reportTimeInterval" style="width:auto;" value="${securityInfo.reportTimeInterval}" data-type="非负整数" error-msg="填写0或正整数"/>
								<p class="hint">两次发帖间隔小于此时间，0 为不限制</p>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--强制用户验证激活邮箱-->
						<div class="unit">
							<div class="left"><p class="subtitle">强制用户验证激活邮箱</p></div>
							<div class="right">
								<ul class="equal-8">
									<c:choose>
										<c:when test="${securityInfo.isActivateEmail=='1'}">
											<li><input type="radio" class="fill" name="isActivateEmail" value="1" checked/>是</li>
											<li><input type="radio" class="fill" name="isActivateEmail" value="0" />否</li>
										</c:when>
										<c:otherwise>
											<li><input type="radio" class="fill" name="isActivateEmail" value="1" />是</li>
											<li><input type="radio" class="fill" name="isActivateEmail" value="0" checked/>否</li>
										</c:otherwise>
									</c:choose>
									<span class="clearfix"></span>
								</ul>
								<p class="hint">选择是的话，用户必须验证激活自己的邮箱后，才可以进行发布操作</p>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--强制用户上传头像-->
						<div class="unit">
							<div class="left"><p class="subtitle">强制用户上传头像</p></div>
							<div class="right">
								<ul class="equal-8">
									<c:choose>
										<c:when test="${securityInfo.isUploadAvatar=='1'}">
											<li><input type="radio" class="fill" name="isUploadAvatar" value="1" checked/>是</li>
											<li><input type="radio" class="fill" name="isUploadAvatar" value="0" />否</li>
										</c:when>
										<c:otherwise>
											<li><input type="radio" class="fill" name="isUploadAvatar" value="1" />是</li>
											<li><input type="radio" class="fill" name="isUploadAvatar" value="0" checked/>否</li>
										</c:otherwise>
									</c:choose>
									<span class="clearfix"></span>
								</ul>
								<p class="hint">选择是的话，用户必须设置自己的头像后才能进行发布操作</p>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--提交按钮-->
						<div class="unit">
							<input type="button" id="save" class="button yes" value="保存" />
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