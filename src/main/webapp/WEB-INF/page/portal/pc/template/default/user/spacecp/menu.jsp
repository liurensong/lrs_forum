<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="w-left">
	<div class="u-box-l">
		<div class="u-face">
			<c:choose>
				<c:when test="${empty userInfo.avatar}">
					<img src="${pageContext.request.contextPath}/static/common/images/noavatar.png" />
				</c:when>
				<c:otherwise>
					<img src="${userInfo.avatar}" />
				</c:otherwise>
			</c:choose>
			<div>${userInfo.loginName}</div>
		</div>
		<ul class="user_link">
			<li><a href="${pageContext.request.contextPath}/forum/user_info/spacecp_profile.action"><span class="icon"><i class="icon-chevron-right"></i></span> 个人资料</a></li>
			<li><a href="${pageContext.request.contextPath}/forum/user_info/spacecp_point.action"><span class="icon"><i class="icon-chevron-right"></i></span> 积分</a></li>
			<li><a href="${pageContext.request.contextPath}/forum/user_info/spacecp_avatar.action"><span class="icon"><i class="icon-chevron-right"></i></span> 修改头像</a></li>
			<li><a href="${pageContext.request.contextPath}/forum/user_info/spacecp_usergroup.action"><span class="icon"><i class="icon-chevron-right"></i></span> 用户组</a></li>
			<li><a href="${pageContext.request.contextPath}/forum/user_info/spacecp_password.action"><span class="icon"><i class="icon-chevron-right"></i></span> 密码安全</a></li>
			<li><a href="${pageContext.request.contextPath}/forum/user_info/spacecp_thread.action"><span class="icon"><i class="icon-chevron-right"></i></span> 帖子</a></li>
		</ul>
	</div>
</div>
<script>
	// 高亮菜单
	$(function() {
		var userLink = "${userLink}";
		$('ul.user_link li>a').each(function() {
			var url = $(this).attr("href");
			if (url.indexOf(userLink)>=0) {
				$(this).parent().addClass("active");
			}
		});
	});
</script>