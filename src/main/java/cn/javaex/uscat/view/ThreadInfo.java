package cn.javaex.uscat.view;

import java.util.List;

/**
 * 帖子主题表
 * 
 * @author 陈霓清
 */
public class ThreadInfo {
	private String tid;				// 主键
	private String fid;				// 板块id
	private String subjectId;		// 主题分类id
	private String title;			// 帖子标题
	private String createTime;		// 创建时间
	private String createUserId;	// 创建人id
	private String status;			// 主题状态（0：待审核；1：正常；2：屏蔽）
	private String viewCount;		// 总浏览次数
	private String viewCountDay;	// 日浏览次数
	private String viewCountWeek;	// 周浏览次数
	private String viewCountMonth;	// 月浏览次数
	private String isTop;			// 是否置顶（0：不置顶；1：置顶）
	private String isDigest;		// 是否精华（0：不是；1：是）
	private String lastEditUserId;	// 最后操作人id
	private String lastEditTime;	// 最后操作时间
	
	private String loginName;		// 主题发帖人用户名
	private String avatar;			// 主题发帖人头像
	private String userStatus;		// 主题发帖人状态
	private String boardName;		// 板块名称
	private String zoneName;		// 分区名称
	private String subjectNameHtml;	// 主题分类（带html代码）
	private ThreadReplyInfo threadReplyInfo;	// 帖子回复信息
	private int replyCount;			// 帖子回复数
	private String contentText;		// 主题摘要
	private List<ThreadReplyInfo> threadReplyList;	// 回帖列表
	
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getViewCount() {
		return viewCount;
	}
	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}
	public String getViewCountDay() {
		return viewCountDay;
	}
	public void setViewCountDay(String viewCountDay) {
		this.viewCountDay = viewCountDay;
	}
	public String getViewCountWeek() {
		return viewCountWeek;
	}
	public void setViewCountWeek(String viewCountWeek) {
		this.viewCountWeek = viewCountWeek;
	}
	public String getViewCountMonth() {
		return viewCountMonth;
	}
	public void setViewCountMonth(String viewCountMonth) {
		this.viewCountMonth = viewCountMonth;
	}
	public String getIsTop() {
		return isTop;
	}
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	public String getIsDigest() {
		return isDigest;
	}
	public void setIsDigest(String isDigest) {
		this.isDigest = isDigest;
	}
	public String getLastEditUserId() {
		return lastEditUserId;
	}
	public void setLastEditUserId(String lastEditUserId) {
		this.lastEditUserId = lastEditUserId;
	}
	public String getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(String lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getSubjectNameHtml() {
		return subjectNameHtml;
	}
	public void setSubjectNameHtml(String subjectNameHtml) {
		this.subjectNameHtml = subjectNameHtml;
	}
	public ThreadReplyInfo getThreadReplyInfo() {
		return threadReplyInfo;
	}
	public void setThreadReplyInfo(ThreadReplyInfo threadReplyInfo) {
		this.threadReplyInfo = threadReplyInfo;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public List<ThreadReplyInfo> getThreadReplyList() {
		return threadReplyList;
	}
	public void setThreadReplyList(List<ThreadReplyInfo> threadReplyList) {
		this.threadReplyList = threadReplyList;
	}
	
}
