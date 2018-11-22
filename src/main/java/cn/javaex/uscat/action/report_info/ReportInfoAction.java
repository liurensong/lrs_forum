package cn.javaex.uscat.action.report_info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.javaex.uscat.service.report_info.ReportInfoService;
import cn.javaex.uscat.view.ReportInfo;
import cn.javaex.uscat.view.Result;

@Controller
@RequestMapping("report_info")
public class ReportInfoAction {

	@Autowired
	private ReportInfoService reportInfoService;
	
	/**
	 * 查询用户举报记录
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("list.action")
	public String list(ModelMap map,
			@RequestParam(required=false, value="status") String status,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(status)) {
			param.put("status", status);
			map.put("status", status);
		}
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<ReportInfo> list = reportInfoService.list(param);
		PageInfo<ReportInfo> pageInfo = new PageInfo<ReportInfo>(list);
		map.put("pageInfo", pageInfo);
		
		return "admin/report_info/list";
	}
	
	/**
	 * 批量处理举报记录
	 * @param idArr 举报记录id数组
	 * @return
	 */
	@RequestMapping("batch_delete_thread.json")
	@ResponseBody
	public Result batchDeleteThread(
			@RequestParam(value="idArr") String[] idArr) {
		
		reportInfoService.batchDeleteThread(idArr);
		
		return Result.success();
	}
	
	/**
	 * 批量删除举报记录
	 * @param idArr 举报记录id数组
	 * @return
	 */
	@RequestMapping("batch_delete_report.json")
	@ResponseBody
	public Result batchDeleteReport(
			@RequestParam(value="idArr") String[] idArr) {
		
		reportInfoService.batchDeleteReport(idArr);
		
		return Result.success();
	}

}
