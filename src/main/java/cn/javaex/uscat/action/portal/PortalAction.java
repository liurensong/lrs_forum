package cn.javaex.uscat.action.portal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.javaex.uscat.service.channel_info.ChannelInfoService;
import cn.javaex.uscat.service.download_count.DownloadCountService;
import cn.javaex.uscat.service.nav_info.NavInfoService;
import cn.javaex.uscat.service.seo_info.SeoInfoService;
import cn.javaex.uscat.service.template_info.TemplateInfoService;
import cn.javaex.uscat.service.user_info.UserInfoService;
import cn.javaex.uscat.service.web_info.WebInfoService;
import cn.javaex.uscat.view.ChannelInfo;
import cn.javaex.uscat.view.DownloadCount;
import cn.javaex.uscat.view.NavInfo;
import cn.javaex.uscat.view.SeoInfo;
import cn.javaex.uscat.view.UserInfo;
import cn.javaex.uscat.view.WebInfo;

@Controller
@RequestMapping("portal")
public class PortalAction {

	@Autowired
	private WebInfoService webInfoService;
	@Autowired
	private SeoInfoService seoInfoService;
	@Autowired
	private NavInfoService navInfoService;
	@Autowired
	private TemplateInfoService templateInfoService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private DownloadCountService downloadCountService;
	
	/**
	 * 安装
	 * @param map
	 * @return
	 */
	@RequestMapping("install.action")
	public String install(ModelMap map) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 查询当前用户数量，如果是首次安装，则跳转到注册管理员页面
		int count = userInfoService.countUser(null, null);
		if (count>0) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		
		return "portal/pc/template/" + template + "/install/install";
	}
	
	/**
	 * 跳转门户首页
	 * @return
	 */
	@RequestMapping("index.action")
	public String index(ModelMap map, HttpServletRequest request) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// SEO信息
		// 查询门户首页的SEO信息
		SeoInfo seoInfo = seoInfoService.selectByType("portal");
		map.put("seoInfo", seoInfo);
		// title
		String title = seoInfo.getTitle();
		if (StringUtils.isEmpty(title)) {
			title = webInfo.getName();
		} else {
			// 替换站点名称
			title = title.replace("{bbname}", webInfo.getName());
		}
		map.put("title", title);
		// keywords
		String keywords = seoInfo.getKeywords();
		if (StringUtils.isEmpty(keywords)) {
			keywords = webInfo.getName();
		} else {
			// 替换站点名称
			keywords = keywords.replace("{bbname}", webInfo.getName());
		}
		map.put("keywords", keywords);
		// description
		String description = seoInfo.getDescription();
		if (StringUtils.isEmpty(description)) {
			description = webInfo.getName();
		} else {
			// 替换站点名称
			description = description.replace("{bbname}", webInfo.getName());
		}
		map.put("description", description);
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		map.put("active", "portal/index.action");
		
		// 下载计数器
		for (NavInfo navInfo : navlist) {
			if ("system".equals(navInfo.getType())) {
				if ("portal/index.action".equals(navInfo.getLink())) {
					if ("1".equals(navInfo.getIsDownloadCount())) {
						List<DownloadCount> downloadList = downloadCountService.list();
						map.put("downloadList", downloadList);
					}
				}
			}
		}
		
		return "portal/pc/template/" + template + "/portal/index";
	}
	
	/**
	 * 跳转频道页面
	 * @return
	 */
	@RequestMapping("portal.action")
	public String portal(ModelMap map,
			HttpServletRequest request,
			@RequestParam(value="channelId") String channelId) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 判断合法性
		try {
			Integer.parseInt(channelId);
		} catch (Exception e) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		ChannelInfo channelInfo = channelInfoService.selectById(channelId);
		if (channelInfo==null) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		map.put("channelInfo", channelInfo);
		
		// SEO信息
		// title
		String title = channelInfo.getTitle();
		if (StringUtils.isEmpty(title)) {
			title = webInfo.getName();
		} else {
			// 替换站点名称
			title = title.replace("{bbname}", webInfo.getName());
		}
		map.put("title", title);
		// keywords
		String keywords = channelInfo.getKeywords();
		if (StringUtils.isEmpty(keywords)) {
			keywords = webInfo.getName();
		} else {
			// 替换站点名称
			keywords = keywords.replace("{bbname}", webInfo.getName());
		}
		map.put("keywords", keywords);
		// description
		String description = channelInfo.getDescription();
		if (StringUtils.isEmpty(description)) {
			description = webInfo.getName();
		} else {
			// 替换站点名称
			description = description.replace("{bbname}", webInfo.getName());
		}
		map.put("description", description);
		
		// 下载计数器
		NavInfo navInfo = navInfoService.selectByChannelId(channelId);
		if ("1".equals(navInfo.getIsDownloadCount())) {
			List<DownloadCount> downloadList = downloadCountService.list();
			map.put("downloadList", downloadList);
		}
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		map.put("active", "portal.action?channelId="+channelId);
		
		return "portal/pc/template/" + template + "/channel/" + channelInfo.getTemplate();
	}
}
