package cn.javaex.uscat.dao.web_info;

import cn.javaex.uscat.view.WebInfo;

public interface IWebInfoDAO {

	/**
	 * 查询网站信息
	 * @return
	 */
	WebInfo select();

	/**
	 * 保存站点信息配置
	 * @param webInfo
	 */
	int update(WebInfo webInfo);

}