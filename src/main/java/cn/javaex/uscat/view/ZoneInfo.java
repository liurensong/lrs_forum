package cn.javaex.uscat.view;

import java.util.List;

/**
 * 分区表
 * 
 * @author 陈霓清
 */
public class ZoneInfo {
	private String gid;			// 分区主键
	private String sort;		// 排序
	private String name;		// 分区名称
	private String color;		// 分区颜色
	private String row;			// 论坛首页下级子版块横排数量
	private String isShow;		// 是否显示分区
	private String title;		// title
	private String keywords;	// keywords
	private String description;	// description
	
	private List<BoardInfo> boardList;	// 分区下的板块列表
	private int zoneModeratorCount;		// 分区的超级版主数量
	private List<ZoneModerator> zoneModeratorList;	// 分区的超级版主信息
	
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
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
	public List<BoardInfo> getBoardList() {
		return boardList;
	}
	public void setBoardList(List<BoardInfo> boardList) {
		this.boardList = boardList;
	}
	public int getZoneModeratorCount() {
		return zoneModeratorCount;
	}
	public void setZoneModeratorCount(int zoneModeratorCount) {
		this.zoneModeratorCount = zoneModeratorCount;
	}
	public List<ZoneModerator> getZoneModeratorList() {
		return zoneModeratorList;
	}
	public void setZoneModeratorList(List<ZoneModerator> zoneModeratorList) {
		this.zoneModeratorList = zoneModeratorList;
	}
	
}
