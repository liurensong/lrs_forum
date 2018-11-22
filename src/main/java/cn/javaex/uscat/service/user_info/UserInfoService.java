package cn.javaex.uscat.service.user_info;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.dao.activate_info.IActivateInfoDAO;
import cn.javaex.uscat.dao.group_info.IGroupInfoDAO;
import cn.javaex.uscat.dao.register_setting.IRegisterSettingDAO;
import cn.javaex.uscat.dao.thread_info.IThreadInfoDAO;
import cn.javaex.uscat.dao.thread_reply_info.IThreadReplyInfoDAO;
import cn.javaex.uscat.dao.user_count.IUserCountDAO;
import cn.javaex.uscat.dao.user_count_log.IUserCountLogDAO;
import cn.javaex.uscat.dao.user_info.IUserInfoDAO;
import cn.javaex.uscat.dao.user_profile_info.IUserProfileInfoDAO;
import cn.javaex.uscat.dao.web_info.IWebInfoDAO;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.activate_info.ActivateInfoService;
import cn.javaex.uscat.service.email_info.EmailInfoService;
import cn.javaex.uscat.util.GetIp;
import cn.javaex.uscat.util.Jiami;
import cn.javaex.uscat.util.MD5;
import cn.javaex.uscat.util.ValidatorUtil;
import cn.javaex.uscat.view.ActivateInfo;
import cn.javaex.uscat.view.RegisterSetting;
import cn.javaex.uscat.view.UserCount;
import cn.javaex.uscat.view.UserInfo;
import cn.javaex.uscat.view.UserProfileInfo;
import cn.javaex.uscat.view.WebInfo;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Service("UserInfoService")
public class UserInfoService {
	@Autowired
	private IUserInfoDAO iUserInfoDAO;
	@Autowired
	private IUserProfileInfoDAO iUserProfileInfoDAO;
	@Autowired
	private IUserCountDAO iUserCountDAO;
	@Autowired
	private IRegisterSettingDAO iRegisterSettingDAO;
	@Autowired
	private IGroupInfoDAO iGroupInfoDAO;
	@Autowired
	private IWebInfoDAO iWebInfoDAO;
	@Autowired
	private EmailInfoService emailInfoService;
	@Autowired
	private ActivateInfoService activateInfoService;
	@Autowired
	private IActivateInfoDAO iActivateInfoDAO;
	@Autowired
	private IThreadReplyInfoDAO iThreadReplyInfoDAO;
	@Autowired
	private IThreadInfoDAO iThreadInfoDAO;
	@Autowired
	private IUserCountLogDAO iUserCountLogDAO;
	
