drop table gls_auditreport;
drop table gls_auditreport_language;
drop table gls_auditreport_pdf;

drop table gls_gs_type;
drop table gls_gs_type_copy;
drop table gls_num;
drop table gls_contact;
drop table gls_order;
drop table gls_order_fitting;
drop table gls_order_model;
drop table gls_order_product;
drop table gls_order_remark;
drop table gls_order_remarkfile;
drop table gls_order_schedule;
drop table gls_order_uploadfile;
drop table gls_gs_supplier;
drop table gls_supplier_agent;
drop table gls_supplier_type;
drop table gls_suprelationship;
drop table gls_product;
drop table gls_product_language; 
drop table gls_product_patent;
drop table gls_product_picture;
drop table gls_mainproduct;

drop table gls_quote;
drop table gls_quote_product;
drop table gls_quote_qa;
drop table gls_quote_updatelog;

drop table gls_sku_prolanguage;
drop table gls_sku_provalue_language;
drop table gls_user_follow;
rename table gls_tms_quote to tms_quote;


drop table gls_factory_report;
drop table gls_factory_report_file;

alter table admin modify email  varchar(50) default '' comment '邮件';
alter table contact modify email  varchar(50) default '' comment '邮箱';
alter table customer modify cemail varchar(50) default '' comment '邮箱';
alter table financing_auth modify supplier_email varchar(50) default '' comment '供应商邮箱';
alter table financing_auth modify buyer_email varchar(50) default '' comment '采购商邮箱';
alter table financing_check modify supplier_email varchar(50) default '' comment '供应商邮箱';
alter table financing_check modify buyer_email varchar(50) default '' comment '采购商邮箱';
alter table financing_order modify supplier_email varchar(50) default '' comment '供应商邮箱';
alter table financing_order modify buyer_email varchar(50) default '' comment '采购商邮箱';
alter table gls_contact modify email varchar(50) default '' comment '邮箱';
alter table gls_order_docking modify email varchar(50) default '' comment '邮箱';
alter table gls_order_logistics modify Cr_Contacts_Email varchar(50) default '' comment '客户传真';
alter table gls_order_logistics modify Ce_Contacts_Email varchar(50) default '' comment '0 未付款 1 已交定金 2 已付清';
alter table gls_order_logistics modify Nf_Contacts_Email varchar(50) default '' comment '附件备注1';
alter table gls_purchaseorder modify supplierEmail varchar(50) default '' comment '供应商邮箱';
alter table gls_purchaseorder modify customerEmail varchar(50) default '' comment '客户邮箱';
alter table gls_risk_business modify email varchar(50) default '' comment '邮箱';
alter table gls_tms_orders modify cr_contacts_email varchar(50) default '' comment '发货联系人邮箱';
alter table gls_tms_orders modify ce_contacts_email varchar(50) default '' comment '收货联系人邮箱';
alter table gls_tms_orders modify nf_contacts_email varchar(50) default '' comment '通知联系人邮箱';
alter table gls_video_users modify email varchar(50) default '' comment '邮箱';
alter table `order` modify supplier_email varchar(50) default '' comment '供应商邮箱';
alter table `order` modify customer_email varchar(50) default '' comment '客户邮箱';
alter table order_invoice_yh modify send_email varchar(50) default '' comment '发件人邮箱';
alter table order_invoice_yh modify receive_email varchar(50) default '' comment '收件人邮箱';
alter table quote modify quote_contacts_email varchar(50) default '' comment '本地顾问邮箱';
alter table quote modify quote_customer_email varchar(50) default '' comment '客户email';
alter table service_company modify link_email varchar(50) default '' comment '联系人邮箱';
alter table supplier_check modify link_email varchar(50) default '' comment '联系人邮箱';
alter table user modify email varchar(50) default '' comment '邮箱';

