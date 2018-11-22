package cn.javaex.uscat.service.download_count;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.dao.download_count.IDownloadCountDAO;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.user_info.UserInfoService;
import cn.javaex.uscat.view.DownloadCount;
import cn.javaex.uscat.view.UserInfo;

@Service("DownloadCountService")
public class DownloadCountService {
	@Autowired
	private IDownloadCountDAO iDownloadCountDAO;
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 查询列表
	 * @return
	 */
	public List<DownloadCount> list() {
		return iDownloadCountDAO.list();
	}

	/**
	 * 保存下载计数器接口
	 * @param downloadCountList
	 */
	public void save(List<DownloadCount> downloadCountList) {
		for (DownloadCount downloadCount : downloadCountList) {
			if (StringUtils.isEmpty(downloadCount.getId())) {
				// 新增
				iDownloadCountDAO.insert(downloadCount);
			} else {
				// 更新
				iDownloadCountDAO.update(downloadCount);
			}
		}
	}

	/**
	 * 根据id查询计数器信息
	 * @param id
	 * @return
	 */
	public DownloadCount selectById(String id) {
		return iDownloadCountDAO.selectById(id);
	}

	/**
	 * 删除
	 * @param idArr
	 */
	public void delete(String[] idArr) {
		iDownloadCountDAO.delete(idArr);
	}

	/**
	 * 更新
	 * @param downloadCount
	 */
	public void update(DownloadCount downloadCount) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formatter.format(currentTime);
		downloadCount.setUpdateTime(now);
		iDownloadCountDAO.update(downloadCount);
	}

	/**
	 * 下载次数累加，并返回url
	 * @param request 
	 * @param id
	 * @return
	 * @throws QingException
	 */
	public String updateById(HttpServletRequest request, String id) throws QingException {
		DownloadCount downloadCount = iDownloadCountDAO.selectById(id);
		if (downloadCount!=null) {
			// 判断是否需要登录后才能下载
			if ("1".equals(downloadCount.getIsLogin())) {
				// 获取用户信息
				UserInfo userInfo = userInfoService.getUserInfo(request);
				if (userInfo==null) {
					throw new QingException(ErrorMsg.ERROR_400003);
				}
			}
			
			downloadCount.setCount(downloadCount.getCount()+1);
			iDownloadCountDAO.update(downloadCount);
			
			return downloadCount.getUrl();
		}
		
		return null;
	}
	
}
