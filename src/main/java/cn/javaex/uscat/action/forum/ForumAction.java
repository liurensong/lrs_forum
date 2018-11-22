package cn.javaex.uscat.action.forum;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import cn.javaex.uscat.constant.FixedOption.DefaultOrderField;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.board_group_perm.BoardGroupPermService;
import cn.javaex.uscat.service.board_info.BoardInfoService;
import cn.javaex.uscat.service.channel_info.ChannelInfoService;
import cn.javaex.uscat.service.download_count.DownloadCountService;
import cn.javaex.uscat.service.friend_link.FriendLinkService;
import cn.javaex.uscat.service.nav_info.NavInfoService;
import cn.javaex.uscat.service.report_info.ReportInfoService;
import cn.javaex.uscat.service.seo_info.SeoInfoService;
import cn.javaex.uscat.service.subject_class.SubjectClassService;
import cn.javaex.uscat.service.template_info.TemplateInfoService;
import cn.javaex.uscat.service.thread_info.ThreadInfoService;
import cn.javaex.uscat.service.thread_reply_info.ThreadReplyInfoService;
import cn.javaex.uscat.service.user_info.UserInfoService;
import cn.javaex.uscat.service.user_perm.UserPermService;
import cn.javaex.uscat.service.web_info.WebInfoService;
import cn.javaex.uscat.service.zone_info.ZoneInfoService;
import cn.javaex.uscat.view.BoardInfo;
import cn.javaex.uscat.view.ChannelInfo;
import cn.javaex.uscat.view.DownloadCount;
import cn.javaex.uscat.view.FriendLink;
import cn.javaex.uscat.view.NavInfo;
import cn.javaex.uscat.view.ReportInfo;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.SeoInfo;
import cn.javaex.uscat.view.SubjectClass;
import cn.javaex.uscat.view.ThreadInfo;
import cn.javaex.uscat.view.ThreadReplyInfo;
import cn.javaex.uscat.view.UserInfo;
import cn.javaex.uscat.view.UserPerm;
import cn.javaex.uscat.view.WebInfo;
import cn.javaex.uscat.view.ZoneInfo;

@Controller
@RequestMapping("forum")
public class ForumAction {

	@Autowired
	private WebInfoService webInfoService;
	@Autowired
	private SeoInfoService seoInfoService;
	@Autowired
	private NavInfoService navInfoService;
	@Autowired
	private ZoneInfoService zoneInfoService;
	@Autowired
	private FriendLinkService friendLinkService;
	@Autowired
	private SubjectClassService subjectClassService;
	@Autowired
	private ThreadInfoService threadInfoService;
	@Autowired
	private ThreadReplyInfoService threadReplyInfoService;
	@Autowired
	private BoardInfoService boardInfoService;
	@Autowired
	private TemplateInfoService templateInfoService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ReportInfoService reportInfoService;
	@Autowired
	private UserPermService userPermService;
	@Autowired
	private BoardGroupPermService boardGroupPermService;
	@Autowired
	private DownloadCountService downloadCountService;
	@Autowired
	private ChannelInfoService channelInfoService;
	
	/**
	 * 跳转论坛首页
	 * @return
	 */
	@RequestMapping("index.action")
	public String index(ModelMap map, HttpServletRequest request) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		for (NavInfo navInfo : navlist) {
			// 判断是否是首页
			if ("1".equals(navInfo.getIsIndex())) {
				// 判断该链接是否是系统内置的（或者是频道）
				if ("system".equals(navInfo.getType())) {
					if ("forum/index.action".equals(navInfo.getLink())) {
						// 论坛首页
						map.put("active", "forum/index.action");
						
						// 下载计数器
						if ("1".equals(navInfo.getIsDownloadCount())) {
							List<DownloadCount> downloadList = downloadCountService.list();
							map.put("downloadList", downloadList);
						}
					} else if ("portal/index.action".equals(navInfo.getLink())) {
						// 门户首页
						map.put("active", navInfo.getLink());
						
						// 下载计数器
						if ("1".equals(navInfo.getIsDownloadCount())) {
							List<DownloadCount> downloadList = downloadCountService.list();
							map.put("downloadList", downloadList);
						}
						
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
						
						return "portal/pc/template/" + template + "/portal/index";
					} else {
						// 频道
						String channelId = navInfo.getChannelId();
						map.put("active", "portal/portal.action?channelId="+channelId);
						ChannelInfo channelInfo = channelInfoService.selectById(channelId);
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
						if ("1".equals(navInfo.getIsDownloadCount())) {
							List<DownloadCount> downloadList = downloadCountService.list();
							map.put("downloadList", downloadList);
						}
						return "portal/pc/template/" + template + "/channel/" + channelInfo.getTemplate();
					}
				} else {
					// 自定义链接
					map.put("active", navInfo.getLink());
					return "redirect:"+navInfo.getLink();
				}
			}
		}
		
