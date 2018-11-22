package cn.javaex.uscat.action.point_rule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.service.point_rule.PointRuleService;
import cn.javaex.uscat.view.PointRule;
import cn.javaex.uscat.view.Result;

@Controller
@RequestMapping("point_rule")
public class PointRuleAction {

	@Autowired
	private PointRuleService pointRuleService;
	
	/**
	 * 保存积分策略
	 * @param ruleActionArr 策略行为数组
	 * @param ruleExtcredits1Arr
	 * @param ruleExtcredits2Arr
	 * @param ruleExtcredits3Arr
	 * @param ruleExtcredits4Arr
	 * @param ruleExtcredits5Arr
	 * @param ruleExtcredits6Arr
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(
			@RequestParam(value="ruleActionArr") String[] ruleActionArr,
			@RequestParam(required=false, value="ruleExtcredits1Arr") String[] ruleExtcredits1Arr,
			@RequestParam(required=false, value="ruleExtcredits2Arr") String[] ruleExtcredits2Arr,
			@RequestParam(required=false, value="ruleExtcredits3Arr") String[] ruleExtcredits3Arr,
			@RequestParam(required=false, value="ruleExtcredits4Arr") String[] ruleExtcredits4Arr,
			@RequestParam(required=false, value="ruleExtcredits5Arr") String[] ruleExtcredits5Arr,
			@RequestParam(required=false, value="ruleExtcredits6Arr") String[] ruleExtcredits6Arr) {
		
		List<PointRule> pointRuleList = new ArrayList<PointRule>();
		
		// 遍历行为数组
		for (int i=0; i<ruleActionArr.length; i++) {
			PointRule pointRule = new PointRule();
			pointRule.setAction(ruleActionArr[i]);
			try {
				pointRule.setExtcredits1(ruleExtcredits1Arr[i]);
			} catch (Exception e) {
				pointRule.setExtcredits1("0");
			}
			try {
				pointRule.setExtcredits2(ruleExtcredits2Arr[i]);
			} catch (Exception e) {
				pointRule.setExtcredits2("0");
			}
			try {
				pointRule.setExtcredits3(ruleExtcredits3Arr[i]);
			} catch (Exception e) {
				pointRule.setExtcredits3("0");
			}
			try {
				pointRule.setExtcredits4(ruleExtcredits4Arr[i]);
			} catch (Exception e) {
				pointRule.setExtcredits4("0");
			}
			try {
				pointRule.setExtcredits5(ruleExtcredits5Arr[i]);
			} catch (Exception e) {
				pointRule.setExtcredits5("0");
			}
			try {
				pointRule.setExtcredits6(ruleExtcredits6Arr[i]);
			} catch (Exception e) {
				pointRule.setExtcredits6("0");
			}

			pointRuleList.add(pointRule);
		}
		
		pointRuleService.save(pointRuleList);
		
		return Result.success();
	}
}
