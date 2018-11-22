package cn.javaex.uscat.dao.board_moderator;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.BoardModerator;

public interface IBoardModeratorDAO {

	/**
	 * 查询板块下的版主
	 * @param fid
	 * @return
	 */
	List<BoardModerator> listByFid(String fid);
	
	/**
	 * 删除当前板块下的所有数据
	 * @param fid 板块id
	 * @return
	 */
	int deleteByFid(String fid);
	
	/**
	 * 根据板块id、用户id，查询是否已有重复数据
	 * @param fid 板块id
	 * @param userId 用户id
	 * @return
	 */
	int countByFidAndUserId(@Param("fid") String fid, @Param("userId") String userId);
	
	/**
	 * 插入
	 * @param boardModerator
	 * @return
	 */
	int insert(BoardModerator boardModerator);

	/**
	 * 删除版主
	 * @param idArr 主键数组
	 * @return
	 */
	int delete(@Param("idArr") String[] idArr);
	
	/**
	 * 根据主键id，查询版主记录
	 * @param id
	 * @return
	 */
	BoardModerator selectById(String id);
}