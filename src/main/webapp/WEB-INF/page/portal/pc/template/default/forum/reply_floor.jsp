<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>回复楼层</title>
<c:import url="../common/common.jsp"></c:import>
<!--代码高亮-->
<link href="${pageContext.request.contextPath}/static/default/javaex/pc/lib/highlight.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/default/javaex/pc/lib/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
<style>
.edit-editor-body .edit-body-container{min-height: 340px;}
</style>
</head>

<body>
	<div class="main">
		<c:if test="${!empty threadReplyInfo}">
			<div class="pbt">
				<div class="quote">
					<blockquote>
						<font size="2">
							<font color="#999">${threadReplyInfo.loginName} 发表于 ${threadReplyInfo.replyTime}</font>
							<br>
							${threadReplyInfo.contentText}
						</font>
					</blockquote>
				</div>
			</div>
		</c:if>
		
		<!-- 发表回复 -->
		<div>
			<div id="edit" class="edit-container"></div>
			<input type="hidden" id="contentHtml" name="contentHtml" value="" />
			<input type="hidden" id="contentText" name="contentText" value="" />
			
			<input type="button" id="save_reply_floor" class="button post-send" style="width:auto;margin-top: 10px;float:right;" value="参与/回复主题" />
		</div>
	</div>
</body>
<script>
	var tid = "${tid}";
	var replyId = "${replyId}";
</script>
<script src="${pageContext.request.contextPath}/static/common/js/forum/reply_floor.js"></script>
</html>
