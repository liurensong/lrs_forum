package cn.javaex.uscat.view;

import java.io.Serializable;

/**
 * 幻灯片数据表
 * 
 * @author 陈霓清
 */
@SuppressWarnings("serial")
public class SlideInfo implements Serializable {
	private String id;			// 主键
	private String apiId;		// 接口表的id
	private String sort;		// 排序
	private String title;		// 标题
	private String summary;		// 看点
	private String imageSmall;	// 小图
	private String imageBig;	// 大图
	private String url;			// 链接地址
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getImageSmall() {
		return imageSmall;
	}
	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}
	public String getImageBig() {
		return imageBig;
	}
	public void setImageBig(String imageBig) {
		this.imageBig = imageBig;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
