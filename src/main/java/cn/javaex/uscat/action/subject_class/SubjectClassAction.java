package cn.javaex.uscat.action.subject_class;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.service.subject_class.SubjectClassService;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.SubjectClass;

@Controller
@RequestMapping("subject_class")
public class SubjectClassAction {

	@Autowired
	private SubjectClassService subjectClassService;
	
	/**
	 * 保存板块下的主题分类
	 * @param fid 板块id
	 * @param isSubjectClass 是否启用主题分类
	 * @param subjectIdArr 主题分类id数组
	 * @param sortArr 排序数组
	 * @param nameHtmlArr 分类名称数组（带html代码，用于页面显示时有样式）
	 * @param isShowArr 是否启用数组
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(
			@RequestParam(value="fid") String fid,
			@RequestParam(value="isSubjectClass") String isSubjectClass,
			@RequestParam(value="subjectIdArr") String[] subjectIdArr,
			@RequestParam(value="sortArr") String[] sortArr,
			@RequestParam(value="nameHtmlArr") String[] nameHtmlArr,
			@RequestParam(value="isShowArr") String[] isShowArr) {
		
		List<SubjectClass> subjectClassList = new ArrayList<SubjectClass>();
		
		// 判断是否已有既存数据
		if (subjectIdArr.length==0) {
			// 全是新增
			// 遍历sortArr数组
			for (int i=0; i<sortArr.length; i++) {
				SubjectClass subjectClass = new SubjectClass();
				subjectClass.setFid(fid);
				subjectClass.setSort(sortArr[i]);
				subjectClass.setNameHtml(nameHtmlArr[i]);
				subjectClass.setNameText(nameHtmlArr[i].replaceAll("</?[^>]+>", ""));
				subjectClass.setIsShow(isShowArr[i]);
				
				subjectClassList.add(subjectClass);
			}
		} else {
			// 遍历idArr数组
			for (int i=0; i<subjectIdArr.length; i++) {
				SubjectClass subjectClass = new SubjectClass();
				subjectClass.setSubjectId(subjectIdArr[i]);
				subjectClass.setFid(fid);
				subjectClass.setSort(sortArr[i]);
				subjectClass.setNameHtml(nameHtmlArr[i]);
				subjectClass.setNameText(nameHtmlArr[i].replaceAll("</?[^>]+>", ""));
				subjectClass.setIsShow(isShowArr[i]);
				
				subjectClassList.add(subjectClass);
			}
		}
		
		subjectClassService.save(fid, isSubjectClass,subjectClassList);
		
		return Result.success();
	}
	
	/**
	 * 批量删除主题分类
	 * @param fid 板块id
	 * @param subjectIdArr 主题分类id数组
	 * @return
	 */
	@RequestMapping("delete.json")
	@ResponseBody
	public Result delete(
			@RequestParam(value="fid") String fid,
			@RequestParam(value="subjectIdArr") String[] subjectIdArr) {
		
		subjectClassService.delete(fid, subjectIdArr);
		
		return Result.success();
	}
	
	/**
	 * 获取某个板块下的主题分类列表
	 * @param fid 板块id
	 * @return
	 */
	@RequestMapping("list_by_fid.json")
	@ResponseBody
	public Result listByFid(@RequestParam(value="fid") String fid) {
		
		List<SubjectClass> subjectList = subjectClassService.listByFid(fid);
		
		return Result.success().add("subjectList", subjectList);
	}
}
