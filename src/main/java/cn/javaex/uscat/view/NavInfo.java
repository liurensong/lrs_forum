package cn.javaex.uscat.view;

import java.io.Serializable;

/**
 * 导航表
 * 
 * @author 陈霓清
 */
@SuppressWarnings("serial")
public class NavInfo implements Serializable {
	private String id;		// 主键
	private String name;	// 导航名称
	private String link;	// 导航链接
	private String type;	// 导航类型（system：系统内置；user：用户自定义）
	private String sort;	// 排序用
	private String isIndex;	// 是否首页（1：是，0：不是）
	private String isUse;	// 是否可用（1：可用，0：不可用）
	private String channelId;			// 自定义页面id
	private String isDownloadCount;		// 当前页面是否包含下载计数器
	
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getIsIndex() {
		return isIndex;
	}
	public void setIsIndex(String isIndex) {
		this.isIndex = isIndex;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getIsDownloadCount() {
		return isDownloadCount;
	}
	public void setIsDownloadCount(String isDownloadCount) {
		this.isDownloadCount = isDownloadCount;
	}
	
}
