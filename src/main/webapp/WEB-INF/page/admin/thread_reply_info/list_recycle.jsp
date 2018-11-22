<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>回帖回收站</title>
<c:import url="../common/common.jsp"></c:import>
<style>
a {
	color: #055f95;
}
a:hover{
	text-decoration: underline;
}
.bold {
	font-weight: 700;
}
.table td {
	border-bottom: 1px solid #B5CFD9;
}
</style>
</head>

<body>
	<!-- 面包屑导航和主体内容 -->
	<div class="content">
		<!--面包屑导航-->
		<div class="content-header">
			<div class="breadcrumb">
				<span>内容</span>
				<span class="divider">/</span>
				<span class="active">帖子回收站</span>
			</div>
		</div>

		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--页面有多个表格时，可以用于标识表格-->
				<h2>帖子回收站列表</h2>
				<!--正文内容-->
				<div class="main">
					<!--表格上方的操作元素，添加、删除等-->
					<div class="operation-wrap">
						<div class="buttons-wrap">
							<button class="button green" onclick="recoveryAll()"><span class="icon-replay" style="font-weight:bold;"></span> 全部还原</button>
							<button class="button red" onclick="deleteAll()"><span class="icon-remove"></span> 全部删除</button>
						</div>
					</div>
					
					<table class="table no-line">
						<tbody>
							<c:choose>
								<c:when test="${fn:length(pageInfo.list)==0}">
									<tr>
										<td colspan="2" style="text-align:center;">暂无记录</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${pageInfo.list}" var="entity">
										<tr>
											<td style="width: 80px;">
												<ul class="equal-1">
													<li><input type="radio" class="fill" name="${entity.id}" value="delete" checked/>删除</li>
													<li><input type="radio" class="fill" name="${entity.id}" value="recovery" />还原</li>
													<span class="clearfix"></span>
												</ul>
											</td>
											<td>
												<h3>
													<a href="${pageContext.request.contextPath}/forum/forumdisplay.action?fid=${entity.fid}" target="_blank">${entity.board_name}</a>
													 》  
													<a href="${pageContext.request.contextPath}/forum/viewthread.action?tid=${entity.tid}" target="_blank">${fn:substring(entity.title, 0, 80)}</a>
												</h3>
												<span class="bold">回帖人:</span>${entity.login_name} <span class="bold">时间:</span>${entity.reply_time}
												<p class="divider"></p>
												${fn:substring(entity.content_text, 0, 200)}
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					
					<!-- 分页 -->
					<div class="page">
						<ul id="page" class="pagination"></ul>
					</div>
					
					<button class="button green" onclick="batchOpt()"><span class="icon-check2"></span> 提交</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	javaex.loading();

	var currentPage = "${pageInfo.pageNum}";
	var pageCount = "${pageInfo.pages}";
	
	javaex.page({
		id : "page",
		pageCount : pageCount,	// 总页数
		currentPage : currentPage,// 默认选中第几页
		// 返回当前选中的页数
		callback:function(rtn) {
			search(rtn.pageNum);
		}
	});
	
	function search(pageNum) {
		if (pageNum==undefined) {
			pageNum = 1;
		}
		
		window.location.href = "list_recycle.action?pageNum="+pageNum;
	}
	
	// 批量处理
	var idArr = new Array();
	var optArr = new Array();
	var list = eval('${jsonList}');
	function batchOpt() {
		idArr = [];
		optArr = [];
		if (list.length>0) {
			for (var i=0; i<list.length; i++) {
				idArr.push(list[i].id);
				optArr.push($(':radio[name="'+list[i].id+'"]:checked').val());
			}
			
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "batch_opt.json",
				type : "POST",
				dataType : "json",
				traditional : "true",	// 允许传递数组
				data : {
					"idArr" : idArr,
					"optArr" : optArr
				},
				success : function(rtn) {
					if (rtn.code=="000000") {
						javaex.optTip({
							content : rtn.message,
							type : "success"
						});
						// 建议延迟加载
						setTimeout(function() {
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
		}
	}
	
	// 全部还原
	function recoveryAll() {
		javaex.optTip({
			content : "数据提交中，请稍候...",
			type : "submit"
		});
		
		$.ajax({
			url : "recovery_all.json",
			type : "POST",
			dataType : "json",
			success : function(rtn) {
				if (rtn.code=="000000") {
					javaex.optTip({
						content : rtn.message,
						type : "success"
					});
					// 建议延迟加载
					setTimeout(function() {
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
	}
	
	// 全部删除
	function deleteAll() {
		javaex.optTip({
			content : "数据提交中，请稍候...",
			type : "submit"
		});
		
		$.ajax({
			url : "delete_all.json",
			type : "POST",
			dataType : "json",
			success : function(rtn) {
				if (rtn.code=="000000") {
					javaex.optTip({
						content : rtn.message,
						type : "success"
					});
					// 建议延迟加载
					setTimeout(function() {
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
	}
</script>
</html>
