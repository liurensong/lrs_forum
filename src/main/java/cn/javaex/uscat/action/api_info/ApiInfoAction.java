package cn.javaex.uscat.action.api_info;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.api_info.ApiInfoService;
import cn.javaex.uscat.service.zone_info.ZoneInfoService;
import cn.javaex.uscat.view.ApiInfo;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.ZoneInfo;

@Controller
@RequestMapping("api_info")
public class ApiInfoAction {

	@Autowired
	private ApiInfoService apiInfoService;
	@Autowired
	private ZoneInfoService zoneInfoService;
	
	/**
	 * 查询所有幻灯片接口
	 */
	@RequestMapping("list_slide.action")
	public String listSlide(ModelMap map) {
		
		List<ApiInfo> list = apiInfoService.listByType("slide");
		map.put("list", list);
		
		return "admin/api_info/list_slide";
	}
	
	/**
	 * 保存接口
	 * @param type 接口类型
	 * @param idArr 接口主键数组
	 * @param sortArr 接口排序数组
	 * @param nameArr 接口名称数组
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(
			@RequestParam(value="type") String type,
			@RequestParam(value="idArr") String[] idArr,
			@RequestParam(value="sortArr") String[] sortArr,
			@RequestParam(value="nameArr") String[] nameArr) {
		
		List<ApiInfo> apiInfoList = new ArrayList<ApiInfo>();
		
		// 判断是否已有既存数据
		if (idArr.length==0) {
			// 全是新增
			// 遍历sortArr数组
			for (int i=0; i<sortArr.length; i++) {
				ApiInfo apiInfo = new ApiInfo();
				apiInfo.setSort(sortArr[i]);
				apiInfo.setName(nameArr[i]);
				apiInfo.setType(type);
				
				apiInfoList.add(apiInfo);
			}
		} else {
			// 遍历idArr数组
			for (int i=0; i<idArr.length; i++) {
				ApiInfo apiInfo = new ApiInfo();
				apiInfo.setId(idArr[i]);
				apiInfo.setSort(sortArr[i]);
				apiInfo.setName(nameArr[i]);
				apiInfo.setType(type);
				
				apiInfoList.add(apiInfo);
			}
		}
		
		apiInfoService.save(apiInfoList);
		
		return Result.success();
	}
	
	/**
	 * 删除
	 * @param idArr 接口主键数组
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public Result delete(
			@RequestParam(value="idArr") String[] idArr) {
		
		apiInfoService.delete(idArr);
		
		return Result.success();
	}
	
	/**
	 * 查询所有自定义接口
	 */
	@RequestMapping("list_user.action")
	public String listUser(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<ApiInfo> list = apiInfoService.listByType("user");
		PageInfo<ApiInfo> pageInfo = new PageInfo<ApiInfo>(list);
		map.put("pageInfo", pageInfo);
		
		return "admin/api_info/list_user";
	}
	
	/**
	 * 用户自定义接口配置
	 */
	@RequestMapping("user_edit.action")
	public String userEdit(ModelMap map,
			@RequestParam(value="id") String id,
			@RequestParam(value="name") String name) {
		
		// 数据来源，可见板块
		List<ZoneInfo> zoneList = zoneInfoService.listShow();
		map.put("zoneList", zoneList);
		
		// 查询该接口的信息
		ApiInfo apiInfo = apiInfoService.selectById(id);
		map.put("apiInfo", apiInfo);
		
		return "admin/api_info/user_edit";
	}
	
	/**
	 * 保存自定义数据条件设置
	 * @throws QingException
	 */
	@RequestMapping("user_save.json")
	@ResponseBody
	public Result userSave(HttpServletRequest request) throws QingException {
		ApiInfo apiInfo = new ApiInfo();
		apiInfo.setId(request.getParameter("id"));
		
		// 1.0 判断数据来源于哪些板块
		String fidAll = request.getParameter("fidAll");
		if ("0".equals(fidAll)) {
			// 不区分来源
			apiInfo.setFid("0");
		} else {
			// 来源个别分区
			String[] fidArr = request.getParameterValues("fid");
			if (fidArr==null || fidArr.length==0) {
				throw new QingException(ErrorMsg.ERROR_400001);
			}
			String fid = "";
			for (int i=0; i<fidArr.length; i++) {
				if (i==0) {
					fid = fidArr[i];
				} else {
					fid = fid + "," + fidArr[i];
				}
			}
			apiInfo.setFid(fid);
		}
		
		// 2.0 设置排序字段
		String orderField = request.getParameter("orderField");
		apiInfo.setOrderField(orderField);
		
		// 3.0 设置周期
		if ("reply_time".equals(orderField) || "create_time".equals(orderField)) {
			apiInfo.setCycle("0");
		} else {
			String cycle = request.getParameter("cycle");
			apiInfo.setCycle(cycle);
		}
		
		// 4.0 设置获取条数
		String num = request.getParameter("num");
		apiInfo.setNum(num);
		
		// 5.0 保存设置
		apiInfoService.update(apiInfo);
		
		return Result.success();
	}
}
