package com.youxianqin.mediacenter.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyVideoPlayViewPagerAdapter extends PagerAdapter{
    private ArrayList<View> ViewList;
    private ArrayList<String> TitleList;
  
    public MyVideoPlayViewPagerAdapter(ArrayList<View> ViewList,ArrayList<String> TitleList){
    	this.ViewList = ViewList;
    	this.TitleList = TitleList;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ViewList.size();
	}
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return TitleList.get(position);
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	
	 @Override  
     public void destroyItem(ViewGroup container, int position, Object object)   {     
         container.removeView(ViewList.get(position));//É¾³ýÒ³¿¨  
     }  
    @Override  
    public Object instantiateItem(ViewGroup container, int position) {  
        container.addView(ViewList.get(position));  
        
        return ViewList.get(position);  
    } 
}