package cn.javaex.uscat.action.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.api_info.ApiInfoService;
import cn.javaex.uscat.service.download_count.DownloadCountService;
import cn.javaex.uscat.service.slide_info.SlideInfoService;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.SlideInfo;
import cn.javaex.uscat.view.ThreadInfo;

@Controller
@RequestMapping("api")
public class ApiAction {

	@Autowired
	private SlideInfoService slideProfileInfoService;
	@Autowired
	private ApiInfoService apiInfoService;
	@Autowired
	private DownloadCountService downloadCountService;
	
	/**
	 * 幻灯片数据
	 * @param apiId 接口表主键
	 * @return
	 */
	@RequestMapping("slide.json")
	@ResponseBody
	public Result slideList(@RequestParam(value="apiId") String apiId) {
		
		List<SlideInfo> list = slideProfileInfoService.listByApiId(apiId);
		
		return Result.success().add("list", list);
	}
	
	/**
	 * 根据自定义接口查询数据
	 * @return
	 * @throws QingException 
	 */
	@RequestMapping("data.json")
	@ResponseBody
	public Result data(
			@RequestParam(value="apiId") String apiId) throws QingException {
		try {
			Integer.parseInt(apiId);
		} catch (Exception e) {
			throw new QingException(ErrorMsg.ERROR_400002);
		}
		
		List<ThreadInfo> list = apiInfoService.listDataById(apiId);
		
		return Result.success().add("list", list);
	}
	
	/**
	 * 下载计数器文件
	 * @param apiId
	 * @return
	 * @throws QingException
	 */
	@RequestMapping("download.json")
	@ResponseBody
	public Result download(
			HttpServletRequest request,
			@RequestParam(value="apiId") String apiId) throws QingException {
		try {
			Integer.parseInt(apiId);
		} catch (Exception e) {
			throw new QingException(ErrorMsg.ERROR_400002);
		}
		
		String url = downloadCountService.updateById(request, apiId);
		
		return Result.success().add("url", url);
	}
}
