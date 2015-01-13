package com.youxianqin.mediacenter.bean;


public class constant {
	
public static String SERVER_HOST="http://www.miwifi.com:8888";

public static String MENU_STRING="菜单";
	
public static String TABLE_TYPE="MovieType";
public static String TABLE_TYPE_COLUMN="type";
public static String TABLE_REFRESHTIME_COLUMN="refreshtime";
public static String CREATE_TABLE_TYPE_SQL="create table if not exists "+TABLE_TYPE+
		" ("+TABLE_TYPE_COLUMN+" varchar(30),"+
		TABLE_REFRESHTIME_COLUMN+" INTEGER)";
	
public static String NETWORK_ERROR_STRING="更新服务信息失败，请检查网络连接";

public static String SQLITE_DB_NAME ="mediacenter";

public static int SQLITE_DB_VERSION = 1;
public static String TABLE_VIDEOPARENT="videoparent";
public static String TABLE_VIDEOPARENT_NAME_COLUMN="name";
public static String TABLE_VIDEOPARENT_SCRIPTIMAGE_URL="picurl";
public static String CREATE_TABLE_VIDEOPARENT_SQL="create table if not exists "+TABLE_VIDEOPARENT+
" ("+TABLE_VIDEOPARENT_NAME_COLUMN+" varchar(40),"+
TABLE_TYPE_COLUMN+" varchar(30),"+	
TABLE_VIDEOPARENT_SCRIPTIMAGE_URL+" varchar(200))";

public static String TABLE_HISTORY="history";
public static String TABLE_LAST_PLAY_TIME_COLUMN="lasttime";
public static String TABLE_VIDEO_NAME_COLUMN="videoname";
public static String TABLE_VIDEO_URL_COLUMN="videourl";
public static String TABLE_VIDEO_SCRIPTIMAGE_URL="picurl";
public static String TABLE_LAST_PLAY_POS_COLUMN="lastpos";
public static String CREATE_TABLE_HISTORY_SQL="create table if not exists "+TABLE_HISTORY+
" ("+TABLE_VIDEO_URL_COLUMN+" varchar(200),"+
TABLE_VIDEO_NAME_COLUMN+" varchar(100),"+	
TABLE_VIDEO_SCRIPTIMAGE_URL+" varchar(200),"+
TABLE_LAST_PLAY_TIME_COLUMN+" INTEGER,"+
TABLE_LAST_PLAY_POS_COLUMN+" INTEGER)";
public static String VIDEO_PLAY_ACTIVITY_VIEWPAGER_TITLE1="视频列表";
public static String VIEO_PLAY_ACTIVITY_VIEWPAGER_TITLE2="历史记录";
}