package cn.javaex.uscat.view;

/**
 * 上传设置
 * @author 陈霓清
 *
 */
public class UploadInfo {
	private String id;			// 主键
	private String type;		// 类型（七牛云）
	private String domain;		// 域名
	private String ak;			// ak
	private String sk;			// sk
	private String bucket;		// 空间名称
	private String compress;	// 压缩率
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getAk() {
		return ak;
	}
	public void setAk(String ak) {
		this.ak = ak;
	}
	public String getSk() {
		return sk;
	}
	public void setSk(String sk) {
		this.sk = sk;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getCompress() {
		return compress;
	}
	public void setCompress(String compress) {
		this.compress = compress;
	}
	
}
