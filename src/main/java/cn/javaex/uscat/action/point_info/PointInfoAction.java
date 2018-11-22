package cn.javaex.uscat.action.point_info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.service.point_info.PointInfoService;
import cn.javaex.uscat.service.point_rule.PointRuleService;
import cn.javaex.uscat.view.PointInfo;
import cn.javaex.uscat.view.PointRule;
import cn.javaex.uscat.view.Result;

@Controller
@RequestMapping("point_info")
public class PointInfoAction {

	@Autowired
	private PointInfoService pointInfoService;
	@Autowired
	private PointRuleService pointRuleService;
	
	/**
	 * 跳转到积分设置页面
	 */
	@RequestMapping("edit.action")
	public String edit(ModelMap map) {
		// 查询所有
		List<PointInfo> pointList = pointInfoService.list(null);
		map.put("pointList", pointList);
		
		// 查询启用的
		List<PointInfo> pointUseList = pointInfoService.list("1");
		map.put("pointUseList", pointUseList);
		
		// 查询积分策略表
		List<PointRule> pointRuleList = pointRuleService.list();
		map.put("pointRuleList", pointRuleList);
		
		return "admin/point_info/edit";
	}
	
	/**
	 * 保存积分基本设置
	 * @param setArr
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(@RequestParam(value="setArr") String[] setArr) {
		
		pointInfoService.save(setArr);
		
		return Result.success();
	}
}
