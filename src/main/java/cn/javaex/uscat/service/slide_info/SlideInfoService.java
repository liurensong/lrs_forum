package cn.javaex.uscat.service.slide_info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.dao.slide_info.ISlideInfoDAO;
import cn.javaex.uscat.view.SlideInfo;

@Service("SlideInfoService")
public class SlideInfoService {
	@Autowired
	private ISlideInfoDAO iSlideProfileInfoDAO;

	/**
	 * 根据接口主键，查询所有幻灯片数据
	 * @param apiId 接口表的主键
	 * @return
	 */
	public List<SlideInfo> listByApiId(String apiId) {
		return iSlideProfileInfoDAO.listByApiId(apiId);
	}

	/**
	 * 保存幻灯片数据
	 * @param fieldInfoList
	 */
	public void save(List<SlideInfo> slideProfileInfoList) {
		for (SlideInfo slideProfileInfo : slideProfileInfoList) {
			if (StringUtils.isEmpty(slideProfileInfo.getId())) {
				// 插入
				iSlideProfileInfoDAO.insert(slideProfileInfo);
			} else {
				// 更新
				iSlideProfileInfoDAO.update(slideProfileInfo);
			}
		}
	}

	/**
	 * 删除幻灯片数据
	 * @param idArr 幻灯片数据主键数组
	 */
	public void delete(String[] idArr) {
		// 批量删除幻灯片数据
		iSlideProfileInfoDAO.delete(idArr);
	}

}
