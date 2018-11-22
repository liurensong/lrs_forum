package cn.javaex.uscat.dao.thread_reply_info;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.ThreadReplyInfo;

public interface IThreadReplyInfoDAO {
	
	/**
	 * 插入新的帖子回复
	 * @param threadReplyInfo
	 * @return
	 */
	int insert(ThreadReplyInfo threadReplyInfo);

	/**
	 * 更新回帖内容
	 * @param threadReplyInfo
	 * @return
	 */
	int update(ThreadReplyInfo threadReplyInfo);
	
	/**
	 * 根据主题id，查询帖子回复表中的最新一条记录
	 * @param tid 主题id
	 * @return
	 */
	ThreadReplyInfo selectLastByTid(String tid);

	/**
	 * 根据主题id，获取帖子的回复数
	 * @param tid 主题id
	 * @return
	 */
	int countReplyByTid(String tid);

	/**
	 * 根据主题id，查询帖子回复内容
	 * @param tid 主题id
	 * @return
	 */
	List<ThreadReplyInfo> listByTid(String tid);

	/**
	 * 查询当前帖子目前最新的楼层
	 * @param tid
	 * @return
	 */
	int selectLastFloorByTid(String tid);

	/**
	 * 根据帖子回复表的主键，查询当前楼层的回复内容
	 * @param id 帖子回复表的id
	 * @return
	 */
	ThreadReplyInfo selectById(String id);

	/**
	 * 根据用户id，查询用户的回帖数
	 * @param replyUserId 回帖用户id
	 * @return
	 */
	int countByReplyUserId(String replyUserId);

	/***
	 * 根据主题id，查询帖子回复表中的第一条记录（即主题摘要）
	 * @param tid
	 * @return
	 */
	String selectPostContentByTid(String tid);

	/**
	 * 查询用户上次发帖的时间
	 * @param replyUserId 回帖用户id
	 * @return
	 */
	String selectLastReplyTimeByReplyUserId(String replyUserId);

	/**
	 * 根据举报记录的id数组，查询帖子回复表的list集合
	 * @param reportIdArr 举报记录的id数组
	 * @return
	 */
	List<ThreadReplyInfo> listByReportIdArr(@Param("reportIdArr") String[] reportIdArr);

	/**
	 * 查询回收站的回帖
	 * @return
	 */
	List<Map<String, Object>> listRecycle();

	/**
	 * 删除用户的回帖记录
	 * @param userIdArr 用户id数组
	 * @return
	 */
	int deleteByUserIdArr(@Param("userIdArr") String[] userIdArr);

	/**
	 * 根据主题主键数组，删除回帖记录
	 * @param tidArr 主题主键数组
	 * @return
	 */
	int deleteByTidArr(@Param("tidArr") String[] tidArr);

	/**
	 * 根据回帖id，查询该帖所在版块
	 * @param id 回帖id
	 * @return
	 */
	String selectFidById(String id);

	/**
	 * 根据主键，删除回帖记录
	 * @param id 主键
	 * @return
	 */
	int deleteById(String id);

	/**
	 * 查询回收站的帖子（高效查询）
	 * @return
	 */
	List<ThreadReplyInfo> listRecycle2();

	/**
	 * 删除所有回收站的帖子
	 * @return
	 */
	int deleteStatus2();

	/**
	 * 根据主题id，回帖人id,查询帖子回复表中的所有记录
	 * @param tid
	 * @return
	 */
	List<ThreadReplyInfo> listByTidAndReplyUserId(String replyUserId);

}