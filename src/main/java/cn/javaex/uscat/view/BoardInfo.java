package cn.javaex.uscat.view;

import java.util.List;

/**
 * 板块表
 * 
 * @author 陈霓清
 */
public class BoardInfo {
	private String fid;					// 板块主键
	private String gid;					// 分区主键
	private String sort;				// 排序
	private String name;				// 板块名称
	private String icon;				// 板块图标
	private String width;				// 板块图标宽度
	private String height;				// 板块图标高度
	private String banner;				// 顶部横幅
	private String isShow;				// 是否显示板块
	private String remark;				// 板块简介
	private String title;				// title
	private String keywords;			// keywords
	private String description;			// description
	private String isDefaultPerm;		// 是否启用默认权限（1：是；0：否）
	private String isSubjectClass;		// 是否启用主题分类（1：是；0：否）
	private String defaultOrderField;	// 主题默认排序字段（回复时间、发布时间、回复数量、浏览次数）
	
	private int boardModeratorCount;	// 板块的版主数量
	private List<BoardModerator> boardModeratorList;	// 板块的版主信息
	private int threadCountToday;		// 板块下的今日帖子数量（主题+回帖）
	private int threadCount;			// 板块下的主题数量
	private int threadReplyCount;		// 板块下的帖子数量
	private BoardGroupPerm boardGroupPerm;	// 板块权限明细
	
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
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
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getIsDefaultPerm() {
		return isDefaultPerm;
	}
	public void setIsDefaultPerm(String isDefaultPerm) {
		this.isDefaultPerm = isDefaultPerm;
	}
	public String getIsSubjectClass() {
		return isSubjectClass;
	}
	public void setIsSubjectClass(String isSubjectClass) {
		this.isSubjectClass = isSubjectClass;
	}
	public String getDefaultOrderField() {
		return defaultOrderField;
	}
	public void setDefaultOrderField(String defaultOrderField) {
		this.defaultOrderField = defaultOrderField;
	}
	public int getBoardModeratorCount() {
		return boardModeratorCount;
	}
	public void setBoardModeratorCount(int boardModeratorCount) {
		this.boardModeratorCount = boardModeratorCount;
	}
	public List<BoardModerator> getBoardModeratorList() {
		return boardModeratorList;
	}
	public void setBoardModeratorList(List<BoardModerator> boardModeratorList) {
		this.boardModeratorList = boardModeratorList;
	}
	public int getThreadCountToday() {
		return threadCountToday;
	}
	public void setThreadCountToday(int threadCountToday) {
		this.threadCountToday = threadCountToday;
	}
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public int getThreadReplyCount() {
		return threadReplyCount;
	}
	public void setThreadReplyCount(int threadReplyCount) {
		this.threadReplyCount = threadReplyCount;
	}
	public BoardGroupPerm getBoardGroupPerm() {
		return boardGroupPerm;
	}
	public void setBoardGroupPerm(BoardGroupPerm boardGroupPerm) {
		this.boardGroupPerm = boardGroupPerm;
	}
	
}
