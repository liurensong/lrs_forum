<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="shortcut icon" href=${pageContext.request.contextPath}/static/favicon.ico type=image/x-icon>
<!-- 头部 -->
<div class="uscat-head">
	<div class="uscat-content">
		<!--logo-->
		<div class="logo">
			<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/static/default/images/logo.png"></a>
		</div>
		
		<!--导航-->
		<div class="nav">
			<ul>
				<c:if test="${!empty navlist}">
					<c:forEach items="${navlist}" var="navInfo">
						<c:choose>
							<c:when test="${!empty active && fn:contains(navInfo.link, active)}">
								<li class="active"><a href="${pageContext.request.contextPath}/${navInfo.link}">${navInfo.name}</a></li>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${fn:contains(navInfo.link, 'http')}">
										<li><a href="${navInfo.link}" target="_blank">${navInfo.name}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.request.contextPath}/${navInfo.link}">${navInfo.name}</a></li>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
			</ul>
		</div>
		
		<!-- 搜索 -->
		<div class="search-box-cover">
			<div class="sbox-bar">
				<input id="keyword" class="sboxword" value="${keyword}" onkeydown="if(event.keyCode==13){search();}">
				<div class="search" onclick="search();"></div>
			</div>
		</div>
		
		<!-- 用户信息 -->
		<c:choose>
			<c:when test="${userInfo==null}">
				<!-- 未登录 -->
				<div class="nav-r">
					<ul>
						<li>
							<a href="${pageContext.request.contextPath}/forum/user_info/login.action">
								<span class="icon">
									<img src="${pageContext.request.contextPath}/static/common/images/wicon_face.png" />
								</span>
							</a>
						</li>
						<li>
							<span class="t">
								<a href="${pageContext.request.contextPath}/forum/user_info/register.action">注册</a>
								|
								<a href="${pageContext.request.contextPath}/forum/user_info/login.action">登录</a>
							</span>
						</li>
					</ul>
				</div>
			</c:when>
			<c:otherwise>
				<!-- 已登录 -->
				<div id="head_user_box" class="index-spot index-spot01">
					<div class="index-me index-me01">
						<span>
							<a target="_blank" href="#">
								<span class="icon-notifications_none"></span>
							</a>
							<span class="head_info_tag">0</span>
						</span>
					</div>
					<div class="idst_l" style="border-color: rgb(194, 194, 194);">
						<a class="head_user_avator" href="javascript:;" style="opacity: 1;">
							<c:choose>
								<c:when test="${empty userInfo.avatar}">
									<img class="touxiang" src="${pageContext.request.contextPath}/static/common/images/noavatar.png" />
								</c:when>
								<c:otherwise>
									<img class="touxiang" src="${userInfo.avatar}" />
								</c:otherwise>
							</c:choose>
						</a>
						<div class="zoom_user_avator zoomIn_user_avator" style="opacity: 0;">
							<a class="a_link_home" href="${pageContext.request.contextPath}/forum/user_info/spacecp_point.action" target="_blank">
								<c:choose>
									<c:when test="${empty userInfo.avatar}">
										<img src="${pageContext.request.contextPath}/static/common/images/noavatar.png" />
									</c:when>
									<c:otherwise>
										<img src="${userInfo.avatar}" />
									</c:otherwise>
								</c:choose>
							</a>
						</div>
						<div class="head_container" id="head_container" >
							<div class="head_btn_wrap">
								<div class="btn_wrap_item clr">
									<span class="head_signin" style="background-color: transparent;">&nbsp;</span>
<!-- 									<span class="head_signin">签到</span> -->
								</div>
								<div class="btn_wrap_item level_center_box">
									<p class="head_nickname">${userInfo.loginName}</p>
									<p class="head_user_level">${userInfo.groupName}</p>
									<div class="progress">
										<div class="level"></div>
									</div>
									<p id="user_point" class="head_level_nums text_right">0 / 0</p>
								</div>
								<div class="btn_wrap_item">
									<ul class="link_btn_wrap">
										<a class="a_link_home" href="${pageContext.request.contextPath}/forum/user_info/spacecp_point.action" target="_blank">
											<li class="link_btn_item"><span class="link_btn_icon icon-home2"></span>个人主页
											</li>
										</a>
										<a class="a_link_profile" href="${pageContext.request.contextPath}/forum/user_info/spacecp_thread.action" target="_blank">
											<li class="link_btn_item"><span class="link_btn_icon icon-file-text-o"></span>我的帖子
											</li>
										</a>
										<a class="a_link_profile" href="${pageContext.request.contextPath}/forum/user_info/spacecp_password.action" target="_blank">
											<li class="link_btn_item"><span class="link_btn_icon icon-security"></span>修改密码
											</li>
										</a>
										<a class="a_link_profile" href="${pageContext.request.contextPath}/forum/user_info/spacecp_profile.action" target="_blank">
											<li class="link_btn_item"><span class="link_btn_icon icon-user-o"></span>个人资料
											</li>
										</a>
									</ul>
								</div>
								<div class="btn_wrap_item last_btn_item">
									<c:if test="${userInfo.groupName=='管理员'}">
										<a href="${pageContext.request.contextPath}/admin/index.action" target="_blank" class="head_btn_logout" style="float: left;">后台管理</a>
									</c:if>
									<span class="head_btn_logout" onclick="logout()">退出</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<script>
	// 搜索
	function search() {
		var keyword = $("#keyword").val();
		
		// 判断关键词是否为空
		keyword = keyword.replace(/(^\s*)|(\s*$)/g, "");
		if (keyword!="") {
			window.location.href = "${pageContext.request.contextPath}/forum/search.action?keyword="+ keyword;
		}
	}
	
	// 退出登录
	function logout() {
		$.ajax({
			url : "${pageContext.request.contextPath}/forum/user_info/logout.json",
			type : "POST",
			dataType : "json",
			success : function(rtn) {
				if (rtn.code=="000000") {
					delCookie("userToken");
					window.location.reload();
				}
			}
		});
	}
	
	$(function() {
		// 获取用户当前积分和晋升下一用户组所需的最低积分
		if (!!getCookie("userToken")) {
			$.ajax({
				url : "${pageContext.request.contextPath}/forum/user_count/select_point.json",
				type : "POST",
				dataType : "json",
				success : function(rtn) {
					if (rtn.code=="000000") {
						var percent = "100%";
						if (rtn.data.groupType=="system") {
							$("#user_point").html(rtn.data.curPoint + " / 0");
						} else {
							$("#user_point").html(rtn.data.curPoint + " / " + rtn.data.nextPoint);
							percent = parseInt((rtn.data.curPoint / rtn.data.nextPoint) * 100) + "%";
						}
						javaex.progress({
							percent : percent,
							selector : ".progress .level"
						});
					}
				}
			});
		}
	});
</script>
<script src="${pageContext.request.contextPath}/static/common/js/forum/header.js"></script>
