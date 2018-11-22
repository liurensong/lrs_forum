package cn.javaex.uscat.view;

/**
 * 下载计数器表
 * 
 * @author 陈霓清
 */
public class DownloadCount {
	private String id;			// 主键
	private String sort;		// 排序用
	private String name;		// 名称
	private String icon;		// 图标
	private String remark;		// 简介
	private String updateTime;	// 更新时间
	private String url;			// 链接
	private int count;			// 下载次数
	private String isLogin;		// 是否需要登录后才能下载
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}
	
}
