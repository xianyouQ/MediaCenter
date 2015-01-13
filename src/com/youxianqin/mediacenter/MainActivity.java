package com.youxianqin.mediacenter;

import java.util.ArrayList;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.youxianqin.mediacenter.adapter.MyFragmentPagerAdapter;
import com.youxianqin.mediacenter.bean.VideoParent;


public class MainActivity extends FragmentActivity{

	private PagerTabStrip tabs;
	private ViewPager pager;
	private MyFragmentPagerAdapter adapter;	
	LayoutInflater mLayoutInflater;  
	MediaCenterApp MyMediaCenter;
	ArrayList<MyFragment> mFragmentList = new ArrayList<MyFragment>(); 
	private DrawerLayout MainView;
	private RelativeLayout LeftView;
    private FragmentManager mFragmentManager;
    private ActionBar myActionBar;
	void initView(){
		mLayoutInflater = getLayoutInflater(); 
		tabs=(PagerTabStrip)findViewById(R.id.pagertab);
		pager=(ViewPager) findViewById(R.id.pager);
		MainView = (DrawerLayout) findViewById(R.id.DrawLayout);
		LeftView = (RelativeLayout) findViewById(R.id.leftView);
		MyMediaCenter = (MediaCenterApp) this.getApplication();	
		mFragmentManager = getSupportFragmentManager();

		

		
		for(int i =0 ;i <MyMediaCenter.Titles.size();i++)
		{
			MyFragment mMyFragment = new MyFragment(i,MyMediaCenter.LruCache);
			mFragmentList.add(mMyFragment);
			
		}
	
		adapter=new MyFragmentPagerAdapter(mFragmentManager, mFragmentList,MyMediaCenter.Titles);
		
		pager.setAdapter(adapter);
		
		MainView.setDrawerListener(new DrawerLayoutListener());
		MyPagerChangeListener listener = new MyPagerChangeListener();
	    pager.setOnPageChangeListener(listener);
		
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initView();
	}
	
	public void ShowLeftView(VideoParent mVideoParent)
	{
		
		
	}
	 @Override  
	    public boolean onCreateOptionsMenu(Menu menu) {  
	        // Inflate the menu; this adds items to the action bar if it is present.  
	        getMenuInflater().inflate(R.menu.activity_main, menu);  
	        return true;  
	    }  
	 
	@Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
        case R.id.action_settings:  
            Toast.makeText(this, "Menu Item refresh selected",  
                    Toast.LENGTH_SHORT).show();  
            break;  
  
        default:  
            break;  
        }  
        return super.onOptionsItemSelected(item);  
    }  
	
	class DrawerLayoutListener implements DrawerListener
	{

		@Override
		public void onDrawerClosed(View arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDrawerOpened(View arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDrawerSlide(View arg0, float arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDrawerStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	class MyPagerChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
		
			
		}
		
	}
	

	
}