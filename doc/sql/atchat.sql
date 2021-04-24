

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_chat_msg
-- ----------------------------
DROP TABLE IF EXISTS `t_chat_msg`;
CREATE TABLE `t_chat_msg`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `send_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送人',
  `accept_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接收人',
  `message` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `message_flag` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息类型',
  `send_time` datetime(0) NOT NULL COMMENT '发送时间',
  `sign_flag` int(2) NULL DEFAULT 0 COMMENT '签收状态，是否已读',
  `sign_time` datetime(0) NULL DEFAULT NULL COMMENT '签收时间',
  `room_id` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '聊天室id',
  `status` int(2) NULL DEFAULT NULL COMMENT '消息状态 0在线传输 1离线数据 2推送 3系统消息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '聊天信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_chat_room
-- ----------------------------
DROP TABLE IF EXISTS `t_chat_room`;
CREATE TABLE `t_chat_room`  (
  `id` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(3) NULL DEFAULT NULL,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_feedback
-- ----------------------------
DROP TABLE IF EXISTS `t_feedback`;
CREATE TABLE `t_feedback`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `md` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `app_version` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contact` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `score` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `files` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_friends
-- ----------------------------
DROP TABLE IF EXISTS `t_friends`;
CREATE TABLE `t_friends`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '我的id',
  `friends_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友id',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '成为好友时间',
  `friend_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友备注',
  `level` int(2) NULL DEFAULT NULL COMMENT '关系级别',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_friends_userid`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '好友关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_friends_request
-- ----------------------------
DROP TABLE IF EXISTS `t_friends_request`;
CREATE TABLE `t_friends_request`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `send_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友申请发送方',
  `accept_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友申请接收方',
  `status` int(2) NULL DEFAULT NULL COMMENT '申请状态',
  `request_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `accept_time` datetime(0) NULL DEFAULT NULL COMMENT '同意时间',
  `send_remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发送请求备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_friends_req_accept`(`accept_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '好友申请' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_mail_msg
-- ----------------------------
DROP TABLE IF EXISTS `t_mail_msg`;
CREATE TABLE `t_mail_msg`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `send_mail_id` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送邮件账户',
  `accept_mail_id` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接收邮件账户',
  `mail_message` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱信息',
  `mail_send_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `mail_sign` int(2) NULL DEFAULT NULL COMMENT '是否签收',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '邮箱列表信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `username` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `usercode` int(25) NULL DEFAULT NULL COMMENT '用户唯一码',
  `nickname` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `intro` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `portrait_raw` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像原版',
  `portrait_thumb` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像缩略',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `account_level` int(3) NULL DEFAULT NULL COMMENT '账户等级',
  `coin` int(10) NULL DEFAULT NULL COMMENT '硬币',
  `qrcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扫码添加好友',
  `device_id` char(65) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机设备id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_unique_user_username`(`username`) USING BTREE COMMENT '用户名唯一',
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `t_user_profile`;
CREATE TABLE `t_user_profile`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `sex` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `identity_card` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证id',
  `current_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前住址',
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `f_key_user_id` FOREIGN KEY (`id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户详细信息（不经常变动和查询的数据）' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
