package com.wyxz.chaogao.app;

import java.io.File;

import android.os.Environment;

/**
 * 常量
 * 
 * @author wuzhenlin
 * 
 */
public interface Constants {
    String TYPE_FAV = "fav";
    String TYPE_FEED="feed";
    String TYPE_RSS = "rss";
    String VERSION_CODE = "1";// 这里不能格式化
    String VERSION_NAME = "1.0.0";// 这里不能格式化
    boolean DEBUG = false;// 这里不能格式化,影响打包
    String MARKET = "anzhuo";
    /** 网络请求返回成功的结果码**/
    int SUCC_CODE = 0;
    /** 缓存图片内存大小**/
    int MEMORY_CACHE_SIZE = 6 * 1024 * 1024;// 6M
    /** 缓存图片文件夹大小**/
    long DISK_CACHE_SIZE = 100 * 1024 * 1024;// 100M
    /** 缓存图片文件夹位置**/
    File IMAGE_CACHE_DIR = new File(Environment.getExternalStorageDirectory(), "bs"
            + File.separator + "ImageCache");
    
    /** 下载图片文件夹位置**/
    File IMAGE_DOWNLOAD_DIR = new File(Environment.getExternalStorageDirectory(), "DCIM"
            + File.separator + "Reader");
    
    /** 初始化应用 失败 广播 动作**/
    String ACTION_INIT_FAIL = "com.spriteapp.reader.init_fail";
    /** 初始化应用 成功 广播 动作**/
    String ACTION_INIT_SUCC = "com.spriteapp.reader.init_succ";
    /** 初始化应用 跳转登录界面广播 动作**/
    String ACTION_INIT_SIGNIN = "com.spriteapp.reader.init_signin";
    /** 订阅标签有变动广播 动作**/
    String ACTION_CHANGE_TAG = "com.spriteapp.reader.change_tag";
    
    /** 标签类型 系统标签**/
    String TAG_SYS = "sys";
    /** 标签类型 用户标签**/
    String TAG_SUBSCRIBE = "tag";
    /** 标签类型 用户标签**/
    String TAG_FAVORITE = "fav";
    
    /** 标签和作者关联类型 系统标签下作者 0**/
    String TAG_AUTHOR_SYS = "0";
    /** 标签和作者关联类型 用户订阅标签下作者 1**/
    String TAG_AUTHOR_USER = "1";
    
    
    /**文章阅读状态**/
    String FEED_UNREAD="0";//未阅读
    String FEED_READED="1";//已阅读
    
    /** 请求码**/
    int BASE_REQUEST_CODE = 100;
    
    /** 请求码 进入登录界面**/
    int REQUEST_CODE_SIGNIN = BASE_REQUEST_CODE + 1;
    /** 请求码 进入注册界面**/
    int REQUEST_CODE_SIGNUP = BASE_REQUEST_CODE + 2;
    /** 请求码 进入个人信息界面**/
    int REQUEST_CODE_PROFILE = BASE_REQUEST_CODE + 3;
    /** 请求码 进入编辑用户头像界面  一般是选择图片后进入  编辑个人头像  **/
    int REQUEST_CODE_CROP_IMAGE = BASE_REQUEST_CODE + 4;
    /** 请求码 进入相机拍照界面**/
    int REQUEST_CODE_CHOICE_PICTURE_BY_CAMERA = BASE_REQUEST_CODE + 5;
    /** 请求码 进入相册界面**/
    int REQUEST_CODE_CHOICE_PICTURE_BY_ALBUM = BASE_REQUEST_CODE + 6;
    /** 请求码 进入编辑用户昵称界面**/
    int REQUEST_CODE_EDIT_USER_NICKNAME = BASE_REQUEST_CODE + 7;
    /** 请求码 进入编辑用户简介界面**/
    int REQUEST_CODE_EDIT_USER_DESC = BASE_REQUEST_CODE + 8;
    
    /** 最大简介长度**/
    int MAX_DESC_LEN = 30;
    /** 最小简介长度**/
    int MIN_DESC_LEN = 5;
    
    /** 传递用户bean**/
    String PASSING_USER_KEY = "PASSING.USER.KEY";
    
    /** 用户性别 男  类型 **/
    int GENDER_MALE_TYPE = 1;
    /** 用户性别 女  类型 **/
    int GENDER_FEMALE_TYPE = 2;
    /** 用户性别 男 **/
    String GENDER_MALE = "男";
    /** 用户性别 女**/
    String GENDER_FEMALE = "女";
    
    /** 绑定第三方平台类型  qq**/
    String BIND_TYPE_QQ = "qq";
    /** 绑定第三方平台类型  sina**/
    String BIND_TYPE_SINA = "sina";
    
    /** 分页的每页数量**/
    int PAGE_COUNT = 20;
    int OFFLINE_COUNT = 100;
    
    /** 广点通广告应用appid**/
    String APPId = "1104009454";
    /** 广点通  banner  广告位id**/
    String BannerPosId = "5060206102813530";
    /** 广点通  应用墙   广告位id**/
    String APPWallPosId = "6020002182816504";
    /** 广点通  插屏  广告位id**/
    String InterteristalPosId = "5010500192211575";
    /** 广点通  开屏  广告位id**/
    String SplashPosId = "6070107172517583";
    
}
