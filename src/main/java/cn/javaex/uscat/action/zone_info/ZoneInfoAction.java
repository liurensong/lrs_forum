package cn.javaex.uscat.action.zone_info;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.zone_info.ZoneInfoService;
import cn.javaex.uscat.view.BoardInfo;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.ZoneInfo;

@Controller
@RequestMapping("zone_info")
public class ZoneInfoAction {

	@Autowired
	private ZoneInfoService zoneInfoService;
	
	/**
	 * 查询所有分区和板块列表
	 */
	@RequestMapping("list.action")
	public String list(ModelMap map) {
		
		List<ZoneInfo> list = zoneInfoService.listAll();
		map.put("list", list);
		
		return "admin/zone_info/list";
	}
	
	/**
	 * 保存分区和板块
	 */
	@RequestMapping("save_zone_and_board.json")
	@ResponseBody
	public Result saveZoneAndBoard(
			@RequestParam(value="gidArr") String[] gidArr,
			@RequestParam(value="zoneSortArr") String[] zoneSortArr,
			@RequestParam(value="zoneNameArr") String[] zoneNameArr,
			@RequestParam(required=false, value="fidArr") String[] fidArr,
			@RequestParam(required=false, value="boardGidArr") String[] boardGidArr,
			@RequestParam(required=false, value="boardSortArr") String[] boardSortArr,
			@RequestParam(required=false, value="boardNameArr") String[] boardNameArr) throws QingException {
		
		List<ZoneInfo> zoneInfoList = new ArrayList<ZoneInfo>();
		List<BoardInfo> boardInfoList = new ArrayList<BoardInfo>();
		
		// 遍历区数据
		for (int i=0; i<zoneSortArr.length; i++) {
			ZoneInfo zoneInfo = new ZoneInfo();
			try {
				zoneInfo.setGid(gidArr[i]);
			} catch (Exception e) {
			}
			zoneInfo.setSort(zoneSortArr[i]);
			zoneInfo.setName(zoneNameArr[i]);
			
			zoneInfoList.add(zoneInfo);
		}
		
		// 遍历板块数据
		if (boardSortArr!=null && boardSortArr.length>0) {
			for (int i=0; i<boardSortArr.length; i++) {
				BoardInfo boardInfo = new BoardInfo();
				try {
					boardInfo.setFid(fidArr[i]);
				} catch (Exception e) {
				}
				boardInfo.setGid(boardGidArr[i]);
				boardInfo.setSort(boardSortArr[i]);
				boardInfo.setName(boardNameArr[i]);
				
				boardInfoList.add(boardInfo);
			}
		}
		
		zoneInfoService.saveZoneAndBoard(zoneInfoList, boardInfoList);

		return Result.success();
	}
	
	/**
	 * 分区编辑
	 * @param map
	 * @param gid 分区id
	 * @return
	 */
	@RequestMapping("edit.action")
	public String edit(ModelMap map,
			@RequestParam(required=false, value="gid") String gid) {
		
		ZoneInfo zoneInfo = zoneInfoService.selectById(gid);
		map.put("zoneInfo", zoneInfo);
		
		return "admin/zone_info/edit";
	}
	
	/**
	 * 保存分区设置
	 * @param zoneInfo
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(ZoneInfo zoneInfo) {
		
		zoneInfoService.save(zoneInfo);
		
		return Result.success();
	}
	
	/**
	 * 根据分区id删除分区和其下的板块
	 * @param gid 分区id
	 * @return
	 * @throws QingException
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public Result delete(@RequestParam(value="gid") String gid) throws QingException {
		
		zoneInfoService.delete(gid);
		
		return Result.success();
	}
	
	/**
	 * 更新分区的可见与否
	 * @param zoneInfo
	 * @return
	 */
	@RequestMapping("update_is_show.json")
	@ResponseBody
	public Result updateIsShow(
			@RequestParam(value="id") String id,
			@RequestParam(value="isShow") String isShow) {
		
		ZoneInfo zoneInfo = new ZoneInfo();
		zoneInfo.setGid(id);
		zoneInfo.setIsShow(isShow);
		
		zoneInfoService.save(zoneInfo);
		
		return Result.success();
	}
}
