package cn.javaex.uscat.service.upload_info;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.dao.upload_info.IUploadInfoDAO;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.view.UploadInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@Service("UploadInfoService")
public class UploadInfoService {
	@Autowired
	private IUploadInfoDAO iUploadInfoDAO;

	/**
	 * 根据类型，查询上传设置信息
	 * @param type 类型
	 * @return
	 */
	public UploadInfo selectByType(String type) {
		return iUploadInfoDAO.selectByType(type);
	}

	/**
	 * 保存上传设置
	 * @param boardInfo
	 */
	public void save(UploadInfo boardInfo) {
		iUploadInfoDAO.update(boardInfo);
	}

	/**
	 * 上传本地图片到七牛云
	 * @param file
	 * @param uploadInfo
	 * @return
	 * @throws QingException
	 * @throws IOException
	 */
	public String uploadImage(MultipartFile file, UploadInfo uploadInfo) throws QingException, IOException {
		/**
		 * 构造一个带指定Zone对象的配置类
		 * 华东 : Zone.zone0()
		 * 华北 : Zone.zone1()
		 * 华南 : Zone.zone2()
		 * 北美 : Zone.zoneNa0()
		 */
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// ...生成上传凭证，然后准备上传
		String accessKey = uploadInfo.getAk();
		String secretKey = uploadInfo.getSk();
		String bucket = uploadInfo.getBucket();
		String compress = uploadInfo.getCompress();
		
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		String imgUrl = "";
		try {
			// 数据流上传
			InputStream byteInputStream = file.getInputStream();
			Auth auth = Auth.create(accessKey, secretKey);
			String upToken = auth.uploadToken(bucket);
			try {
				Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
				// 解析上传成功的结果
				DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//				System.out.println(putRet.key);
//				System.out.println(putRet.hash);
				imgUrl = uploadInfo.getDomain() + putRet.hash;
				
				// 图片压缩后再次上传
				if ("0".equals(compress) || "100".equals(compress)) {
					// 不压缩
				} else {
					imgUrl = uploadCompressImage(uploadInfo, auth, cfg, bucket, imgUrl);
					// 删除原图
					String deleteKey = putRet.hash;
					deleteFile(auth, cfg, bucket, deleteKey);
				}
			} catch (QiniuException ex) {
				Response r = ex.response;
				System.err.println(r.toString());
				try {
					System.err.println(r.bodyString());
				} catch (QiniuException ex2) {
					// ignore
				}
				throw new QingException(ErrorMsg.ERROR_500002);
			}
		} catch (UnsupportedEncodingException ex) {
			// ignore
			throw new QingException(ErrorMsg.ERROR_500002);
		}
		
		return imgUrl;
	}

	/**
	 * 图片压缩后再次上传
	 * @param uploadInfo
	 * @param auth
	 * @param cfg
	 * @param bucket
	 * @param imgUrl
	 * @return
	 * @throws QingException
	 */
	private String uploadCompressImage(UploadInfo uploadInfo, Auth auth, Configuration cfg, String bucket, String imgUrl) throws QingException {
		String compress = uploadInfo.getCompress();
		String apiCut = "imageView2/0/q/"+compress+"|imageslim";
		
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth, cfg);
		// 要fetch的url
		String url = imgUrl + apiCut;
//		System.out.println(url);
		
		try {
			// 调用fetch方法抓取文件
			String hash = bucketManager.fetch(url, bucket, null).hash;
//			System.out.println(hash);
			
			return uploadInfo.getDomain() + hash;
		} catch (QiniuException e) {
			e.printStackTrace();
			throw new QingException(ErrorMsg.ERROR_500003);
		}
	}

	/**
	 * 上传头像（本地上传）
	 * @param file
	 * @param uploadInfo
	 * @return
	 * @throws QingException
	 * @throws IOException
	 */
	public String uploadAvatar(MultipartFile file, UploadInfo uploadInfo) throws QingException, IOException {
		/**
		 * 构造一个带指定Zone对象的配置类
		 * 华东 : Zone.zone0()
		 * 华北 : Zone.zone1()
		 * 华南 : Zone.zone2()
		 * 北美 : Zone.zoneNa0()
		 */
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// ...生成上传凭证，然后准备上传
		String accessKey = uploadInfo.getAk();
		String secretKey = uploadInfo.getSk();
		String bucket = uploadInfo.getBucket();
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		
		String imgUrl = "";
		try {
			// 数据流上传
			InputStream byteInputStream = file.getInputStream();
			Auth auth = Auth.create(accessKey, secretKey);
			String upToken = auth.uploadToken(bucket);
			try {
				Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
				// 解析上传成功的结果
				DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//				System.out.println(putRet.key);
//				System.out.println(putRet.hash);
				String deleteKey = putRet.hash;
				imgUrl = uploadInfo.getDomain() + putRet.hash;

				// 图片裁剪后再次上传
				imgUrl = uploadCutImage(uploadInfo, auth, cfg, bucket, imgUrl);
				// 删除原图
				deleteFile(auth, cfg, bucket, deleteKey);
			} catch (QiniuException ex) {
				Response r = ex.response;
				System.err.println(r.toString());
				try {
					System.err.println(r.bodyString());
				} catch (QiniuException ex2) {
					// ignore
				}
				throw new QingException(ErrorMsg.ERROR_500002);
			}
		} catch (UnsupportedEncodingException ex) {
			// ignore
			throw new QingException(ErrorMsg.ERROR_500002);
		}
		
		return imgUrl;
	}
	
	/**
	 * 图片裁剪后再次上传
	 * @param uploadInfo
	 * @param auth
	 * @param cfg
	 * @param bucket
	 * @param imgUrl
	 * @return
	 * @throws QingException 
	 */
	public String uploadCutImage(UploadInfo uploadInfo, Auth auth, Configuration cfg, String bucket, String imgUrl) throws QingException {
		String width = "200";
		String height = "200";
		String apiCut = "?imageView2/1/w/"+width+"/h/"+height;
		
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth, cfg);
		// 要fetch的url
		String url = imgUrl + apiCut;
