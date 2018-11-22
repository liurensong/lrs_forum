package cn.javaex.uscat.action.security_info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.service.security_info.SecurityInfoService;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.SecurityInfo;

@Controller
@RequestMapping("security_info")
public class SecurityInfoAction {

	@Autowired
	private SecurityInfoService securityInfoService;
	
	/**
	 * 查询防灌水信息
	 */
	@RequestMapping("edit.action")
	public String edit(ModelMap map) {
		
		SecurityInfo securityInfo = securityInfoService.select();
		map.put("securityInfo", securityInfo);
		
		return "admin/security_info/edit";
	}
	
	/**
	 * 保存防灌水信息配置
	 * @param securityInfo
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(SecurityInfo securityInfo) {
		
		securityInfoService.save(securityInfo);
		return Result.success();
	}
}
