package cn.javaex.uscat.action.upload_info;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.upload_info.UploadInfoService;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.UploadInfo;

@Controller
@RequestMapping("forum/upload_info")
public class OpenAction {

	@Autowired
	private UploadInfoService uploadInfoService;
	
	/**
	 * 上传本地图片到七牛云
	 * @param file
	 * @param type 类型
	 * @return
	 * @throws IOException
	 * @throws QingException
	 */
	@RequestMapping("upload_image.json")
	@ResponseBody
	public Result uploadImage(
			MultipartFile file,
			@RequestParam(value="type") String type) throws IOException, QingException {
		
		UploadInfo uploadInfo = uploadInfoService.selectByType(type);
		String imgUrl = uploadInfoService.uploadImage(file, uploadInfo);
		
		return Result.success().add("imgUrl", imgUrl);
	}
	
	/**
	 * 上传头像（200*200）（本地上传）
	 * @param file
	 * @param type 类型
	 * @return
	 * @throws IOException
	 * @throws QingException
	 */
	@RequestMapping("upload_avatar.json")
	@ResponseBody
	public Result uploadAvatar(
			MultipartFile file,
			@RequestParam(value="type") String type) throws IOException, QingException {
		
		UploadInfo uploadInfo = uploadInfoService.selectByType(type);
		String imgUrl = uploadInfoService.uploadAvatar(file, uploadInfo);
		
		return Result.success().add("imgUrl", imgUrl);
	}
}