//		System.out.println(url);
		
		try {
			// 调用fetch方法抓取文件
			String hash = bucketManager.fetch(url, bucket, null).hash;
//			System.out.println(hash);
			
			return uploadInfo.getDomain() + hash;
		} catch (QiniuException e) {
			e.printStackTrace();
			throw new QingException(ErrorMsg.ERROR_500003);
		}
	}
	
	/**
	 * 删除原图
	 * @param auth
	 * @param cfg
	 * @param bucket
	 * @param fileName
	 */
	private void deleteFile(Auth auth, Configuration cfg, String bucket, String fileName) {
		//构造一个带指定Zone对象的配置类
//		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
			bucketManager.delete(bucket, fileName);
		} catch (QiniuException ex) {
			// 如果遇到异常，说明删除失败
			System.err.println(ex.code());
			System.err.println(ex.response.toString());
		}
	}
	
	/**
	 * 上传base64图片
	 * @param file64
	 * @param uploadInfo
	 * @return
	 * @throws IOException 
	 */
	public String uploadAvatarByBase64(String file64, UploadInfo uploadInfo) throws IOException {
		// 密钥配置
		String ak = uploadInfo.getAk();
		String sk = uploadInfo.getSk();
		Auth auth = Auth.create(ak, sk);
		
		// 空间名
		String bucketname = uploadInfo.getBucket();
		// 上传的图片名
		String key = UUID.randomUUID().toString().replace("-", "");
		
		file64 = file64.substring(22);
//		System.out.println("file64:"+file64);
		String url = "http://upload.qiniu.com/putb64/" + -1 + "/key/" + UrlSafeBase64.encodeToString(key);
		// 非华东空间需要根据注意事项 1 修改上传域名
		RequestBody rb = RequestBody.create(null, file64);
		String upToken  = auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
		Request request = new Request.Builder()
				.url(url)
				.addHeader("Content-Type", "application/octet-stream")
				.addHeader("Authorization", "UpToken " + upToken)
				.post(rb).build();
//		System.out.println(request.headers());
		OkHttpClient client = new OkHttpClient();
		okhttp3.Response response = client.newCall(request).execute();
		System.out.println(response);
		
		String imgUrl = uploadInfo.getDomain() + key;
		
		return imgUrl;
	}

	/**
	 * 上传文件
	 * @param file
	 * @param uploadInfo
	 * @return
	 * @throws QingException 
	 * @throws IOException 
	 */
	public String uploadFile(MultipartFile file, UploadInfo uploadInfo) throws QingException, IOException {
		/**
		 * 构造一个带指定Zone对象的配置类
		 * 华东 : Zone.zone0()
		 * 华北 : Zone.zone1()
		 * 华南 : Zone.zone2()
		 * 北美 : Zone.zoneNa0()
		 */
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// ...生成上传凭证，然后准备上传
		String accessKey = uploadInfo.getAk();
		String secretKey = uploadInfo.getSk();
		String bucket = uploadInfo.getBucket();
		
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		// 文件原名称
		String fileName = file.getOriginalFilename();
		String key = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
		String fileUrl = "";
		try {
			// 数据流上传
			InputStream byteInputStream = file.getInputStream();
			Auth auth = Auth.create(accessKey, secretKey);
			String upToken = auth.uploadToken(bucket);
			try {
				Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
				// 解析上传成功的结果
				DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//				System.out.println(putRet.key);
//				System.out.println(putRet.hash);
				fileUrl = uploadInfo.getDomain() + putRet.key;
			} catch (QiniuException ex) {
				Response r = ex.response;
				System.err.println(r.toString());
				try {
					System.err.println(r.bodyString());
				} catch (QiniuException ex2) {
					// ignore
				}
				throw new QingException(ErrorMsg.ERROR_500002);
			}
		} catch (UnsupportedEncodingException ex) {
			// ignore
			throw new QingException(ErrorMsg.ERROR_500002);
		}
		
		return fileUrl;
	}
}
