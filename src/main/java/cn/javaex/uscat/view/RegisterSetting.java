package cn.javaex.uscat.view;

import java.io.Serializable;

/**
 * 注册设置表
 * 
 * @author 陈霓清
 */
@SuppressWarnings("serial")
public class RegisterSetting implements Serializable {
	private String id;						// 主键
	private String isAllowRegister;			// 是否开放注册
	private String closeRegisterMessage;	// 关闭注册时的提示信息
	private String isCheckEmail;			// 注册时是否需要验证邮箱
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsAllowRegister() {
		return isAllowRegister;
	}
	public void setIsAllowRegister(String isAllowRegister) {
		this.isAllowRegister = isAllowRegister;
	}
	public String getCloseRegisterMessage() {
		return closeRegisterMessage;
	}
	public void setCloseRegisterMessage(String closeRegisterMessage) {
		this.closeRegisterMessage = closeRegisterMessage;
	}
	public String getIsCheckEmail() {
		return isCheckEmail;
	}
	public void setIsCheckEmail(String isCheckEmail) {
		this.isCheckEmail = isCheckEmail;
	}
	
}
