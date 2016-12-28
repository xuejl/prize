ALTER TABLE redrain.as_shop ADD business_district_id INT(11) DEFAULT 0 NULL;

CREATE TABLE as_consultation_banner
(
    consultation_banner_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type_id INT(11) NOT NULL COMMENT '咨询类型id',
    consultation_id INT(11) NOT NULL COMMENT '咨询id',
    bannner_img VARCHAR(255) NOT NULL COMMENT '关联图片'
);

CREATE TABLE as_consultation_city
(
    type_id INT(11) NOT NULL COMMENT '品类id',
    consultation_city_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    province_id INT(11) DEFAULT '0' NOT NULL COMMENT '省份id',
    city_id INT(11) DEFAULT '0' NOT NULL COMMENT '城市',
    area_id INT(11) DEFAULT '0' NOT NULL COMMENT '地区',
    title VARCHAR(100) NOT NULL COMMENT '标题',
    cover_img VARCHAR(255) NOT NULL COMMENT '封面',
    consultation_desc VARCHAR(255) NOT NULL COMMENT '简介',
    content TEXT NOT NULL COMMENT '内容',
    visit_count INT(50) DEFAULT '0' NOT NULL COMMENT '浏览次数',
    public_time VARCHAR(50) NOT NULL COMMENT '发布时间',
    status INT(2) DEFAULT '1' NOT NULL COMMENT '1 草稿 2 发布',
    address VARCHAR(255) NOT NULL,
    lng VARCHAR(50) NOT NULL,
    lat VARCHAR(50) NOT NULL
);

CREATE TABLE as_consultation_convenience
(
    consultation_convenience_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type_id INT(11) NOT NULL COMMENT '品类id',
    province_id INT(11) DEFAULT '0' NOT NULL COMMENT '省份id',
    city_id INT(11) DEFAULT '0' NOT NULL COMMENT '城市',
    area_id INT(11) DEFAULT '0' NOT NULL COMMENT '地区',
    title VARCHAR(100) NOT NULL COMMENT '标题',
    cover_img VARCHAR(255) NOT NULL COMMENT '封面',
    line_img VARCHAR(255) NOT NULL COMMENT '线路图',
    content TEXT NOT NULL COMMENT '内容',
    type INT(2) DEFAULT '1' NOT NULL COMMENT '1 交通指南 2 免费公园',
    visit_count INT(50) DEFAULT '0' NOT NULL COMMENT '浏览次数',
    public_time VARCHAR(50) NOT NULL COMMENT '发布时间',
    status INT(2) DEFAULT '1' NOT NULL COMMENT '1 草稿 2 发布',
    address VARCHAR(255) NOT NULL,
    lng VARCHAR(50) NOT NULL,
    lat VARCHAR(50) NOT NULL,
    open_time VARCHAR(50) NOT NULL COMMENT '开放时间'
);

CREATE TABLE as_consultation_food
(
    consultation_food_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type_id INT(11) NOT NULL COMMENT '品类id',
    province_id INT(11) DEFAULT '0' NOT NULL COMMENT '省份id',
    city_id INT(11) DEFAULT '0' NOT NULL COMMENT '城市',
    area_id INT(11) DEFAULT '0' NOT NULL COMMENT '地区',
    yp_base_category_id INT(11) DEFAULT '0' NOT NULL COMMENT '美食类型id',
    name VARCHAR(100) DEFAULT '0' NOT NULL COMMENT '名称',
    cover_img VARCHAR(100) DEFAULT '0' NOT NULL COMMENT '封面图',
    star_level INT(2) DEFAULT '0' NOT NULL COMMENT '星级',
    visit_count INT(50) DEFAULT '0' NOT NULL COMMENT '浏览次数',
    public_time VARCHAR(50) NOT NULL COMMENT '发布时间',
    status INT(2) DEFAULT '1' NOT NULL COMMENT '1 草稿 2 发布',
    address VARCHAR(255) NOT NULL,
    lng VARCHAR(50) NOT NULL,
    lat VARCHAR(50) NOT NULL,
    open_time VARCHAR(255) NOT NULL COMMENT '营业时间',
    business_district_id INT(11) COMMENT '商圈'
);

