package cn.javaex.uscat.dao.user_count;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.UserCount;

public interface IUserCountDAO {

	/**
	 * 插入
	 * @param userCount
	 * @return
	 */
	int insert(UserCount userCount);

	/**
	 * 更新
	 * @param userCount
	 * @return
	 */
	int update(UserCount userCount);

	/**
	 * 根据用户id，查询用户积分记录数据
	 * @param userId
	 * @return
	 */
	UserCount selectByUserId(String userId);

	/**
	 * 清空用户积分、帖子数等信息记录表
	 * @param userIdArr 用户id数组
	 * @return
	 */
	int clearByUserIdArr(@Param("userIdArr") String[] userIdArr);
}