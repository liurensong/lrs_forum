package cn.javaex.uscat.dao.nav_info;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.NavInfo;

public interface INavInfoDAO {

	/**
	 * 查询导航列表
	 */
	List<NavInfo> list();

	/**
	 * 插入新的导航
	 * @param navInfo
	 * @return
	 */
	int insert(NavInfo navInfo);
	
	/**
	 * 更新导航
	 * @param navInfo
	 * @return
	 */
	int update(NavInfo navInfo);

	/**
	 * 批量删除导航
	 * @param idArr 主键数组
	 * @return
	 */
	int delete(@Param("idArr") String[] idArr);

	/**
	 * 可用导航列表
	 * @return
	 */
	List<NavInfo> listIsUse();

	/**
	 * 查询网站首页链接
	 * @return
	 */
	String selectIndexLink();

	/**
	 * 频道编辑时，同步更新导航
	 * @param navInfo
	 */
	int updateByChannel(NavInfo navInfo);

	/**
	 * 根据频道主键删除导航
	 * @param channelId
	 */
	int deleteByChannelId(String channelId);

	/**
	 * 查询自定义页面导航信息
	 * @param channelId
	 * @return
	 */
	NavInfo selectByChannelId(String channelId);
}