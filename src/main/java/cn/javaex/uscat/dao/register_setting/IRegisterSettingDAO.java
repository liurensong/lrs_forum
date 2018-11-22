package cn.javaex.uscat.dao.register_setting;

import cn.javaex.uscat.view.RegisterSetting;

public interface IRegisterSettingDAO {

	/**
	 * 查询注册设置信息
	 * @return
	 */
	RegisterSetting select();

	/**
	 * 保存注册设置
	 * @param registerSetting
	 */
	int update(RegisterSetting registerSetting);
}