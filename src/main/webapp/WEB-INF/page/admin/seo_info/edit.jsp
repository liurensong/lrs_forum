<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>SEO设置</title>
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
				<span>全局</span>
				<span class="divider">/</span>
				<span class="active">SEO设置</span>
			</div>
		</div>
		
		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--正文-->
				<div class="main-0">
					<!--选项卡切换-->
					<div id="tab" class="tab tab-nav">
						<!--选项卡标题部分-->
						<div class="tab-title">
							<ul>
								<li>门户首页</li>
								<li>论坛首页</li>
								<span class="clearfix"></span>
							</ul>
						</div>
						<!--选项卡内容部分-->
						<div class="tab-content">
							<div>
								<div class="main">
									<p class="tip">SEO优化提醒</p>
									<div class="word">
										<ul>
											<li>站点名称&nbsp;<font class="highlight">{bbname}</font>（应用范围：所有位置）</li>
										</ul>
									</div>
									<form id="form1">
										<input type="hidden" name="type" value="portal">
										<!--title-->
										<div class="unit">
											<div class="left">
												<p class="subtitle">title</p>
											</div>
											<div class="right">
												<input type="text" class="text" name="title" value="${seoPortal.title}" />
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
												<input type="text" class="text" name="keywords" value="${seoPortal.keywords}" />
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
												<textarea class="desc" name="description">${seoPortal.description}</textarea>
												<p class="hint">description用于搜索引擎优化，放在 meta 的 description 标签中</p>
											</div>
											<!--清浮动-->
											<span class="clearfix"></span>
										</div>
									</form>
								</div>
							</div>
							<div>
								<div class="main">
									<p class="tip">SEO优化提醒</p>
									<div class="word">
										<ul>
											<li>站点名称&nbsp;<font class="highlight">{bbname}</font>（应用范围：所有位置）</li>
										</ul>
									</div>
									<form id="form2">
										<input type="hidden" name="type" value="forum">
										<!--title-->
										<div class="unit">
											<div class="left">
												<p class="subtitle">title</p>
											</div>
											<div class="right">
												<input type="text" class="text" name="title" value="${seoForum.title}" />
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
												<input type="text" class="text" name="keywords" value="${seoForum.keywords}" />
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
												<textarea class="desc" name="description">${seoForum.description}</textarea>
												<p class="hint">description用于搜索引擎优化，放在 meta 的 description 标签中</p>
											</div>
											<!--清浮动-->
											<span class="clearfix"></span>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					
					<!--提交按钮-->
					<div class="unit" style="margin-top: 0;">
						<input type="button" id="save" class="button yes" value="保存" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	javaex.loading();
	
	var index = 1;	// 当前选中的标签
	javaex.tab({
		id : "tab",		// tab的id
		current : 1,	// 默认选中第几个选项卡
		mode : "click",	// 鼠标移动切换选项卡
		// 切换选项卡后返回一个对象，包含选项卡的索引，从1开始计
		// 初始化会自动执行一次
		callback: function (rtn) {
			index = rtn.index;
		}
	});
	
	// 监听点击保存按钮事件
	$("#save").click(function() {
		if (index==1) {
			// 门户首页
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "save.json",
				type : "POST",
				dataType : "json",
				data : $("#form1").serialize(),
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
		} else if (index==2) {
			// 论坛首页
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "save.json",
				type : "POST",
				dataType : "json",
				data : $("#form2").serialize(),
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