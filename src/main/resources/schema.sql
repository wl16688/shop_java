CREATE TABLE IF NOT EXISTS eb_store_order (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type INT,
  pid INT,
  order_id VARCHAR(255),
  trade_no VARCHAR(255),
  supplier_id INT,
  store_id INT,
  uid INT,
  real_name VARCHAR(255),
  user_phone VARCHAR(255),
  province VARCHAR(255),
  user_address VARCHAR(255),
  user_location VARCHAR(255),
  cart_id VARCHAR(255),
  pink_id INT,
  activity_id INT,
  activity_append VARCHAR(255),
  freight_price DECIMAL(10,2),
  total_num INT,
  total_price DECIMAL(10,2),
  pay_price DECIMAL(10,2),
  pay_postage DECIMAL(10,2),
  deduction_price DECIMAL(10,2),
  coupon_id INT,
  coupon_price DECIMAL(10,2),
  paid INT,
  pay_time INT,
  pay_type VARCHAR(255),
  add_time INT,
  status INT,
  refund_status INT,
  mark VARCHAR(255),
  is_del INT
);

CREATE TABLE IF NOT EXISTS eb_system_store (
  id INT AUTO_INCREMENT PRIMARY KEY,
  erp_shop_id INT,
  name VARCHAR(255),
  introduction VARCHAR(255),
  phone VARCHAR(255),
  address VARCHAR(255),
  province INT,
  city INT,
  area INT,
  street INT,
  detailed_address VARCHAR(255),
  image VARCHAR(255),
  latitude VARCHAR(255),
  longitude VARCHAR(255),
  valid_time VARCHAR(255),
  day_time VARCHAR(255),
  is_show INT,
  is_del INT
);

CREATE TABLE IF NOT EXISTS eb_store_product (
  id INT AUTO_INCREMENT PRIMARY KEY,
  pid INT,
  type INT,
  product_type INT,
  relation_id INT,
  mer_id INT,
  image VARCHAR(255),
  recommend_image VARCHAR(255),
  slider_image VARCHAR(255),
  store_name VARCHAR(255),
  store_info VARCHAR(255),
  keyword VARCHAR(255),
  bar_code VARCHAR(255),
  cate_id VARCHAR(255),
  price VARCHAR(255),
  vip_price VARCHAR(255),
  ot_price VARCHAR(255),
  postage VARCHAR(255),
  unit_name VARCHAR(255),
  sort INT,
  sales INT,
  stock INT,
  is_show INT,
  is_hot INT,
  is_benefit INT,
  is_best INT,
  is_new INT,
  add_time INT,
  is_postage INT,
  is_del INT
);

CREATE TABLE IF NOT EXISTS eb_system_attachment (
  att_id INT,
  type INT,
  file_type INT,
  relation_id INT,
  name VARCHAR(255),
  att_dir VARCHAR(255),
  satt_dir VARCHAR(255),
  att_size VARCHAR(255),
  att_type VARCHAR(255),
  pid INT,
  time INT,
  image_type INT
);

CREATE TABLE IF NOT EXISTS eb_system_role (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type INT,
  relation_id INT,
  role_name VARCHAR(255),
  rules VARCHAR(255),
  level INT,
  status INT
);

CREATE TABLE IF NOT EXISTS eb_system_config (
  id INT AUTO_INCREMENT PRIMARY KEY,
  is_store INT,
  menu_name VARCHAR(255),
  type VARCHAR(255),
  input_type VARCHAR(255),
  config_tab_id INT,
  parameter VARCHAR(255),
  upload_type INT,
  required VARCHAR(255),
  value VARCHAR(255),
  info VARCHAR(255),
  `desc` VARCHAR(255),
  sort INT,
  status INT
);

