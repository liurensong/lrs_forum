package cn.javaex.uscat.view;

/**
 * 用户积分获取记录日志
 * 
 * @author 陈霓清
 */
public class UserCountLog {
	private String id;			// 主键
	private String userId;		// 用户id
	private String action;		// 行为
	private String cycle;		// 奖励周期（定时任务删除用，例如存的是每天，则每天0点删除该记录）
	private int rewardNum;		// 行为次数
	
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public int getRewardNum() {
		return rewardNum;
	}
	public void setRewardNum(int rewardNum) {
		this.rewardNum = rewardNum;
	}
	
}
