package cn.javaex.uscat.service.user_perm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.dao.board_info.IBoardInfoDAO;
import cn.javaex.uscat.dao.board_moderator.IBoardModeratorDAO;
import cn.javaex.uscat.dao.zone_moderator.IZoneModeratorDAO;
import cn.javaex.uscat.view.BoardInfo;
import cn.javaex.uscat.view.UserInfo;
import cn.javaex.uscat.view.UserPerm;

@Service("UserPermService")
public class UserPermService {

	@Autowired
	private IBoardInfoDAO iBoardInfoDAO;
	@Autowired
	private IZoneModeratorDAO iZoneModeratorDAO;
	@Autowired
	private IBoardModeratorDAO iBoardModeratorDAO;
	
	/**
	 * 设置用户权限
	 * @param fid 板块id
	 * @param userInfo
	 * @return
	 */
	public UserPerm setUserPerm(String fid, UserInfo userInfo) {
		String userId = userInfo.getId();
		BoardInfo boardInfo = iBoardInfoDAO.selectById(fid);
		
		UserPerm userPerm = new UserPerm();
		// 1.0 判断用户对该板块下的帖子是否有管理权限
		if ("管理员".equals(userInfo.getGroupName())) {
			// 1.1 管理员
			userPerm.setIsAdmin("1");
		} else {
			// 1.2 判断用户是不是该分区的超级版主
			int count = iZoneModeratorDAO.countByGidAndUserId(boardInfo.getGid(), userId);
			if (count>0) {
				userPerm.setIsAdmin("1");
			} else {
				// 1.3 判断用户是不是该板块的版主
				count = iBoardModeratorDAO.countByFidAndUserId(fid, userId);
				if (count>0) {
					userPerm.setIsAdmin("1");
				} else {
					userPerm.setIsAdmin("0");
				}
			}
		}
		
		return userPerm;
	}
	
}
