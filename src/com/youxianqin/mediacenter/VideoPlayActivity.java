package com.youxianqin.mediacenter;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.MediaController.OnHiddenListener;
import io.vov.vitamio.widget.MediaController.OnShownListener;
import io.vov.vitamio.widget.VideoView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youxianqin.mediacenter.adapter.MyVideoPlayViewPagerAdapter;
import com.youxianqin.mediacenter.adapter.VideoGridViewAdapter;
import com.youxianqin.mediacenter.bean.MySqliteDbHelper;
import com.youxianqin.mediacenter.bean.NetWorkPictureUpLoad;
import com.youxianqin.mediacenter.bean.NetWorkStringDownLoad;
import com.youxianqin.mediacenter.bean.Video;
import com.youxianqin.mediacenter.bean.VideoParent;
import com.youxianqin.mediacenter.bean.constant;



public class VideoPlayActivity extends Activity implements  OnInfoListener, OnBufferingUpdateListener{
	private ArrayList<Video> VideoList = new ArrayList<Video>();
	private VideoView mVideoView;
	private ProgressBar pb;
	private TextView downloadRateView, loadRateView;
	private MySqliteDbHelper VideoViewBufferDBHelper;
	private MediaController myMediaController;
	private ImageButton screenshotBtn;
	private ImageButton FullNarrowBtn;
	private MediaCenterApp myMediaCenterApp;
	private VideoParent mVideoParent;
	private ViewPager mViewPager;
	private ArrayList<View> ViewList = new ArrayList<View>();
	private ArrayList<String> TitleList = new ArrayList<String>();
	private GridView VideoGridView;
	private VideoGridViewAdapter VideoGridViewAdapter;
	private RelativeLayout VideoViewRelativeLayout;
	private WebView VideoInfoWebView;
	private int VideoPos;
	private  DisplayMetrics metric;
	private boolean Orientation=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_play_activity);
		
		 if (!LibsChecker.checkVitamioLibs(this))
		      return;
		 initView();
         InitVideoView();
}

	void initView(){
		 mVideoView = (VideoView) findViewById(R.id.buffer);
		 pb = (ProgressBar) findViewById(R.id.probar);
		 downloadRateView = (TextView) findViewById(R.id.download_rate);
		 myMediaCenterApp=(MediaCenterApp)this.getApplication();
		 VideoViewBufferDBHelper = new MySqliteDbHelper(myMediaCenterApp);
		 loadRateView = (TextView) findViewById(R.id.load_rate);
		 Bundle videoparent = this.getIntent().getBundleExtra("VideoParent");
		 mVideoParent = (VideoParent)videoparent.getSerializable("VideoParent");
		 mViewPager = (ViewPager) findViewById(R.id.videoplaypager);
		 VideoViewRelativeLayout = (RelativeLayout) findViewById(R.id.VideoViewRelativeLayout);
		 screenshotBtn = (ImageButton) findViewById(R.id.ScreenShotBtn);
		 FullNarrowBtn=(ImageButton) findViewById(R.id.FullNarrowScreenBtn);
		 FullNarrowBtn.setOnClickListener(new VideoPlayBtnOnClickListener());
		 screenshotBtn.setOnClickListener(new VideoPlayBtnOnClickListener());
		 LayoutInflater Inflater = getLayoutInflater().from(this);   
		 View VideoListView = Inflater.inflate(R.layout.video_list_activity, null);
		 View VideoInfoView = Inflater.inflate(R.layout.video_info_activity, null);
		 VideoGridView = (GridView) VideoListView.findViewById(R.id.VideoGridView);
		 VideoGridViewAdapter = new VideoGridViewAdapter(VideoList, myMediaCenterApp);
		 VideoGridView.setAdapter(VideoGridViewAdapter);
		 VideoGridView.setOnItemClickListener(new VideoGridViewOnItemClickListener());
		 VideoInfoWebView = (WebView)VideoInfoView.findViewById(R.id.VideoInfoWebView);
		 VideoInfoWebView.setWebViewClient(new WebViewClient() {
			 public boolean shouldOverrideUrlLoading(WebView view, String url)
			                         { //  ��д�˷������������ҳ��������ӻ����ڵ�ǰ��webview����ת������������Ǳ�
			                                 view.loadUrl(url);
			                                 return true;
			                         }
			 });
		 VideoInfoWebView.loadUrl(constant.URL_TO_BAIKE+mVideoParent.GetName());
		 ViewList.add(VideoListView);
		 ViewList.add(VideoInfoView);
		 TitleList.add(constant.VIDEO_PLAY_ACTIVITY_VIEWPAGER_TITLE1);
		 TitleList.add(constant.VIEO_PLAY_ACTIVITY_VIEWPAGER_TITLE2);
		 mViewPager.setAdapter(new MyVideoPlayViewPagerAdapter(ViewList, TitleList));
         myMediaController = new MediaController(this,true,VideoViewRelativeLayout);
         myMediaController.setVisibility(View.GONE);
         VideoDownLoad VideoListDownLoad = new VideoDownLoad();
         VideoListDownLoad.execute(constant.SERVER_HOST+"myhome/getvideolist/?parent="+mVideoParent.GetName());
         metric= new DisplayMetrics();
         getWindowManager().getDefaultDisplay().getMetrics(metric);
         
         myMediaController.setOnShownListener(new OnShownListener() {
		
		  @Override
		  public void onShown() {
			// TODO Auto-generated method stub
		
			if(screenshotBtn.getVisibility() != View.VISIBLE){
				screenshotBtn.setVisibility(View.VISIBLE);
			}
			if(FullNarrowBtn.getVisibility() != View.VISIBLE){
				FullNarrowBtn.setVisibility(View.VISIBLE);
			}
		 }
	 });
     myMediaController.setOnHiddenListener(new OnHiddenListener() {
		
		@Override
		public void onHidden() {
			// TODO Auto-generated method stub
			if(screenshotBtn.getVisibility() != View.GONE){
				screenshotBtn.setVisibility(View.GONE);
			}
			if(FullNarrowBtn.getVisibility() != View.GONE){
				FullNarrowBtn.setVisibility(View.GONE);
			}
		}
	});
   

	}
	
	void InitVideoView(){
		    mVideoView.setMediaController(myMediaController);
		    mVideoView.requestFocus();
		    mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
		    mVideoView.setOnInfoListener(this);
		    mVideoView.setOnBufferingUpdateListener(this);
		    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
		      @Override
		      public void onPrepared(MediaPlayer mediaPlayer) {
		        // optional need Vitamio 4.0
		    	 
		        mediaPlayer.setPlaybackSpeed(1.0f);
		        
		        VideoList.get(VideoPos).SetDuration(mediaPlayer.getDuration());
		        if(!Orientation){
		        LinearLayout.LayoutParams VideoViewRelativeLayoutParams = (LinearLayout.LayoutParams)VideoViewRelativeLayout.getLayoutParams();
		        VideoViewRelativeLayoutParams.height = (int)((float)mediaPlayer.getVideoHeight()*(float)metric.widthPixels/(float)mediaPlayer.getVideoWidth());
		        Log.e("VideoPrepare",mediaPlayer.getVideoHeight()+" "+VideoViewRelativeLayoutParams.height);
		        VideoViewRelativeLayout.setLayoutParams(VideoViewRelativeLayoutParams);
		        }
		      }
		    });
		    mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					SaveVideoHistory();
					VideoPos=VideoPos+1;
					Uri VideoURI = Uri.parse(constant.SERVER_HOST+"/"+VideoList.get(VideoPos).GetVideoUrl());
		      		mVideoView.setVideoURI(VideoURI);
				}
			});
		    
		  
	}