CREATE TABLE as_consultation_hotel
(
    consultation_traffic_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type_id INT(11) NOT NULL COMMENT '品类id',
    province_id INT(11) DEFAULT '0' NOT NULL COMMENT '省份id',
    city_id INT(11) DEFAULT '0' NOT NULL COMMENT '城市',
    area_id INT(11) DEFAULT '0' NOT NULL COMMENT '地区',
    business_district_id INT(11) DEFAULT '0' NOT NULL COMMENT '商圈id',
    brand_id INT(11) DEFAULT '0' NOT NULL COMMENT '品牌id',
    suggest_price FLOAT DEFAULT '0' NOT NULL COMMENT '推荐价格',
    name VARCHAR(100) NOT NULL COMMENT '名称',
    star_level INT(2) DEFAULT '0' NOT NULL COMMENT '星级',
    visit_count INT(50) DEFAULT '0' NOT NULL COMMENT '浏览次数',
    public_time VARCHAR(50) NOT NULL COMMENT '发布时间',
    status INT(2) DEFAULT '1' NOT NULL COMMENT '1 草稿 2 发布',
    address VARCHAR(255) NOT NULL,
    lng VARCHAR(50) NOT NULL,
    lat VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL COMMENT '联系电话',
    cover_img VARCHAR(255) NOT NULL COMMENT '封面图',
    create_time VARCHAR(50) NOT NULL
);

CREATE TABLE as_consultation_imgs
(
    consultation_imgs_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type_id INT(11) NOT NULL COMMENT '品类id',
    consultation_id INT(11) NOT NULL COMMENT '咨询id',
    img_url VARCHAR(255) NOT NULL COMMENT '关联图片'
);

CREATE TABLE as_consultation_info
(
    consultation_info_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type_id INT(11) NOT NULL COMMENT '品类id',
    province_id INT(11) DEFAULT '0' NOT NULL COMMENT '省份id',
    city_id INT(11) DEFAULT '0' NOT NULL COMMENT '城市',
    area_id INT(11) DEFAULT '0' NOT NULL COMMENT '地区',
    title VARCHAR(100) NOT NULL COMMENT '标题',
    cover_img VARCHAR(255) NOT NULL COMMENT '封面',
    consultation_desc VARCHAR(255) NOT NULL COMMENT '简介',
    content TEXT NOT NULL COMMENT '内容',
    visit_count INT(50) DEFAULT '0' NOT NULL COMMENT '浏览次数',
    public_time VARCHAR(50) NOT NULL COMMENT '发布时间',
    status INT(2) DEFAULT '1' NOT NULL COMMENT '1 草稿 2 发布',
    address VARCHAR(255) NOT NULL,
    lng VARCHAR(50) NOT NULL,
    lat VARCHAR(50) NOT NULL
);

CREATE TABLE as_consultation_shop
(
    yp_base_category_id INT(11) NOT NULL COMMENT '品类分类',
    consultation_shop_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    province_id INT(11) DEFAULT '0' NOT NULL COMMENT '省份id',
    city_id INT(11) DEFAULT '0' NOT NULL COMMENT '城市',
    area_id INT(11) DEFAULT '0' NOT NULL COMMENT '地区',
    shop_name VARCHAR(100) NOT NULL COMMENT '店铺名称',
    cover_img VARCHAR(255) NOT NULL COMMENT '封面',
    visit_count INT(50) DEFAULT '0' NOT NULL COMMENT '浏览次数',
    suggest_price FLOAT DEFAULT '0' NOT NULL COMMENT '推荐价格',
    star_level INT(2) NOT NULL COMMENT '推荐级别',
    create_time VARCHAR(30) NOT NULL COMMENT '创建时间',
    motify_time VARCHAR(20) NOT NULL COMMENT '修改时间',
    status INT(2) DEFAULT '1' NOT NULL COMMENT '1 草稿 2 发布',
    business_district_id INT(50) NOT NULL COMMENT '商圈id',
    address VARCHAR(100) NOT NULL COMMENT '地址',
    phone VARCHAR(20) NOT NULL COMMENT '电话',
    business_time VARCHAR(50) NOT NULL COMMENT '营业时间',
    banner_img VARCHAR(255) DEFAULT 'banner
' COMMENT 'banner图',
    lng VARCHAR(100) NOT NULL COMMENT '经度',
    lat VARCHAR(100) NOT NULL COMMENT '纬度',
    site VARCHAR(255) NOT NULL COMMENT '官网',
    shop_type_yp_base_category_id INT(11) NOT NULL COMMENT '店铺分类'
);

