package cn.javaex.uscat.view;

import java.io.Serializable;

/**
 * 积分策略表
 * 
 * @author 陈霓清
 */
@SuppressWarnings("serial")
public class PointRule implements Serializable {
	private String id;				// 主键
	private String name;			// 策略名称
	private String action;			// 策略行为
	private String cycle;			// 奖励周期
	private String rewardNum;		// 奖励次数
	private String extcredits1;		// 积分
	private String extcredits2;
	private String extcredits3;
	private String extcredits4;
	private String extcredits5;
	private String extcredits6;
	
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
	public String getRewardNum() {
		return rewardNum;
	}
	public void setRewardNum(String rewardNum) {
		this.rewardNum = rewardNum;
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
	
}
