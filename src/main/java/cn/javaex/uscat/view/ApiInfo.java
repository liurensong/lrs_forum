package cn.javaex.uscat.view;

import java.io.Serializable;

/**
 * api接口表
 * 
 * @author 陈霓清
 */
@SuppressWarnings("serial")
public class ApiInfo implements Serializable {
	private String id;			// 主键
	private String name;		// 接口中文名称
	private String sort;		// 接口排序
	private String type;		// 接口类型
	private String fid;			// 数据来源板块（格式为1,2,4...），来源全部板块的话，设为0
	private String orderField;	// 排序字段
	private String cycle;		// 排序字段为回复数量或浏览次数时，可以设置周期（1周内，1月内，不限）
	private String num;			// 返回多少条数据
	private String cacheTime;	// 缓存时间
	
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getCacheTime() {
		return cacheTime;
	}
	public void setCacheTime(String cacheTime) {
		this.cacheTime = cacheTime;
	}
	
}
