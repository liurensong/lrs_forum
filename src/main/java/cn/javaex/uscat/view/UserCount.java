package cn.javaex.uscat.view;

/**
 * 用户积分、帖子数等信息记录表
 * 
 * @author 陈霓清
 */
public class UserCount {
	private String id;			// 主键
	private String userId;		// 用户id
	private int extcredits1;	// 积分
	private int extcredits2;
	private int extcredits3;
	private int extcredits4;
	private int extcredits5;
	private int extcredits6;
	private int posts;			// 主题数
	private int threads;		// 回帖数
	private int digestposts;	// 精华帖数
	private int point;			// 用户总积分
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getExtcredits1() {
		return extcredits1;
	}
	public void setExtcredits1(int extcredits1) {
		this.extcredits1 = extcredits1;
	}
	public int getExtcredits2() {
		return extcredits2;
	}
	public void setExtcredits2(int extcredits2) {
		this.extcredits2 = extcredits2;
	}
	public int getExtcredits3() {
		return extcredits3;
	}
	public void setExtcredits3(int extcredits3) {
		this.extcredits3 = extcredits3;
	}
	public int getExtcredits4() {
		return extcredits4;
	}
	public void setExtcredits4(int extcredits4) {
		this.extcredits4 = extcredits4;
	}
	public int getExtcredits5() {
		return extcredits5;
	}
	public void setExtcredits5(int extcredits5) {
		this.extcredits5 = extcredits5;
	}
	public int getExtcredits6() {
		return extcredits6;
	}
	public void setExtcredits6(int extcredits6) {
		this.extcredits6 = extcredits6;
	}
	public int getPosts() {
		return posts;
	}
	public void setPosts(int posts) {
		this.posts = posts;
	}
	public int getThreads() {
		return threads;
	}
	public void setThreads(int threads) {
		this.threads = threads;
	}
	public int getDigestposts() {
		return digestposts;
	}
	public void setDigestposts(int digestposts) {
		this.digestposts = digestposts;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
}
