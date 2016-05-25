package com.youxianqin.mediacenter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youxianqin.mediacenter.R;

public class LeftMenuListViewAdapter extends BaseAdapter {
    private String[] MenuStringList;
    private LayoutInflater inflater;
    public LeftMenuListViewAdapter(String[] MenuStringList,LayoutInflater inflater)
    {
    	this.MenuStringList=MenuStringList;
    	this.inflater=inflater;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return MenuStringList.length;
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
		final ViewHolder holder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater
					.inflate(R.layout.left_menu_item_activity, null);
			holder = new ViewHolder();
			holder.LeftMenuItemTextView = (TextView) convertView.findViewById(R.id.LeftMenuItemTextView);
			convertView.setTag(holder);
	}
	else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.LeftMenuItemTextView.setText(MenuStringList[position]);
	
		return convertView;
	
}
	
	class ViewHolder{
		TextView LeftMenuItemTextView;
		
	}
}
