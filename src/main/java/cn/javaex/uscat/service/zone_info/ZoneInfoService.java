package cn.javaex.uscat.service.zone_info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.dao.board_group_perm.IBoardGroupPermDAO;
import cn.javaex.uscat.dao.board_info.IBoardInfoDAO;
import cn.javaex.uscat.dao.board_moderator.IBoardModeratorDAO;
import cn.javaex.uscat.dao.thread_info.IThreadInfoDAO;
import cn.javaex.uscat.dao.zone_info.IZoneInfoDAO;
import cn.javaex.uscat.dao.zone_moderator.IZoneModeratorDAO;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.view.BoardGroupPerm;
import cn.javaex.uscat.view.BoardInfo;
import cn.javaex.uscat.view.ZoneInfo;
import cn.javaex.uscat.view.ZoneModerator;

@Service("ZoneInfoService")
public class ZoneInfoService {
	@Autowired
	private IZoneInfoDAO iZoneInfoDAO;
	@Autowired
	private IBoardInfoDAO iBoardInfoDAO;
	@Autowired
	private IThreadInfoDAO iThreadInfoDAO;
	@Autowired
	private IZoneModeratorDAO iZoneModeratorDAO;
	@Autowired
	private IBoardModeratorDAO iBoardModeratorDAO;
	@Autowired
	private IBoardGroupPermDAO iBoardGroupPermDAO;
	
	/**
	 * 保存分区和板块
	 * @param zoneInfoList
	 * @param boardInfoList 
	 */
	public void saveZoneAndBoard(List<ZoneInfo> zoneInfoList, List<BoardInfo> boardInfoList) {
		for (ZoneInfo zoneInfo : zoneInfoList) {
			if (StringUtils.isEmpty(zoneInfo.getGid())) {
				// 插入
				zoneInfo.setIsShow("1");
				iZoneInfoDAO.insert(zoneInfo);
			} else {
				// 更新
				iZoneInfoDAO.update(zoneInfo);
			}
		}
		
		for (BoardInfo boardInfo : boardInfoList) {
			if (StringUtils.isEmpty(boardInfo.getFid())) {
				// 插入
				boardInfo.setIsShow("1");
				// 新增默认设置默认权限
				boardInfo.setIsDefaultPerm("1");
				iBoardInfoDAO.insert(boardInfo);
			} else {
				// 更新
				iBoardInfoDAO.update(boardInfo);
			}
		}
	}

	/**
	 * 查询分区列表
	 * @return
	 */
	public List<ZoneInfo> list() {
		return iZoneInfoDAO.list();
	}

	/**
	 * 查询所有分区和板块列表
	 * @return
	 */
	public List<ZoneInfo> listAll() {
		// 1.0 查询所有分区list
		List<ZoneInfo> list = iZoneInfoDAO.list();
		if (list!=null && list.isEmpty()==false) {
			// 2.0 遍历每一个分区
			for (ZoneInfo zoneInfo : list) {
				// 3.0 查询每个分区的超级版主数量
				int zoneModeratorCount = iZoneModeratorDAO.countByGidAndUserId(zoneInfo.getGid(), null);
				zoneInfo.setZoneModeratorCount(zoneModeratorCount);
				
				// 4.0 查询该分区下的板块列表
				List<BoardInfo> boardList = iBoardInfoDAO.listByGid(zoneInfo.getGid());
				if (boardList!=null && boardList.isEmpty()==false) {
					// 5.0 遍历这些板块
					for (BoardInfo boardInfo : boardList) {
						// 6.0 查询每个板块下的版主数量
						int boardModeratorCount = iBoardModeratorDAO.countByFidAndUserId(boardInfo.getFid(), null);
						boardInfo.setBoardModeratorCount(boardModeratorCount);
					}
					zoneInfo.setBoardList(boardList);
				}
			}
		}
		return list;
	}
	
	/**
	 * 查询所有可见分区及其下可见板块信息
	 * @return
	 */
	public List<ZoneInfo> listShow() {
		List<ZoneInfo> list = iZoneInfoDAO.listShow();
		if (list!=null && list.isEmpty()==false) {
			// 遍历每一个分区
			for (ZoneInfo zoneInfo : list) {
				// 查询该分区下的板块列表
				List<BoardInfo> boardList = iBoardInfoDAO.listShowByGid(zoneInfo.getGid());
				if (boardList!=null && boardList.isEmpty()==false) {
					// 遍历这些可见的板块，查询主题总数和回帖总数
					for (BoardInfo boardInfo : boardList) {
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
					
					zoneInfo.setBoardList(boardList);
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据主键查询分区详细信息
	 * @param gid 分区id
	 * @return
	 */
	public ZoneInfo selectById(String gid) {
		return iZoneInfoDAO.selectById(gid);
	}
	
	/**
	 * 根据主键查询分区详细信息（包括超级版主）
	 * @param gid 分区id
	 * @return
	 */
	public ZoneInfo selectInfoById(String gid) {
		ZoneInfo zoneInfo = iZoneInfoDAO.selectById(gid);
		
		// 查询分区的超级版主信息
		List<ZoneModerator> zoneModeratorList = iZoneModeratorDAO.listByGid(zoneInfo.getGid());
		zoneInfo.setZoneModeratorList(zoneModeratorList);
		
		return zoneInfo;
	}
	
	/**
	 * 保存分区设置
	 * @param zoneInfo
	 */
	public void save(ZoneInfo zoneInfo) {
		iZoneInfoDAO.update(zoneInfo);
	}

	/**
	 * 根据分区id删除分区和其下的板块
	 * @param gid 分区id
	 * @throws QingException
	 */
	public void delete(String gid) throws QingException {
		// 1.0 校验该分区下是否有帖子存在
		List<BoardInfo> list = iBoardInfoDAO.listByGid(gid);
		if (list!=null && list.isEmpty()==false) {
			for (BoardInfo boardInfo : list) {
				int count = iThreadInfoDAO.countByFid(boardInfo.getFid());
				if (count>0) {
					throw new QingException(ErrorMsg.ERROR_300002);
				}
			}
		}
		
		// 2.0 删除分区下的板块
		iBoardInfoDAO.deleteByGid(gid);
		
		// 3.0 删除分区
		iZoneInfoDAO.deleteById(gid);
	}

	/**
	 * 查询可见分区及其下板块的权限
	 * @param groupId 用户组id
	 * @return
	 */
	public List<ZoneInfo> listShowPermByGroupId(String groupId) {
		List<ZoneInfo> list = iZoneInfoDAO.listShow();
		if (list!=null && list.isEmpty()==false) {
			// 遍历每一个分区
			for (ZoneInfo zoneInfo : list) {
				// 查询该分区下的板块列表
				List<BoardInfo> boardList = iBoardInfoDAO.listShowByGid(zoneInfo.getGid());
				if (boardList!=null && boardList.isEmpty()==false) {
					// 遍历这些可见的板块，查询权限
					for (BoardInfo boardInfo : boardList) {
						BoardGroupPerm boardGroupPerm = iBoardGroupPermDAO.selectByFidAndGroupId(boardInfo.getFid(), groupId);
						boardInfo.setBoardGroupPerm(boardGroupPerm);
					}
					
					zoneInfo.setBoardList(boardList);
				}
			}
		}
		return list;
	}

}
