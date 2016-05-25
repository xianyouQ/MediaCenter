package com.youxianqin.mediacenter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huewu.pla.lib.MultiColumnPullToRefreshListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.youxianqin.mediacenter.adapter.MyPinterestLinkAdatper;
import com.youxianqin.mediacenter.bean.MySqliteDbHelper;
import com.youxianqin.mediacenter.bean.NetWorkStringDownLoad;
import com.youxianqin.mediacenter.bean.VideoParent;
import com.youxianqin.mediacenter.bean.constant;


public class MyFragment extends Fragment{
   private MyPinterestLinkAdatper adapter;
   private MediaParentDownLoad MyMediaParentDownLoad;
   private MySqliteDbHelper VideoParentDBHelper;
   private long NowTime;
   private int Pos;
   private MediaCenterApp myMediaCenterApp;
   private MainFragment FragmentParent;
   private View RootView;
   public MyFragment(int  Pos){
	   this.Pos=Pos;
	 
   }
   public MyFragment(){
	   
   }
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		FragmentParent = (MainFragment)this.getParentFragment();
		myMediaCenterApp = (MediaCenterApp)FragmentParent.getActivity().getApplication();
		RootView=inflater.inflate(R.layout.pageritem, container,false);
		MultiColumnPullToRefreshListView RefreshListView = (MultiColumnPullToRefreshListView)RootView.findViewById(R.id.list_warterfall);
		VideoParentDBHelper = new MySqliteDbHelper(myMediaCenterApp);
		myMediaCenterApp.VideoParentList.put(myMediaCenterApp.Titles.get(Pos).GetTypeName(),VideoParentDBHelper.queryVideoParent(myMediaCenterApp.Titles.get(Pos).GetTypeName()));
		RefreshListView.setOnItemClickListener(new RefreshListViewOnItemClickListener());
		adapter = new MyPinterestLinkAdatper(myMediaCenterApp.VideoParentList.get(myMediaCenterApp.Titles.get(Pos).GetTypeName()), myMediaCenterApp);
		RefreshListView.setAdapter(adapter);
		NowTime = System.currentTimeMillis();      
	 	if(NowTime-myMediaCenterApp.Titles.get(Pos).GetRefreshTime() > 600000){
		
	    MyMediaParentDownLoad = new MediaParentDownLoad();
	    MyMediaParentDownLoad.execute(constant.SERVER_HOST+"myhome/getvideoparent/?type="+myMediaCenterApp.Titles.get(Pos).GetTypeName());
	 	}
		return RootView;
	}
	
	

	class MediaParentDownLoad extends NetWorkStringDownLoad{

	
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			  if (result !=null )
		       {
		    	   try {
		              
		              JSONArray VideoParentJsonArray = new JSONArray(result);
		              myMediaCenterApp.VideoParentList.remove(myMediaCenterApp.Titles.get(Pos).GetTypeName());
		              ArrayList<VideoParent> mVideoParentList = new ArrayList<VideoParent>();
		              for (int i =0 ;i < VideoParentJsonArray.length() ; i++)
		              {
		            	  JSONObject VideoParentItemJson=(JSONObject) VideoParentJsonArray.get(i);
		            	  JSONObject  VideoParentItem = VideoParentItemJson.getJSONObject("fields");
		                  VideoParent VideoParent = new VideoParent();
		                  VideoParent.SetName(VideoParentItem.getString("parent_name"));
		                  VideoParent.SetScriptPicUrl(VideoParentItem.getString("videoparent_scriptPic")); 
		                  VideoParent.SetType(myMediaCenterApp.Titles.get(Pos).GetTypeName());
		                  mVideoParentList.add(VideoParent);
		              }
		              myMediaCenterApp.Titles.get(Pos).SetRefreshTime(NowTime);
		              
		              VideoParentDBHelper.insertToVideoParent(mVideoParentList,myMediaCenterApp.Titles.get(Pos).GetTypeName());
		              VideoParentDBHelper.insertToTableType(myMediaCenterApp.Titles.get(Pos));
		              myMediaCenterApp.VideoParentList.put(myMediaCenterApp.Titles.get(Pos).GetTypeName(),VideoParentDBHelper.queryVideoParent(myMediaCenterApp.Titles.get(Pos).GetTypeName()));
		              adapter.notifyDataSetChanged();
		           } catch (JSONException e){
		    	    	 e.printStackTrace();
		    	     }
		    	   FragmentParent.SetNetWorkErr(false);
		       }
		      
			  else
			  {
				  FragmentParent.SetNetWorkErr(true);
				  
			  }
			
		}
		
		
	}
	
	class RefreshListViewOnItemClickListener implements com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(PLA_AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			 VideoParent ClickVideoParent = myMediaCenterApp.VideoParentList.get(myMediaCenterApp.Titles.get(Pos).GetTypeName()).get((int)id);
			 Intent intent = new Intent();
			 intent.setClass(MyFragment.this.getActivity(), VideoPlayActivity.class);
			 Bundle bd = new Bundle();
			 bd.putSerializable("VideoParent", ClickVideoParent);
			 intent.putExtra("VideoParent", bd);
			 startActivity(intent);
		}

	
	}
	
}