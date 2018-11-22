/*
Navicat MySQL Data Transfer

Source Server         : uscat演示站
Source Server Version : 50538
Source Host           : mysql.sql115.cdncenter.net:3306
Source Database       : sq_uscat

Target Server Type    : MYSQL
Target Server Version : 50538
File Encoding         : 65001

Date: 2018-07-29 21:24:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activate_info
-- ----------------------------
DROP TABLE IF EXISTS `activate_info`;
CREATE TABLE `activate_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `type` varchar(30) NOT NULL,
  `code` varchar(10) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activate_info
-- ----------------------------

-- ----------------------------
-- Table structure for api_info
-- ----------------------------
DROP TABLE IF EXISTS `api_info`;
CREATE TABLE `api_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `sort` smallint(4) NOT NULL,
  `type` varchar(10) NOT NULL,
  `fid` varchar(100) DEFAULT NULL,
  `order_field` varchar(50) DEFAULT NULL,
  `cycle` smallint(2) DEFAULT NULL,
  `num` smallint(2) DEFAULT NULL,
  `cache_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of api_info
-- ----------------------------

-- ----------------------------
-- Table structure for board_group_perm
-- ----------------------------
DROP TABLE IF EXISTS `board_group_perm`;
CREATE TABLE `board_group_perm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fid` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `is_view_board` tinyint(1) DEFAULT NULL,
  `is_post` tinyint(1) DEFAULT NULL,
  `is_reply` tinyint(1) DEFAULT NULL,
  `is_download_attachment` tinyint(1) DEFAULT NULL,
  `is_upload_attachment` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of board_group_perm
-- ----------------------------

-- ----------------------------
-- Table structure for board_info
-- ----------------------------
DROP TABLE IF EXISTS `board_info`;
CREATE TABLE `board_info` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL,
  `sort` smallint(4) NOT NULL,
  `name` varchar(50) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `width` smallint(3) DEFAULT NULL,
  `height` smallint(3) DEFAULT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `is_default_perm` tinyint(1) DEFAULT NULL,
  `is_subject_class` tinyint(1) DEFAULT NULL,
  `default_order_field` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of board_info
-- ----------------------------

-- ----------------------------
-- Table structure for board_moderator
-- ----------------------------
DROP TABLE IF EXISTS `board_moderator`;
CREATE TABLE `board_moderator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fid` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of board_moderator
-- ----------------------------

-- ----------------------------
-- Table structure for channel_info
-- ----------------------------
DROP TABLE IF EXISTS `channel_info`;
CREATE TABLE `channel_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sort` smallint(3) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `template` varchar(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel_info
-- ----------------------------

-- ----------------------------
-- Table structure for download_count
-- ----------------------------
DROP TABLE IF EXISTS `download_count`;
CREATE TABLE `download_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sort` smallint(4) NOT NULL,
  `name` varchar(50) NOT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `is_login` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of download_count
-- ----------------------------

-- ----------------------------
-- Table structure for email_info
-- ----------------------------
DROP TABLE IF EXISTS `email_info`;
CREATE TABLE `email_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `smtp` varchar(50) NOT NULL,
  `port` smallint(6) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of email_info
-- ----------------------------

-- ----------------------------
-- Table structure for friend_link
-- ----------------------------
DROP TABLE IF EXISTS `friend_link`;
CREATE TABLE `friend_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sort` smallint(3) NOT NULL,
  `name` varchar(100) NOT NULL,
  `url` varchar(255) NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friend_link
-- ----------------------------
INSERT INTO `friend_link` VALUES ('4', '1', 'javaex前端框架', 'http://www.javaex.cn/', null);
INSERT INTO `friend_link` VALUES ('5', '2', '妖气山视频管理系统', 'http://173cms.javaex.cn/', null);

-- ----------------------------
-- Table structure for group_info
-- ----------------------------
DROP TABLE IF EXISTS `group_info`;
CREATE TABLE `group_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `type` varchar(10) NOT NULL,
  `point` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of group_info
-- ----------------------------
INSERT INTO `group_info` VALUES ('1', '管理员', 'system', null);
INSERT INTO `group_info` VALUES ('2', '超级版主', 'system', null);
INSERT INTO `group_info` VALUES ('3', '版主', 'system', null);
INSERT INTO `group_info` VALUES ('4', '游客', 'visitor', null);
INSERT INTO `group_info` VALUES ('5', '新手上路', 'user', '0');
INSERT INTO `group_info` VALUES ('6', '注册会员', 'user', '50');
INSERT INTO `group_info` VALUES ('7', '中级会员', 'user', '200');
INSERT INTO `group_info` VALUES ('8', '高级会员', 'user', '500');
INSERT INTO `group_info` VALUES ('9', '金牌会员', 'user', '1000');
INSERT INTO `group_info` VALUES ('10', '论坛元老', 'user', '3000');

-- ----------------------------
-- Table structure for nav_info
-- ----------------------------
DROP TABLE IF EXISTS `nav_info`;
CREATE TABLE `nav_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sort` smallint(3) NOT NULL,
  `name` varchar(50) NOT NULL,
  `link` varchar(255) NOT NULL,
  `type` varchar(50) NOT NULL,
  `is_index` tinyint(1) NOT NULL,
  `is_use` tinyint(1) NOT NULL,
  `channel_id` smallint(3) DEFAULT NULL,
  `is_download_count` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of nav_info
-- ----------------------------
INSERT INTO `nav_info` VALUES ('1', '1', '首页', 'portal/index.action', 'system', '0', '0', null, '0');
INSERT INTO `nav_info` VALUES ('2', '2', '论坛', 'forum/index.action', 'system', '1', '1', null, '0');
INSERT INTO `nav_info` VALUES ('3', '3', 'Javaex文档', 'http://doc.javaex.cn/javaex/', 'user', '0', '1', null, '0');
INSERT INTO `nav_info` VALUES ('4', '4', 'SSM文档', 'http://doc.javaex.cn/ssm/', 'user', '0', '1', null, '0');

-- ----------------------------
-- Table structure for point_info
-- ----------------------------
DROP TABLE IF EXISTS `point_info`;
CREATE TABLE `point_info` (
  `id` int(11) NOT NULL,
  `var_name` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `conversion_ratio` int(11) DEFAULT NULL,
  `is_use` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of point_info
-- ----------------------------
INSERT INTO `point_info` VALUES ('1', 'extcredits1', '铜币', '100', '1');
INSERT INTO `point_info` VALUES ('2', 'extcredits2', '银币', '10', '1');
INSERT INTO `point_info` VALUES ('3', 'extcredits3', '金币', '1', '1');
INSERT INTO `point_info` VALUES ('4', 'extcredits4', '贡献', '0', '1');
INSERT INTO `point_info` VALUES ('5', 'extcredits5', '狗牌', '0', '1');
INSERT INTO `point_info` VALUES ('6', 'extcredits6', '', '0', '0');

-- ----------------------------
-- Table structure for point_rule
-- ----------------------------
DROP TABLE IF EXISTS `point_rule`;
CREATE TABLE `point_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `action` varchar(50) NOT NULL,
  `cycle` varchar(50) NOT NULL,
  `reward_num` varchar(50) NOT NULL,
  `extcredits1` smallint(5) NOT NULL,
  `extcredits2` smallint(5) NOT NULL,
  `extcredits3` smallint(5) NOT NULL,
  `extcredits4` smallint(5) NOT NULL,
  `extcredits5` smallint(5) NOT NULL,
  `extcredits6` smallint(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of point_rule
-- ----------------------------
INSERT INTO `point_rule` VALUES ('1', '签到', 'signin', '每天', '1', '2', '0', '0', '0', '0', '0');
INSERT INTO `point_rule` VALUES ('2', '发表主题', 'post', '每天', '10', '2', '0', '0', '0', '0', '0');
INSERT INTO `point_rule` VALUES ('3', '发表回复', 'reply', '每天', '40', '1', '0', '0', '0', '0', '0');
INSERT INTO `point_rule` VALUES ('4', '加精华', 'digest', '不限', '不限', '0', '0', '0', '5', '0', '0');
INSERT INTO `point_rule` VALUES ('5', '取消精华', 'undigest', '不限', '不限', '0', '0', '0', '-5', '0', '0');
INSERT INTO `point_rule` VALUES ('6', '删除主题', 'delpost', '不限', '不限', '-2', '0', '0', '0', '0', '0');
INSERT INTO `point_rule` VALUES ('7', '删除回复', 'delreply', '不限', '不限', '-1', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for register_setting
-- ----------------------------
DROP TABLE IF EXISTS `register_setting`;
CREATE TABLE `register_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_allow_register` tinyint(1) DEFAULT NULL,
  `close_register_message` varchar(255) DEFAULT NULL,
  `is_check_email` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of register_setting
-- ----------------------------
INSERT INTO `register_setting` VALUES ('1', '1', '', '0');

-- ----------------------------
-- Table structure for report_info
-- ----------------------------
DROP TABLE IF EXISTS `report_info`;
CREATE TABLE `report_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reply_id` int(11) NOT NULL,
  `report_content` varchar(255) DEFAULT NULL,
  `report_user_id` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_info
-- ----------------------------

-- ----------------------------
-- Table structure for security_info
-- ----------------------------
DROP TABLE IF EXISTS `security_info`;
CREATE TABLE `security_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `report_time_interval` int(11) NOT NULL,
  `is_activate_email` tinyint(1) NOT NULL,
  `is_upload_avatar` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of security_info
-- ----------------------------
INSERT INTO `security_info` VALUES ('1', '0', '0', '0');

-- ----------------------------
-- Table structure for seo_info
-- ----------------------------
DROP TABLE IF EXISTS `seo_info`;
CREATE TABLE `seo_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seo_info
-- ----------------------------
INSERT INTO `seo_info` VALUES ('1', 'javaex - java程序员写的前端框架', 'javaex,前端框架', 'javaex - java程序员写的前端框架', 'portal');
INSERT INTO `seo_info` VALUES ('2', 'javaex - java程序员写的前端框架', 'javaex,前端框架', 'javaex - java程序员写的前端框架', 'forum');

-- ----------------------------
-- Table structure for slide_info
-- ----------------------------
DROP TABLE IF EXISTS `slide_info`;
CREATE TABLE `slide_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_id` int(11) NOT NULL,
  `sort` smallint(2) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `summary` varchar(100) DEFAULT NULL,
  `image_small` varchar(255) DEFAULT NULL,
  `image_big` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of slide_info
-- ----------------------------

-- ----------------------------
-- Table structure for subject_class
-- ----------------------------
DROP TABLE IF EXISTS `subject_class`;
CREATE TABLE `subject_class` (
  `subject_id` int(11) NOT NULL AUTO_INCREMENT,
  `fid` int(11) NOT NULL,
  `sort` smallint(3) NOT NULL,
  `name_html` varchar(100) NOT NULL,
  `name_text` varchar(50) NOT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject_class
-- ----------------------------

-- ----------------------------
-- Table structure for template_info
-- ----------------------------
DROP TABLE IF EXISTS `template_info`;
CREATE TABLE `template_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(10) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of template_info
-- ----------------------------
INSERT INTO `template_info` VALUES ('1', 'pc', 'default');
INSERT INTO `template_info` VALUES ('2', 'm', 'default');

-- ----------------------------
-- Table structure for thread_info
-- ----------------------------
DROP TABLE IF EXISTS `thread_info`;
CREATE TABLE `thread_info` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `fid` int(11) NOT NULL,
  `subject_id` varchar(4) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `create_user_id` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `view_count` int(11) NOT NULL,
  `view_count_day` int(11) DEFAULT NULL,
  `view_count_week` int(11) DEFAULT NULL,
  `view_count_month` int(11) DEFAULT NULL,
  `is_top` tinyint(1) DEFAULT NULL,
  `is_digest` tinyint(1) DEFAULT NULL,
  `last_edit_user_id` int(11) DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of thread_info
-- ----------------------------

-- ----------------------------
-- Table structure for thread_reply_info
-- ----------------------------
DROP TABLE IF EXISTS `thread_reply_info`;
CREATE TABLE `thread_reply_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `content_html` varchar(20000) NOT NULL,
  `content_text` varchar(1000) NOT NULL,
  `reply_time` datetime NOT NULL,
  `reply_user_id` int(11) NOT NULL,
  `floor` int(11) NOT NULL,
  `be_reply_id` int(11) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of thread_reply_info
-- ----------------------------

-- ----------------------------
-- Table structure for upload_info
-- ----------------------------
DROP TABLE IF EXISTS `upload_info`;
CREATE TABLE `upload_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `domain` varchar(100) DEFAULT NULL,
  `ak` varchar(100) DEFAULT NULL,
  `sk` varchar(100) DEFAULT NULL,
  `bucket` varchar(50) DEFAULT NULL,
  `compress` smallint(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of upload_info
-- ----------------------------
INSERT INTO `upload_info` VALUES ('1', 'qiniu', 'http://op9mul7kx.bkt.clouddn.com/', 'WRV7MsQJ2wrjSOlogx640kxIys7ZvCNyPeD9Xmwn', 'NyU6BdhO39z3nGqwF4xjKwO9DBahNPpLF0mfu6eE', 'mytest1', '100');

-- ----------------------------
-- Table structure for user_count
-- ----------------------------
DROP TABLE IF EXISTS `user_count`;
CREATE TABLE `user_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `extcredits1` int(11) DEFAULT NULL,
  `extcredits2` int(11) DEFAULT NULL,
  `extcredits3` int(11) DEFAULT NULL,
  `extcredits4` int(11) DEFAULT NULL,
  `extcredits5` int(11) DEFAULT NULL,
  `extcredits6` int(11) DEFAULT NULL,
  `posts` mediumint(8) DEFAULT NULL,
  `threads` mediumint(8) DEFAULT NULL,
  `digestposts` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_count
-- ----------------------------

-- ----------------------------
-- Table structure for user_count_log
-- ----------------------------
DROP TABLE IF EXISTS `user_count_log`;
CREATE TABLE `user_count_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `action` varchar(50) NOT NULL,
  `cycle` varchar(50) NOT NULL,
  `reward_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_count_log
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `is_email_checked` tinyint(1) DEFAULT NULL,
  `register_time` datetime DEFAULT NULL,
  `register_ip` varchar(50) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `last_login_ip` varchar(50) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_point_log
-- ----------------------------
DROP TABLE IF EXISTS `user_point_log`;
CREATE TABLE `user_point_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `extcredits1` smallint(3) DEFAULT NULL,
  `extcredits2` smallint(3) DEFAULT NULL,
  `extcredits3` smallint(3) DEFAULT NULL,
  `extcredits4` smallint(3) DEFAULT NULL,
  `extcredits5` smallint(3) DEFAULT NULL,
  `extcredits6` smallint(3) DEFAULT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_point_log
-- ----------------------------

-- ----------------------------
-- Table structure for user_profile_info
-- ----------------------------
DROP TABLE IF EXISTS `user_profile_info`;
CREATE TABLE `user_profile_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_profile_info
-- ----------------------------

-- ----------------------------
-- Table structure for web_info
-- ----------------------------
DROP TABLE IF EXISTS `web_info`;
CREATE TABLE `web_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `domain` varchar(100) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `record_number` varchar(50) DEFAULT NULL,
  `license` tinyint(1) DEFAULT NULL,
  `statistical_code` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of web_info
-- ----------------------------
INSERT INTO `web_info` VALUES ('1', 'Javaex论坛', 'http://www.javaex.cn/', '291026192@qq.com', '苏ICP备18008530号', '1', '');

-- ----------------------------
-- Table structure for zone_info
-- ----------------------------
DROP TABLE IF EXISTS `zone_info`;
CREATE TABLE `zone_info` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `sort` smallint(4) NOT NULL,
  `name` varchar(50) NOT NULL,
  `color` varchar(20) DEFAULT NULL,
  `row` smallint(2) DEFAULT NULL,
  `is_show` tinyint(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of zone_info
-- ----------------------------

-- ----------------------------
-- Table structure for zone_moderator
-- ----------------------------
DROP TABLE IF EXISTS `zone_moderator`;
CREATE TABLE `zone_moderator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of zone_moderator
-- ----------------------------
