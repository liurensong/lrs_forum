package cn.javaex.uscat.action.thread_info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.board_info.BoardInfoService;
import cn.javaex.uscat.service.thread_info.ThreadInfoService;
import cn.javaex.uscat.service.user_info.UserInfoService;
import cn.javaex.uscat.service.zone_info.ZoneInfoService;
import cn.javaex.uscat.view.BoardInfo;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.ThreadInfo;
import cn.javaex.uscat.view.UserInfo;
import cn.javaex.uscat.view.ZoneInfo;

@Controller
@RequestMapping("thread_info")
public class ThreadInfoAction {

	@Autowired
	private ThreadInfoService threadInfoService;
	@Autowired
	private ZoneInfoService zoneInfoService;
	@Autowired
	private BoardInfoService boardInfoService;
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 查询所有主题（正常）
	 * @param map
	 * @param gid 分区id
	 * @param fid 板块id
	 * @param keyword 标题关键字
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("list_normal.action")
	public String listNormal(ModelMap map,
			@RequestParam(required=false, value="gid") String gid,
			@RequestParam(required=false, value="fid") String fid,
			@RequestParam(required=false, value="keyword") String keyword,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		// 查询分区列表
		List<ZoneInfo> zoneList = zoneInfoService.list();
		map.put("zoneList", zoneList);
		
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(gid)) {
			param.put("gid", gid);
			map.put("gid", gid);
			
			// 指定分区时，查询其下的板块列表
			List<BoardInfo> boardList = boardInfoService.listByGid(gid);
			map.put("boardList", boardList);
		}
		if (!StringUtils.isEmpty(fid)) {
			param.put("fid", fid);
			map.put("fid", fid);
		}
		if (!StringUtils.isEmpty(keyword)) {
			param.put("keyword", keyword.trim());
			map.put("keyword", keyword);
		}
		param.put("status", "1");
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<ThreadInfo> list = threadInfoService.list(param);
		PageInfo<ThreadInfo> pageInfo = new PageInfo<ThreadInfo>(list);
		map.put("pageInfo", pageInfo);
		
		List<ZoneInfo> zoneListAll = zoneInfoService.listAll();
		map.put("zoneListAll", zoneListAll);
		
		return "admin/thread_info/list_normal";
	}
	
	/**
	 * 查询所有主题（回收站）
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
			@RequestParam(required=false, value="gid") String gid,
			@RequestParam(required=false, value="fid") String fid,
			@RequestParam(required=false, value="keyword") String keyword,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		// 查询分区列表
		List<ZoneInfo> zoneList = zoneInfoService.list();
		map.put("zoneList", zoneList);
		
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(gid)) {
			param.put("gid", gid);
			map.put("gid", gid);
			
			// 指定分区时，查询其下的板块列表
			List<BoardInfo> boardList = boardInfoService.listByGid(gid);
			map.put("boardList", boardList);
		}
		if (!StringUtils.isEmpty(fid)) {
			param.put("fid", fid);
			map.put("fid", fid);
		}
		if (!StringUtils.isEmpty(keyword)) {
			param.put("keyword", keyword.trim());
			map.put("keyword", keyword);
		}
		param.put("status", "2");
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<ThreadInfo> list = threadInfoService.list(param);
		PageInfo<ThreadInfo> pageInfo = new PageInfo<ThreadInfo>(list);
		map.put("pageInfo", pageInfo);

		return "admin/thread_info/list_recycle";
	}
	
	/**
	 * 批量更新主题状态
	 * @param tidArr 主题id数组
	 * @param status 状态
	 * @return
	 */
	@RequestMapping("batch_update_status.json")
	@ResponseBody
	public Result batchUpdateStatus(
			@RequestParam(value = "tidArr") String[] tidArr,
			@RequestParam(value = "status") String status) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("idArr", tidArr);
		param.put("status", status);
		
		threadInfoService.batchUpdateStatus(param);
		
		return Result.success();
	}
	
	/**
	 * 批量彻底删除主题
	 * @param tidArr 主题id数组
	 * @return
	 */
	@RequestMapping("batch_delete.json")
	@ResponseBody
	public Result batchDelete(
			@RequestParam(value = "tidArr") String[] tidArr) {
		
		threadInfoService.batchDelete(tidArr);
		
		return Result.success();
	}
	
	/**
	 * 批量移动到板块
	 * @param tidArr 主题id数组
	 * @param fid 板块id
	 * @param subjectId 主题分类id
	 * @return
	 */
	@RequestMapping("batch_move_to_board.json")
	@ResponseBody
	public Result batchMoveToBoard(
			@RequestParam(value = "tidArr") String[] tidArr,
			@RequestParam(value = "fid") String fid,
			@RequestParam(required=false, value = "subjectId") String subjectId) {
		
		threadInfoService.batchMoveToBoard(tidArr, fid, subjectId);
		
		return Result.success();
	}
	
	/**
	 * 后台批量操作主题
	 * @param tidArr 主题id数组
	 * @param isTop 是否置顶
	 * @param isDigest 是否精华
	 * @param isDelete 是否删除（回收站）
	 * @return
	 * @throws QingException 
	 */
	@RequestMapping("batch_opt.json")
	@ResponseBody
	public Result batchOpt(
			HttpServletRequest request,
			@RequestParam(value = "tidArr") String[] tidArr,
			@RequestParam(required=false, value = "isTop") String isTop,
			@RequestParam(required=false, value = "isDigest") String isDigest,
			@RequestParam(required=false, value = "isDelete") String isDelete) throws QingException {
		
		UserInfo userInfo = userInfoService.getUserInfo(request);
		
		String optSelect = "";
		if (!StringUtils.isEmpty(isTop)) {
			if ("1".equals(isTop)) {
				optSelect = "top1";
			} else {
				optSelect = "top0";
			}
		} else if (!StringUtils.isEmpty(isDigest)) {
			if ("1".equals(isDigest)) {
				optSelect = "digest1";
			} else {
				optSelect = "digest0";
			}
		} else if (!StringUtils.isEmpty(isDelete)) {
			optSelect = "delete";
		} else {
			throw new QingException(ErrorMsg.ERROR_300003);
		}
		
		threadInfoService.batchOpt(tidArr, optSelect, userInfo);
		
		return Result.success();
	}
}
