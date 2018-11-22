package cn.javaex.uscat.service.user_profile_info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.dao.group_info.IGroupInfoDAO;
import cn.javaex.uscat.dao.user_count.IUserCountDAO;
import cn.javaex.uscat.dao.user_profile_info.IUserProfileInfoDAO;
import cn.javaex.uscat.view.UserCount;
import cn.javaex.uscat.view.UserProfileInfo;

@Service("UserProfileInfoService")
public class UserProfileInfoService {
	
	@Autowired
	private IUserProfileInfoDAO iUserProfileInfoDAO;
	@Autowired
	private IUserCountDAO iUserCountDAO;
	@Autowired
	private IGroupInfoDAO iGroupInfoDAO;
	
	/**
	 * 根据总积分，变更用户的用户组
	 * @param userId 用户id
	 */
	public void updateUserGroup(String userId) {
		UserCount userCount = iUserCountDAO.selectByUserId(userId);
		// 计算最新总积分
		// 总积分计算公式：发帖数（主题）+精华帖数*5+贡献*2+铜币
		int point = userCount.getPosts() + userCount.getDigestposts()*5 + userCount.getExtcredits4()*2 + userCount.getExtcredits1();
		// 查询最新总积分对应的用户组id
		String groupId = iGroupInfoDAO.selectGroupIdByPoint(String.valueOf(point));
		// 变更用户的用户组
		UserProfileInfo userProfileInfo = new UserProfileInfo();
		userProfileInfo.setUserId(userId);
		userProfileInfo.setGroupId(groupId);
		iUserProfileInfoDAO.update(userProfileInfo);
	}
	
	/**
	 * 保存用户扩展信息
	 * @param userProfileInfo
	 */
	public void save(UserProfileInfo userProfileInfo) {
		iUserProfileInfoDAO.update(userProfileInfo);
	}
}
