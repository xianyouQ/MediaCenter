package com.youxianqin.mediacenter;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youxianqin.mediacenter.adapter.MyFragmentPagerAdapter;
import com.youxianqin.mediacenter.bean.constant;


public class MainFragment extends Fragment{

	
	private ViewPager pager;
	private MyFragmentPagerAdapter adapter;	 
	MediaCenterApp MyMediaCenter;
	ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>(); 
    private FragmentManager mFragmentManager;
    private TextView NetWorkErrTextView;
    private View RootView;
	void initView(){
		pager=(ViewPager) RootView.findViewById(R.id.pager);
		MyMediaCenter = (MediaCenterApp) this.getActivity().getApplication();	
		mFragmentManager = this.getChildFragmentManager();
		NetWorkErrTextView = (TextView)RootView.findViewById(R.id.NetWorkErrTextView);
		NetWorkErrTextView.setText(constant.NETWORK_ERROR_STRING);
		//NetWorkErrTextView.setAlpha();
		

		
		for(int i =0 ;i <MyMediaCenter.Titles.size();i++)
		{
			MyFragment mMyFragment = new MyFragment(i);
			mFragmentList.add(mMyFragment);
			
		}
	
		adapter=new MyFragmentPagerAdapter(mFragmentManager, mFragmentList,MyMediaCenter.Titles);
		
		pager.setAdapter(adapter);
		
	
		
		
	}
    @Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
    	
    	RootView=inflater.inflate(R.layout.main_fragment, container,false);
    	initView();
		return RootView;
	}

	public void SetNetWorkErr(boolean show){
    	if(show){
    		if(NetWorkErrTextView.getVisibility()!=View.VISIBLE){
    			NetWorkErrTextView.setVisibility(View.VISIBLE);
    		}
    	}
    	else{
    		if(NetWorkErrTextView.getVisibility()!=View.GONE){
    			NetWorkErrTextView.setVisibility(View.GONE);
    		}
    	}
    	
    }
	
	
	

	
}