package cn.javaex.uscat.dao.subject_class;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.javaex.uscat.view.SubjectClass;

public interface ISubjectClassDAO {

	/**
	 * 根据板块id，查询其下所有分类
	 * @param fid 板块id
	 * @return
	 */
	List<SubjectClass> listByFid(String fid);

	/**
	 * 插入新的主题分类
	 * @param subjectClass
	 * @return
	 */
	int insert(SubjectClass subjectClass);

	/**
	 * 更新主题分类
	 * @param subjectClass
	 * @return
	 */
	int update(SubjectClass subjectClass);

	/**
	 * 批量删除主题分类
	 * @param subjectIdArr 主题分类id数组
	 */
	int delete(@Param("subjectIdArr") String[] subjectIdArr);

	/**
	 * 根据板块id，查询该板块下的主题分类数量
	 * @param fid 板块id
	 * @return
	 */
	int countByFid(String fid);

	/**
	 * 根据主键，查询主题分类名称（带html代码）
	 * @param subjectId 主键
	 * @return
	 */
	String selectNameHtmlBySubjectId(String subjectId);
	
}