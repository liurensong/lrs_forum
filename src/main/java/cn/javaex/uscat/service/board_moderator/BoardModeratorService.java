package cn.javaex.uscat.service.board_moderator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.dao.board_moderator.IBoardModeratorDAO;
import cn.javaex.uscat.dao.group_info.IGroupInfoDAO;
import cn.javaex.uscat.dao.user_info.IUserInfoDAO;
import cn.javaex.uscat.dao.user_profile_info.IUserProfileInfoDAO;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.user_profile_info.UserProfileInfoService;
import cn.javaex.uscat.view.BoardModerator;
import cn.javaex.uscat.view.UserProfileInfo;

@Service("BoardModeratorService")
public class BoardModeratorService {
	@Autowired
	private IBoardModeratorDAO iBoardModeratorDAO;
	@Autowired
	private IUserInfoDAO iUserInfoDAO;
	@Autowired
	private IUserProfileInfoDAO iUserProfileInfoDAO;
	@Autowired
	private UserProfileInfoService userProfileInfoService;
	@Autowired
	private IGroupInfoDAO iGroupInfoDAO;
	
	/**
	 * 查询板块下的版主
	 * @param fid 板块id
	 * @return
	 */
	public List<BoardModerator> listByFid(String fid) {
		return iBoardModeratorDAO.listByFid(fid);
	}

	/**
	 * 保存版主
	 * @param fid 板块id
	 * @param loginNameArr 用户名数组
	 * @throws QingException
	 */
	public void save(String fid, String[] loginNameArr) throws QingException {
		// 1.0 遍历当前的版主，重新计算他们的总积分，并重新设置用户组
		List<BoardModerator> list = iBoardModeratorDAO.listByFid(fid);
		if (list!=null && list.isEmpty()==false) {
			for (BoardModerator boardModerator : list) {
				userProfileInfoService.updateUserGroup(boardModerator.getUserId());
			}
		}
		
		// 2.0 删除当前板块下的所有数据
		iBoardModeratorDAO.deleteByFid(fid);
		
		// 3.0 遍历loginNameArr数组
		for (int i=0; i<loginNameArr.length; i++) {
			// 4.0 根据用户名，查询用户id
			String userId = iUserInfoDAO.selectUserIdByLoginName(loginNameArr[i]);
			if (StringUtils.isEmpty(userId)) {
				throw new QingException("用户名：" + loginNameArr[i] + " 不存在");
			}
			// 5.0 根据板块id、用户id，查询是否已有重复数据
			int count = iBoardModeratorDAO.countByFidAndUserId(fid, userId);
			if (count==0) {
				// 6.0 不存在重复数据时，执行插入操作
				BoardModerator boardModerator = new BoardModerator();
				boardModerator.setFid(fid);
				boardModerator.setUserId(userId);
				iBoardModeratorDAO.insert(boardModerator);
				
				// 7.0 将该用户的用户组变更为版主
				// 查询版主的用户组id
				String groupId = iGroupInfoDAO.selectGroupIdByNameAndType("版主", "system");
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
	 * 删除版主
	 * @param idArr 主键数组
	 */
	public void delete(String[] idArr) {
		// 1.0 遍历当前的版主，重新计算他们的总积分，并重新设置用户组
		for (int i=0; i<idArr.length; i++) {
			BoardModerator boardModerator = iBoardModeratorDAO.selectById(idArr[i]);
			userProfileInfoService.updateUserGroup(boardModerator.getUserId());
		}
		
		// 2.0 删除这些版主
		iBoardModeratorDAO.delete(idArr);
	}
	
}
