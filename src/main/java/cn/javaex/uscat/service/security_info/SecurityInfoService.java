package cn.javaex.uscat.service.security_info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.dao.security_info.ISecurityInfoDAO;
import cn.javaex.uscat.view.SecurityInfo;

@Service("SecurityInfoService")
public class SecurityInfoService {
	@Autowired
	private ISecurityInfoDAO iSecurityInfoDAO;

	/**
	 * 查询防灌水信息
	 * @return
	 */
	public SecurityInfo select() {
		return iSecurityInfoDAO.select();
	}

	/**
	 * 保存防灌水信息配置
	 * @param securityInfo
	 */
	public void save(SecurityInfo securityInfo) {
		iSecurityInfoDAO.update(securityInfo);
	}

}
