package com.youxianqin.mediacenter.bean;

import java.io.Serializable;
import java.util.Date;

import android.util.Log;

public class Video implements Comparable<Video>,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String VideoUrl;
	private String  VideoName;
	private String VideoParent;
	private String VideoScriptPicUrl;
	private int VideoId=0;
	private long LastTime=0;
	private long LastPos=0;
	private long Duration=0;
	private boolean IsSelect =false;
	public Video()
	{
		
	}
	
	public void ChangVideoInfo(Video other)
	{
		this.LastTime = other.LastTime;
		this.LastPos = other.LastPos;
	}
	public Video(String VideoName,String VideoUrl,String VideoParent,int VideoId)
	{
		this.VideoName = VideoName;
		this.VideoUrl=VideoUrl;
		this.VideoParent = VideoParent;
		this.VideoId=VideoId;
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
	public void SetVideoParent(String VideoParent)
	{
		this.VideoParent = VideoParent;
	}
	public String GetVideoParent()
	{
		return this.VideoParent;
	}
	public void SetVideoId(int VideoId)
	{
		this.VideoId = VideoId;
	}
	public int GetVideoId()
	{
		return this.VideoId;
	}
	
	public void SetDuration(long Duration)
	{
		this.Duration = Duration;
	}
	public long GetDuration()
	{
		return this.Duration;
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
	
	@SuppressWarnings("deprecation")
	public String GetLastStringTime()
	{
	 if(this.LastTime == 0) {
		 return " ";
	 }
	 Date date = new Date(this.LastTime);
	  return date.toLocaleString();	
	}
	
	public void SetLastPos(long LastPos)
	{
	 this.LastPos = LastPos;
	}
	
	
	public long GetLastPos()
	{
		return this.LastPos;
	}
	public void SetSelect(boolean isSelect)
	{
		this.IsSelect=isSelect;
	}
	public boolean GetSelect()
	{
		return this.IsSelect;
	}
	
	@Override
	public int compareTo(Video arg0) {
		// TODO Auto-generated method stub
		if(this.VideoId==0) return -1;
		if(this.VideoId > arg0.VideoId) return 1;
		if(this.VideoId < arg0.VideoId) return -1;
		else return 0;
	}

}