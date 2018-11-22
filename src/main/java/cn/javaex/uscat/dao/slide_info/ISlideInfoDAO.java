package cn.javaex.uscat.dao.slide_info;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.SlideInfo;

public interface ISlideInfoDAO {

	/**
	 * 根据接口主键，查询所有幻灯片数据
	 * @param apiId 接口表的主键
	 * @return
	 */
	List<SlideInfo> listByApiId(String apiId);

	/**
	 * 插入新的幻灯片数据
	 */
	int insert(SlideInfo slideProfileInfo);
	
	/**
	 * 更新幻灯片数据
	 */
	int update(SlideInfo slideProfileInfo);

	
	/**
	 * 批量删除幻灯片数据
	 * @param idArr 主键数组
	 * @return
	 */
	int delete(@Param("idArr") String[] idArr);

}