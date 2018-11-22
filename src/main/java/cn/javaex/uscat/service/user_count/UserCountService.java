package cn.javaex.uscat.service.user_count;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.dao.group_info.IGroupInfoDAO;
import cn.javaex.uscat.dao.point_rule.IPointRuleDAO;
import cn.javaex.uscat.dao.user_count.IUserCountDAO;
import cn.javaex.uscat.dao.user_count_log.IUserCountLogDAO;
import cn.javaex.uscat.dao.user_point_log.IUserPointLogDAO;
import cn.javaex.uscat.dao.user_profile_info.IUserProfileInfoDAO;
import cn.javaex.uscat.view.PointRule;
import cn.javaex.uscat.view.UserCount;
import cn.javaex.uscat.view.UserCountLog;
import cn.javaex.uscat.view.UserPointLog;
import cn.javaex.uscat.view.UserProfileInfo;

@Service("UserCountService")
public class UserCountService {
	@Autowired
	private IUserCountDAO iUserCountDAO;
	@Autowired
	private IUserCountLogDAO iUserCountLogDAO;
	@Autowired
	private IPointRuleDAO iPointRuleDAO;
	@Autowired
	private IGroupInfoDAO iGroupInfoDAO;
	@Autowired
	private IUserProfileInfoDAO iUserProfileInfoDAO;
	@Autowired
	private IUserPointLogDAO iUserPointLogDAO;
	
