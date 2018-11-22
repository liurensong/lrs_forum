package cn.javaex.uscat.dao.thread_info;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.ThreadInfo;

public interface IThreadInfoDAO {

	/**
	 * 插入新的主题
	 * @param threadInfo
	 * @return
	 */
	int insert(ThreadInfo threadInfo);

	/**
	 * 查询某个板块下的主题列表（排序字段为主题创建时间或主题浏览次数）
	 * @param param
	 * @return
	 */
	List<ThreadInfo> listByForumdisplay(Map<String, Object> param);
	/**
	 * 查询某个板块下的主题列表（排序字段为帖子回复时间）
	 * @param param
	 * @return
	 */
	List<ThreadInfo> listByReplyTime(Map<String, Object> param);
	
	/**
	 * 查询某个板块下的帖子列表（排序字段为帖子回复数量）
	 * @param param
	 * @return
	 */
	List<ThreadInfo> listByReplyCount(Map<String, Object> param);
	
	/**
	 * 根据主题id，获取帖子主题信息（正常状态的）
	 * @param tid 主题id
	 * @return
	 */
	ThreadInfo selectByTid(String tid);

	/**
	 * 更新主题信息
	 * @param threadInfo
	 * @return
	 */
	int update(ThreadInfo threadInfo);

	/**
	 * 根据用户id，查询用户发布的帖子数（主题数）
	 * @param createUserId
	 * @return
	 */
	int countByCreateUserId(String createUserId);

	/**
	 * 根据板块id，查询主题总数
	 * @param fid 板块id
	 * @return
	 */
	int countByFid(String fid);
	
	/**
	 * 根据板块id，查询回帖总数
	 * @param fid 板块id
	 * @return
	 */
	int countThreadReplyByFid(String fid);

	/**
	 * 根据板块id，查询今日帖子数量（主题+回帖）
	 * @param fid 板块id
	 * @return
	 */
	int countThreadCountTodayByFid(String fid);

	/**
	 * 清空帖子浏览量
	 * @param threadInfo
	 * @return
	 */
	int clearViewCount(ThreadInfo threadInfo);

	/**
	 * 查询主题列表（后台管理专用）
	 * @param param
	 * @return
	 */
	List<ThreadInfo> list(Map<String, Object> param);

	/**
	 * 根据主题id，查询主题信息（操作主题专用）
	 * @param tid 主题id
	 * @return
	 */
	ThreadInfo selectByTidForAdmin(String tid);

	/**
	 * 删除用户的主题记录
	 * @param userIdArr 用户id数组
	 * @return
	 */
	int deleteByUserIdArr(@Param("userIdArr") String[] userIdArr);

	/**
	 * 批量更新主题状态
	 * @param param
	 */
	int batchUpdate(Map<String, Object> param);

	/**
	 * 批量彻底删除主题
	 * @param tidArr 主题id数组
	 * @return
	 */
	int batchDelete(@Param("tidArr") String[] tidArr);

	/**
	 * 后台批量操作主题
	 */
	int batchOpt(Map<String, Object> param);
	
	/**
	 * 查询置顶主题（按最后编辑时间倒序排列）
	 * @param fid 板块id
	 * @return
	 */
	List<ThreadInfo> listTopByForumdisplay(String fid);

	/**
	 * 查询精华主题（按最后编辑时间倒序排列）
	 * @param fid 板块id
	 * @return
	 */
	List<ThreadInfo> listDigestByForumdisplay(String fid);

	/**
	 * 搜索
	 * @param keyword
	 * @return
	 */
	List<ThreadInfo> listBySearch(@Param("keyword") String keyword);

	/**
	 * 查询用户的主题
	 * @param createUserId 用户id
	 * @return
	 */
	List<ThreadInfo> listByUserId(String createUserId);

	/**
	 * 查询用户回帖记录
	 * @param replyUserId 回帖用户id
	 * @return
	 */
	List<ThreadInfo> listByReplyUserId(@Param("replyUserId") String replyUserId);

	/**
	 * 查询主题列表（排序字段为主题创建时间或主题浏览次数，api接口专用）
	 * @param param
	 * @return
	 */
	List<ThreadInfo> apiListByOrderField(Map<String, Object> param);

	/**
	 * 查询主题列表（排序字段为帖子回复时间，api接口专用）
	 * @param param
	 * @return
	 */
	List<ThreadInfo> apiListByReplyTime(Map<String, Object> param);

	/**
	 * 查询主题列表（排序字段为帖子回复数量，api接口专用）
	 * @param param
	 * @return
	 */
	List<ThreadInfo> apiListByReplyCount(Map<String, Object> param);

}