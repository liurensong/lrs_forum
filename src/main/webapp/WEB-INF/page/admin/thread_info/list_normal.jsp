<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>论坛主题管理</title>
<c:import url="../common/common.jsp"></c:import>
<style>
	.table a {
		color: #2366A8;
	}
	.table a:hover{
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
				<span>内容</span>
				<span class="divider">/</span>
				<span class="active">论坛主题管理</span>
			</div>
		</div>

		<!--全部主体内容-->
		<div class="list-content">
			<!--块元素-->
			<div class="block">
				<!--页面有多个表格时，可以用于标识表格-->
				<h2>最新主题列表</h2>
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
					
					<table id="table" class="table">
						<thead>
							<tr>
								<th class="checkbox"><input type="checkbox" class="fill listen-1" /> </th>
								<th style="width:40%;">标题</th>
								<th>分区</th>
								<th>版块</th>
								<th>作者</th>
								<th>置顶</th>
								<th>精华</th>
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
											<td>
												<a href="${pageContext.request.contextPath}/forum/viewthread.action?tid=${threadInfo.tid}" target="_blank">
													${fn:substring(threadInfo.title, 0, 50)}
												</a>
											</td>
											<td>${threadInfo.zoneName}</td>
											<td>${threadInfo.boardName}</td>
											<td>
												${threadInfo.loginName}
												<c:if test="${threadInfo.userStatus=='2'}">
													(<font color="red">封禁</font>)
												</c:if>
											</td>
											<td>${threadInfo.isTop=='1'?'<font color=green>置顶</font>':''}</td>
											<td>${threadInfo.isDigest=='1'?'<font color=green>精华</font>':''}</td>
											<td>${threadInfo.replyCount}</td>
											<td>${threadInfo.viewCount}</td>
											<td>${threadInfo.threadReplyInfo.loginName}<br/>${threadInfo.threadReplyInfo.replyTime}</td>
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
					
					<!--块元素-->
					<div class="block no-shadow">
						<!--banner用来修饰块元素的名称-->
						<div class="banner">
							<p class="tab fixed">批量操作</p>
						</div>
						<!--正文内容-->
						<div class="main" style="margin-top: -10px;margin-bottom:100px;">
							<input type="radio" class="fill" name="radio" value="move_to_board" />批量移动到版块
							<select id="fid2">
								<c:forEach items="${zoneListAll}" var="zoneInfo">
									<option value="" disabled="disabled">${zoneInfo.name}</option>
									<c:forEach items="${zoneInfo.boardList}" var="boardInfo">
										<option value="${boardInfo.fid}">&nbsp;&nbsp;&nbsp;&nbsp;${boardInfo.name}</option>
									</c:forEach>
								</c:forEach>
							</select>
							主题分类：<select id="subject_id"></select>
							<br />
							<input type="radio" class="fill" name="radio" value="set_top1" />批量置顶
							<br />
							<input type="radio" class="fill" name="radio" value="set_top0" />批量解除置顶
							<br />
							<input type="radio" class="fill" name="radio" value="set_digest1" />批量设置精华
							<br />
							<input type="radio" class="fill" name="radio" value="set_digest0" />批量解除精华
							<br />
							<input type="radio" class="fill" name="radio" value="delete" />批量删除
							<br />
							<button id="save" class="button green" style="margin-top:20px;"><span class="icon-check2"></span> 提交</button>
						</div>
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
	
	$(function() {
		// 初始化显示批量操作的主题分类
		loadSubject($("#fid2").val());
	});
	
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
		
		window.location.href = "list_normal.action"
				+ "?gid="+gid
				+ "&fid="+fid
				+ "&keyword="+keyword
				+ "&pageNum="+pageNum
				;
	}
	
	javaex.select({
		id : "fid2",
		callback: function (rtn) {
			loadSubject(rtn.selectValue);
		}
	});
	
	javaex.select({
		id : "subject_id"
	});
	
	// 根据选择的板块，显示主题分类
	function loadSubject(fid) {
		$.ajax({
			url : "${pageContext.request.contextPath}/subject_class/list_by_fid.json",
			type : "POST",
			dataType : "json",
			data : {
				"fid" : fid
			},
			success : function(rtn) {
				if (rtn.code=="000000") {
					var html = '';
					var subjectList = rtn.data.subjectList;
					if (subjectList.length>0) {
						for (var i=0; i<subjectList.length; i++) {
							html += '<option value="'+subjectList[i].subjectId+'">'+subjectList[i].nameText+'</option>';
						}
					} else {
						html += '<option value="">无主题分类</option>';
					}
					
					$("#subject_id").empty();
					$("#subject_id").append(html);
					
					javaex.select({
						id : "subject_id"
					});
				}
			}
		});
	}
	
	// 批量提交按钮点击事件
	$("#save").click(function() {
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
		
		// 判断选择的哪一个单选框进行操作
		var opt = $(':radio[name="radio"]:checked').val();
		
		if (opt=="move_to_board") {
			// 批量移动到版块
			var fid = $("#fid2").val();
			var subjectId = $("#subject_id").val();
			
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "batch_move_to_board.json",
				type : "POST",
				dataType : "json",
				traditional : "true",
				data : {
					"tidArr" : tidArr,
					"fid" : fid,
					"subjectId" : subjectId
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
		} else if (opt=="set_top1") {
			// 批量置顶
			batchSetTop(tidArr, "1");
		} else if (opt=="set_top0") {
			// 批量解除置顶
			batchSetTop(tidArr, "0");
		} else if (opt=="set_digest1") {
			// 批量设置精华
			batchSetDigest(tidArr, "1");
		} else if (opt=="set_digest0") {
			// 批量解除精华
			batchSetDigest(tidArr, "0");
		} else if (opt=="delete") {
			// 批量删除
			batchDelete(tidArr, "1");
		} else {
			javaex.optTip({
				content : "请选择操作选项",
				type : "error"
			});
		}
	});
	
	// 批量置顶/取消置顶主题
	function batchSetTop(tidArr, isTop) {
		javaex.optTip({
			content : "数据提交中，请稍候...",
			type : "submit"
		});
		
		$.ajax({
			url : "batch_opt.json",
			type : "POST",
			dataType : "json",
			traditional : "true",
			data : {
				"tidArr" : tidArr,
				"isTop" : isTop
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
	
	// 批量设置/取消精华
	function batchSetDigest(tidArr, isDigest) {
		javaex.optTip({
			content : "数据提交中，请稍候...",
			type : "submit"
		});
		
		$.ajax({
			url : "batch_opt.json",
			type : "POST",
			dataType : "json",
			traditional : "true",
			data : {
				"tidArr" : tidArr,
				"isDigest" : isDigest
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
	
	// 批量删除
	function batchDelete(tidArr, isDelete) {
		javaex.optTip({
			content : "数据提交中，请稍候...",
			type : "submit"
		});
		
		$.ajax({
			url : "batch_opt.json",
			type : "POST",
			dataType : "json",
			traditional : "true",
			data : {
				"tidArr" : tidArr,
				"isDelete" : isDelete
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
