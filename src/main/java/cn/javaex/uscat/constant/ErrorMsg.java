package cn.javaex.uscat.constant;


public class ErrorMsg {
	
	// 用户注册、登录、修改错误提示
	/** 用户名或密码不能为空 */
	public static final String ERROR_100001 = "账号或密码不能为空";
	/** 用户名或密码错误 */
	public static final String ERROR_100002 = "账号或密码错误";
	/** 该账号已被管理员封禁 */
	public static final String ERROR_100003 = "该账号已被管理员封禁";
	/** 账号不能为空 */
	public static final String ERROR_100004 = "账号不能为空";
	/** 账号长度应为2到10个字符 */
	public static final String ERROR_100005 = "账号长度应为2到10个字符";
	/** 密码不能为空 */
	public static final String ERROR_100006 = "密码不能为空";
	/** 密码长度应为6到16个字符 */
	public static final String ERROR_100007 = "密码长度应为6到16个字符";
	/** 邮箱不能为空 */
	public static final String ERROR_100008 = "邮箱不能为空";
	/** 账号已被占用 */
	public static final String ERROR_100009 = "账号已被占用";
	/** 邮箱已被占用 */
	public static final String ERROR_100010 = "邮箱已被占用";
	/** 注册失败，请稍后重试 */
	public static final String ERROR_100011 = "注册失败，请稍后重试";
	/** 未登录 */
	public static final String ERROR_100012 = "未登录";
	/** 验证码不能为空 */
	public static final String ERROR_100013 = "验证码不能为空";
	/** 验证码不正确 */
	public static final String ERROR_100014 = "验证码不正确";
	/** 验证码已过期 */
	public static final String ERROR_100015 = "验证码已过期";
	/** 新邮箱不能与旧邮箱相同 */
	public static final String ERROR_100016 = "新邮箱不能与旧邮箱相同";
	/** 新邮箱已被占用，如果忘记密码，可以找回 */
	public static final String ERROR_100017 = "新邮箱已被占用，如果忘记密码，可以找回";
	/** 该邮箱尚未绑定 */
	public static final String ERROR_100018 = "该邮箱尚未绑定";
	/** 该账号尚未激活 */
	public static final String ERROR_100019 = "该账号尚未激活";
	/** 邮箱格式不正确 */
	public static final String ERROR_100020 = "邮箱格式不正确";
	/** 验证失败，请尝试重新发送邮件 */
	public static final String ERROR_100021 = "验证失败，请尝试重新发送邮件";
	/** 旧密码填写错误 */
	public static final String ERROR_100022 = "旧密码填写错误";
	/** 非法注册 */
	public static final String ERROR_100023 = "非法注册";
	
	// 用户组校验
	/** 组头衔禁止重复 */
	public static final String ERROR_200001 = "组头衔禁止重复";
	/** 存在已被使用的用户组，无法删除 */
	public static final String ERROR_200002 = "存在已被使用的用户组，无法删除";
	/** 用户组积分不能重复 */
	public static final String ERROR_200003 = "用户组积分不能重复";
	/** 至少保留积分下限为0的会员用户组 */
	public static final String ERROR_200004 = "至少保留积分下限为0的会员用户组";
	
	// 后台操作校验
	/** 该分区下尚有帖子存在，无法删除 */
	public static final String ERROR_300001 = "该板块下尚有帖子存在，无法删除";
	/** 该板块下尚有帖子存在，无法删除 */
	public static final String ERROR_300002 = "该分区下尚有帖子存在，无法删除";
	/** 参数丢失，可能是网络异常 */
	public static final String ERROR_300003 = "参数丢失，可能是网络异常";
	
	// 接口校验
	/** 至少选择一个板块作为数据来源 */
	public static final String ERROR_400001 = "至少选择一个板块作为数据来源";
	/** 指定接口不存在 */
	public static final String ERROR_400002 = "指定接口不存在";
	/** 登录后才能下载 */
	public static final String ERROR_400003 = "登录后才能下载";
	
	// 图片上传校验
	/** 上传失败，可能是远程图片做了防盗链处理 */
	public static final String ERROR_500001 = "上传失败，可能是远程图片做了防盗链处理";
	/** 上传失败，可能是网络不稳定 */
	public static final String ERROR_500002 = "上传失败，可能是网络不稳定";
	/** 上传失败，图片裁剪时发生异常 */
	public static final String ERROR_500003 = "上传失败，图片裁剪时发生异常";
	
	// 帖子校验
	/** 必须上传头像后才能发帖 */
	public static final String ERROR_600001 = "必须上传头像后才能发帖";
	/** 必须验证邮箱后才能发帖 */
	public static final String ERROR_600002 = "必须验证邮箱后才能发帖";
	/** 2次发帖时间间隔太短 */
	public static final String ERROR_600003 = "2次发帖时间间隔太短";
	/** 标题字数超过80字 */
	public static final String ERROR_600004 = "标题字数超过80字";
	/** 主题不存在 */
	public static final String ERROR_600005 = "主题不存在";
	/** 您所在用户组没有权限执行删除操作 */
	public static final String ERROR_600006 = "您所在用户组没有权限执行删除操作";
	/** 权限不足 */
	public static final String ERROR_600007 = "权限不足";
	
	// 邮件相关
	/** 设置发信人失败 */
	public static final String ERROR_120001 = "设置发信人失败";
	/** 您的邮箱已满，请清理后重试 */
	public static final String ERROR_120002 = "您的邮箱已满，请清理后重试";
	/** 您的邮件账号可能输入错误 */
	public static final String ERROR_120003 = "您的邮件账号可能输入错误";
	/** 发信人的身份认证失败 */
	public static final String ERROR_120004 = "发信人的身份认证失败";
	/** 您的信箱大小受到限制 */
	public static final String ERROR_120005 = "您的信箱大小受到限制";
	/** 发信人的身份认证失败 */
	public static final String ERROR_120006 = "发信人的身份认证失败";
	/** 系统异常，请稍后重试 */
	public static final String ERROR_120007 = "系统异常，请稍后重试";
	/** 该网站未开启邮件发送服务 */
	public static final String ERROR_120008 = "该网站未开启邮件发送服务";
}
