CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id；自动增长列',
  `company_id` int(11) NOT NULL DEFAULT -10000 COMMENT '公司id',
  `name` char(32) NOT NULL DEFAULT '' COMMENT '部门名称',
  `level` int(2) NOT NULL DEFAULT 1 COMMENT '级别',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '上级',
  `full_path` varchar(255) DEFAULT '' COMMENT '部门全路径',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT '部门表';

CREATE TABLE `department_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `department_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '部门id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_deptid_uid` (`department_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='部门-用户关系表'; 


