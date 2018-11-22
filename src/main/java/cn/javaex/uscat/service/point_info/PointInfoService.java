package cn.javaex.uscat.service.point_info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.javaex.uscat.dao.point_info.IPointInfoDAO;
import cn.javaex.uscat.view.PointInfo;

@Service("PointInfoService")
public class PointInfoService {
	@Autowired
	private IPointInfoDAO iPointInfoDAO;

	/**
	 * 查询积分表信息
	 * @param isUse 是否启用的
	 * @return
	 */
	public List<PointInfo> list(String isUse) {
		return iPointInfoDAO.list(isUse);
	}

	/**
	 * 保存积分基本设置
	 * @param setArr
	 */
	public void save(String[] setArr) {
		for (int i=0; i<setArr.length; i++) {
			PointInfo pointInfo = new PointInfo();
			// 数据格式：var_name=extcredits1,is_use=1,name=铜币,conversion_ratio=100
			// 1.0 按,分割后取值
			String[] arr = setArr[i].split(",");
			String varName = arr[0].split("=")[1];
			String isUse = arr[1].split("=")[1];
			String name = arr[2].split("=").length==1?"":arr[2].split("=")[1];
			String conversionRatio = arr[3].split("=").length==1?"0":arr[3].split("=")[1];
			
			// 2.0 设置属性值，并更新
			pointInfo.setVarName(varName);
			pointInfo.setIsUse(isUse);
			pointInfo.setName(name);
			pointInfo.setConversionRatio(conversionRatio);
			
			iPointInfoDAO.update(pointInfo);
		}
	}

}
