<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${title}</title>
<meta name="keywords" content="${keywords}" />
<meta name="description" content="${description}" />
<c:import url="../common/common.jsp"></c:import>
<!--代码高亮-->
<link href="${pageContext.request.contextPath}/static/default/javaex/pc/lib/highlight.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/default/javaex/pc/lib/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
<style>
.unit .right {
	width: 89%;
}
.edit-editor-body .edit-body-container {
	min-height: 320px;
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
		<div class="list-content" style="width: 1200px;margin:0 auto 20px auto;">
			<div class="block no-shadow">
				<div class="main-0">
					<div class="tab-bar">
						<div class="title">
							<a href="${pageContext.request.contextPath}/"><span class="icon-home2"></span> 论坛</a> / 
							<a href="${pageContext.request.contextPath}/forum/board_list.action?gid=${zoneInfo.gid}"><span>${zoneInfo.name}</span></a> / 
							<a href="${pageContext.request.contextPath}/forum/forumdisplay.action?fid=${boardInfo.fid}"><span>${boardInfo.name}</span></a>
						</div>
					</div>
					
					<div class="main">
						<!--帖子分类-->
						<c:if test="${fn:length(subjectClassList)>0}">
							<div class="unit">
								<div class="left"><span class="required">*</span><p class="subtitle">主题分类</p></div>
								<div class="right">
									<select id="subject_id">
										<c:forEach items="${subjectClassList}" var="subjectClass">
											<option value="${subjectClass.subjectId}">${subjectClass.nameText}</option>
										</c:forEach>
									</select>
								</div>
								<span class="clearfix"></span>
							</div>
						</c:if>
						
						<!--帖子标题-->
						<div class="unit">
							<div class="left"><span class="required">*</span><p class="subtitle">帖子标题</p></div>
							<div class="right">
								<input type="text" class="text" id="title" maxlength="80" data-type="必填" placeholder="请输入帖子标题，字数不超过80字">
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--帖子内容-->
						<div class="unit">
							<div class="left"><span class="required">*</span><p class="subtitle">帖子内容</p></div>
							<div class="right">
								<div id="edit" class="edit-container"></div>
								<input type="hidden" id="contentHtml" name="contentHtml" value="" />
								<input type="hidden" id="contentText" name="contentText" value="" />
							</div>
							<span class="clearfix"></span>
						</div>
						
						<!--提交按钮-->
						<div class="unit">
							<div class="left"></div>
							<div class="right">
								<input type="button" id="save" class="button post-send" style="margin:0;" value="发表帖子" />
							</div>
							<span class="clearfix"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!--底部-->
	<c:import url="../common/footer.jsp"></c:import>
</body>
<script>
var fid = "${fid}";
</script>
<script src="${pageContext.request.contextPath}/static/common/js/forum/newthread.js"></script>
</html>
