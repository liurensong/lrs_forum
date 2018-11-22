package cn.javaex.uscat.view;

/**
 * 用户组表
 * 
 * @author 陈霓清
 */
public class GroupInfo {
	private String id;		// 主键
	private String name;	// 用户组名称
	private String type;	// 该用户组是否为系统内置（system代表内置的管理组，不可修改）
	private String point;	// 自动晋级用户组的最低积分（对管理组无效）
	
	private BoardGroupPerm boardGroupPerm;	// 用户组对板块的权限设置记录
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public BoardGroupPerm getBoardGroupPerm() {
		return boardGroupPerm;
	}
	public void setBoardGroupPerm(BoardGroupPerm boardGroupPerm) {
		this.boardGroupPerm = boardGroupPerm;
	}
	
}
