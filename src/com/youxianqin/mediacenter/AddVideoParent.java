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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.youxianqin.mediacenter.adapter.AddParentListViewAdapter;
import com.youxianqin.mediacenter.bean.NetWorkStringUpLoad;
import com.youxianqin.mediacenter.bean.VideoType;
import com.youxianqin.mediacenter.bean.constant;


public class AddVideoParent extends Activity {
    private ListView AddParentTypeList;
    private EditText AddParentEdit;
    private Button AddParentBtn;
    private HashMap<String,ArrayList<String>> InPutString;
    private VideoType SelectType;
    private NetWorkStringUpLoad parentNetWorkStringUpLoad;
    private AddParentListViewAdapter mAddParentListViewAdapter;
    private MediaCenterApp mMediaCenterApp;
	void initView()
	{
		AddParentTypeList = (ListView) findViewById(R.id.AddParentTypeList);
		AddParentEdit = (EditText) findViewById(R.id.AddParentEdit);
		AddParentBtn = (Button) findViewById(R.id.AddParentBtn);
		AddParentBtn.setOnClickListener(new AddParentBtnOnClickListener());
		mMediaCenterApp = (MediaCenterApp)this.getApplication();
		mAddParentListViewAdapter = new AddParentListViewAdapter(mMediaCenterApp.Titles,this.getLayoutInflater());
		AddParentTypeList.setAdapter(mAddParentListViewAdapter);
		AddParentTypeList.setOnItemClickListener(new AddParentTypeListOnItemClickListener());
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_parent_activity);
		initView();
	}
	
	private class AddParentBtnOnClickListener implements OnClickListener{


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String InputText = AddParentEdit.getText().toString();
			System.out.println(InputText);
			if(InputText.length()==0){
				Toast.makeText(AddVideoParent.this, constant.INPUT_NULL_STRING_WARRING, Toast.LENGTH_SHORT).show();
			}
			if(SelectType==null){
				Toast.makeText(AddVideoParent.this, constant.INPUT_NULL_STRING_WARRING, Toast.LENGTH_SHORT).show();
			}
			else
			{
				InPutString = new HashMap<String,ArrayList<String>>();
			    ArrayList<String> InPutTypeName = new ArrayList<String>();
				ArrayList<String> InPutParentName = new ArrayList<String>();
				InPutTypeName.add(SelectType.GetTypeName());
				InPutParentName.add(InputText);
				InPutString.put("video_type", InPutTypeName);
				InPutString.put("video_parent", InPutParentName);
				parentNetWorkStringUpLoad=new NetWorkStringUpLoad(InPutString);
				parentNetWorkStringUpLoad.execute("addvideoparent/");
			}
		}
		
	}
	
	private class AddParentTypeListOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			mAddParentListViewAdapter.SetItemSelect(arg2);
			SelectType = mMediaCenterApp.Titles.get(arg2);
			mAddParentListViewAdapter.notifyDataSetInvalidated();
		}
		
	}
	
}