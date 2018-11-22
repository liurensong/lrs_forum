package cn.javaex.uscat.dao.download_count;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.DownloadCount;

public interface IDownloadCountDAO {

	/**
	 * 查询列表
	 * @return
	 */
	List<DownloadCount> list();

	/**
	 * 新增
	 * @param downloadCount
	 * @return
	 */
	int insert(DownloadCount downloadCount);

	/**
	 * 更新
	 * @param downloadCount
	 * @return
	 */
	int update(DownloadCount downloadCount);

	/**
	 * 删除
	 * @param idArr
	 * @return
	 */
	int delete(@Param("idArr") String[] idArr);

	/**
	 * 根据id查询计数器信息
	 * @param id
	 * @return
	 */
	DownloadCount selectById(String id);

}