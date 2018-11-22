package cn.javaex.uscat.view;

/**
 * 主题分类表
 * 
 * @author 陈霓清
 */
public class SubjectClass {
	private String subjectId;			// 主键
	private String fid;			// 板块id
	private String sort;		// 排序
	private String nameHtml;	// 分类名称（带html代码，用于页面显示时有样式）
	private String nameText;	// 分类名称（纯文字，用于发帖时选择分类）
	private String isShow;		// 是否启用（1：启用；0：不启用）
	
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getNameHtml() {
		return nameHtml;
	}
	public void setNameHtml(String nameHtml) {
		this.nameHtml = nameHtml;
	}
	public String getNameText() {
		return nameText;
	}
	public void setNameText(String nameText) {
		this.nameText = nameText;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
}
