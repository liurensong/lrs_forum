package cn.javaex.uscat.dao.zone_info;

import java.util.List;

import cn.javaex.uscat.view.ZoneInfo;

public interface IZoneInfoDAO {
	
	/**
	 * 查询所有分区列表
	 * @return
	 */
	List<ZoneInfo> list();
	
	/**
	 * 查询所有可见分区列表
	 * @return
	 */
	List<ZoneInfo> listShow();
	
	/**
	 * 插入新的分区
	 * @param zoneInfo
	 */
	int insert(ZoneInfo zoneInfo);

	/**
	 * 更新分区
	 * @param zoneInfo
	 */
	int update(ZoneInfo zoneInfo);

	/**
	 * 根据主键查询分区详细信息
	 * @param gid 分区id
	 * @return
	 */
	ZoneInfo selectById(String gid);

	/**
	 * 根据分区Id删除分区
	 * @param gid
	 */
	int deleteById(String gid);

}