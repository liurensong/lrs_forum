package cn.javaex.uscat.action.download_count;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.download_count.DownloadCountService;
import cn.javaex.uscat.service.upload_info.UploadInfoService;
import cn.javaex.uscat.view.DownloadCount;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.UploadInfo;

@Controller
@RequestMapping("download_count")
public class DownloadCountAction {

	@Autowired
	private DownloadCountService downloadCountService;
	@Autowired
	private UploadInfoService uploadInfoService;
	
	/**
	 * 查询列表
	 */
	@RequestMapping("list.action")
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<DownloadCount> list = downloadCountService.list();
		PageInfo<DownloadCount> pageInfo = new PageInfo<DownloadCount>(list);
		map.put("pageInfo", pageInfo);
		
		return "admin/download_count/list";
	}
	
	/**
	 * 保存下载计数器接口
	 * @param idArr 接口主键数组
	 * @param sortArr 接口排序数组
	 * @param nameArr 接口名称数组
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(
			@RequestParam(value="idArr") String[] idArr,
			@RequestParam(value="sortArr") String[] sortArr,
			@RequestParam(value="nameArr") String[] nameArr) {
		
		List<DownloadCount> downloadCountList = new ArrayList<DownloadCount>();
		
		// 判断是否已有既存数据
		if (idArr.length==0) {
			// 全是新增
			// 遍历sortArr数组
			for (int i=0; i<sortArr.length; i++) {
				DownloadCount downloadCount = new DownloadCount();
				downloadCount.setSort(sortArr[i]);
				downloadCount.setName(nameArr[i]);
				
				downloadCountList.add(downloadCount);
			}
		} else {
			// 遍历idArr数组
			for (int i=0; i<idArr.length; i++) {
				DownloadCount downloadCount = new DownloadCount();
				downloadCount.setId(idArr[i]);
				downloadCount.setSort(sortArr[i]);
				downloadCount.setName(nameArr[i]);
				
				downloadCountList.add(downloadCount);
			}
		}
		
		downloadCountService.save(downloadCountList);
		
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
		
		downloadCountService.delete(idArr);
		
		return Result.success();
	}
	
	/**
	 * 编辑
	 * @param map
	 * @param id 主键
	 * @return
	 */
	@RequestMapping("edit.action")
	public String edit(ModelMap map,
			@RequestParam(value = "id") String id) {
		
		DownloadCount downloadCount = downloadCountService.selectById(id);
		map.put("downloadCount", downloadCount);
		
		return "admin/download_count/edit";
	}
	
	/**
	 * 更新
	 * @param downloadCount
	 * @return
	 */
	@RequestMapping("update.json")
	@ResponseBody
	public Result update(DownloadCount downloadCount) {
		
		downloadCountService.update(downloadCount);
		
		return Result.success();
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws QingException
	 */
	@RequestMapping("upload_file.json")
	@ResponseBody
	public Result uploadFile(
			MultipartFile file,
			@RequestParam(value="type") String type) throws IOException, QingException {
		
		UploadInfo uploadInfo = uploadInfoService.selectByType(type);
		String fileUrl = uploadInfoService.uploadFile(file, uploadInfo);
		
		return Result.success().add("fileUrl", fileUrl);
	}
}
