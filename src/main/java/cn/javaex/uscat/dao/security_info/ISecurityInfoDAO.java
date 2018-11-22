package cn.javaex.uscat.dao.security_info;

import cn.javaex.uscat.view.SecurityInfo;

public interface ISecurityInfoDAO {

	/**
	 * 查询防灌水信息
	 * @return
	 */
	SecurityInfo select();

	/**
	 * 保存站点信息配置
	 * @param webInfo
	 */
	int update(SecurityInfo securityInfo);

}