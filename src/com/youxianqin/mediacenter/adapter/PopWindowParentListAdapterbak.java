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

import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;
import com.youxianqin.mediacenter.R;
import com.youxianqin.mediacenter.bean.VideoParent;


public class PopWindowParentListAdapterbak extends BaseAdapter implements PinnedSectionListAdapter{
    private ArrayList<Item> VideoParentAndTypeList = new ArrayList<Item>();
    private LayoutInflater inflater;
    private int Pin =1;
    private int NoPin=0;
    private void initItemArray(HashMap<String,ArrayList<VideoParent>> VideoParentHashMap)
    {
    	Iterator iter = VideoParentHashMap.entrySet().iterator();
    	while (iter.hasNext()) {
    		Map.Entry entry = (Map.Entry) iter.next();
    		  String type = (String)entry.getKey();
    		  Item typeItem = new Item(Pin,type);
    		  VideoParentAndTypeList.add(typeItem);
    		  ArrayList<VideoParent> val = (ArrayList<VideoParent>)entry.getValue();
    		  for(VideoParent StrVideoParentItem:val){
    			  Item parentItem = new Item(NoPin,StrVideoParentItem.GetName());
    			  VideoParentAndTypeList.add(parentItem);
    		  }
    		}	    
    }
    
    public PopWindowParentListAdapterbak(HashMap<String,ArrayList<VideoParent>> VideoParentHashMap,LayoutInflater inflater)
    {
    	initItemArray(VideoParentHashMap);
    	this.inflater = inflater;
    }
	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return VideoParentAndTypeList.size();
	}

	@Override
	public Item getItem(int arg0) {
		// TODO Auto-generated method stub
		return VideoParentAndTypeList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public int getItemViewType(int arg0) {
		// TODO Auto-generated method stub
		return VideoParentAndTypeList.get(arg0).getPinned();
	}
	

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Item SelectItem = VideoParentAndTypeList.get(arg0);
		View ItemView = inflater.inflate(R.layout.pop_list_item_activity, null);
		TextView TypeOrParentTV = (TextView) ItemView.findViewById(R.id.TypeOrParentStringTV);
		TypeOrParentTV.setText(SelectItem.getShowString());
		if(SelectItem.GetSelect())
			TypeOrParentTV.setBackgroundColor(Color.parseColor("#ffffbb33"));
		return ItemView;
	}

	
	@Override
	public boolean isItemViewTypePinned(int viewType) {
		// TODO Auto-generated method stub
		return viewType==Pin;
	}
	public void SetItemSelect(int SelectInt)
	{
		for(Item item:VideoParentAndTypeList)
		{
			item.SetSelect(false);
		}
		VideoParentAndTypeList.get(SelectInt).SetSelect(true);
	}
   class Item{
    	private int Pinned;
    	private String ShowString;
    	private boolean IsSelect;
    	public Item(int Pinned,String ShowString){
    		this.Pinned=Pinned;
    		this.ShowString = ShowString;
    	}
    	public int getPinned(){
    		return this.Pinned;
    	}
    	public String getShowString(){
    		return this.ShowString;
    	}
    	public void SetSelect(boolean IsSelect){
    	    if(Pinned==1){
    	    	this.IsSelect=false;
    	    	return ;
    	    }
    		this.IsSelect=IsSelect;
    	}
    	public boolean GetSelect(){
    		return this.IsSelect;
    	}
    }
}