<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<img id="goTopBtn" class="return-to-top" src="${pageContext.request.contextPath}/static/default/images/return-to-top.png">

<div class="footerbg">
	<div class="uscat-content footer-bar">
		<div class="f-m">
			<div class="menubar">
				<ul>
					<li><a href="javascript:;">首页</a></li><li>|</li>
					<li><a href="javascript:;">论坛</a></li><li>|</li>
<!-- 					<li><a href="javascript:;">充值</a></li> -->
					<li><a href="javascript:;" target="_blank">联系我们</a></li>
				</ul>
			</div>
			<div class="menubar">
				<ul>
					<!-- 网站名称 -->
					<li><a href="${webInfo.domain}">${webInfo.name}</a> </li>
					<!-- 管理员邮箱 -->
					<c:if test="${!empty webInfo.email}">
						<li>管理员邮箱：${webInfo.email}</li>
					</c:if>
				</ul>
			</div>
			<div class="menubar">
				<ul>
					<!-- 显示授权信息链接 -->
					<c:if test="${webInfo.license=='1'}">
						<li>Powered by<a href="http://www.javaex.cn/" target="_blank" style="padding: 0 4px;"><strong>uscat</strong></a><em>1.0</em></li>
					</c:if>
					<!-- 备案信息 -->
					<c:if test="${!empty webInfo.recordNumber}">
						<li style="margin-left:10px;">备案号：<a href="http://www.miibeian.gov.cn/" target="_blank" style="padding: 0;">${webInfo.recordNumber}</a></li>
					</c:if>
					<!-- 网站第三方统计代码 -->
					<c:if test="${!empty webInfo.statisticalCode}">
						<li>${webInfo.statisticalCode}</li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</div>

<script>
	javaex.goTopBtn({
		id : "goTopBtn"
	});
</script>