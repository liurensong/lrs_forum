<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>编辑版主</title>
<c:import url="../common/common.jsp"></c:import>
</head>

<body>
	<!--内容区域-->
	<div class="list-content">
		<!--块元素-->
		<div class="block">
			<!--正文内容-->
			<div class="main">
				<!--表格上方的操作元素，添加、删除等-->
				<div class="operation-wrap">
					<div class="buttons-wrap">
						<button id="add" class="button blue"><span class="icon-plus"></span> 添加</button>
						<button id="delete" class="button red"><span class="icon-minus"></span> 删除</button>
						<button id="save" class="button green"><span class="icon-check2"></span> 保存</button>
					</div>
				</div>
				<table id="table" class="table">
					<thead>
						<tr>
							<th class="checkbox"><input type="checkbox" class="fill listen-subject" /> </th>
							<th>请输入正确的用户名</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="zoneModerator">
							<tr>
								<td class="checkbox"><input type="checkbox" class="fill listen-subject-2" name="id" value="${zoneModerator.id}"/> </td>
								<td><input type="text" class="text" name="loginName" value="${zoneModerator.loginName}" data-type="必填" error-pos="36" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
<script>
	// 点击添加按钮，table向下追加一行
	$("#add").click(function() {
		var trHtml = '';
		trHtml += '<tr>';
		trHtml += '<td class="checkbox"><input type="checkbox" class="fill listen-1-2" name="id" value="" /> </td>';
		trHtml += '<td><input type="text" class="text" name="loginName" value="" data-type="必填" error-pos="36" /></td>';
		trHtml += '</tr>';
		$("#table tbody").append(trHtml);
		
		// 动态加载html代码之后，需要重新渲染
		javaex.render();
	});
	
	// 点击删除按钮事件
	$("#delete").click(function() {
		idArr = [];
		$(':checkbox[name="id"]').each(function() {
			if ($(this).is(":checked")) {
				var id = $(this).val();
				if (id!="") {
					idArr.push(id);
				}
			}
		});
		
		// 判断是否选择了有效值
		if (idArr.length==0) {
			// 仅仅删除多添加的tr行，不需要后台处理
			$(':checkbox[name="id"]:checked').each(function() {
				$(this).parent().parent().parent().remove();
			});
		} else {
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "delete.json",
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
	});
	
	// 点击保存按钮事件
	var fid = "${fid}";
	var loginNameArr = new Array();
	$("#save").click(function() {
		// 表单验证函数
		if (javaexVerify()) {
			loginNameArr = [];
			
			// 用户名
			$('input[name="loginName"]').each(function() {
				loginNameArr.push($(this).val());
			});
			
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			// 向后台提交
			$.ajax({
				url : "save.json",
				dataType : "json",
				traditional : "true",	// 允许传递数组
				data : {
					"fid" : fid,
					"loginNameArr" : loginNameArr
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
	});
</script>
</html>
