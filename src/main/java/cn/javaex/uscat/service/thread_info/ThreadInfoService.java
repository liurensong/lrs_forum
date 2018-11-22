package cn.javaex.uscat.service.thread_info;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.dao.security_info.ISecurityInfoDAO;
import cn.javaex.uscat.dao.subject_class.ISubjectClassDAO;
import cn.javaex.uscat.dao.thread_info.IThreadInfoDAO;
import cn.javaex.uscat.dao.thread_reply_info.IThreadReplyInfoDAO;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.user_count.UserCountService;
import cn.javaex.uscat.view.SecurityInfo;
import cn.javaex.uscat.view.ThreadInfo;
import cn.javaex.uscat.view.ThreadReplyInfo;
import cn.javaex.uscat.view.UserInfo;

@Service("ThreadInfoService")
public class ThreadInfoService {
	@Autowired
	private IThreadInfoDAO iThreadInfoDAO;
	@Autowired
	private IThreadReplyInfoDAO iThreadReplyInfoDAO;
	@Autowired
	private ISubjectClassDAO iSubjectClassDAO;
	@Autowired
	private UserCountService userCountService;
	@Autowired
	private ISecurityInfoDAO iSecurityInfoDAO;
	
	/**
	 * 查询主题列表（后台管理专用）
	 * @param param
	 * @return
	 */
	public List<ThreadInfo> list(Map<String, Object> param) {
		// 1.0 查询主题基本信息
		List<ThreadInfo> list = iThreadInfoDAO.list(param);
		
		// 2.0 查询主题的附加信息
		if (list!=null && list.isEmpty()==false) {
			// 2.1 遍历这些主题，并查询关联的帖子回复表中的记录
			for (ThreadInfo threadInfo : list) {
				String tid = threadInfo.getTid();
				// 2.2 根据主题id，查询帖子回复表中的最新一条记录
				ThreadReplyInfo threadReplyInfo = iThreadReplyInfoDAO.selectLastByTid(tid);
				threadInfo.setThreadReplyInfo(threadReplyInfo);
				
				// 2.3 根据主题id，获取帖子的回复数
				int replyCount = iThreadReplyInfoDAO.countReplyByTid(tid);
				threadInfo.setReplyCount(replyCount);
			}
		}
		return list;
	}
	
	/**
	 * 保存帖子
	 * @param userInfo 
	 * @param threadInfo
	 * @param threadReplyInfo
	 * @throws ParseException 
	 * @throws QingException 
	 */
	public void save(UserInfo userInfo, ThreadInfo threadInfo, ThreadReplyInfo threadReplyInfo) throws ParseException, QingException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formatter.format(currentTime);
		
		// 1.0 查询防灌水限制
		checkSecurity(userInfo, now);
		
