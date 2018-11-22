package cn.javaex.uscat.action.board_info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.board_info.BoardInfoService;
import cn.javaex.uscat.service.group_info.GroupInfoService;
import cn.javaex.uscat.service.subject_class.SubjectClassService;
import cn.javaex.uscat.service.zone_info.ZoneInfoService;
import cn.javaex.uscat.view.BoardInfo;
import cn.javaex.uscat.view.GroupInfo;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.SubjectClass;
import cn.javaex.uscat.view.ZoneInfo;

@Controller
@RequestMapping("board_info")
public class BoardInfoAction {

	@Autowired
	private BoardInfoService boardInfoService;
	@Autowired
	private ZoneInfoService zoneInfoService;
	@Autowired
	private GroupInfoService groupInfoService;
	@Autowired
	private SubjectClassService subjectClassService;
	
	/**
	 * 板块编辑
	 * @param map
	 * @param fid 板块id
	 * @return
	 */
	@RequestMapping("edit.action")
	public String edit(ModelMap map,
			@RequestParam(required=false, value="fid") String fid) {
		
		BoardInfo boardInfo = boardInfoService.selectById(fid);
		map.put("boardInfo", boardInfo);
		
		// 查询所有分区
		List<ZoneInfo> zoneList = zoneInfoService.list();
		map.put("zoneList", zoneList);
		
		// 根据板块id，查询所有会员用户组，及其板块权限
		List<GroupInfo> groupInfoList = groupInfoService.listPermByFid(fid);
		map.put("groupInfoList", groupInfoList);
		
		// 根据板块id，查询其下所有分类
		List<SubjectClass> subjectClassList = subjectClassService.listByFid(fid);
		map.put("subjectClassList", subjectClassList);
		
		return "admin/board_info/edit";
	}
	
	/**
	 * 保存板块设置
	 * @param boardInfo
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(BoardInfo boardInfo) {
		
		boardInfoService.save(boardInfo);
		
		return Result.success();
	}
	
	/**
	 * 根据主键删除板块
	 * @param fid 板块id
	 * @return
	 * @throws QingException
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public Result delete(@RequestParam(value="fid") String fid) throws QingException {
		
		boardInfoService.deleteById(fid);
		
		return Result.success();
	}
	
	/**
	 * 更新板块的可见与否
	 * @param zoneInfo
	 * @return
	 */
	@RequestMapping("update_is_show.json")
	@ResponseBody
	public Result updateIsShow(
			@RequestParam(value="id") String id,
			@RequestParam(value="isShow") String isShow) {
		
		BoardInfo boardInfo = new BoardInfo();
		boardInfo.setFid(id);
		boardInfo.setIsShow(isShow);
		
		boardInfoService.save(boardInfo);
		
		return Result.success();
	}
	
	/**
	 * 获取某个分区下的板块列表
	 * @param gid 分区id
	 * @return
	 */
	@RequestMapping("list_by_gid.json")
	@ResponseBody
	public Result listByGid(@RequestParam(value="gid") String gid) {
		
		List<BoardInfo> boardList = boardInfoService.listByGid(gid);
		
		return Result.success().add("boardList", boardList);
	}
}
