package cn.javaex.uscat.dao.board_info;

import java.util.List;

import cn.javaex.uscat.view.BoardInfo;

public interface IBoardInfoDAO {

	/**
	 * 根据分区id，查询分区下的板块列表
	 * @param gid
	 * @return
	 */
	List<BoardInfo> listByGid(String gid);
	
	/**
	 * 根据分区id，查询分区下可见的板块列表
	 * @param gid
	 * @return
	 */
	List<BoardInfo> listShowByGid(String gid);
	
	/**
	 * 插入新的板块
	 * @param boardInfo
	 */
	int insert(BoardInfo boardInfo);

	/**
	 * 更新板块
	 * @param boardInfo
	 */
	int update(BoardInfo boardInfo);

	/**
	 * 根据板块Id查询板块信息
	 * @param fid 板块id
	 * @return
	 */
	BoardInfo selectById(String fid);

	/**
	 * 根据板块Id删除板块
	 * @param fid 板块id
	 */
	int deleteById(String fid);

	/**
	 * 根据分区id删除板块
	 * @param gid 分区id
	 */
	int deleteByGid(String gid);

}