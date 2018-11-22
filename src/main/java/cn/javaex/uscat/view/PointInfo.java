package cn.javaex.uscat.view;

import java.io.Serializable;

/**
 * 积分表
 * 
 * @author 陈霓清
 */
@SuppressWarnings("serial")
public class PointInfo implements Serializable {
	private String id;				// 主键
	private String varName;			// 变量名（系统设定，不可改变）
	private String name;			// 中文名
	private String conversionRatio;	// 兑换比率
	private String isUse;			// 是否可用
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConversionRatio() {
		return conversionRatio;
	}
	public void setConversionRatio(String conversionRatio) {
		this.conversionRatio = conversionRatio;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	
}
