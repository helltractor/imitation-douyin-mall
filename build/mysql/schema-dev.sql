CREATE DATABASE IF NOT EXISTS `imitation_douyin_mall`;
USE `imitation_douyin_mall`;

DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `product_id` bigint NOT NULL COMMENT '产品id',
    `name` varchar(32) NOT NULL COMMENT '名称',
    `picture` varchar(255) DEFAULT NULL COMMENT '图片路径',
    `quantity` int DEFAULT '1' COMMENT '数量',
    `price` decimal(10,2) NOT NULL COMMENT '价格',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_product_id` (`product_id`)
) COMMENT='购物车';

DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `consignee` varchar(32) DEFAULT NULL COMMENT '收货人',
    `sex` varchar(2) DEFAULT NULL COMMENT '性别',
    `phone` varchar(11) NOT NULL COMMENT '手机号',
    `country_code` varchar(12) DEFAULT NULL COMMENT '国家编号',
    `country_name` varchar(32) NOT NULL COMMENT '国家名称',
    `state_code` varchar(12) DEFAULT NULL COMMENT '省份编号',
    `state_name` varchar(32) NOT NULL COMMENT '省份名称',
    `city_code` varchar(12) DEFAULT NULL COMMENT '城市编号',
    `city_name` varchar(32) NOT NULL COMMENT '城市名称',
    `zip_code` varchar(32) NOT NULL COMMENT '邮政编码',
    `detail` varchar(255) NOT NULL COMMENT '详细地址',
    `label` varchar(50) NOT NULL COMMENT '标签',
    `is_default` tinyint DEFAULT 0 COMMENT '是否默认地址',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`)
) COMMENT='地址簿';

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(32) NOT NULL COMMENT '名称',
    `description` varchar(255) NOT NULL COMMENT '描述',
    `picture` varchar(255) NOT NULL COMMENT '图片路径',
    `price` decimal(10,2) NOT NULL COMMENT '价格',
    `category_id` bigint NOT NULL COMMENT '分类id',
    `status` tinyint NOT NULL COMMENT '状态',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT='产品信息';

DROP TABLE IF EXISTS `credit_card`;
CREATE TABLE `credit_card` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `bank_name` varchar(32) DEFAULT NULL COMMENT '银行名称',
    `bank_code` varchar(12) DEFAULT NULL COMMENT '银行编号',
    `card_number` varchar(32) DEFAULT NULL COMMENT '卡号',
    `holder` varchar(32) DEFAULT NULL COMMENT '持卡人',
    `type` varchar(32) DEFAULT NULL COMMENT '卡类型',
    `cvv` varchar(32) NOT NULL COMMENT '卡安全码',
    `expiration_year` varchar(4) NOT NULL COMMENT '卡有效期年',
    `expiration_month` varchar(2) NOT NULL COMMENT '卡有效期月',
    `validity` varchar(10) DEFAULT NULL COMMENT '卡有效期',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `is_default` tinyint DEFAULT 0 COMMENT '是否默认卡',
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`)
) COMMENT='信用卡';

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code` bigint NOT NULL COMMENT '订单编号',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `address_book_id` bigint NOT NULL COMMENT '地址簿id',
    `order_time` datetime NOT NULL COMMENT '下单时间',
    `checkout_time` datetime NOT NULL COMMENT '结账时间',
    `currency` varchar(32) NOT NULL COMMENT '货币',
    `pay_method` tinyint DEFAULT 0 COMMENT '支付方式',
    `pay_status` tinyint NOT NULL COMMENT '支付状态',
    `amount` decimal(10,2) NOT NULL COMMENT '订单金额',
    `freight` decimal(10,2) DEFAULT 0 COMMENT '运费',
    `voucher_id` bigint DEFAULT NULL COMMENT '优惠券id',
    `voucher_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠券金额',
    `order_status` tinyint NOT NULL COMMENT '订单状态',
    `remark` varchar(255) DEFAULT NULL COMMENT '备注',
    `phone` varchar(11) NOT NULL COMMENT '手机号',
    `user_name` varchar(32) NOT NULL COMMENT '用户名',
    `consignee` varchar(32) NOT NULL COMMENT '收货人',
    `address_detail` varchar(255) NOT NULL COMMENT '详细地址',
    `dispatch_time` datetime DEFAULT NULL COMMENT '发货时间',
    `estimated_delivery_time` datetime DEFAULT NULL COMMENT '预计送达时间',
    `delivery_time` datetime DEFAULT NULL COMMENT '送达时间',
    `closing_time` datetime DEFAULT NULL COMMENT '成交时间',
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_address_book_id` (`address_book_id`)
) COMMENT='订单';

DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_id` bigint NOT NULL COMMENT '订单id',
    `product_id` bigint NOT NULL COMMENT '产品id',
    `name` varchar(32) NOT NULL COMMENT '名称',
    `picture` varchar(255) DEFAULT NULL COMMENT '图片路径',
    `quantity` int NOT NULL COMMENT '数量',
    `price` decimal(10,2) NOT NULL COMMENT '价格',
    PRIMARY KEY (`id`),
    INDEX `idx_order_id` (`order_id`),
    INDEX `idx_product_id` (`product_id`)
) COMMENT='订单明细';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_name` varchar(32) NOT NULL COMMENT '用户名',
    `email` varchar(50) NOT NULL COMMENT '邮箱',
    `password` varchar(32) NOT NULL COMMENT '密码',
    `name` varchar(32) DEFAULT NULL COMMENT '姓名',
    `sex` varchar(2) DEFAULT NULL COMMENT '性别',
    `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
    `id_number` varchar(18) DEFAULT NULL COMMENT '身份证号',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT='用户信息';

DROP TABLE IF EXISTS `confirm_code`;
CREATE TABLE `confirm_code` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `code` varchar(6) NOT NULL COMMENT '验证码',
    `type` tinyint NOT NULL COMMENT '验证码类型',
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `expire_time` datetime NOT NULL COMMENT '过期时间',
    `is_used` tinyint DEFAULT 0 COMMENT '是否已使用',
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`)
) COMMENT='验证码';