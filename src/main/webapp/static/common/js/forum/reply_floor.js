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
$("#save_reply_floor").click(function() {
	var contentHtml = $("#contentHtml").val();
	var contentText = $("#contentText").val();
	if (contentText=="") {
		javaex.alert({
			content : "请输入帖子内容"
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
			"replyId" : replyId,
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
					// 关闭弹出层
					parent.javaex.close();
					// 刷新页面
					parent.location.reload();
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
});