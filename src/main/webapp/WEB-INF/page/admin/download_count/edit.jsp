<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>下载计数器编辑</title>
<c:import url="../common/common.jsp"></c:import>
</head>
<body>
	<!-- 面包屑导航和主体内容 -->
	<div class="content">
		<!--面包屑导航-->
		<div class="content-header">
			<div class="breadcrumb">
				<span>接口</span>
				<span class="divider">/</span>
				<span class="active">下载计数器编辑</span>
			</div>
		</div>
		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--修饰块元素名称-->
				<div class="banner">
					<p class="tab fixed">${downloadCount.name}</p>
				</div>
				<!--正文-->
				<div class="main">
					<!--表单-->
					<form id="form">
						<input type="hidden" name="id" value="${downloadCount.id}" />
						
						<!--上传文件-->
						<div class="unit">
							<div class="left">
								<span class="required">*</span><p class="subtitle">上传文件</p>
							</div>
							<div class="right">
								<a href="javascript:;" class="button indigo" style="padding:0;">
									<label for="upload" style="padding:8px 12px;"><span class="icon-upload"></span> 上传</label>
								</a>
								<input type="file" class="hide" id="upload" />
								<span><input type="text" class="text" id="url" name="url" data-type="必填" error-pos="52" value="${downloadCount.url}" /></span>
								<p class="hint">可以不上传，直接填写文件地址，亦可填写诸如百度网盘地址</p>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--图标-->
						<div class="unit">
							<div class="left">
								<p class="subtitle">图标</p>
							</div>
							<div class="right">
								<div class="grid-1-9">
									<div style="min-width:80px;">
										<label for="upload_icon" style="display: inline-block; width:60px;height:60px;">
											<c:choose>
												<c:when test="${empty downloadCount.icon}">
													<img id="upload_icon_image" src="${pageContext.request.contextPath}/static/default/javaex/pc/images/grey.gif" width="100%" height="100%" />
												</c:when>
												<c:otherwise>
													<img id="upload_icon_image" src="${downloadCount.icon}" width="100%" height="100%" />
												</c:otherwise>
											</c:choose>
										</label>
										<input type="file" class="hide" id="upload_icon" accept="image/gif, image/jpeg, image/jpg, image/png" />
									</div>
									<div style="position: relative;">
										<input type="text" id="icon" class="text" style="position: absolute;bottom: 0;" name="icon" value="${downloadCount.icon}" />
									</div>
								</div>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--简介-->
						<div class="unit">
							<div class="left"><p class="subtitle">简介</p></div>
							<div class="right">
								<textarea name="remark" class="desc">${downloadCount.remark}</textarea>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--下载次数-->
						<div class="unit">
							<div class="left">
								<span class="required">*</span><p class="subtitle">下载次数</p>
							</div>
							<div class="right">
								<input type="text" class="text" name="count" style="width:300px;" data-type="非负整数" error-msg="填写0或正整数" value="${downloadCount.count}" />
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--是否需要登录-->
						<div class="unit">
							<div class="left"><p class="subtitle">必须登录</p></div>
							<div class="right">
								<ul class="equal-8">
									<c:choose>
										<c:when test="${downloadCount.isLogin=='1'}">
											<li><input type="radio" class="fill" name="isLogin" value="1" checked/>是</li>
											<li><input type="radio" class="fill" name="isLogin" value="0" />否</li>
										</c:when>
										<c:otherwise>
											<li><input type="radio" class="fill" name="isLogin" value="1" />是</li>
											<li><input type="radio" class="fill" name="isLogin" value="0" checked/>否</li>
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
								<a href="javascript:history.back();">
									<input type="button" id="return" class="button no" value="返回" />
								</a>
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
	
	// 上传文件
	javaex.upload({
		type : "file",
		url : "${pageContext.request.contextPath}/download_count/upload_file.json?type=qiniu",	// 请求路径
		id : "upload",			// <input type="file" />的id
		param : "file",			// 参数名称，SSM中与MultipartFile的参数名保持一致
		callback : function (rtn) {
			if (rtn.code=="000000") {
				$("#url").val(rtn.data.fileUrl);
			} else {
				javaex.optTip({
					content : rtn.msg,
					type : "error"
				});
			}
		}
	});
	
	// 上传图标
	javaex.upload({
		type : "image",
		url : "${pageContext.request.contextPath}/forum/upload_info/upload_image.json?type=qiniu",	// 请求路径
		id : "upload_icon",	// <input type="file" />的id
		maxSize : "5120",
		param : "file",			// 参数名称，SSM中与MultipartFile的参数名保持一致
		dataType : "url",		// 返回的数据类型：base64 或 url
		callback : function (rtn) {
			// 后台返回的数据
			if (rtn.code=="000000") {
				var imgUrl = rtn.data.imgUrl;
				$("#upload_icon_image").attr("src", imgUrl);
				$("#icon").val(imgUrl);
			} else {
				javaex.optTip({
					content : rtn.msg,
					type : "error"
				});
			}
		}
	});
	
	// 点击保存按钮事件
	$("#save").click(function() {
		// 表单验证函数
		if (javaexVerify()) {
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			// 向后台提交
			$.ajax({
				url : "update.json",
				type : "POST",
				dataType : "json",
				traditional : "true",
				data :  $("#form").serialize(),
				success : function(rtn) {
					if (rtn.code=="000000") {
						javaex.optTip({
							content : rtn.message,
							type : "success"
						});
						// 建议延迟加载
						setTimeout(function() {
							// 跳转页面
							history.back();
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