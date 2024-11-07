## 数据库设计文档

| 序号 | 数据表名          | 中文名称  |
|----|---------------|-------|
| 1  | shopping_cart | 购物车表  |
| 2  | address_book  | 地址表   |
| 3  | product       | 产品表   |
| 4  | credit_card   | 信用卡表  |
| 5  | orders        | 订单表   |
| 6  | order_detail  | 订单明细表 |
| 7  | user          | 用户表   |
| 8  | confirm_code  | 验证码表  |

### 1. shopping_cart

shopping_cart表为购物车表，用于存储C端用户的购物车信息。具体表结构如下：

| 字段名         | 数据类型          | 说明   | 备注   |
|-------------|---------------|------|------|
| id          | bigint        | 主键   | 自增   |
| user_id     | bigint        | 用户id | 逻辑外键 |
| product_id  | bigint        | 产品id | 逻辑外键 |
| name        | varchar(32)   | 名称 |      |
| picture     | varchar(255)  | 图片路径 |      |
| quantity    | int           | 数量 |      |
| price       | decimal(10,2) | 价格 |      |
| create_time | datetime      | 创建时间 |      |

### 2. address_book

address_book表为地址表，用于存储C端用户的收货地址信息。具体表结构如下：

| 字段名          | 数据类型         | 说明     | 备注      |
|--------------|--------------|--------|---------|
| id           | bigint       | 主键     | 自增      |
| user_id      | bigint       | 用户id   | 逻辑外键    |
| consignee    | varchar(50)  | 收货人    |         |
| sex          | varchar(2)   | 性别     |         |
| phone        | varchar(11)  | 手机号    |         |
| country_code | varchar(12)  | 国家编码   |         |
| country_name | varchar(32)  | 国家名称   |         |
| state_code   | varchar(12)  | 省份编码   |         |
| state_name   | varchar(32)  | 省份名称   |         |
| city_code    | varchar(12)  | 城市编码   |         |
| city_name    | varchar(32)  | 城市名称   |         |
| zip_code     | varchar(32)  | 邮编     |         |
| detail       | varchar(255) | 详细地址信息 | 具体到门牌号  |
| label        | varchar(50)  | 标签     | 公司、家、学校 |
| is_default   | tinyint      | 是否默认地址 | 1是 0否   |
| create_time  | datetime     | 创建时间   |         |
| update_time  | datetime     | 更新时间   |         |


### 3. product

product表为产品表，用于存储产品信息。具体表结构如下：

| 字段名            | 数据类型          | 说明     | 备注      |
|----------------|---------------|--------|---------|
| id             | bigint        | 主键     | 自增      |
| name           | varchar(32)   | 名称   |         |
| description    | varchar(255)  | 描述   |         |
| picture        | varchar(255)  | 图片路径 |         |
| price          | decimal(10,2) | 价格   |         |
| category_id    | bigint        | 分类id | 逻辑外键    |
| status         | int           | 状态   | 1启用 0禁用 |
| create_time    | datetime      | 创建时间   |         |
| update_time    | datetime      | 更新时间   |         |

### 4. credit_card

credit_card表为信用卡表，用于存储C端用户的信用卡信息。具体表结构如下：

| 字段名              | 数据类型        | 说明     | 备注    |
|------------------|-------------|--------|-------|
| id               | bigint      | 主键     | 自增    |
| user_id          | bigint      | 用户id   | 逻辑外键  |
| bank_name        | varchar(32) | 银行名称   |       |
| bank_code        | varchar(12) | 银行编码   |       |
| number           | varchar(32) | 信用卡号   |       |
| holder           | varchar(32) | 持卡人    |       |
| type             | varchar(12) | 卡类型    |       |
| cvv              | varchar(4)  | 卡背面三位数 |       |
| expiration_year  | varchar(4)  | 卡有效期年  |       |
| expiration_month | varchar(2)  | 卡有效期月  |       |
| validity         | varchar(10) | 有效期    |       |
| is_default       | tinyint(1)  | 是否默认卡  | 1是 0否 |