CREATE TABLE `product_picture` (
  `product_img_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '产品图片ID',
  `product_img_url` varchar(255) DEFAULT '' COMMENT '产品图片路径',
  `product_id` int(10) DEFAULT -10000 COMMENT '产品ID',
  `is_history` int(10) DEFAULT '0' COMMENT '是否过期(-1-过期，0-没过期)',
  `max_url` varchar(255) DEFAULT '' COMMENT '大图片路径',
  `min_url` varchar(255) DEFAULT '' COMMENT '小图片路径',
  `img_type` tinyint(4) DEFAULT -1 COMMENT '图片类型, 1，产品三视图 2，产品宣传图 3，产品规格图,4 ， 其他文件',
  `create_user_id` int(11) DEFAULT -10000 COMMENT '上传人用户id',
  `file_path` varchar(255) DEFAULT '' COMMENT '文件路径',
  `sort_index` int(11) NOT NULL DEFAULT '0' COMMENT '排序序号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`product_img_id`),
  KEY `pid` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=769471 DEFAULT CHARSET=utf8 COMMENT='产品图片信息表';  

CREATE TABLE `quote` (
  `quote_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表Id',
  `quote_user_id` int(11) DEFAULT '0' COMMENT '报价人ID',
  `quote_date` datetime DEFAULT NULL COMMENT '报价时间',
  `quote_company_id` int(11) DEFAULT '0' COMMENT '报价公司ID',
  `supplier_id` int(11) DEFAULT '0' COMMENT '供应商id',
  `supplier_name` varchar(255) DEFAULT '' COMMENT '公司名称',
  `supplier_user_id` int(11) DEFAULT '0' COMMENT '报价联系人id',
  `supplier_address` varchar(255) DEFAULT '' COMMENT '公司具体地址',
  `quote_contacts_name` varchar(255) DEFAULT '' COMMENT '报价联系人名字  及本地顾问名字',
  `quote_contacts_tel` varchar(50) DEFAULT '' COMMENT '联系方式    引自供应商  本地顾问可以改成自己的',
  `quote_contacts_fax` varchar(50) DEFAULT '' COMMENT '公司传真',
  `quote_contacts_email` varchar(50) DEFAULT '' COMMENT '本地顾问邮箱',
  `quote_quote_username` varchar(50) DEFAULT '' COMMENT '报价人',
  `quote_quote_no` varchar(255) DEFAULT '' COMMENT '报价流水号',
  `quote_customer_id` int(11) DEFAULT '0' COMMENT '客户ID',
  `quote_customer_company_id` int(11) DEFAULT '0' COMMENT '客户公司ID',
  `quote_status` int(11) DEFAULT '0' COMMENT '状态（-1-删除,1-草稿，,2-本地顾问发出，3-采购商已看，4-已回）',
  `quote_customer_name` varchar(255) DEFAULT '' COMMENT '客户公司名称',
  `quote_customer_contact` varchar(255) DEFAULT '' COMMENT '客户联系人名称',
  `quote_customer_tel` varchar(50) DEFAULT '' COMMENT '联系方式',
  `quote_customer_fax` varchar(50) DEFAULT '' COMMENT '客户传真',
  `quote_customer_address` varchar(255) DEFAULT '' COMMENT '客户地址',
  `quote_customer_email` varchar(50) DEFAULT '' COMMENT '客户email',
  `pd_attached_path` varchar(255) DEFAULT '' COMMENT 'pdf附件路径',
  `quote_case` int(11) DEFAULT '1' COMMENT '1,进行中 2,已流单, 3,已接单 4,项目取消',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`quote_id`)
) ENGINE=InnoDB AUTO_INCREMENT=328 DEFAULT CHARSET=utf8 COMMENT='报价表';