		// 查询所有可用分区及其下板块信息
		List<ZoneInfo> zoneList = zoneInfoService.listShow();
		map.put("zoneList", zoneList);
		
		// 查询友情链接
		List<FriendLink> friendLinkList = friendLinkService.list();
		map.put("friendLinkList", friendLinkList);
		
		// SEO信息
		// 查询论坛首页的SEO信息
		SeoInfo seoInfo = seoInfoService.selectByType("forum");
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
		
		return "portal/pc/template/" + template + "/forum/index";
	}
	
	/**
	 * 跳转到板块列表页面
	 * @param map
	 * @param gid 分区id
	 * @return
	 */
	@RequestMapping("board_list.action")
	public String boardList(ModelMap map,
			HttpServletRequest request,
			@RequestParam(required=false, value="gid") String gid) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		map.put("active", "forum/index.action");
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 判断参数合法性
		try {
			Integer.parseInt(gid);
			map.put("gid", gid);
		} catch (Exception e) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		
		// 查询分区信息
		ZoneInfo zoneInfo = zoneInfoService.selectInfoById(gid);
		if (zoneInfo==null) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		map.put("zoneInfo", zoneInfo);
		
		// SEO信息
		// title
		String title = zoneInfo.getName();
		if (StringUtils.isEmpty(title)) {
			title = zoneInfo.getName() + " - " + webInfo.getName();
		} else {
			// 替换分区名称
			title = title.replace("{fgroup}", zoneInfo.getName());
			// 替换站点名称
			title = title.replace("{bbname}", webInfo.getName());
		}
		map.put("title", title);
		// keywords
		String keywords = zoneInfo.getKeywords();
		if (StringUtils.isEmpty(keywords)) {
			keywords = zoneInfo.getName() + "," + webInfo.getName();
		} else {
			// 替换分区名称
			keywords = keywords.replace("{fgroup}", zoneInfo.getName());
			// 替换站点名称
			keywords = keywords.replace("{bbname}", webInfo.getName());
		}
		map.put("keywords", keywords);
		// description
		String description = zoneInfo.getDescription();
		if (StringUtils.isEmpty(description)) {
			description = zoneInfo.getName() + "," + webInfo.getName();
		} else {
			// 替换分区名称
			description = description.replace("{fgroup}", zoneInfo.getName());
			// 替换站点名称
			description = description.replace("{bbname}", webInfo.getName());
		}
		map.put("description", description);
		
		// 根据分区id，查询分区下可见的板块信息
		List<BoardInfo> boardList = boardInfoService.listShowByGid(gid);
		map.put("boardList", boardList);
		
		return "portal/pc/template/" + template + "/forum/board_list";
	}
	
	/**
	 * 跳转到帖子列表页面
	 * @param map
	 * @param digest 是否精华帖
	 * @param fid 板块id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("forumdisplay.action")
	public String forumdisplay(ModelMap map,
			HttpServletRequest request,
			@RequestParam(required=false, value="digest") String digest,
			@RequestParam(required=false, value="fid") String fid,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		map.put("active", "forum/index.action");
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 查询该板块信息
		// 判断参数合法性
		try {
			Integer.parseInt(fid);
			map.put("fid", fid);
		} catch (Exception e) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		BoardInfo boardInfo = boardInfoService.selectInfoById(fid);
		if (boardInfo==null) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		map.put("boardInfo", boardInfo);
		
		// 查询该板块对应的分区信息
		ZoneInfo zoneInfo = zoneInfoService.selectInfoById(boardInfo.getGid());
		map.put("zoneInfo", zoneInfo);
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		map.put("userInfo", userInfo);
		// 判断当前用户组有没有权限
		if ("0".equals(boardInfo.getIsDefaultPerm())) {
			// 不走默认权限
			boolean flag = boardGroupPermService.checkPerm(boardInfo.getFid(), userInfo, "is_view_board");
			if (!flag) {
				// 权限不足
				if (userInfo==null) {
					// 游客，重定向到登录页
					return "redirect:user_info/login.action";
				} else {
					// 会员用户组，跳转到403，权限不足页面
					return "portal/pc/template/" + template + "/error/403";
				}
			}
		}
		
		// SEO信息
		// title
		String title = boardInfo.getTitle();
		if (StringUtils.isEmpty(title)) {
			title = boardInfo.getName() + " - " + zoneInfo.getName() + " - " + webInfo.getName();
		} else {
			// 替换当前版块名称
			title = title.replace("{forum}", boardInfo.getName());
			// 替换当前版块简介
			title = title.replace("{fremark}", boardInfo.getRemark());
			// 替换分区名称
			title = title.replace("{fgroup}", zoneInfo.getName());
			// 替换站点名称
			title = title.replace("{bbname}", webInfo.getName());
		}
		map.put("title", title);
		// keywords
		String keywords = boardInfo.getKeywords();
		if (StringUtils.isEmpty(keywords)) {
			keywords = boardInfo.getName() + "," + boardInfo.getRemark() + "," + zoneInfo.getName() + "," + webInfo.getName();
		} else {
			// 替换当前版块名称
			keywords = keywords.replace("{forum}", boardInfo.getName());
			// 替换当前版块简介
			keywords = keywords.replace("{fremark}", boardInfo.getRemark());
			// 替换分区名称
			keywords = keywords.replace("{fgroup}", zoneInfo.getName());
			// 替换站点名称
			keywords = keywords.replace("{bbname}", webInfo.getName());
		}
		map.put("keywords", keywords);
		// description
		String description = boardInfo.getDescription();
		if (StringUtils.isEmpty(description)) {
			description = boardInfo.getName() + "," + boardInfo.getRemark() + "," + zoneInfo.getName() + "," + webInfo.getName();
		} else {
			// 替换当前版块名称
			description = description.replace("{forum}", boardInfo.getName());
			// 替换当前版块简介
			description = description.replace("{fremark}", boardInfo.getRemark());
			// 替换分区名称
			description = description.replace("{fgroup}", zoneInfo.getName());
			// 替换站点名称
			description = description.replace("{bbname}", webInfo.getName());
		}
		map.put("description", description);
		
		// 查询置顶主题（按最后编辑时间倒序排列）
		List<ThreadInfo> topList = threadInfoService.listTopByForumdisplay(fid);
		map.put("topList", topList);
		
		// 查询精华主题（按最后编辑时间倒序排列）
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<ThreadInfo> digestList = threadInfoService.listDigestByForumdisplay(fid);
		PageInfo<ThreadInfo> digestPageInfo = new PageInfo<ThreadInfo>(digestList);
		map.put("digestPageInfo", digestPageInfo);
		
		// 获取该板块下的主题分类
		List<SubjectClass> subjectClassList = subjectClassService.listByFid(fid);
		map.put("subjectClassList", subjectClassList);
		
		// 默认排序字段选项
		String defaultOrderField = boardInfo.getDefaultOrderField();
		if (StringUtils.isEmpty(defaultOrderField)) {
			defaultOrderField = "reply_time";
		}
		map.put("defaultOrderField", defaultOrderField);
		List<Map<String, String>> defaultOrderFieldList = new ArrayList<Map<String, String>>();
		Map<String, String> fieldMap = null;
		for (DefaultOrderField opt : DefaultOrderField.values()) {
			fieldMap = new HashMap<String, String>();
			fieldMap.put("value", opt.getValue());
			fieldMap.put("name", opt.getName());
			defaultOrderFieldList.add(fieldMap);
			
			if (opt.getValue().equals(defaultOrderField)) {
				map.put("defaultOrderFieldName", opt.getName());
			}
		}
		map.put("defaultOrderFieldList", defaultOrderFieldList);
		
		// 根据是否精华帖翻页，来决定tab切换的默认索引
		if (StringUtils.isEmpty(digest)) {
			map.put("tabIndex", "1");
		} else {
			map.put("tabIndex", "2");
		}
		
		return "portal/pc/template/" + template + "/forum/forumdisplay";
	}
	
	/**
	 * 用过ajax请求非置顶帖子数据
	 * @param digest
	 * @param fid
	 * @param subjectId
	 * @param defaultOrderField
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("forumdisplay.json")
	@ResponseBody
	public Result forumDisplayJson(
			@RequestParam(required=false, value="digest") String digest,
			@RequestParam(required=false, value="fid") String fid,
			@RequestParam(required=false, value="subjectId") String subjectId,
			@RequestParam(required=false, value="defaultOrderField") String defaultOrderField,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="20") int pageSize) {
		
		// 查询板块信息
		BoardInfo boardInfo = boardInfoService.selectById(fid);
		
		// 获取该板块下的帖子
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("digest", digest);
		param.put("fid", fid);
		param.put("subjectId", subjectId);
		if (StringUtils.isEmpty(defaultOrderField)) {
			defaultOrderField = boardInfo.getDefaultOrderField();
			if (StringUtils.isEmpty(defaultOrderField)) {
				defaultOrderField = "reply_time";
			}
		}
		param.put("defaultOrderField", defaultOrderField);
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<ThreadInfo> list = threadInfoService.listByForumdisplay(param);
		PageInfo<ThreadInfo> pageInfo = new PageInfo<ThreadInfo>(list);
		
		return Result.success().add("pageInfo", pageInfo);
	}
	
	/**
	 * 跳转到发布新帖子页面
	 * @param map
	 * @param request
	 * @param fid 板块id
	 * @return
	 */
	@RequestMapping("newthread.action")
	public String newthread(ModelMap map,
			HttpServletRequest request,
			@RequestParam(value="fid") String fid) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		map.put("active", "forum/index.action");
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 判断参数合法性
		try {
			Integer.parseInt(fid);
			map.put("fid", fid);
		} catch (Exception e) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		
		// 查询该板块信息
		BoardInfo boardInfo = boardInfoService.selectById(fid);
		if (boardInfo==null) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		map.put("boardInfo", boardInfo);
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 未登录，重定向到登录页面
			return "redirect:user_info/login.action";
		} else {
			// 判断当前用户组有没有权限
			if ("0".equals(boardInfo.getIsDefaultPerm())) {
				// 不走默认权限
				boolean flag = boardGroupPermService.checkPerm(boardInfo.getFid(), userInfo, "is_post");
				if (!flag) {
					// 会员用户组，跳转到403，权限不足页面
					return "portal/pc/template/" + template + "/error/403";
				}
			}
		}
		
		// 查询该板块对应的分区信息
		ZoneInfo zoneInfo = zoneInfoService.selectById(boardInfo.getGid());
		map.put("zoneInfo", zoneInfo);
		
		// 获取该板块下的主题分类
		List<SubjectClass> subjectClassList = subjectClassService.listByFid(fid);
		map.put("subjectClassList", subjectClassList);
		
		// SEO信息
		// title
		String title = boardInfo.getName() + " - " + zoneInfo.getName() + " - " + webInfo.getName();
		map.put("title", title);
		// keywords
		String keywords = boardInfo.getName() + "," + boardInfo.getRemark() + "," + zoneInfo.getName() + "," + webInfo.getName();
		map.put("keywords", keywords);
		// description
		String description = boardInfo.getName() + "," + boardInfo.getRemark() + "," + zoneInfo.getName() + "," + webInfo.getName();
		map.put("description", description);
		
		return "portal/pc/template/" + template + "/forum/newthread";
	}
	
	/**
	 * 发布新帖子
	 * @param fid 板块id
	 * @param subjectId 主题id
	 * @param title 帖子标题
	 * @param contentHtml 帖子内容（包含html代码）
	 * @param contentText 帖子简介（纯文本）
	 * @return
	 * @throws QingException
	 * @throws ParseException 
	 */
	@RequestMapping("save_newthread.json")
	@ResponseBody
	public Result saveNewthread(
			HttpServletRequest request,
			@RequestParam(value="fid") String fid,
			@RequestParam(required=false, value="subjectId") String subjectId,
			@RequestParam(value="title") String title,
			@RequestParam(value="contentHtml") String contentHtml,
			@RequestParam(value="contentText") String contentText) throws QingException, ParseException {
		
		// 校验标题长度
		if (title.length()>80) {
			throw new QingException(ErrorMsg.ERROR_600004);
		}
		
		BoardInfo boardInfo = boardInfoService.selectById(fid);
		if (boardInfo==null) {
			throw new QingException(ErrorMsg.ERROR_300003);
		}
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 未登录
			throw new QingException(ErrorMsg.ERROR_100012);
		} else {
			// 判断当前用户组有没有权限
			if ("0".equals(boardInfo.getIsDefaultPerm())) {
				// 不走默认权限
				boolean flag = boardGroupPermService.checkPerm(boardInfo.getFid(), userInfo, "is_post");
				if (!flag) {
					throw new QingException(ErrorMsg.ERROR_600007);
				}
			}
		}
		
		// 主题
		ThreadInfo threadInfo = new ThreadInfo();
		threadInfo.setFid(fid);
		threadInfo.setSubjectId(subjectId);
		threadInfo.setTitle(title);
		threadInfo.setCreateUserId(userInfo.getId());
		
		// 帖子回复表
		ThreadReplyInfo threadReplyInfo = new ThreadReplyInfo();
		threadReplyInfo.setContentHtml(contentHtml);
		threadReplyInfo.setContentText(contentText);
		threadReplyInfo.setReplyUserId(userInfo.getId());
		
		threadInfoService.save(userInfo, threadInfo, threadReplyInfo);
		
		return Result.success();
	}
	
	/**
	 * 跳转到帖子内容页面
	 * @param map
	 * @param tid 主题id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("viewthread.action")
	public String viewthread(ModelMap map,
			HttpServletRequest request,
			@RequestParam(required=false, value="tid") String tid,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) throws IllegalAccessException, InvocationTargetException {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		map.put("active", "forum/index.action");
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 判断参数合法性
		try {
			Integer.parseInt(tid);
			map.put("tid", tid);
		} catch (Exception e) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		
		// 根据主题id，获取主题信息并更新浏览次数
		ThreadInfo threadInfo = threadInfoService.updateThreadViewByTid(tid);
		if (threadInfo==null) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		map.put("threadInfo", threadInfo);
		
		// 查询该板块信息
		BoardInfo boardInfo = boardInfoService.selectById(threadInfo.getFid());
		map.put("boardInfo", boardInfo);
		
		// 查询该板块对应的分区信息
		ZoneInfo zoneInfo = zoneInfoService.selectById(boardInfo.getGid());
		map.put("zoneInfo", zoneInfo);
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		// 判断当前用户组有没有权限
		if ("0".equals(boardInfo.getIsDefaultPerm())) {
			// 不走默认权限
			boolean flag = boardGroupPermService.checkPerm(boardInfo.getFid(), userInfo, "is_view_board");
			if (!flag) {
				// 权限不足
				if (userInfo==null) {
					// 游客，重定向到登录页
					return "redirect:user_info/login.action";
				} else {
					// 会员用户组，跳转到403，权限不足页面
					return "portal/pc/template/" + template + "/error/403";
				}
			}
		}
		// 设置用户组的权限
		if (userInfo!=null) {
			map.put("userInfo", userInfo);
			UserPerm userPerm = userPermService.setUserPerm(boardInfo.getFid(), userInfo);
			userInfo.setUserPerm(userPerm);
		}
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<ThreadReplyInfo> list = threadReplyInfoService.listByTid(tid);
		PageInfo<ThreadReplyInfo> pageInfo = new PageInfo<ThreadReplyInfo>(list);
		map.put("pageInfo", pageInfo);
		
		// SEO信息
		// title
		String title = threadInfo.getTitle() + " - " + boardInfo.getName() + " - " + zoneInfo.getName() + " - " + webInfo.getName();
		map.put("title", title);
		// keywords
		String keywords = threadInfo.getTitle();
		map.put("keywords", keywords);
		// description
		String threadContentText = list.get(0).getContentText();
		// 删除普通标签
		threadContentText = threadContentText.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
		// 删除转义字符
		threadContentText = threadContentText.replaceAll("&.{2,6}?;", "");
		String description = threadContentText + "," + boardInfo.getName() + "," + boardInfo.getRemark() + "," + zoneInfo.getName() + "," + webInfo.getName();
		map.put("description", description);
		
		return "portal/pc/template/" + template + "/forum/viewthread";
	}
	
	/**
	 * 打开回复楼层页面
	 * @param tid 帖子id
	 * @param replyId 回复楼层记录的id
	 * @return
	 */
	@RequestMapping("reply_floor.action")
	public String replyFloor(ModelMap map,
			HttpServletRequest request,
			@RequestParam(value="tid") String tid,
			@RequestParam(value="replyId") String replyId) {
		map.put("tid", tid);
		map.put("replyId", replyId);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		
		// 查询权限
		ThreadInfo threadInfo = threadInfoService.selectByTid(tid);
		if (threadInfo==null) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		
		BoardInfo boardInfo = boardInfoService.selectById(threadInfo.getFid());
		if (boardInfo==null) {
			// 404
			return "portal/pc/template/" + template + "/error/404";
		}
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 游客，重定向到登录页
			return "redirect:user_info/login.action";
		} else {
			// 判断当前用户组有没有权限
			if ("0".equals(boardInfo.getIsDefaultPerm())) {
				// 不走默认权限
				boolean flag = boardGroupPermService.checkPerm(boardInfo.getFid(), userInfo, "is_reply");
				if (!flag) {
					// 会员用户组，跳转到403，权限不足页面
					return "portal/pc/template/" + template + "/error/403";
				}
			}
		}
		
		// 如果是回复1楼（楼主），则等同于直接回复帖子
		// 如果不是回复楼主，则需要查询当前被回复的楼层的的简介内容（纯文本）
		if (!StringUtils.isEmpty(replyId)) {
			// 根据帖子回复表的主键，查询当前楼层的回复内容
			ThreadReplyInfo threadReplyInfo = threadReplyInfoService.selectById(replyId);
			map.put("threadReplyInfo", threadReplyInfo);
		}
		
		return "portal/pc/template/" + template + "/forum/reply_floor";
	}
	
	/**
	 * 跳转到编辑回复页面
	 * @param map
	 * @param request
	 * @param fid 板块id
	 * @return
	 */
	@RequestMapping("editthread.action")
	public String editthread(ModelMap map,
			@RequestParam(value="tid") String tid,
			@RequestParam(value="replyId") String replyId) {
		map.put("tid", tid);
		map.put("replyId", replyId);
		
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		map.put("active", "forum/index.action");
		
		// 根据主题id，获取帖子信息
		ThreadInfo threadInfo = threadInfoService.selectByTid(tid);
		map.put("threadInfo", threadInfo);
		// 获取楼主发布的主题或者是回复的楼层内容
		ThreadReplyInfo threadReplyInfo = threadReplyInfoService.selectById(replyId);
		map.put("threadReplyInfo", threadReplyInfo);
		
		// 查询该板块信息
		BoardInfo boardInfo = boardInfoService.selectById(threadInfo.getFid());
		map.put("boardInfo", boardInfo);
		
		// 查询该板块对应的分区信息
		ZoneInfo zoneInfo = zoneInfoService.selectById(boardInfo.getGid());
		map.put("zoneInfo", zoneInfo);
		
		// 获取该板块下的主题分类
		List<SubjectClass> subjectClassList = subjectClassService.listByFid(threadInfo.getFid());
		map.put("subjectClassList", subjectClassList);
		
		// SEO信息
		// title
		String title = threadInfo.getTitle() + " - " + boardInfo.getName() + " - " + zoneInfo.getName() + " - " + webInfo.getName();
		map.put("title", title);
		// keywords
		String keywords = threadInfo.getTitle();
		map.put("keywords", keywords);
		// description
		String description = boardInfo.getName() + "," + boardInfo.getRemark() + "," + zoneInfo.getName() + "," + webInfo.getName();
		map.put("description", description);
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		return "portal/pc/template/" + template + "/forum/editthread";
	}
	
	/**
	 * 保存帖子/回复编辑
	 * @param tid 帖子id
	 * @param replyId 回复记录id
	 * @param subjectId 主题id
	 * @param title 帖子标题
	 * @param contentHtml 帖子内容（包含html代码）
	 * @param contentText 帖子简介（纯文本）
	 * @return
	 * @throws QingException
	 * @throws ParseException 
	 */
	@RequestMapping("save_editthread.json")
	@ResponseBody
	public Result saveEditthread(
			HttpServletRequest request,
			@RequestParam(value="tid") String tid,
			@RequestParam(value="replyId") String replyId,
			@RequestParam(required=false, value="subjectId") String subjectId,
			@RequestParam(value="title") String title,
			@RequestParam(value="contentHtml") String contentHtml,
			@RequestParam(value="contentText") String contentText) throws QingException, ParseException {
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 未登录
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		
		// 主题
		ThreadInfo threadInfo = new ThreadInfo();
		threadInfo.setTid(tid);
		
		// 帖子回复表
		// 根据回帖id，获取回帖记录信息
		ThreadReplyInfo threadReplyInfo = threadReplyInfoService.selectById(replyId);
		// 判断楼层
		if (threadReplyInfo.getFloor()==1) {
			// 楼主编辑帖子主题
			// 主题
			threadInfo.setSubjectId(subjectId);
			threadInfo.setTitle(title);
			
			// 帖子回复表
			threadReplyInfo.setId(replyId);
			threadReplyInfo.setContentHtml(contentHtml);
			threadReplyInfo.setContentText(contentText);
		} else {
			// 编辑楼层的回复内容
			// 帖子回复表
			threadReplyInfo.setId(replyId);
			threadReplyInfo.setContentHtml(contentHtml);
			threadReplyInfo.setContentText(contentText);
		}
		
		threadInfoService.save(userInfo, threadInfo, threadReplyInfo);
		
		return Result.success();
	}
	
	/**
	 * 发表新回复
	 * @param tid 帖子id
	 * @param replyId 回复楼层记录的id
	 * @param contentHtml
	 * @param contentText
	 * @return
	 * @throws QingException
	 * @throws ParseException
	 */
	@RequestMapping("save_thread_reply.json")
	@ResponseBody
	public Result saveThreadReply(
			HttpServletRequest request,
			@RequestParam(value="tid") String tid,
			@RequestParam(required=false, value="replyId") String replyId,
			@RequestParam(value="contentHtml") String contentHtml,
			@RequestParam(value="contentText") String contentText) throws QingException, ParseException {
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 未登录
			throw new QingException(ErrorMsg.ERROR_100012);
		} else {
			ThreadInfo threadInfo = threadInfoService.selectByTid(tid);
			if (threadInfo==null) {
				throw new QingException(ErrorMsg.ERROR_120007);
			}
			BoardInfo boardInfo = boardInfoService.selectById(threadInfo.getFid());
			if (boardInfo==null) {
				throw new QingException(ErrorMsg.ERROR_120007);
			}
			// 判断当前用户组有没有权限
			if ("0".equals(boardInfo.getIsDefaultPerm())) {
				// 不走默认权限
				boolean flag = boardGroupPermService.checkPerm(boardInfo.getFid(), userInfo, "is_reply");
				if (!flag) {
					// 权限不足
					throw new QingException(ErrorMsg.ERROR_600007);
				}
			}
		}
		
		// 帖子回复表
		ThreadReplyInfo threadReplyInfo = new ThreadReplyInfo();
		threadReplyInfo.setTid(tid);
		threadReplyInfo.setBeReplyId(replyId);
		threadReplyInfo.setContentHtml(contentHtml);
		threadReplyInfo.setContentText(contentText);
		threadReplyInfo.setReplyUserId(userInfo.getId());
		
		threadInfoService.saveThreadReply(userInfo, threadReplyInfo);
		
		return Result.success();
	}
	
	/**
	 * 用户举报楼层
	 * @param request
	 * @param replyId 回帖id（包括1楼的）
	 * @param reportContent 举报内容
	 * @return
	 * @throws QingException
	 */
	@RequestMapping("save_report_floor.json")
	@ResponseBody
	public Result saveReportFloor(
			HttpServletRequest request,
			@RequestParam(value="replyId") String replyId,
			@RequestParam(required=false, value="reportContent") String reportContent) throws QingException {
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 未登录
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		
		ReportInfo reportInfo = new ReportInfo();
		reportInfo.setReplyId(replyId);
		reportInfo.setReportContent(reportContent);
		reportInfo.setReportUserId(userInfo.getId());
		reportInfo.setStatus("0");
		reportInfoService.save(reportInfo);
		
		return Result.success();
	}
	
	/**
	 * 删除楼层（回收站）
	 * @param request
	 * @param replyId 回帖id
	 * @return
	 * @throws QingException
	 */
	@RequestMapping("delete_floor.json")
	@ResponseBody
	public Result deleteFloor(
			HttpServletRequest request,
			@RequestParam(value="replyId") String replyId) throws QingException {
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 未登录
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		
		threadReplyInfoService.deleteFloor(replyId, userInfo);
		
		return Result.success();
	}
	
	/**
	 * 管理组操作主题
	 * @param request
	 * @param tid 主题id
	 * @param optSelect 操作选项
	 * @return
	 * @throws QingException
	 */
	@RequestMapping("update_thread_status.json")
	@ResponseBody
	public Result updateThreadStatus(
			HttpServletRequest request,
			@RequestParam(value="tid") String tid,
			@RequestParam(value="optSelect") String optSelect) throws QingException {
		
		// 获取用户信息
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 未登录
			throw new QingException(ErrorMsg.ERROR_100012);
		} else {
			// 权限判断
			ThreadInfo threadInfo = threadInfoService.selectByTid(tid);
			if (threadInfo==null) {
				throw new QingException(ErrorMsg.ERROR_120007);
			}
			BoardInfo boardInfo = boardInfoService.selectById(threadInfo.getFid());
			UserPerm userPerm = userPermService.setUserPerm(boardInfo.getFid(), userInfo);
			if (!"1".equals(userPerm.getIsAdmin())) {
				throw new QingException(ErrorMsg.ERROR_600007);
			}
		}
		
		threadInfoService.updateThreadStatus(tid, optSelect, userInfo);
		
		return Result.success();
	}
	
	/**
	 * 搜索
	 * @param map
	 * @param keyword
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("search.action")
	public String search(ModelMap map,
			@RequestParam(required=false, value="keyword") String keyword,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="20") int pageSize) {
		// 站点信息
		WebInfo webInfo = webInfoService.select();
		map.put("webInfo", webInfo);
		
		// 获取可用导航
		List<NavInfo> navlist = navInfoService.listIsUse();
		map.put("navlist", navlist);
		map.put("active", "forum/index.action");
		
		// 获取所选模板
		String template = templateInfoService.selectNameByType("pc");
		// SEO标题
		map.put("title", "搜索 - " + webInfo.getName());
		
		if (!StringUtils.isEmpty(keyword)) {
			// pageHelper分页插件
			// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
			PageHelper.startPage(pageNum, pageSize);
			List<ThreadInfo> list = threadInfoService.listBySearch(keyword);
			PageInfo<ThreadInfo> pageInfo = new PageInfo<ThreadInfo>(list);
			map.put("pageInfo", pageInfo);
		}
		
		map.put("keyword", keyword);
		return "portal/pc/template/" + template + "/forum/search";
	}
}
