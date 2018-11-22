<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>分区详细设置</title>
<c:import url="../common/common.jsp"></c:import>
<style>
.unit .left {
	width: 120px !important;
}
.word {
	background-color: #f6f8fa;
	padding: 10px 20px;
	border-radius: 3px;
}
.highlight {
	color: #C00;
	font-weight: 700;
}
</style>
</head>

<body>
	<!-- 面包屑导航和主体内容 -->
	<div class="content">
		<!--面包屑导航-->
		<div class="content-header">
			<div class="breadcrumb">
				<span>板块</span>
				<span class="divider">/</span>
				<span class="active">分区详细设置</span>
			</div>
		</div>
		
		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--修饰块元素名称-->
				<div class="banner">
					<p class="tab fixed">${zoneInfo.name}（gid:${zoneInfo.gid}）</p>
				</div>

				<!--正文-->
				<div class="main">
					<!--表单-->
					<form id="form">
						<input type="hidden" name="gid" value="${zoneInfo.gid}" />
						
						<!--分区名称-->
						<div class="unit">
							<div class="left"><span class="required">*</span><p class="subtitle">分区名称</p></div>
							<div class="right">
								<input type="text" class="text" data-type="必填" name="name" value="${zoneInfo.name}" />
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--分区名称颜色-->
						<div class="unit">
							<div class="left"><p class="subtitle">名称颜色</p></div>
							<div class="right">
								<input type="text" class="text" name="color" value="${zoneInfo.color}" />
								<p class="hint">请填写#81b235这种格式</p>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--论坛首页下级子版块横排-->
						<div class="unit">
							<div class="left"><span class="required">*</span><p class="subtitle">子版块横排</p></div>
							<div class="right">
								<input type="text" class="text" data-type="正整数" error-msg="请填写正整数" name="row" value="${zoneInfo.row}" />
								<p class="hint">设置进入论坛首页时下级子版块横排时每行版块数量，至少填1</p>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--显示分区-->
						<div class="unit">
							<div class="left"><p class="subtitle">显示分区</p></div>
							<div class="right">
								<ul class="equal-8">
									<c:choose>
										<c:when test="${zoneInfo.isShow=='0'}">
											<li><input type="radio" class="fill" name="isShow" value="1" />是</li>
											<li><input type="radio" class="fill" name="isShow" value="0" checked/>否</li>
										</c:when>
										<c:otherwise>
											<li><input type="radio" class="fill" name="isShow" value="1" checked/>是</li>
											<li><input type="radio" class="fill" name="isShow" value="0" />否</li>
										</c:otherwise>
									</c:choose>
									<span class="clearfix"></span>
								</ul>
								<p class="hint">选择“否”将暂时将分区隐藏不显示，但分区内容仍将保留，且用户仍可通过 URL 访问到此分区及其版块</p>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<p class="tip">SEO优化设置（不填时，系统自动生成）</p>
						<div class="word">
							<ul>
								<li>站点名称&nbsp;<font class="highlight">{bbname}</font>（应用范围：所有位置）</li>
								<li>分区名称&nbsp;<font class="highlight">{fgroup}</font>（应用范围：除首页以外）</li>
							</ul>
						</div>
						
						<!--title-->
						<div class="unit">
							<div class="left">
								<p class="subtitle">title</p>
							</div>
							<div class="right">
								<input type="text" class="text" name="title" value="${zoneInfo.title}" />
							</div>
							<!--清浮动-->
							<span class="clearfix"></span>
						</div>
						
						<!--keywords-->
						<div class="unit">
							<div class="left">
								<p class="subtitle">keywords</p>
							</div>
							<div class="right">
								<input type="text" class="text" name="keywords" value="${zoneInfo.keywords}" />
								<p class="hint">keywords用于搜索引擎优化，放在 meta 的 keyword 标签中，多个关键字间请用半角逗号 "," 隔开</p>
							</div>
							<!--清浮动-->
							<span class="clearfix"></span>
						</div>
						
						<!--description-->
						<div class="unit">
							<div class="left">
								<p class="subtitle">description</p>
							</div>
							<div class="right">
								<textarea class="desc" name="description">${zoneInfo.description}</textarea>
								<p class="hint">description用于搜索引擎优化，放在 meta 的 description 标签中</p>
							</div>
							<!--清浮动-->
							<span class="clearfix"></span>
						</div>
						
						<!--提交按钮-->
						<div class="unit" style="width: 800px;">
							<div style="text-align: center;">
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
	
	// 点击保存按钮事件
	$("#save").click(function() {
		if (javaexVerify()) {
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "${pageContext.request.contextPath}/zone_info/save.json",
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
							window.location.href = "${pageContext.request.contextPath}/zone_info/list.action";
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
	
	// 监听点击返回按钮事件
	$("#return").click(function() {
		history.back();
	});
</script>
</html>
