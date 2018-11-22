<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>主题回收站</title>
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
				<span class="active">主题回收站</span>
			</div>
		</div>

		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--页面有多个表格时，可以用于标识表格-->
				<h2>主题回收站列表</h2>
				<!--正文内容-->
				<div class="main">
					<!--表格上方的搜索操作-->
					<div style="text-align:right;margin-bottom:10px;">
						<!--分区-->
						<select id="gid">
							<option value="">全部</option>
							<c:forEach items="${zoneList}" var="zoneInfo">
								<option value="${zoneInfo.gid}" <c:if test="${zoneInfo.gid==gid}">selected</c:if>>${zoneInfo.name}</option>
							</c:forEach>
						</select>
						<!--板块-->
						<select id="fid">
							<option value="">全部</option>
							<c:forEach items="${boardList}" var="boardInfo">
								<option value="${boardInfo.fid}" <c:if test="${boardInfo.fid==fid}">selected</c:if>>${boardInfo.name}</option>
							</c:forEach>
						</select>
						<div class="input-group">
							<!-- 主题标题检索 -->
							<input type="text" class="text" id="keyword" value="${keyword}" placeholder="检索标题" />
							<!-- 点击查询按钮 -->
							<button class="button blue" onclick="search()"><span class="icon-search"></span></button>
						</div>
					</div>
					
					<!--表格上方的操作元素，添加、删除等-->
					<div class="operation-wrap">
						<div class="buttons-wrap">
							<button id="recovery" class="button green"><span class="icon-check2"></span> 批量恢复</button>
							<button id="delete" class="button red"><span class="icon-remove"></span> 彻底删除</button>
						</div>
					</div>
					
					<table id="table" class="table">
						<thead>
							<tr>
								<th class="checkbox"><input type="checkbox" class="fill listen-1" /> </th>
								<th style="width:50%;">标题</th>
								<th>分区</th>
								<th>版块</th>
								<th>作者</th>
								<th>回复</th>
								<th>浏览</th>
								<th>最后发表</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${fn:length(pageInfo.list)==0}">
									<tr>
										<td colspan="8" style="text-align:center;">暂无记录</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${pageInfo.list}" var="threadInfo">
										<tr>
											<td class="checkbox"><input type="checkbox" class="fill listen-1-2" value="${threadInfo.tid}" name="tid" /> </td>
											<td>${fn:substring(threadInfo.title, 0, 50)}</td>
											<td>${threadInfo.zoneName}</td>
											<td>${threadInfo.boardName}</td>
											<td>${threadInfo.loginName}</td>
											<td>${threadInfo.replyCount}</td>
											<td>${threadInfo.viewCount}</td>
											<td>${threadInfo.threadReplyInfo.replyTime}</td>
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

	var currentPage = "${pageInfo.pageNum}";
	var pageCount = "${pageInfo.pages}";
	
	javaex.select({
		id : "gid",
		callback: function (rtn) {
			var gid = rtn.selectValue;
			if (gid=="") {
				var html = '<option value="">全部</option>';
				$("#fid").empty();
				$("#fid").append(html);
				
				javaex.select({
					id : "fid",
					isSearch : false
				});
			} else {
				$.ajax({
					url : "${pageContext.request.contextPath}/board_info/list_by_gid.json",
					type : "POST",
					dataType : "json",
					data : {
						"gid" : gid
					},
					success : function(rtn) {
						if (rtn.code=="000000") {
							var html = '<option value="">全部</option>';
							var boardList = rtn.data.boardList;
							if (boardList.length>0) {
								for (var i=0; i<boardList.length; i++) {
									html += '<option value="'+boardList[i].fid+'">'+boardList[i].name+'</option>';
								}
							}
							
							$("#fid").empty();
							$("#fid").append(html);
							
							javaex.select({
								id : "fid"
							});
						}
					}
				});
			}
		}
	});
	
	javaex.select({
		id : "fid"
	});
	
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
		
		// 分区id
		var gid = $("#gid").val();
		// 板块id
		var fid = $("#fid").val();
		// 关键字检索
		var keyword = $("#keyword").val();
		
		window.location.href = "list_recycle.action"
				+ "?gid="+gid
				+ "&fid="+fid
				+ "&keyword="+keyword
				+ "&pageNum="+pageNum
				;
	}
	
	// 批量恢复
	$("#recovery").click(function() {
		var tidArr = new Array();
		$(':checkbox[name="tid"]:checked').each(function() {
			tidArr.push($(this).val());
		});
		
		// 判断至少选择一条记录
		if (tidArr.length==0) {
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
			url : "batch_update_status.json",
			type : "POST",
			dataType : "json",
			traditional : "true",
			data : {
				"tidArr" : tidArr,
				"status" : "1"
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
	});
	
	// 批量彻底删除
	$("#delete").click(function() {
		var tidArr = new Array();
		$(':checkbox[name="tid"]:checked').each(function() {
			tidArr.push($(this).val());
		});
		
		// 判断至少选择一条记录
		if (tidArr.length==0) {
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
			url : "batch_delete.json",
			type : "POST",
			dataType : "json",
			traditional : "true",
			data : {
				"tidArr" : tidArr
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
	});
</script>
</html>