	/**
	 * 积分变更
	 * @param userId 用户id
	 * @param action 行为
	 */
	public void update(String userId, String action) {
		// 是否可以获取奖励标识
		boolean flag = false;
		UserCount userCountNew = new UserCount();
		
		// 1.0 查询积分策略
		PointRule pointRule = iPointRuleDAO.selectByAction(action);
		// 校验奖励次数
		String rewardNum = pointRule.getRewardNum();
		try {
			// 奖励限制次数
			int nRewardNum = Integer.parseInt(rewardNum);
			// 目前奖励次数
			int nowRewardNum = 0;
			
			// 2.0 查询用户获取奖励次数（日志）
			UserCountLog userCountLog = iUserCountLogDAO.selectByUserIdAndAction(userId, action);
			if (userCountLog==null) {
				// 2.1 第一次获取奖励，新增日志
				UserCountLog userCountLog2 = new UserCountLog();
				userCountLog2.setUserId(userId);
				userCountLog2.setAction(action);
				userCountLog2.setCycle(pointRule.getCycle());
				userCountLog2.setRewardNum(1);
				
				iUserCountLogDAO.insert(userCountLog2);
			} else {
				nowRewardNum = userCountLog.getRewardNum();
				// 2.2 更新日志
				userCountLog.setRewardNum(nowRewardNum+1);
				
				iUserCountLogDAO.update(userCountLog);
			}
			
			// 判断是否可以获取奖励
			if (nowRewardNum<nRewardNum) {
				flag = true;
			}
		} catch (Exception e) {
			// 奖励不限次数
			flag = true;
		}
		
		// 3.0 判断是否可以获取奖励
		if (flag) {
			// 3.1 查询当前用户现有的积分记录
			UserCount userCountOld = iUserCountDAO.selectByUserId(userId);
			if (userCountOld==null) {
				userCountOld = new UserCount();
				
				// 3.1.1 如果没有查到数据（未知原因导致的错误数据），则自动插入一条数据以修复
				UserCount userCount = new UserCount();
				userCount.setUserId(userId);
				userCount.setExtcredits1(0);
				userCount.setExtcredits2(0);
				userCount.setExtcredits3(0);
				userCount.setExtcredits4(0);
				userCount.setExtcredits5(0);
				userCount.setExtcredits6(0);
				userCount.setPosts(0);
				userCount.setThreads(0);
				userCount.setDigestposts(0);
				
				iUserCountDAO.insert(userCount);
				
				BeanUtils.copyProperties(userCount, userCountOld);
			}
			
			// 3.2 属性拷贝
			BeanUtils.copyProperties(userCountOld, userCountNew);
			
			// 3.3 设置新的积分记录
			// 计算积分（如果是减分，则最低为0分）
			int extcredits1 = userCountOld.getExtcredits1()+Integer.parseInt(pointRule.getExtcredits1());
			userCountNew.setExtcredits1(extcredits1>=0?extcredits1:0);
			int extcredits2 = userCountOld.getExtcredits2()+Integer.parseInt(pointRule.getExtcredits2());
			userCountNew.setExtcredits2(extcredits2>=0?extcredits2:0);
			int extcredits3 = userCountOld.getExtcredits3()+Integer.parseInt(pointRule.getExtcredits3());
			userCountNew.setExtcredits3(extcredits3>=0?extcredits3:0);
			int extcredits4 = userCountOld.getExtcredits4()+Integer.parseInt(pointRule.getExtcredits4());
			userCountNew.setExtcredits4(extcredits4>=0?extcredits4:0);
			int extcredits5 = userCountOld.getExtcredits5()+Integer.parseInt(pointRule.getExtcredits5());
			userCountNew.setExtcredits5(extcredits5>=0?extcredits5:0);
			int extcredits6 = userCountOld.getExtcredits6()+Integer.parseInt(pointRule.getExtcredits6());
			userCountNew.setExtcredits6(extcredits6>=0?extcredits6:0);
			// 判断奖励行为
			if ("post".equals(action)) {
				// 主题数加1
				userCountNew.setPosts(userCountOld.getPosts()+1);
			} else if ("reply".equals(action)) {
				// 回帖数加1
				userCountNew.setThreads(userCountOld.getThreads()+1);
			} else if ("digest".equals(action)) {
				// 精华数加1
				userCountNew.setDigestposts(userCountOld.getDigestposts()+1);
			} else if ("undigest".equals(action)) {
				// 精华数减1
				int nDigestposts = userCountOld.getDigestposts()-1;
				userCountNew.setDigestposts(nDigestposts>=0?nDigestposts:0);
			} else if ("delpost".equals(action)) {
				// 主题数减1
				int nPosts = userCountOld.getPosts()-1;
				userCountNew.setPosts(nPosts>=0?nPosts:0);
			} else if ("delreply".equals(action)) {
				// 回帖数减1
				int nThreads = userCountOld.getThreads()-1;
				userCountNew.setThreads(nThreads>=0?nThreads:0);
			}
			iUserCountDAO.update(userCountNew);
			
			// 4.0 重新计算总积分，变更用户组
			// 4.1 查询用户当前的扩展信息
			UserProfileInfo userProfileInfo = iUserProfileInfoDAO.selectByUserId(userId);
			// 只有会员用户组才自动变更
			if ("user".equals(userProfileInfo.getGroupType())) {
				// 4.2 计算最新总积分
				// 总积分计算公式：发帖数（主题）+精华帖数*5+贡献*2+铜币
				int point = userCountNew.getPosts() + userCountNew.getDigestposts()*5 + userCountNew.getExtcredits4()*2 + userCountNew.getExtcredits1();
				// 4.3 查询最新总积分对应的用户组id
				String groupIdNew = iGroupInfoDAO.selectGroupIdByPoint(String.valueOf(point));
				// 4.4 判断是否需要变更用户的用户组
				if (!groupIdNew.equals(userProfileInfo.getGroupId())) {
					// 变更用户组
					userProfileInfo.setGroupId(groupIdNew);
					
					iUserProfileInfoDAO.update(userProfileInfo);
				}
			}
			
			// 5.0 记录积分日志
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = formatter.format(currentTime);
			
			UserPointLog userPointLog = new UserPointLog();
			userPointLog.setUserId(userId);
			userPointLog.setName(pointRule.getName());
			userPointLog.setExtcredits1(pointRule.getExtcredits1());
			userPointLog.setExtcredits2(pointRule.getExtcredits2());
			userPointLog.setExtcredits3(pointRule.getExtcredits3());
			userPointLog.setExtcredits4(pointRule.getExtcredits4());
			userPointLog.setExtcredits5(pointRule.getExtcredits5());
			userPointLog.setExtcredits6(pointRule.getExtcredits6());
			userPointLog.setTime(now);
			iUserPointLogDAO.insert(userPointLog);
		}
	}

	/**
	 * 查询用户积分
	 * @param userId 用户id
	 * @return
	 */
	public UserCount selectByUserId(String userId) {
		UserCount userCount = iUserCountDAO.selectByUserId(userId);
		// 总积分计算公式：发帖数（主题）+精华帖数*5+贡献*2+铜币
		int point = userCount.getPosts() + userCount.getDigestposts()*5 + userCount.getExtcredits4()*2 + userCount.getExtcredits1();
		userCount.setPoint(point);
		
		return userCount;
	}
	
}
