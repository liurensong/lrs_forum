package cn.javaex.uscat.view;

/**
 * 用户个人信息简况表
 * 
 * @author 陈霓清
 */
public class UserProfileInfo {
	private String id;			// 主键
	private String userId;		// 用户id
	private String groupId;		// 用户组id
	private String avatar;		// 用户头像图片地址
	
	private String groupType;	// 用户组类型（系统用户组或会员用户组）
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
}
