package cn.javaex.uscat.service.board_group_perm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.dao.board_group_perm.IBoardGroupPermDAO;
import cn.javaex.uscat.dao.board_info.IBoardInfoDAO;
import cn.javaex.uscat.dao.group_info.IGroupInfoDAO;
import cn.javaex.uscat.view.BoardGroupPerm;
import cn.javaex.uscat.view.BoardInfo;
import cn.javaex.uscat.view.UserInfo;

@Service("BoardGroupPermService")
public class BoardGroupPermService {
	@Autowired
	private IBoardGroupPermDAO iBoardGroupPermDAO;
	@Autowired
	private IBoardInfoDAO iBoardInfoDAO;
	@Autowired
	private IGroupInfoDAO iGroupInfoDAO;
	
	/**
	 * 保存板块与用户组之间的权限设置
	 * @param fid 板块id
	 * @param isDefaultPerm 是否启用默认权限
	 * @param boardGroupPermList
	 */
	public void save(String fid, String isDefaultPerm, List<BoardGroupPerm> boardGroupPermList) {
		BoardInfo boardInfo = new BoardInfo();
		boardInfo.setFid(fid);
		boardInfo.setIsDefaultPerm(isDefaultPerm);
		iBoardInfoDAO.update(boardInfo);
		
		// 判断是否启用默认权限
		if (!"1".equals(isDefaultPerm)) {
			// 需要为每个会员用户组单独设置权限
			for (BoardGroupPerm boardGroupPerm : boardGroupPermList) {
				// 判断是新增还是更新
				// 根据板块id和会员用户组id，查询记录数，有1条记录，则说明是更新，反之为新增
				int count = iBoardGroupPermDAO.countByFidAndGroupId(fid, boardGroupPerm.getGroupId());
				if (count==0) {
					// 新增
					iBoardGroupPermDAO.insert(boardGroupPerm);
				} else {
					// 更新
					// 根据板块id和会员用户组id更新权限规则
					iBoardGroupPermDAO.update(boardGroupPerm);
				}
			}
		}
	}

	/**
	 * 校验用户是否有权限
	 * @param fid 板块id
	 * @param userInfo
	 * @param type 行为类型
	 * @return
	 */
	public boolean checkPerm(String fid, UserInfo userInfo, String type) {
		String groupId = "";
		if (userInfo==null) {
			// 游客
			groupId = iGroupInfoDAO.selectGroupIdByNameAndType("游客", "visitor");
		} else {
			// 会员用户组
			if ("system".equals(userInfo.getGroupType())) {
				// 管理组直接跳过
				return true;
			}
			groupId = userInfo.getGroupId();
		}
		
		if (!"".equals(groupId)) {
			try {
				String perm = iBoardGroupPermDAO.selectByFidAndGroupIdAndType(fid, groupId, type);
				if ("1".equals(perm)) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
		
		return false;
	}

}
