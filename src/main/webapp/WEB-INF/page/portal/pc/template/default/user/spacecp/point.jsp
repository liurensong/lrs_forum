<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${title}</title>
<c:import url="../../common/common.jsp"></c:import>
<style>
*, :before, :after {
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}
.xg1, .xg1 a {
	color: #999 !important;
}
.table td {
	padding: 8px 16px;
}
.content-img .page {
	margin: 0;
	margin-top: 20px;
}
</style>
</head>

<body>
	<!-- 头部和导航 -->
	<c:import url="../../common/header.jsp"></c:import>
	
	<div class="list-content-0 content-img">
		<div class="w-full img">
			<div class="vuscat-content">
				<div class="v2-t-menu-t"></div>
			</div>
		</div>
		<div class="w-full">
			<div class="uscat-content" style="text-align:left;position: unset;">
				<!-- 左侧 -->
				<c:import url="menu.jsp"></c:import>
				
				<!-- 右侧 -->
				<div class="w-right">
					<div class="u-box-r">
						<div class="t"><span>我的积分</span></div>
						<div class="main">
							<!--选项卡切换-->
							<div id="tab" class="tab tab-nav">
								<!--选项卡标题部分-->
								<div class="tab-title">
									<ul>
										<li>我的积分</li>
										<li>转账</li>
										<li>积分记录</li>
										<li>积分规则</li>
										<span class="clearfix"></span>
									</ul>
								</div>
								<!--选项卡内容部分-->
								<div class="tab-content">
									<div>
										<div class="main">
											<div class="xi1">
												<c:forEach items="${pointUseList}" var="pointInfo">
													<c:if test="${pointInfo.varName=='extcredits1'}">
														<b>${pointInfo.name}：</b>${userInfo.userCount.extcredits1}
													</c:if>
												</c:forEach>
											</div>
											<ul class="equal-4" style="margin-top:20px;">
												<c:forEach items="${pointUseList}" var="pointInfo">
													<c:choose>
														<c:when test="${pointInfo.varName=='extcredits2'}">
															<li><b>${pointInfo.name}：</b>${userInfo.userCount.extcredits2}</li>
														</c:when>
														<c:when test="${pointInfo.varName=='extcredits3'}">
															<li><b>${pointInfo.name}：</b>${userInfo.userCount.extcredits3}</li>
														</c:when>
														<c:when test="${pointInfo.varName=='extcredits4'}">
															<li><b>${pointInfo.name}：</b>${userInfo.userCount.extcredits4}</li>
														</c:when>
														<c:when test="${pointInfo.varName=='extcredits5'}">
															<li><b>${pointInfo.name}：</b>${userInfo.userCount.extcredits5}</li>
														</c:when>
														<c:when test="${pointInfo.varName=='extcredits6'}">
															<li><b>${pointInfo.name}：</b>${userInfo.userCount.extcredits6}</li>
														</c:when>
													</c:choose>
												</c:forEach>
												<span class="clearfix"></span>
											</ul>
											<div><b>积分：</b>${userInfo.userCount.point}<span class="xg1"> ( 总积分=发帖数（主题）+精华帖数*5+贡献*2+铜币 )</span></div>
										</div>
									</div>
									<div><div class="main">该功能尚未开放</div></div>
									<div>
										<div class="main">
											<table class="table no-vertical-line no-border-line color2">
												<thead>
													<tr>
														<th>行为名称</th>
														<c:forEach items="${pointUseList}" var="pointInfo">
															<th>${pointInfo.name}</th>
														</c:forEach>
														<th style="width:180px;">最后奖励时间</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${pageInfo.list}" var="userPointLog">
														<tr>
															<td>${userPointLog.name}</td>
															<c:forEach items="${pointUseList}" var="pointInfo">
																<c:choose>
																	<c:when test="${pointInfo.varName=='extcredits1'}">
																		<td>${userPointLog.extcredits1}</td>
																	</c:when>
																	<c:when test="${pointInfo.varName=='extcredits2'}">
																		<td>${userPointLog.extcredits2}</td>
																	</c:when>
																	<c:when test="${pointInfo.varName=='extcredits3'}">
																		<td>${userPointLog.extcredits3}</td>
																	</c:when>
																	<c:when test="${pointInfo.varName=='extcredits4'}">
																		<td>${userPointLog.extcredits4}</td>
																	</c:when>
																	<c:when test="${pointInfo.varName=='extcredits5'}">
																		<td>${userPointLog.extcredits5}</td>
																	</c:when>
																	<c:when test="${pointInfo.varName=='extcredits6'}">
																		<td>${userPointLog.extcredits6}</td>
																	</c:when>
																</c:choose>
															</c:forEach>
															<td>${userPointLog.time}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											
											<div class="page">
												<ul id="page" class="pagination"></ul>
											</div>
										</div>
									</div>
									<div>
										<div class="main">
											<table class="table color2">
												<thead>
													<tr>
														<th>策略名称</th>
														<th>周期</th>
														<th>奖励次数</th>
														<c:forEach items="${pointUseList}" var="pointInfo">
															<th>${pointInfo.name}</th>
														</c:forEach>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${pointRuleList}" var="pointRule">
														<tr>
															<td>${pointRule.name}</td>
															<td>${pointRule.cycle}</td>
															<td>${pointRule.rewardNum}</td>
															<c:forEach items="${pointUseList}" var="pointInfo">
																<td>
																	<c:choose>
																		<c:when test="${pointInfo.varName=='extcredits1'}">
																			${pointRule.extcredits1}
																		</c:when>
																		<c:when test="${pointInfo.varName=='extcredits2'}">
																			${pointRule.extcredits2}
																		</c:when>
																		<c:when test="${pointInfo.varName=='extcredits3'}">
																			${pointRule.extcredits3}
																		</c:when>
																		<c:when test="${pointInfo.varName=='extcredits4'}">
																			${pointRule.extcredits4}
																		</c:when>
																		<c:when test="${pointInfo.varName=='extcredits5'}">
																			${pointRule.extcredits5}
																		</c:when>
																		<c:when test="${pointInfo.varName=='extcredits6'}">
																			${pointRule.extcredits6}
																		</c:when>
																	</c:choose>
																</td>
															</c:forEach>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--底部-->
	<c:import url="../../common/footer.jsp"></c:import>
</body>
<script>
	javaex.tab({
		id : "tab",		// tab的id
		current : 1,		// 默认选中第几个选项卡
		mode : "click"
	});
	
	var currentPage = "${pageInfo.pageNum}";
	var pageCount = "${pageInfo.pages}";
	javaex.page({
		id : "page",
		pageCount : pageCount,	// 总页数
		currentPage : currentPage,// 默认选中第几页
		callback:function(rtn) {
			jump(rtn.pageNum);
		}
	});
	function jump(pageNum) {
		if (!pageNum) {
			pageNum = 1;
		}
		window.location.href = "${pageContext.request.contextPath}/forum/user_info/spacecp_point.action"
				+ "?pageNum="+pageNum
				;
	}
</script>
</html>
