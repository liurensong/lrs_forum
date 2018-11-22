package cn.javaex.uscat.dao.user_info;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.UserInfo;

public interface IUserInfoDAO {
	
	/**
	 * 查询用户
	 * @param loginName 登录名
	 * @param password 登录密码
	 * @return
	 */
	UserInfo selectUser(@Param("loginName") String loginName, @Param("password") String password);

	/**
	 * 通过id查询用户信息，用于帖子内容页显示用户信息
	 * @param id 用户id
	 * @return
	 */
	UserInfo selectUserInfoById(String id);

	/**
	 * 查询用户数量
	 * @param loginName 注册时填写的账号
	 * @param email 注册时填写的邮箱
	 * @return
	 */
	int countUser(@Param("loginName") String loginName, @Param("email") String email);
	
	/**
	 * 注册新用户
	 * @param userInfo
	 */
	int insert(UserInfo userInfo);

	/**
	 * 更新用户信息
	 * @param userInfo
	 * @return
	 */
	int update(UserInfo userInfo);

	/**
	 * 根据邮箱查询用户
	 * @param email
	 * @return
	 */
	UserInfo selectUserByEmail(String email);

	/**
	 * 根据用户id，查询用户的激活状态
	 * @param id 用户id
	 * @return
	 */
	String selectUserStatusById(String id);

	/**
	 * 查询所有用户
	 * @param param
	 * @return
	 */
	List<UserInfo> list(Map<String, Object> param);

	/**
	 * 批量更新用户
	 * @param param
	 */
	int batchUpdate(Map<String, Object> param);

	/**
	 * 根据主键查询用户信息（后台管理专用）
	 * @param id 用户id
	 * @return
	 */
	UserInfo selectById(String id);

	/**
	 * 根据用户名，查询用户id
	 * @param loginName 用户名
	 * @return
	 */
	String selectUserIdByLoginName(String loginName);

	/**
	 * 根据旧密码和邮箱，判断查询用户
	 * @param password
	 * @param email
	 * @return
	 */
	UserInfo selectByPasswordAndEmail(@Param("password") String password, @Param("email") String email);

}