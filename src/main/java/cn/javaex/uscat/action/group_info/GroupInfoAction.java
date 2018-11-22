package cn.javaex.uscat.action.group_info;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.group_info.GroupInfoService;
import cn.javaex.uscat.view.GroupInfo;
import cn.javaex.uscat.view.Result;

@Controller
@RequestMapping("group_info")
public class GroupInfoAction {

	@Autowired
	private GroupInfoService groupInfoService;
	
	/**
	 * 查询所有用户组（系统内置的管理组）
	 */
	@RequestMapping("list.action")
	public String list(ModelMap map,
			@RequestParam(required=false, value="type") String type) {
		
		List<GroupInfo> list = groupInfoService.listByType(type);
		map.put("list", list);
		
		if ("system".equals(type)) {
			return "admin/group_info/list_system";
		} else {
			return "admin/group_info/list_user";
		}
	}
	
	/**
	 * 保存用户组
	 * @param idArr 用户组主键数组
	 * @param nameArr 用户组名称数组
	 * @param pointArr 最低积分数组
	 * @throws QingException
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(
			@RequestParam(value="idArr") String[] idArr,
			@RequestParam(value="nameArr") String[] nameArr,
			@RequestParam(value="pointArr") String[] pointArr) throws QingException {
		
		List<GroupInfo> groupInfoList = new ArrayList<GroupInfo>();
		
		// 遍历idArr数组
		for (int i=0; i<idArr.length; i++) {
			GroupInfo groupInfo = new GroupInfo();
			groupInfo.setId(idArr[i]);
			groupInfo.setName(nameArr[i]);
			groupInfo.setPoint(pointArr[i]);
			
			groupInfoList.add(groupInfo);
		}
		
		groupInfoService.save(groupInfoList);

		return Result.success();
	}
	
	/**
	 * 删除
	 * @param idArr 用户组主键数组
	 * @throws QingException 
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public Result delete(@RequestParam(value="idArr") String[] idArr) throws Exception {
		
		groupInfoService.delete(idArr);
		return Result.success();
	}
}
