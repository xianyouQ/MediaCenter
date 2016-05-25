package com.youxianqin.mediacenter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import android.util.Log;

import com.youxianqin.mediacenter.bean.LruCacheForMediaCenter;
import com.youxianqin.mediacenter.bean.VideoParent;
import com.youxianqin.mediacenter.bean.VideoType;


public class MediaCenterApp extends Application{
	
	//private MyPagerAdapter adapter;

	public ArrayList<VideoType> Titles= new ArrayList<VideoType>();
	public HashMap<String,ArrayList<VideoParent>> VideoParentList=new HashMap<String,ArrayList<VideoParent>>();
	public LruCacheForMediaCenter LruCache;
	public boolean NetError;
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