package com.youxianqin.mediacenter.adapter;

import java.util.ArrayList;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youxianqin.mediacenter.R;
import com.youxianqin.mediacenter.bean.VideoType;

public class AddParentListViewAdapter extends BaseAdapter {
    private ArrayList<VideoType> VideoTypeList;
    private LayoutInflater inflater;
    public AddParentListViewAdapter(ArrayList<VideoType> VideoTypeList,LayoutInflater inflater)
    {
    	this.VideoTypeList=VideoTypeList;
    	this.inflater=inflater;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return VideoTypeList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	
			convertView = inflater
					.inflate(R.layout.left_menu_item_activity, null);

			TextView LeftMenuItemTextView = (TextView) convertView.findViewById(R.id.LeftMenuItemTextView);
            LeftMenuItemTextView.setText(VideoTypeList.get(position).GetTypeName());
            if(VideoTypeList.get(position).GetSelect())
            {
            	LeftMenuItemTextView.setBackgroundColor(Color.parseColor("#ffffbb33"));
            }
		    return convertView;
	
}
	
	public void SetItemSelect(int position){
		
		for(VideoType item:VideoTypeList)
		{
			item.SetSelect(false);
		}
		VideoTypeList.get(position).SetSelect(true);
	}
	
	
}