@Override
public boolean onInfo(MediaPlayer mp, int what, int extra) {
  switch (what) {
  case MediaPlayer.MEDIA_INFO_BUFFERING_START:
    if (mVideoView.isPlaying()) {
      mVideoView.pause();
      pb.setVisibility(View.VISIBLE);
      downloadRateView.setText("");
      loadRateView.setText("");
      downloadRateView.setVisibility(View.VISIBLE);
      loadRateView.setVisibility(View.VISIBLE);
      
    }
    break;
  case MediaPlayer.MEDIA_INFO_BUFFERING_END:
    mVideoView.start();
    pb.setVisibility(View.GONE);
    downloadRateView.setVisibility(View.GONE);
    loadRateView.setVisibility(View.GONE);
    break;
  case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
    downloadRateView.setText("" + extra + "kb/s" + "  ");
    break;
  }
  return true;
}

@Override
public void onBufferingUpdate(MediaPlayer mp, int percent) {
  loadRateView.setText(percent + "%");
}
	

private void ChangePhoneToOrientation(){
	LinearLayout.LayoutParams VideoViewRelativeLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
	 VideoViewRelativeLayout.setLayoutParams(VideoViewRelativeLayoutParams);	
	 mViewPager.setVisibility(View.GONE);   
	 getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
    Orientation = true;
}

