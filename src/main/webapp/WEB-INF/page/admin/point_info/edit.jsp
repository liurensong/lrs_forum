<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>积分设置</title>
<c:import url="../common/common.jsp"></c:import>
<style>
.unit .left {
	width: 120px !important;
}
</style>
</head>

<body>
	<!-- 面包屑导航和主体内容 -->
	<div class="content">
		<!--面包屑导航-->
		<div class="content-header">
			<div class="breadcrumb">
				<span>全局</span>
				<span class="divider">/</span>
				<span class="active">积分设置</span>
			</div>
		</div>
		
		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--正文-->
				<div class="main-0">
					<!--选项卡切换-->
					<div id="tab" class="tab tab-nav">
						<!--选项卡标题部分-->
						<div class="tab-title">
							<ul>
								<li>基本设置</li>
								<li>积分策略</li>
								<span class="clearfix"></span>
							</ul>
						</div>
						<!--选项卡内容部分-->
						<div class="tab-content">
							<div>
								<div class="main">
									<table class="table no-line">
										<thead>
											<tr>
												<th>启用</th>
												<c:forEach items="${pointList}" var="pointInfo">
													<th><input type="checkbox" class="fill" id="${pointInfo.varName}" name="${pointInfo.varName}" value="1" <c:if test="${pointInfo.isUse=='1'}">checked</c:if>/>${pointInfo.varName}</th>
												</c:forEach>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>积分名称</td>
												<c:forEach items="${pointList}" var="pointInfo">
													<td><input type="text" class="text" name="${pointInfo.varName}" value="${pointInfo.name}" /></td>
												</c:forEach>
											</tr>
											<tr>
												<td>兑换比率</td>
												<c:forEach items="${pointList}" var="pointInfo">
													<td><input type="text" class="text" name="${pointInfo.varName}" value="${pointInfo.conversionRatio}" data-type="空|非负整数" error-msg="数字格式不正确" error-pos="36"/></td>
												</c:forEach>
											</tr>
										</tbody>
									</table>
									<p class="tip">
										兑换比率说明：<br />
										铜币（100），银币（10），金币（1）的意思是：一个100个铜币可以兑换10个银币，或者1个金币。也可以反向兑换<br />
										兑换比率设为0或空时，表示该币种不支持兑换
									</p>
									
									<p class="tip success">
										总积分计算公式：<br />
										发帖数+精华帖数*5+贡献*2+铜币（暂不支持修改）
									</p>
								</div>
							</div>
							<div>
								<div class="main">
									<table class="table no-line">
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
													<td><input type="hidden" name="ruleAction" value="${pointRule.action}" />${pointRule.name}</td>
													<td>${pointRule.cycle}</td>
													<td>${pointRule.rewardNum}</td>
													<c:forEach items="${pointUseList}" var="pointInfo">
														<td>
															<c:choose>
																<c:when test="${pointInfo.varName=='extcredits1'}">
																	<input type="text" class="text" name="ruleExtcredits1" value="${pointRule.extcredits1}" data-type="整数" error-msg="填写整数" error-pos="36" />
																</c:when>
																<c:when test="${pointInfo.varName=='extcredits2'}">
																	<input type="text" class="text" name="ruleExtcredits2" value="${pointRule.extcredits2}" data-type="整数" error-msg="填写整数" error-pos="36" />
																</c:when>
																<c:when test="${pointInfo.varName=='extcredits3'}">
																	<input type="text" class="text" name="ruleExtcredits3" value="${pointRule.extcredits3}" data-type="整数" error-msg="填写整数" error-pos="36" />
																</c:when>
																<c:when test="${pointInfo.varName=='extcredits4'}">
																	<input type="text" class="text" name="ruleExtcredits4" value="${pointRule.extcredits4}" data-type="整数" error-msg="填写整数" error-pos="36" />
																</c:when>
																<c:when test="${pointInfo.varName=='extcredits5'}">
																	<input type="text" class="text" name="ruleExtcredits5" value="${pointRule.extcredits5}" data-type="整数" error-msg="填写整数" error-pos="36" />
																</c:when>
																<c:when test="${pointInfo.varName=='extcredits6'}">
																	<input type="text" class="text" name="ruleExtcredits6" value="${pointRule.extcredits6}" data-type="整数" error-msg="填写整数" error-pos="36" />
																</c:when>
															</c:choose>
														</td>
													</c:forEach>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<p class="tip">
										各项积分增减允许的范围为 -99～+99。如果为更多的操作设置积分策略，系统就需要更频繁的更新用户积分，同时意味着消耗更多的系统资源，因此请根据实际情况酌情设置。
									</p>
								</div>
							</div>
						</div>
					</div>
					
					<!--提交按钮-->
					<div class="unit" style="margin-top: 0;">
						<input type="button" id="save" class="button yes" value="保存" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	javaex.loading();
	
	var index = 1;	// 当前选中的标签
	javaex.tab({
		id : "tab",		// tab的id
		current : 1,	// 默认选中第几个选项卡
		mode : "click",	// 鼠标移动切换选项卡
		// 切换选项卡后返回一个对象，包含选项卡的索引，从1开始计
		// 初始化会自动执行一次
		callback: function (rtn) {
			index = rtn.index;
		}
	});
	
	// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 基本设置事件开始 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	var setArr = new Array();
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 基本设置事件结束 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 积分策略事件开始 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	var ruleActionArr = new Array();
	var ruleExtcredits1Arr = new Array();
	var ruleExtcredits2Arr = new Array();
	var ruleExtcredits3Arr = new Array();
	var ruleExtcredits4Arr = new Array();
	var ruleExtcredits5Arr = new Array();
	var ruleExtcredits6Arr = new Array();
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 积分策略事件结束 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	// 监听点击保存按钮事件
	$("#save").click(function() {
		// 表单验证函数
		if (javaexVerify()) {
			if (index==1) {
				// 基本设置
				setArr = [];
				for (var i=1; i<=6; i++) {
					var str = "";
					$('input[name="extcredits'+i+'"]').each(function(n) {
						if (n==0) {
							str += "var_name=" + $(this).attr("id");
							if ($(this).is(":checked")) {
								str += ",is_use=1";
							} else {
								str += ",is_use=0";
							}
						} else if (n==1) {
							str += ",name=" + $(this).val();
						} else if (n==2) {
							str += ",conversion_ratio=" + $(this).val();
							setArr.push(str);
						}
					});
				}
				
				javaex.optTip({
					content : "数据提交中，请稍候...",
					type : "submit"
				});
				
				// 向后台提交
				$.ajax({
					url : "${pageContext.request.contextPath}/point_info/save.json",
					dataType : "json",
					traditional : "true",	// 允许传递数组
					data : {
						"setArr" : setArr
					},
					success : function(rtn) {
						if (rtn.code=="000000") {
							javaex.optTip({
								content : rtn.message,
								type : "success"
							});
							// 建议延迟加载
							setTimeout(function() {
								// 刷新页面
								window.location.reload();
							}, 2000);
						} else {
							javaex.optTip({
								content : rtn.message,
								type : "error"
							});
						}
					}
				});
			} else if (index==2) {
				// 积分策略
				ruleActionArr = [];
				ruleExtcredits1Arr = [];
				ruleExtcredits2Arr = [];
				ruleExtcredits3Arr = [];
				ruleExtcredits4Arr = [];
				ruleExtcredits5Arr = [];
				ruleExtcredits6Arr = [];
				
				$('input[name="ruleAction"]').each(function() {
					ruleActionArr.push($(this).val());
				});
				$('input[name="ruleExtcredits1"]').each(function() {
					ruleExtcredits1Arr.push($(this).val());
				});
				$('input[name="ruleExtcredits2"]').each(function() {
					ruleExtcredits2Arr.push($(this).val());
				});
				$('input[name="ruleExtcredits3"]').each(function() {
					ruleExtcredits3Arr.push($(this).val());
				});
				$('input[name="ruleExtcredits4"]').each(function() {
					ruleExtcredits4Arr.push($(this).val());
				});
				$('input[name="ruleExtcredits5"]').each(function() {
					ruleExtcredits5Arr.push($(this).val());
				});
				$('input[name="ruleExtcredits6"]').each(function() {
					ruleExtcredits6Arr.push($(this).val());
				});
				
				javaex.optTip({
					content : "数据提交中，请稍候...",
					type : "submit"
				});
				
				// 向后台提交
				$.ajax({
					url : "${pageContext.request.contextPath}/point_rule/save.json",
					dataType : "json",
					traditional : "true",	// 允许传递数组
					data : {
						"ruleActionArr" : ruleActionArr,
						"ruleExtcredits1Arr" : ruleExtcredits1Arr,
						"ruleExtcredits2Arr" : ruleExtcredits2Arr,
						"ruleExtcredits3Arr" : ruleExtcredits3Arr,
						"ruleExtcredits4Arr" : ruleExtcredits4Arr,
						"ruleExtcredits5Arr" : ruleExtcredits5Arr,
						"ruleExtcredits6Arr" : ruleExtcredits6Arr
					},
					success : function(rtn) {
						if (rtn.code=="000000") {
							javaex.optTip({
								content : rtn.message,
								type : "success"
							});
						} else {
							javaex.optTip({
								content : rtn.message,
								type : "error"
							});
						}
					}
				});
			}
		}
	});
</script>
</html>