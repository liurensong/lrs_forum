package cn.javaex.uscat.action.user_info;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.nav_info.NavInfoService;
import cn.javaex.uscat.service.point_info.PointInfoService;
import cn.javaex.uscat.service.point_rule.PointRuleService;
import cn.javaex.uscat.service.register_setting.RegisterSettingService;
import cn.javaex.uscat.service.template_info.TemplateInfoService;
import cn.javaex.uscat.service.thread_info.ThreadInfoService;
import cn.javaex.uscat.service.user_count.UserCountService;
import cn.javaex.uscat.service.user_info.UserInfoService;
import cn.javaex.uscat.service.user_point_log.UserPointLogService;
import cn.javaex.uscat.service.web_info.WebInfoService;
import cn.javaex.uscat.service.zone_info.ZoneInfoService;
import cn.javaex.uscat.view.NavInfo;
import cn.javaex.uscat.view.PointInfo;
import cn.javaex.uscat.view.PointRule;
import cn.javaex.uscat.view.RegisterSetting;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.ThreadInfo;
import cn.javaex.uscat.view.UserCount;
import cn.javaex.uscat.view.UserInfo;
import cn.javaex.uscat.view.UserPointLog;
import cn.javaex.uscat.view.WebInfo;
import cn.javaex.uscat.view.ZoneInfo;

@Controller
@RequestMapping("forum/user_info")
public class UserInfoAction {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private WebInfoService webInfoService;
	@Autowired
	private NavInfoService navInfoService;
	@Autowired
	private TemplateInfoService templateInfoService;
	@Autowired
	private RegisterSettingService registerSettingService;
	@Autowired
	private PointInfoService pointInfoService;
	@Autowired
	private ThreadInfoService threadInfoService;
	@Autowired
	private UserCountService userCountService;
	@Autowired
	private PointRuleService pointRuleService;
	@Autowired
	private ZoneInfoService zoneInfoService;
	@Autowired
	private UserPointLogService userPointLogService;
	
