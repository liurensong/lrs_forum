$(function() {
	changeTimeText();
});

// 修改时间显示
function changeTimeText() {
	// 当前系统时间
	var now = javaex.getTime();
	// 修改时间显示
	$(".change-time").each(function() {
		var day = javaex.getTimeDiff($(this).text(), now, "day");
		if (day>6) {
			// 直接显示返回的时间，不改变
		} else if (day>0) {
			// 修改为几天前
			if (day==1) {
				$(this).text("昨天 " + $(this).text().split(" ")[1]);
			} else if (day==2) {
				$(this).text("前天 " + $(this).text().split(" ")[1]);
			} else {
				$(this).text(day + " 天前");
			}
		} else if (day==0) {
			var hour = javaex.getTimeDiff($(this).text(), now, "hour");
			if (hour>0) {
				$(this).text(hour+" 小时前");
				$(this).addClass("xi1");
			} else if (hour==0) {
				var minute = javaex.getTimeDiff($(this).text(), now, "minute");
				if (minute>0) {
					$(this).text(minute+" 分钟前");
					$(this).addClass("xi1");
				} else if (minute==0) {
					var second = javaex.getTimeDiff($(this).text(), now, "second");
					$(this).text(second+" 秒前");
					$(this).addClass("xi1");
				}
			}
		}
	});
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

// 监听点击快速发帖按钮事件
$("#fastpost").click(function() {
	// 表单验证函数
	if (javaexVerify()) {
		var title = $("#title").val();
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
			url : "save_newthread.json",
			type : "POST",
			dataType : "json",
			data : {
				"userToken" : getCookie("userToken"),
				"fid" : fid,
				"subjectId" : $("#subject_id").val(),
				"title" : title,
				"contentHtml" : contentHtml,
				"contentText" : contentText
			},
			success : function(rtn) {
				if (rtn.code=="000000") {
					javaex.optTip({
						content : "发布成功",
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
});