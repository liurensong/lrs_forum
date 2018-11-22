package cn.javaex.uscat.service.board_info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.dao.board_info.IBoardInfoDAO;
import cn.javaex.uscat.dao.board_moderator.IBoardModeratorDAO;
import cn.javaex.uscat.dao.thread_info.IThreadInfoDAO;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.view.BoardInfo;
import cn.javaex.uscat.view.BoardModerator;

@Service("BoardInfoService")
public class BoardInfoService {
	@Autowired
	private IBoardInfoDAO iBoardInfoDAO;
	@Autowired
	private IThreadInfoDAO iThreadInfoDAO;
	@Autowired
	private IBoardModeratorDAO iBoardModeratorDAO;
	
	/**
	 * 根据主键查询板块信息（编辑用）
	 * @param fid 板块id
	 * @return
	 */
	public BoardInfo selectById(String fid) {
		return iBoardInfoDAO.selectById(fid);
	}
	
	/**
	 * 根据主键查询板块信息（包括今日发帖数、主题数、帖数）
	 * @param fid 板块id
	 * @return
	 */
	public BoardInfo selectInfoById(String fid) {
		BoardInfo boardInfo = iBoardInfoDAO.selectById(fid);
		
		// 1.0 根据板块id，查询今日帖子数量（主题+回帖=直接统计thread_reply_info表）
		int threadCountToday = iThreadInfoDAO.countThreadCountTodayByFid(fid);
		boardInfo.setThreadCountToday(threadCountToday);
		// 2.0 根据板块id，查询主题总数
		int threadCount = iThreadInfoDAO.countByFid(fid);
		boardInfo.setThreadCount(threadCount);
		// 3.0 根据板块id，查询回帖总数
		int threadReplyCount = iThreadInfoDAO.countThreadReplyByFid(fid);
		boardInfo.setThreadReplyCount(threadReplyCount);
		// 4.0 查询板块的版主信息
		List<BoardModerator> boardModeratorList = iBoardModeratorDAO.listByFid(fid);
		boardInfo.setBoardModeratorList(boardModeratorList);
		
		return boardInfo;
	}
	
	/**
	 * 保存板块设置
	 * @param boardInfo
	 */
	public void save(BoardInfo boardInfo) {
		iBoardInfoDAO.update(boardInfo);
	}

	/**
	 * 根据主键删除板块
	 * @param fid 板块id
	 * @throws QingException 
	 */
	public void deleteById(String fid) throws QingException {
		// 1.0 校验该板块下是否有帖子
		int count = iThreadInfoDAO.countByFid(fid);
		if (count>0) {
			throw new QingException(ErrorMsg.ERROR_300001);
		}
		// 2.0 删除板块
		iBoardInfoDAO.deleteById(fid);
	}

	/**
	 * 根据分区id，查询分区下可见的板块信息
	 * @param gid 分区id
	 * @return
	 */
	public List<BoardInfo> listShowByGid(String gid) {
		// 1.0 根据分区id，查询该分区下可见的板块列表
		List<BoardInfo> list = iBoardInfoDAO.listShowByGid(gid);
		if (list!=null && list.isEmpty()==false) {
			// 遍历这些可见的板块，查询主题总数和回帖总数
			for (BoardInfo boardInfo : list) {
				String fid = boardInfo.getFid();
				// 根据板块id，查询今日帖子数量（主题+回帖=直接统计thread_reply_info表）
				int threadCountToday = iThreadInfoDAO.countThreadCountTodayByFid(fid);
				boardInfo.setThreadCountToday(threadCountToday);
				// 根据板块id，查询主题总数
				int threadCount = iThreadInfoDAO.countByFid(fid);
				boardInfo.setThreadCount(threadCount);
				// 根据板块id，查询回帖总数
				int threadReplyCount = iThreadInfoDAO.countThreadReplyByFid(fid);
				boardInfo.setThreadReplyCount(threadReplyCount);
			}
		}
		return list;
	}

	/**
	 * 获取某个分区下的板块列表
	 * @param gid 分区id
	 * @return
	 */
	public List<BoardInfo> listByGid(String gid) {
		return iBoardInfoDAO.listByGid(gid);
	}

}
