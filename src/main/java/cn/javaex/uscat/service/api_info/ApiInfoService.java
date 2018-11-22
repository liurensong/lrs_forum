package cn.javaex.uscat.service.api_info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.dao.api_info.IApiInfoDAO;
import cn.javaex.uscat.dao.thread_info.IThreadInfoDAO;
import cn.javaex.uscat.dao.thread_reply_info.IThreadReplyInfoDAO;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.view.ApiInfo;
import cn.javaex.uscat.view.ThreadInfo;
import cn.javaex.uscat.view.ThreadReplyInfo;

@Service("ApiInfoService")
public class ApiInfoService {
	@Autowired
	private IApiInfoDAO iApiInfoDAO;
	@Autowired
	private IThreadInfoDAO iThreadInfoDAO;
	@Autowired
	private IThreadReplyInfoDAO iThreadReplyInfoDAO;
	
	/**
	 * 查询指定类型的接口列表
	 * @param type 接口类型
	 * @return
	 */
	public List<ApiInfo> listByType(String type) {
		return iApiInfoDAO.listByType(type);
	}
	
	/**
	 * 保存接口
	 * @param slideInfoList
	 */
	public void save(List<ApiInfo> slideInfoList) {
		for (ApiInfo slideInfo : slideInfoList) {
			if (StringUtils.isEmpty(slideInfo.getId())) {
				// 插入
				iApiInfoDAO.insert(slideInfo);
			} else {
				// 更新
				iApiInfoDAO.update(slideInfo);
			}
		}
	}
	
	/**
	 * 删除接口
	 * @param idArr 接口主键数组
	 */
	public void delete(String[] idArr) {
		// 批量删除接口
		iApiInfoDAO.delete(idArr);
	}

	/**
	 * 根据接口id，查询该接口的信息
	 * @param id
	 * @return
	 */
	public ApiInfo selectById(String id) {
		return iApiInfoDAO.selectById(id);
	}

	/**
	 * 更新接口设置
	 * @param apiInfo
	 */
	public void update(ApiInfo apiInfo) {
		iApiInfoDAO.update(apiInfo);
	}

	/**
	 * 根据自定义接口查询数据
	 * @param id
	 * @return
	 * @throws QingException 
	 */
	public List<ThreadInfo> listDataById(String id) throws QingException {
		Map<String, Object> param = new HashMap<String, Object>();
		List<ThreadInfo> list = null;
		
		// 1.0 获取接口数据
		ApiInfo apiInfo = iApiInfoDAO.selectById(id);
		if (apiInfo==null || !"user".equals(apiInfo.getType())) {
			throw new QingException(ErrorMsg.ERROR_400002);
		}
		
		// 2.0 判断数据来源
		if (StringUtils.isEmpty(apiInfo.getFid())) {
			throw new QingException(ErrorMsg.ERROR_400002);
		}
		String[] fidArr = null;
		if (!"0".equals(apiInfo.getFid())) {
			fidArr = apiInfo.getFid().split(",");
		}
		param.put("fidArr", fidArr);
		
		// 3.0 查询条数
		param.put("num", apiInfo.getNum());
		
		// 4.0 判断排序字段和周期
		String orderField = apiInfo.getOrderField();
		param.put("orderField", orderField);
		param.put("cycle", apiInfo.getCycle());
		if ("create_time".equals(orderField) || "view_count".equals(orderField)) {
			// 1.1 如果是按主题创建的时间或浏览次数排序，则直接率先查询主题
			list = iThreadInfoDAO.apiListByOrderField(param);
		} else if ("reply_time".equals(orderField)) {
			// 1.2 如果是按回复时间排序，则需要联合查询回帖表的数据
			list = iThreadInfoDAO.apiListByReplyTime(param);
		} else if ("reply_count".equals(orderField)) {
			// 1.3 如果是按回复数量排序，则需要联合查询回帖表的数据
			list = iThreadInfoDAO.apiListByReplyCount(param);
		}
		
		// 5.0 查询主题的附加信息
		if (list!=null && list.isEmpty()==false) {
			// 5.1 遍历这些主题，并查询关联的帖子回复表中的记录
			for (ThreadInfo threadInfo : list) {
				String tid = threadInfo.getTid();
				// 5.2 根据主题id，查询帖子回复表中的第一条记录（即主题摘要）
				String contentText = iThreadReplyInfoDAO.selectPostContentByTid(tid);
				threadInfo.setContentText(contentText);
				
				// 5.3 根据主题id，查询帖子回复表中的最新一条记录
				ThreadReplyInfo threadReplyInfo = iThreadReplyInfoDAO.selectLastByTid(tid);
				threadInfo.setThreadReplyInfo(threadReplyInfo);
				
				// 5.4 根据主题id，获取帖子的回复数
				int replyCount = iThreadReplyInfoDAO.countReplyByTid(tid);
				threadInfo.setReplyCount(replyCount);
			}
		}
		return list;
	}

}
