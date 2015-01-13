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
import android.widget.Toast;

import com.huewu.pla.lib.MultiColumnPullToRefreshListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.youxianqin.mediacenter.adapter.MyPinterestLinkAdatper;
import com.youxianqin.mediacenter.bean.LruCacheForMediaCenter;
import com.youxianqin.mediacenter.bean.MySqliteDbHelper;
import com.youxianqin.mediacenter.bean.NetWorkStringDownLoad;
import com.youxianqin.mediacenter.bean.VideoParent;
import com.youxianqin.mediacenter.bean.constant;


public class MyFragment extends Fragment{
   private MyPinterestLinkAdatper adapter;
   private ArrayList<VideoParent> VideoParentList;
   private MediaParentDownLoad MyMediaParentDownLoad;
   private MySqliteDbHelper VideoParentDBHelper;
   private LruCacheForMediaCenter LruCache;
   private long NowTime;
   private int Pos;
   private MediaCenterApp myMediaCenterApp;
   private MainActivity FragmentParent;
   private View RootView;
   public MyFragment(int  Pos, LruCacheForMediaCenter LruCache){
	   this.Pos=Pos;
	   this.LruCache = LruCache;
	 
   }
   public MyFragment(){
	   
   }
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(RootView == null || myMediaCenterApp == null || VideoParentList == null || VideoParentDBHelper == null)
		{
		FragmentParent = (MainActivity)this.getActivity();
		myMediaCenterApp = (MediaCenterApp)FragmentParent.getApplication();
		VideoParentList =new ArrayList<VideoParent>();
		RootView=inflater.inflate(R.layout.pageritem, container,false);
		MultiColumnPullToRefreshListView RefreshListView = (MultiColumnPullToRefreshListView)RootView.findViewById(R.id.list_warterfall);
		VideoParentDBHelper = new MySqliteDbHelper(myMediaCenterApp);
		System.out.println("Title count "+myMediaCenterApp.Titles.size()+" Pos "+Pos);
		 VideoParentList.addAll(VideoParentDBHelper.queryVideoParent(myMediaCenterApp.Titles.get(Pos).GetTypeName()));
		RefreshListView.setOnItemClickListener(new RefreshListViewOnItemClickListener());
		adapter = new MyPinterestLinkAdatper(VideoParentList, myMediaCenterApp, LruCache);
		RefreshListView.setAdapter(adapter);
		}
		NowTime = System.currentTimeMillis();      
	 	if(NowTime-myMediaCenterApp.Titles.get(Pos).GetRefreshTime() > 600000){
		
	    MyMediaParentDownLoad = new MediaParentDownLoad();
	    MyMediaParentDownLoad.execute(constant.SERVER_HOST+"/myhome/getvideoparent/?type="+myMediaCenterApp.Titles.get(Pos).GetTypeName());
	 	}
		return RootView;
	}
	
	 
	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("Fragment"+myMediaCenterApp.Titles.get(Pos).GetTypeName()+"Destroy");
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		((ViewGroup) RootView.getParent()).removeView(RootView);
		super.onDestroyView();
		
		System.out.println("Fragment"+myMediaCenterApp.Titles.get(Pos).GetTypeName()+"DestroyView");
	}





	class MediaParentDownLoad extends NetWorkStringDownLoad{

	
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			  if (result !=null )
		       {
		    	   try {
		              
		              JSONArray VideoParentJsonArray = new JSONArray(result);
		              VideoParentList.clear();
		              for (int i =0 ;i < VideoParentJsonArray.length() ; i++)
		              {
		            	  JSONObject VideoParentItemJson=(JSONObject) VideoParentJsonArray.get(i);
		            	  JSONObject  VideoParentItem = VideoParentItemJson.getJSONObject("fields");
		                  VideoParent VideoParent = new VideoParent();
		                  VideoParent.SetName(VideoParentItem.getString("parent_name"));
		                  VideoParent.SetScriptPicUrl(VideoParentItem.getString("videoparent_scriptPic")); //hehehehe
		                  VideoParent.SetType(myMediaCenterApp.Titles.get(Pos).GetTypeName());
		                  VideoParentList.add(VideoParent);
		              }
		              myMediaCenterApp.Titles.get(Pos).SetRefreshTime(NowTime);
		              
		              VideoParentDBHelper.insertToVideoParent(VideoParentList,myMediaCenterApp.Titles.get(Pos).GetTypeName());
		              VideoParentDBHelper.insertToTableType(myMediaCenterApp.Titles.get(Pos));
		              adapter.notifyDataSetChanged();
		           } catch (JSONException e){
		    	    	 e.printStackTrace();
		    	     }
		       }
		      
			  else
			  {
				  Toast.makeText(myMediaCenterApp,constant.NETWORK_ERROR_STRING, Toast.LENGTH_LONG).show();
				  
			  }
			
		}
		
		
	}
	
	class RefreshListViewOnItemClickListener implements com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(PLA_AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			 VideoParent ClickVideoParent = VideoParentList.get((int)id);
			 Intent intent = new Intent();
			 intent.setClass(MyFragment.this.getActivity(), VideoPlayActivity.class);
			 Bundle bd = new Bundle();
			 bd.putSerializable("VideoParent", ClickVideoParent);
			 intent.putExtra("VideoParent", bd);
			 startActivity(intent);
		}

	
	}
	
}