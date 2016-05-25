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

import com.youxianqin.mediacenter.adapter.MyFragmentPagerAdapter;
import com.youxianqin.mediacenter.bean.VideoType;
import com.youxianqin.mediacenter.bean.constant;


public class VideoSetFragment extends Fragment {
	private View RootView;
	private ViewPager VideoSetFragmentPager;
	private FragmentManager mFragmentManager;
	private ArrayList<Fragment> FragmentChildList=new ArrayList<Fragment>();
	private MyFragmentPagerAdapter mVideoSetFragmentPagerAdapter;
	private ArrayList<VideoType> VideoTypeList = new ArrayList<VideoType>();
	void initView()
	{
		VideoSetFragmentPager = (ViewPager) RootView.findViewById(R.id.VideoSetFragmentPager);
		VideoSetFragmentChild FragmentChildHasParent = new VideoSetFragmentChild(true);
		VideoSetFragmentChild FragmentChildNOTHasParent = new VideoSetFragmentChild(false);
		FragmentChildList.add(FragmentChildNOTHasParent);
		FragmentChildList.add(FragmentChildHasParent);
		mFragmentManager = this.getChildFragmentManager();
		VideoType mVideoTypeNotParent = new VideoType();
		mVideoTypeNotParent.SetTypeName("未分类");
		VideoType mVideoTypeParent = new VideoType();
		mVideoTypeParent.SetTypeName("已分类");
		VideoTypeList.add(mVideoTypeNotParent);
		VideoTypeList.add(mVideoTypeParent);
		mVideoSetFragmentPagerAdapter = new MyFragmentPagerAdapter(mFragmentManager,
				FragmentChildList,VideoTypeList);
		VideoSetFragmentPager.setAdapter(mVideoSetFragmentPagerAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		RootView=inflater.inflate(R.layout.video_set_fragment_activity, container,false);
    	initView();
		return RootView;
	}
	
	
	
	
}