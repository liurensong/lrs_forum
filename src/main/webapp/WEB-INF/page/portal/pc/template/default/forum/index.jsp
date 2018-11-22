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
<!--幻灯片-->
<script src="${pageContext.request.contextPath}/static/common/js/jquery.SuperSlide.2.1.1.js"></script>
<link href="${pageContext.request.contextPath}/static/default/css/index-top.css" rel="stylesheet" />
<style>
.product_feed{padding: 20px 14px 20px 170px;height: 100px;}
.product_feed .img_box{position: absolute;width: 90px;height: 90px;top: 25px;left: 50px;}
.product_feed .img_box img{display: block;width: 100%;height: 100%;}
.product_feed h5{font-size: 18px;line-height: 20px;color: #000;}
.product_feed .txt_brief{font-size: 14px;line-height: 18px;color: #808080;}
.product_feed .download_box{margin-top: 15px;}
.product_feed .btn_box{float: left;margin-right: 14px;width: 64px;}
.product_feed .btn_box a{display: block;height: 22px;border: 1px solid #ccc;line-height: 22px;text-align: center;position: relative;color: #a3a3a3;}
.product_feed .btn_box a:hover{color:#0097ff;}
.product_feed .txt_time{font-size: 11px;white-space: nowrap;color: #adadad;line-height: 19px;text-align: center;}
.download_count{position: absolute;right: 20px;bottom: 10px;}
</style>
</head>

<body>
	<!-- 头部和导航 -->
	<c:import url="../common/header.jsp"></c:import>
	
	<div class="list-content-0">
		<div class="list-content" style="width: 1200px;margin:20px auto;">
			<!-- 首页4格 -->
			<div class="grid-53-65 spacing-20">
				<div>
					<div class="imageFocus" style="position: relative;">
						<div class="bd slidebox">
							<div class="acgislideprev slidebarup">
								<a href="javascript::;" class="prev"></a>
							</div>
							<ul id="huandeng" class="slideshow">
								
							</ul>
							<div class="acgislidenext slidebardown">
								<a href="javascript::;" class="next"></a>
							</div>
						</div>
						<div class="hd slidebar" style="position: absolute; top: 5px; left: 4px;">
							<ul>
								<li class="on">1</li>
								<li>2</li>
								<li>3</li>
								<li>4</li>
								<li>5</li>
							</ul>
						</div>
					</div>
				</div>
				<div>
					<div class="block no-shadow" style="height: 320px;">
						<div class="main">
							<div id="tab" class="tab">
								<!--选项卡标题部分-->
								<div class="tab-title">
									<em>资讯索引</em>
									<ul style="float: right;">
										<li>热门帖</li>
										<li>新主题</li>
										<li>新回复</li>
										<span class="clearfix"></span>
									</ul>
								</div>
								<!--选项卡内容部分-->
								<div class="tab-content">
									<div>
										<div class="main animated fadeInDown">
											<div id="tab_1" class="right-list">
												
											</div>
										</div>
									</div>
									<div>
										<div class="main animated fadeInDown">
											<div id="tab_2" class="right-list">
												
											</div>
										</div>
									</div>
									<div>
										<div class="main animated fadeInDown">
											<div id="tab_3" class="right-list">
												
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 下载计数器 -->
			<c:if test="${fn:length(downloadList)>0}">
				<div class="block no-shadow">
					<div class="banner">
						<p class="tab fixed">原创程序下载（交流群：587243028）</p>
					</div>
					<div class="main" style="margin-bottom:-10px;">
						<ul class="equal-3" style="width:1180px;">
							<c:forEach items="${downloadList}" var="downloadCount">
								<li>
									<div class="block" style="width: 373px;margin: 0;">
										<div class="product_feed">
											<div class="img_box">
												<img src="${downloadCount.icon}">
											</div>
											<h5>${downloadCount.name}</h5>
											<p class="txt_brief">${downloadCount.remark}</p>
											<div class="download_box">
												<div class="btn_box">
													<a href="javascript:;" onclick="downLoad('${downloadCount.id}')"><span class="icon-tv"></span>下载 </a>
													<p class="txt_time">${downloadCount.updateTime}</p>
												</div>
											</div>
										</div>
										<p class="download_count">下载量：${downloadCount.count}</p>
									</div>
								</li>
							</c:forEach>
							<span class="clearfix"></span>
						</ul>
					</div>
				</div>
			</c:if>
			
			<!--分区和板块-->
			<c:forEach items="${zoneList}" var="zoneInfo">
				<div class="block no-shadow">
					<div class="main" style="margin-bottom:-10px;">
						<p class="commend"><span style="color:${zoneInfo.color};">${zoneInfo.name}</span></p>
						<ul class="equal-${zoneInfo.row}">
							<c:forEach items="${zoneInfo.boardList}" var="boardInfo">
								<li>
									<h3>
										<a href="${pageContext.request.contextPath}/forum/forumdisplay.action?fid=${boardInfo.fid}" target="_blank">
											<c:choose>
												<c:when test="${empty boardInfo.icon}">
													<img src="${pageContext.request.contextPath}/static/default/images/forum.gif" width="${boardInfo.width}" height="${boardInfo.height}" />${boardInfo.name}
												</c:when>
												<c:otherwise>
													<img src="${boardInfo.icon}" width="${boardInfo.width}" height="${boardInfo.height}" />${boardInfo.name}
												</c:otherwise>
											</c:choose>
										</a>
										<c:if test="${boardInfo.threadCountToday!='0'}">
											<em><strong>(${boardInfo.threadCountToday})</strong></em>
										</c:if>
									</h3>
									<p style="margin-left: ${boardInfo.width+10}px;font-size: 12px;">${boardInfo.remark}</p>
									<p style="margin-left: ${boardInfo.width+10}px;font-size: 12px;">主题:${boardInfo.threadCount}, 帖数:${boardInfo.threadReplyCount}</p>
								</li>
							</c:forEach>
							<span class="clearfix"></span>
						</ul>
					</div>
				</div>
			</c:forEach>
			
			<!-- 友情链接 -->
			<c:if test="${fn:length(friendLinkList)>0}">
				<div class="block no-shadow">
					<div class="h-tab-bar">
						<div class="titleicon home_icon vv_wicon_h_jpgame"></div>
					</div>
					<div class="main friend_link" style="margin-bottom:-20px;">
						<ul class="equal-8">
							<c:forEach items="${friendLinkList}" var="friendLink">
								<li><a title="${friendLink.name}" href="${friendLink.url}" target="_blank">${friendLink.name}</a></li>
							</c:forEach>
							<span class="clearfix"></span>
						</ul>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<!--底部-->
	<c:import url="../common/footer.jsp"></c:import>
</body>
<script>
	javaex.tab({
		id : "tab",
		current : 1
	});
	
	function downLoad(apiId) {
		$.ajax({
			url : "${pageContext.request.contextPath}/api/download.json",
			type : "POST",
			dataType : "json",
			data : {
				"apiId" : apiId
			},
			success : function(rtn) {
				if (rtn.code=="000000") {
					var index = rtn.data.url.lastIndexOf(".");
					var suffix= rtn.data.url.substring(index+1, rtn.data.url.length);
					if (suffix.length>4) {
						window.open(rtn.data.url);
					} else {
						var a = document.createElement("a");
						a.download = rtn.data.url;
						a.href = rtn.data.url;
						$("body").append(a);
						a.click();
						$(a).remove();
					}
				} else {
					javaex.alert({
						content : rtn.message
					});
				}
			}
		});
	}

	$(function() {
		// 获取幻灯片数据
		huandeng("7");
		
		// 右侧tab切换内容
		tabData("tab_1", "8");
		tabData("tab_2", "9");
		tabData("tab_3", "10");
	});
	
	// 获取幻灯片数据
	function huandeng(apiId) {
		$.ajax({
			url : "${pageContext.request.contextPath}/api/slide.json?apiId="+apiId,
			type : "POST",
			dataType : "json",
			success : function(rtn) {
				if (rtn.code=="999999") {
					huandeng(apiId);
					return;
				}
				var list = rtn.data.list;
				var html = '';
				for (var i=0; i<list.length; i++) {
					html += '<li>';
					html += '<a href="'+list[i].url+'" target="_blank">';
					html += '<img src="'+list[i].imageBig+'" width="100%" height="100%" />';
					html += '</a>';
					html += '<span class="title">'+list[i].title+'</span>';
					html += '</li>';
				}
				$("#huandeng").append(html);
				
				$(".imageFocus").slide({
					mainCell : ".bd ul",
					autoPlay : true
				});
			}
		});
	}
	
	// 右侧tab切换内容
	function tabData(tabDataId, apiId) {
		$.ajax({
			url : "${pageContext.request.contextPath}/api/data.json?apiId="+apiId,
			type : "POST",
			dataType : "json",
			success : function(rtn) {
				if (rtn.code=="999999") {
					tabData(tabDataId, apiId);
					return;
				}
				var list = rtn.data.list;
				var html = '';
				if (list.length>0) {
					if (!list[0].avatar) {
						html += '<div class="zxsy_index_img" style="background:url(${pageContext.request.contextPath}/static/common/images/noavatar.png) no-repeat;background-size:100% 100%;"></div>';
					} else {
						html += '<div class="zxsy_index_img" style="background:url('+list[0].avatar+') no-repeat;background-size:100% 100%;"></div>';
					}
					html += '<div class="zxsy_index_title">';
					html += '<b><a href="${pageContext.request.contextPath}/forum/viewthread.action?tid='+list[0].tid+'" target="_blank">'+list[0].title.substring(0, 30)+'</a></b>';
					html += '<span>'+list[0].createTime+'</span>';
					html += '</div>';
					html += '<a href="${pageContext.request.contextPath}/forum/viewthread.action?tid='+list[0].tid+'" class="zxsy_index_summary">'+list[0].contentText.substring(0, 50)+'</a>';
					html += '<hr class="zxsy_index_hr">';
				}
				html += '<ul>';
				for (var i=1; i<list.length; i++) {
					html += '<li>';
					html += '<a href="${pageContext.request.contextPath}/forum/viewthread.action?tid='+list[i].tid+'" target="_blank">'+list[i].title.substring(0, 30)+'</a>';
					html += '<span>'+list[i].createTime+'</span>';
					html += '</li>';
				}
				html += '</ul>';
				$("#"+tabDataId).append(html);
			}
		});
	}
</script>
</html>
