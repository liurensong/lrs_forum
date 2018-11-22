package cn.javaex.uscat.action.thread_reply_info;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.javaex.uscat.service.thread_reply_info.ThreadReplyInfoService;
import cn.javaex.uscat.view.Result;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("thread_reply_info")
public class ThreadReplyInfoAction {

	@Autowired
	private ThreadReplyInfoService threadReplyInfoService;
	
	/**
	 * 回帖回收站
	 * @param map
	 * @param gid 分区id
	 * @param fid 板块id
	 * @param keyword 标题关键字
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("list_recycle.action")
	public String listRecycle(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> list = threadReplyInfoService.listRecycle();
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);
		map.put("pageInfo", pageInfo);
		
		// 转为json格式，方便js调用
		JSONArray jsonList = JSONArray.fromObject(pageInfo.getList());
		map.put("jsonList", jsonList);
		
		return "admin/thread_reply_info/list_recycle";
	}
	
	/**
	 * 批量处理回收站的回帖
	 * @param idArr 回帖id数组
	 * @param optArr 操作数组
	 * @return
	 */
	@RequestMapping("batch_opt.json")
	@ResponseBody
	public Result batchOpt(
			@RequestParam(value="idArr") String[] idArr,
			@RequestParam(value="optArr") String[] optArr) {
		
		threadReplyInfoService.batchOpt(idArr, optArr);
		
		return Result.success();
	}
	
	/**
	 * 全部还原
	 * @return
	 */
	@RequestMapping("recovery_all.json")
	@ResponseBody
	public Result recoveryAll() {
		
		threadReplyInfoService.recoveryAll();
		
		return Result.success();
	}
	
	/**
	 * 全部删除
	 * @return
	 */
	@RequestMapping("delete_all.json")
	@ResponseBody
	public Result deleteAll() {
		
		threadReplyInfoService.deleteAll();
		
		return Result.success();
	}
}
