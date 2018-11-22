<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>用户举报</title>
<c:import url="../common/common.jsp"></c:import>
</head>

<body>
	<!-- 面包屑导航和主体内容 -->
	<div class="content">
		<!--面包屑导航-->
		<div class="content-header">
			<div class="breadcrumb">
				<span>内容</span>
				<span class="divider">/</span>
				<span class="active">用户举报</span>
			</div>
		</div>

		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--页面有多个表格时，可以用于标识表格-->
				<h2>举报记录列表</h2>
				<!--正文内容-->
				<div class="main">
					<!--表格上方的操作元素，添加、删除等-->
					<div class="operation-wrap">
						<div class="buttons-wrap">
							<button onclick="deleteThread()" class="button green"><span class="icon-check2"></span> 批量处理举报记录</button>
							<button onclick="deleteReport()" class="button red"><span class="icon-remove"></span> 批量删除举报记录</button>
							<span style="float: right;"><input type="checkbox" class="fill" name="status" value="1" onchange="showStatus(this)" <c:if test="${status=='1'}">checked</c:if>/>查看已处理记录</span>
						</div>
					</div>
					
					<table class="table">
						<thead>
							<tr>
								<th class="checkbox"><input type="checkbox" class="fill listen-1" /> </th>
								<th>主题id</th>
								<th>帖子id</th>
								<th>举报楼层</th>
								<th style="width:50%;">举报内容</th>
								<th style="width:20%;">举报理由</th>
								<th>举报人</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${fn:length(pageInfo.list)==0}">
									<tr>
										<td colspan="7" style="text-align:center;">暂无记录</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${pageInfo.list}" var="reportInfo">
										<tr>
											<td class="checkbox"><input type="checkbox" class="fill listen-1-2" value="${reportInfo.id}" name="id" /> </td>
											<td>${reportInfo.tid}</td>
											<td>${reportInfo.replyId}</td>
											<td>${reportInfo.floor}</td>
											<td>${fn:substring(reportInfo.contentText, 0, 50)}</td>
											<td>${reportInfo.reportContent}</td>
											<td>${reportInfo.loginName}</td>
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
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	javaex.loading();
	
	var status = "${status}";
	var currentPage = "${pageInfo.pageNum}";
	var pageCount = "${pageInfo.pages}";
	
	// 切换举报记录的处理状态
	function showStatus(obj) {
		if ($(obj).is(":checked")) {
			status = "1";
		} else {
			status = "0";
		}
		window.location.href = "list.action?status="+status;
	}
	
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
		
		window.location.href = "list.action"
				+ "?pageNum="+pageNum
				+ "&status="+status
				;
	}
	
	// 批量处理举报记录
	function deleteThread() {
		var idArr = new Array();
		$(':checkbox[name="id"]:checked').each(function() {
			idArr.push($(this).val());
		});
		
		// 判断至少选择一条记录
		if (idArr.length==0) {
			javaex.optTip({
				content : "至少选择一条记录",
				type : "error"
			});
			return;
		}
		
		javaex.optTip({
			content : "数据提交中，请稍候...",
			type : "submit"
		});
		
		$.ajax({
			url : "batch_delete_thread.json",
			type : "POST",
			dataType : "json",
			traditional : "true",
			data : {
				"idArr" : idArr
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
	}
	
	// 批量删除举报记录
	function deleteReport() {
		var idArr = new Array();
		$(':checkbox[name="id"]:checked').each(function() {
			idArr.push($(this).val());
		});
		
		// 判断至少选择一条记录
		if (idArr.length==0) {
			javaex.optTip({
				content : "至少选择一条记录",
				type : "error"
			});
			return;
		}
		
		javaex.optTip({
			content : "数据提交中，请稍候...",
			type : "submit"
		});
		
		$.ajax({
			url : "batch_delete_report.json",
			type : "POST",
			dataType : "json",
			traditional : "true",
			data : {
				"idArr" : idArr
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
	}
</script>
</html>
