package cn.javaex.uscat.dao.zone_moderator;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.ZoneModerator;

public interface IZoneModeratorDAO {

	/**
	 * 查询分区下的超级版主
	 * @param gid
	 * @return
	 */
	List<ZoneModerator> listByGid(String gid);
	
	/**
	 * 删除当前分区下的所有数据
	 * @param gid 分区id
	 * @return
	 */
	int deleteByGid(String gid);
	
	/**
	 * 根据分区id、用户id，查询是否已有重复数据
	 * @param gid 分区id
	 * @param userId 用户id
	 * @return
	 */
	int countByGidAndUserId(@Param("gid") String gid, @Param("userId") String userId);
	
	/**
	 * 插入
	 * @param zoneModerator
	 * @return
	 */
	int insert(ZoneModerator zoneModerator);

	/**
	 * 删除超级版主
	 * @param idArr 主键数组
	 * @return
	 */
	int delete(@Param("idArr") String[] idArr);

	/**
	 * 根据主键id，查询超级版主记录
	 * @param id
	 * @return
	 */
	ZoneModerator selectById(String id);

}