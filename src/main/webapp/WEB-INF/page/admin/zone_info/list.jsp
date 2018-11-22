<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>板块管理</title>
<c:import url="../common/common.jsp"></c:import>
<style>
.lastboard {
	padding-left: 55px;
	background: url(${pageContext.request.contextPath}/static/common/images/bg_repno.gif) no-repeat -240px -600px;
}
.board {
	padding-left: 60px;
	background: url(${pageContext.request.contextPath}/static/common/images/bg_repno.gif) no-repeat -240px -550px;
}
a.window {
	color: #055f95;
}
a.window:hover{
	text-decoration: underline;
}
</style>
</head>

<body>
	<!-- 面包屑导航和主体内容 -->
	<div class="content">
		<!--面包屑导航-->
		<div class="content-header">
			<div class="breadcrumb">
				<span>板块</span>
				<span class="divider">/</span>
				<span class="active">板块管理</span>
			</div>
		</div>
		
		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--正文内容-->
				<div class="main">
					<!--class加上color可以实现隔行变色-->
					<!--color1表示奇数行着色、color2表示偶数行着色-->
					<table id="table" class="table no-line">
						<thead>
							<tr>
								<th style="width:80px;">显示顺序</th>
								<th>板块名称</th>
								<th>版主</th>
								<th>是否可见</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="zoneInfo">
								<tr>
									<td><input type="text" class="text" name="zone_sort" value="${zoneInfo.sort}" data-type="非负整数" error-msg="请填写非负整数" error-pos="36" /></td>
									<td><input type="text" class="text" name="zone_name" value="${zoneInfo.name}" data-type="必填" error-pos="36" style="width:auto;" /></td>
									<td>
										<input type="hidden" name="gid" value="${zoneInfo.gid}" />
										(gid:${zoneInfo.gid})
										<c:choose>
											<c:when test="${zoneInfo.zoneModeratorCount=='0'}">
												无 / <a class="window" href="javascript:;" onclick="addZoneModerator('${zoneInfo.gid}')">添加超级版主</a>
											</c:when>
											<c:otherwise>
												${zoneInfo.zoneModeratorCount}人 / <a class="window" href="javascript:;" onclick="addZoneModerator('${zoneInfo.gid}')">编辑超级版主</a>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${zoneInfo.isShow=='1'}">
												<input type="checkbox" class="switch" value="1" onchange="updateIsShow('zone', '${zoneInfo.gid}', '0')" checked/>
											</c:when>
											<c:otherwise>
												<input type="checkbox" class="switch" value="0" onchange="updateIsShow('zone', '${zoneInfo.gid}', '1')" />
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<a href="${pageContext.request.contextPath}/zone_info/edit.action?gid=${zoneInfo.gid}">
											<button class="button blue"><span class="icon-edit-2"></span> 编辑</button>
										</a>
										<button class="button red" onclick="deleteZone(this, '${zoneInfo.gid}')"><span class="icon-remove"></span> 删除</button>
									</td>
								</tr>
								<c:forEach items="${zoneInfo.boardList}" var="boardInfo">
									<tr>
										<td><input type="text" class="text" name="board_sort" value="${boardInfo.sort}" data-type="非负整数" error-msg="请填写非负整数" error-pos="36" /></td>
										<td>
											<span class="board"></span>
											<input type="text" class="text" name="board_name" value="${boardInfo.name}" data-type="必填" error-pos="36" style="width:auto;" />
										</td>
										<td>
											<input type="hidden" name="fid" value="${boardInfo.fid}" />
											<input type="hidden" name="board_gid" value="${zoneInfo.gid}" />
											(fid:${boardInfo.fid})
											<c:choose>
												<c:when test="${boardInfo.boardModeratorCount=='0'}">
													无 / <a class="window" href="javascript:;" onclick="addBoardModerator('${boardInfo.fid}')">添加版主</a>
												</c:when>
												<c:otherwise>
													${boardInfo.boardModeratorCount}人 / <a class="window" href="javascript:;" onclick="addBoardModerator('${boardInfo.fid}')">编辑版主</a>
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${boardInfo.isShow=='1'}">
													<input type="checkbox" class="switch" value="1" onchange="updateIsShow('board', '${boardInfo.fid}', '0')" checked />
												</c:when>
												<c:otherwise>
													<input type="checkbox" class="switch" value="0" onchange="updateIsShow('board', '${boardInfo.fid}', '1')" />
												</c:otherwise>
											</c:choose>
										</td>
										<td>
											<a href="${pageContext.request.contextPath}/board_info/edit.action?fid=${boardInfo.fid}">
												<button class="button blue"><span class="icon-edit-2"></span> 编辑</button>
											</a>
											<button class="button red" onclick="deleteBoard(this, '${boardInfo.fid}')"><span class="icon-remove"></span> 删除</button>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="4">
										<div class="lastboard">
											<a href="javascript:;" onclick="addBoard(this, ${zoneInfo.gid})" style="color:#F60;"><span class="icon-plus"></span> 添加新板块</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<a href="javascript:;" onclick="addZone()" style="color:#F60;margin-left:10px;"><span class="icon-plus"></span> 添加新分区</a>
					<p class="divider"></p>
					<button class="button green" onclick="save()"><span class="icon-check2"></span> 保存</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	javaex.loading();
	
	// 添加超级版主
	function addZoneModerator(gid) {
		javaex.dialog({
			type : "window",
			title : "编辑超级版主(gid:"+gid+")",
			url : "${pageContext.request.contextPath}/zone_moderator/list.action?gid="+gid,	// 页面地址或网址或请求地址
			width : "800",	// 弹出层宽度
			height : "500"	// 弹出层高度
		});
	}
	
	// 添加版主
	function addBoardModerator(fid) {
		javaex.dialog({
			type : "window",
			title : "编辑版主(fid:"+fid+")",
			url : "${pageContext.request.contextPath}/board_moderator/list.action?fid="+fid,	// 页面地址或网址或请求地址
			width : "800",	// 弹出层宽度
			height : "500"	// 弹出层高度
		});
	}
	
	// 更新分区或板块的可见与否
	function updateIsShow(type, id, isShow) {
		if (type=="zone") {
			// 分区
			url = "${pageContext.request.contextPath}/zone_info/update_is_show.json";
		} else if (type=="board") {
			// 板块
			url = "${pageContext.request.contextPath}/board_info/update_is_show.json";
		}
		
		javaex.optTip({
			content : "数据提交中，请稍候...",
			type : "submit"
		});
		
		// 向后台提交
		$.ajax({
			url : url,
			dataType : "json",
			data : {
				"id" : id,
				"isShow" : isShow
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
	
	// 添加新的分区
	function addZone() {
		var trHtml = '';
		trHtml += '<tr>';
		trHtml += '<td><input type="text" class="text" name="zone_sort" value="0" data-type="非负整数" error-msg="请填写非负整数" error-pos="36" /></td>';
		trHtml += '<td>';
		trHtml += '<input type="text" class="text" name="zone_name" value="新分区名称" style="width:auto;" data-type="必填" error-pos="36" />';
		trHtml += '<a href="javascript:;" onclick="deleteTr(this)" style="color:red;margin-left:10px;"><span class="icon-remove"></span> 删除</a>';
		trHtml += '</td>';
		trHtml += '<td><input type="hidden" name="gid" value="" /></td>';
		trHtml += '<td></td>';
		trHtml += '<td></td>';
		trHtml += '</tr>';
		$("#table tbody").append(trHtml);
		
		// 动态加载html代码之后，需要重新渲染
		javaex.render();
	}
	
	// 添加新的板块
	function addBoard(obj, gid) {
		var trHtml = '';
		trHtml += '<tr>';
		trHtml += '<td><input type="text" class="text" name="board_sort" value="0" data-type="非负整数" error-msg="请填写非负整数" error-pos="36" /></td>';
		trHtml += '<td>';
		trHtml += '<span class="board"></span>';
		trHtml += '<input type="text" class="text" name="board_name" value="新版块名称" style="width:auto;" data-type="必填" error-pos="36" />';
		trHtml += '<a href="javascript:;" onclick="deleteTr(this)" style="color:red;margin-left:10px;"><span class="icon-remove"></span> 删除</a>';
		trHtml += '</td>';
		trHtml += '<td><input type="hidden" name="fid" value="" /><input type="hidden" name="board_gid" value="'+gid+'" /></td>';
		trHtml += '<td></td>';
		trHtml += '<td></td>';
		trHtml += '</tr>';
		$(obj).parent().parent().parent().before(trHtml);
		
		// 动态加载html代码之后，需要重新渲染
		javaex.render();
	}
	
	// 删除当前行
	function deleteTr(obj) {
		$(obj).parent().parent().remove();
	}
	
	// 保存
	var gidArr = new Array();
	var zoneSortArr = new Array();
	var zoneNameArr = new Array();
	
	var fidArr = new Array();
	var boardGidArr = new Array();
	var boardSortArr = new Array();
	var boardNameArr = new Array();
	function save() {
		// 表单验证函数
		if (javaexVerify()) {
			gidArr = [];
			zoneSortArr = [];
			zoneNameArr = [];
			
			fidArr = [];
			boardGidArr = [];
			boardSortArr = [];
			boardNameArr = [];
			
			// 分区主键
			$('input[name="gid"]').each(function() {
				gidArr.push($(this).val());
			});
			// 分区排序
			$('input[name="zone_sort"]').each(function() {
				zoneSortArr.push($(this).val());
			});
			// 分区名称
			$('input[name="zone_name"]').each(function() {
				zoneNameArr.push($(this).val());
			});
			
			// 板块主键
			$('input[name="fid"]').each(function() {
				fidArr.push($(this).val());
			});
			// 板块对应的上级区id
			$('input[name="board_gid"]').each(function() {
				boardGidArr.push($(this).val());
			});
			// 板块排序
			$('input[name="board_sort"]').each(function() {
				boardSortArr.push($(this).val());
			});
			// 板块名称
			$('input[name="board_name"]').each(function() {
				boardNameArr.push($(this).val());
			});
			
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			// 向后台提交
			$.ajax({
				url : "${pageContext.request.contextPath}/zone_info/save_zone_and_board.json",
				dataType : "json",
				traditional : "true",	// 允许传递数组
				data : {
					"gidArr" : gidArr,
					"zoneSortArr" : zoneSortArr,
					"zoneNameArr" : zoneNameArr,
					"fidArr" : fidArr,
					"boardGidArr" : boardGidArr,
					"boardSortArr" : boardSortArr,
					"boardNameArr" : boardNameArr
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
	
	// 删除分区及其下的板块
	function deleteZone(obj, gid) {
		javaex.deleteDialog(
			obj,	// obj是必须的
			{
				content : "确定要删除么",
				tip : "会连同其下板块一起删除",
				callback : "callback("+gid+")"
			}
		);
	}
	function callback(gid) {
		javaex.optTip({
			content : "数据提交中，请稍候...",
			type : "submit"
		});
		
		// 向后台提交
		$.ajax({
			url : "${pageContext.request.contextPath}/zone_info/delete.json",
			dataType : "json",
			data : {
				"gid" : gid
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
					return false;
				}
			}
		});
	}
	
	// 删除板块
	function deleteBoard(obj, fid) {
		javaex.deleteDialog(
			obj,	// obj是必须的
			{
				content : "确定要删除么",
				callback : "callback2("+fid+")"
			}
		);
	}
	function callback2(fid) {
		javaex.optTip({
			content : "数据提交中，请稍候...",
			type : "submit"
		});
		
		// 向后台提交
		$.ajax({
			url : "${pageContext.request.contextPath}/board_info/delete.json",
			dataType : "json",
			data : {
				"fid" : fid
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
					return false;
				}
			}
		});
	}
</script>
</html>
