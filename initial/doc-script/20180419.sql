 CREATE TABLE `language` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT 0 COMMENT '用户id',
  `language_type` int(11) DEFAULT '2' COMMENT '语言类型\n1汉语 2英语  3日语  4韩语  5德语  6法语  7意大利  8西班牙  9俄语  10阿拉伯语  11印度语  12越南语  13马来语  14泰语 15其他',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识,0.可用，1.已删除不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3232 DEFAULT CHARSET=utf8;

-- insert into language(user_id,language_type,is_deleted) select users_id,language_tpye,is_deleted from gls_language;

CREATE TABLE `contact` (
  `contact_Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `customer_id` bigint(20) DEFAULT '0' COMMENT 'crm客户表ID',
  `user_Id` int(10) DEFAULT '0' COMMENT '公司ID',
  `name` varchar(255) DEFAULT '' COMMENT '姓名',
  `job` varchar(50) DEFAULT '' COMMENT '职位',
  `mobile` varchar(100) DEFAULT '' COMMENT '手机',
  `telephone` varchar(100) DEFAULT '' COMMENT '电话',
  `address` varchar(255) DEFAULT '' COMMENT '地址',
  `zipcode` varchar(6) DEFAULT '' COMMENT '邮编',
  `fax` varchar(50) DEFAULT '' COMMENT '传真',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `create_date` datetime DEFAULT current_timestamp() COMMENT '录入时间',
  `department` varchar(50) DEFAULT '' COMMENT '部门',
  `imgUrl` varchar(255) DEFAULT '' COMMENT '头像',
  `is_key` int(10) DEFAULT '0' COMMENT '是否主要联系人（0-不是，1-是）',
  `facebook` varchar(30) DEFAULT '' COMMENT 'facebook',
  `type1` int(11) DEFAULT '0' COMMENT '0有审厂报告  1 无审厂报告',
  `contact_name` varchar(100) DEFAULT '' COMMENT '联系人姓名',
  `position` varchar(10) DEFAULT '' COMMENT '位置',
  `contact_phone` varchar(100) DEFAULT '' COMMENT '联系人手机号',
  `contact_fax` varchar(15) DEFAULT '' COMMENT '联系人传真',
  `skype` varchar(10) DEFAULT '' COMMENT 'skype',
  `wechat` varchar(10) DEFAULT '' COMMENT '微信',
  `linkedin` varchar(20) DEFAULT '' COMMENT '领英',
  `contact_remarks` varchar(100) DEFAULT '' COMMENT '备注',
  `status` int(2) DEFAULT 0 COMMENT '联系人状态(0为激活，1为禁用)',
  `birthday` datetime DEFAULT '1990-01-01' COMMENT '生日',
  `qq` varchar(15) DEFAULT '' COMMENT 'QQ',
  `twitter` varchar(20) DEFAULT '' COMMENT 'Twitter',
  `msn` varchar(20) DEFAULT '' COMMENT 'MSN',
  `sex` varchar(6) DEFAULT '' COMMENT '性别  不存1/2的话,国际化男女字符数要调整',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`contact_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=85271 DEFAULT CHARSET=utf8 COMMENT='联系人信息表';

-- insert into contact select * from gls_contact;

CREATE TABLE `email_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '邮件内容',
  `user_id` int(11) DEFAULT 0 COMMENT '用户ID',
  `subject` varchar(200) DEFAULT '' COMMENT '邮件主题  ',
  `sent_date` datetime DEFAULT current_timestamp() COMMENT '发送日期',
  `reply_sign` int(11) DEFAULT 0 COMMENT '邮件是否需要回执 0不需要 1 需要',
  `is_read` int(11) DEFAULT 0 COMMENT '是否为已读  0 未读 1 已读',
  `contain_attachment` int(11) DEFAULT 0 COMMENT '邮件是否包含附件 0 没附件 1 有附件',
  `atachment_name` varchar(200) DEFAULT '' COMMENT '附件名称',
  `attachment_path` varchar(1000) DEFAULT '' COMMENT '附件的保存路径',
  `eform` varchar(100) DEFAULT '' COMMENT '发件人的地址和姓名 ',
  `eto` varchar(200) DEFAULT '' COMMENT '邮件接收人',
  `cc` varchar(200) DEFAULT '' COMMENT '邮件抄送人员',
  `bcc` varchar(100) DEFAULT '' COMMENT '邮件密送人员',
  `message_id` varchar(100) DEFAULT '' COMMENT '邮件标识(判断邮件是否存在)',
  `body_content` longtext COMMENT '邮件正文',
  `state` int(11) DEFAULT 3 COMMENT '状态：1 自己发的  2 收到的   3 表示未发送（草稿）',
  `flag` int(20) DEFAULT 0 COMMENT '标志（1:转发2:回复3：回复全部）',
  `size` varchar(200) DEFAULT '' COMMENT '邮件大小',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8385 DEFAULT CHARSET=utf8;

-- insert into email_content select * from emailcontent;

 CREATE TABLE `email_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '发送用户与收件用户关系表',
  `send_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '发送用户id',
  `receive_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '收件用户id',
  `con_id` int(11) NOT NULL DEFAULT 0 COMMENT '邮件id',
  `rtime` date NOT NULL DEFAULT 0 COMMENT '记录时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- insert into email_relation select * from emailrelation;

CREATE TABLE `email_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户的邮件账号信息',
  `account` varchar(50) DEFAULT '' COMMENT '邮箱账号',
  `pwd` varchar(100) DEFAULT '' COMMENT '邮箱密码',
  `smtp_host` varchar(100) DEFAULT '' COMMENT '发送邮件服务器地址',
  `smtp_port` int(11) DEFAULT 25 COMMENT '发送邮件服务器',
  `pop_host` varchar(100) DEFAULT '' COMMENT '接收邮件服务器地址',
  `pop_port` int(11) DEFAULT 110 COMMENT '接收邮件服务器端口',
  `user_id` int(11) DEFAULT -10000 COMMENT '用户ID',
  `nickname` varchar(45) DEFAULT '' COMMENT '昵称名字',
  `sign` longtext COMMENT '用户签名',
  `sign_enabled` int(11) DEFAULT 0 COMMENT '是否启用签名（0-不是，1-是）',
  `pop_ssl` int(5) DEFAULT 1 COMMENT 'ssl协议，0为否，1为是',
  `smtp_ssl` int(5) DEFAULT 1 COMMENT 'ssl协议',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=273 DEFAULT CHARSET=utf8; 

-- insert into email_user select * from emailuser;

 CREATE TABLE `supplier_relationship` (
  `sr_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `sid` int(10) DEFAULT -10000 COMMENT '公司id(供应商)',
  `cid` int(10) DEFAULT -10000 COMMENT '客户id(采购商)',
  `local_id` int(11) DEFAULT -10000 COMMENT '本地顾问Id',
  `type` int(10) DEFAULT -1 COMMENT '0-交易过的(供-采)，1-新增的(供-采),2-报过价的(供-采)，4-产销对接(供-本)，6-服务（采-本）7-新增（本-采）8-新增（采-供）9-(采-本) 10-新增（本-供）',
  `is_follow` int(11) DEFAULT 0 COMMENT '对于采购商标志是否正在follow（0-不是，1-是）',
  `follow_count` int(11) DEFAULT 0 COMMENT '针对本-采的跟单数量（本地顾问可跟同一个采购商多张单）',
  `is_serviced` int(11) DEFAULT 0 COMMENT '对于采购商是否已服务过（0-不是，1-是）',
  `is_success` int(11) DEFAULT -1 COMMENT '针对供-本产销对接情况（-2-解除，-1-失败，0-申请中，1-成功）',
  `dj_no` varchar(255) DEFAULT '' COMMENT '供-本产销对接流水号',
  `create_date` datetime DEFAULT current_timestamp COMMENT '录入时间',
  `cost` varchar(20) DEFAULT 0 COMMENT '产销对接服务费,内部外部以逗号隔开（暂时）',
  `flag` int(5) DEFAULT 0 COMMENT '客户类型：1.已合作客户，2.重要客户，3.潜在客户',
  `inside_outside` int(1) unsigned zerofill DEFAULT 1 COMMENT '0-内部，1-外部',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`sr_id`),
  UNIQUE KEY `unique_sc` (`sid`,`cid`) USING BTREE,
  UNIQUE KEY `unique_sl` (`sid`,`local_id`) USING BTREE,
  UNIQUE KEY `unique_cl` (`cid`,`local_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4258 DEFAULT CHARSET=utf8 COMMENT='客户关系表';

-- insert into supplier_relationship select * from gls_suprelationship;

CREATE TABLE `message` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `theme` varchar(255) DEFAULT '' COMMENT '消息主题',
  `time` varchar(20) DEFAULT '',
  `content` varchar(2000) DEFAULT '' COMMENT '消息内容',
  `status` int(20) DEFAULT 0 COMMENT '消息状态0:未读;1:已读通过;-1:已读不通过',
  `message_type` varchar(50) DEFAULT 0 COMMENT '消息类型： 0:所有消息;1:投诉消息;2:审核消息; 3',
  `user_id` int(20) DEFAULT -10000 COMMENT '发送用户id',
  `receive_id` int(20) DEFAULT -10000 COMMENT '接收用户id',
  `message_flag` int(10) DEFAULT 0 COMMENT '0:商机消息提醒 ;1:工作提醒 ;2:消息提醒',
  `order_no` varchar(255) DEFAULT '' COMMENT '订单编号',
  `supplier_id` int(20) DEFAULT -10000 COMMENT '公司ID',
  `product_id` int(20) DEFAULT -10000 COMMENT '产品id',
  `grouporder_id` int(20) DEFAULT -10000 COMMENT '拼单ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8244 DEFAULT CHARSET=utf8 COMMENT='消息表'; 

-- insert into message select * from gls_message 

CREATE TABLE `area` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动增长列',
  `name` char(32) NOT NULL DEFAULT '' COMMENT '中文名称',
  `international` varchar(255) DEFAULT '' COMMENT '国际名称',
  `level` int(2) NOT NULL DEFAULT 1 COMMENT '级别',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '上级',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '区域表';
-- mysql> insert into area select * from gls_area;

CREATE TABLE `user_follow` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(10) DEFAULT -10000 COMMENT '用户id',
  `product_id` int(10) DEFAULT -10000 COMMENT '产品编号',
  `supplier_id` int(11) DEFAULT -10000 COMMENT '供应商ID',
  `type` int(1) DEFAULT 0 COMMENT '类型（0-关注产品，1-关注公司）',
  `is_dayout` int(11) DEFAULT '0' COMMENT '数据是否过期【有没有用】（-1-过期，0-没过期）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=451 DEFAULT CHARSET=utf8  COMMENT '关注表';

-- insert into user_follow select * from gls_user_follow;





