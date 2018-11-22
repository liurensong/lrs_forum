package cn.javaex.uscat.action.board_group_perm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.javaex.uscat.service.board_group_perm.BoardGroupPermService;
import cn.javaex.uscat.view.BoardGroupPerm;
import cn.javaex.uscat.view.Result;

@Controller
@RequestMapping("board_group_perm")
public class BoardGroupPermAction {

	@Autowired
	private BoardGroupPermService boardGroupPermService;
	
	/**
	 * 保存板块与用户组之间的权限设置
	 * @param fid 板块id
	 * @param isDefaultPerm 是否启用默认权限
	 * @param groupIdArr 用户组id数组
	 * @param isViewBoardArr 是否允许浏览板块数组
	 * @param isPostArr 是否允许发新帖数组
	 * @param isReplyArr 是否允许回复帖子数组
	 * @param isDownloadAttachmentArr 是否允许下载附件数组
	 * @param isUploadAttachmentArr 是否允许上传附件数组
	 * @return
	 */
	@RequestMapping("save.json")
	@ResponseBody
	public Result save(
			@RequestParam(value="fid") String fid,
			@RequestParam(value="isDefaultPerm") String isDefaultPerm,
			@RequestParam(value="groupIdArr") String[] groupIdArr,
			@RequestParam(value="isViewBoardArr") String[] isViewBoardArr,
			@RequestParam(value="isPostArr") String[] isPostArr,
			@RequestParam(value="isReplyArr") String[] isReplyArr,
			@RequestParam(value="isDownloadAttachmentArr") String[] isDownloadAttachmentArr,
			@RequestParam(value="isUploadAttachmentArr") String[] isUploadAttachmentArr) {
		
		List<BoardGroupPerm> boardGroupPermList = new ArrayList<BoardGroupPerm>();
		
		// 遍历会员用户组id数组
		for (int i=0; i<groupIdArr.length; i++) {
			BoardGroupPerm boardGroupPerm = new BoardGroupPerm();
			boardGroupPerm.setFid(fid);
			boardGroupPerm.setGroupId(groupIdArr[i]);
			boardGroupPerm.setisViewBoard(isViewBoardArr[i]);
			boardGroupPerm.setIsPost(isPostArr[i]);
			boardGroupPerm.setIsReply(isReplyArr[i]);
			boardGroupPerm.setIsDownloadAttachment(isDownloadAttachmentArr[i]);
			boardGroupPerm.setIsUploadAttachment(isUploadAttachmentArr[i]);
			
			boardGroupPermList.add(boardGroupPerm);
		}
		
		boardGroupPermService.save(fid, isDefaultPerm, boardGroupPermList);
		
		return Result.success();
	}
}
