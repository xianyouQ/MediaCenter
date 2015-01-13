package com.youxianqin.mediacenter.bean;

import android.util.Log;


public class VideoHistory implements Comparable<VideoHistory>{
	private String VideoUrl;
	private String  VideoName;
	private String VideoScriptPicUrl;
	private long LastTime;
	private int PinType;
	private long LastPos;
	public VideoHistory()
	{
		
	}
	public VideoHistory(Video VideoItem)
	{
	 this.VideoUrl = VideoItem.GetVideoUrl();
	 this.VideoScriptPicUrl = VideoItem.GetVideoScriptPicUrl();
	 this.VideoName =VideoItem.GetVideoName();
	 Log.e("history init",VideoItem.GetVideoUrl()+" "+VideoItem.GetVideoScriptPicUrl()+" "+VideoItem.GetVideoName());
	}
	public void SetVideoName(String VideoName)
	{
		this.VideoName = VideoName;
	}
	
	public String GetVideoName()
	{
		return this.VideoName;
	}
	
	public void SetVideoUrl(String VideoUrl)
	{
		this.VideoUrl = VideoUrl;
	}
    
	public String GetVideoUrl()
	{
		return this.VideoUrl;
	}
	public void SetVideoScriptPicUrl(String VideoScriptPicUrl)
	{
		this.VideoScriptPicUrl=VideoScriptPicUrl;
	}
	public String GetVideoScriptPicUrl()
	{
		return this.VideoScriptPicUrl;
	}
	
	public void SetLastTime(long LastTime)
	{
	 this.LastTime = LastTime;
	 Log.e("Lastime",this.LastTime+"");
	}
	
	
	public long GetLastTime()
	{
		return this.LastTime;
	}
	public void SetLastPos(long LastPos)
	{
	 this.LastPos = LastPos;
	}
	
	
	public long GetLastPos()
	{
		return this.LastPos;
	}
	
	public void SetPinType(int PinType)
	{
		this.PinType = PinType;
	}
	
	public int GetPinType()
	{
		return this.PinType;
	}
	@Override
	public int compareTo(VideoHistory arg0) {
		// TODO Auto-generated method stub
		if(this.LastTime > arg0.LastTime) return 1;
		if(this.LastTime < arg0.LastTime) return -1;
		else return 0;
	}
	

	
}