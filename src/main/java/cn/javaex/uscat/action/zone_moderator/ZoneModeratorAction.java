package cn.javaex.uscat.action.zone_moderator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.zone_moderator.ZoneModeratorService;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.ZoneModerator;

@Controller
@RequestMapping("zone_moderator")
public class ZoneModeratorAction {

	@Autowired
	private ZoneModeratorService zoneModeratorService;
	
	/**
	 * 编辑超级版主
	 * @param map
	 * @param gid 分区id
	 * @return
	 */
	@RequestMapping("list.action")
	public String list(ModelMap map,
			@RequestParam(value="gid") String gid) {
		map.put("gid", gid);
		
		List<ZoneModerator> list = zoneModeratorService.listByGid(gid);
		map.put("list", list);
		
		return "admin/zone_moderator/list";
	}
	
	/**
	 * 保存超级版主
	 * @param gid 分区id
	 * @param loginNameArr 用户名数组
	 * @return
	 * @throws QingException
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(
			@RequestParam(value="gid") String gid,
			@RequestParam(value="loginNameArr") String[] loginNameArr) throws QingException {
		
		zoneModeratorService.save(gid, loginNameArr);
		
		return Result.success();
	}
	
	/**
	 * 删除超级版主
	 * @param idArr 主键数组
	 * @return
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public Result delete(@RequestParam(value="idArr") String[] idArr) {
		
		zoneModeratorService.delete(idArr);
		
		return Result.success();
	}
}
