<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${title}</title>
<c:import url="../../common/common.jsp"></c:import>
<style>
*, :before, :after {
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}
a.indigo{
	color: #fff;
}
</style>
</head>

<body>
	<!-- 头部和导航 -->
	<c:import url="../../common/header.jsp"></c:import>
	
	<div class="list-content-0 content-img">
		<div class="w-full img">
			<div class="vuscat-content">
				<div class="v2-t-menu-t"></div>
			</div>
		</div>
		<div class="w-full">
			<div class="uscat-content" style="text-align:left;position: unset;">
				<!-- 左侧 -->
				<c:import url="menu.jsp"></c:import>
				
				<!-- 右侧 -->
				<div class="w-right">
					<div class="u-box-r">
						<div class="t"><span>修改头像</span></div>
						<!--正文内容-->
						<div class="main">
							<!--静态提示-->
							<p class="tip warning">请勿使用包含不良信息或敏感内容的图片作为用户头像。</p>
							<!--分割线-->
							<p class="divider"></p>
							<!--上传组件区域-->
							<div class="unit">
								<a href="javascript:;" class="button indigo">
									<label for="upload-avatar">点击这里上传图片</label>
								</a>
								<input type="file" id="upload-avatar" class="hide" accept="image/gif, image/jpeg, image/jpg, image/png" />
								<p class="hint">支持JPG、GIF、PNG格式，文件应小于5M，文件太大将导致无法读取</p>
							</div>
							<!--分割线-->
							<p class="divider"></p>
							<!--头像上传预览区域-->
							<div class="unit">
								<div class="original">
									<div id="image-box" class="image-box">
										<!--裁剪层-->
										<div id="cut-box" class="cut-box"></div>
										<!--背景层（可移动图片）-->
										<div id="move-box" class="move-box"></div>
									</div>
									<!--放大、缩小-->
									<span id="narrow" class="icon-zoom_out" style="color: #666;font-size: 20px;"></span>
									<span id="enlarge" class="icon-zoom_in" style="color: #666;font-size: 20px;float: right;"></span>
								</div>
								<!--裁剪后的预览区域-->
								<div class="preview">
									<!--静态提示-->
									<p class="tip">
										您上传的头像会自动生成3种尺寸，请注意中、小尺寸的头像是否清晰。
									</p>
									<div class="view">
										<div class="view-avatar180">
											<div class="avatar180"></div>
											<p class="hint">大尺寸头像，180像素X180像素</p>
										</div>
										<div class="view-avatar50">
											<div class="avatar50"></div>
											<p class="hint">中尺寸头像，50像素X50像素</p>
										</div>
										<div class="view-avatar30">
											<div class="avatar30"></div>
											<p class="hint">小尺寸头像，30像素X30像素</p>
										</div>
									</div>
								</div>
								<!--自动返回裁剪后的图片地址-->
								<input type="hidden" id="data-url" value="" />
								<!--清浮动-->
								<span class="clearfix"></span>
							</div>
							<!--分割线-->
							<p class="divider"></p>
							<!--保存头像区域-->
							<div class="unit">
								<button class="button navy" onclick="saveAvatar()">保存头像</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--底部-->
	<c:import url="../../common/footer.jsp"></c:import>
</body>
<script>
	// 点击上传（必须用change）
	$("#upload-avatar").change(function() {
		javaex.uploadAvatar(
			this,	// 必填
			{
				imgDivId : "image-box",	// 本地上传的图片区域id
				cutBox : "cut-box",	// 裁剪区域id
				moveBox : "move-box",	// 背景区域id，可拖动
				dataUrl : "data-url",	// 最终将图片地址返回给哪个input存储
				type : "base64"		// 图片地址类型，目前仅支持base64
			}
		);
	});
	
	// 缩小
	$("#narrow").click(function() {
		javaex.narrow();
	});
	
	// 放大
	$("#enlarge").click(function() {
		javaex.enlarge();
	});
	
	// 保存用户头像
	function saveAvatar() {
		$.ajax({
			url : "${pageContext.request.contextPath}/forum/user_profile_info/save_avatar.json",
			type : "POST",
			dataType : "json",
			data : {
				"avatar" : $("#data-url").val()
			},
			success : function(rtn) {
				if (rtn.code=="000000") {
					javaex.optTip({
						content : "修改成功",
						type : "success"
					});
					// 建议延迟加载
					setTimeout(function() {
						// 刷新页面
						parent.location.reload();
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
