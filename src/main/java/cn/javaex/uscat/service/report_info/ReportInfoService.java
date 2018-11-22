package cn.javaex.uscat.service.report_info;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.dao.report_info.IReportInfoDAO;
import cn.javaex.uscat.dao.thread_info.IThreadInfoDAO;
import cn.javaex.uscat.dao.thread_reply_info.IThreadReplyInfoDAO;
import cn.javaex.uscat.view.ReportInfo;
import cn.javaex.uscat.view.ThreadInfo;
import cn.javaex.uscat.view.ThreadReplyInfo;

@Service("ReportInfoService")
public class ReportInfoService {
	@Autowired
	private IReportInfoDAO iReportInfoDAO;
	@Autowired
	private IThreadInfoDAO iThreadInfoDAO;
	@Autowired
	private IThreadReplyInfoDAO iThreadReplyInfoDAO;
	
	/**
	 * 保存用户举报记录
	 * @param reportInfo
	 */
	public void save(ReportInfo reportInfo) {
		iReportInfoDAO.insert(reportInfo);
	}

	/**
	 * 查询用户举报记录
	 * @param param 
	 * @return
	 */
	public List<ReportInfo> list(Map<String, Object> param) {
		return iReportInfoDAO.list(param);
	}

	/**
	 * 批量处理举报记录
	 * @param idArr
	 */
	public void batchDeleteThread(String[] reportIdArr) {
		// 1.0 将帖子更新为屏蔽状态
		// 1.1 取出帖子list
		List<ThreadReplyInfo> threadReplyList = iThreadReplyInfoDAO.listByReportIdArr(reportIdArr);
		if (threadReplyList!=null && threadReplyList.isEmpty()==false) {
			// 1.2 遍历帖子list，并处理
			for (ThreadReplyInfo threadReplyInfo : threadReplyList) {
				if (threadReplyInfo.getFloor()==1) {
					// 1.3 当处理的楼层是1楼时，则直接处理主题
					ThreadInfo threadInfo = new ThreadInfo();
					threadInfo.setTid(threadReplyInfo.getTid());
					threadInfo.setStatus("2");	// 0：待审核；1：正常；2：屏蔽
					iThreadInfoDAO.update(threadInfo);
				} else {
					// 1.4 处理帖子回复
					threadReplyInfo.setStatus("2");	// 0：待审核；1：正常；2：屏蔽
					iThreadReplyInfoDAO.update(threadReplyInfo);
				}
			}
		}
		// 2.0 将举报记录改为已处理状态
		iReportInfoDAO.updateStatus1(reportIdArr);
	}
	
	/**
	 * 批量删除举报记录
	 * @param idArr 举报记录id数组
	 */
	public void batchDeleteReport(String[] idArr) {
		iReportInfoDAO.batchDeleteReport(idArr);
	}

}
