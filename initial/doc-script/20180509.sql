drop table gls_buyer_agent;
drop table gls_certifications;
drop table gls_config;

drop table gls_bidding;
drop table gls_bidding_product_pic;
drop table gls_bidding_record;
drop table gls_bidding_users;
drop table gls_creditcheck;
drop table gls_dockingproduction;
drop table gls_equipment;
drop table gls_file;             
drop table gls_grouporder_invite;  
drop table gls_grouporder_join;    
drop table gls_grouporder_price;   
drop table gls_grouporder_skuprice;
drop table gls_insidedistribution;
drop table gls_language;
drop table gls_message;
drop table gls_money;
drop table gls_operation_handle;
drop table gls_operation_post;
drop table gls_order_load;
drop table gls_order_logistics;
drop table gls_order_docking;
drop table gls_order_attr;
drop table gls_order_paymant;
drop table gls_order_person;
drop table gls_order_qa;
drop table gls_order_skuprice;
drop table gls_order_stream;
drop table gls_order_streamfile;
drop table gls_order_tracking;
drop table gls_orderitem;
drop table gls_parameter;
drop table gls_parameter_product;
drop table gls_picture;
drop table gls_platform;
drop table gls_protocol;
drop table gls_purchaseorder;
drop table gls_risk_business;
drop table gls_risk_company;
drop table gls_search;
drop table gls_statis_user;
drop table gls_supplier;
drop table gls_user_config;
drop table gls_user_experience;
drop table gls_vendor_sku;
drop table gls_video;
drop table gls_video_log;
drop table gls_video_users;


rename table gls_comment_buyer to comment_buyer;       
rename table gls_comment_local to comment_local;       
rename table gls_comment_supplier to comment_supplier; 
rename table gls_users_bigdata to user_bigdata;
rename table gls_customer_file to customer_file;    com.platform.ucenter.crm.controller.NewCrmController
rename table gls_distribution_ratio to distribution_ratio; com.platform.basement.controller.MainController;

 CREATE TABLE `gls_localconsultant` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userId` int(20) DEFAULT NULL,
  `industry` varchar(50) DEFAULT NULL,
  `industry2` varchar(50) DEFAULT NULL,
  `mainProductCode` varchar(50) DEFAULT NULL,
  `mainProductCode2` varchar(50) DEFAULT NULL,
  `productExp` varchar(50) DEFAULT NULL,
  `productExp2` varchar(50) DEFAULT NULL,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识,0.可用，1.已删除不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=331 DEFAULT CHARSET=utf8
gls_localconsultant 是否 local_advisor合并

