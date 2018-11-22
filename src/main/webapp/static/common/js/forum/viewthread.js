javaex.select({
	id : "opt_select"
});

function optThread() {
	var optSelect = $("#opt_select").val();
	if (optSelect=="") {
		javaex.alert({
			content : "请先选择一个操作选项"
		});
	} else {
		$.ajax({
			url : "update_thread_status.json",
			type : "POST",
			dataType : "json",
			data : {
				"tid" : tid,
				"optSelect" : optSelect
			},
			success : function(rtn) {
				if (rtn.code=="000000") {
					javaex.alert({
						content : rtn.message,
						callback : "optCallback()"
					});
				} else {
					javaex.alert({
						content : rtn.message
					});
				}
			}
		});
	}
}
function optCallback() {
	var optSelect = $("#opt_select").val();
	if (optSelect=="delete") {
		window.location.href = "forumdisplay.action?fid=${threadInfo.fid}";
	}
}

// 举报楼层回复
function reportFloor(replyId) {
	if (!getCookie("userToken")) {
		// 打开登录框
		loginDialog();
		return;
	}

	var html = '请点击举报理由';
	html += '<ul class="equal-1 report">';
	html += '<li><input type="radio" class="fill" name="report" value="广告垃圾" checked/>广告垃圾</li>';
	html += '<li><input type="radio" class="fill" name="report" value="违规内容"/>违规内容</li>';
	html += '<li><input type="radio" class="fill" name="report" value="恶意灌水"/>恶意灌水</li>';
	html += '<li><input type="radio" class="fill" name="report" value="重复发帖"/>重复发帖</li>';
	html += '<li><input type="radio" class="fill" name="report" value="其他"/>其他</li>';
	html += '<li><textarea class="desc" id="report_content" style="display:none;" placeholder="请填写举报内容"></textarea></li>';
	html += '<span class="clearfix"></span>';
	html += '</ul>';
	javaex.alert({
		title : "举报",
		content : html,
		width : "350",
		callback : "reportFloorCallback("+replyId+")"
	});
	javaex.render();
	$(':radio[name="report"]').change(function() {
		if ($(this).val()=="其他") {
			$("#report_content").show();
		} else {
			$("#report_content").hide();
		}
	});
}
// 举报回调函数
function reportFloorCallback(replyId) {
	var reportContent = $(':radio[name="report"]:checked').val();
	if (reportContent=="其他") {
		reportContent = $("#report_content").val();
		if (!reportContent) {
			reportContent = "其他";
		} else {
			reportContent = reportContent.replace(/<(script)[\S\s]*?\1>|<\/?(a|img)[^>]*>/gi, "").substring(0, 80);;
		}
	}
	// 发送举报请求
	$.ajax({
		url : "save_report_floor.json",
		type : "POST",
		dataType : "json",
		data : {
			"replyId" : replyId,
			"reportContent" : reportContent
		},
		success : function(rtn) {
			if (rtn.code=="000000") {
				javaex.optTip({
					content : "举报成功",
					type : "success"
				});
				// 建议延迟加载
				setTimeout(function() {
					// 关闭弹出层
					javaex.close();
				}, 2000);
				return true;
			} else {
				javaex.optTip({
					content : rtn.message,
					type : "error"
				});
			}
		}
	});
	return false;
}

// 删除楼层回复
function deleteFloor(replyId) {
	javaex.confirm({
		title : "删除/屏蔽",
		width : "350",
		content : "确定要删除该楼层的回复么？<br />删除后，该回复会进入回收站，可在后台进行彻底删除。彻底删除前，前台页面访问时内容会被屏蔽",
		callback : "deleteFloorCallback("+replyId+")"
	});
}
function deleteFloorCallback(replyId) {
	$.ajax({
		url : "delete_floor.json",
		type : "POST",
		dataType : "json",
		data : {
			"replyId" : replyId
		},
		success : function(rtn) {
			if (rtn.code=="000000") {
				javaex.optTip({
					content : "操作成功",
					type : "success"
				});
				// 建议延迟加载
				setTimeout(function() {
					javaex.close();
					window.location.reload();
				}, 2000);
				return true;
			} else {
				javaex.optTip({
					content : rtn.message,
					type : "error"
				});
			}
		}
	});
	return false;
}

javaex.page({
	id : "page",
	pageCount : pageCount,	// 总页数
	currentPage : currentPage,// 默认选中第几页
	// 返回当前选中的页数

	callback:function(rtn) {
		jump(rtn.pageNum);
	}
});

function jump(pageNum) {
	if (!pageNum) {
		pageNum = 1;
	}

	window.location.href = "viewthread.action"
			+ "?tid="+tid
			+ "&pageNum="+pageNum
			;
}

javaex.edit({
	id : "edit",
	image : {
		url : "upload_info/upload_image.json?type=qiniu",	// 请求路径
		param : "file",		// 参数名称，SSM中与MultipartFile的参数名保持一致
		dataType : "url",	// 返回的数据类型：base64 或 url
		rtn : "rtnData",	// 后台返回的数据对象，在前面页面用该名字存储
		imgUrl : "data.imgUrl"	// 根据返回的数据对象，获取图片地址。  例如后台返回的数据为：{code: "000000", message: "操作成功！", data: {imgUrl: "1.jpg"}}
	},
	content : '',	// 这里必须是单引号，因为html代码中都是双引号，会产生冲突
	callback : function(rtn) {
		$("#contentHtml").val(rtn.html);
		$("#contentText").val(rtn.text.substring(0, 200));
	}
});

// 监听点击保存按钮事件
$("#save_reply").click(function() {
	var contentHtml = $("#contentHtml").val();
	var contentText = $("#contentText").val();
	if (contentText=="") {
		javaex.alert({
			content : "请输入回复内容"
		});
		return false;
	}

	javaex.optTip({
		content : "数据提交中，请稍候...",
		type : "submit"
	});

	$.ajax({
		url : "save_thread_reply.json",
		type : "POST",
		dataType : "json",
		data : {
			"tid" : tid,
			"contentHtml" : contentHtml,
			"contentText" : contentText
		},
		success : function(rtn) {
			if (rtn.code=="000000") {
				javaex.optTip({
					content : "回复成功",
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
});

// 回复楼层
function replyFloor(replyId) {
	if (!getCookie("userToken")) {
		// 打开登录框
		loginDialog();
		return;
	}

	if (!replyId) {
		replyId = "";
	}
	javaex.dialog({
		type : "window",
		title : "参与/回复主题",
		url : "reply_floor.action?tid="+tid+"&replyId="+replyId,
		width : "1000",	// 弹出层宽度
		height : "630"	// 弹出层高度
	});
}

// 编辑回复
function editReply(replyId) {
	window.location.href = "editthread.action?tid="+tid+"&replyId="+replyId;
}

// 打开登录框
function loginDialog() {
	javaex.dialog({
		type : "login",	// 弹出层类型
		width : "400",
		height : "320",
		url : "user_info/login_dialog.action"
	});
}