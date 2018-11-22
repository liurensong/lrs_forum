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
.moderator .fill-label{
	float: right;
	margin-top: 14px;
	margin-right: 20px;
}
em.zhuti {
	color: #007CD5;
}
</style>
</head>

<body>
	<!-- 头部和导航 -->
	<c:import url="../common/header.jsp"></c:import>
	
	<div class="mainwarp" style="background-image: url(${boardInfo.banner});">
		<div class="list-content" style="width: 1200px;margin:0 auto 20px auto;background-color: #FFF;">
			<div class="board-top-info">
				<div class="ba-icon">
					<img src="${boardInfo.icon}" />
				</div>
				<div style="display: inline-block;margin-right: 20px;float: left;">
					<p class="ba-name">
						<span class="ba-name-txt">${zoneInfo.name} / ${boardInfo.name}</span>
					</p>
					<p class="ba-info">
						<span class="label">今日</span>
						<span class="value">${boardInfo.threadCountToday}</span>
						<span class="label">主题</span>
						<span class="value">${boardInfo.threadCount}</span>
						<span class="label">帖数</span>
						<span class="value">${boardInfo.threadReplyCount}</span>
					</p>
				</div>
			</div>
			
			<div class="grid-11-4">
				<!-- 左侧区域 -->
				<div style="min-width:879px;border-right: 1px solid #eee;">
					<div id="tab" class="tab">
						<!--选项卡标题部分-->
						<div class="tab-title post-type-nav">
							<ul>
								<li><span class="nav-item">看贴</span></li>
								<li><span class="nav-item">精华</span></li>
								<a href="${pageContext.request.contextPath}/forum/newthread.action?fid=${fid}" class="more" style="float: right;margin-top:7px;margin-right: 10px;">
									<button class="button post-send" style="float:left;"><span class="icon-mode_edit"></span> 发新帖</button>
								</a>
								<span class="clearfix"></span>
							</ul>
						</div>
						<!--选项卡内容部分-->
						<div class="tab-content">
							<div>
								<!-- 主题分类 -->
								<c:if test="${fn:length(subjectClassList)>0}">
									<div class="main-10" style="background-color: #f7f7f7;border: 1px dotted #e8e8e8;">
										<div class="headfilter">
											<a href="javascript:;" onclick="changeSubjectId('')" <c:if test="${empty subjectId}">class="current"</c:if>>全部</a>
											<c:forEach items="${subjectClassList}" var="subjectClass">
												<c:choose>
													<c:when test="${subjectClass.subjectId==subjectId}">
														<a href="javascript:;" onclick="changeSubjectId('${subjectClass.subjectId}')" class="current">${subjectClass.nameHtml}</a>
													</c:when>
													<c:otherwise>
														<a href="javascript:;" onclick="changeSubjectId('${subjectClass.subjectId}')">${subjectClass.nameHtml}</a>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</div>
									</div>
								</c:if>
								
								<!-- 帖子列表 -->
								<div class="block no-shadow">
									<!-- 置顶主题 -->
									<c:if test="${fn:length(topList)>0}">
										<div class="main-0">
											<div class="post-list">
												<ul class="post-ul">
													<c:forEach items="${topList}" var="threadInfo">
														<li class="post-li">
															<a href="${pageContext.request.contextPath}/forum/viewthread.action?tid=${threadInfo.tid}">
																<div class="post-li-main">
																	<div class="icon-top"></div>
																	<div class="post-li-top">
																		<div class="post-li-avatar">
																			<c:choose>
																				<c:when test="${empty threadInfo.avatar}">
																					<img src="${pageContext.request.contextPath}/static/common/images/noavatar.png" class="user-face" />
																				</c:when>
																				<c:otherwise>
																					<img src="${threadInfo.avatar}" class="user-face" />
																				</c:otherwise>
																			</c:choose>
																		</div>
																		<div class="post-li-info">
																			<div class="post-nickname">${threadInfo.loginName}</div>
																			<div class="post-time change-time">${threadInfo.createTime}</div>
																		</div>
																		<div class="post-content-foot">
																			<div class="post-countinfo">
																				<span class="icon-eye"></span> <span class="margin-right-10">${threadInfo.viewCount}</span>
																				<span class="icon-commenting-o"></span> <span>${threadInfo.replyCount}</span>
																			</div>
																			<div class="last-reply">
																				<span class="reply-nickname margin-right-10">${threadInfo.threadReplyInfo.loginName}</span>
																				<span class="change-time">${threadInfo.threadReplyInfo.replyTime}</span>
																			</div>
																		</div>
																	</div>
																	<div class="post-li-content">
																		<div class="post-content-text">
																			<p class="post-title">${threadInfo.title}</p>
																			<p class="post-content">${threadInfo.contentText}</p>
																		</div>
																	</div>
																</div>
															</a>
														</li>
													</c:forEach>
												</ul>
											</div>
										</div>
									</c:if>
									
									<!-- 普通主题 -->
									<div class="main-0" <c:if test="${fn:length(topList)>0}">style="border-top: 2px solid #79c239;"</c:if>>
										<div class="post-list">
											<ul id="post_list" class="post-ul">
												
											</ul>
										</div>
									</div>
								</div>
								
								<!-- 分页 -->
								<div class="page" style="padding: 0 10px;">
									<a href="${pageContext.request.contextPath}/forum/newthread.action?fid=${fid}">
										<button class="button post-send" style="float:left;"><span class="icon-mode_edit"></span> 发新帖</button>
									</a>
									<ul id="page" class="pagination"></ul>
								</div>
							</div>
							<div>
								<div class="main-0">
									<div class="post-list">
										<ul class="post-ul">
											<c:choose>
												<c:when test="${fn:length(digestPageInfo.list)==0}">
													<li><p class="nothread"></p></li>
												</c:when>
												<c:otherwise>
													<c:forEach items="${digestPageInfo.list}" var="threadInfo">
														<li class="post-li">
															<a href="${pageContext.request.contextPath}/forum/viewthread.action?tid=${threadInfo.tid}">
																<div class="post-li-main">
																	<div class="icon-digest"></div>
																	<div class="post-li-top">
																		<div class="post-li-avatar">
																			<c:choose>
																				<c:when test="${empty threadInfo.avatar}">
																					<img src="${pageContext.request.contextPath}/static/common/images/noavatar.png" class="user-face" />
																				</c:when>
																				<c:otherwise>
																					<img src="${threadInfo.avatar}" class="user-face" />
																				</c:otherwise>
																			</c:choose>
																		</div>
																		<div class="post-li-info">
																			<div class="post-nickname">${threadInfo.loginName}</div>
																			<div class="post-time change-time">${threadInfo.createTime}</div>
																		</div>
																		<div class="post-content-foot">
																			<div class="post-countinfo">
																				<span class="icon-eye"></span> <span class="margin-right-10">${threadInfo.viewCount}</span>
																				<span class="icon-commenting-o"></span> <span>${threadInfo.replyCount}</span>
																			</div>
																			<div class="last-reply">
																				<span class="reply-nickname margin-right-10">${threadInfo.threadReplyInfo.loginName}</span>
																				<span class="change-time">${threadInfo.threadReplyInfo.replyTime}</span>
																			</div>
																		</div>
																	</div>
																	<div class="post-li-content">
																		<div class="post-content-text">
																			<p class="post-title">
																				<c:if test="${!empty threadInfo.subjectNameHtml}">
																					<em class="zhuti">[${threadInfo.subjectNameHtml}]</em>
																				</c:if>
																				${threadInfo.title}
																			</p>
																			<p class="post-content">${threadInfo.contentText}</p>
																		</div>
																	</div>
																</div>
															</a>
														</li>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</ul>
									</div>
									<!-- 分页 -->
									<div class="page" style="padding: 0 10px;">
										<a href="${pageContext.request.contextPath}/forum/newthread.action?fid=${fid}">
											<button class="button post-send" style="float:left;"><span class="icon-mode_edit"></span> 发新帖</button>
										</a>
										<ul id="page2" class="pagination"></ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<!-- 右侧区域 -->
				<div style="width:320px;padding: 0 20px;">
					<div class="moderator">筛选<input type="checkbox" class="fill" id="new_window" value="1" style="float:right;"/>新窗口打开</div>
					<ul class="equal-2" style="margin-top: 10px;width: 300px;">
						<c:forEach items="${defaultOrderFieldList}" var="opt">
							<li><input type="radio" class="fill" name="orderField" value="${opt.value}" <c:if test="${opt.value==defaultOrderField}">checked</c:if>/>${opt.name}</li>
						</c:forEach>
						<span class="clearfix"></span>
					</ul>
					
					<div class="moderator">超级版主</div>
					<ul class="equal-3" style="margin-top: 10px;width: 300px;">
						<c:choose>
							<c:when test="${fn:length(zoneInfo.zoneModeratorList)==0}">
								<li>
									超级版主空缺中
								</li>
							</c:when>
							<c:otherwise>
								<c:forEach items="${zoneInfo.zoneModeratorList}" var="zoneModerator">
									<li>
										<a class="moderator-avatar" href="${pageContext.request.contextPath}/forum/user_info/space_profile.action?userId=${zoneModerator.userId}" target="_blank">
											<img src="${zoneModerator.avatar}"/>
										</a>
										<p class="moderator-name">${zoneModerator.loginName}</p>
									</li>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						<span class="clearfix"></span>
					</ul>
					
					<div class="moderator">版主</div>
					<ul class="equal-3" style="margin-top: 10px;width: 300px;">
						<c:choose>
							<c:when test="${fn:length(boardInfo.boardModeratorList)==0}">
								版主空缺中
							</c:when>
							<c:otherwise>
								<c:forEach items="${boardInfo.boardModeratorList}" var="boardModerator">
									<li>
										<a class="moderator-avatar" href="${pageContext.request.contextPath}/forum/user_info/space_profile.action?userId=${boardModerator.userId}" target="_blank">
											<c:choose>
												<c:when test="${empty boardModerator.avatar}">
													<img src="${pageContext.request.contextPath}/static/common/images/noavatar.png" />
												</c:when>
												<c:otherwise>
													<img src="${boardModerator.avatar}" />
												</c:otherwise>
											</c:choose>
										</a>
										<p class="moderator-name">${boardModerator.loginName}</p>
									</li>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						<span class="clearfix"></span>
					</ul>
				</div>
			</div>

			<!--快速发帖-->
			<div class="block no-shadow" style="margin-top:20px;">
				<div class="banner">
					<p class="tab fixed">快速发帖</p>
				</div>
				<!--正文内容-->
				<div class="main" style="padding-bottom: 0;">
					<!--帖子分类-->
					<c:if test="${fn:length(subjectClassList)>0}">
						<div class="unit" style="margin-top:0;">
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
					<div class="unit" <c:if test="${fn:length(subjectClassList)==0}"> style="margin-top:0;"</c:if>>
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
							<input type="button" id="fastpost" class="button post-send" style="margin:0;" value="发表帖子" />
						</div>
						<span class="clearfix"></span>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!--底部-->
	<c:import url="../common/footer.jsp"></c:import>
