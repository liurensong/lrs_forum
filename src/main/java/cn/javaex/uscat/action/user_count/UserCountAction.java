package cn.javaex.uscat.action.user_count;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.constant.ErrorMsg;
import cn.javaex.uscat.exception.QingException;
import cn.javaex.uscat.service.group_info.GroupInfoService;
import cn.javaex.uscat.service.user_count.UserCountService;
import cn.javaex.uscat.service.user_info.UserInfoService;
import cn.javaex.uscat.view.GroupInfo;
import cn.javaex.uscat.view.Result;
import cn.javaex.uscat.view.UserCount;
import cn.javaex.uscat.view.UserInfo;

@Controller
@RequestMapping("forum/user_count")
public class UserCountAction {

	@Autowired
	private UserCountService userCountService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private GroupInfoService groupInfoService;
	
	/**
	 * 获取用户当前积分和晋升下一用户组所需的最低积分
	 * @param request
	 * @return
	 * @throws QingException
	 */
	@RequestMapping("select_point.json")
	@ResponseBody
	public Result selectPoint(HttpServletRequest request) throws QingException {

		UserInfo userInfo = userInfoService.getUserInfo(request);
		if (userInfo==null) {
			throw new QingException(ErrorMsg.ERROR_100012);
		}
		
		UserCount userCount = userCountService.selectByUserId(userInfo.getId());
		
		GroupInfo groupInfo = groupInfoService.selectByPoint(userCount.getPoint());
		
		return Result.success()
				.add("groupType", userInfo.getGroupType())
				.add("curPoint", userCount.getPoint())
				.add("nextPoint", groupInfo.getPoint())
				;
	}
	
}
