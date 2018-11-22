package cn.javaex.uscat.view;

/**
 * 友情链接表
 * 
 * @author 陈霓清
 */
public class FriendLink {
	private String id;		// 主键
	private String sort;	// 排序
	private String name;	// 站点名称
	private String url;		// 站点 URL
	private String logo;	// logo 地址(可选)
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}
