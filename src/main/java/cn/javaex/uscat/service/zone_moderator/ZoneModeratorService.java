package cn.javaex.uscat.service.zone_moderator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.dao.group_info.IGroupInfoDAO;
import cn.javaex.uscat.dao.user_info.IUserInfoDAO;
import cn.javaex.uscat.dao.user_profile_info.IUserProfileInfoDAO;
import cn.javaex.uscat.dao.zone_moderator.IZoneModeratorDAO;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.user_profile_info.UserProfileInfoService;
import cn.javaex.uscat.view.UserProfileInfo;
import cn.javaex.uscat.view.ZoneModerator;

@Service("ZoneModeratorService")
public class ZoneModeratorService {
	@Autowired
	private IZoneModeratorDAO iZoneModeratorDAO;
	@Autowired
	private IUserInfoDAO iUserInfoDAO;
	@Autowired
	private IUserProfileInfoDAO iUserProfileInfoDAO;
	@Autowired
	private UserProfileInfoService userProfileInfoService;
	@Autowired
	private IGroupInfoDAO iGroupInfoDAO;
	
	/**
	 * 查询分区下的超级版主
	 * @param gid 分区id
	 * @return
	 */
	public List<ZoneModerator> listByGid(String gid) {
		return iZoneModeratorDAO.listByGid(gid);
	}

	/**
	 * 保存超级版主
	 * @param gid 分区id
	 * @param loginNameArr 用户名数组
	 * @throws QingException
	 */
	public void save(String gid, String[] loginNameArr) throws QingException {
		// 1.0 遍历当前的版主，重新计算他们的总积分，并重新设置用户组
		List<ZoneModerator> list = iZoneModeratorDAO.listByGid(gid);
		if (list!=null && list.isEmpty()==false) {
			for (ZoneModerator zoneModerator : list) {
				userProfileInfoService.updateUserGroup(zoneModerator.getUserId());
			}
		}
		
		// 2.0 删除当前分区下的所有数据
		iZoneModeratorDAO.deleteByGid(gid);
		
		// 3.0 遍历loginNameArr数组
		for (int i=0; i<loginNameArr.length; i++) {
			// 4.0 根据用户名，查询用户id
			String userId = iUserInfoDAO.selectUserIdByLoginName(loginNameArr[i]);
			if (StringUtils.isEmpty(userId)) {
				throw new QingException("用户名：" + loginNameArr[i] + " 不存在");
			}
			// 5.0 根据分区id、用户id，查询是否已有重复数据
			int count = iZoneModeratorDAO.countByGidAndUserId(gid, userId);
			if (count==0) {
				// 6.0 不存在重复数据时，执行插入操作
				ZoneModerator zoneModerator = new ZoneModerator();
				zoneModerator.setGid(gid);
				zoneModerator.setUserId(userId);
				iZoneModeratorDAO.insert(zoneModerator);
				
				// 7.0 将该用户的用户组变更为超级版主
				// 查询超级版主的用户组id
				String groupId = iGroupInfoDAO.selectGroupIdByNameAndType("超级版主", "system");
				// 变更用户的用户组
				if (!StringUtils.isEmpty(groupId)) {
					UserProfileInfo userProfileInfo = new UserProfileInfo();
					userProfileInfo.setUserId(userId);
					userProfileInfo.setGroupId(groupId);
					iUserProfileInfoDAO.update(userProfileInfo);
				}
			}
		}
	}

	/**
	 * 删除超级版主
	 * @param idArr 主键数组
	 */
	public void delete(String[] idArr) {
		// 1.0 遍历当前的版主，重新计算他们的总积分，并重新设置用户组
		for (int i=0; i<idArr.length; i++) {
			ZoneModerator zoneModerator = iZoneModeratorDAO.selectById(idArr[i]);
			userProfileInfoService.updateUserGroup(zoneModerator.getUserId());
		}
		
		// 2.0 删除这些超级版主
		iZoneModeratorDAO.delete(idArr);
	}
	
}
