package cn.javaex.uscat.dao.user_profile_info;

import java.util.Map;

import cn.javaex.uscat.view.UserProfileInfo;

public interface IUserProfileInfoDAO {

	/**
	 * 注册新的用户扩展信息
	 * @param userProfileInfo
	 */
	int insert(UserProfileInfo userProfileInfo);

	/**
	 * 查询指定用户组的数量
	 * @param groupId 用户组主键
	 * @return
	 */
	int countByGroupId(String groupId);

	/**
	 * 保存用户信息扩展信息
	 * @param userProfileInfo
	 */
	int update(UserProfileInfo userProfileInfo);

	/**
	 * 批量更新用户扩展信息
	 * @param param
	 */
	int batchUpdate(Map<String, Object> param);

	/**
	 * 根据用户id，查询用户扩展信息
	 * @param userId 用户id
	 * @return
	 */
	UserProfileInfo selectByUserId(String userId);

}