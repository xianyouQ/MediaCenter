package com.youxianqin.mediacenter;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.hb.views.PinnedSectionListView;
import com.youxianqin.mediacenter.adapter.PopWindowParentListAdapterbak;
import com.youxianqin.mediacenter.adapter.VideoSetGridViewAdapter;
import com.youxianqin.mediacenter.bean.NetWorkStringDownLoad;
import com.youxianqin.mediacenter.bean.NetWorkStringUpLoad;
import com.youxianqin.mediacenter.bean.Video;
import com.youxianqin.mediacenter.bean.constant;


public class VideoSetFragmentChild extends Fragment  {
    private View RootView;
    private GridView VideoGridView;
    private ArrayList<Video> VideoList;
    private MediaCenterApp mMediaCenterApp;
    private VideoSetGridViewAdapter VideoSetGridViewAdapter;
    private boolean HasParent;
    private VideoSetDownLoad mVideoSetDownLoad;
    private Button VideoSetBtn;


    
    public VideoSetFragmentChild(boolean HasParent)
    {
    	this.HasParent = HasParent;
    }
    void initView()
    {
    	mMediaCenterApp = (MediaCenterApp) this.getParentFragment().getActivity().getApplication();
    	VideoList = new ArrayList<Video>();
    	VideoGridView = (GridView) RootView.findViewById(R.id.VideoSetGridView);
    	VideoSetGridViewAdapter = new VideoSetGridViewAdapter(VideoList,mMediaCenterApp);
    	VideoGridView.setAdapter(VideoSetGridViewAdapter);
    	VideoGridView.setOnItemClickListener(new VideoGridViewOnItemClickListener());
    	mVideoSetDownLoad = new VideoSetDownLoad();
    	VideoSetBtn = (Button) RootView.findViewById(R.id.VideoSetBtn);
    	VideoSetBtn.setOnClickListener(new VideoSetBtnOnClickListener());
    	if(HasParent)
    		mVideoSetDownLoad.execute(constant.SERVER_HOST+"myhome/getnotvideolist/?parent="+constant.VIDEO_SET_TYPE[0]);
    	else {
    		mVideoSetDownLoad.execute(constant.SERVER_HOST+"myhome/getvideolist/?parent="+constant.VIDEO_SET_TYPE[0]);
    	}
    	
    }

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		RootView = inflater.inflate(R.layout.video_set_fragment_child_activity, null);
		initView();
		return RootView;
	}
	
	
	class VideoSetDownLoad extends NetWorkStringDownLoad{

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			  if (result !=null )
		       {
		    	   try {
		              
		              JSONArray VideoJsonArray = new JSONArray(result);
		              VideoList.clear();
		              for (int i =0 ;i < VideoJsonArray.length() ; i++)
		              {
		            	  JSONObject VideoItemJson=(JSONObject) VideoJsonArray.get(i);
		            	  JSONObject  VideoItem = VideoItemJson.getJSONObject("fields");
		                  Video Video = new Video();
		                  Video.SetVideoName(VideoItem.getString("file_name"));
		                  Video.SetVideoUrl(VideoItem.getString("full_path")); //heheheh
		                  Video.SetVideoScriptPicUrl(VideoItem.getString("video_scriptPic"));
		                  VideoList.add(Video);
		              }
		              //VideoListClean();
		              
		              VideoSetGridViewAdapter.notifyDataSetChanged();
		           } catch (JSONException e){
		    	    	 e.printStackTrace();
		    	     }
		       }
		      
			  else
			  {
				  //Toast.makeText(VideoPlayActivity.this,constant.NETWORK_ERROR_STRING, Toast.LENGTH_LONG).show();
				  
			  }
			
		}
		
		
	}

 
	
	private class VideoSetBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//int ViewId = arg0.getId();
			switch(arg0.getId()){
			case R.id.VideoSetBtn:
				HashMap<String,ArrayList<String>> SelectVideoList = new HashMap<String,ArrayList<String>>();
				ArrayList<String> SelectVideoString= new ArrayList<String>();

				for(Video mVideo:VideoList)
				{
					if(mVideo.GetSelect())
					{
						SelectVideoString.add(mVideo.GetVideoScriptPicUrl());
					}
				}
				 SelectVideoList.put("video_scriptPic",SelectVideoString);
				 Intent intent = new Intent();
				 intent.setClass(VideoSetFragmentChild.this.getParentFragment().getActivity(), ChangeVideoParent.class);
				 Bundle bd = new Bundle();
				 bd.putSerializable("VideoList", SelectVideoList);
				 intent.putExtra("VideoList", bd);
				 startActivity(intent);
				break;
		
		}
		}
		
	}
	

	private class VideoGridViewOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			VideoSetGridViewAdapter.SetItemSelect(arg2);
			VideoSetGridViewAdapter.notifyDataSetInvalidated(); 
		}
		
	}
}