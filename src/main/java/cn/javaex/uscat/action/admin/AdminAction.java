package cn.javaex.uscat.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.user_info.UserInfoService;
import cn.javaex.uscat.util.MD5;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.UserInfo;

@Controller
@RequestMapping("admin")
public class AdminAction {

	@Autowired
	private UserInfoService userInfoService;

	// 登录页面显示
	@RequestMapping("login.action")
	public String login() {
		return "admin/login";
	}
	
	/**
	 * 管理员登录后台
	 * @throws Exception 
	 */
	@RequestMapping("login.json")
	@ResponseBody
	public Result login(HttpServletRequest request) throws Exception {
		
		// 1.0 获取登录参数
		String loginName = request.getParameter("login_name");
		String password = request.getParameter("password");
		
		// 2.0 校验用户
		// 2.1 校验用户名或密码是否填写
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
			throw new QingException(ErrorMsg.ERROR_100001);
		}
		
		// 2.2 校验用户名、密码是否正确
		UserInfo userInfo = userInfoService.selectUser(loginName, MD5.md5(password));
		if (userInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100002);
		}
		
		// 2.3 校验是否是管理员
		if (!"管理员".equals(userInfo.getGroupName())) {
			throw new QingException(ErrorMsg.ERROR_100002);
		}
		
		// 3.0 校验成功，设置session
		request.getSession().setAttribute("userInfo", userInfo);
		
		return Result.success();
	}
	
	/**
	 * 清除session
	 */
	@RequestMapping("logout.action")
	public String logout(HttpSession session) {
		// 清除session
		session.invalidate();
		
		return "redirect:login.action";
	}

	/**
	 * 管理中心首页
	 */
	@RequestMapping("center.action")
	public String center(ModelMap map) {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>程序版本：</p></div>");
		sb.append("	<div class='right'>uscat 1.0</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>作者：</p></div>");
		sb.append("	<div class='right'>陈霓清</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>联系方式：</p></div>");
		sb.append("	<div class='right'>QQ：291026192</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>官网：</p></div>");
		sb.append("	<div class='right'><a href='http://www.javaex.cn/' target='_blank'>http://www.javaex.cn/</a></div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>版权所有：</p></div>");
		sb.append("	<div class='right'>陈霓清</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		
		map.put("info", sb.toString());
		
		return "admin/index";
	}
	
	/**
	 * 收费服务
	 */
	@RequestMapping("service.action")
	public String service(ModelMap map) {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>功能定制：</p></div>");
		sb.append("	<div class='right'>1000起</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>模板定制：</p></div>");
		sb.append("	<div class='right'>5000起/套，500起/单页</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>网站定制：</p></div>");
		sb.append("	<div class='right'>接受任何类型的项目定制，价格详谈</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		sb.append("<div class='unit'>");
		sb.append("	<div class='left'><p class='subtitle'>联系方式：</p></div>");
		sb.append("	<div class='right'>陈霓清，QQ：291026192</div>");
		sb.append("	<span class='clearfix'></span>");
		sb.append("</div>");
		
		map.put("info", sb.toString());
		
		return "admin/service/service";
	}
	
	/**
	 * 首页
	 */
	@RequestMapping("index.action")
	public String index() {
		return "admin/menu/index";
	}
	
	/**
	 * 全局
	 */
	@RequestMapping("basic.action")
	public String basic() {
		return "admin/menu/basic";
	}
	
	/**
	 * 界面
	 */
	@RequestMapping("layout.action")
	public String layout() {
		return "admin/menu/layout";
	}
	
	/**
	 * 内容
	 */
	@RequestMapping("content.action")
	public String content() {
		return "admin/menu/content";
	}
	
	/**
	 * 用户
	 */
	@RequestMapping("user.action")
	public String user() {
		return "admin/menu/user";
	}
	
	/**
	 * 论坛
	 */
	@RequestMapping("forum.action")
	public String forum() {
		return "admin/menu/forum";
	}
	
	/**
	 * 防灌水
	 */
	@RequestMapping("security.action")
	public String security() {
		return "admin/menu/security";
	}
	
	/**
	 * 运营
	 */
	@RequestMapping("announce.action")
	public String announce() {
		return "admin/menu/announce";
	}
	
	/**
	 * 接口
	 */
	@RequestMapping("api.action")
	public String api() {
		return "admin/menu/api";
	}
	
	/**
	 * 站长
	 */
	@RequestMapping("founder.action")
	public String founder() {
		return "admin/menu/founder";
	}
}
