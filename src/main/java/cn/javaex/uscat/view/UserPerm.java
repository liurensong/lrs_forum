package cn.javaex.uscat.view;

/**
 * 用户对板块下帖子的操作权限（不是表）
 * 
 * @author 陈霓清
 */
public class UserPerm {
	private String isAdmin;	// 是否有管理权限（管理员有全部权限，超级版主有对应分区下的管理权限，版主有对应板块下的管理权限）
	
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
