package com.youxianqin.mediacenter;

import java.util.ArrayList;

import android.app.Application;
import android.util.Log;

import com.youxianqin.mediacenter.bean.LruCacheForMediaCenter;
import com.youxianqin.mediacenter.bean.VideoType;


public class MediaCenterApp extends Application{
	
	//private MyPagerAdapter adapter;

	ArrayList<VideoType> Titles= new ArrayList<VideoType>();
	LruCacheForMediaCenter LruCache;
	boolean NetError;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		LruCache = new LruCacheForMediaCenter(this);
		Log.e("App","Application create");
		
		//System.out.println("new app start");
		//Titles.add(constant.MENU_STRING);
	}
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		LruCache.close();
		Log.e("App","Application Destory");
	}
	
	
	
	
	
	
}