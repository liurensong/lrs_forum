package cn.javaex.uscat.dao.user_count_log;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.UserCountLog;

public interface IUserCountLogDAO {

	/**
	 * 插入
	 * @param userCount
	 * @return
	 */
	int insert(UserCountLog userCountLog);

	/**
	 * 更新
	 * @param userCount
	 * @return
	 */
	int update(UserCountLog userCountLog);

	/**
	 * 根据用户id和行为，查询奖励次数记录
	 * @param userId 用户id
	 * @param action 行为
	 * @return
	 */
	UserCountLog selectByUserIdAndAction(@Param("userId") String userId, @Param("action") String action);

	/**
	 * 根据周期，清空数据
	 * @param cycle 周期
	 * @return
	 */
	int clearByCycle(@Param("cycle") String cycle);

	/**
	 * 删除用户积分获取记录日志
	 * @param userIdArr 用户id数组
	 * @return
	 */
	int deleteByUserIdArr(@Param("userIdArr") String[] userIdArr);

}