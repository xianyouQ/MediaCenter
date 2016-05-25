package com.youxianqin.mediacenter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.youxianqin.mediacenter.adapter.PopParentListViewAdapter;
import com.youxianqin.mediacenter.bean.NetWorkStringUpLoad;


public class ChangeVideoParent extends Activity {
	 private Button ChangParentBtn;
	    private ListView PopWindowParentList;
	    private PopParentListViewAdapter mPopWindowParentListAdapter;
	    private String SelectParent;
	    private MediaCenterApp mMediaCenterApp;
	    private HashMap<String,ArrayList<String>> SelectVideoList;
	    private NetWorkStringUpLoad mNetWorkStringUpLoad;
    void initView(){
    	mMediaCenterApp = (MediaCenterApp) this.getApplication();
    	ChangParentBtn = (Button) findViewById(R.id.ChangParentConfirmBtn);
    	PopWindowParentList = (ListView)findViewById(R.id.PopWindowParentList);
    	ChangParentBtn.setOnClickListener(new VideoSetBtnOnClickListener());
    	mPopWindowParentListAdapter = new PopParentListViewAdapter(mMediaCenterApp.VideoParentList,this.getLayoutInflater());
    	PopWindowParentList.setAdapter(mPopWindowParentListAdapter);
    	PopWindowParentList.setOnItemClickListener(new PopWindowParentListOnItemClickListener());
    	 Bundle VideoSelectListBD = this.getIntent().getBundleExtra("VideoList");
    	 SelectVideoList = (HashMap<String,ArrayList<String>>)VideoSelectListBD.getSerializable("VideoList");
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoparent_pop_activity);
		initView();
	}
    
	private class VideoSetBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//int ViewId = arg0.getId();
			switch(arg0.getId()){
			case R.id.ChangParentConfirmBtn:	
			ArrayList<String> SelectParentList= new ArrayList<String>();
			if(SelectParent==null)
			{
				System.out.println("No Find Parent");
				break;
			}
			System.out.println(SelectParent);
			SelectParentList.add(SelectParent);

			SelectVideoList.put("video_parent", SelectParentList);
			mNetWorkStringUpLoad = new NetWorkStringUpLoad(SelectVideoList);
			mNetWorkStringUpLoad.execute("setvideoparent/");

			break;
			
		}
		}
		
	}
	
	private class PopWindowParentListOnItemClickListener implements OnItemClickListener{


		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			TextView TypeOrParentStringTV = (TextView)arg1.findViewById(R.id.TypeOrParentStringTV);
			SelectParent = TypeOrParentStringTV.getText().toString();
			System.out.println(arg2+"has click");
			mPopWindowParentListAdapter.SetItemSelect(arg2);
			mPopWindowParentListAdapter.notifyDataSetInvalidated(); 
		}
		
	}
}