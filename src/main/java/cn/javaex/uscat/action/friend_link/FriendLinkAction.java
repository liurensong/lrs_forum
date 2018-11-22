package cn.javaex.uscat.action.friend_link;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.service.friend_link.FriendLinkService;
import cn.javaex.uscat.view.FriendLink;
import cn.javaex.uscat.view.Result;

@Controller
@RequestMapping("friend_link")
public class FriendLinkAction {

	@Autowired
	private FriendLinkService friendLinkService;
	
	/**
	 * 查询所有友情链接
	 */
	@RequestMapping("list.action")
	public String list(ModelMap map) {
		
		List<FriendLink> list = friendLinkService.list();
		map.put("list", list);
		
		return "admin/friend_link/list";
	}
	
	/**
	 * 保存友情链接
	 * @param idArr 主键数组
	 * @param sortArr 排序数组
	 * @param nameArr 站点名称名称数组
	 * @param urlArr 站点URL数组
	 * @param logoArr logo地址数组
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(
			@RequestParam(value="idArr") String[] idArr,
			@RequestParam(value="sortArr") String[] sortArr,
			@RequestParam(value="nameArr") String[] nameArr,
			@RequestParam(value="urlArr") String[] urlArr,
			@RequestParam(value="logoArr") String[] logoArr) {
		
		List<FriendLink> friendLinkList = new ArrayList<FriendLink>();
		
		// 判断是否已有既存数据
		if (idArr.length==0) {
			// 全是新增
			// 遍历sortArr数组
			for (int i=0; i<sortArr.length; i++) {
				FriendLink friendLink = new FriendLink();
				friendLink.setSort(sortArr[i]);
				friendLink.setName(nameArr[i]);
				friendLink.setUrl(urlArr[i]);
				friendLink.setLogo(logoArr[i]);
				
				friendLinkList.add(friendLink);
			}
		} else {
			// 遍历idArr数组
			for (int i=0; i<idArr.length; i++) {
				FriendLink friendLink = new FriendLink();
				friendLink.setId(idArr[i]);
				friendLink.setSort(sortArr[i]);
				friendLink.setName(nameArr[i]);
				friendLink.setUrl(urlArr[i]);
				friendLink.setLogo(logoArr[i]);
				
				friendLinkList.add(friendLink);
			}
		}
		friendLinkService.save(friendLinkList);

		return Result.success();
	}
	
	/**
	 * 批量删除
	 * @param idArr 友情链接主键数组
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public Result delete(ModelMap map, 
			@RequestParam(value="idArr") String[] idArr) {
		
		friendLinkService.delete(idArr);
		return Result.success();
	}
}
