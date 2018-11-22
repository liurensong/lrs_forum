package cn.javaex.uscat.service.point_rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.dao.point_rule.IPointRuleDAO;
import cn.javaex.uscat.view.PointRule;

@Service("PointRuleService")
public class PointRuleService {
	@Autowired
	private IPointRuleDAO iPointRuleDAO;

	/**
	 * 查询积分策略信息
	 * @return
	 */
	public List<PointRule> list() {
		return iPointRuleDAO.list();
	}

	/**
	 * 更新积分策略
	 * @param pointRuleList
	 */
	public void save(List<PointRule> pointRuleList) {
		for (PointRule pointRule : pointRuleList) {
			iPointRuleDAO.update(pointRule);
		}
	}

}
