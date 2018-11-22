package cn.javaex.uscat.task;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import cn.javaex.uscat.dao.thread_info.IThreadInfoDAO;
import cn.javaex.uscat.dao.user_count_log.IUserCountLogDAO;
import cn.javaex.uscat.view.ThreadInfo;

/**
 * Quartz定时任务类
 */
public class Quartz {
	
	@Autowired
	private IThreadInfoDAO iThreadInfoDAO;
	@Autowired
	private IUserCountLogDAO iUserCountLogDAO;
	
	/**
	 * 定时任务
	 */
	public void execute() {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		
		// 1.0 清空帖子浏览量
		clearThreadViewCount(calendar);
		
		// 2.0 清空用户奖励日志
		clearUserCountLog(calendar);
	}

	/**
	 * 清空帖子浏览量
	 * @param calendar
	 */
	private void clearThreadViewCount(Calendar calendar) {
		ThreadInfo threadInfo = new ThreadInfo();
		// 1.0 清空帖子的今日浏览量
		threadInfo.setViewCountDay("0");
		
		// 2.0 星期一，清空帖子的周浏览量
		int nWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
		if (nWeek==1) {
			threadInfo.setViewCountWeek("0");
		}

		// 3.0 每月1号，清空帖子的月浏览量
		int nMonth = calendar.get(Calendar.DAY_OF_MONTH);
		if (nMonth==1) {
			threadInfo.setViewCountMonth("0");
		}
		
		iThreadInfoDAO.clearViewCount(threadInfo);
	}
	
	/**
	 * 清空用户奖励日志
	 * @param calendar
	 */
	private void clearUserCountLog(Calendar calendar) {
		// 1.0 清空每天的
		iUserCountLogDAO.clearByCycle("每天");
		
		// 2.0 星期一，清空每周的
		int nWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
		if (nWeek==1) {
			iUserCountLogDAO.clearByCycle("每周");
		}
	}
}
