package cn.javaex.uscat.view;

import java.io.Serializable;

/**
 * 分区超级版主表
 * 
 * @author 陈霓清
 */
@SuppressWarnings("serial")
public class ZoneModerator implements Serializable {
	private String id;			// 主键
	private String gid;			// 分区id
	private String userId;		// 用户id
	
	private String loginName;	// 用户名
	private String avatar;		// 用户头像
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
}
