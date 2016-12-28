package com.aoshi.util;

import org.springframework.context.ApplicationContext;

/**
 * 项目名称：
 * 
 * @author:fh
 * 
 */
public class Const {
	public static final String CONSULTATIONINFOFILEPATHIMG = "ascmMall/consultationInfo"; //  免费公园上传路径
	public static final String CONSULTATIONCONVENIENCEFILEPATHIMG = "ascmMall/consultationConvenience"; // 交通指南上传路径
	public static final String CONSULTATIONHOTELFILEPATHIMG = "ascmMall/consultationHotel"; // 酒店上传路径
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_menuList = "menuList"; // 当前菜单
	public static final String SESSION_allmenuList = "allmenuList"; // 全部菜单
	public static final String SESSION_QX = "QX";
	public static final String SESSION_userpds = "userpds";
	public static final String SESSION_USERROL = "USERROL"; // 用户对象
	public static final String SESSION_USERNAME = "USERNAME"; // 用户名
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String LOGIN = "/login_toLogin.do"; // 登录地址
	public static final String SYSNAME = "admin/config/SYSNAME.txt"; // 系统名称路径
	public static final String PAGE = "admin/config/PAGE.txt"; // 分页条数配置路径
	public static final String EMAIL = "admin/config/EMAIL.txt"; // 邮箱服务器配置路径
	public static final String SMS1 = "admin/config/SMS1.txt"; // 短信账户配置路径1
	public static final String SMS2 = "admin/config/SMS2.txt"; // 短信账户配置路径2
	public static final String FWATERM = "admin/config/FWATERM.txt"; // 文字水印配置路径
	public static final String IWATERM = "admin/config/IWATERM.txt"; // 图片水印配置路径
	public static final String WEIXIN = "admin/config/WEIXIN.txt"; // 微信配置路径
	public static final String FILEPATHIMG = "uploadFiles/uploadImgs/"; // 图片上传路径
	public static final String FILEPATHFILE = "uploadFiles/file/"; // 文件上传路径
	public static final String FILEPATHIMGDIR = "ftpdir/";
	public static final String USERFILEPATHIMG = "ascmMall/user"; // 用户图片上传路径
	public static final String ADFILEPATHIMG = "ascmMall/ad"; // 广告图片上传路径
	public static final String STOREFILEPATHIMG = "ascmMall/store"; // 商场图片上传路径
	public static final String CONSULTATIONCITYFILEPATHIMG = "ascmMall/consultationCity"; // 百科同城活动上传路径
	
	public static final String POINTFILEPATHIMG = "ascmMall/shoppoint"; // 积分商城广告图
	public static final String SHOPFILEPATHIMG = "ascmMall/shop"; // 店铺图片上传路径
	public static final String LOGOFILEPATHIMG = "ascmMall/logo"; // 品牌logo上传路径
	public static final String GOODSFILEPATHIMG = "ascmMall/goods"; // 商品图片上传路径
	public static final String COUPONFILEPATHIMG = "ascmMall/coupon"; // 商品图片上传路径
	public static final String REMMOMEMDFILEPATHIMG = "ascmMall/remmemd"; // 推荐图片上传路径
	public static final String GOODSTYPEPATHIMG = "ascmMall/goodsType"; // 商品类型上传路径
	public static final String YIYUANPAIPATHAPK = "yypapk"; // android安装包apk
	public static final String YIYUANPAIPATHIPA = "yypipa"; // ios安装包ipa

	public static final String ACTIVITYFILEPATHIMG = "ascmMall/activity"; // 活动图片上传路径
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; // 二维码存放路径
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(prize)|(app)|(weixin)|(static)|(main)|(websocket)|(notify)).*"; // 不对匹配该值的访问路径拦截（正则）
	public static final String NO_LOG_PATH = ".*/((plugins)|(tab)|(head)).*"; // 不对匹配该值的访问路径记录日志（正则）

	public static ApplicationContext WEB_APP_CONTEXT = null; // 该值会在web容器启动时由WebAppContextListener初始化

	public static final String SALT = "aoshi";

	/**
	 * APP Constants
	 */
	// app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[] { "countries", "uname", "passwd", "title", "full_name", "company_name",
			"countries_code", "area_code", "telephone", "mobile" };
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[] { "国籍", "邮箱帐号", "密码", "称谓", "名称", "公司名称", "国家编号", "区号", "电话", "手机号" };

	// app根据用户名获取会员信息接口_请求协议中的参数
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[] { "USERNAME" };
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[] { "用户名" };

	// 日期全格式
	public static final String DATE_AND_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	// 保留日期部分
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	// 保留时间部分
	public static final String TIME_PATTERN = "HH:mm:ss";

}
