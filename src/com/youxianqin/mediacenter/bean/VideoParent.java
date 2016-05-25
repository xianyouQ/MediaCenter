package com.youxianqin.mediacenter.bean;

import java.io.Serializable;


public class VideoParent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name;
	private String ScriptPicUrl;
	private String Type;
	private boolean IsSelect;
public VideoParent(String Name,String ScriptPicUrl)
{
	this.Name = Name;
	this.ScriptPicUrl=ScriptPicUrl;
}
	
public VideoParent(){
	
}
	

public void SetName(String Name)
{
	this.Name=Name;
}
	
public String GetName(){
	return this.Name;
}
	
public void SetType(String Type)
{
	this.Type=Type;
}
	
public String GetType(){
	return this.Type;
}
public void SetScriptPicUrl(String ScriptPicUrl)
{
	this.ScriptPicUrl=ScriptPicUrl;
}

public String GetScriptPicUrl()
{
	return this.ScriptPicUrl;
}

public void SetSelect(boolean isSelect)
{
	this.IsSelect=isSelect;
}
public boolean GetSelect()
{
	return this.IsSelect;
}
}