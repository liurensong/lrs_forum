<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>用户管理</title>
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
				<span>用户</span>
				<span class="divider">/</span>
				<span class="active">编辑用户</span>
			</div>
		</div>

		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--修饰块元素名称-->
				<div class="banner">
					<p class="tab fixed">用户编辑</p>
				</div>
				<!--正文-->
				<div class="main">
					<!--表单-->
					<form id="form" action="" method="">
						<input type="hidden" name="id" value="${id}" />
						
						<!--上传头像-->
						<div class="unit">
							<div class="left">
								<p class="subtitle">头像</p>
							</div>
							<div class="right">
								<div class="grid-1-9">
									<div style="min-width:142px;">
										<!-- 图片承载容器 -->
										<label for="upload" style="display: inline-block; width:130px;height:130px;">
											<c:choose>
												<c:when test="${empty editUserInfo.avatar}">
													<img id="upload_avatar" src="http://cdn.javaex.cn/javaex/pc/images/grey.gif" width="100%" height="100%" />
												</c:when>
												<c:otherwise>
													<img id="upload_avatar" src="${editUserInfo.avatar}" width="100%" height="100%" />
												</c:otherwise>
											</c:choose>
										</label>
										<input type="file" class="hide" id="upload" accept="image/gif, image/jpeg, image/jpg, image/png" />
									</div>
									<div style="position: relative;">
										<input type="text" id="avatar" class="text" style="position: absolute;bottom: 0;" name="avatar" value="${editUserInfo.avatar}" />
									</div>
								</div>
							</div>
							<!--清浮动-->
							<span class="clearfix"></span>
						</div>

						<!--账号-->
						<div class="unit">
							<div class="left">
								<span class="required">*</span><p class="subtitle">账号</p>
							</div>
							<div class="right">
								<c:choose>
									<c:when test="${empty editUserInfo.loginName}">
										<input type="text" class="text" name="loginName" data-type="必填" value="" />
									</c:when>
									<c:otherwise>
										<input type="text" class="text readonly" name="loginName" value="${editUserInfo.loginName}" readonly/>
									</c:otherwise>
								</c:choose>
								<p class="hint">编辑时，账号不可修改</p>
							</div>
							<span class="clearfix"></span>
						</div>

						<!--密码-->
						<div class="unit">
							<div class="left">
								<span class="required">*</span><p class="subtitle">密码</p>
							</div>
							<div class="right">
								<c:choose>
									<c:when test="${empty editUserInfo.password}">
										<input type="text" class="text" name="password" data-type="必填" value="${editUserInfo.password}" />
									</c:when>
									<c:otherwise>
										<input type="text" class="text" name="password" value="" />
									</c:otherwise>
								</c:choose>
								<p class="hint">编辑时，留空表示不修改密码</p>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--邮箱-->
						<div class="unit">
							<div class="left">
								<span class="required">*</span><p class="subtitle">邮箱</p>
							</div>
							<div class="right">
								<input type="text" class="text" name="email" data-type="邮箱" error-msg="邮箱格式错误" value="${editUserInfo.email}" />
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--邮箱状态-->
						<div class="unit">
							<div class="left"><p class="subtitle">邮箱状态</p></div>
							<div class="right">
								<ul class="equal-8">
									<c:choose>
										<c:when test="${editUserInfo.isEmailChecked=='1'}">
											<li><input type="radio" class="fill" name="isEmailChecked" value="1" checked/>已激活</li>
											<li><input type="radio" class="fill" name="isEmailChecked" value="0" />未激活</li>
										</c:when>
										<c:otherwise>
											<li><input type="radio" class="fill" name="isEmailChecked" value="1" />已激活</li>
											<li><input type="radio" class="fill" name="isEmailChecked" value="0" checked/>未激活</li>
										</c:otherwise>
									</c:choose>
									<span class="clearfix"></span>
								</ul>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--用户组-->
						<div class="unit">
							<div class="left">
								<p class="subtitle">用户组</p>
							</div>
							<div class="right">
								<select id="group_id" name="groupId">
									<c:forEach items="${groupList}" var="groupInfo" varStatus="status" >
										<option value="${groupInfo.id}" <c:if test="${groupInfo.id==editUserInfo.groupId}">selected</c:if>>${groupInfo.name}</option>
									</c:forEach>
								</select>
							</div>
							<span class="clearfix"></span>
						</div>

						<!--注册时间-->
						<div class="unit">
							<div class="left">
								<p class="subtitle">注册时间</p>
							</div>
							<div class="right">
								<input type="text" id="date1" name="registerTime" class="date" style="width: 200px;" value="${editUserInfo.registerTime}" readonly/>
							</div>
							<span class="clearfix"></span>
						</div>

						<!--注册 IP-->
						<div class="unit">
							<div class="left">
								<p class="subtitle">注册 IP</p>
							</div>
							<div class="right">
								<input type="text" class="text" name="registerIp" value="${editUserInfo.registerIp}" />
								<p class="hint">不填，则系统自动生成当前ip</p>
							</div>
							<span class="clearfix"></span>
						</div>

						<!--上次访问时间-->
						<div class="unit">
							<div class="left">
								<p class="subtitle">上次访问时间</p>
							</div>
							<div class="right">
								<input type="text" id="date2" name="lastLoginTime" class="date" style="width: 200px;" value="${editUserInfo.lastLoginTime}" readonly/>
							</div>
							<span class="clearfix"></span>
						</div>

						<!--上次访问 IP-->
						<div class="unit">
							<div class="left">
								<p class="subtitle">上次访问 IP</p>
							</div>
							<div class="right">
								<input type="text" class="text" name="lastLoginIp" value="${editUserInfo.lastLoginIp}" />
								<p class="hint">不填，则系统自动生成当前ip</p>
							</div>
							<span class="clearfix"></span>
						</div>

						<!--提交按钮-->
						<div class="unit" style="width: 800px;">
							<div style="text-align: center;">
								<!--表单提交时，必须是input元素，并指定type类型为button，否则ajax提交时，会返回error回调函数-->
								<input type="button" id="return" class="button no" value="返回" />
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

	javaex.select({
		id : "group_id"
	});
	
	var registerTime = "${editUserInfo.registerTime}";
	if (registerTime=="") {
		registerTime = javaex.getTime();
	}
	javaex.date({
		id : "date1",	// 承载日期组件的id
		isTime : true,
		date : registerTime
	});
	
	var lastLoginTime = "${editUserInfo.lastLoginTime}";
	if (lastLoginTime=="") {
		lastLoginTime = javaex.getTime();
	}
	javaex.date({
		id : "date2",	// 承载日期组件的id
		isTime : true,
		date : lastLoginTime
	});
	
	// 上传头像
	javaex.upload({
		type : "image",
		url : "${pageContext.request.contextPath}/forum/upload_info/upload_avatar.json?type=qiniu",	// 请求路径
		id : "upload",	// <input type="file" />的id
		maxSize : "5120",
		param : "file",			// 参数名称，SSM中与MultipartFile的参数名保持一致
		dataType : "url",		// 返回的数据类型：base64 或 url
		callback : function (rtn) {
			// 后台返回的数据
			if (rtn.code=="000000") {
				var imgUrl = rtn.data.imgUrl;
				$("#upload_avatar").attr("src", imgUrl);
				$("#avatar").val(imgUrl);
			} else {
				javaex.optTip({
					content : rtn.message,
					type : "error"
				});
			}
		}
	});
	
	$("#return").click(function() {
		history.back();
	});
	
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
						// 建议延迟加载
						setTimeout(function() {
							// 跳转页面
							window.location.href = "list_normal.action";
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