CREATE TABLE as_consultation_shopping
(
    consultation_shopping_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type_id INT(11) NOT NULL COMMENT '品类id',
    province_id INT(11) DEFAULT '0' NOT NULL COMMENT '省份id',
    city_id INT(11) DEFAULT '0' NOT NULL COMMENT '城市',
    area_id INT(11) DEFAULT '0' NOT NULL COMMENT '地区',
    yp_base_category_id INT(11) DEFAULT '0' NOT NULL COMMENT '美食类型id',
    recommend_result VARCHAR(255) DEFAULT '0' NOT NULL COMMENT '推荐理由',
    name VARCHAR(100) DEFAULT '0' NOT NULL COMMENT '名称',
    cover_img VARCHAR(100) DEFAULT '0' NOT NULL COMMENT '封面图',
    recommend_level INT(2) DEFAULT '0' NOT NULL COMMENT '推荐星级',
    visit_count INT(50) DEFAULT '0' NOT NULL COMMENT '浏览次数',
    public_time VARCHAR(50) NOT NULL COMMENT '发布时间',
    status INT(2) DEFAULT '1' NOT NULL COMMENT '1 草稿 2 发布',
    address VARCHAR(255) NOT NULL COMMENT '地址',
    lng VARCHAR(50) NOT NULL COMMENT '经度',
    lat VARCHAR(50) NOT NULL COMMENT '纬度',
    route VARCHAR(255) COMMENT '线路',
    shopping_desc VARCHAR(255) NOT NULL COMMENT '简介'
);

CREATE TABLE as_consultation_specialty
(
    consultation_specialty_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type_id INT(11) NOT NULL COMMENT '品类id',
    province_id INT(11) DEFAULT '0' NOT NULL COMMENT '省份id',
    city_id INT(11) DEFAULT '0' NOT NULL COMMENT '城市',
    area_id INT(11) DEFAULT '0' NOT NULL COMMENT '地区',
    consultation_desc VARCHAR(255) DEFAULT '0' NOT NULL COMMENT '简介',
    name VARCHAR(100) DEFAULT '0' NOT NULL COMMENT '名称',
    cover_img VARCHAR(100) DEFAULT '0' NOT NULL COMMENT '封面图',
    visit_count INT(50) DEFAULT '0' NOT NULL COMMENT '浏览次数',
    public_time VARCHAR(50) NOT NULL COMMENT '发布时间',
    status INT(2) DEFAULT '1' NOT NULL COMMENT '1 草稿 2 发布'
);

CREATE TABLE as_consultation_spots
(
    consultation_spots_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type_id INT(11) NOT NULL COMMENT '品类id',
    province_id INT(11) DEFAULT '0' NOT NULL COMMENT '省份id',
    city_id INT(11) DEFAULT '0' NOT NULL COMMENT '城市',
    area_id INT(11) DEFAULT '0' NOT NULL COMMENT '地区',
    yp_base_category_id INT(11) DEFAULT '0' NOT NULL COMMENT '美食类型id',
    consultation_desc VARCHAR(255) DEFAULT '0' NOT NULL COMMENT '描述',
    name VARCHAR(100) DEFAULT '0' NOT NULL COMMENT '名称',
    cover_img VARCHAR(100) DEFAULT '0' NOT NULL COMMENT '封面图',
    star_level INT(2) DEFAULT '0' NOT NULL COMMENT '星级',
    visit_count INT(50) DEFAULT '0' NOT NULL COMMENT '浏览次数',
    public_time VARCHAR(50) NOT NULL COMMENT '发布时间',
    status INT(2) DEFAULT '1' NOT NULL COMMENT '1 草稿 2 发布',
    address VARCHAR(255) NOT NULL,
    lng VARCHAR(50) NOT NULL,
    lat VARCHAR(50) NOT NULL,
    open_time VARCHAR(255) NOT NULL COMMENT '开放时间',
    line VARCHAR(255) NOT NULL COMMENT '线路图'
);

CREATE TABLE as_consultation_street_food
(
    consultation_street_food_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    consultation_shop_id INT(11) NOT NULL COMMENT '咨询店铺id',
    food_content TEXT NOT NULL COMMENT '内容',
    food_img VARCHAR(255) NOT NULL COMMENT '菜图',
    food_name VARCHAR(255) NOT NULL COMMENT '菜名'
);

CREATE TABLE as_consultation_type
(
    consultation_type_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    parent_id INT(11) NOT NULL COMMENT '咨询类型父级id',
    type_name VARCHAR(50) NOT NULL COMMENT '类型名称',
    type_img VARCHAR(255) NOT NULL COMMENT '咨询类型图片',
    sort_order INT(11) NOT NULL COMMENT '排序',
    type_level INT(11) NOT NULL COMMENT '类型等级',
    id_path VARCHAR(20) NOT NULL
);

ALTER TABLE redrain.as_yp_base_category MODIFY type INT(11) COMMENT '基础类型分类：1云屏-行业标签 2云屏-服务内容 3红包雨-礼品类型 4红包雨-优惠劵标签6.咨询模块-品类 7.资讯模块－逛街 8.资讯管理-商场分类 9.咨询模块-店铺分类 ';