### 5. orders

orders表为订单表，用于存储C端用户的订单数据。具体表结构如下：

| 字段名                     | 数据类型          | 说明     | 备注                            |
|-------------------------|---------------|--------|-------------------------------|
| id                      | bigint        | 主键     | 自增                            |
| code                    | varchar(255)  | 订单流水号  | 唯一                            |
| user_id                 | bigint        | 用户id   | 逻辑外键                          |
| address_book_id         | bigint        | 地址id   | 逻辑外键                          |
| order_time              | datetime      | 下单时间   |                               |
| checkout_time           | datetime      | 付款时间   |                               |
| currency                | varchar(32)   | 货币     |                               |
| pay_method              | tinyint       | 支付方式   | 0信用卡支付 1微信支付 2支付宝支付           |
| pay_status              | tinyint       | 支付状态   | 0未支付 1已支付 2退款                 |
| amount                  | decimal(10,2) | 订单金额   |                               |
| freight                 | decimal(10,2) | 运费     |                               |
| weight                  | decimal(10,2) | 订单重量   |                               |
| voucher_id              | bigint        | 优惠券id  | 逻辑外键                          |
| voucher_amount          | decimal(10,2) | 优惠金额   |                               |
| order_status            | tinyint       | 订单状态   | 0待支付 1待发货 2待收货 3已完成 4已取消 5已拒单 |
| remark                  | varchar(100)  | 备注信息   |                               |
| phone                   | varchar(11)   | 手机号    |                               |
| user_name               | varchar(32)   | 用户姓名   |                               |
| consignee               | varchar(32)   | 收货人    |                               |
| address_detail          | varchar(255)  | 详细地址信息 |                               |
| dispatch_time           | datetime      | 发货时间   |                               |
| estimated_delivery_time | datetime      | 预计送达时间 |                               |
| delivery_time           | datetime      | 送达时间   |                               |
| closing_time            | datetime      | 成交时间   |                               |

### 6. order_detail

order_detail表为订单明细表，用于存储C端用户的订单明细数据。具体表结构如下：

| 字段名        | 数据类型          | 说明   | 备注   |
|------------|---------------|------|------|
| id         | bigint        | 主键   | 自增   |
| order_id   | bigint        | 订单id | 逻辑外键 |
| product_id | bigint        | 产品id | 逻辑外键 |
| name       | varchar(32)   | 名称 |      |
| picture    | varchar(255)  | 图片路径 |      |
| quantity   | int           | 数量 |      |
| price      | decimal(10,2) | 价格 |      |

### 7. user

user表为用户表，用于存储C端用户的信息。具体表结构如下：

| 字段名         | 数据类型         | 说明   | 备注 |
|-------------|--------------|------|----|
| id          | bigint       | 主键   | 自增 |
| user_name   | varchar(32)  | 用户名  |    |
| email       | varchar(50)  | 邮箱   |    |
| password    | varchar(32)  | 密码   |    |
| name        | varchar(32)  | 姓名   |    |
| sex         | varchar(2)   | 性别   |    |
| phone       | varchar(11)  | 手机号  |    |
| id_number   | varchar(18)  | 身份证号 |    |
| create_time | datetime     | 创建时间 |    |
| update_time | datetime     | 更新时间 |    |

## 8. confirm_code

confirm_code表为验证码表，用于存储验证码信息。具体表结构如下：

| 字段名         | 数据类型       | 说明    | 备注                   |
|-------------|------------|-------|----------------------|
| id          | bigint     | 主键    | 自增                   |
| user_id     | bigint     | 用户id  | 逻辑外键                 |
| code        | varchar(6) | 验证码   |                      |
| type        | tinyint(1) | 验证码类型 | 1注册 2登录 3修改密码 4修改手机号 |
| create_time | timestamp  | 创建时间  |                      |
| expire_time | timestamp  | 过期时间  |                      |
| is_used     | tinyint(1) | 是否已使用 | 1是 0否                |