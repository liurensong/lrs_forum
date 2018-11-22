package cn.javaex.uscat.view;

import java.io.Serializable;

/**
 * 自定义页面表
 * 
 * @author 陈霓清
 */
@SuppressWarnings("serial")
public class ChannelInfo implements Serializable {
	private String id;				// 主键
	private String sort;			// 排序
	private String name;			// 名称
	private String template;		// 模板
	private String title;			// SEO
	private String keywords;
	private String description;
	
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
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
