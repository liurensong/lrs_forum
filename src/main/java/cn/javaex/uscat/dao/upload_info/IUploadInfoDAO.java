package cn.javaex.uscat.dao.upload_info;

import cn.javaex.uscat.view.UploadInfo;

public interface IUploadInfoDAO {

	/**
	 * 根据类型，查询上传设置信息
	 * @param type 类型
	 * @return
	 */
	UploadInfo selectByType(String type);
	
	/**
	 * 更新上传设置
	 * @param uploadInfo
	 * @return
	 */
	int update(UploadInfo uploadInfo);
}