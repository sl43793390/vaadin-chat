/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.22.130
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 192.168.22.130:3306
 Source Schema         : chat

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 24/07/2024 09:08:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for emoji
-- ----------------------------
DROP TABLE IF EXISTS `emoji`;
CREATE TABLE `emoji`  (
  `EMO_DESC` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `EMOJI` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`EMO_DESC`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of emoji
-- ----------------------------
INSERT INTO `emoji` VALUES ('一碗热气腾腾的面条', '🍜');
INSERT INTO `emoji` VALUES ('二头肌', '💪');
INSERT INTO `emoji` VALUES ('亲吻', '😗');
INSERT INTO `emoji` VALUES ('光环笑脸', '😇');
INSERT INTO `emoji` VALUES ('厕所', '🚽');
INSERT INTO `emoji` VALUES ('双手合', '🙏');
INSERT INTO `emoji` VALUES ('吐舌头', '😛');
INSERT INTO `emoji` VALUES ('品尝美味食物表情', '😋');
INSERT INTO `emoji` VALUES ('啤酒杯碰杯', '🍻');
INSERT INTO `emoji` VALUES ('四叶草', '🍀');
INSERT INTO `emoji` VALUES ('困', '😪');
INSERT INTO `emoji` VALUES ('坚持', '😣');
INSERT INTO `emoji` VALUES ('大声哭泣的脸', '😭');
INSERT INTO `emoji` VALUES ('太阳镜笑', '😎');
INSERT INTO `emoji` VALUES ('失望得到宽慰的脸', '😥');
INSERT INTO `emoji` VALUES ('开口笑紧闭的眼睛', '😆');
INSERT INTO `emoji` VALUES ('开口笑脸和微笑的眼睛', '😄');
INSERT INTO `emoji` VALUES ('惊讶', '😲');
INSERT INTO `emoji` VALUES ('愤怒', '😠');
INSERT INTO `emoji` VALUES ('棒棒糖', '🍭');
INSERT INTO `emoji` VALUES ('熟睡', '😴');
INSERT INTO `emoji` VALUES ('狗', '🐶');
INSERT INTO `emoji` VALUES ('猪', '🐷');
INSERT INTO `emoji` VALUES ('痛苦', '😧');
INSERT INTO `emoji` VALUES ('眨眼表情', '😉');
INSERT INTO `emoji` VALUES ('笑嘻嘻', '😀');
INSERT INTO `emoji` VALUES ('笑嘻嘻的脸，含笑的眼睛', '😁');
INSERT INTO `emoji` VALUES ('笑脸淌冷汗', '😅');
INSERT INTO `emoji` VALUES ('脸上带着喜悦的泪水', '😂');
INSERT INTO `emoji` VALUES ('花束', '💐');
INSERT INTO `emoji` VALUES ('西瓜', '🍉');
INSERT INTO `emoji` VALUES ('足球', '⚽');
INSERT INTO `emoji` VALUES ('闭眼亲吻', '😚');
INSERT INTO `emoji` VALUES ('面带微笑的眼睛', '😊');
INSERT INTO `emoji` VALUES ('面带心形', '😍');
INSERT INTO `emoji` VALUES ('面无表情', '😑');
INSERT INTO `emoji` VALUES ('飞吻', '😘');
INSERT INTO `emoji` VALUES ('骑自行车', '🚴');
INSERT INTO `emoji` VALUES ('鸡腿', '🍗');
INSERT INTO `emoji` VALUES ('鼓掌', '👏');

-- ----------------------------
-- Table structure for group_info
-- ----------------------------
DROP TABLE IF EXISTS `group_info`;
CREATE TABLE `group_info`  (
  `GROUP_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `GROUP_NAME` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `GROUP_OWNER` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `GROUP_IMG` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `GROUP_ANNOUNCEMENT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `GROUP_STATUS` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `DT_CREATE` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`GROUP_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_info
-- ----------------------------
INSERT INTO `group_info` VALUES ('group1', '组1', 'admin', NULL, NULL, NULL, '2024-07-23 16:36:23');

-- ----------------------------
-- Table structure for group_message
-- ----------------------------
DROP TABLE IF EXISTS `group_message`;
CREATE TABLE `group_message`  (
  `GROUP_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `MESSAGE_ID` bigint(24) NOT NULL,
  `MESSAGE` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IMG_URL` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `FILE_URL` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `FILE_NAME` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DT_SEND` datetime NULL DEFAULT NULL,
  `FLAG_DELETE` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`GROUP_ID`, `MESSAGE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_message
-- ----------------------------
INSERT INTO `group_message` VALUES ('group1', 'admin', 123, 'test', NULL, NULL, NULL, '2024-07-23 16:37:11', NULL);
INSERT INTO `group_message` VALUES ('group1', 'test', 124, 'testwerwerww', NULL, NULL, NULL, '2024-07-23 16:37:11', NULL);
INSERT INTO `group_message` VALUES ('group1', 'qwe', 125, 'qweqweqwe', NULL, NULL, NULL, '2024-07-23 17:18:51', NULL);
INSERT INTO `group_message` VALUES ('group1', 'admin', 1815678773463379968, '🍜', NULL, NULL, NULL, '2024-07-23 17:22:08', NULL);
INSERT INTO `group_message` VALUES ('group1', 'test', 1815680295987343360, '🐶', NULL, NULL, NULL, '2024-07-23 17:28:11', NULL);

-- ----------------------------
-- Table structure for group_user
-- ----------------------------
DROP TABLE IF EXISTS `group_user`;
CREATE TABLE `group_user`  (
  `USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `GROUP_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DT_CREATE` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`USER_ID`, `GROUP_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_user
-- ----------------------------
INSERT INTO `group_user` VALUES ('admin', 'group1', '2024-07-23 16:04:23');
INSERT INTO `group_user` VALUES ('qwe', 'group1', '2024-07-23 17:08:58');
INSERT INTO `group_user` VALUES ('test', 'group1', '2024-07-23 16:13:10');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `PERMISSION_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限ID',
  `RESOURCE_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名',
  `ACTION` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中文名',
  `VERSION` decimal(10, 0) NULL DEFAULT NULL COMMENT '版本',
  `ID_PARENT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父id',
  `NBR_LEVEL` tinyint(1) NULL DEFAULT NULL COMMENT '层级',
  `NBR_ORDER` int(11) NOT NULL COMMENT '序号',
  `TIMESTAMP` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`PERMISSION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES ('300', 'userRoot', '用户管理', NULL, NULL, NULL, 300, '2022-12-03 21:38:00');
INSERT INTO `permissions` VALUES ('301', 'userAdd', '新增用户', NULL, '305', NULL, 308, '2022-12-03 21:31:50');
INSERT INTO `permissions` VALUES ('302', 'deleteUser', '删除用户', NULL, '305', NULL, 307, '2022-12-03 21:32:18');
INSERT INTO `permissions` VALUES ('303', 'updateUser', '修改用户', NULL, '305', NULL, 306, '2022-12-03 21:37:36');
INSERT INTO `permissions` VALUES ('305', 'userMgmt', '用户列表', NULL, '300', NULL, 305, '2022-12-12 21:41:33');
INSERT INTO `permissions` VALUES ('350', 'roleMgmt', '角色管理', NULL, NULL, NULL, 350, '2022-12-12 21:44:03');
INSERT INTO `permissions` VALUES ('351', 'roleAdd', '新增角色', NULL, '350', NULL, 351, '2022-12-12 21:45:16');
INSERT INTO `permissions` VALUES ('352', 'roleUpdate', '修改角色', NULL, '350', NULL, 352, '2022-12-12 21:45:41');
INSERT INTO `permissions` VALUES ('353', 'roleDelete', '删除角色', NULL, '350', NULL, 353, '2022-12-12 21:46:03');
INSERT INTO `permissions` VALUES ('800', 'checkedAllUser', '管理员', NULL, NULL, NULL, 800, '2022-12-03 21:30:43');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `ROLE_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `ID_INSTITUTION` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构ID',
  `ROLE_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `VERSION` tinyint(4) NULL DEFAULT NULL COMMENT '版本',
  `ROLE_DESC` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `TIMESTAMP` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`ID_INSTITUTION`, `ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('admin', '123', 'admin', NULL, 'admin', '2022-12-03 21:29:49');
INSERT INTO `roles` VALUES ('test', '123', 'test', NULL, 'test', NULL);

-- ----------------------------
-- Table structure for roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE `roles_permissions`  (
  `ROLE_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `PERMISSION_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限ID',
  `VERSION` tinyint(4) NULL DEFAULT NULL COMMENT '版本',
  `TIMESTAMP` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`ROLE_ID`, `PERMISSION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles_permissions
-- ----------------------------
INSERT INTO `roles_permissions` VALUES ('admin', '300', NULL, '2024-07-18 16:40:05');
INSERT INTO `roles_permissions` VALUES ('admin', '301', NULL, '2024-07-18 16:40:39');
INSERT INTO `roles_permissions` VALUES ('admin', '302', NULL, '2024-07-18 16:40:39');
INSERT INTO `roles_permissions` VALUES ('admin', '303', NULL, '2024-07-18 16:40:39');
INSERT INTO `roles_permissions` VALUES ('admin', '305', NULL, '2024-07-18 16:40:39');
INSERT INTO `roles_permissions` VALUES ('admin', '350', NULL, '2024-07-18 16:40:39');
INSERT INTO `roles_permissions` VALUES ('admin', '351', NULL, '2024-07-18 16:40:39');
INSERT INTO `roles_permissions` VALUES ('admin', '352', NULL, '2024-07-18 16:40:39');
INSERT INTO `roles_permissions` VALUES ('admin', '353', NULL, '2024-07-18 16:40:39');
INSERT INTO `roles_permissions` VALUES ('admin', '800', NULL, '2024-07-18 16:40:39');
INSERT INTO `roles_permissions` VALUES ('test', '300', NULL, '2024-07-19 10:04:44');
INSERT INTO `roles_permissions` VALUES ('test', '305', NULL, '2024-07-19 10:04:44');
INSERT INTO `roles_permissions` VALUES ('test', '350', NULL, '2024-07-19 10:04:44');

-- ----------------------------
-- Table structure for user_friend
-- ----------------------------
DROP TABLE IF EXISTS `user_friend`;
CREATE TABLE `user_friend`  (
  `USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FRIEND_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '好友',
  `DT_CREATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DT_UPDATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`USER_ID`, `FRIEND_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_friend
-- ----------------------------
INSERT INTO `user_friend` VALUES ('admin', 'qwe', '2024-07-19 09:56:13', '2024-07-19 09:56:13');
INSERT INTO `user_friend` VALUES ('admin', 'test', '2024-07-19 09:52:55', '2024-07-19 09:52:55');
INSERT INTO `user_friend` VALUES ('test', 'admin', '2024-07-19 15:54:54', '2024-07-19 15:54:54');
INSERT INTO `user_friend` VALUES ('test', 'qwe', '2024-07-22 09:34:12', '2024-07-22 09:34:12');
INSERT INTO `user_friend` VALUES ('test', 'zhagnwuji', '2024-07-22 09:38:51', '2024-07-22 09:38:51');
INSERT INTO `user_friend` VALUES ('zhagnwuji', 'test', '2024-07-22 09:46:22', '2024-07-22 09:46:22');

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `GROUP` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DT_CREATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`USER_ID`, `GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_group
-- ----------------------------

-- ----------------------------
-- Table structure for user_message
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message`  (
  `USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送人',
  `RECEIVE_USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接收人',
  `MESSAGE_ID` bigint(24) NOT NULL,
  `MESSAGE` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IMG_URL` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `FILE_URL` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `FILE_NAME` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `EMOJI` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表情',
  `DT_SEND` datetime NULL DEFAULT NULL,
  `FLAG_DELETE` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`USER_ID`, `RECEIVE_USER_ID`, `MESSAGE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_message
-- ----------------------------
INSERT INTO `user_message` VALUES ('admin', 'qwe', 1815650903504490496, '😂', NULL, NULL, NULL, NULL, '2024-07-23 15:31:23', NULL);
INSERT INTO `user_message` VALUES ('admin', 'qwe', 1815650923809116160, '😄', NULL, NULL, NULL, NULL, '2024-07-23 15:31:28', NULL);
INSERT INTO `user_message` VALUES ('admin', 'qwe', 1815655149880774656, '👏', NULL, NULL, NULL, NULL, '2024-07-23 15:48:16', NULL);
INSERT INTO `user_message` VALUES ('admin', 'test', 1, '11111111111111111111111111111111', NULL, NULL, NULL, NULL, '2024-07-19 13:06:59', NULL);
INSERT INTO `user_message` VALUES ('admin', 'test', 2, '22222222222222222222222222222', NULL, NULL, NULL, NULL, '2024-07-19 13:07:16', NULL);
INSERT INTO `user_message` VALUES ('admin', 'test', 3, '2333333333', NULL, NULL, NULL, NULL, '2024-07-19 13:07:16', NULL);
INSERT INTO `user_message` VALUES ('admin', 'test', 7, '23333333337777777', NULL, NULL, NULL, NULL, '2024-07-19 13:07:16', NULL);
INSERT INTO `user_message` VALUES ('admin', 'test', 8, '2333333333888888888888888888888888888888', NULL, NULL, NULL, NULL, '2024-07-19 13:07:16', NULL);
INSERT INTO `user_message` VALUES ('admin', 'test', 9, '9999993339999', NULL, NULL, NULL, NULL, '2024-07-19 13:07:16', NULL);
INSERT INTO `user_message` VALUES ('admin', 'test', 1815576749702983680, '文件发送', NULL, 'http://192.168.190.100:9000/test/sl/test_1721702203589.png', 'test.png', NULL, '2024-07-23 10:36:44', NULL);
INSERT INTO `user_message` VALUES ('test', 'admin', 4, '4444444', NULL, NULL, NULL, NULL, '2024-07-19 13:07:49', NULL);
INSERT INTO `user_message` VALUES ('test', 'admin', 5, '55555', NULL, NULL, NULL, NULL, '2024-07-19 13:07:49', NULL);
INSERT INTO `user_message` VALUES ('test', 'admin', 6, '6666666666666666666666666666666666666666666666666666666666666', NULL, NULL, NULL, NULL, '2024-07-19 13:07:49', NULL);
INSERT INTO `user_message` VALUES ('test', 'admin', 10, '1000000066666666', NULL, NULL, NULL, NULL, '2024-07-19 13:07:49', NULL);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `USER_NAME` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `EMAIL` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `PASSWORD` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CREATE_TIME` datetime NULL DEFAULT NULL,
  `EXPIRE_TIME` datetime NULL DEFAULT NULL,
  `DEPARTMENT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ROLE_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ORGANIZATION` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ID_INSTITUTION` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `VERSION` tinyint(4) NULL DEFAULT NULL COMMENT '版本',
  `CD_PHONE` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `USER_FLAG` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户类型',
  `CD_FROZEN_STATE` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冻结状态',
  `DT_LOGIN` date NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('admin', 'admin', NULL, 'dc1fd00e3eeeb940ff46f457bf97d66ba7fcc36e0b20802383de142860e76ae6', '2022-12-03 20:49:35', NULL, NULL, NULL, NULL, '123', NULL, NULL, NULL, NULL, '2022-12-03');
INSERT INTO `users` VALUES ('demo1', 'demo', '', '126e355563fd922800abe1023fa32f0a6f70ab4300dd4b88f34bece0aa27bb2e', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES ('qwe', 'qwe', 'test', '6cdfdbc1f5b70b1dd580db3b44ba12f3e0070d8eea439573fc9f22aaaf6a073c', NULL, NULL, 'test', NULL, 'test', '123', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES ('test', 'test', 'test', '09d3c4e64ed1fe457d91d234fd4def9b19bf4a0382c553c903f6fd538698b0d9', NULL, NULL, 'test', NULL, 'test', '123', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES ('zhagnwuji', 'wuji', '', '4526a4972ddebedff938cf43dcf47d3d116d9421764aa5d7f8d1c6fdf561dd93', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES ('zhangsan', '张三', 'zhangsan', 'eeb83455b8219fa319726e168f33500e782b17fbe95aa176ebd0d0d2ebcb4d83', NULL, NULL, '', NULL, 'zhangsan', '123', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for users_roles
-- ----------------------------
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles`  (
  `USER_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `ROLE_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `VERSION` tinyint(4) NULL DEFAULT NULL COMMENT '版本',
  `TIMESTAMP` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`USER_ID`, `ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_roles
-- ----------------------------
INSERT INTO `users_roles` VALUES ('admin', 'admin', NULL, '2022-12-03 22:04:39');
INSERT INTO `users_roles` VALUES ('demo1', 'test', NULL, '2024-07-22 09:47:01');
INSERT INTO `users_roles` VALUES ('lisi', 'test', NULL, '2024-07-19 10:25:06');
INSERT INTO `users_roles` VALUES ('test', 'admin', NULL, '2024-07-19 15:58:15');
INSERT INTO `users_roles` VALUES ('zhagnwuji', 'test', NULL, '2024-07-22 09:48:04');
INSERT INTO `users_roles` VALUES ('zhangsan', 'test', NULL, '2024-07-19 10:21:16');

SET FOREIGN_KEY_CHECKS = 1;
