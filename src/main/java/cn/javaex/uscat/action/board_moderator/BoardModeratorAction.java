package cn.javaex.uscat.action.board_moderator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.board_moderator.BoardModeratorService;
import cn.javaex.uscat.view.BoardModerator;
import cn.javaex.uscat.view.Result;

@Controller
@RequestMapping("board_moderator")
public class BoardModeratorAction {

	@Autowired
	private BoardModeratorService boardModeratorService;
	
	/**
	 * 编辑版主
	 * @param map
	 * @param fid 分区id
	 * @return
	 */
	@RequestMapping("list.action")
	public String list(ModelMap map,
			@RequestParam(value="fid") String fid) {
		map.put("fid", fid);
		
		List<BoardModerator> list = boardModeratorService.listByFid(fid);
		map.put("list", list);
		
		return "admin/board_moderator/list";
	}
	
	/**
	 * 保存版主
	 * @param fid 分区id
	 * @param loginNameArr 用户名数组
	 * @return
	 * @throws QingException
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(
			@RequestParam(value="fid") String fid,
			@RequestParam(value="loginNameArr") String[] loginNameArr) throws QingException {
		
		boardModeratorService.save(fid, loginNameArr);
		
		return Result.success();
	}
	
	/**
	 * 删除版主
	 * @param idArr 主键数组
	 * @return
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public Result delete(@RequestParam(value="idArr") String[] idArr) {
		
		boardModeratorService.delete(idArr);
		
		return Result.success();
	}
}
