package cn.javaex.uscat.dao.point_info;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.PointInfo;

public interface IPointInfoDAO {

	/**
	 * 查询积分表信息
	 * @param isUse
	 * @return
	 */
	List<PointInfo> list(@Param("isUse") String isUse);

	/**
	 * 更新积分表
	 * @param pointInfo
	 * @return
	 */
	int update(PointInfo pointInfo);
	
}