CREATE TABLE IF NOT EXISTS eb_user (
  uid INT,
  account VARCHAR(255),
  pwd VARCHAR(255),
  real_name VARCHAR(255),
  birthday VARCHAR(255),
  card_id VARCHAR(255),
  mark VARCHAR(255),
  partner_id INT,
  group_id INT,
  nickname VARCHAR(255),
  avatar VARCHAR(255),
  phone VARCHAR(255),
  add_time INT,
  add_ip VARCHAR(255),
  last_time INT,
  last_ip VARCHAR(255),
  now_money VARCHAR(255),
  brokerage_price VARCHAR(255),
  integral INT,
  sign_num INT,
  status INT,
  level INT,
  spread_uid INT,
  is_promoter INT
);

CREATE TABLE IF NOT EXISTS eb_user_extract (
  id INT AUTO_INCREMENT PRIMARY KEY,
  uid INT,
  real_name VARCHAR(255),
  extract_type VARCHAR(255),
  bank_code VARCHAR(255),
  bank_address VARCHAR(255),
  alipay_code VARCHAR(255),
  extract_price DECIMAL(10,2),
  extract_fee DECIMAL(10,2),
  mark VARCHAR(255),
  balance DECIMAL(10,2),
  fail_msg VARCHAR(255),
  fail_time INT,
  add_time INT,
  status INT
);

CREATE TABLE IF NOT EXISTS eb_system_menus (
  id INT AUTO_INCREMENT PRIMARY KEY,
  pid INT,
  type INT,
  icon VARCHAR(255),
  menu_name VARCHAR(255),
  module VARCHAR(255),
  controller VARCHAR(255),
  action VARCHAR(255),
  api_url VARCHAR(255),
  methods VARCHAR(255),
  params VARCHAR(255),
  sort INT,
  is_show INT,
  is_show_path INT,
  access INT,
  menu_path VARCHAR(255),
  path VARCHAR(255),
  auth_type INT
);

CREATE TABLE IF NOT EXISTS eb_system_admin (
  id INT AUTO_INCREMENT PRIMARY KEY,
  account VARCHAR(255),
  pwd VARCHAR(255),
  real_name VARCHAR(255),
  phone VARCHAR(255),
  roles VARCHAR(255),
  status INT,
  admin_type INT,
  relation_id INT,
  head_pic VARCHAR(255),
  last_ip VARCHAR(255),
  last_time INT,
  add_time INT,
  login_count INT,
  level INT
);

CREATE TABLE IF NOT EXISTS eb_store_coupon_issue (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cid INT,
  category INT,
  type INT,
  receive_type INT,
  coupon_type INT,
  coupon_title VARCHAR(255),
  start_time INT,
  end_time INT,
  total_count INT,
  remain_count INT,
  coupon_price DECIMAL(10,2),
  use_min_price DECIMAL(10,2),
  status INT
);

CREATE TABLE IF NOT EXISTS eb_category (
  id INT AUTO_INCREMENT PRIMARY KEY,
  pid INT,
  type INT,
  relation_id INT,
  owner_id INT,
  name VARCHAR(255),
  sort INT,
  `group` INT,
  other VARCHAR(255),
  is_show INT
);

CREATE TABLE IF NOT EXISTS eb_community (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type INT,
  relation_id INT,
  content_type INT,
  title VARCHAR(255),
  image VARCHAR(255),
  video_url VARCHAR(255),
  content VARCHAR(255),
  like_num INT,
  collect_num INT,
  play_num INT,
  comment_num INT
);

CREATE TABLE IF NOT EXISTS eb_article (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cid VARCHAR(255),
  title VARCHAR(255),
  author VARCHAR(255),
  image_input VARCHAR(255),
  synopsis VARCHAR(255),
  share_title VARCHAR(255),
  share_synopsis VARCHAR(255),
  visit VARCHAR(255),
  likes INT,
  sort INT,
  url VARCHAR(255),
  status INT,
  add_time VARCHAR(255),
  hide INT,
  admin_id INT,
  mer_id INT,
  is_hot INT,
  is_banner INT
);

CREATE TABLE IF NOT EXISTS eb_user_bill (
  id INT AUTO_INCREMENT PRIMARY KEY,
  uid INT,
  link_id VARCHAR(255),
  pm INT,
  title VARCHAR(255),
  category VARCHAR(255),
  type VARCHAR(255),
  number DECIMAL(10,2),
  balance DECIMAL(10,2),
  mark VARCHAR(255),
  add_time INT,
  status INT
);

