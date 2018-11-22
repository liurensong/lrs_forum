package cn.javaex.uscat.action.user_profile_info;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.upload_info.UploadInfoService;
import cn.javaex.uscat.service.user_info.UserInfoService;
import cn.javaex.uscat.service.user_profile_info.UserProfileInfoService;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.UploadInfo;
import cn.javaex.uscat.view.UserInfo;
import cn.javaex.uscat.view.UserProfileInfo;

@Controller
@RequestMapping("forum/user_profile_info")
public class UserProfileInfoAction {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserProfileInfoService userProfileInfoService;
	@Autowired
	private UploadInfoService uploadInfoService;
	
	/**
	 * 保存用户头像
	 * @param request
	 * @param avatar
	 * @param userToken
	 * @return
	 * @throws IOException
	 * @throws QingException
	 */
	@RequestMapping("save_avatar.json")
	@ResponseBody
	public Result saveAvatar(
			HttpServletRequest request,
			@RequestParam(value="avatar") String avatar) throws QingException, IOException {
		
		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			// 未登录
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		
		UploadInfo uploadInfo = uploadInfoService.selectByType("qiniu");
		String imgUrl = uploadInfoService.uploadAvatarByBase64(avatar, uploadInfo);
		
		// 保存用户头像
		UserProfileInfo userProfileInfo = new UserProfileInfo();
		userProfileInfo.setAvatar(imgUrl);
		userProfileInfo.setUserId(userInfo.getId());
		
		userProfileInfoService.save(userProfileInfo);
		
		// 重新设置session
		userInfo = userInfoService.updateUserInfoSession(request);
		request.getSession().setAttribute("userInfo", userInfo);
		
		return Result.success();
	}
}
