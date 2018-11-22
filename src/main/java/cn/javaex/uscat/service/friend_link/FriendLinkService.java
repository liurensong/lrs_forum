package cn.javaex.uscat.service.friend_link;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.dao.friend_link.IFriendLinkDAO;
import cn.javaex.uscat.view.FriendLink;

@Service("FriendLinkService")
public class FriendLinkService {
	@Autowired
	private IFriendLinkDAO iFriendLinkDAO;

	/**
	 * 查询友情链接列表
	 */
	public List<FriendLink> list() {
		return iFriendLinkDAO.list();
	}
	
	/**
	 * 保存友情链接
	 * @param friendLinkList
	 */
	public void save(List<FriendLink> friendLinkList) {
		for (FriendLink friendLink : friendLinkList) {
			if (StringUtils.isEmpty(friendLink.getId())) {
				// 插入
				iFriendLinkDAO.insert(friendLink);
			} else {
				// 更新
				iFriendLinkDAO.update(friendLink);
			}
		}
	}

	/**
	 * 删除友情链接
	 * @param idArr 友情链接主键数组
	 */
	public void delete(String[] idArr) {
		iFriendLinkDAO.delete(idArr);
	}

}