	/**
	 * 创建管理员账号
	 * @param request
	 * @return
	 * @throws QingException 
	 */
	public Map<String, Object> insertAdmin(HttpServletRequest request) throws QingException {
		// 判断是否是第一个用户
		int count = iUserInfoDAO.countUser(null, null);
		if (count>0) {
			throw new QingException(ErrorMsg.ERROR_100023);
		}
		
		// 1.0 获取注册参数
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		// 2.0 校验注册信息
		// 2.1 校验账号是否填写
		if (StringUtils.isEmpty(loginName)) {
			throw new QingException(ErrorMsg.ERROR_100004);
		}
		// 2.2 校验账号长度
		loginName = loginName.replaceAll("\\s*", "");
		if (loginName.length()<2 || loginName.length()>10) {
			throw new QingException(ErrorMsg.ERROR_100005);
		}
		
		// 2.3 校验密码是否填写
		if (StringUtils.isEmpty(password)) {
			throw new QingException(ErrorMsg.ERROR_100006);
		}
		// 2.4 校验密码长度
		password = password.replaceAll("\\s*", "");
		if (password.length()<6 || password.length()>16) {
			throw new QingException(ErrorMsg.ERROR_100007);
		}
		
		// 2.5 校验邮箱是否填写
		if (StringUtils.isEmpty(email)) {
			throw new QingException(ErrorMsg.ERROR_100008);
		}
		// 2.6 校验邮箱是否正确
		if (!ValidatorUtil.isEmail(email)) {
			throw new QingException(ErrorMsg.ERROR_100020);
		}
		
		// 3.0 注册新用户
		String userIp = GetIp.getIp(request);
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formatter.format(currentTime);
		
		UserInfo userInfo = new UserInfo();
		userInfo.setLoginName(loginName);
		userInfo.setPassword(MD5.md5(password));
		userInfo.setEmail(email);
		userInfo.setRegisterTime(now);
		userInfo.setRegisterIp(userIp);
		userInfo.setLastLoginTime(now);
		userInfo.setLastLoginIp(userIp);
		userInfo.setStatus("1");
		iUserInfoDAO.insert(userInfo);
		
		// 用户id
		String UID = userInfo.getId();
		
		// 4.0 向用户扩展表插入数据
		UserProfileInfo userProfileInfo = new UserProfileInfo();
		userProfileInfo.setUserId(UID);
		// 查询管理员用户组
		String groupId = iGroupInfoDAO.selectGroupIdByNameAndType("管理员", "system");
		userProfileInfo.setGroupId(groupId);
		iUserProfileInfoDAO.insert(userProfileInfo);
		
		// 5.0 向用户积分统计表插入数据
		UserCount userCount = new UserCount();
		userCount.setUserId(UID);
		userCount.setExtcredits1(0);
		userCount.setExtcredits2(0);
		userCount.setExtcredits3(0);
		userCount.setExtcredits4(0);
		userCount.setExtcredits5(0);
		userCount.setExtcredits6(0);
		userCount.setPosts(0);
		userCount.setThreads(0);
		userCount.setDigestposts(0);
		
		iUserCountDAO.insert(userCount);
		
		// 6.0 对用户信息进行加密，用于cookie存储
		// 用户的登录名和密码
		String userToken = Jiami.getInstance().encrypt(loginName) + "&&" + Jiami.getInstance().encrypt(password);
		// 将用户名转为没有特殊字符的base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		userToken = encoder.encode(userToken.getBytes());
		
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("userToken", userToken);
		
		// 将用户信息保存进session
		// 取出用户身份信息
		UserInfo adminInfo = getUserByUserToken(userToken);
		request.getSession().setAttribute("userInfo", adminInfo);
		
		return info;
	}
	
	/**
	 * 校验用户登录
	 * @param loginName 登录名
	 * @param password 登录密码
	 * @return
	 */
	public UserInfo selectUser(String loginName, String password) {
		return iUserInfoDAO.selectUser(loginName, password);
	}

