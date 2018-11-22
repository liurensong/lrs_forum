package cn.javaex.uscat.view;

/**
 * 板块-会员用户组权限表
 * 
 * @author 陈霓清
 */
public class BoardGroupPerm {
	private String id;						// 主键
	private String fid;						// 板块id
	private String groupId;					// 用户组id
	private String isViewBoard;				// 是否允许浏览板块（1：允许；0：不允许）
	private String isPost;					// 是否允许发新帖（1：允许；0：不允许）
	private String isReply;					// 是否允许回复帖子（1：允许；0：不允许）
	private String isDownloadAttachment;	// 是否允许下载附件（1：允许；0：不允许）
	private String isUploadAttachment;		// 是否允许上传附件（1：允许；0：不允许）
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getisViewBoard() {
		return isViewBoard;
	}
	public void setisViewBoard(String isViewBoard) {
		this.isViewBoard = isViewBoard;
	}
	public String getIsPost() {
		return isPost;
	}
	public void setIsPost(String isPost) {
		this.isPost = isPost;
	}
	public String getIsReply() {
		return isReply;
	}
	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}
	public String getIsDownloadAttachment() {
		return isDownloadAttachment;
	}
	public void setIsDownloadAttachment(String isDownloadAttachment) {
		this.isDownloadAttachment = isDownloadAttachment;
	}
	public String getIsUploadAttachment() {
		return isUploadAttachment;
	}
	public void setIsUploadAttachment(String isUploadAttachment) {
		this.isUploadAttachment = isUploadAttachment;
	}
	
}
