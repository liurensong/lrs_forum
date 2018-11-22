package cn.javaex.uscat.service.user_point_log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.dao.user_point_log.IUserPointLogDAO;
import cn.javaex.uscat.view.UserPointLog;

@Service("UserPointLogService")
public class UserPointLogService {
	@Autowired
	private IUserPointLogDAO iUserPointLogDAO;

	/**
	 * 查询用户的积分记录
	 * @param userId
	 * @return
	 */
	public List<UserPointLog> listByUserId(String userId) {
		return iUserPointLogDAO.listByUserId(userId);
	}

}
