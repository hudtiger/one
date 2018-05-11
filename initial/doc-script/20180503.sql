drop table gls_tms_bill;
drop table gls_tms_bill_price;
drop table gls_tms_track;

rename table gls_tms_confirm to tms_confirm;
rename table gls_tms_confirm_price to tms_confirm_price;
rename table gls_tms_orders to tms_order;
rename table gls_tms_plan to tms_plan;
rename table gls_tms_plan_detail to tms_plan_detail;
rename table gls_tms_plan_price to tms_plan_price;

rename table gls_bank_account to bank_account;
rename table gls_pay_orders to pay_order;
rename table gls_pay_order_commission to pay_order_commission;


alter table tms_plan add column order_id bigint(20) default 0 comment '关联订单id' after plan_id;
alter table tms_quote add column order_id bigint(20) default 0 comment '关联订单id' after tms_order_id;
alter table tms_confirm add column order_id bigint(20) default 0 comment '关联订单id' after id;