	/**
	 * 注册新用户
	 * @param UserInfo
	 * @return 
	 * @throws QingException 
	 */
	public Map<String, Object> insertNewUser(HttpServletRequest request) throws QingException {
		// 1.0 获取注册参数
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		// 2.0 校验注册信息
		// 2.1 校验账号是否填写
		if (StringUtils.isEmpty(loginName)) {
			throw new QingException(ErrorMsg.ERROR_100004);
		}
		// 2.2 校验账号长度
		loginName = loginName.replaceAll("\\s*", "");
		if (loginName.length()<2 || loginName.length()>10) {
			throw new QingException(ErrorMsg.ERROR_100005);
		}
		
		// 2.3 校验密码是否填写
		if (StringUtils.isEmpty(password)) {
			throw new QingException(ErrorMsg.ERROR_100006);
		}
		// 2.4 校验密码长度
		password = password.replaceAll("\\s*", "");
		if (password.length()<6 || password.length()>16) {
			throw new QingException(ErrorMsg.ERROR_100007);
		}
		
		// 2.5 校验邮箱是否填写
		if (StringUtils.isEmpty(email)) {
			throw new QingException(ErrorMsg.ERROR_100008);
		}
		// 2.6 校验邮箱是否正确
		if (!ValidatorUtil.isEmail(email)) {
			throw new QingException(ErrorMsg.ERROR_100020);
		}
		
		int count = 0;
		// 2.7 校验账号是否已被占用
		count = countUser(loginName, null);
		if (count>0) {
			throw new QingException(ErrorMsg.ERROR_100009);
		}
		// 2.8 校验邮箱是否已被占用
		email = email.replaceAll("\\s*", "").toLowerCase();
		count = countUser(null, email);
		if (count>0) {
			throw new QingException(ErrorMsg.ERROR_100010);
		}
		
		// 3.0 注册新用户
		String userIp = GetIp.getIp(request);
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formatter.format(currentTime);
		
		UserInfo userInfo = new UserInfo();
		userInfo.setLoginName(loginName);
		userInfo.setPassword(MD5.md5(password));
		userInfo.setEmail(email);
		userInfo.setRegisterTime(now);
		userInfo.setRegisterIp(userIp);
		userInfo.setLastLoginTime(now);
		userInfo.setLastLoginIp(userIp);
		// 判断是否需要激活邮箱
		RegisterSetting registerSetting = iRegisterSettingDAO.select();
		if ("1".equals(registerSetting.getIsCheckEmail())) {
			userInfo.setStatus("0");	// 账号未激活
		} else {
			userInfo.setStatus("1");	// 账号已激活
		}
		iUserInfoDAO.insert(userInfo);
		
		// 用户id
		String UID = userInfo.getId();
		
		// 4.0 向用户扩展表插入数据
		UserProfileInfo userProfileInfo = new UserProfileInfo();
		userProfileInfo.setUserId(UID);
		// 查询积分为0分的用户组
		String groupId = iGroupInfoDAO.selectGroupIdByPoint("0");
		userProfileInfo.setGroupId(groupId);
		iUserProfileInfoDAO.insert(userProfileInfo);
		
		// 5.0 向用户积分统计表插入数据
		UserCount userCount = new UserCount();
		userCount.setUserId(UID);
		userCount.setExtcredits1(0);
		userCount.setExtcredits2(0);
		userCount.setExtcredits3(0);
		userCount.setExtcredits4(0);
		userCount.setExtcredits5(0);
		userCount.setExtcredits6(0);
		userCount.setPosts(0);
		userCount.setThreads(0);
		userCount.setDigestposts(0);
		
		iUserCountDAO.insert(userCount);
		
		// 6.0 对用户信息进行加密，用于cookie存储
		// 用户的登录名和密码
		String userToken = Jiami.getInstance().encrypt(loginName) + "&&" + Jiami.getInstance().encrypt(password);
		// 将用户名转为没有特殊字符的base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		userToken = encoder.encode(userToken.getBytes());
		
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("userToken", userToken);
		
		return info;
	}
	
	/**
	 * 校验账号是否已经被注册
	 * @param loginName 注册时填写的账号
	 * @param email 注册时填写的邮箱
	 * @return
	 */
	public int countUser(String loginName, String email) {
		return iUserInfoDAO.countUser(loginName, email);
	}

