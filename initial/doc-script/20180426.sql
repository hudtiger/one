CREATE TABLE `supplier_language` (
  `supplier_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `create_user_id` int(10) DEFAULT -10000,
  `supplier_name` varchar(255) DEFAULT '' COMMENT '公司名称',
  `country` varchar(255) DEFAULT '' COMMENT '国家',
  `city` varchar(255) DEFAULT '' COMMENT '城市',
  `ownership` varchar(1000) DEFAULT '' COMMENT '公司属性(例如：Wholly Owned（全资企业）等)',
  `introduction` text COMMENT '公司介绍',
  `load_port` varchar(255) DEFAULT '' COMMENT '装货港口',
  `address` varchar(500) DEFAULT '' COMMENT '具体地址',
  `own_brandname` varchar(255) DEFAULT '' COMMENT '商标名称',
  `main_product` varchar(255) DEFAULT '' COMMENT '主营产品',
  `area` varchar(45) DEFAULT '' COMMENT '地区',
  `locale` varchar(20) DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  KEY `sid` (`supplier_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司信息多语言表（-1-平台，0-采购商，1-供应商，2-本地顾问)';


CREATE TABLE `num` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num` int(11) DEFAULT 0 COMMENT '流水号码',
  `type` int(11) DEFAULT 0 COMMENT '类型（0-竞价流水号,1-报价单流水号,2-订单流水号,3-产销对接流水号）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='生成流水号';

-- insert into num select * from gls_num;

rename table gls_customer to customer;


CREATE TABLE `order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_no` varchar(50) DEFAULT '' COMMENT 'po编号',
  `create_company_id` int(11) DEFAULT -10000 COMMENT '创建公司ID',
  `supplier_id` int(11) DEFAULT -10000 COMMENT '供应商编号',
  `create_user_id` int(11) DEFAULT -10000 COMMENT '创建人ID',
  `product_type` varchar(50) DEFAULT '' COMMENT '产品类型',
  `factory_mode` varchar(255) DEFAULT '' COMMENT '工厂型号',
  `customer_mode` varchar(255) DEFAULT '' COMMENT '客户型号',
  `order_qty` int(30) DEFAULT '0' COMMENT '订单数量',
  `price` decimal(23,10) DEFAULT 0 COMMENT '单价',
  `amount` decimal(23,10) DEFAULT 0 COMMENT '订单金额',
  `last_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最迟装船日',
  `destination_port` varchar(255) DEFAULT '' COMMENT '目的港',
  `free_complete_machine` int(30) DEFAULT 1 COMMENT '是否免费整机 0 免费  1 不免费',
  `is_examine_good` int(30) DEFAULT 0 COMMENT '是否验货 0买家 验货  1 第三方验货  2不验货',
  `examine_good_rule` varchar(255) DEFAULT '' COMMENT '验货标准',
  `transport_mode` int(30) DEFAULT -1 COMMENT '运输方式',
  `cbm` decimal(23,2) DEFAULT 0 COMMENT 'm³',
  `supplier_name` varchar(255) DEFAULT '' COMMENT '供应商名称',
  `supplier_address` varchar(255) DEFAULT '' COMMENT '供应商地址',
  `supplier_contacts` varchar(255) DEFAULT '' COMMENT '供应商联系人',
  `supplier_fax` varchar(255) DEFAULT '' COMMENT '供应商传真',
  `supplier_tel` varchar(30) DEFAULT '' COMMENT '供应商电话',
  `supplier_email` varchar(50) DEFAULT '' COMMENT '供应商邮箱',
  `customer_name` varchar(255) DEFAULT '' COMMENT '客户司公名称',
  `customer_contacts` varchar(255) DEFAULT '' COMMENT '客户联系人',
  `creator` varchar(255) DEFAULT '' COMMENT '创建人姓名',
  `customer_fax` varchar(255) DEFAULT '' COMMENT '客户传真',
  `customer_tel` varchar(30) DEFAULT '' COMMENT '客户联系电话',
  `customer_address` varchar(255) DEFAULT '' COMMENT '客户地址',
  `customer_email` varchar(50) DEFAULT '' COMMENT '客户邮箱',
  `remark` varchar(255) DEFAULT '',
  `type` int(11) DEFAULT 0 COMMENT '-1 订单已拒绝；0：待确认（未发送本地顾问确认） 1：本地顾问确认（未回签）2：等待回签 3: 订单已回签;4: 收款确认;5: 印刷品确认;6: 排产计划;7: 订舱;8: 订验货;（第三方验货）9: 来料检验报告;10: 生产照片;12: 验货报告;13: 收款/LC确认;14: 发货;15: 运输中;16: 买家确认收货;17: 收款确认;18: 结束;',
  `supplier_contacts_id` int(11) DEFAULT -10000 COMMENT '供应商联系人ID',
  `local_advisor_id` int(11) DEFAULT -10000 COMMENT '本地顾问ID',
  `bidding_id` int(11) DEFAULT '0' COMMENT '竞价ID/报价ID',
  `payment` int(11) DEFAULT '0' COMMENT '0 未付款 1 已交定金 2 已付清',
  `source` int(11) DEFAULT 0 COMMENT '来自0.新建订单/1.竞价/2.报价',
  `inspection` int(30) DEFAULT 0 COMMENT '验货方式：0-不验货/1-Buyer方/2-第三方',
  `third_ins` varchar(255) DEFAULT '' COMMENT '第三方验证商信息',
  `deposit` decimal(23,0) DEFAULT 0 COMMENT '定金百分比',
  `ind_payment` decimal(23,0) DEFAULT 0 COMMENT '预付百分比',
  `blc_payment` decimal(23,0) DEFAULT 0 COMMENT '尾款百分比',
  `req_specification` varchar(255) DEFAULT '' COMMENT '附加订单规格说明',
  `req_inspection_path` varchar(255) DEFAULT '' COMMENT '附件2',
  `req_inspection_remark` varchar(255) DEFAULT '' COMMENT '附件备注1',
  `req_other_path` varchar(255) DEFAULT '' COMMENT '附件2',
  `req_other_remark` varchar(255) DEFAULT '' COMMENT '附件2的备注',
  `percent_id` int(10) unsigned DEFAULT 0 COMMENT 'fund_percent表的收入比例Id',
  `order_no` varchar(255) DEFAULT '' COMMENT '订单流水号',
  `is_advisor_documentary` int(10) DEFAULT 0 COMMENT '是否联系本地顾问跟单，0为否，1为是',
  `contact_status` int(10) DEFAULT 0 COMMENT '联系状态 0 表示未发送跟单请求 1表示已发送 2表示已接收 -1表示已拒绝',
  `locale` varchar(255) DEFAULT '' COMMENT '区域',
  `shipping_terms` varchar(10) DEFAULT '' COMMENT '装运条款',
  `shipping_port` varchar(50) DEFAULT '' COMMENT '装运港',
  `payment_terms` text COMMENT '付款条款',
  `pay_condition` varchar(30) NOT NULL DEFAULT '' COMMENT '付款条件',
  `pay_days` int(11) NOT NULL DEFAULT '0' COMMENT '结算天数',
  `pay_deposit` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '提货',
  `pay_before_shipment` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '发运',
  `pay_oa` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '赊账',
  `pay_lc_at_sight` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '信用证极期',
  `pay_lc` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT 'lc信用证',
  `pay_other_type` varchar(100) NOT NULL DEFAULT '' COMMENT '其它方式',
  `pay_condition_description` varchar(150) NOT NULL DEFAULT ' ' COMMENT '付款条件显示字段，录入订单时候生成如：OA 120days',
  `is_financing_request` int(10) DEFAULT 0 COMMENT '是否申请订单融资  1是 0 否',
  `documentary_cost` decimal(23,10) DEFAULT 0 COMMENT '订单跟踪服务费',
  `is_online_booking` int(10) DEFAULT 0 COMMENT '在线订舱，1表示是；0表示否',
  `is_financing` int(10) DEFAULT 0 COMMENT '是否订单融资，0为否，1为是',
  `is_supplier_cs` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否以工厂名义出口报关（新增）0：否，1：是，如果选择否，融资订单本地顾问制单流程必须上传委托付款书单据',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单表';

