package com.youxianqin.mediacenter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.youxianqin.mediacenter.bean.NetWorkStringUpLoad;
import com.youxianqin.mediacenter.bean.constant;



public class AddVideoType extends Activity {
    private EditText AddTypeEdit;
    private Button AddTypeBtn;
	private HashMap<String,ArrayList<String>> InPutString;
	private NetWorkStringUpLoad typeNetWorkStringUpLoad;
	
	void initView(){
		AddTypeEdit = (EditText) findViewById(R.id.AddTypeEdit);
		AddTypeBtn = (Button)findViewById(R.id.AddTypeBtn);
		
		AddTypeBtn.setOnClickListener(new AddTypeBtnOnClickListener());
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_type_activity);
		initView();
	}
	
	
	private class AddTypeBtnOnClickListener implements OnClickListener{


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String InputText = AddTypeEdit.getText().toString();
			System.out.println(InputText);
			if(InputText.length()==0){
				Toast.makeText(AddVideoType.this, constant.INPUT_NULL_STRING_WARRING, Toast.LENGTH_SHORT).show();
			}
			else
			{
				InPutString = new HashMap<String,ArrayList<String>>();
				ArrayList<String> InPutTypeName = new ArrayList<String>();
				InPutTypeName.add(InputText);
				InPutString.put("video_type", InPutTypeName);
				typeNetWorkStringUpLoad=new NetWorkStringUpLoad(InPutString);
				typeNetWorkStringUpLoad.execute("addvideotype/");
			}
		}
		
	}
	
}