	/**
	 * 页面一加载就获取用户信息
	 * @param request
	 * @return
	 */
	public UserInfo getUserInfo(HttpServletRequest request) {
		// 1.0 从session中取用户信息
		// 判断session
		HttpSession session  = request.getSession();
		// 从session中取出用户身份信息
		UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
		if (userInfo!=null) {
			return userInfo;
		}
		
		// 2.0 session失效时，获取cookie
		String userToken = "";
		Cookie[] cookieArr = request.getCookies();
		if (cookieArr!=null && cookieArr.length>0) {
			for (int i=0; i<cookieArr.length; i++) {
				Cookie cookie = cookieArr[i];
				if ("userToken".equals(cookie.getName())) {
					try {
						userToken = URLDecoder.decode(cookie.getValue(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						break;
					}
					break;
				}
			}
		}
		
		// 2.1 指定cookie不存在时，直接返回null
		if (StringUtils.isEmpty(userToken)) {
			return null;
		}
		
		// 2.2 指定cookie存在时，模拟登录，获取用户信息
		try {
			userInfo = getUserInfoByUserToken(userToken);
			// 将用户信息保存进session
			request.getSession().setAttribute("userInfo", userInfo);
		} catch (QingException e) {
			// 用户凭证是伪造的
			return null;
		}
		
		return userInfo;
	}
	
	/**
	 * 更新用户session
	 * @param request
	 * @return
	 */
	public UserInfo updateUserInfoSession(HttpServletRequest request) {
		UserInfo userInfo = null;
		// 1.0 获取cookie
		String userToken = "";
		Cookie[] cookieArr = request.getCookies();
		if (cookieArr!=null && cookieArr.length>0) {
			for (int i=0; i<cookieArr.length; i++) {
				Cookie cookie = cookieArr[i];
				if ("userToken".equals(cookie.getName())) {
					try {
						userToken = URLDecoder.decode(cookie.getValue(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						break;
					}
					break;
				}
			}
		}
		
		// 2.0 根据userToken重新获取用户信息
		// 2.1 指定cookie不存在时，直接返回null
		if (StringUtils.isEmpty(userToken)) {
			return null;
		}
		
		// 2.2 指定cookie存在时，模拟登录，获取用户信息
		try {
			userInfo = getUserInfoByUserToken(userToken);
			// 将用户信息保存进session
			request.getSession().setAttribute("userInfo", userInfo);
		} catch (QingException e) {
			// 用户凭证是伪造的
			return null;
		}
		
		return userInfo;
	}
	
	/**
	 * 根据userToken，获取用户信息
	 * @param userToken
	 * @return
	 * @throws QingException
	 */
	public UserInfo getUserByUserToken(String userToken) throws QingException {
		// userToken编码转换
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decoderBase64;
		try {
			decoderBase64 = decoder.decodeBuffer(userToken);
			userToken = new String(decoderBase64);
		} catch (IOException e) {
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		
		String[] arr = userToken.split("&&");
		if (arr.length<=1) {
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		
		// userToken解密
		String loginName = Jiami.getInstance().decrypt(arr[0]);
		String password = Jiami.getInstance().decrypt(arr[1]);
		
		// 根据主键查询用户信息
		UserInfo userInfo = selectUser(loginName, MD5.md5(password));
		if (userInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		
		return userInfo;
	}
	
	/**
	 * 根据userToken，自动登录
	 * @param userToken
	 * @return
	 * @throws QingException
	 */
	public UserInfo getUserInfoByUserToken(String userToken) throws QingException {
		// userToken编码转换
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decoderBase64;
		try {
			decoderBase64 = decoder.decodeBuffer(userToken);
			userToken = new String(decoderBase64);
		} catch (IOException e) {
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		
		String[] arr = userToken.split("&&");
		if (arr.length<=1) {
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		
		// userToken解密
		String loginName = Jiami.getInstance().decrypt(arr[0]);
		String password = Jiami.getInstance().decrypt(arr[1]);
		
		// 根据主键查询用户信息
		UserInfo userInfo = selectUser(loginName, MD5.md5(password));
		if (userInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		// 校验用户是否正常
		if ("0".equals(userInfo.getStatus())) {
			throw new QingException(ErrorMsg.ERROR_100019);
		}
		if ("2".equals(userInfo.getStatus())) {
			throw new QingException(ErrorMsg.ERROR_100003);
		}
		
		return userInfo;
	}

	/**
	 * 发送邮箱验证
	 * @param userInfo 用户信息
	 * @param subject 邮件标题
	 * @param type 验证类型
	 * @throws QingException
	 */
	public void saveActivateInfoAndSendEmail(UserInfo userInfo, String subject, String type) throws QingException {
		// 获取站点信息
		WebInfo webInfo = iWebInfoDAO.select();
		String webName = webInfo.getName();
		
		// 获取当前系统时间
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formatter.format(currentTime);
		
		String verificationCode = String.valueOf((int)((Math.random()*9+1)*100000));
		
		// 生成邮件内容
		StringBuffer content = new StringBuffer();
		content.append("<div style='background:#f7f7f7;overflow:hidden'>");
		content.append("<div style='background:#fff;border:1px solid #ccc;margin:2%;padding:0 30px'>");
		content.append("<div style='line-height:40px;height:40px'>&nbsp;</div>");
		content.append("<p style='margin:0;padding:0;font-size:14px;line-height:30px;color:#333;font-family:arial,sans-serif;font-weight:bold'>亲爱的用户 <b style='font-size:18px;color:#f90'>"+userInfo.getLoginName()+"</b>：</p>");
		content.append("<div style='line-height:20px;height:20px'>&nbsp;</div>");
		content.append("<p style='margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif'>您好！感谢您使用"+webName+"服务，您正在进行邮箱验证，本次请求的验证码为：</p>");
		content.append("<p style='margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif'>");
		content.append("<b style='font-size:18px;color:#f90'>"+verificationCode+"</b>");
		content.append("<span style='margin:0;padding:0;margin-left:10px;line-height:30px;font-size:14px;color:#979797;font-family:'宋体',arial,sans-serif'>(为了保障您账号的安全性，请在1小时内完成验证。)</span>");
		content.append("</p>");
		content.append("<div style='line-height:80px;height:80px'>&nbsp;</div>");
		content.append("<p style='margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif'>"+webName+"团队</p>");
		content.append("<p style='margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif'>"+now+"</p>");
		content.append("</div>");
		content.append("</div>");
		
		// 发送验证码邮件
		emailInfoService.sendEmail(userInfo.getEmail(), subject, content.toString());
		
		// 生成验证码有效期
		ActivateInfo activateInfo = new ActivateInfo();
		activateInfo.setUserId(userInfo.getId());
		activateInfo.setType(type);
		activateInfo.setCode(verificationCode);
		activateInfo.setCreateTime(now);
		
		activateInfoService.save(activateInfo);
	}

	/**
	 * 邮箱验证，激活账号
	 * @param userInfo
	 * @param identifyingCode
	 * @throws QingException
	 * @throws ParseException
	 */
	public void updateUserStatus(UserInfo userInfo, String identifyingCode) throws QingException, ParseException {
		// 1.0 获取验证记录
		ActivateInfo activateInfo = iActivateInfoDAO.selectByUserIdAndType(userInfo.getId(), "register");
		if (activateInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100021);
		}
		
		// 2.0 校验
		// 2.1 校验验证码是否正确
		if (!identifyingCode.equals(activateInfo.getCode())) {
			throw new QingException(ErrorMsg.ERROR_100014);
		}
		
		// 2.2 校验验证码是否已过期
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formatter.format(currentTime);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date d1 = df.parse(now);
		Date d2 = df.parse(activateInfo.getCreateTime());
		long diff = d1.getTime() - d2.getTime();
		long hours = diff / (1000 * 60 * 60);
//		System.out.println("diff:"+diff);
//		System.out.println("hours"+hours);
		if (hours>0) {
			throw new QingException(ErrorMsg.ERROR_100015);
		}
		
		// 3.0 修改用户状态
		iUserInfoDAO.update(userInfo);
		
		// 4.0 删除验证记录
		iActivateInfoDAO.delete(activateInfo.getId());
	}

	/**
	 * 用户登录
	 * @param request
	 * @return
	 * @throws QingException 
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String, Object> login(HttpServletRequest request) throws QingException, UnsupportedEncodingException {
		// 1.0 获取登录参数
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		
		// 2.0 校验用户
		// 2.1 校验用户名或密码是否填写
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
			throw new QingException(ErrorMsg.ERROR_100001);
		}
		
		// 2.2 校验用户名、密码是否正确
		UserInfo userInfo = selectUser(loginName, MD5.md5(password));
		if (userInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100002);
		}
		
		// 2.3 校验用户是否正常
		if ("0".equals(userInfo.getStatus())) {
			throw new QingException(ErrorMsg.ERROR_100019);
		}
		if ("2".equals(userInfo.getStatus())) {
			throw new QingException(ErrorMsg.ERROR_100003);
		}
		
		// 3.0 将用户信息保存进session
		request.getSession().setAttribute("userInfo", userInfo);
		
		// 4.0 对用户信息进行加密，用于cookie存储
		// 用户的登录名和密码
		String userToken = Jiami.getInstance().encrypt(loginName) + "&&" + Jiami.getInstance().encrypt(password);
		// 将用户名转为没有特殊字符的base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		userToken = encoder.encode(userToken.getBytes());
		
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("userToken", userToken);
		
		return info;
	}

	/**
	 * 用户找回密码，发送邮箱验证码
	 * @param email 用户填写的邮箱地址
	 * @throws QingException 
	 */
	public void updateActivateCode(String email) throws QingException {
		
		if (StringUtils.isEmpty(email)) {
			throw new QingException(ErrorMsg.ERROR_100008);
		}
		
		// 校验邮箱是否存在
		UserInfo userInfo = iUserInfoDAO.selectUserByEmail(email);
		if (userInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100018);
		}
		
		saveActivateInfoAndSendEmail(userInfo, "找回密码验证", "find_password");
	}

	/**
	 * 用户自己设置新的密码
	 * @param email 邮箱地址
	 * @param identifyingCode 邮箱验证码
	 * @param password 新密码
	 * @throws QingException
	 * @throws ParseException
	 */
	public void updatePassword(String email, String identifyingCode, String password) throws QingException, ParseException {
		if (StringUtils.isEmpty(password)) {
			throw new QingException(ErrorMsg.ERROR_100006);
		}
		
		password = password.replaceAll("\\s*", "");
		if (password.length()<6 || password.length()>16) {
			throw new QingException(ErrorMsg.ERROR_100007);
		}
		
		// 获取用户id
		ActivateInfo activateInfo = iActivateInfoDAO.selectByEmailAndCodeAndType(email, identifyingCode, "find_password");
		if (activateInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100014);
		}
		// 校验验证码是否已过期
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formatter.format(currentTime);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date d1 = df.parse(now);
		Date d2 = df.parse(activateInfo.getCreateTime());
		long diff = d1.getTime() - d2.getTime();
		long hours = diff / (1000 * 60 * 60);
		if (hours>0) {
			throw new QingException(ErrorMsg.ERROR_100015);
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setId(activateInfo.getUserId());
		userInfo.setPassword(MD5.md5(password));
		// 如果用户此前为未激活状态，则直接改为激活状态
		String status = iUserInfoDAO.selectUserStatusById(activateInfo.getUserId());
		if ("0".equals(status)) {
			userInfo.setStatus("1");
		}
		// 设置邮箱状态为激活状态
		userInfo.setIsEmailChecked("1");
		
		iUserInfoDAO.update(userInfo);
	}

	/**
	 * 查询所有用户
	 * @param param
	 * @return
	 */
	public List<UserInfo> list(Map<String, Object> param) {
		return iUserInfoDAO.list(param);
	}

	/**
	 * 批量更新用户状态
	 * @param param
	 */
	public void batchUpdateStatus(Map<String, Object> param) {
		iUserInfoDAO.batchUpdate(param);
	}

	/**
	 * 批量更换用户所在用户组
	 * @param param
	 */
	public void batchChangeGroup(Map<String, Object> param) {
		iUserProfileInfoDAO.batchUpdate(param);
	}

	/**
	 * 根据主键查询用户信息（后台管理专用）
	 * @param id 用户id
	 * @return
	 */
	public UserInfo selectById(String id) {
		return iUserInfoDAO.selectById(id);
	}

	/**
	 * 保存用户信息
	 * @param userInfo
	 * @throws QingException
	 */
	public void save(UserInfo userInfo) throws QingException {
		if (StringUtils.isEmpty(userInfo.getId())) {
			// 新增
			// 1.0 获取注册参数
			String loginName = userInfo.getLoginName();
			String password = userInfo.getPassword();
			String email = userInfo.getEmail();
			
			// 2.0 校验注册信息
			// 2.1 校验账号是否填写
			if (StringUtils.isEmpty(loginName)) {
				throw new QingException(ErrorMsg.ERROR_100004);
			}
			// 2.2 校验账号长度
			loginName = loginName.replaceAll("\\s*", "");
			if (loginName.length()<2 || loginName.length()>10) {
				throw new QingException(ErrorMsg.ERROR_100005);
			}
			
			// 2.3 校验密码是否填写
			if (StringUtils.isEmpty(password)) {
				throw new QingException(ErrorMsg.ERROR_100006);
			}
			// 2.4 校验密码长度
			password = password.replaceAll("\\s*", "");
			if (password.length()<6 || password.length()>16) {
				throw new QingException(ErrorMsg.ERROR_100007);
			}
			
			// 2.5 校验邮箱是否填写
			if (StringUtils.isEmpty(email)) {
				throw new QingException(ErrorMsg.ERROR_100008);
			}
			// 2.7 校验邮箱是否正确
			if (!ValidatorUtil.isEmail(email)) {
				throw new QingException(ErrorMsg.ERROR_100020);
			}
			
			int count = 0;
			// 2.8 校验账号是否已被占用
			count = countUser(loginName, null);
			if (count>0) {
				throw new QingException(ErrorMsg.ERROR_100009);
			}
			// 2.9 校验邮箱是否已被占用
			email = email.replaceAll("\\s*", "").toLowerCase();
			count = countUser(null, email);
			if (count>0) {
				throw new QingException(ErrorMsg.ERROR_100010);
			}
			
			// 3.0 注册新用户
			// 获取当前时间
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = formatter.format(currentTime);
			// 注册时间
			if (StringUtils.isEmpty(userInfo.getRegisterTime())) {
				userInfo.setRegisterTime(now);
			}
			// 上次访问时间
			if (StringUtils.isEmpty(userInfo.getLastLoginTime())) {
				userInfo.setLastLoginTime(now);
			}
			userInfo.setPassword(MD5.md5(password));	// 密码加密
			userInfo.setStatus("1");	// 正常状态
			
			iUserInfoDAO.insert(userInfo);
			
			// 4.0 向用户扩展表插入数据
			UserProfileInfo userProfileInfo = new UserProfileInfo();
			userProfileInfo.setUserId(userInfo.getId());
			userProfileInfo.setAvatar(userInfo.getAvatar());
			userProfileInfo.setGroupId(userInfo.getGroupId());
			
			iUserProfileInfoDAO.insert(userProfileInfo);
			
			// 5.0 向用户积分统计表插入数据
			UserCount userCount = new UserCount();
			userCount.setUserId(userInfo.getId());
			userCount.setExtcredits1(0);
			userCount.setExtcredits2(0);
			userCount.setExtcredits3(0);
			userCount.setExtcredits4(0);
			userCount.setExtcredits5(0);
			userCount.setExtcredits6(0);
			userCount.setPosts(0);
			userCount.setThreads(0);
			userCount.setDigestposts(0);
			
			iUserCountDAO.insert(userCount);
		} else {
			// 编辑
			if (!StringUtils.isEmpty(userInfo.getPassword())) {
				userInfo.setPassword(MD5.md5(userInfo.getPassword()));	// 密码加密
			}
			iUserInfoDAO.update(userInfo);
			
			UserProfileInfo userProfileInfo = new UserProfileInfo();
			userProfileInfo.setUserId(userInfo.getId());
			userProfileInfo.setAvatar(userInfo.getAvatar());
			userProfileInfo.setGroupId(userInfo.getGroupId());
			
			iUserProfileInfoDAO.update(userProfileInfo);
		}
	}
	
	/**
	 * 批量删除封禁用户的相关内容
	 * @param userIdArr 用户id数组
	 * @param deleteArr 删除内容表名数组
	 */
	public void batchDeleteContent(String[] userIdArr, String[] deleteArr) {
		// 遍历需要删除哪些表的数据
		for (int i=0; i<deleteArr.length; i++) {
			if ("thread".equals(deleteArr[i])) {
				// 删除用户的主题和回帖
				// 1.0 删除回帖记录
				iThreadReplyInfoDAO.deleteByUserIdArr(userIdArr);
				
				// 2.0 删除主题记录
				iThreadInfoDAO.deleteByUserIdArr(userIdArr);
				
				// 3.0 删除用户积分获取记录日志
				iUserCountLogDAO.deleteByUserIdArr(userIdArr);
				
				// 4.0 清空用户积分、帖子数等信息记录表
				iUserCountDAO.clearByUserIdArr(userIdArr);
			}
		}
	}

	/**
	 * 根据用户id，查询用户信息，前台用户空间专用
	 * @param id 用户id
	 * @return
	 */
	public UserInfo selectUserInfoById(String id) {
		UserInfo userInfo = iUserInfoDAO.selectUserInfoById(id);
		if (userInfo!=null) {
			UserCount userCount = iUserCountDAO.selectByUserId(id);
			// 总积分计算公式：发帖数（主题）+精华帖数*5+贡献*2+铜币
			int point = userCount.getPosts() + userCount.getDigestposts()*5 + userCount.getExtcredits4()*2 + userCount.getExtcredits1();
			userCount.setPoint(point);
			
			userInfo.setUserCount(userCount);
		}
		
		return userInfo;
	}

	/**
	 * 用户自己修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @param email
	 * @param identifyingCode
	 * @throws QingException 
	 * @throws ParseException
	 */
	public void updateNewPassword(String oldPassword, String newPassword, String email, String identifyingCode) throws QingException, ParseException {
		// 1.0 根据旧密码和邮箱，判断查询用户
		UserInfo userInfo = iUserInfoDAO.selectByPasswordAndEmail(MD5.md5(oldPassword), email);
		if (userInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100022);
		}
		
		// 2.0 校验新密码
		newPassword = newPassword.replaceAll("\\s*", "");
		if (newPassword.length()<6 || newPassword.length()>16) {
			throw new QingException(ErrorMsg.ERROR_100007);
		}
		
		// 3.0 校验验证码
		ActivateInfo activateInfo = iActivateInfoDAO.selectByEmailAndCodeAndType(email, identifyingCode, "change_password");
		if (activateInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100014);
		}
		// 4.0 校验验证码是否已过期
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formatter.format(currentTime);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date d1 = df.parse(now);
		Date d2 = df.parse(activateInfo.getCreateTime());
		long diff = d1.getTime() - d2.getTime();
		long hours = diff / (1000 * 60 * 60);
		if (hours>0) {
			throw new QingException(ErrorMsg.ERROR_100015);
		}
		
		// 5.0 更新新密码
		userInfo.setPassword(MD5.md5(newPassword));
		userInfo.setIsEmailChecked("1");
		
		iUserInfoDAO.update(userInfo);
	}

	/**
	 * 设置邮箱验证码
	 * @param request
	 * @throws QingException 
	 */
	public void saveCode(HttpServletRequest request) throws QingException {
		UserInfo userInfo = getUserInfo(request);
		if (userInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		
		saveActivateInfoAndSendEmail(userInfo, "修改密码验证", "change_password");
	}

}
