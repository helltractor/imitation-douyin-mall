CREATE DATABASE IF NOT EXISTS `imitation_douyin_mall` DEFAULT CHARACTER SET = 'utf8mb4';

USE `imitation_douyin_mall`;

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `user_id` BIGINT NOT NULL COMMENT '用户id',
  `product_id` BIGINT NOT NULL COMMENT '产品id',
  `quantity` INT DEFAULT '1' COMMENT '数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`, `product_id`)
) COMMENT = '购物车';

INSERT INTO cart (user_id, product_id, quantity, create_time) VALUES (1, 1, 1, NOW());
INSERT INTO cart (user_id, product_id, quantity, create_time) VALUES (1, 2, 1, NOW());

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(32) NOT NULL COMMENT '名称',
  `description` VARCHAR(255) NOT NULL COMMENT '描述',
  `picture` VARCHAR(255) NOT NULL COMMENT '图片路径',
  `price` DECIMAL(10, 2) NOT NULL COMMENT '价格',
  `categories` JSON NOT NULL COMMENT '分类',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) COMMENT = '产品信息';

INSERT INTO product (name, description, picture, price, categories, create_time, update_time)
    VALUES ('iPhone 12', 'Apple iPhone 12 128GB 黑色', 'https://img14.360buyimg.com/n1/s450x450_jfs/t1/142073/38/1917/198712/5f7f3b1eEe7b7b7b7/7b7b7b7b7b7b7b7b.jpg', 6999.00, '["手机", "Apple"]', NOW(), NOW());
INSERT INTO product (name, description, picture, price, categories, create_time, update_time)
    VALUES ('MacBook Pro', 'Apple MacBook Pro 13.3英寸 2020款', 'https://img14.360buyimg.com/n1/s450x450_jfs/t1/142073/38/1917/198712/5f7f3b1eEe7b7b7b7/7b7b7b7b7b7b7b7b.jpg', 9999.00, '["电脑", "Apple"]', NOW(), NOW());

DROP TABLE IF EXISTS `address_book`;

CREATE TABLE `address_book` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT NOT NULL COMMENT '用户id',
  `country_name` VARCHAR(32) NOT NULL COMMENT '国家名称',
  `state_name` VARCHAR(32) NOT NULL COMMENT '省份名称',
  `city_name` VARCHAR(32) NOT NULL COMMENT '城市名称',
  `zip_code` VARCHAR(32) NOT NULL COMMENT '邮政编码',
  `detail` VARCHAR(255) NOT NULL COMMENT '详细地址',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`)
) COMMENT = '地址簿';

INSERT INTO address_book (user_id, country_name, state_name, city_name, zip_code, detail, create_time, update_time)
	VALUES (1, '中国', '浙江省', '杭州市', '310000', '杭州市钱塘区10号大街梦琴湾', NOW(), NOW());

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_id` VARCHAR(36) NOT NULL COMMENT '订单id',
  `user_id` BIGINT NOT NULL COMMENT '用户id',
  `address_book_id` BIGINT NOT NULL COMMENT '地址簿id',
  `email` VARCHAR(50) NOT NULL COMMENT '邮箱',
  `user_currency` VARCHAR(32) NOT NULL COMMENT '货币',
  `paid_status` TINYINT DEFAULT 0 COMMENT '支付状态',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`order_id`),
  INDEX `idx_user_id` (`user_id`)
) COMMENT = '订单';

INSERT INTO orders (order_id, user_id, address_book_id, email, user_currency, paid_status, create_time, update_time)
    VALUES ('1', 1, 1, 'mall@helltractor.top', 'USD', 0, NOW(), NOW());

DROP TABLE IF EXISTS `order_detail`;

CREATE TABLE `order_detail` (
  `order_id` VARCHAR(36) NOT NULL COMMENT '订单id',
  `product_id` BIGINT NOT NULL COMMENT '产品id',
  `quantity` INT NOT NULL COMMENT '数量',
  `cost` DECIMAL(10, 2) NOT NULL COMMENT '价格',
  PRIMARY KEY (`order_id`, `product_id`)
) COMMENT = '订单明细';

INSERT INTO order_detail (order_id, product_id, quantity, cost)
    VALUES ('1', 1, 1, 100.00);
INSERT INTO order_detail (order_id, product_id, quantity, cost)
    VALUES ('1', 2, 1, 200.00);

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email` VARCHAR(50) NOT NULL COMMENT '邮箱',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `confirm_password` VARCHAR(32) DEFAULT NULL COMMENT '验证码',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_email` (`email`)
) COMMENT = '用户信息';

INSERT INTO user (email, password, confirm_password, create_time, update_time)
    VALUES ('mall@helltractor.top', '$2a$10$z8xoM71Dyt3Zc5x2cWeWMeLBUX2xuzDDnq.RH52uRtRRbrx5JneBW', '666666', NOW(), NOW());

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `transaction_id` VARCHAR(36) NOT NULL COMMENT '交易id',
  `user_id` BIGINT NOT NULL COMMENT '用户id',
  `order_id` VARCHAR(36) NOT NULL COMMENT '订单id',
  `amount` DECIMAL(10, 2) NOT NULL COMMENT '订单金额',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
--   `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`transaction_id`),
  INDEX `idx_user_id` (`user_id`)
) COMMENT = '交易信息';

INSERT INTO payment (transaction_id, user_id, order_id, amount, create_time)
    VALUES ('1', 1, '1', 300.00, NOW());