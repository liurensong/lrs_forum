<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>接口条件编辑</title>
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
				<span class="active">自定义数据接口条件编辑</span>
			</div>
		</div>
		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--修饰块元素名称-->
				<div class="banner">
					<p class="tab fixed">${apiInfo.name}</p>
				</div>
				<!--正文-->
				<div class="main">
					<!--表单-->
					<form id="form">
						<input type="hidden" name="id" value="${apiInfo.id}" />
						
						<!-- 数据来源 -->
						<div class="unit">
							<div class="left">
								<span class="required">*</span><p class="subtitle">数据来源</p>
							</div>
							<div class="right">
								<input type="checkbox" class="fill listen-1" name="fidAll" value="0" />全选
								<c:forEach items="${zoneList}" var="zoneInfo">
									<ul class="equal-4">
										<li>${zoneInfo.name}</li>
										<span class="clearfix"></span>
									</ul>
									<ul class="equal-4">
										<c:forEach items="${zoneInfo.boardList}" var="boardInfo">
											<li><input type="checkbox" class="fill listen-1-2" name="fid" value="${boardInfo.fid}" />${boardInfo.name}</li>
										</c:forEach>
										<span class="clearfix"></span>
									</ul>
								</c:forEach>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--主题排序字段-->
						<div class="unit">
							<div class="left"><p class="subtitle">排序字段</p></div>
							<div class="right">
								<ul class="equal-4">
									<c:choose>
										<c:when test="${empty apiInfo.orderField}">
											<li><input type="radio" class="fill" name="orderField" value="reply_time" checked/>回复时间</li>
											<li><input type="radio" class="fill" name="orderField" value="create_time" />发布时间</li>
											<li><input type="radio" class="fill" name="orderField" value="reply_count" />回复数量</li>
											<li><input type="radio" class="fill" name="orderField" value="view_count" />浏览次数</li>
										</c:when>
										<c:otherwise>
											<li><input type="radio" class="fill" name="orderField" value="reply_time" <c:if test="${apiInfo.orderField=='reply_time'}">checked</c:if>/>回复时间</li>
											<li><input type="radio" class="fill" name="orderField" value="create_time" <c:if test="${apiInfo.orderField=='create_time'}">checked</c:if>/>发布时间</li>
											<li><input type="radio" class="fill" name="orderField" value="reply_count" <c:if test="${apiInfo.orderField=='reply_count'}">checked</c:if>/>回复数量</li>
											<li><input type="radio" class="fill" name="orderField" value="view_count" <c:if test="${apiInfo.orderField=='view_count'}">checked</c:if>/>浏览次数</li>
										</c:otherwise>
									</c:choose>
									<span class="clearfix"></span>
								</ul>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--周期-->
						<div id="cycle" class="unit" style="display:none;">
							<div class="left"><p class="subtitle">排序周期</p></div>
							<div class="right">
								<ul class="equal-4">
									<c:choose>
										<c:when test="${empty apiInfo.cycle}">
											<li><input type="radio" class="fill" name="cycle" value="7" checked/>一周内</li>
											<li><input type="radio" class="fill" name="cycle" value="30" />一月内</li>
											<li><input type="radio" class="fill" name="cycle" value="0" />不限</li>
										</c:when>
										<c:otherwise>
											<li><input type="radio" class="fill" name="cycle" value="7" <c:if test="${apiInfo.cycle=='7'}">checked</c:if>/>一周内</li>
											<li><input type="radio" class="fill" name="cycle" value="30" <c:if test="${apiInfo.cycle=='30'}">checked</c:if>/>一月内</li>
											<li><input type="radio" class="fill" name="cycle" value="0" <c:if test="${apiInfo.cycle=='0'}">checked</c:if>/>不限</li>
										</c:otherwise>
									</c:choose>
									
									<span class="clearfix"></span>
								</ul>
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--获取条数-->
						<div class="unit">
							<div class="left">
								<span class="required">*</span><p class="subtitle">获取条数</p>
							</div>
							<div class="right">
								<input type="text" class="text" name="num" style="width:300px;" data-type="正整数" error-msg="必须填写正整数" value="${apiInfo.num}" />
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
	
	$(function() {
		// 回显数据
		// 勾选板块
		var fid = "${apiInfo.fid}";
		if (fid=="0") {
			$(':checkbox[name="fidAll"]').attr("checked", true);
			$(':checkbox[name="fid"]').attr("checked", true);
		} else {
			var fidArr = fid.split(",");
			for (var i=0; i<fidArr.length; i++) {
				$(':checkbox[name="fid"][value="'+fidArr[i]+'"]').attr("checked", true);
			}
		}
		
		// 显示周期
		var orderField = "${apiInfo.orderField}";
		if (orderField=="reply_count" || orderField=="view_count") {
			$("#cycle").show();
		}
	});
	
	// 切换排序字段事件
	$(':radio[name="orderField"]').on("change", function() {
		var orderField = $(this).val();
		if (orderField=="reply_time" || orderField=="create_time") {
			// 如果是回复时间或发布时间，则不需要设置周期
			$("#cycle").hide();
		} else {
			// 如果是回复数量或浏览次数，则可以设置周期
			$("#cycle").show();
		}
	});
	
	// 点击保存按钮事件
	$("#save").click(function() {
		if (javaexVerify()) {
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "user_save.json",
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