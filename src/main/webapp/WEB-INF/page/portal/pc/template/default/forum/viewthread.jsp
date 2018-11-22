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
<script src="${pageContext.request.contextPath}/static/common/js/jquery.lazyload.js"></script>
<script type="text/javascript" charset="utf-8">
$(function() {
	$("img").lazyload({
		placeholder : "${pageContext.request.contextPath}/static/default/javaex/pc/images/grey.gif",
		effect: "fadeIn"
	});
});
</script>
<style>
ul.report>li{
	margin-bottom:0;
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
			<!--帖子内容-->
			<div class="block no-shadow">
				<div class="main-0">
					<div class="tab-bar">
						<div class="title">
							<a href="${pageContext.request.contextPath}/"><span class="icon-home2"></span> 论坛</a> / 
							<a href="${pageContext.request.contextPath}/forum/board_list.action?gid=${zoneInfo.gid}"><span>${zoneInfo.name}</span></a> / 
							<a href="${pageContext.request.contextPath}/forum/forumdisplay.action?fid=${boardInfo.fid}"><span>${boardInfo.name}</span></a>
						</div>
					</div>
					<c:if test="${!empty userInfo && userInfo.userPerm.isAdmin=='1'}">
						<div class="operation-wrap" style="text-align: right;">
							<select id="opt_select">
								<option value="">帖子管理</option>
								<option value="digest1">设为精华帖</option>
								<option value="digest0">取消精华帖</option>
								<option value="top1">置顶主题</option>
								<option value="top0">取消置顶</option>
								<option value="delete">删除主题</option>
							</select>
							<button class="button red" onclick="optThread()">操作</button>
						</div>
					</c:if>
					
					<table class="table" style="border-bottom:none;">
						<tbody>
							<tr>
								<td class="postauthor">
									<div class="hm">
										<span class="xg1">查看:</span> <span class="xi1">${threadInfo.viewCount}</span>
										<span class="pipe">|</span>
										<span class="xg1">回复:</span> <span class="xi1">${threadInfo.replyCount}</span>
									</div>
								</td>
								<td class="posttopic">${threadInfo.title}</td>
							</tr>
						</tbody>
					</table>
					<table class="table no-cross-line">
						<tbody>
							<c:forEach items="${pageInfo.list}" var="threadReplyInfo">
								<tr class="threadad">
									<td class="postauthor"></td>
									<td class="adcontent"></td>
								</tr>
								<tr>
									<td class="postauthor">
										<div class="poster">
											<span style="padding-left: 20px;">
												<a href="${pageContext.request.contextPath}/forum/user_info/space_profile.action?userId=${threadReplyInfo.userInfo.id}" target="_blank">${threadReplyInfo.userInfo.loginName}</a>
											</span>
										</div>
										<div>
											<div class="avatar">
												<a href="${pageContext.request.contextPath}/forum/user_info/space_profile.action?userId=${threadReplyInfo.userInfo.id}" target="_blank">
													<c:choose>
														<c:when test="${empty threadReplyInfo.userInfo.avatar}">
															<img src="${pageContext.request.contextPath}/static/common/images/noavatar.png" alt="头像" width="100%" height="100%" />
														</c:when>
														<c:otherwise>
															<img src="${threadReplyInfo.userInfo.avatar}" alt="头像" width="100%" height="100%" />
														</c:otherwise>
													</c:choose>
												</a>
											</div>
										</div>
										<ul class="otherinfo">
											<li><label>组别</label><span style="color:#9933CC"><font color="#9933CC">${threadReplyInfo.userInfo.status=='2'?'禁止访问':threadReplyInfo.userInfo.groupName}</font></span></li>
											<li><label>UID</label>${threadReplyInfo.userInfo.id}</li>
											<li><label>主题</label>${threadReplyInfo.userInfo.userCount.posts}</li>
											<li><label>帖子</label>${threadReplyInfo.userInfo.userCount.threads}</li>
										</ul>
									</td>
									<td class="postcontent">
										<div class="pi">
											<em>发表于 <span>${threadReplyInfo.replyTime}</span></em>
											<c:choose>
												<c:when test="${threadReplyInfo.floor=='1'}">
													<strong>楼主</strong>
												</c:when>
												<c:otherwise>
													<strong>${threadReplyInfo.floor}<sup>#</sup></strong>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="content">
											<!-- 引用 -->
											<c:if test="${!empty threadReplyInfo.quote}">
												<div class="quote">
													<blockquote>
														<font size="2">
															<font color="#999">${threadReplyInfo.quote.loginName} 发表于 ${threadReplyInfo.quote.replyTime} <strong>楼层 ${threadReplyInfo.quote.floor}<sup>#</sup></strong></font>
															<br>
															${threadReplyInfo.quote.contentText}
														</font>
													</blockquote>
												</div>
												<br>
											</c:if>
											<!-- 回帖内容 -->
											<c:choose>
												<c:when test="${threadReplyInfo.status=='1' && threadReplyInfo.userInfo.status=='1'}">
													${threadReplyInfo.contentHtml}
												</c:when>
												<c:otherwise>
													<div class="notfoud-container">
														<div class="img-404">
														</div>
														<p class="notfound-p">哎呀，内容被封印了...</p>
														<div class="notfound-reason">
															<p>可能的原因：</p>
															<ul>
																<li>内容含有敏感词汇</li>
																<li>作者正在关禁闭</li>
															</ul>
														</div>
													</div>
												</c:otherwise>
											</c:choose>
										</div>
									</td>
								</tr>
								<tr>
									<td class="postauthor"></td>
									<td class="postactions">
										<div class="p_control hin">
											<em>
												<c:choose>
													<c:when test="${threadReplyInfo.floor=='1'}">
														<a class="fastre" href="javascript:;" onclick="replyFloor()">回复</a>
													</c:when>
													<c:otherwise>
														<a class="fastre" href="javascript:;" onclick="replyFloor('${threadReplyInfo.id}')">回复</a>
													</c:otherwise>
												</c:choose>
												<c:if test="${!empty userInfo && (userInfo.id==threadReplyInfo.userInfo.id) && threadReplyInfo.status=='1'}">
													<a class="editp" href="javascript:;" onclick="editReply('${threadReplyInfo.id}')">编辑</a>
												</c:if>
											</em>
											<p style="float: right;">
												<a href="javascript:;" onclick="reportFloor('${threadReplyInfo.id}')">举报</a>
												<c:if test="${threadReplyInfo.floor!='1'}">
													<c:if test="${!empty userInfo && userInfo.userPerm.isAdmin=='1'}">
														<a href="javascript:;" onclick="deleteFloor('${threadReplyInfo.id}')" style="margin-left: 20px;"><b><span class="icon-trash-o"></span> 删除</b></a>
													</c:if>
												</c:if>
											</p>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			<!-- 分页 -->
			<div class="page">
				<a href="${pageContext.request.contextPath}/forum/newthread.action?fid=${threadInfo.fid}">
					<button class="button post-send" style="float:left;"><span class="icon-mode_edit"></span> 发新帖</button>
				</a>
				<a href="${pageContext.request.contextPath}/forum/forumdisplay.action?fid=${threadInfo.fid}">
					<button class="button post-send" <c:if test="${pageInfo.pages>1}">style="margin-top: -24px;"</c:if>><span class="icon-step-backward"></span> 返回列表</button>
				</a>
				<ul id="page" class="pagination"></ul>
			</div>
			
			<!--发表回复-->
			<div class="block no-shadow">
				<div class="main-0">
					<table class="table">
						<tbody>
							<tr>
								<td class="postauthor">
									
								</td>
								<td style="padding: 20px;">
									<div id="edit" class="edit-container"></div>
									<input type="hidden" id="contentHtml" name="contentHtml" value="" />
									<input type="hidden" id="contentText" name="contentText" value="" />
									
									<input type="button" id="save_reply" class="button post-send" style="width:auto;margin-top: 10px;" value="发表回复" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<!--底部-->
	<c:import url="../common/footer.jsp"></c:import>
</body>
<script>
	var tid = "${tid}";
	var currentPage = "${pageInfo.pageNum}";
	var pageCount = "${pageInfo.pages}";
</script>
<script src="${pageContext.request.contextPath}/static/common/js/forum/viewthread.js"></script>
</html>
