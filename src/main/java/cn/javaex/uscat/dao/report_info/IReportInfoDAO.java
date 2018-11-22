package cn.javaex.uscat.dao.report_info;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.ReportInfo;

public interface IReportInfoDAO {

	/**
	 * 插入新的举报记录
	 * @param reportInfo
	 * @return
	 */
	int insert(ReportInfo reportInfo);

	/**
	 * 查询用户举报记录
	 * @param param 
	 * @return
	 */
	List<ReportInfo> list(Map<String, Object> param);

	/**
	 * 批量删除举报记录
	 * @param idArr 举报记录id数组
	 * @return
	 */
	int batchDeleteReport(@Param("idArr") String[] idArr);

	/**
	 * 根据举报id数组，将举报记录更改为已处理状态
	 * @param idArr 举报记录id数组
	 * @return
	 */
	int updateStatus1(@Param("idArr") String[] idArr);
	
}