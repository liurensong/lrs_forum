package cn.javaex.uscat.service.register_setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.dao.register_setting.IRegisterSettingDAO;
import cn.javaex.uscat.view.RegisterSetting;

@Service("RegisterSettingService")
public class RegisterSettingService {
	@Autowired
	private IRegisterSettingDAO iRegisterSettingDAO;

	/**
	 * 查询注册设置
	 * @return
	 */
	public RegisterSetting select() {
		return iRegisterSettingDAO.select();
	}

	/**
	 * 保存注册设置
	 * @param registerSetting
	 */
	public void save(RegisterSetting registerSetting) {
		iRegisterSettingDAO.update(registerSetting);
	}

}
