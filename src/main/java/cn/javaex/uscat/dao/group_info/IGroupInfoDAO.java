package cn.javaex.uscat.dao.group_info;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.GroupInfo;

public interface IGroupInfoDAO {

	/**
	 * 根据类型查询用户组列表
	 * @param type 用户组类型
	 * @return
	 */
	List<GroupInfo> listByType(String type);

	/**
	 * 插入新的用户组
	 * @param groupInfo
	 * @return
	 */
	int insert(GroupInfo groupInfo);
	
	/**
	 * 更新用户组
	 * @param groupInfo
	 * @return
	 */
	int update(GroupInfo groupInfo);

	/**
	 * 批量删除用户组
	 * @param idArr 主键数组
	 * @return
	 */
	int delete(@Param("idArr") String[] idArr);

	/**
	 * 查询用户组名的数量，用于校验名字重复
	 * @param name 用户组名
	 * @param id 主键
	 * @return
	 */
	int countByName(@Param("name") String name, @Param("id") String id);

	/**
	 * 查询会员用户组积分的数量，用于校验积分重复
	 * @param point 用户组积分
	 * @param id 主键
	 * @return
	 */
	int countByPoint(@Param("point") String point, @Param("id") String id);

	/**
	 * 根据最低积分查询用户组id
	 * @param point
	 * @return
	 */
	String selectGroupIdByPoint(String point);

	/**
	 * 查询用户组列表
	 */
	List<GroupInfo> list();

	/**
	 * 根据用户组名称和类型，查询用户组id
	 * @param name
	 * @param type 
	 * @return
	 */
	String selectGroupIdByNameAndType(@Param("name") String name, @Param("type") String type);

	/**
	 * 查询板块权限设定所需的用户组
	 * @return
	 */
	List<GroupInfo> listByPermSetting();

	/**
	 * 根据最低积分，查询用户组信息
	 * @param point
	 * @return
	 */
	GroupInfo selectByPoint(@Param("point") int point);
}