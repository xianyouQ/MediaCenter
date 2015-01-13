package com.youxianqin.mediacenter.bean;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySqliteDbHelper extends SQLiteOpenHelper{
    
	
	
	

	public MySqliteDbHelper(Context context) {
		super(context, constant.SQLITE_DB_NAME, null, constant.SQLITE_DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL(constant.CREATE_TABLE_TYPE_SQL);
		arg0.execSQL(constant.CREATE_TABLE_VIDEOPARENT_SQL);
		arg0.execSQL(constant.CREATE_TABLE_HISTORY_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public void insertToTableType(ArrayList<VideoType> VideoTypeList){
		SQLiteDatabase db =getWritableDatabase();	
		db.delete(constant.TABLE_TYPE, null, null);
		for(int i=0;i<VideoTypeList.size();i++){	
			ContentValues cv =new ContentValues();
			cv.put(constant.TABLE_TYPE_COLUMN, VideoTypeList.get(i).GetTypeName());
			cv.put(constant.TABLE_REFRESHTIME_COLUMN, VideoTypeList.get(i).GetRefreshTime());
			db.insert(constant.TABLE_TYPE, null, cv);
				    
		}
		db.close();
			
	}
	
	public void insertToTableType(VideoType videoType)
	{
		SQLiteDatabase db =getWritableDatabase();	
		
		db.delete(constant.TABLE_TYPE, constant.TABLE_TYPE_COLUMN+"=?", new String[]{videoType.GetTypeName()});
		ContentValues cv =new ContentValues();
		cv.put(constant.TABLE_TYPE_COLUMN, videoType.GetTypeName());
		cv.put(constant.TABLE_REFRESHTIME_COLUMN, videoType.GetRefreshTime());
		db.insert(constant.TABLE_TYPE, null, cv);
		db.close();
	}
	public ArrayList<VideoType> queryVideoType()
	{
		ArrayList<VideoType> VideoTypeList=new ArrayList<VideoType>();
		SQLiteDatabase db =getWritableDatabase();
		Cursor cs=db.query(constant.TABLE_TYPE, null, null, null, null, null, null);
		int TypeColumn = cs.getColumnIndex(constant.TABLE_TYPE_COLUMN);
		int timeColumn = cs.getColumnIndex(constant.TABLE_REFRESHTIME_COLUMN);
		for(cs.moveToFirst();!cs.isAfterLast();cs.moveToNext())
		{
			VideoType mVideoType = new VideoType();
			mVideoType.SetTypeName(cs.getString(TypeColumn));
			mVideoType.SetRefreshTime(cs.getLong(timeColumn));
			VideoTypeList.add(mVideoType);
		}
		cs.close();
		db.close();
		return VideoTypeList;
	}
	
	public void insertToVideoParent(ArrayList<VideoParent> VideoParentList,String Type)
	{
		SQLiteDatabase db =getWritableDatabase();	
		db.delete(constant.TABLE_VIDEOPARENT, constant.TABLE_TYPE_COLUMN+"=?", new String[]{Type});
		for(int i= 0 ;i < VideoParentList.size(); i++)
		{
			VideoParent parent=VideoParentList.get(i);
			ContentValues cv = new ContentValues();
			cv.put(constant.TABLE_TYPE_COLUMN, parent.GetType());
			cv.put(constant.TABLE_VIDEOPARENT_NAME_COLUMN, parent.GetName());
			cv.put(constant.TABLE_VIDEOPARENT_SCRIPTIMAGE_URL, parent.GetScriptPicUrl());
			db.insert(constant.TABLE_VIDEOPARENT, null, cv);
		}
		db.close();
		
	}
	
	public ArrayList<VideoParent> queryVideoParent(String Type)
	{
		
		ArrayList<VideoParent> VideoParentList = new ArrayList<VideoParent>();
		SQLiteDatabase db =getWritableDatabase();
		Cursor cs=db.query(constant.TABLE_VIDEOPARENT, null, constant.TABLE_TYPE_COLUMN+"=?", new String[]{Type}, null, null, null);
		int NameColumn = cs.getColumnIndex(constant.TABLE_VIDEOPARENT_NAME_COLUMN);
		int ScriptPicUrlColumn=cs.getColumnIndex(constant.TABLE_VIDEOPARENT_SCRIPTIMAGE_URL);
		int TypeColumn = cs.getColumnIndex(constant.TABLE_TYPE_COLUMN);
		for(cs.moveToFirst();!cs.isAfterLast();cs.moveToNext())
		{
			VideoParent parent = new VideoParent();
			parent.SetName(cs.getString(NameColumn));
			parent.SetScriptPicUrl(cs.getString(ScriptPicUrlColumn));
			parent.SetType(cs.getString(TypeColumn));
			VideoParentList.add(parent);
		}
		cs.close();
		db.close();
		return VideoParentList;
	}
	
	public void insertToVideoHistory(VideoHistory Videohistory)
	{
		SQLiteDatabase db =getWritableDatabase();
		Log.e("Videohistory db",Videohistory.GetLastPos()+" "+Videohistory.GetLastTime()
				  );
		//long DeleteTime=System.currentTimeMillis()-604800000;
		//db.delete(constant.TABLE_HISTORY, constant.TABLE_LAST_PLAY_TIME_COLUMN+"<?", new String[]{DeleteTime+""});
		//db.delete(constant.TABLE_HISTORY, constant.TABLE_VIDEO_URL_COLUMN+"=?", new String[]{Videohistory.GetVideoUrl()});
		ContentValues cv = new ContentValues();
		cv.put(constant.TABLE_LAST_PLAY_POS_COLUMN,Videohistory.GetLastPos());
		cv.put(constant.TABLE_LAST_PLAY_TIME_COLUMN,Videohistory.GetLastTime());
		cv.put(constant.TABLE_VIDEO_NAME_COLUMN, Videohistory.GetVideoName());
		cv.put(constant.TABLE_VIDEO_SCRIPTIMAGE_URL, Videohistory.GetVideoScriptPicUrl());
		cv.put(constant.TABLE_VIDEO_URL_COLUMN, Videohistory.GetVideoUrl());
		db.insert(constant.TABLE_HISTORY, null, cv);
		db.close();
	}
	
	public ArrayList<VideoHistory> queryVideoHistory()
	{
		ArrayList<VideoHistory> VideoHistoryList = new ArrayList<VideoHistory>();
		SQLiteDatabase db =getWritableDatabase();
		Cursor cs=db.query(constant.TABLE_HISTORY, null, null, null, null, null, null);
		for(cs.moveToFirst();!cs.isAfterLast();cs.moveToNext())
		{
			VideoHistory history = new VideoHistory();
			history.SetVideoName(cs.getString(cs.getColumnIndex(constant.TABLE_VIDEO_NAME_COLUMN)));
			history.SetVideoScriptPicUrl(cs.getString(cs.getColumnIndex(constant.TABLE_VIDEO_SCRIPTIMAGE_URL)));
			history.SetVideoUrl(cs.getString(cs.getColumnIndex(constant.TABLE_VIDEO_URL_COLUMN)));
			history.SetLastPos(cs.getLong(cs.getColumnIndex(constant.TABLE_LAST_PLAY_POS_COLUMN)));
			history.SetLastTime(cs.getLong(cs.getColumnIndex(constant.TABLE_LAST_PLAY_TIME_COLUMN)));
			VideoHistoryList.add(history);
		}
		cs.close();
		db.close();
		return VideoHistoryList;
	}
	
	
}