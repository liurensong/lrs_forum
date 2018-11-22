package cn.javaex.uscat.service.web_info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.dao.web_info.IWebInfoDAO;
import cn.javaex.uscat.view.WebInfo;

@Service("WebInfoService")
public class WebInfoService {
	@Autowired
	private IWebInfoDAO iWebInfoDAO;

	/**
	 * 查询网站信息
	 * @return
	 */
	public WebInfo select() {
		return iWebInfoDAO.select();
	}

	/**
	 * 保存站点信息配置
	 * @param webInfo
	 */
	public void save(WebInfo webInfo) {
		iWebInfoDAO.update(webInfo);
	}

}