		// 2.0 判断是新增还是更新
		if (StringUtils.isEmpty(threadInfo.getTid())) {
			// 新增
			// 2.1 帖子主题表
			threadInfo.setCreateTime(now);
			threadInfo.setViewCount("0");
			threadInfo.setViewCountDay("0");
			threadInfo.setViewCountWeek("0");
			threadInfo.setViewCountMonth("0");
			threadInfo.setStatus("1");
			threadInfo.setIsTop("0");
			threadInfo.setIsDigest("0");
			iThreadInfoDAO.insert(threadInfo);
			
			// 2.2 帖子回复表
			threadReplyInfo.setTid(threadInfo.getTid());
			threadReplyInfo.setFloor(1);
			threadReplyInfo.setReplyTime(now);
			threadReplyInfo.setStatus("1");
			iThreadReplyInfoDAO.insert(threadReplyInfo);
			
			// 3.0 积分变更
			userCountService.update(threadInfo.getCreateUserId(), "post");
		} else {
			// 更新
			// 2.1 帖子主题表
			if (!StringUtils.isEmpty(threadInfo.getTitle())) {
				iThreadInfoDAO.update(threadInfo);
			}
			
			// 2.2 帖子回复表
			iThreadReplyInfoDAO.update(threadReplyInfo);
		}
	}

	/**
	 * 发表新回复
	 * @param userInfo 
	 * @param threadReplyInfo
	 * @throws ParseException 
	 * @throws QingException 
	 */
	public void saveThreadReply(UserInfo userInfo, ThreadReplyInfo threadReplyInfo) throws QingException, ParseException {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formatter.format(currentTime);
		
		// 1.0 查询防灌水限制
		checkSecurity(userInfo, now);
		
		// 2.0 插入新的回复记录
		// 查询当前帖子目前最新的楼层
		int floor = iThreadReplyInfoDAO.selectLastFloorByTid(threadReplyInfo.getTid());
		threadReplyInfo.setFloor(floor+1);
		threadReplyInfo.setReplyTime(now);
		threadReplyInfo.setStatus("1");
		
		iThreadReplyInfoDAO.insert(threadReplyInfo);
		
		// 3.0 积分变更
		userCountService.update(threadReplyInfo.getReplyUserId(), "reply");
	}
	
	/**
	 * 防灌水限制校验
	 * @param userInfo
	 * @param now 当前系统时间
	 * @throws QingException
	 * @throws ParseException
	 */
	private void checkSecurity(UserInfo userInfo, String now) throws QingException, ParseException {
		SecurityInfo securityInfo = iSecurityInfoDAO.select();
		// 1.1 头像限制
		if ("1".equals(securityInfo.getIsUploadAvatar())) {
			if (StringUtils.isEmpty(userInfo.getAvatar())) {
				throw new QingException(ErrorMsg.ERROR_600001);
			}
		}
		// 1.2 邮箱验证限制
		if ("1".equals(securityInfo.getIsActivateEmail())) {
			if ("0".equals(userInfo.getIsEmailChecked())) {
				throw new QingException(ErrorMsg.ERROR_600002);
			}
		}
		// 1.3 发帖时间间隔
		if ("0".equals(securityInfo.getReportTimeInterval()) || StringUtils.isEmpty(securityInfo.getReportTimeInterval())) {
			// 不校验
		} else {
			// 1.3.1 查询发帖间隔秒数
			int nReportTimeInterval = Integer.parseInt(securityInfo.getReportTimeInterval());
			// 1.3.2 查询用户上次发帖的时间
			String lastReplyTime = iThreadReplyInfoDAO.selectLastReplyTimeByReplyUserId(userInfo.getId());
			if (!StringUtils.isEmpty(lastReplyTime)) {
				// 1.3.3 计算发帖时间差（秒）
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d1 = df.parse(now);
				Date d2 = df.parse(lastReplyTime);
				long diff = d1.getTime() - d2.getTime();
//				System.out.println("diff:"+diff);
				long seconds = diff / 1000;
				// 1.3.4 判断是否受到防灌水限制
				if (seconds<=nReportTimeInterval) {
					throw new QingException(ErrorMsg.ERROR_600003 + "，至少需要间隔" + nReportTimeInterval + "秒");
				}
			}
		}
	}
	
	/**
	 * 查询置顶主题（按最后编辑时间倒序排列）
	 * @param fid 
	 * @return
	 */
	public List<ThreadInfo> listTopByForumdisplay(String fid) {
		// 1.0 查询置顶主题
		List<ThreadInfo> list = iThreadInfoDAO.listTopByForumdisplay(fid);
		
		// 2.0 查询主题的附加信息
		if (list!=null && list.isEmpty()==false) {
			// 2.1 遍历这些主题，并查询关联的帖子回复表中的记录
			for (ThreadInfo threadInfo : list) {
				String tid = threadInfo.getTid();
				// 2.2 根据主题id，查询帖子回复表中的第一条记录（即主题摘要）
				String contentText = iThreadReplyInfoDAO.selectPostContentByTid(tid);
				threadInfo.setContentText(contentText);
				
				// 2.3 根据主题id，查询帖子回复表中的最新一条记录
				ThreadReplyInfo threadReplyInfo = iThreadReplyInfoDAO.selectLastByTid(tid);
				threadInfo.setThreadReplyInfo(threadReplyInfo);
				
				// 2.4 根据主题id，获取帖子的回复数
				int replyCount = iThreadReplyInfoDAO.countReplyByTid(tid);
				threadInfo.setReplyCount(replyCount);
				
				// 2.5 查询帖子的主题分类
				if (!StringUtils.isEmpty(threadInfo.getSubjectId())) {
					String subjectNameHtml = iSubjectClassDAO.selectNameHtmlBySubjectId(threadInfo.getSubjectId());
					threadInfo.setSubjectNameHtml(subjectNameHtml);
				}
			}
		}
		return list;
	}
	
	/**
	 * 查询精华主题（按最后编辑时间倒序排列）
	 * @param fid
	 * @return
	 */
	public List<ThreadInfo> listDigestByForumdisplay(String fid) {
		// 1.0 查询精华主题
		List<ThreadInfo> list = iThreadInfoDAO.listDigestByForumdisplay(fid);
		
		// 2.0 查询主题的附加信息
		if (list!=null && list.isEmpty()==false) {
			// 2.1 遍历这些主题，并查询关联的帖子回复表中的记录
			for (ThreadInfo threadInfo : list) {
				String tid = threadInfo.getTid();
				// 2.2 根据主题id，查询帖子回复表中的第一条记录（即主题摘要）
				String contentText = iThreadReplyInfoDAO.selectPostContentByTid(tid);
				threadInfo.setContentText(contentText);
				
				// 2.3 根据主题id，查询帖子回复表中的最新一条记录
				ThreadReplyInfo threadReplyInfo = iThreadReplyInfoDAO.selectLastByTid(tid);
				threadInfo.setThreadReplyInfo(threadReplyInfo);
				
				// 2.4 根据主题id，获取帖子的回复数
				int replyCount = iThreadReplyInfoDAO.countReplyByTid(tid);
				threadInfo.setReplyCount(replyCount);
				
				// 2.5 查询帖子的主题分类
				if (!StringUtils.isEmpty(threadInfo.getSubjectId())) {
					String subjectNameHtml = iSubjectClassDAO.selectNameHtmlBySubjectId(threadInfo.getSubjectId());
					threadInfo.setSubjectNameHtml(subjectNameHtml);
				}
			}
		}
		return list;
	}
	
	/**
	 * 查询某个板块下的帖子列表
	 * @param param
	 * @return
	 */
	public List<ThreadInfo> listByForumdisplay(Map<String, Object> param) {
		List<ThreadInfo> list = null;
		
		// 1.0 判断排序方式
		String defaultOrderField = param.get("defaultOrderField").toString();
		if ("create_time".equals(defaultOrderField) || "view_count".equals(defaultOrderField)) {
			// 1.1 如果是按主题创建的时间或浏览次数排序，则直接率先查询主题
			list = iThreadInfoDAO.listByForumdisplay(param);
		} else if ("reply_time".equals(defaultOrderField)) {
			// 1.2 如果是按回复时间排序，则需要联合查询回帖表的数据
			list = iThreadInfoDAO.listByReplyTime(param);
		} else if ("reply_count".equals(defaultOrderField)) {
			// 1.3 如果是按回复数量排序，则需要联合查询回帖表的数据
			list = iThreadInfoDAO.listByReplyCount(param);
		} else {
			return null;
		}
		
		// 2.0 查询主题的附加信息
		if (list!=null && list.isEmpty()==false) {
			// 2.1 遍历这些主题，并查询关联的帖子回复表中的记录
			for (ThreadInfo threadInfo : list) {
				String tid = threadInfo.getTid();
				// 2.2 根据主题id，查询帖子回复表中的第一条记录（即主题摘要）
				String contentText = iThreadReplyInfoDAO.selectPostContentByTid(tid);
				threadInfo.setContentText(contentText);
				
				// 2.3 根据主题id，查询帖子回复表中的最新一条记录
				ThreadReplyInfo threadReplyInfo = iThreadReplyInfoDAO.selectLastByTid(tid);
				threadInfo.setThreadReplyInfo(threadReplyInfo);
				
				// 2.4 根据主题id，获取帖子的回复数
				int replyCount = iThreadReplyInfoDAO.countReplyByTid(tid);
				threadInfo.setReplyCount(replyCount);
				
				// 2.5 查询帖子的主题分类
				if (!StringUtils.isEmpty(threadInfo.getSubjectId())) {
					String subjectNameHtml = iSubjectClassDAO.selectNameHtmlBySubjectId(threadInfo.getSubjectId());
					threadInfo.setSubjectNameHtml(subjectNameHtml);
				}
			}
		}
		return list;
	}

	/**
	 * 根据主题id，获取帖子主题信息（正常状态的）
	 * @param tid 主题id
	 * @return
	 */
	public ThreadInfo selectByTid(String tid) {
		return iThreadInfoDAO.selectByTid(tid);
	}
	
	/**
	 * 根据主题id，获取帖子主题信息（正常状态的）
	 * @param tid 主题id
	 * @return
	 */
	public ThreadInfo updateThreadViewByTid(String tid) {
		// 1.0 根据主题id，获取帖子主题信息（正常状态的）
		ThreadInfo threadInfo = iThreadInfoDAO.selectByTid(tid);
		
		// 2.0 浏览量自增
		if (threadInfo!=null) {
			// 浏览总量自增
			int nViewCount = Integer.parseInt(threadInfo.getViewCount());
			nViewCount++;
			
			// 日浏览量自增
			int nViewCountDay = Integer.parseInt(threadInfo.getViewCountDay());
			nViewCountDay++;
			
			// 周浏览量自增
			int nViewCountWeek = Integer.parseInt(threadInfo.getViewCountWeek());
			nViewCountWeek++;
			
			// 月浏览量自增
			int nViewCountMonth = Integer.parseInt(threadInfo.getViewCountMonth());
			nViewCountMonth++;
			
			// 更新浏览次数
			ThreadInfo threadInfo2 = new ThreadInfo();
			threadInfo2.setTid(threadInfo.getTid());
			threadInfo2.setViewCount(String.valueOf(nViewCount));
			threadInfo2.setViewCountDay(String.valueOf(nViewCountDay));
			threadInfo2.setViewCountWeek(String.valueOf(nViewCountWeek));
			threadInfo2.setViewCountMonth(String.valueOf(nViewCountMonth));
			
			iThreadInfoDAO.update(threadInfo2);
			
			// 3.0 统计该帖子的回复数量
			int replyCount = iThreadReplyInfoDAO.countReplyByTid(tid);
			threadInfo.setReplyCount(replyCount);
		}
		
		return threadInfo;
	}

	/**
	 * 管理组操作主题
	 * @param tid 主题id
	 * @param optSelect 操作选项
	 * @param userInfo 
	 * @throws QingException 
	 */
	public void updateThreadStatus(String tid, String optSelect, UserInfo userInfo) throws QingException {
		// 1.0 查询主题信息
		ThreadInfo threadInfo = iThreadInfoDAO.selectByTidForAdmin(tid);
		if (threadInfo==null) {
			throw new QingException(ErrorMsg.ERROR_600005);
		}
		
		// 2.0 设置操作人和操作时间
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formatter.format(currentTime);
		
		threadInfo.setLastEditUserId(userInfo.getId());
		threadInfo.setLastEditTime(now);
		
		// 3.0 判断操作选项，并进行操作
		if ("digest1".equals(optSelect)) {
			// 设为精华帖
			// 已经是精华帖就不操作了，防止2次加分
			if (!"1".equals(threadInfo.getIsDigest())) {
				threadInfo.setIsDigest("1");
				iThreadInfoDAO.update(threadInfo);
				
				// 积分变更
				userCountService.update(threadInfo.getCreateUserId(), "digest");
			}
		} else if ("digest0".equals(optSelect)) {
			// 取消精华帖
			// 已经是精华帖的情况下才操作
			if ("1".equals(threadInfo.getIsDigest())) {
				threadInfo.setIsDigest("0");
				iThreadInfoDAO.update(threadInfo);
				
				// 积分变更
				userCountService.update(threadInfo.getCreateUserId(), "undigest");
			}
		} else if ("top1".equals(optSelect)) {
			// 置顶主题
			threadInfo.setIsTop("1");
			iThreadInfoDAO.update(threadInfo);
		} else if ("top0".equals(optSelect)) {
			// 取消置顶
			threadInfo.setIsTop("0");
			iThreadInfoDAO.update(threadInfo);
		} else if ("delete".equals(optSelect)) {
			// 删除主题（回收站）
			threadInfo.setStatus("2");
			iThreadInfoDAO.update(threadInfo);
		}
	}

	/**
	 * 批量更新主题状态
	 * @param param
	 */
	public void batchUpdateStatus(Map<String, Object> param) {
		iThreadInfoDAO.batchUpdate(param);
	}

	/**
	 * 批量彻底删除主题
	 * @param tidArr 主题id数组
	 */
	public void batchDelete(String[] tidArr) {
		// 1.0 积分变更
		for (int i=0; i<tidArr.length; i++) {
			ThreadInfo threadInfo = iThreadInfoDAO.selectByTidForAdmin(tidArr[i]);
			userCountService.update(threadInfo.getCreateUserId(), "delpost");
		}
		
		// 2.0 删除帖子（这里不考虑积分变更的问题，仅仅是为了清除冗余数据）
		iThreadReplyInfoDAO.deleteByTidArr(tidArr);
		
		// 3.0 删除主题
		iThreadInfoDAO.batchDelete(tidArr);
	}

	/**
	 * 后台批量操作主题
	 * @param param
	 * @param userInfo 
	 */
	public void batchOpt(Map<String, Object> param, UserInfo userInfo) {
		iThreadInfoDAO.batchOpt(param);
	}

	/**
	 * 后台批量操作主题
	 * @param tidArr
	 * @param optSelect
	 * @param userInfo
	 * @throws QingException
	 */
	public void batchOpt(String[] tidArr, String optSelect, UserInfo userInfo) throws QingException {
		for (int i=0; i<tidArr.length; i++) {
			updateThreadStatus(tidArr[i], optSelect, userInfo);
		}
	}

	/**
	 * 批量移动到板块
	 * @param tidArr 主题id数组
	 * @param fid 板块id
	 * @param subjectId 主题分类id
	 */
	public void batchMoveToBoard(String[] tidArr, String fid, String subjectId) {
		for (int i=0; i<tidArr.length; i++) {
			ThreadInfo threadInfo = new ThreadInfo();
			threadInfo.setTid(tidArr[i]);
			threadInfo.setFid(fid);
			threadInfo.setSubjectId(subjectId);
			
			iThreadInfoDAO.update(threadInfo);
		}
	}

	/**
	 * 搜索
	 * @param keyword
	 * @return
	 */
	public List<ThreadInfo> listBySearch(String keyword) {
		// 1.0 查询精华主题
		List<ThreadInfo> list = iThreadInfoDAO.listBySearch(keyword);
		
		// 2.0 查询主题的附加信息
		if (list!=null && list.isEmpty()==false) {
			// 2.1 遍历这些主题，并查询关联的帖子回复表中的记录
			for (ThreadInfo threadInfo : list) {
				String tid = threadInfo.getTid();
				// 2.2 根据主题id，获取帖子的回复数
				int replyCount = iThreadReplyInfoDAO.countReplyByTid(tid);
				threadInfo.setReplyCount(replyCount);
			}
		}
		return list;
	}

	/**
	 * 查询用户的主题
	 * @param userId 用户id
	 * @return
	 */
	public List<ThreadInfo> listByUserId(String userId) {
		// 1.0 查询用户的主题
		List<ThreadInfo> list = iThreadInfoDAO.listByUserId(userId);
		
		// 2.0 查询主题的附加信息
		if (list!=null && list.isEmpty()==false) {
			// 2.1 遍历这些主题，并查询关联的帖子回复表中的记录
			for (ThreadInfo threadInfo : list) {
				String tid = threadInfo.getTid();
				// 2.2 根据主题id，查询帖子回复表中的最新一条记录
				ThreadReplyInfo threadReplyInfo = iThreadReplyInfoDAO.selectLastByTid(tid);
				threadInfo.setThreadReplyInfo(threadReplyInfo);
				
				// 2.3 根据主题id，获取帖子的回复数
				int replyCount = iThreadReplyInfoDAO.countReplyByTid(tid);
				threadInfo.setReplyCount(replyCount);
			}
		}
		return list;
	}

	/**
	 * 查询用户回帖记录
	 * @param userId 用户id
	 * @return
	 */
	public List<ThreadInfo> listReplyByUserId(String replyUserId) {
		// 1.0 查询用户的主题
		List<ThreadInfo> list = iThreadInfoDAO.listByReplyUserId(replyUserId);
		
		// 2.0 查询主题的附加信息
		if (list!=null && list.isEmpty()==false) {
			// 2.1 遍历这些主题，并查询关联的帖子回复表中的记录
			for (ThreadInfo threadInfo : list) {
				// 2.2 根据主题id，回帖人id,查询帖子回复表中的所有记录
				List<ThreadReplyInfo> threadReplyList = iThreadReplyInfoDAO.listByTidAndReplyUserId(replyUserId);
				threadInfo.setThreadReplyList(threadReplyList);
				
				// 2.3 根据主题id，获取帖子的回复数
				int replyCount = iThreadReplyInfoDAO.countReplyByTid(threadInfo.getTid());
				threadInfo.setReplyCount(replyCount);
			}
		}
		return list;
	}

}
