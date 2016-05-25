package com.youxianqin.mediacenter.adapter;
import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.youxianqin.mediacenter.bean.VideoType;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> MyFragmentList;
	private ArrayList<VideoType> VideoTypeList;
        public MyFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> MyFragmentList,ArrayList<VideoType> VideoTypeList) {
		super(fm);
		this.MyFragmentList=MyFragmentList;
		this.VideoTypeList=VideoTypeList;
		// TODO Auto-generated constructor stub
	}

		
       
     
		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return MyFragmentList.size();
		}






		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return VideoTypeList.get(position).GetTypeName();
		}






		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return MyFragmentList.get(arg0);
		}






}