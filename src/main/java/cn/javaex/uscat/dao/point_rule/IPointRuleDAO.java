package cn.javaex.uscat.dao.point_rule;

import java.util.List;

import cn.javaex.uscat.view.PointRule;

public interface IPointRuleDAO {

	/**
	 * 查询积分策略
	 * @return
	 */
	List<PointRule> list();

	/**
	 * 更新积分策略
	 * @param pointRule
	 * @return
	 */
	int update(PointRule pointRule);

	/**
	 * 查询某个行为的积分策略
	 * @param action 行为
	 * @return
	 */
	PointRule selectByAction(String action);
	
}