package cn.javaex.uscat.action.register_setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.service.register_setting.RegisterSettingService;
import cn.javaex.uscat.view.RegisterSetting;
import cn.javaex.uscat.view.Result;

@Controller
@RequestMapping("register_setting")
public class RegisterSettingAction {

	@Autowired
	private RegisterSettingService registerSettingService;
	
	/**
	 * 查询注册设置
	 */
	@RequestMapping("edit.action")
	public String edit(ModelMap map) {
		
		RegisterSetting registerSetting = registerSettingService.select();
		map.put("registerSetting", registerSetting);
		
		return "admin/register_setting/edit";
	}
	
	/**
	 * 保存注册设置
	 * @param registerSetting
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(RegisterSetting registerSetting) {
		
		registerSettingService.save(registerSetting);
		return Result.success();
	}
}