	/**
	 * 创建管理员账号
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("create_admin.json")
	@ResponseBody
	public Result createAdmin(HttpServletRequest request) throws Exception {
		
		Map<String, Object> info = userInfoService.insertAdmin(request);
		
		return Result.success().add("info", info);
	}
	
	/**
	 * 跳转用户注册页面
	 * @return
	 */
	@RequestMapping("register.action")
	public String register(ModelMap map) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// 查询注册设置信息
		RegisterSetting registerSetting = registerSettingService.select();
		map.put("registerSetting", registerSetting);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		// 判断是否开放注册
		if ("1".equals(registerSetting.getIsAllowRegister())) {
			// 开放注册
			return "portal/pc/template/" + template + "/user/register";
		} else {
			// 关闭注册
			return "portal/pc/template/" + template + "/user/close_register";
		}
	}
	
	/**
	 * 用户注册
	 * @throws Exception 
	 */
	@RequestMapping("register.json")
	@ResponseBody
	public Result register(HttpServletRequest request) throws Exception {
		
		Map<String, Object> info = userInfoService.insertNewUser(request);
		
		return Result.success().add("info", info);
	}
	
	/**
	 * 用户注册时，邮箱验证
	 * @param request
	 * @param userToken 用户凭证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("register_email.json")
	@ResponseBody
	public Result registerEmail(
			HttpServletRequest request,
			@RequestParam(value="userToken") String userToken) throws Exception {
		
		if (StringUtils.isEmpty(userToken)) {
			throw new QingException(ErrorMsg.ERROR_100011);
		}

		UserInfo userInfo = userInfoService.getUserByUserToken(userToken);

		userInfoService.saveActivateInfoAndSendEmail(userInfo, "注册邮箱验证", "register");
		
		return Result.success();
	}
	
	/**
	 * 邮箱验证，激活账号
	 * @param request
	 * @param identifyingCode 验证码
	 * @param userToken 用户凭证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("update_user_status.json")
	@ResponseBody
	public Result updateUserStatus(
			HttpServletRequest request,
			@RequestParam(value="identifyingCode") String identifyingCode,
			@RequestParam(value="userToken") String userToken) throws Exception {

		// 校验验证码
		if (StringUtils.isEmpty(identifyingCode)) {
			throw new QingException(ErrorMsg.ERROR_100013);
		}
		
		// 校验用户凭证
		if (StringUtils.isEmpty(userToken)) {
			throw new QingException(ErrorMsg.ERROR_100011);
		}

		// 取出用户身份信息
		UserInfo userInfo = userInfoService.getUserByUserToken(userToken);
		
		UserInfo userInfo2 = new UserInfo();
		userInfo2.setId(userInfo.getId());
		userInfo2.setStatus("1");	// 激活
		userInfo2.setIsEmailChecked("1");	// 设置邮箱状态为已激活
		
		userInfoService.updateUserStatus(userInfo2, identifyingCode);
		
		// 将用户信息保存进session
		request.getSession().setAttribute("userInfo", userInfo);
		
		return Result.success();
	}
	
	/**
	 * 打开登录弹出层
	 * @return
	 */
	@RequestMapping("login_dialog.action")
	public String loginDialog() {
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		return "portal/pc/template/" + template + "/user/login_dialog";
	}
	
	/**
	 * 跳转用户登录页面
	 * @return
	 */
	@RequestMapping("login.action")
	public String login(ModelMap map) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		return "portal/pc/template/" + template + "/user/login";
	}
	
	/**
	 * 用户登录
	 * @throws Exception
	 */
	@RequestMapping("login.json")
	@ResponseBody
	public Result login(HttpServletRequest request) throws Exception {
		
		Map<String, Object> info = userInfoService.login(request);
		
		return Result.success().add("info", info);
	}
	
	/**
	 * 用户注销，清除session
	 */
	@RequestMapping("logout.json")
	@ResponseBody
	public Result login(HttpSession session) {
		// 清除session
		session.invalidate();
		
		return Result.success();
	}
	
	/**
	 * 跳转找回密码页面
	 * @return
	 */
	@RequestMapping("find_password.action")
	public String findPassword(ModelMap map) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		return "portal/pc/template/" + template + "/user/find_password";
	}
	
	/**
	 * 用户找回密码，发送邮箱验证码
	 * @param email 用户填写的邮箱地址
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("find_pwd_email.json")
	@ResponseBody
	public Result findPwdEmail(@RequestParam(value="email") String email) throws Exception {
		
		userInfoService.updateActivateCode(email);
		
		return Result.success();
	}
	
	/**
	 * 用户自己设置新的密码
	 * @param request
	 * @param email 邮箱地址
	 * @param identifyingCode 邮箱验证码
	 * @param password 新密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("update_password.json")
	@ResponseBody
	public Result updatePassword(
			HttpServletRequest request,
			@RequestParam(value="email") String email,
			@RequestParam(value="identifyingCode") String identifyingCode,
			@RequestParam(value="password") String password) throws Exception {

		userInfoService.updatePassword(email, identifyingCode, password);
		
		return Result.success();
	}
	
	/**
	 * 查看用户资料
	 * @param map
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping("space_profile.action")
	public String spaceProfile(ModelMap map,
			HttpServletRequest request,
			@RequestParam(required=false, value="userId") String userId) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 判断参数合法性
		try {
			Integer.parseInt(userId);
		} catch (Exception e) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		
		UserInfo spaceUserInfo = userInfoService.selectUserInfoById(userId);
		if (spaceUserInfo==null) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		map.put("spaceUserInfo", spaceUserInfo);
		
		// 查询启用的积分
		List<PointInfo> pointUseList = pointInfoService.list("1");
		map.put("pointUseList", pointUseList);
		
		// 获取登录用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		map.put("title", "用户资料 - " + webInfo.getName());
		return "portal/pc/template/" + template + "/user/space/profile";
	}
	
	/**
	 * 查看用户主题和帖子
	 * @param map
	 * @param userId 用户id
	 * @param type 查看类型
	 * @return
	 */
	@RequestMapping("space_thread.action")
	public String spaceThread(ModelMap map,
			HttpServletRequest request,
			@RequestParam(required=false, value="userId") String userId,
			@RequestParam(required=false, value="type") String type,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 判断参数合法性
		try {
			Integer.parseInt(userId);
		} catch (Exception e) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		
		UserInfo spaceUserInfo = userInfoService.selectUserInfoById(userId);
		if (spaceUserInfo==null) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		map.put("spaceUserInfo", spaceUserInfo);
		
		if ("reply".equals(type)) {
			// pageHelper分页插件
			// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
			PageHelper.startPage(pageNum, pageSize);
			List<ThreadInfo> list = threadInfoService.listReplyByUserId(userId);
			PageInfo<ThreadInfo> pageInfo = new PageInfo<ThreadInfo>(list);
			map.put("pageInfo", pageInfo);
			
			map.put("type", type);
		} else {
			// pageHelper分页插件
			// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
			PageHelper.startPage(pageNum, pageSize);
			List<ThreadInfo> list = threadInfoService.listByUserId(userId);
			PageInfo<ThreadInfo> pageInfo = new PageInfo<ThreadInfo>(list);
			map.put("pageInfo", pageInfo);
		}
		
		// 获取当前登录用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		map.put("title", "用户帖子 - " + webInfo.getName());
		return "portal/pc/template/" + template + "/user/space/thread";
	}
	
	/**
	 * 用户个人信息之个人资料
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("spacecp_profile.action")
	public String spacecpProfile(ModelMap map, HttpServletRequest request) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 获取登录用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 非法访问，重定向到登录页
			return "redirect:login.action";
		}
		map.put("userInfo", userInfo);
		
		map.put("title", "个人资料 - " + webInfo.getName());
		map.put("userLink", "spacecp_profile");
		
		return "portal/pc/template/" + template + "/user/spacecp/profile";
	}
	
	/**
	 * 用户个人信息之积分
	 * @param map
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("spacecp_point.action")
	public String spacecpPoint(ModelMap map,
			HttpServletRequest request,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 非法访问，重定向到登录页
			return "redirect:login.action";
		} else {
			UserCount userCount = userCountService.selectByUserId(userInfo.getId());
			userInfo.setUserCount(userCount);
			map.put("userInfo", userInfo);
		}
		
		// 积分记录
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<UserPointLog> userPointLogList = userPointLogService.listByUserId(userInfo.getId());
		PageInfo<UserPointLog> pageInfo = new PageInfo<UserPointLog>(userPointLogList);
		map.put("pageInfo", pageInfo);
		
		// 查询启用的积分
		List<PointInfo> pointUseList = pointInfoService.list("1");
		map.put("pointUseList", pointUseList);
		
		// 查询积分策略表
		List<PointRule> pointRuleList = pointRuleService.list();
		map.put("pointRuleList", pointRuleList);
		
		map.put("title", "积分 - " + webInfo.getName());
		map.put("userLink", "spacecp_point");
		
		return "portal/pc/template/" + template + "/user/spacecp/point";
	}
	
	/**
	 * 用户个人信息之修改头像
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("spacecp_avatar.action")
	public String spacecpAvatar(ModelMap map, HttpServletRequest request) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 非法访问，重定向到登录页
			return "redirect:login.action";
		}
		
		map.put("title", "修改头像 - " + webInfo.getName());
		map.put("userLink", "spacecp_avatar");
		
		return "portal/pc/template/" + template + "/user/spacecp/avatar";
	}
	
	/**
	 * 用户个人信息之用户组
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("spacecp_usergroup.action")
	public String spacecpUsergroup(ModelMap map, HttpServletRequest request) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 获取当前登录用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		if (userInfo==null) {
			// 非法访问，重定向到登录页
			return "redirect:login.action";
		}
		
		// 根据用户组id，查询可见分区及其下板块的权限
		List<ZoneInfo> zoneList = zoneInfoService.listShowPermByGroupId(userInfo.getGroupId());
		map.put("zoneList", zoneList);
		
		map.put("title", "用户组 - " + webInfo.getName());
		map.put("userLink", "spacecp_usergroup");
		
		return "portal/pc/template/" + template + "/user/spacecp/usergroup";
	}
	
	/**
	 * 用户个人信息之密码安全
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("spacecp_password.action")
	public String spacecpPassword(ModelMap map, HttpServletRequest request) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 获取当前登录用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		if (userInfo==null) {
			// 非法访问，重定向到登录页
			return "redirect:login.action";
		}
		
		map.put("title", "密码安全 - " + webInfo.getName());
		map.put("userLink", "spacecp_password");
		
		return "portal/pc/template/" + template + "/user/spacecp/password";
	}
	
	/**
	 * 获取邮箱验证码
	 * @param request
	 * @return
	 * @throws QingException
	 */
	@RequestMapping("get_code.json")
	@ResponseBody
	public Result getCode(HttpServletRequest request) throws QingException {

		userInfoService.saveCode(request);
		
		return Result.success();
	}
	
	/**
	 * 用户自己修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @param email
	 * @param identifyingCode
	 * @return
	 * @throws QingException 
	 * @throws ParseException
	 */
	@RequestMapping("update_new_password.json")
	@ResponseBody
	public Result updateNewPassword(
			@RequestParam(value="oldPassword") String oldPassword,
			@RequestParam(value="newPassword") String newPassword,
			@RequestParam(value="email") String email,
			@RequestParam(value="identifyingCode") String identifyingCode) throws QingException, ParseException {

		userInfoService.updateNewPassword(oldPassword, newPassword, email, identifyingCode);
		
		return Result.success();
	}
	
	/**
	 * 用户个人信息之主题和帖子
	 * @param map
	 * @param request
	 * @param type
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("spacecp_thread.action")
	public String spacecpThread(ModelMap map,
			HttpServletRequest request,
			@RequestParam(required=false, value="type") String type,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 获取当前登录用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		if (userInfo==null) {
			// 非法访问，重定向到登录页
			return "redirect:login.action";
		}
		
		if ("reply".equals(type)) {
			// pageHelper分页插件
			// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
			PageHelper.startPage(pageNum, pageSize);
			List<ThreadInfo> list = threadInfoService.listReplyByUserId(userInfo.getId());
			PageInfo<ThreadInfo> pageInfo = new PageInfo<ThreadInfo>(list);
			map.put("pageInfo", pageInfo);
			
			map.put("type", type);
		} else {
			// pageHelper分页插件
			// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
			PageHelper.startPage(pageNum, pageSize);
			List<ThreadInfo> list = threadInfoService.listByUserId(userInfo.getId());
			PageInfo<ThreadInfo> pageInfo = new PageInfo<ThreadInfo>(list);
			map.put("pageInfo", pageInfo);
		}
		
		map.put("title", "帖子 - " + webInfo.getName());
		map.put("userLink", "spacecp_thread");
		
		return "portal/pc/template/" + template + "/user/spacecp/thread";
	}
}
