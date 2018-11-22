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
.table th, .table td {
	padding: 8px 16px;
}
.u-box-r h4{
	-moz-border-radius: 4px 4px 0 0;
    -webkit-border-radius: 4px 4px 0 0;
    border-radius: 4px 4px 0 0;
	line-height: 38px;
    background-color: #F60;
	text-align: center;
	color: #fff;
}
span.green{
	color: green;
	background-color: transparent;
}
span.red{
	color: red;
	background-color: transparent;
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
						<div class="t"><span>用户组权限</span></div>
						<div class="main">
							<h4>我的主用户组 - ${userInfo.groupName}</h4>
							<table class="table color2">
								<thead>
									<tr>
										<th>版块名称</th>
										<th>浏览帖子</th>
										<th>发新帖子</th>
										<th>发表回复</th>
										<th>下载附件</th>
										<th>上传附件</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${zoneList}" var="zoneInfo">
										<tr>
											<td>${zoneInfo.name}</td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<c:forEach items="${zoneInfo.boardList}" var="boardInfo">
											<tr>
												<td style="padding-left:40px">${boardInfo.name}</td>
												<c:choose>
													<c:when test="${boardInfo.isDefaultPerm!='0' || userInfo.groupType=='system'}">
														<td><span class="icon-check2 green"></span></td>
														<td><span class="icon-check2 green"></span></td>
														<td><span class="icon-check2 green"></span></td>
														<td><span class="icon-check2 green"></span></td>
														<td><span class="icon-check2 green"></span></td>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${boardInfo.boardGroupPerm.isViewBoard=='1'}">
																<td><span class="icon-check2 green"></span></td>
															</c:when>
															<c:otherwise>
																<td><span class="icon-remove red"></span></td>
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${boardInfo.boardGroupPerm.isPost=='1'}">
																<td><span class="icon-check2 green"></span></td>
															</c:when>
															<c:otherwise>
																<td><span class="icon-remove red"></span></td>
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${boardInfo.boardGroupPerm.isReply=='1'}">
																<td><span class="icon-check2 green"></span></td>
															</c:when>
															<c:otherwise>
																<td><span class="icon-remove red"></span></td>
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${boardInfo.boardGroupPerm.isDownloadAttachment=='1'}">
																<td><span class="icon-check2 green"></span></td>
															</c:when>
															<c:otherwise>
																<td><span class="icon-remove red"></span></td>
															</c:otherwise>
														</c:choose>
														<c:choose>
															<c:when test="${boardInfo.boardGroupPerm.isUploadAttachment=='1'}">
																<td><span class="icon-check2 green"></span></td>
															</c:when>
															<c:otherwise>
																<td><span class="icon-remove red"></span></td>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--底部-->
	<c:import url="../../common/footer.jsp"></c:import>
</body>
</html>
