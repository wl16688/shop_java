INSERT INTO eb_system_admin (account, pwd, real_name, roles, status) VALUES ('admin', 'admin', 'Super Admin', '1', 1);

-- Role Management Mock Data
INSERT INTO eb_system_role (type, relation_id, role_name, rules, level, status) VALUES 
(0, 0, '超级管理员', '1,2,3,4,5,6,7,8,9', 0, 1),
(0, 0, '商品管理员', '1,5,6', 1, 1),
(2, 1, '供应商A-管理员', '1,5', 1, 1),
(2, 2, '供应商B-运营', '1,5,7', 2, 0);

-- Product Category Mock Data (group=2 for products)
INSERT INTO eb_category (pid, type, relation_id, owner_id, name, sort, `group`, other, is_show) VALUES
(0, 0, 0, 0, '家用电器', 1, 2, '', 1),
(0, 0, 0, 0, '手机数码', 2, 2, '', 1),
(1, 0, 0, 0, '电视', 1, 2, '', 1),
(1, 0, 0, 0, '空调', 2, 2, '', 1),
(2, 0, 0, 0, '智能手机', 1, 2, '', 1),
(2, 0, 0, 0, '平板电脑', 2, 2, '', 1);

-- Product Mock Data
INSERT INTO eb_store_product (pid, type, product_type, relation_id, mer_id, image, store_name, price, sales, stock, is_show, is_del, is_verify, is_sold) VALUES
(0, 0, 0, 0, 0, 'https://img.alicdn.com/imgextra/i4/O1CN013pY1G41nO9qE8T2xN_!!6000000005076-0-tps-800-800.jpg', '小米14 Pro 徕卡光学镜头 骁龙8Gen3 钛金属特别版', '4999.00', 1205, 500, 1, 0, 1, 0),
(0, 0, 0, 0, 0, 'https://img.alicdn.com/imgextra/i3/O1CN0181T2V61H1w3eL1n5o_!!6000000000698-0-tps-800-800.jpg', 'Apple iPhone 15 Pro Max 256GB 原色钛金属', '9999.00', 850, 200, 1, 0, 1, 0),
(0, 0, 0, 0, 0, 'https://img.alicdn.com/imgextra/i1/O1CN01Z7z6qD1l1w4p5N9T6_!!6000000004758-0-tps-800-800.jpg', '华为 HUAWEI Mate 60 Pro 麒麟9000S 雅川青', '6999.00', 3021, 50, 1, 0, 1, 0),
(0, 0, 0, 0, 0, 'https://img.alicdn.com/imgextra/i2/O1CN01Y8V6oT1K1w4n5N9T6_!!6000000001108-0-tps-800-800.jpg', '索尼（SONY）65英寸 4K 智能电视 XR-65X90L', '7499.00', 156, 30, 0, 0, 1, 0),
(0, 0, 0, 0, 0, 'https://img.alicdn.com/imgextra/i4/O1CN01x8V6oT1J1w4n5N9T6_!!6000000001008-0-tps-800-800.jpg', '格力（GREE）1.5匹 云佳 新一级能效 变频空调', '2999.00', 8900, 1200, 1, 0, 1, 0),
(0, 0, 0, 0, 0, '', '公社体验票', '199.00', 0, 10000, 0, 1, 1, 0);

-- Order Mock Data
INSERT INTO eb_store_order (order_id, real_name, user_phone, total_price, pay_price, pay_type, status) VALUES
('WX202310241001', '张三', '13800138000', 4999.00, 4999.00, 'weixin', 1),
('WX202310241002', '李四', '13900139000', 9999.00, 9999.00, 'alipay', 2),
('WX202310241003', '王五', '13700137000', 6999.00, 6800.00, 'weixin', 0),
('WX202310241004', '赵六', '13600136000', 2999.00, 2999.00, 'balance', 3);