CREATE TABLE `order_product` (
  `order_product_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '表ID',
  `order_id` int(11) DEFAULT -10000 COMMENT '订单id',
  `product_id` int(10) DEFAULT -10000 COMMENT '产品ID',
  `productName` varchar(255) DEFAULT '' COMMENT '产品名称',
  `model_No` varchar(100) DEFAULT '' COMMENT '产品型号',
  `specification` text COMMENT '规格说明',
  `quantity` int(11) DEFAULT 0 COMMENT '数量',
  `units` varchar(50) DEFAULT '' COMMENT '单位：0个，1对，2件，3箱',
  `container_size` int(11) DEFAULT 0 COMMENT '集装箱尺寸：(0)20''GP,(1)40''GP,(2)40''HQ,(3)45''HQ,(4)20''Refrigerated,(5)40''Refrigerated',
  `container_qTY` int(11) DEFAULT 0 COMMENT '装柜数量',
  `currency` varchar(50) DEFAULT '' COMMENT '货币类型：0人民币，1美元，2欧元，3港币，4台币，5日元',
  `product_img_url` varchar(255) DEFAULT '' COMMENT '产品图片',
  `order_product_remark` varchar(255) DEFAULT '' COMMENT '备注',
  `unit_price` double(50,3) DEFAULT 0 COMMENT '单位价格',
  `delivery` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '交货时间',
  `order_product_model_price` double(50,0) DEFAULT 0 COMMENT '模型价格',
  `tgp_qty` int(11) DEFAULT 0 COMMENT '20''''GP规格集装箱装载数量（单位：set）',
  `fgp_qty` int(11) DEFAULT 0 COMMENT '40''''GP规格集装箱装载数量（单位：set）',
  `fhq_qty` int(11) DEFAULT 0 COMMENT '40''''HQ规格集装箱装载数量（单位：set）',
  `ffhq_qty` int(11) DEFAULT 0 COMMENT '45''''HQ规格集装箱装载数量（单位：set）',
  `tr_qty` int(11) DEFAULT 0 COMMENT '20''''Refrigerated规格集装箱装载数量（单位：set）',
  `fr_qty` int(11) DEFAULT 0 COMMENT '40''''Refrigerated规格集装箱装载数量（单位：set）',
  `inner_package_length` bigint(20) DEFAULT 0 COMMENT '内包装大小:长',
  `inner_package_width` bigint(20) DEFAULT 0 COMMENT '内包装大小:宽',
  `inner_package_high` bigint(20) DEFAULT 0 COMMENT '内包装大小:高',
  `inner_package_qty` bigint(20) DEFAULT 0 COMMENT '内包装可装产品数',
  `outer_package_length` bigint(20) DEFAULT 0 COMMENT '外包装纸箱大小:长',
  `outer_package_width` bigint(20) DEFAULT 0 COMMENT '外包装纸箱大小:宽',
  `outer_package_high` bigint(20) DEFAULT 0 COMMENT '外包装纸箱大小:高',
  `outer_package_qty` bigint(20) DEFAULT 0 COMMENT '外包装纸箱可装产品数',
  `product_length` bigint(20) DEFAULT 0 COMMENT '产品长',
  `product_width` bigint(20) DEFAULT 0 COMMENT '产品宽',
  `product_high` bigint(20) DEFAULT 0 COMMENT '产品高',
  `product_unit` varchar(20) DEFAULT '' COMMENT '产品尺寸单位',
  `product_gross_weight` decimal(8,2) DEFAULT 0 COMMENT '产品净重',
  `product_net_weight` decimal(8,2) DEFAULT 0 COMMENT '产品毛重',
  `product_weight_unit` varchar(10) DEFAULT '' COMMENT '产品重量单位',
  `shipments_method` varchar(20) DEFAULT '' COMMENT '出货方式',
  `package_method` varchar(200) DEFAULT '' COMMENT '包装方式',
  `total_price` double(12,3) DEFAULT 0 COMMENT '总金额',
  `product_standards` varchar(255) DEFAULT '' COMMENT '产品标准',
  `other_standards` varchar(255) DEFAULT '' COMMENT '其他标准',
  `buyer_mode` varchar(50) DEFAULT '' COMMENT '买家型号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`order_product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='订单商品表';

CREATE TABLE `order_fitting` (
  `fit_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '配件id',
  `order_id` int(11) DEFAULT -10000 COMMENT '订单id',
  `fit_no` varchar(50) DEFAULT '' COMMENT '配置型号',
  `fit_product_no` varchar(50) DEFAULT '' COMMENT '产品型号',
  `fit_name` varchar(50) DEFAULT '' COMMENT '配件名称',
  `fit_units_price` double(10,2) DEFAULT 0 COMMENT '配件单价',
  `fit_quntity` int(11) DEFAULT 0 COMMENT '配件数量',
  `fit_units` varchar(20) DEFAULT '' COMMENT '配件单位',
  `fit_total_price` double(10,2) DEFAULT 0 COMMENT '配件金额',
  `fit_shipments_method` varchar(255) DEFAULT '' COMMENT '配件出货方式',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`fit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8 COMMENT='订单商品器件表';

CREATE TABLE `order_model` (
  `order_model_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '模具 id',
  `order_id` int(11) DEFAULT -10000 COMMENT '订单id',
  `model_no` varchar(50) DEFAULT '' COMMENT '模具型号',
  `model_product_no` varchar(50) DEFAULT '' COMMENT '模具型号',
  `model_name` varchar(50) DEFAULT '' COMMENT '模具名称',
  `model_units_price` double(10,2) DEFAULT 0 COMMENT '模具单价',
  `model_quntity` int(11) DEFAULT 0 COMMENT '模具数量',
  `model_units` varchar(20) DEFAULT '' COMMENT '模具单位',
  `model_total_price` double(10,2) DEFAULT 0 COMMENT '模具金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`order_model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8 COMMENT='订单商品模具表';

CREATE TABLE `order_schedule` (
  `order_trace_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT -10000 COMMENT '订单ID',
  `trace_type` int(11) DEFAULT -1  COMMENT '4-待付定金,5-印刷品待确认,\r\n6-待跟排产,7-待IQC,8-待OQC,9-待验货报告,10-待付走货款,11-代理资料待上传,\r\n12-待发货,13-客户待签收,14-待付尾款,16物流中',
  `trace_ate` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '下一状态预计时间/物流到达目的地时间',
  `place` varchar(255) DEFAULT '' COMMENT '流物目的地',
  `trace_remark` varchar(255) DEFAULT '' COMMENT '备注',
  `file_url` varchar(500) NOT NULL DEFAULT '' COMMENT '多文件用; 分号',
  `type` int(10) NOT NULL DEFAULT 0 COMMENT '0本地顾问，1是跟单顾问',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
  PRIMARY KEY (`order_trace_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4504 DEFAULT CHARSET=utf8 COMMENT='订单跟踪表';


-- CREATE TABLE `order_uploadfile` (
--   `order_file_id` int(11) NOT NULL AUTO_INCREMENT,
--   `order_id` int(11) DEFAULT -10000 COMMENT '订单ID',
--   `create_user_id` int(11) DEFAULT -10000 COMMENT '上传用户Id',
--   `file_name` varchar(255) DEFAULT '' COMMENT '文件名称',
--   `file_path` varchar(255) DEFAULT '' COMMENT '文件路径',
--   `file_state` int(11) DEFAULT -1 COMMENT '文件状态类型（2-回签资料，5-印刷品待确认,6-待跟排产,7-待IQC,8-待OQC,9-待验货报告,11-代理资料待上传）',
--   `file_type` int(11) DEFAULT -1 COMMENT '上传类型（0-图片，1-文件）',
--   `reply_file_name` varchar(255) DEFAULT '',
--   `reply_file_path` varchar(255) DEFAULT '',
--   `file_remark` varchar(500) DEFAULT '',
--   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
--   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
--   `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
--   PRIMARY KEY (`order_file_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=5243 DEFAULT CHARSET=utf8 COMMENT='订单附件表';

rename table gls_service_company to service_company;
rename table gls_service_company_config to service_company_config;

rename table gls_financing_check to financing_check; 
rename table gls_financing_file to financing_file;  
rename table gls_financing_orders to financing_order;

rename table gls_order_invoice to order_invoice;
rename table gls_order_invoice_bg to order_invoice_bg;      
rename table gls_order_invoice_fp to order_invoice_fp;       
rename table gls_order_invoice_fpdetail to order_invoice_fp_detail; 
rename table gls_order_invoice_td to order_invoice_td;       
rename table gls_order_invoice_ycdz to order_invoice_ycdz;     
rename table gls_order_invoice_yh to order_invoice_yh;       
rename table gls_order_invoice_zxd to order_invoice_zxd;      
rename table gls_order_invoice_zxddetail to order_invoice_zxd_detail;

rename table gls_port to port;

-- CREATE TABLE `order_remark` (
--   `order_remark_id` int(11) NOT NULL AUTO_INCREMENT,
--   `order_id` int(11) DEFAULT -10000 COMMENT '订单ID',
--   `remark_type` int(11) DEFAULT -1 COMMENT '4-待付定金,5-印刷品待确认,\r\n6-待跟排产,7-待IQC,8-待OQC,9-待验货报告,10-待付走货款,11-代理资料待上传,\r\n12-待发货,13-客户待签收,14-待付尾款,16物流中',
--   `remark_date` datetime DEFAULT '1900-01-01' COMMENT '下一状态预计时间/物流到达目的地时间',
--   `remark_detail` varchar(255) DEFAULT '' COMMENT '流物目的地',
--   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
--   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
--   `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
--   PRIMARY KEY (`order_remark_id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='订单备注表';
-- 
--  CREATE TABLE `order_remarkfile` (
--   `order_remark_file_id` int(11) NOT NULL AUTO_INCREMENT,
--   `order_remark_id` int(11) DEFAULT -10000,
--   `file_name` varchar(255) DEFAULT '' COMMENT '文件名称',
--   `path` varchar(255) DEFAULT '' COMMENT '文件路径',
--   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
--   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
--   `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识:0.可用;1.已删除不可用',
--   PRIMARY KEY (`order_remark_file_id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='订单备注文件表'; 



