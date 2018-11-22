package cn.javaex.uscat.view;

import java.io.Serializable;

/**
 * 防灌水表
 * 
 * @author 陈霓清
 */
@SuppressWarnings("serial")
public class SecurityInfo implements Serializable {
	private String id;					// 主键
	private String reportTimeInterval;	// 两次发表时间间隔(秒)
	private String isActivateEmail;		// 是否强制用户验证激活邮箱（1：是；0否）
	private String isUploadAvatar;		// 是否强制用户上传头像（1：是；0否）
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportTimeInterval() {
		return reportTimeInterval;
	}
	public void setReportTimeInterval(String reportTimeInterval) {
		this.reportTimeInterval = reportTimeInterval;
	}
	public String getIsActivateEmail() {
		return isActivateEmail;
	}
	public void setIsActivateEmail(String isActivateEmail) {
		this.isActivateEmail = isActivateEmail;
	}
	public String getIsUploadAvatar() {
		return isUploadAvatar;
	}
	public void setIsUploadAvatar(String isUploadAvatar) {
		this.isUploadAvatar = isUploadAvatar;
	}
	
}
