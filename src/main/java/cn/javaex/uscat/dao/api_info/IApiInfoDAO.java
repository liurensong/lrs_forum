package cn.javaex.uscat.dao.api_info;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.ApiInfo;

public interface IApiInfoDAO {

	/**
	 * 查询指定类型的接口列表
	 * @param type 接口类型
	 * @return
	 */
	List<ApiInfo> listByType(String type);

	/**
	 * 插入新的接口
	 */
	int insert(ApiInfo apiInfo);
	
	/**
	 * 更新接口
	 */
	int update(ApiInfo apiInfo);

	
	/**
	 * 批量删除接口
	 * @param idArr 主键数组
	 * @return
	 */
	int delete(@Param("idArr") String[] idArr);

	/**
	 * 根据接口id，查询该接口的信息
	 * @param id
	 * @return
	 */
	ApiInfo selectById(String id);

}