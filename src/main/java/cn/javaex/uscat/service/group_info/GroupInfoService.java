package cn.javaex.uscat.service.group_info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.dao.board_group_perm.IBoardGroupPermDAO;
import cn.javaex.uscat.dao.group_info.IGroupInfoDAO;
import cn.javaex.uscat.dao.user_profile_info.IUserProfileInfoDAO;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.view.BoardGroupPerm;
import cn.javaex.uscat.view.GroupInfo;

@Service("GroupInfoService")
public class GroupInfoService {
	@Autowired
	private IGroupInfoDAO iGroupInfoDAO;
	@Autowired
	private IUserProfileInfoDAO iUserProfileInfoDAO;
	@Autowired
	private IBoardGroupPermDAO iBoardGroupPermDAO;
	
	/**
	 * 根据类型查询用户组列表
	 * @param type 用户组类型
	 * @return
	 */
	public List<GroupInfo> listByType(String type) {
		return iGroupInfoDAO.listByType(type);
	}

	/**
	 * 保存用户组
	 * @param groupInfoList
	 * @throws QingException
	 */
	public void save(List<GroupInfo> groupInfoList) throws QingException {
		for (GroupInfo groupInfo : groupInfoList) {
			if (StringUtils.isEmpty(groupInfo.getId())) {
				// 插入
				// 1.0 判断组名是否重复
				int count = iGroupInfoDAO.countByName(groupInfo.getName(), null);
				if (count>0) {
					throw new QingException(ErrorMsg.ERROR_200001);
				}
				
				// 2.0 判断积分是否重复
				count = iGroupInfoDAO.countByPoint(groupInfo.getPoint(), null);
				if (count>0) {
					throw new QingException(ErrorMsg.ERROR_200003);
				}
				
				// 3.0 插入
				groupInfo.setType("user");
				iGroupInfoDAO.insert(groupInfo);
			} else {
				// 更新
				// 1.0 判断组名是否重复（排除自己）
				int count = iGroupInfoDAO.countByName(groupInfo.getName(), groupInfo.getId());
				if (count>0) {
					throw new QingException(ErrorMsg.ERROR_200001);
				}
				
				// 2.0 判断积分是否重复（排除自己）
				count = iGroupInfoDAO.countByPoint(groupInfo.getPoint(), groupInfo.getId());
				if (count>0) {
					throw new QingException(ErrorMsg.ERROR_200003);
				}
				
				// 3.0 更新
				iGroupInfoDAO.update(groupInfo);
			}
		}
	}

	/**
	 * 删除用户组
	 * @param idArr 用户组主键数组
	 * @throws QingException 
	 */
	public void delete(String[] idArr) throws QingException {
		// 1.0 判断所选用户组有没有被使用的
		for (int i=0; i<idArr.length; i++) {
			int count = iUserProfileInfoDAO.countByGroupId(idArr[i]);
			if (count>0) {
				throw new QingException(ErrorMsg.ERROR_200002);
			}
		}
		
		// 2.0 批量删除用户组
		iGroupInfoDAO.delete(idArr);
		
		// 3.0 至少需要保留最低积分为0分的用户组
		int count = iGroupInfoDAO.countByPoint("0", null);
		if (count==0) {
			throw new QingException(ErrorMsg.ERROR_200004);
		}
	}

	/**
	 * 根据板块id，查询所有会员用户组，及其板块权限
	 * @param fid 板块id
	 * @return
	 */
	public List<GroupInfo> listPermByFid(String fid) {
		// 1.0 查询所有会员用户组和游客
		List<GroupInfo> list = iGroupInfoDAO.listByPermSetting();
		if (list!=null && list.isEmpty()==false) {
			for (GroupInfo groupInfo : list) {
				// 2.0 根据板块id，用户组id，查询每个用户组对板块的权限设置
				BoardGroupPerm boardGroupPerm = iBoardGroupPermDAO.selectByFidAndGroupId(fid, groupInfo.getId());
				groupInfo.setBoardGroupPerm(boardGroupPerm);
			}
		}
		
		return list;
	}

	/**
	 * 查询用户组列表
	 */
	public List<GroupInfo> list() {
		return iGroupInfoDAO.list();
	}

	/**
	 * 根据最低积分，查询用户组信息
	 * @param point
	 * @return
	 */
	public GroupInfo selectByPoint(int point) {
		return iGroupInfoDAO.selectByPoint(point);
	}

}
