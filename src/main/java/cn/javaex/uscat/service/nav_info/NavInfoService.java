package cn.javaex.uscat.service.nav_info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.dao.nav_info.INavInfoDAO;
import cn.javaex.uscat.view.NavInfo;

@Service("NavInfoService")
public class NavInfoService {
	@Autowired
	private INavInfoDAO iNavInfoDAO;

	/**
	 * 查询导航列表
	 */
	public List<NavInfo> list() {
		return iNavInfoDAO.list();
	}
	
	/**
	 * 可用导航列表
	 * @return
	 */
	public List<NavInfo> listIsUse() {
		return iNavInfoDAO.listIsUse();
	}

	/**
	 * 保存导航
	 * @param NavInfoList
	 */
	public void save(List<NavInfo> navInfoList) {
		for (NavInfo navInfo : navInfoList) {
			if (StringUtils.isEmpty(navInfo.getId())) {
				// 插入
				navInfo.setType("user");
				iNavInfoDAO.insert(navInfo);
			} else {
				// 更新
				iNavInfoDAO.update(navInfo);
			}
		}
	}

	/**
	 * 删除导航
	 * @param idArr 导航主键数组
	 */
	public void delete(String[] idArr) {
		iNavInfoDAO.delete(idArr);
	}

	/**
	 * 查询网站首页链接
	 * @return
	 */
	public String selectIndexLink() {
		return iNavInfoDAO.selectIndexLink();
	}

	/**
	 * 查询自定义页面导航信息
	 * @param channelId
	 * @return
	 */
	public NavInfo selectByChannelId(String channelId) {
		return iNavInfoDAO.selectByChannelId(channelId);
	}

}
