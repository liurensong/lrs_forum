package cn.javaex.uscat.action.upload_info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.service.upload_info.UploadInfoService;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.UploadInfo;

@Controller
@RequestMapping("upload_info")
public class UploadInfoAction {

	@Autowired
	private UploadInfoService uploadInfoService;
	
	/**
	 * 上传设置
	 * @param map
	 * @param type 类型
	 * @return
	 */
	@RequestMapping("edit.action")
	public String edit(ModelMap map,
			@RequestParam(required=false, value="type") String type) {
		
		UploadInfo uploadInfo = uploadInfoService.selectByType(type);
		map.put("uploadInfo", uploadInfo);
		
		return "admin/upload_info/edit";
	}
	
	/**
	 * 保存上传设置
	 * @param uploadInfo
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(UploadInfo uploadInfo) {
		
		uploadInfoService.save(uploadInfo);
		
		return Result.success();
	}
	
}
