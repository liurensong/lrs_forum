package cn.javaex.uscat.dao.friend_link;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.FriendLink;

public interface IFriendLinkDAO {

	/**
	 * 查询友情链接列表
	 * @return
	 */
	List<FriendLink> list();

	/**
	 * 插入新的友情链接
	 * @param friendLink
	 */
	int insert(FriendLink friendLink);

	/**
	 * 更新友情链接
	 * @param friendLink
	 */
	int update(FriendLink friendLink);

	/**
	 * 批量删除友情链接
	 * @param idArr
	 */
	int delete(@Param("idArr") String[] idArr);
	
}