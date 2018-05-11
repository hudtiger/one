drop table go_address;
drop table go_banner;
drop table go_cart_orderdetail;
drop table go_cart_orders;
drop table go_grouporder_comment;
drop table go_grouporder_comment_images;
drop table go_grouporder_invite;
drop table go_grouporder_skuprice;
drop table go_ktms_price;
drop table go_order_detail;
drop table go_orders;
drop table go_payback_user;
drop table go_payback_user_images;
drop table go_theme;
drop table go_theme_grouporder;
drop table go_tms_price;
drop table go_tms_price_copy;
drop table go_user;
drop table go_vendor;
drop table go_vendor_sku;

drop table emailcontent;
drop table emailrelation;
drop table emailuser;

drop table gls_area;

alter table `user` add column `user_no` varchar(10) DEFAULT '' COMMENT '用户工号';

RENAME TABLE gls_sku TO sku;
RENAME TABLE gls_sku_pro TO sku_pro;
RENAME TABLE gls_sku_provalue TO sku_provalue;