</body>
<script src="${pageContext.request.contextPath}/static/common/js/forum/forumdisplay.js"></script>
<script>
	var fid = "${fid}";
	var subjectId = "";
	var defaultOrderField = "${defaultOrderField}";
	
	// 普通帖和精华帖切换
	var tabIndex = "${tabIndex}";
	javaex.tab({
		id : "tab",		// tab的id
		current : tabIndex,	// 默认选中第几个选项卡
		mode : "click"
	});
	
	$(function() {
		getData(1);
	});
	function getData(pageNum) {
		// 预设加载动画
		var html = '<li><div class="main-0" style="height:200px;background: url(${pageContext.request.contextPath}/static/default/images/loading.gif) no-repeat center center;"></li>';
		$("#post_list").empty();
		$("#post_list").append(html);
		
		$.ajax({
			url : "${pageContext.request.contextPath}/forum/forumdisplay.json",
			type : "POST",
			dataType : "json",
			data : {
				"fid" : fid,
				"subjectId" : subjectId,
				"defaultOrderField" : defaultOrderField,
				"pageNum" : pageNum
			},
			success : function(rtn) {
				if (rtn.code=="999999") {
					getData();
					return;
				}
				var pageInfo = rtn.data.pageInfo;
				var currentPage = pageInfo.pageNum;
				var pageCount = pageInfo.pages;
				
				var list = rtn.data.pageInfo.list;
				var html = '';
				for (var i=0; i<list.length; i++) {
					html += '<li class="post-li pop-in">';
					html += '<a href="${pageContext.request.contextPath}/forum/viewthread.action?tid='+list[i].tid+'">';
					html += '<div class="post-li-main">';
					if (list[i].isDigest=="1") {
						html += '<div class="icon-digest"></div>';
					}
					html += '<div class="post-li-top">';
					html += '<div class="post-li-avatar">';
					if (!list[i].avatar) {
						html += '<img src="${pageContext.request.contextPath}/static/common/images/noavatar.png" class="user-face" />';
					} else {
						html += '<img src="'+list[i].avatar+'" class="user-face" />';
					}
					html += '</div>';
					html += '<div class="post-li-info">';
					html += '<div class="post-nickname">'+list[i].loginName+'</div>';
					html += '<div class="post-time change-time">'+list[i].createTime+'</div>';
					html += '</div>';
					html += '<div class="post-content-foot">';
					html += '<div class="post-countinfo">';
					html += '<span class="icon-eye"></span> <span class="margin-right-10">'+list[i].viewCount+'</span>';
					html += '<span class="icon-commenting-o"></span> <span>'+list[i].replyCount+'</span>';
					html += '</div>';
					html += '<div class="last-reply">';
					html += '<span class="reply-nickname margin-right-10">'+list[i].threadReplyInfo.loginName+'</span>';
					html += '<span class="change-time">'+list[i].threadReplyInfo.replyTime+'</span>';
					html += '</div>';
					html += '</div>';
					html += '</div>';
					html += '<div class="post-li-content">';
					html += '<div class="post-content-text">';
					if (!list[i].subjectNameHtml) {
						html += '<p class="post-title">'+list[i].title+'</p>';
					} else {
						html += '<p class="post-title"><em class="zhuti">['+list[i].subjectNameHtml+']</em> '+list[i].title+'</p>';
					}
					html += '<p class="post-content">'+list[i].contentText.substring(0, 200)+'</p>';
					html += '</div>';
					html += '</div>';
					html += '</div>';
					html += '</a>';
					html += '</li>';
				}
				
				// 先清空
				$("#post_list").empty();
				if (html=='') {
					html = '<li><p class="nothread"></p></li>';
				}
				$("#post_list").append(html);
				// 修改显示时间的文本
				changeTimeText();
				javaex.popin();
				
				$("#page").empty();
				javaex.page({
					id : "page",
					pageCount : pageCount,	// 总页数
					currentPage : currentPage,// 默认选中第几页
					// 返回当前选中的页数
					callback:function(rtn) {
						getData(rtn.pageNum);
					}
				});
			}
		});
	}
	
	// 切换主题分类
	function changeSubjectId(newSubjectId) {
		subjectId = newSubjectId;
		getData(1);
	}
	// 切换筛选
	$(':radio[name="orderField"]').change(function() {
		defaultOrderField = $(this).val();
		getData(1);
	});
	// 是否新窗口打开帖子
	$("#new_window").change(function() {
		if ($(this).is(":checked")) {
			$(".post-li a").attr("target", "_blank");
		} else {
			$(".post-li a").attr("target", "_self");
		}
	});
	
	// 精华帖分页
	var digestCurrentPage = "${digestPageInfo.pageNum}";
	var digestPageCount = "${digestPageInfo.pages}";
	javaex.page({
		id : "page2",
		pageCount : digestPageCount,	// 总页数
		currentPage : digestCurrentPage,// 默认选中第几页
		// 返回当前选中的页数
		callback:function(rtn) {
			jump2(rtn.pageNum);
		}
	});
	function jump2(pageNum) {
		if (!pageNum) {
			pageNum = 1;
		}
		window.location.href = "${pageContext.request.contextPath}/forum/forumdisplay.action"
				+ "?fid="+fid
				+ "&digest=1"
				+ "&pageNum="+pageNum
				;
	}
	
	javaex.select({
		id : "subject_id"
	});
</script>
</html>