private void ChangePhoneBackOrientation(){
	mViewPager.setVisibility(View.VISIBLE);
	
	 LinearLayout.LayoutParams VideoViewRelativeLayoutParams = (LinearLayout.LayoutParams) VideoViewRelativeLayout.getLayoutParams();
       VideoViewRelativeLayoutParams.height = (int)((float)mVideoView.getVideoHeight()*(float)metric.widthPixels/(float)mVideoView.getVideoWidth());
       VideoViewRelativeLayout.setLayoutParams(VideoViewRelativeLayoutParams);
       getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
       Orientation=false;
}


private void SaveVideoHistory(){
	if(VideoList.size() > 0)
	{
	int offset = Calendar.getInstance().getTimeZone().getRawOffset();  
	VideoList.get(VideoPos).SetLastTime(System.currentTimeMillis()-offset);
	VideoList.get(VideoPos).SetLastPos(mVideoView.getCurrentPosition());
	VideoViewBufferDBHelper.insertToVideo(VideoList.get(VideoPos));
	}
}

private void ScreenShot()
{
	
	Bitmap result = mVideoView.getCurrentFrame();
	if(result != null){
        Bitmap finalBitmap =ThumbnailUtils.extractThumbnail(result, 500, (int)((float)result.getHeight()/(float)result.getWidth()*500.0));
        myMediaCenterApp.LruCache.addBitmapToMemoryCache(VideoList.get(VideoPos), finalBitmap);
        NetWorkPictureUpLoad uploadPic= new NetWorkPictureUpLoad(VideoList.get(VideoPos));
        uploadPic.execute(finalBitmap);
        
	}
}

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	if(keyCode == KeyEvent.KEYCODE_BACK )
	{
		if(mViewPager.getVisibility() == View.GONE){
			ChangePhoneBackOrientation();
			return true;
		}
		SaveVideoHistory();
	}
	if(keyCode == KeyEvent.KEYCODE_HOME){
		SaveVideoHistory();
	}
	return super.onKeyDown(keyCode, event);
}


private void VideoListClean(){
	ArrayList<Video> HistoryVideoList=VideoViewBufferDBHelper.queryVideo(mVideoParent.GetName());
	for(Video VideoHistoryItem:HistoryVideoList){
		for(Video VideoItem:VideoList){
			if(VideoItem.GetVideoName().equals(VideoHistoryItem.GetVideoName()))
				VideoItem.ChangVideoInfo(VideoHistoryItem);
		}
	}
	Collections.sort(VideoList);
}

class VideoDownLoad extends NetWorkStringDownLoad{

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
		                  Video.SetVideoUrl(VideoItem.getString("full_path")); //hehehehe
		                  Video.SetVideoParent(mVideoParent.GetName());
		                  Video.SetVideoId(VideoItem.getInt("Video_id"));
		                  Video.SetVideoScriptPicUrl(VideoItem.getString("video_scriptPic"));
		                  VideoList.add(Video);
		              }
		              VideoListClean();
		              
		              VideoGridViewAdapter.notifyDataSetChanged();
		              VideoPos =0;
		              
		              if(VideoViewRelativeLayout.getVisibility() != View.VISIBLE)
					        VideoViewRelativeLayout.setVisibility(View.VISIBLE);
			             
		              if(VideoList.size() >0){
		      	        	Uri VideoURI = Uri.parse(constant.SERVER_HOST+"/"+VideoList.get(VideoPos).GetVideoUrl());
		      	        	System.out.println(VideoList.get(VideoPos).GetVideoUrl()+"testtest");
		      	        	mVideoView.setVideoURI(VideoURI);
		     
		              }
		              
		          
		      	   
		           
		           } catch (JSONException e){
		    	    	 e.printStackTrace();
		    	     }
		       }
		      
			  else
			  {
				  Toast.makeText(VideoPlayActivity.this,constant.NETWORK_ERROR_STRING, Toast.LENGTH_LONG).show();
				  
			  }
			
		}
		
		
	}



class VideoGridViewOnItemClickListener implements OnItemClickListener{

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		if(mVideoView.isPlaying())
		{
			mVideoView.stopPlayback();
			
		}
		VideoPos = arg2;
		Uri VideoURI = Uri.parse(constant.SERVER_HOST+VideoList.get(VideoPos).GetVideoUrl());
		mVideoView.setVideoURI(VideoURI);
		mVideoView.start();
	}
}



class VideoPlayBtnOnClickListener implements OnClickListener{

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int ViewId = arg0.getId();
		switch(ViewId){
		
		case R.id.ScreenShotBtn:
			ScreenShot();
			break;
		case R.id.FullNarrowScreenBtn:
		     if(mViewPager.getVisibility() != View.GONE)
		     {
		    	 ChangePhoneToOrientation(); 	
		   
		     }
		     else {
		    	 ChangePhoneBackOrientation();
		     }
			
		    break;
		
		}
	}
	
}
}