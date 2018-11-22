package cn.javaex.uscat.action.seo_info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.service.seo_info.SeoInfoService;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.SeoInfo;

@Controller
@RequestMapping("seo_info")
public class SeoInfoAction {

	@Autowired
	private SeoInfoService seoInfoService;
	
	/**
	 * 查询seo信息
	 */
	@RequestMapping("edit.action")
	public String edit(ModelMap map) {
		
		// 查询门户首页的seo信息
		SeoInfo seoPortal = seoInfoService.selectByType("portal");
		map.put("seoPortal", seoPortal);
		
		// 查询论坛首页的SEO信息
		SeoInfo seoForum = seoInfoService.selectByType("forum");
		map.put("seoForum", seoForum);
		
		return "admin/seo_info/edit";
	}
	
	/**
	 * 保存seo信息配置
	 * @param seoInfo
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(SeoInfo seoInfo) {
		
		seoInfoService.save(seoInfo);
		return Result.success();
	}
}
