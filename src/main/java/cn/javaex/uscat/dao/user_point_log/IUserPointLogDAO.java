package cn.javaex.uscat.dao.user_point_log;

import java.util.List;

import cn.javaex.uscat.view.UserPointLog;

public interface IUserPointLogDAO {

	/**
	 * 记录用户获取系统积分日志
	 * @param userPointLog
	 */
	int insert(UserPointLog userPointLog);
	
	/**
	 * 查询用户的积分记录
	 * @param userId
	 * @return
	 */
	List<UserPointLog> listByUserId(String userId);
}