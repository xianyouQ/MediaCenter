package com.youxianqin.mediacenter.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youxianqin.mediacenter.R;
import com.youxianqin.mediacenter.bean.VideoParent;


public class PopParentListViewAdapter extends BaseAdapter {
    private ArrayList<VideoParent> VideoParentList=new ArrayList<VideoParent>();
    private LayoutInflater inflater;
    private void initItemArray(HashMap<String,ArrayList<VideoParent>> VideoParentHashMap)
    {
    	Iterator iter = VideoParentHashMap.entrySet().iterator();
    	while (iter.hasNext()) {
    		Map.Entry entry = (Map.Entry) iter.next();
 	  
    		  ArrayList<VideoParent> val = (ArrayList<VideoParent>)entry.getValue();
    		  VideoParentList.addAll(val);
    		}	    
    }
   
    
    public PopParentListViewAdapter(HashMap<String,ArrayList<VideoParent>> VideoParentHashMap,LayoutInflater inflater)
    {
    	initItemArray(VideoParentHashMap);
    	this.inflater = inflater;
    }
	
	public int getCount() {
		// TODO Auto-generated method stub
		return VideoParentList.size();
	}

	@Override
	public VideoParent getItem(int arg0) {
		// TODO Auto-generated method stub
		return VideoParentList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		VideoParent SelectItem = VideoParentList.get(arg0);
		View ItemView = inflater.inflate(R.layout.pop_list_item_activity, null);
		TextView TypeOrParentTV = (TextView) ItemView.findViewById(R.id.TypeOrParentStringTV);
		TypeOrParentTV.setText(SelectItem.GetName());
		if(SelectItem.GetSelect())
			TypeOrParentTV.setBackgroundColor(Color.parseColor("#ffffbb33"));
		return ItemView;
	}

	public void SetItemSelect(int SelectInt)
	{
		for(VideoParent item:VideoParentList)
		{
			item.SetSelect(false);
		}
		VideoParentList.get(SelectInt).SetSelect(true);
	}
}
