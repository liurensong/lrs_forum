package cn.javaex.uscat.service.subject_class;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.dao.board_info.IBoardInfoDAO;
import cn.javaex.uscat.dao.subject_class.ISubjectClassDAO;
import cn.javaex.uscat.view.BoardInfo;
import cn.javaex.uscat.view.SubjectClass;

@Service("SubjectClassService")
public class SubjectClassService {
	@Autowired
	private ISubjectClassDAO iSubjectClassDAO;
	@Autowired
	private IBoardInfoDAO iBoardInfoDAO;
	
	/**
	 * 根据板块id，查询其下所有分类
	 * @param fid 板块id
	 * @return
	 */
	public List<SubjectClass> listByFid(String fid) {
		return iSubjectClassDAO.listByFid(fid);
	}

	/**
	 * 保存板块下的主题分类
	 * @param fid 板块id
	 * @param isSubjectClass 是否启用主题分类
	 * @param subjectClassList
	 */
	public void save(String fid, String isSubjectClass, List<SubjectClass> subjectClassList) {
		// 1.0 更新板块是否启用主题分类
		BoardInfo boardInfo = new BoardInfo();
		boardInfo.setFid(fid);
		boardInfo.setIsSubjectClass(isSubjectClass);
		iBoardInfoDAO.update(boardInfo);
		
		// 该板块如果启用了主题分类，则保存主题分类信息
		if ("1".equals(isSubjectClass)) {
			for (SubjectClass subjectClass : subjectClassList) {
				if (StringUtils.isEmpty(subjectClass.getSubjectId())) {
					// 插入
					iSubjectClassDAO.insert(subjectClass);
				} else {
					// 更新
					iSubjectClassDAO.update(subjectClass);
				}
			}
		}
	}

	/**
	 * 批量删除主题分类
	 * @param fid 板块id
	 * @param subjectIdArr 主题分类id数组
	 */
	public void delete(String fid, String[] subjectIdArr) {
		// 1.0 删除主题分类
		iSubjectClassDAO.delete(subjectIdArr);
		
		// 2.0 根据板块id，查询该板块下的主题分类数量
		int count = iSubjectClassDAO.countByFid(fid);
		
		// 3.0 如果该板块下已无主题分类，则设置主题分类为不启用
		if (count==0) {
			BoardInfo boardInfo = new BoardInfo();
			boardInfo.setFid(fid);
			boardInfo.setIsSubjectClass("0");
			
			iBoardInfoDAO.update(boardInfo);
		}
	}
	
}