-- User Mock Data
INSERT INTO eb_user (account, real_name, nickname, phone, avatar, status, now_money, integral) VALUES
('user_001', '张三', '张大侠', '13800138000', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 1, '100.50', 500),
('user_002', '李四', '李老板', '13900139000', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', 1, '5000.00', 1200),
('user_003', '王五', '隔壁老王', '13700137000', 'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png', 0, '0.00', 0);

-- Article Mock Data
INSERT INTO eb_article (title, author, image_input, visit, likes, status) VALUES
('2023年双十一购物狂欢节攻略', '运营团队', 'https://img.alicdn.com/imgextra/i4/O1CN013pY1G41nO9qE8T2xN_!!6000000005076-0-tps-800-800.jpg', '15200', 890, 1),
('数码产品选购指南：如何挑选适合自己的手机', '科技达人', 'https://img.alicdn.com/imgextra/i3/O1CN0181T2V61H1w3eL1n5o_!!6000000000698-0-tps-800-800.jpg', '8500', 320, 1),
('平台升级维护公告', '系统管理员', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '1200', 50, 0);

-- Finance Bill Mock Data
INSERT INTO eb_user_bill (uid, title, category, type, number, balance, status) VALUES
(1, '购买商品扣除余额', 'now_money', 'pay_product', -4999.00, 100.50, 1),
(2, '充值余额', 'now_money', 'recharge', 5000.00, 5000.00, 1),
(1, '退款返还余额', 'now_money', 'refund', 2999.00, 3099.50, 1);

-- Coupon Mock Data
INSERT INTO eb_store_coupon_issue (coupon_title, coupon_type, total_count, remain_count, coupon_price, use_min_price, status) VALUES
('双十一满减神券', 1, 10000, 5000, 100.00, 999.00, 1),
('新人专享无门槛红包', 1, 50000, 25000, 10.00, 0.00, 1),
('数码家电品类券', 2, 5000, 100, 500.00, 4999.00, 0);

-- System Menu Mock Data
INSERT INTO eb_system_menus (pid, menu_name, icon, path, type, is_show) VALUES
(0, '主页', 'DataLine', '/', 1, 1),
(0, '系统设置', 'Setting', '/system', 1, 1),
(2, '角色管理', '', '/role', 2, 1),
(2, '菜单管理', '', '/menu', 2, 1);

-- Brand Mock Data
INSERT INTO eb_store_brand (brand_name, pid, fid, store_id, sort, is_show, is_del, add_time) VALUES
('apple', 0, '', 0, 0, 1, 0, UNIX_TIMESTAMP()),
('华为', 0, '', 0, 0, 1, 0, UNIX_TIMESTAMP()),
('小米', 0, '', 0, 0, 1, 0, UNIX_TIMESTAMP());

-- Unit Mock Data
INSERT INTO eb_store_product_unit (type, relation_id, name, sort, status, is_del, add_time) VALUES
(0, 0, '箱', 0, 1, 0, UNIX_TIMESTAMP()),
(0, 0, '袋', 0, 1, 0, UNIX_TIMESTAMP()),
(0, 0, '斤', 0, 1, 0, UNIX_TIMESTAMP()),
(0, 0, '瓶', 0, 1, 0, UNIX_TIMESTAMP()),
(0, 0, '只', 0, 1, 0, UNIX_TIMESTAMP()),
(0, 0, '个', 0, 1, 0, UNIX_TIMESTAMP()),
(0, 0, '桶', 0, 1, 0, UNIX_TIMESTAMP()),
(0, 0, '升', 0, 1, 0, UNIX_TIMESTAMP()),
(0, 0, '盒', 0, 1, 0, UNIX_TIMESTAMP()),
(0, 0, '套', 0, 1, 0, UNIX_TIMESTAMP());

-- Ensure Mock Data
INSERT INTO eb_store_product_ensure (type, relation_id, name, image, `desc`, sort, status, add_time) VALUES
(0, 0, '正品保障', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', '官方正品，假一赔十', 0, 1, UNIX_TIMESTAMP()),
(0, 0, '极速退款', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', '售后问题，快速处理', 0, 1, UNIX_TIMESTAMP());

-- Rule Mock Data
INSERT INTO eb_store_product_rule (type, relation_id, rule_name, rule_value) VALUES
(0, 0, '颜色', '红色,蓝色,黑色'),
(0, 0, '容量', '128G,256G,512G');

-- Product Category Mock Data
INSERT INTO eb_store_product_category (pid, type, relation_id, cate_name, path, level, pic, big_pic, adv_pic, adv_link, sort, is_show, add_time) VALUES
(0, 0, 0, '家用电器', '/0/', 1, '', '', '', '', 1, 1, UNIX_TIMESTAMP()),
(0, 0, 0, '手机数码', '/0/', 1, '', '', '', '', 2, 1, UNIX_TIMESTAMP()),
(1, 0, 0, '电视', '/0/1/', 2, '', '', '', '', 1, 1, UNIX_TIMESTAMP()),
(1, 0, 0, '空调', '/0/1/', 2, '', '', '', '', 2, 1, UNIX_TIMESTAMP()),
(2, 0, 0, '智能手机', '/0/2/', 2, '', '', '', '', 1, 1, UNIX_TIMESTAMP()),
(2, 0, 0, '平板电脑', '/0/2/', 2, '', '', '', '', 2, 1, UNIX_TIMESTAMP());