CREATE TABLE IF NOT EXISTS eb_store_combination (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type INT,
  product_id INT,
  relation_id INT,
  image VARCHAR(255),
  title VARCHAR(255),
  people INT,
  info VARCHAR(255),
  price DECIMAL(10,2),
  sort INT,
  sales INT,
  stock INT,
  is_show INT,
  start_time INT,
  stop_time INT
);

CREATE TABLE IF NOT EXISTS eb_store_seckill (
  id INT AUTO_INCREMENT PRIMARY KEY,
  activity_id INT,
  type INT,
  product_id INT,
  title VARCHAR(255),
  info VARCHAR(255),
  image VARCHAR(255),
  price DECIMAL(10,2),
  ot_price DECIMAL(10,2),
  stock INT,
  sales INT,
  quota INT,
  start_time INT,
  stop_time INT,
  time_id INT,
  status INT,
  is_show INT
);

CREATE TABLE IF NOT EXISTS eb_system_supplier (
  id INT AUTO_INCREMENT PRIMARY KEY,
  admin_id INT,
  supplier_name VARCHAR(255),
  avatar VARCHAR(255),
  name VARCHAR(255),
  phone VARCHAR(255),
  email VARCHAR(255),
  address VARCHAR(255),
  province INT,
  city INT,
  area INT,
  street INT,
  detailed_address VARCHAR(255),
  mark VARCHAR(255),
  sort INT,
  is_show INT,
  is_del INT,
  add_time VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS eb_store_cart (
  id INT AUTO_INCREMENT PRIMARY KEY,
  uid INT,
  tourist_uid VARCHAR(255),
  type INT,
  product_id INT,
  product_type INT,
  activity_id INT,
  store_id INT,
  staff_id INT,
  product_attr_unique VARCHAR(255),
  cart_num INT,
  add_time INT,
  is_pay INT,
  is_del INT,
  is_new INT
);

CREATE TABLE IF NOT EXISTS eb_store_brand (
  id INT AUTO_INCREMENT PRIMARY KEY,
  brand_name VARCHAR(255),
  pid INT,
  fid VARCHAR(255),
  store_id INT,
  sort INT,
  is_show INT,
  is_del INT,
  is_verify INT DEFAULT 1,
  is_sold INT DEFAULT 0,
  is_police INT DEFAULT 0,
  cate_id VARCHAR(255) DEFAULT '',
  brand_id INT DEFAULT 0,
  unit_name VARCHAR(255) DEFAULT '',
  code VARCHAR(255) DEFAULT '',
  bar_code VARCHAR(255) DEFAULT '',
  slider_image VARCHAR(1000) DEFAULT '',
  add_time INT
);

CREATE TABLE IF NOT EXISTS eb_store_product_unit (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type INT,
  relation_id INT,
  name VARCHAR(255),
  sort INT,
  status INT,
  is_del INT,
  add_time INT
);

CREATE TABLE IF NOT EXISTS eb_store_product_ensure (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type INT,
  relation_id INT,
  name VARCHAR(255),
  image VARCHAR(255),
  `desc` VARCHAR(255),
  sort INT,
  status INT,
  add_time INT
);

CREATE TABLE IF NOT EXISTS eb_store_product_rule (
  id INT AUTO_INCREMENT PRIMARY KEY,
  type INT,
  relation_id INT,
  rule_name VARCHAR(255),
  rule_value VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS eb_store_product_category (
  id INT AUTO_INCREMENT PRIMARY KEY,
  pid INT,
  type INT,
  relation_id INT,
  cate_name VARCHAR(255),
  path VARCHAR(255),
  level INT,
  pic VARCHAR(255),
  big_pic VARCHAR(255),
  adv_pic VARCHAR(255),
  adv_link VARCHAR(255),
  sort INT,
  is_show INT,
  add_time INT
);