CREATE TABLE `quote_product` (
  `quote_product_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `quote_id` int(11) DEFAULT '0' COMMENT '报价表id',
  `product_id` int(11) DEFAULT '0' COMMENT '产品id',
  `product_name` varchar(255) DEFAULT '' COMMENT '产品名称',
  `model_no` varchar(50) DEFAULT '' COMMENT '产品型号',
  `special_features` text COMMENT '产品介绍',
  `product_url` varchar(255) DEFAULT '' COMMENT '产品图片1',
  `quote_price` varchar(100) DEFAULT '' COMMENT '报价价格',
  `currency_type` varchar(50) DEFAULT '' COMMENT '币种  1.dollar 2.yen 3.NTD 4.HKD 5.euro 6.rmb',
  `shipment_term` varchar(30) DEFAULT '' COMMENT '装运条款(FOB、CFR、CIF、EXW、CIP、CPT、DDU、DDP、FCA、FAS、DAF、DES、DEQ、Express Delivery)',
  `loading_port` varchar(20) DEFAULT '' COMMENT '装货港口',
  `port_of_destination` varchar(255) DEFAULT '' COMMENT '目的港',
  `payment_provision` varchar(255) DEFAULT '' COMMENT '付款条款',
  `min_order_qty` varchar(50) DEFAULT '' COMMENT '最小订货量（MOQ）',
  `unit_type` varchar(50) DEFAULT '' COMMENT '产品计量单位    单双套等等',
  `quote_validity` datetime DEFAULT NULL COMMENT '报价有效期',
  `product_unit` varchar(20) DEFAULT '' COMMENT '产品以及内外尺寸单位  ',
  `product_length` bigint(20) DEFAULT '0' COMMENT '产品长',
  `product_width` bigint(20) DEFAULT '0' COMMENT '产品宽',
  `product_high` bigint(20) DEFAULT '0' COMMENT '产品高',
  `inner_package_length` bigint(20) DEFAULT '0' COMMENT '内包装大小:长',
  `inner_package_width` bigint(20) DEFAULT '0' COMMENT '内包装大小:宽',
  `inner_package_high` bigint(20) DEFAULT '0' COMMENT '内包装大小:高',
  `inner_package_qty` bigint(20) DEFAULT '0' COMMENT '内包装可装产品数',
  `outer_package_length` bigint(20) DEFAULT '0' COMMENT '外包装纸箱大小:长',
  `outer_package_width` bigint(20) DEFAULT '0' COMMENT '外包装纸箱大小:宽',
  `outer_package_high` bigint(20) DEFAULT '0' COMMENT '外包装纸箱大小:高',
  `outer_package_qty` bigint(20) DEFAULT '0' COMMENT '外包装纸箱可装产品数',
  `parts_information` varchar(255) DEFAULT '' COMMENT '配件信息',
  `remarks` varchar(255) DEFAULT '' COMMENT '报价备注',
  `sentence_summary` varchar(255) DEFAULT '' COMMENT '一句话描述商品',
  `product_standards` varchar(255) DEFAULT '' COMMENT '产品标准',
  `other_standards` varchar(255) DEFAULT '' COMMENT '其他标准',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`quote_product_id`),
  KEY `product_name` (`product_name`) USING BTREE,
  KEY `min_order_qty` (`min_order_qty`) USING BTREE,
  KEY `proUrl` (`product_url`) USING BTREE,
  KEY `fob` (`quote_price`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=554 DEFAULT CHARSET=utf8 COMMENT='产品报价信息表';

CREATE TABLE `quote_updatelog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `quote_id` int(11) NOT NULL DEFAULT '0' COMMENT '报价单id',
  `quote_no` varchar(50) NOT NULL DEFAULT '' COMMENT '报价单单号',
  `alter_peopleid` int(11) NOT NULL DEFAULT '0' COMMENT '修改人id',
  `alter_people_name` varchar(30) NOT NULL DEFAULT '' COMMENT '修改人名称',
  `alter_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `old_content` varchar(510) NOT NULL DEFAULT '' COMMENT '修改前的内容',
  `new_content` varchar(510) NOT NULL DEFAULT '' COMMENT '修改后内容',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识,0.可用，1.已删除不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报价修改记录表'; 

