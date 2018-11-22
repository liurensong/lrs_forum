<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>编辑版块</title>
<c:import url="../common/common.jsp"></c:import>
<style>
.unit .left {
	width: 120px !important;
}
.tab-content {
	overflow: unset;
}
.tab-content>div {
	overflow: unset;
}
.word {
	background-color: #f6f8fa;
	padding: 10px 20px;
	border-radius: 3px;
}
.highlight {
	color: #C00;
	font-weight: 700;
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
				<span>编辑版块</span>
				<span class="divider">/</span>
				<span class="active">${boardInfo.name}（fid:${boardInfo.fid}）</span>
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
								<li>扩展设置</li>
								<li>权限相关</li>
								<li>主题分类</li>
								<span class="clearfix"></span>
							</ul>
						</div>
						<!--选项卡内容部分-->
						<div class="tab-content">
							<!-- 基本设置 -->
							<div>
								<div class="main">
									<form id="form">
										<input type="hidden" name="fid" value="${boardInfo.fid}" />
										<!--版块名称-->
										<div class="unit">
											<div class="left"><span class="required">*</span><p class="subtitle">版块名称</p></div>
											<div class="right">
												<input type="text" class="text" data-type="必填" name="name" value="${boardInfo.name}" />
											</div>
											<span class="clearfix"></span>
										</div>
										
										<!--版块简介-->
										<div class="unit">
											<div class="left"><p class="subtitle">版块简介</p></div>
											<div class="right">
												<input type="text" class="text" name="remark" value="${boardInfo.remark}" />
												<p class="hint">将显示于版块名称的下面，提供对本版块的简短描述</p>
											</div>
											<span class="clearfix"></span>
										</div>
										
										<!--版块图标-->
										<div class="unit">
											<div class="left">
												<p class="subtitle">版块图标</p>
											</div>
											<div class="right">
												<div class="grid-1-9">
													<div style="min-width:80px;">
														<!-- 图片承载容器 -->
														<label for="upload_icon" style="display: inline-block; width:60px;height:60px;">
															<c:choose>
																<c:when test="${empty boardInfo.icon}">
																	<img id="upload_icon_image" src="${pageContext.request.contextPath}/static/default/javaex/pc/images/grey.gif" width="100%" height="100%" />
																</c:when>
																<c:otherwise>
																	<img id="upload_icon_image" src="${boardInfo.icon}" width="100%" height="100%" />
																</c:otherwise>
															</c:choose>
														</label>
														<input type="file" class="hide" id="upload_icon" accept="image/gif, image/jpeg, image/jpg, image/png" />
													</div>
													<div style="position: relative;">
														<input type="text" id="icon" class="text" style="position: absolute;bottom: 0;" name="icon" value="${boardInfo.icon}" />
													</div>
												</div>
											</div>
											<span class="clearfix"></span>
										</div>
										
										<!--图标宽高-->
										<div class="unit">
											<div class="left"><span class="required">*</span><p class="subtitle">图标宽高</p></div>
											<div class="right">
												<span><input type="text" class="text" name="width" value="${empty boardInfo.width?60:boardInfo.width}" style="width: auto;" data-type="正整数" error-msg="填写正整数" error-pos="50" placeholder="宽" /></span>
												<span><input type="text" class="text" name="height" value="${empty boardInfo.height?60:boardInfo.height}" style="width: auto;" data-type="正整数" error-msg="填写正整数" error-pos="50" placeholder="高" /></span>
												<p class="hint">论坛首页显示图标的宽、高。建议上传200*200像素的图标</p>
											</div>
											<span class="clearfix"></span>
										</div>
										
										<!--顶部横幅-->
										<div class="unit">
											<div class="left">
												<p class="subtitle">顶部横幅</p>
											</div>
											<div class="right">
												<!-- 图片承载容器 -->
												<label for="upload_banner" style="display: inline-block; width:100%;height:100px;">
													<c:choose>
														<c:when test="${empty boardInfo.banner}">
															<img id="upload_banner_image" src="${pageContext.request.contextPath}/static/default/javaex/pc/images/grey.gif" width="100%" height="100%" />
														</c:when>
														<c:otherwise>
															<img id="upload_banner_image" src="${boardInfo.banner}" width="100%" height="100%" />
														</c:otherwise>
													</c:choose>
												</label>
												<input type="file" class="hide" id="upload_banner" accept="image/gif, image/jpeg, image/jpg, image/png" />
												<input type="text" id="banner" class="text" name="banner" value="${boardInfo.banner}" />
											</div>
											<span class="clearfix"></span>
										</div>
										
										<!--显示版块-->
										<div class="unit">
											<div class="left"><p class="subtitle">显示版块</p></div>
											<div class="right">
												<ul class="equal-8">
													<c:choose>
														<c:when test="${boardInfo.isShow=='0'}">
															<li><input type="radio" class="fill" name="isShow" value="1" />是</li>
															<li><input type="radio" class="fill" name="isShow" value="0" checked/>否</li>
														</c:when>
														<c:otherwise>
															<li><input type="radio" class="fill" name="isShow" value="1" checked/>是</li>
															<li><input type="radio" class="fill" name="isShow" value="0" />否</li>
														</c:otherwise>
													</c:choose>
													<span class="clearfix"></span>
												</ul>
												<p class="hint">选择“否”将暂时将分区隐藏不显示，但分区内容仍将保留，且用户仍可通过直接提供带有 fid 的 URL 访问到此版块</p>
											</div>
											<span class="clearfix"></span>
										</div>
										
										<!--上级分区-->
										<div class="unit">
											<div class="left"><p class="subtitle">上级分区</p></div>
											<div class="right">
												<select id="gid" name="gid">
													<c:forEach items="${zoneList}" var="zoneInfo">
														<option value="${zoneInfo.gid}" <c:if test="${zoneInfo.gid==boardInfo.gid}">selected</c:if>>${zoneInfo.name}</option>
													</c:forEach>
												</select>
											</div>
											<!--清浮动-->
											<span class="clearfix"></span>
										</div>
										
										<p class="tip">SEO优化设置（不填时，系统自动生成）</p>
										<div class="word">
											<ul>
												<li>站点名称&nbsp;<font class="highlight">{bbname}</font>（应用范围：所有位置）</li>
												<li>分区名称&nbsp;<font class="highlight">{fgroup}</font>（应用范围：除首页以外）</li>
												<li>当前版块名称&nbsp;<font class="highlight">{forum}</font>（应用范围：除首页以外）</li>
												<li>当前版块简介&nbsp;<font class="highlight">{fremark}</font>（应用范围：除首页以外）</li>
											</ul>
										</div>
										<!--title-->
										<div class="unit">
											<div class="left">
												<p class="subtitle">title</p>
											</div>
											<div class="right">
												<input type="text" class="text" name="title" value="${boardInfo.title}" />
											</div>
											<span class="clearfix"></span>
										</div>
										
										<!--keywords-->
										<div class="unit">
											<div class="left">
												<p class="subtitle">keywords</p>
											</div>
											<div class="right">
												<input type="text" class="text" name="keywords" value="${boardInfo.keywords}" />
												<p class="hint">keywords用于搜索引擎优化，放在 meta 的 keyword 标签中，多个关键字间请用半角逗号 "," 隔开</p>
											</div>
											<span class="clearfix"></span>
										</div>
										
										<!--description-->
										<div class="unit">
											<div class="left">
												<p class="subtitle">description</p>
											</div>
											<div class="right">
												<textarea class="desc" name="description">${boardInfo.description}</textarea>
												<p class="hint">description用于搜索引擎优化，放在 meta 的 description 标签中</p>
											</div>
											<span class="clearfix"></span>
										</div>
									</form>
								</div>
							</div>
							<!-- 扩展设置 -->
							<div>
								<div class="main">
									<!--主题默认排序字段-->
									<div class="unit">
										<div class="left"><p class="subtitle">默认排序字段</p></div>
										<div class="right">
											<ul class="equal-1">
												<c:choose>
													<c:when test="${empty boardInfo.defaultOrderField}">
														<li><input type="radio" class="fill" name="defaultOrderField" value="reply_time" checked/>回复时间</li>
														<li><input type="radio" class="fill" name="defaultOrderField" value="create_time" />发布时间</li>
														<li><input type="radio" class="fill" name="defaultOrderField" value="reply_count" />回复数量</li>
														<li><input type="radio" class="fill" name="defaultOrderField" value="view_count" />浏览次数</li>
													</c:when>
													<c:otherwise>
														<li><input type="radio" class="fill" name="defaultOrderField" value="reply_time" <c:if test="${boardInfo.defaultOrderField=='reply_time'}">checked</c:if>/>回复时间</li>
														<li><input type="radio" class="fill" name="defaultOrderField" value="create_time" <c:if test="${boardInfo.defaultOrderField=='create_time'}">checked</c:if>/>发布时间</li>
														<li><input type="radio" class="fill" name="defaultOrderField" value="reply_count" <c:if test="${boardInfo.defaultOrderField=='reply_count'}">checked</c:if>/>回复数量</li>
														<li><input type="radio" class="fill" name="defaultOrderField" value="view_count" <c:if test="${boardInfo.defaultOrderField=='view_count'}">checked</c:if>/>浏览次数</li>
													</c:otherwise>
												</c:choose>
												<span class="clearfix"></span>
											</ul>
										</div>
										<span class="clearfix"></span>
									</div>
								</div>
							</div>
							<!-- 权限相关 -->
							<div>
								<div class="main">
									<!--使用默认权 -->
									<div class="unit">
										<div class="left"><p class="subtitle">默认权限</p></div>
										<div class="right">
											<input type="checkbox" class="switch" name="isDefaultPerm" value="1" onchange="setDisabled()" <c:if test="${boardInfo.isDefaultPerm!='0'}">checked</c:if>/>
											<p class="hint">浏览版块，全部用户组具有浏览版块帖子权限；发新话题，除游客以外的用户组具有发帖权限；发表回复，除游客以外的用户组具有回复权限；查看附件，全部用户组具有下载/查看附件权限；上传附件，除游客以外的用户组具有上传附件权限；上传图片，除游客以外的用户组具有上传图片权限</p>
										</div>
										<span class="clearfix"></span>
									</div>
									
									<p class="tip">版块权限</p>
									
									<table class="table no-line">
										<thead>
											<tr>
												<th style="font-size:16px;">会员用户组</th>
												<th><input type="checkbox" class="fill listen-a" name="selectAll"/>浏览板块</th>
												<th><input type="checkbox" class="fill listen-b" name="selectAll"/>发新帖子</th>
												<th><input type="checkbox" class="fill listen-c" name="selectAll"/>发表回复</th>
												<th><input type="checkbox" class="fill listen-d" name="selectAll"/>下载附件</th>
												<th><input type="checkbox" class="fill listen-e" name="selectAll"/>上传附件</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${groupInfoList}" var="groupInfo">
												<tr>
													<td>
														<c:choose>
															<c:when test="${groupInfo.boardGroupPerm.isViewBoard=='1' && groupInfo.boardGroupPerm.isPost=='1' && groupInfo.boardGroupPerm.isReply=='1' && groupInfo.boardGroupPerm.isDownloadAttachment=='1' && groupInfo.boardGroupPerm.isUploadAttachment=='1'}">
																<input type="checkbox" class="fill listen-${groupInfo.id}" name="groupId" value="${groupInfo.id}" checked/>${groupInfo.name}
															</c:when>
															<c:otherwise>
																<input type="checkbox" class="fill listen-${groupInfo.id}" name="groupId" value="${groupInfo.id}" />${groupInfo.name}
															</c:otherwise>
														</c:choose>
													</td>
													<td><input type="checkbox" class="fill listen-a-2 listen-${groupInfo.id}-2" name="isViewBoard" value="1" <c:if test="${groupInfo.boardGroupPerm.isViewBoard=='1'}">checked</c:if>/> </td>
													<td>
														<c:choose>
															<c:when test="${groupInfo.type=='visitor'}">
																<input type="checkbox" class="fill listen-b-2 listen-${groupInfo.id}-2 visitor" name="isPost" value="1" <c:if test="${groupInfo.boardGroupPerm.isPost=='1'}">checked</c:if> disabled="disabled"/> 
															</c:when>
															<c:otherwise>
																<input type="checkbox" class="fill listen-b-2 listen-${groupInfo.id}-2" name="isPost" value="1" <c:if test="${groupInfo.boardGroupPerm.isPost=='1'}">checked</c:if>/> 
															</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:choose>
															<c:when test="${groupInfo.type=='visitor'}">
																<input type="checkbox" class="fill listen-c-2 listen-${groupInfo.id}-2 visitor" name="isReply" value="1" <c:if test="${groupInfo.boardGroupPerm.isReply=='1'}">checked</c:if>/> 
															</c:when>
															<c:otherwise>
																<input type="checkbox" class="fill listen-c-2 listen-${groupInfo.id}-2" name="isReply" value="1" <c:if test="${groupInfo.boardGroupPerm.isReply=='1'}">checked</c:if>/> 
															</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:choose>
															<c:when test="${groupInfo.type=='visitor'}">
																<input type="checkbox" class="fill listen-d-2 listen-${groupInfo.id}-2 visitor" name="isDownloadAttachment" value="1" <c:if test="${groupInfo.boardGroupPerm.isDownloadAttachment=='1'}">checked</c:if>/> 
															</c:when>
															<c:otherwise>
																<input type="checkbox" class="fill listen-d-2 listen-${groupInfo.id}-2" name="isDownloadAttachment" value="1" <c:if test="${groupInfo.boardGroupPerm.isDownloadAttachment=='1'}">checked</c:if>/> 
															</c:otherwise>
														</c:choose>
													</td>
													<td>
														<c:choose>
															<c:when test="${groupInfo.type=='visitor'}">
																<input type="checkbox" class="fill listen-e-2 listen-${groupInfo.id}-2 visitor" name="isUploadAttachment" value="1" <c:if test="${groupInfo.boardGroupPerm.isUploadAttachment=='1'}">checked</c:if>/> 
															</c:when>
															<c:otherwise>
																<input type="checkbox" class="fill listen-e-2 listen-${groupInfo.id}-2" name="isUploadAttachment" value="1" <c:if test="${groupInfo.boardGroupPerm.isUploadAttachment=='1'}">checked</c:if>/> 
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<!-- 主题分类 -->
							<div>
								<div class="main">
									<!--启用主题分类 -->
									<div class="unit">
										<div class="left"><p class="subtitle">启用主题分类</p></div>
										<div class="right">
											<ul class="equal-8">
												<c:choose>
													<c:when test="${boardInfo.isSubjectClass=='1'}">
														<li><input type="radio" class="fill" name="isSubjectClass" value="1" checked/>是</li>
														<li><input type="radio" class="fill" name="isSubjectClass" value="0" />否</li>
													</c:when>
													<c:otherwise>
														<li><input type="radio" class="fill" name="isSubjectClass" value="1" />是</li>
														<li><input type="radio" class="fill" name="isSubjectClass" value="0" checked/>否</li>
													</c:otherwise>
												</c:choose>
												<span class="clearfix"></span>
											</ul>
											<p class="hint">设置是否在本版块启用主题分类功能，您需要同时设定相应的分类选项，才能启用本功能</p>
										</div>
										<span class="clearfix"></span>
									</div>
									
									<div id="set_subject" <c:if test="${empty boardInfo.isSubjectClass || boardInfo.isSubjectClass=='0'}">style="display:none;"</c:if>>
										<p class="tip">主题分类</p>
										
										<!--表格上方的操作元素，添加、删除等-->
										<div class="operation-wrap">
											<div class="buttons-wrap">
												<button class="button blue" onclick="addSubject()"><span class="icon-plus"></span> 添加</button>
												<button class="button red" onclick="deleteSubject()"><span class="icon-minus"></span> 删除</button>
											</div>
										</div>
										<table id="table" class="table">
											<thead>
												<tr>
													<th class="checkbox"><input type="checkbox" class="fill listen-subject" /> </th>
													<th style="width:20%;">显示顺序</th>
													<th>分类名称 (支持&lt;font color=red&gt;名称&lt;/font&gt;这种写法)</th>
													<th>是否启用</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${subjectClassList}" var="subjectClass">
													<tr>
														<td class="checkbox"><input type="checkbox" class="fill listen-subject-2" name="subjectId" value="${subjectClass.subjectId}"/> </td>
														<td><input type="text" class="text" name="sort" value="${subjectClass.sort}" data-type="非负整数" error-msg="请填写非负整数" error-pos="36" /></td>
														<td><input type="text" class="text" name="nameHtml" value="${subjectClass.nameHtml}" data-type="必填" error-pos="36" /></td>
														<td><input type="checkbox" class="fill" name="isShow" value="1" <c:if test="${subjectClass.isShow!='0'}">checked</c:if>/> </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<!--提交按钮-->
					<div class="unit" style="margin-top: -10px;width: 800px;">
						<div style="text-align: center;">
							<input type="button" id="return" class="button no" value="返回" />
							<input type="button" id="save" class="button yes" value="保存" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	javaex.loading();
	
	// 板块id
	var fid = $('input[name="fid"]').val();
	
	$(function() {
		setDisabled();
		
		// 获取会员用户组的个数
		var len = "${fn:length(groupInfoList)}";
		// 全选浏览版块
		if ($(':checkbox[name="isViewBoard"]:checked').length==len) {
			$('.listen-a').attr("checked", true);
		}
		// 全选发新帖子
		if ($(':checkbox[name="isPost"]:checked').length==len) {
			$('.listen-b').attr("checked", true);
		}
		// 全选发表回复
		if ($(':checkbox[name="isReply"]:checked').length==len) {
			$('.listen-c').attr("checked", true);
		}
		// 全选下载附件
		if ($(':checkbox[name="isDownloadAttachment"]:checked').length==len) {
			$('.listen-d').attr("checked", true);
		}
		// 全选上传附件
		if ($(':checkbox[name="isUploadAttachment"]:checked').length==len) {
			$('.listen-e').attr("checked", true);
		}
	});
	
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
	javaex.select({
		id : "gid",
		isSearch : false
	});
	
	// 上传板块图标
	javaex.upload({
		type : "image",
		url : "${pageContext.request.contextPath}/forum/upload_info/upload_image.json?type=qiniu",	// 请求路径
		id : "upload_icon",	// <input type="file" />的id
		maxSize : "5120",
		param : "file",			// 参数名称，SSM中与MultipartFile的参数名保持一致
		dataType : "url",		// 返回的数据类型：base64 或 url
		callback : function (rtn) {
			// 后台返回的数据
			if (rtn.code=="000000") {
				var imgUrl = rtn.data.imgUrl;
				$("#upload_icon_image").attr("src", imgUrl);
				$("#icon").val(imgUrl);
			} else {
				javaex.optTip({
					content : rtn.msg,
					type : "error"
				});
			}
		}
	});
	
	// 上传顶部横幅
	javaex.upload({
		type : "image",
		url : "${pageContext.request.contextPath}/forum/upload_info/upload_image.json?type=qiniu",	// 请求路径
		id : "upload_banner",	// <input type="file" />的id
		maxSize : "5120",
		param : "file",			// 参数名称，SSM中与MultipartFile的参数名保持一致
		dataType : "url",		// 返回的数据类型：base64 或 url
		callback : function (rtn) {
			// 后台返回的数据
			if (rtn.code=="000000") {
				var imgUrl = rtn.data.imgUrl;
				$("#upload_banner_image").attr("src", imgUrl);
				$("#banner").val(imgUrl);
			} else {
				javaex.optTip({
					content : rtn.msg,
					type : "error"
				});
			}
		}
	});
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 基本设置事件结束 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 权限相关事件开始 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	var groupIdArr = new Array();
	var isViewBoardArr = new Array();
	var isPostArr = new Array();
	var isReplyArr = new Array();
	var isDownloadAttachmentArr = new Array();
	var isUploadAttachmentArr = new Array();
	
	// 启动或关闭默认权限
	function setDisabled() {
		$(':checkbox[name="selectAll"], :checkbox[name="groupId"], :checkbox[name="isViewBoard"], :checkbox[name="isPost"], :checkbox[name="isReply"], :checkbox[name="isDownloadAttachment"], :checkbox[name="isUploadAttachment"]').each(function() {
			if ($(':checkbox[name="isDefaultPerm"]').is(":checked")) {
				$(this).attr("disabled", "disabled");
			} else {
				if (!$(this).hasClass("visitor")) {
					$(this).attr("disabled", false);
				}
			}
		});
	}
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 权限相关事件结束 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 主题分类事件开始 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	var subjectIdArr = new Array();
	var sortArr = new Array();
	var nameHtmlArr = new Array();
	var isShowArr = new Array();
	
	// 监听是否启用主题分类复选框
	$(':radio[name="isSubjectClass"]').on("change", function() {
		if ($(this).val()==1) {
			$("#set_subject").show();
		} else {
			$("#set_subject").hide();
		}
	});
	
	// 点击添加按钮，table向下追加一行
	function addSubject() {
		var trHtml = '';
		trHtml += '<tr>';
		trHtml += '<td class="checkbox"><input type="checkbox" class="fill listen-subject-2" name="subjectId" value="" /> </td>';
		trHtml += '<td><input type="text" class="text" name="sort" value="" data-type="非负整数" error-msg="请填写非负整数" error-pos="36" /></td>';
		trHtml += '<td><input type="text" class="text" name="nameHtml" value="" data-type="必填" error-pos="36" /></td>';
		trHtml += '<td><input type="checkbox" class="fill" name="isShow" value="1" checked/> </td>';
		trHtml += '</tr>';
		$("#table tbody").append(trHtml);
		
		// 动态加载html代码之后，需要重新渲染
		javaex.render();
	}
	
	// 删除主题
	function deleteSubject() {
		subjectIdArr = [];
		
		// 1.0 遍历所有被勾选的复选框
		$(':checkbox[name="subjectId"]:checked').each(function() {
			// 2.0 添加主键存在的记录
			var subjectId = $(this).val();
			if (subjectId!="") {
				subjectIdArr.push(subjectId);
			}
		});
		
		// 判断所勾选的是不是新增的空白记录
		if (subjectIdArr.length==0) {
			$(':checkbox[name="subjectId"]:checked').each(function() {
				// 前台无刷新去除新增的tr
				$(this).parent().parent().parent().remove();
			});
		} else {
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "${pageContext.request.contextPath}/subject_class/delete.json",
				type : "POST",
				dataType : "json",
				traditional : "true",	// 允许传递数组
				data : {
					"fid" : fid,
					"subjectIdArr" : subjectIdArr
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
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 主题分类事件结束 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 点击保存按钮事件
	$("#save").click(function() {
		if (index=="1") {
			// 基本设置
			if (javaexVerify()) {
				javaex.optTip({
					content : "数据提交中，请稍候...",
					type : "submit"
				});
				
				$.ajax({
					url : "${pageContext.request.contextPath}/board_info/save.json",
					type : "POST",
					dataType : "json",
					data : $("#form").serialize(),
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
		} else if (index=="2") {
			// 主题默认排序字段
			var defaultOrderField = $(':radio[name="defaultOrderField"]:checked').val();
			
			// 扩展设置
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "${pageContext.request.contextPath}/board_info/save.json",
				type : "POST",
				traditional : "true",	// 允许传递数组
				dataType : "json",
				data : {
					"fid" : fid,
					"defaultOrderField" : defaultOrderField
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
		}else if (index=="3") {
			// 权限相关
			groupIdArr = [];
			isViewBoardArr = [];
			isPostArr = [];
			isReplyArr = [];
			isDownloadAttachmentArr = [];
			isUploadAttachmentArr = [];
			
			// 默认权限
			var isDefaultPerm = "0";
			if ($(':checkbox[name="isDefaultPerm"]').is(":checked")) {
				isDefaultPerm = "1";
			}
			// 会员用户组id数组
			$('input[name="groupId"]').each(function() {
				groupIdArr.push($(this).val());
			});
			// 是否允许浏览板块
			$(':checkbox[name="isViewBoard"]').each(function() {
				if ($(this).is(":checked")) {
					isViewBoardArr.push($(this).val());
				} else {
					isViewBoardArr.push("0");
				}
			});
			// 是否允许发新帖
			$(':checkbox[name="isPost"]').each(function() {
				if ($(this).is(":checked")) {
					isPostArr.push($(this).val());
				} else {
					isPostArr.push("0");
				}
			});
			// 是否允许回复帖子
			$(':checkbox[name="isReply"]').each(function() {
				if ($(this).is(":checked")) {
					isReplyArr.push($(this).val());
				} else {
					isReplyArr.push("0");
				}
			});
			// 是否允许下载附件
			$(':checkbox[name="isDownloadAttachment"]').each(function() {
				if ($(this).is(":checked")) {
					isDownloadAttachmentArr.push($(this).val());
				} else {
					isDownloadAttachmentArr.push("0");
				}
			});
			// 是否允许上传附件
			$(':checkbox[name="isUploadAttachment"]').each(function() {
				if ($(this).is(":checked")) {
					isUploadAttachmentArr.push($(this).val());
				} else {
					isUploadAttachmentArr.push("0");
				}
			});
			
			javaex.optTip({
				content : "数据提交中，请稍候...",
				type : "submit"
			});
			
			$.ajax({
				url : "${pageContext.request.contextPath}/board_group_perm/save.json",
				type : "POST",
				traditional : "true",	// 允许传递数组
				dataType : "json",
				data : {
					"fid" : fid,
					"isDefaultPerm" : isDefaultPerm,
					"groupIdArr" : groupIdArr,
					"isViewBoardArr" : isViewBoardArr,
					"isPostArr" : isPostArr,
					"isReplyArr" : isReplyArr,
					"isDownloadAttachmentArr" : isDownloadAttachmentArr,
					"isUploadAttachmentArr" : isUploadAttachmentArr
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
		} else if (index=="4") {
			if (javaexVerify()) {
				// 主题分类
				subjectIdArr = [];
				sortArr = [];
				nameHtmlArr = [];
				isShowArr = [];
				
				// 是否启用主题分类
				var isSubjectClass = $(':radio[name="isSubjectClass"]:checked').val();
				// 主题id数组
				$(':checkbox[name="subjectId"]').each(function() {
					subjectIdArr.push($(this).val());
				});
				if (subjectIdArr.length==0) {
					javaex.optTip({
						content : "至少添加一个主题分类",
						type : "error"
					});
					return;
				}
				// 显示顺序
				$('input[name="sort"]').each(function() {
					sortArr.push($(this).val());
				});
				// 分类名称（带html代码，去除引号）
				$('input[name="nameHtml"]').each(function() {
					nameHtmlArr.push($(this).val().replace(/\'/g, "").replace(/\"/g, ""));
				});
				// 是否启用
				$(':checkbox[name="isShow"]').each(function() {
					if ($(this).is(":checked")) {
						isShowArr.push($(this).val());
					} else {
						isShowArr.push("0");
					}
				});
				
				javaex.optTip({
					content : "数据提交中，请稍候...",
					type : "submit"
				});
				
				// 向后台提交
				$.ajax({
					url : "${pageContext.request.contextPath}/subject_class/save.json",
					dataType : "json",
					traditional : "true",	// 允许传递数组
					data : {
						"fid" : fid,
						"isSubjectClass" : isSubjectClass,
						"subjectIdArr" : subjectIdArr,
						"sortArr" : sortArr,
						"nameHtmlArr" : nameHtmlArr,
						"isShowArr" : isShowArr
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
	
	// 监听点击返回按钮事件
	$("#return").click(function() {
		window.location.href = "${pageContext.request.contextPath}/zone_info/list.action";
	});
</script>
</html>
