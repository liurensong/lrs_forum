package cn.javaex.uscat.service.thread_reply_info;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.dao.thread_reply_info.IThreadReplyInfoDAO;
import cn.javaex.uscat.dao.user_count.IUserCountDAO;
import cn.javaex.uscat.dao.user_info.IUserInfoDAO;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.user_count.UserCountService;
import cn.javaex.uscat.service.user_perm.UserPermService;
import cn.javaex.uscat.view.ThreadReplyInfo;
import cn.javaex.uscat.view.UserCount;
import cn.javaex.uscat.view.UserInfo;
import cn.javaex.uscat.view.UserPerm;

@Service("ThreadReplyInfoService")
public class ThreadReplyInfoService {
	@Autowired
	private IThreadReplyInfoDAO iThreadReplyInfoDAO;
	@Autowired
	private IUserInfoDAO iUserInfoDAO;
	@Autowired
	private IUserCountDAO iUserCountDAO;
	@Autowired
	private UserPermService userPermService;
	@Autowired
	private UserCountService userCountService;
	
	/**
	 * 根据主题id，查询帖子回复内容
	 * @param tid 主题id
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public List<ThreadReplyInfo> listByTid(String tid) throws IllegalAccessException, InvocationTargetException {
		// 1.0 查询该帖子的回复记录
		List<ThreadReplyInfo> list = iThreadReplyInfoDAO.listByTid(tid);
		// 2.0 循环每一条记录
		for (ThreadReplyInfo threadReplyInfo : list) {
			// 2.1 查询回帖人的信息
			String userId = threadReplyInfo.getReplyUserId();
			// 2.1.1 查询该回复记录对应的回复人的信息
			UserInfo userInfo = iUserInfoDAO.selectUserInfoById(userId);
			// 2.1.2 查询用户的发帖数记录
			UserCount userCount = iUserCountDAO.selectByUserId(userId);
			userInfo.setUserCount(userCount);
			// 2.1.3 设置回帖人的信息
			threadReplyInfo.setUserInfo(userInfo);
			
			// 2.2 如果是对楼层的回复，则需要添加引用
			if (!StringUtils.isEmpty(threadReplyInfo.getBeReplyId())) {
				ThreadReplyInfo quote = selectById(threadReplyInfo.getBeReplyId());
				threadReplyInfo.setQuote(quote);
			}
		}
		return list;
	}
	
	/**
	 * 根据帖子回复表的主键，查询当前楼层的回复内容
	 * @param id 帖子回复表的id
	 * @return
	 */
	public ThreadReplyInfo selectById(String id) {
		return iThreadReplyInfoDAO.selectById(id);
	}

	/**
	 * 查询回收站的回帖
	 * @return
	 */
	public List<Map<String, Object>> listRecycle() {
		return iThreadReplyInfoDAO.listRecycle();
	}

	/**
	 * 删除楼层（回收站）
	 * @param id 回帖id
	 * @param userInfo
	 * @throws QingException 
	 */
	public void deleteFloor(String id, UserInfo userInfo) throws QingException {
		// 1.0 根据回帖id，查询该回帖所在版块
		String fid = iThreadReplyInfoDAO.selectFidById(id);
		
		// 2.0 查询板块权限
		UserPerm userPerm = userPermService.setUserPerm(fid, userInfo);
		if (!"1".equals(userPerm.getIsAdmin())) {
			throw new QingException(ErrorMsg.ERROR_600006);
		}
		
		// 将回帖扔到回收站
		ThreadReplyInfo threadReplyInfo = new ThreadReplyInfo();
		threadReplyInfo.setId(id);
		threadReplyInfo.setStatus("2");
		iThreadReplyInfoDAO.update(threadReplyInfo);
	}

	/**
	 * 批量处理回收站的回帖
	 * @param idArr 回帖id数组
	 * @param optArr 操作数组
	 */
	public void batchOpt(String[] idArr, String[] optArr) {
		for (int i=0; i<idArr.length; i++) {
			String id = idArr[i];
			if ("delete".equals(optArr[i])) {
				// 删除
				ThreadReplyInfo threadReplyInfo = iThreadReplyInfoDAO.selectById(id);
				if (threadReplyInfo!=null) {
					// 1.0 积分变更
					userCountService.update(threadReplyInfo.getReplyUserId(), "delreply");
					
					// 2.0 删除
					iThreadReplyInfoDAO.deleteById(id);
				}
			} else if ("recovery".equals(optArr[i])) {
				// 还原
				ThreadReplyInfo threadReplyInfo = new ThreadReplyInfo();
				threadReplyInfo.setId(id);
				threadReplyInfo.setStatus("1");
				iThreadReplyInfoDAO.update(threadReplyInfo);
			}
		}
	}

	/**
	 * 全部还原
	 */
	public void recoveryAll() {
		// 1.0 查询所有回收站的帖子
		List<ThreadReplyInfo> list = iThreadReplyInfoDAO.listRecycle2();
		if (list!=null && list.isEmpty()==false) {
			// 2.0 遍历更新
			for (ThreadReplyInfo threadReplyInfo : list) {
				threadReplyInfo.setStatus("1");
				iThreadReplyInfoDAO.update(threadReplyInfo);
			}
		}
	}

	/**
	 * 全部删除
	 */
	public void deleteAll() {
		// 1.0 查询所有回收站的帖子
		List<ThreadReplyInfo> list = iThreadReplyInfoDAO.listRecycle2();
		if (list!=null && list.isEmpty()==false) {
			// 2.0 遍历删除
			for (ThreadReplyInfo threadReplyInfo : list) {
				// 2.1 积分变更
				userCountService.update(threadReplyInfo.getReplyUserId(), "delreply");
			}
			
			// 3.0 删除所有回收站的帖子
			iThreadReplyInfoDAO.deleteStatus2();
		}
	}

}
