package cn.javaex.uscat.view;

/**
 * 用户获取系统积分记录表
 * 
 * @author 陈霓清
 */
public class UserPointLog {
	private String id;				// 主键
	private String userId;			// 用户id
	private String name;			// 行为名称
	private String extcredits1;		// 积分
	private String extcredits2;
	private String extcredits3;
	private String extcredits4;
	private String extcredits5;
	private String extcredits6;
	private String time;			// 记录时间
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExtcredits1() {
		return extcredits1;
	}
	public void setExtcredits1(String extcredits1) {
		this.extcredits1 = extcredits1;
	}
	public String getExtcredits2() {
		return extcredits2;
	}
	public void setExtcredits2(String extcredits2) {
		this.extcredits2 = extcredits2;
	}
	public String getExtcredits3() {
		return extcredits3;
	}
	public void setExtcredits3(String extcredits3) {
		this.extcredits3 = extcredits3;
	}
	public String getExtcredits4() {
		return extcredits4;
	}
	public void setExtcredits4(String extcredits4) {
		this.extcredits4 = extcredits4;
	}
	public String getExtcredits5() {
		return extcredits5;
	}
	public void setExtcredits5(String extcredits5) {
		this.extcredits5 = extcredits5;
	}
	public String getExtcredits6() {
		return extcredits6;
	}
	public void setExtcredits6(String extcredits6) {
		this.extcredits6 = extcredits6;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
