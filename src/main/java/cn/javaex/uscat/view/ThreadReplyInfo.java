package cn.javaex.uscat.view;

/**
 * 帖子回复表
 * 
 * @author 陈霓清
 */
public class ThreadReplyInfo {
	private String id;				// 主键
	private String tid;				// 帖子主键
	private String contentHtml;		// 回复内容（包含html代码）
	private String contentText;		// 回复简介（纯文本）
	private String replyTime;		// 回复时间
	private String replyUserId;		// 回复人id
	private int floor;				// 回复发布的楼层
	private String beReplyId;		// 被回复的记录的主键
	private String status;			// 帖子状态（0：待审核；1：正常；2：屏蔽）
	
	private String loginName;		// 用户登录名
	private ThreadReplyInfo quote;	// 引用（对楼层回复时，页面需要显示被回复的楼层的纯文本内容）
	
	private UserInfo userInfo;		// 用户信息
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getContentHtml() {
		return contentHtml;
	}
	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
	public String getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public String getBeReplyId() {
		return beReplyId;
	}
	public void setBeReplyId(String beReplyId) {
		this.beReplyId = beReplyId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public ThreadReplyInfo getQuote() {
		return quote;
	}
	public void setQuote(ThreadReplyInfo quote) {
		this.quote = quote;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
