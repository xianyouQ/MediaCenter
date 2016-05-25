package com.youxianqin.mediacenter.bean;


public class constant {
	
public static String SERVER_HOST="http://192.168.31.1:8888/";
public static String PICTURE_URL="WebSystem/";

//public static String MENU_STRING="锟剿碉拷";
	
public static String TABLE_TYPE="MovieType";
public static String TABLE_TYPE_COLUMN="type";
public static String TABLE_REFRESHTIME_COLUMN="refreshtime";
public static String CREATE_TABLE_TYPE_SQL="create table if not exists "+TABLE_TYPE+
		" ("+TABLE_TYPE_COLUMN+" varchar(30),"+
		TABLE_REFRESHTIME_COLUMN+" INTEGER)";
	
public static String NETWORK_ERROR_STRING="网络不听话了";

public static String SQLITE_DB_NAME ="mediacenter";

public static int SQLITE_DB_VERSION = 1;
public static String TABLE_VIDEOPARENT="videoparent";
public static String TABLE_VIDEOPARENT_NAME_COLUMN="name";
public static String TABLE_VIDEOPARENT_SCRIPTIMAGE_URL="picurl";
public static String CREATE_TABLE_VIDEOPARENT_SQL="create table if not exists "+TABLE_VIDEOPARENT+
" ("+TABLE_VIDEOPARENT_NAME_COLUMN+" varchar(40),"+
TABLE_TYPE_COLUMN+" varchar(30),"+	
TABLE_VIDEOPARENT_SCRIPTIMAGE_URL+" varchar(200))";

public static String TABLE_VIDEO="video";
public static String TABLE_LAST_PLAY_TIME_COLUMN="lasttime";
public static String TABLE_VIDEO_NAME_COLUMN="videoname";
public static String TABLE_VIDEO_URL_COLUMN="videourl";
public static String TABLE_VIDEO_ID_COLUMN="videoid";
public static String TABLE_VIDEO_SCRIPTIMAGE_URL="picurl";
public static String TABLE_LAST_PLAY_POS_COLUMN="lastpos";
public static String TABLE_VIDEO_DURATION="duration";
public static String CREATE_TABLE_VIDEO_SQL="create table if not exists "+TABLE_VIDEO+
" ("+TABLE_VIDEO_URL_COLUMN+" varchar(200),"+
TABLE_VIDEO_NAME_COLUMN+" varchar(100),"+	
TABLE_VIDEO_SCRIPTIMAGE_URL+" varchar(200),"+
TABLE_LAST_PLAY_TIME_COLUMN+" INTEGER,"+
TABLE_LAST_PLAY_POS_COLUMN+" INTEGER,"+
TABLE_VIDEO_DURATION+" INTEGER,"+
TABLE_VIDEOPARENT_NAME_COLUMN+" varchar(40),"+
TABLE_VIDEO_ID_COLUMN+" INTEGER)";

public static String VIDEO_PLAY_ACTIVITY_VIEWPAGER_TITLE1="列表";
public static String VIEO_PLAY_ACTIVITY_VIEWPAGER_TITLE2="影片信息";
public static String[] LEFT_MENU_STRING_ARRAY ={"主页","视频分类","更多"};
public static String[] VIDEO_SET_TYPE={"未分类","已分类"};
public static String INPUT_NULL_STRING_WARRING="输入不能为空";
public static String URL_TO_BAIKE="http://wapbaike.baidu.com/search?word=";
}