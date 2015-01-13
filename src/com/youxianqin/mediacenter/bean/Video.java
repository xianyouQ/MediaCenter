package com.youxianqin.mediacenter.bean;

import java.io.Serializable;

public class Video implements Comparable<Video>,Serializable{
	private String VideoUrl;
	private String  VideoName;
	private String VideoParent;
	private String VideoScriptPicUrl;
	private int VideoId;
	public Video()
	{
		
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
	
	public void SetVideoScriptPicUrl(String VideoScriptPicUrl)
	{
		this.VideoScriptPicUrl=VideoScriptPicUrl;
	}
	public String GetVideoScriptPicUrl()
	{
		return this.VideoScriptPicUrl;
	}
	@Override
	public int compareTo(Video arg0) {
		// TODO Auto-generated method stub
		if(this.VideoId > arg0.VideoId) return 1;
		if(this.VideoId < arg0.VideoId) return -1;
		else return 0;
	}

}