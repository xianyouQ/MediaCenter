package com.youxianqin.mediacenter;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.youxianqin.mediacenter.adapter.LeftMenuListViewAdapter;
import com.youxianqin.mediacenter.bean.NetWorkStringDownLoad;
import com.youxianqin.mediacenter.bean.constant;

public class MainActivity extends FragmentActivity {
    private ListView LeftMenuList;
    private FragmentManager fragmentManager;
    private DrawerLayout mDrawerLayout;
    private LeftMenuListViewAdapter mLeftMenuListViewAdapter;
    private ActionBar mActionBar;
    private boolean ShowMenu;
    void initView()
    {
    	mDrawerLayout=(DrawerLayout)findViewById(R.id.MainDrawerLayout);
    	LeftMenuList=(ListView)findViewById(R.id.LeftMenuListView);
    	mActionBar = getActionBar();
    	mLeftMenuListViewAdapter=new LeftMenuListViewAdapter(constant.LEFT_MENU_STRING_ARRAY, LayoutInflater.from(this));
    	LeftMenuList.setAdapter(mLeftMenuListViewAdapter);
    	LeftMenuList.setOnItemClickListener(new LeftMenuListOnItemClickListener());
    	MainFragment mMianFragment= new MainFragment();
    	
    	fragmentManager=getSupportFragmentManager();
    	ReplaceFragment(mMianFragment);
    }
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main_activity);
		initView();
	}
	
	public void ReplaceFragment(Fragment f){
		FragmentTransaction DefaultFT=fragmentManager.beginTransaction();
    	DefaultFT.replace(R.id.MainFragmentLayout, f);
    	DefaultFT.commit();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        MenuItem AddTypeMenuItem = menu.findItem(R.id.add_videotype);
        MenuItem AddParentMenuItem = menu.findItem(R.id.add_videoparent);
        AddTypeMenuItem.setVisible(false);
        AddParentMenuItem.setVisible(false);
        if(ShowMenu){
        	 AddTypeMenuItem.setVisible(true);
             AddParentMenuItem.setVisible(true);
        }
        return true;
    }
	
	@Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
        case R.id.add_videotype:  
        	  Intent MainIntentToAddType = new Intent();
        	  MainIntentToAddType.setClass(MainActivity.this, AddVideoType.class);
	          MainActivity.this.startActivity(MainIntentToAddType);
            break;  
        case R.id.add_videoparent:
        	Intent MainIntentToAddParent = new Intent();
        	MainIntentToAddParent.setClass(MainActivity.this, AddVideoParent.class);
	       MainActivity.this.startActivity(MainIntentToAddParent);
        	break;
        case R.id.init_service:
        	NetWorkStringDownLoad initNetWorkAccess = new NetWorkStringDownLoad();
        	initNetWorkAccess.execute(constant.SERVER_HOST+"myhome/initdata/");
        default:  
            break;  
        }  
        return super.onOptionsItemSelected(item);  
    }  
	private class LeftMenuListOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(arg2==0){
				MainFragment mMianFragment= new MainFragment();
				ReplaceFragment(mMianFragment);
				mDrawerLayout.closeDrawers();
				if(ShowMenu){
					ShowMenu = false;
					supportInvalidateOptionsMenu();
				}
			}
			
			if(arg2==1){
				VideoSetFragment mVideoSetFragment = new  VideoSetFragment();
				ReplaceFragment(mVideoSetFragment);
				if(!ShowMenu){
					ShowMenu = true;
					supportInvalidateOptionsMenu();
				}
				mDrawerLayout.closeDrawers();
			}
			if(arg2==2){
				
				if(ShowMenu){
					ShowMenu = false;
					supportInvalidateOptionsMenu();
				}
			}
		}
		
	}
}