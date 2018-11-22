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
a.xi2 {
	color: #369;
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
						<div class="t"><span>修改密码</span></div>
						<div class="main">
							<form id="form">
								<div class="unit">
									<div class="left"><span class="required">*</span><p class="subtitle">旧密码</p></div>
									<div class="right">
										<input type="password" class="text" name="oldPassword" style="width:300px;" data-type="必填" />
									</div>
									<span class="clearfix"></span>
								</div>
								
								<div class="unit">
									<div class="left"><span class="required">*</span><p class="subtitle">新密码</p></div>
									<div class="right">
										<input type="password" class="text" name="newPassword" style="width:300px;" data-type="必填" />
									</div>
									<span class="clearfix"></span>
								</div>
								
								<div class="unit">
									<div class="left"><p class="subtitle">邮箱</p></div>
									<div class="right">
										<input type="text" class="text readonly" name="email" style="width:300px;" value="${userInfo.email}" readonly/>
										<input type="button" id="get_code" class="button green" style="margin-left: 10px;margin-top: -4px;" value="获取验证码" />
										<div id="show_hint" style="display:none;">
											<p class="hint">系统已经向该邮箱发送了一封验证激活邮件，请查收邮件，进行验证激活。</p>
											<p class="hint">如果没有收到验证邮件，您可以<a href="javascript:;" onclick="getCode()" class="xi2">重新接收验证邮件</a></p>
										</div>
									</div>
									<span class="clearfix"></span>
								</div>
								
								<div class="unit">
									<div class="left"><span class="required">*</span><p class="subtitle">验证码</p></div>
									<div class="right">
										<input type="text" class="text" name="identifyingCode" style="width:300px;" data-type="必填" />
									</div>
									<span class="clearfix"></span>
								</div>
								
								<div class="unit" style="margin-top:40px;">
									<div class="left"> </div>
									<div class="right">
										<input type="button" id="save" class="button post-send" style="width: 100px;" value="保存" />
									</div>
								</div>
							</form>
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
	// 获取验证码
	$("#get_code").click(function() {
		getCode();
	});
	function getCode() {
		$.ajax({
			url : "${pageContext.request.contextPath}/forum/user_info/get_code.json",
			type : "POST",
			dataType : "json",
			success : function(rtn) {
				if (rtn.code=="000000") {
					$("#get_code").removeClass("green");
					$("#get_code").addClass("disable");
					
					$("#show_hint").show();
				} else {
					javaex.optTip({
						content : rtn.message,
						type : "error"
					});
				}
			}
		});
	}
	
	$("#save").click(function() {
		// 表单验证函数
		if (javaexVerify()) {
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "${pageContext.request.contextPath}/forum/user_info/update_new_password.json",
				type : "POST",
				dataType : "json",
				data : $("#form").serialize(),
				success : function(rtn) {
					if (rtn.code=="000000") {
						javaex.optTip({
							content : "操作成功，请重新登录",
							type : "success"
						});
						// 建议延迟加载
						setTimeout(function() {
							// 跳转页面
							window.location.href = "${pageContext.request.contextPath}/forum/user_info/login.action";
						}, 2000);
					} else {
						javaex.optTip({
							content : rtn.message,
							type : "error"
						});
					}
				}
			});
		}
	});
</script>
</html>
