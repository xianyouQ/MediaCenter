package com.youxianqin.mediacenter.bean;



public class VideoType {
	private String TypeName;
	private long RefreshTime;
	
	public VideoType()
	{
		
	}
	public VideoType(String TypeName,long RefreshTime)
	{
		this.TypeName=TypeName;
		this.RefreshTime=RefreshTime;
	}
	
	public void SetTypeName(String TypeName)
	{
		this.TypeName=TypeName;
	}
		
	public String GetTypeName(){
		return this.TypeName;
	}
	
	public void SetRefreshTime(long RefreshTime)
	{
		this.RefreshTime=RefreshTime;
	}
	
	public long GetRefreshTime()
	{
		return this.RefreshTime;
	}
	
}