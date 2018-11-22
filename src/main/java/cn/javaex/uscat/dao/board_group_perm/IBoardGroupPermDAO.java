package cn.javaex.uscat.dao.board_group_perm;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.BoardGroupPerm;

public interface IBoardGroupPermDAO {

	/**
	 * 根据板块id和会员用户组id，查询记录数
	 * @param fid 板块id
	 * @param groupId 用户组id
	 * @return
	 */
	int countByFidAndGroupId(@Param("fid") String fid, @Param("groupId") String groupId);

	/**
	 * 插入新的权限规则
	 * @param boardGroupPerm
	 * @return
	 */
	int insert(BoardGroupPerm boardGroupPerm);

	/**
	 * 根据板块id和会员用户组id更新权限规则
	 * @param boardGroupPerm
	 * @return
	 */
	int update(BoardGroupPerm boardGroupPerm);

	/**
	 * 根据板块id，用户组id，查询每个用户组对板块的权限设置
	 * @param fid 板块id
	 * @param groupId 用户组id
	 * @return
	 */
	BoardGroupPerm selectByFidAndGroupId(@Param("fid") String fid, @Param("groupId") String groupId);

	/**
	 * 查询权限
	 * @param fid
	 * @param groupId
	 * @param type
	 * @return
	 */
	String selectByFidAndGroupIdAndType(@Param("fid") String fid, @Param("groupId") String groupId, @Param("type") String type);

}