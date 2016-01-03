package com.sunnyit.enforcement.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.model.Inspector;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenInspterAddActivity extends BaseActivity {
	
	private TopBarView topbar_inspter_add;
	
	private EditText et_ck_inspter_name1;
	private EditText et_ck_inspter_position1;
	private EditText et_ck_inspter_name2;
	private EditText et_ck_inspter_position2;
	private EditText et_ck_inspter_name3;
	private EditText et_ck_inspter_position3;
	private EditText et_ck_inspter_name4;
	private EditText et_ck_inspter_position4;
	private EditText et_ck_inspter_name5;
	private EditText et_ck_inspter_position5;
	private EditText et_ck_inspter_name6;
	private EditText et_ck_inspter_position6;
	
	private TextView tv_ck_inspter_position1;
	private TextView tv_ck_inspter_position2;
	private TextView tv_ck_inspter_position3;
	private TextView tv_ck_inspter_position4;
	private TextView tv_ck_inspter_position5;
	private TextView tv_ck_inspter_position6;
	
	private String insptertype;
	private String insptertitle;
	private ArrayList<Inspector> mls_Inspector; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_inspter_add);
		
		initComponent();
		
		Intent intent=getIntent();
		insptertype=intent.getStringExtra("insptertype");
		insptertitle=intent.getStringExtra("insptertitle");
		mls_Inspector=(ArrayList<Inspector>)intent.getSerializableExtra("infoList");  
		
		switch (mls_Inspector.size()) {
		case 6:
			et_ck_inspter_name6.setText(mls_Inspector.get(5).getInspectorname());;
			et_ck_inspter_position6.setText(mls_Inspector.get(5).getInspectorduty());
		case 5:
			et_ck_inspter_name5.setText(mls_Inspector.get(4).getInspectorname());;
			et_ck_inspter_position5.setText(mls_Inspector.get(4).getInspectorduty());
		case 4:
			et_ck_inspter_name4.setText(mls_Inspector.get(3).getInspectorname());;
			et_ck_inspter_position4.setText(mls_Inspector.get(3).getInspectorduty());
		case 3:
			et_ck_inspter_name3.setText(mls_Inspector.get(2).getInspectorname());;
			et_ck_inspter_position3.setText(mls_Inspector.get(2).getInspectorduty());
		case 2:
			et_ck_inspter_name2.setText(mls_Inspector.get(1).getInspectorname());;
			et_ck_inspter_position2.setText(mls_Inspector.get(1).getInspectorduty());
		case 1:
			et_ck_inspter_name1.setText(mls_Inspector.get(0).getInspectorname());;
			et_ck_inspter_position1.setText(mls_Inspector.get(0).getInspectorduty());

		default:
			break;
		}
		
		if(insptertype.equals("专家"))
		{
			et_ck_inspter_position1.setHint("请输入职称信息");
			et_ck_inspter_position2.setHint("请输入职称信息");
			et_ck_inspter_position3.setHint("请输入职称信息");
			et_ck_inspter_position4.setHint("请输入职称信息");
			et_ck_inspter_position5.setHint("请输入职称信息");
			et_ck_inspter_position6.setHint("请输入职称信息");
			
			tv_ck_inspter_position1.setText("职称");
			tv_ck_inspter_position2.setText("职称");
			tv_ck_inspter_position3.setText("职称");
			tv_ck_inspter_position4.setText("职称");
			tv_ck_inspter_position5.setText("职称");
			tv_ck_inspter_position6.setText("职称");
		}
		
		topbar_inspter_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				List<Inspector> ls_Inspector=new ArrayList<Inspector>();
				if(!et_ck_inspter_name1.getText().toString().trim().equals(""))
				{
					Inspector in=new Inspector();
					in.setCheckid(UUID.randomUUID().toString());
					in.setInspectorname(et_ck_inspter_name1.getText().toString().trim());
					in.setInspectorduty(et_ck_inspter_position1.getText().toString().trim());
					in.setInspectortype(insptertype);
					ls_Inspector.add(in);
				}
				if(!et_ck_inspter_name2.getText().toString().trim().equals(""))
				{
					Inspector in=new Inspector();
					in.setCheckid(UUID.randomUUID().toString());
					in.setInspectorname(et_ck_inspter_name2.getText().toString().trim());
					in.setInspectorduty(et_ck_inspter_position2.getText().toString().trim());
					in.setInspectortype(insptertype);
					ls_Inspector.add(in);
				}
				if(!et_ck_inspter_name3.getText().toString().trim().equals(""))
				{
					Inspector in=new Inspector();
					in.setCheckid(UUID.randomUUID().toString());
					in.setInspectorname(et_ck_inspter_name3.getText().toString().trim());
					in.setInspectorduty(et_ck_inspter_position3.getText().toString().trim());
					in.setInspectortype(insptertype);
					ls_Inspector.add(in);
				}
				if(!et_ck_inspter_name4.getText().toString().trim().equals(""))
				{
					Inspector in=new Inspector();
					in.setCheckid(UUID.randomUUID().toString());
					in.setInspectorname(et_ck_inspter_name4.getText().toString().trim());
					in.setInspectorduty(et_ck_inspter_position4.getText().toString().trim());
					in.setInspectortype(insptertype);
					ls_Inspector.add(in);
				}
				if(!et_ck_inspter_name5.getText().toString().trim().equals(""))
				{
					Inspector in=new Inspector();
					in.setCheckid(UUID.randomUUID().toString());
					in.setInspectorname(et_ck_inspter_name5.getText().toString().trim());
					in.setInspectorduty(et_ck_inspter_position5.getText().toString().trim());
					in.setInspectortype(insptertype);
					ls_Inspector.add(in);
				}
				if(!et_ck_inspter_name6.getText().toString().trim().equals(""))
				{
					Inspector in=new Inspector();
					in.setCheckid(UUID.randomUUID().toString());
					in.setInspectorname(et_ck_inspter_name6.getText().toString().trim());
					in.setInspectorduty(et_ck_inspter_position6.getText().toString().trim());
					in.setInspectortype(insptertype);
					ls_Inspector.add(in);
				}
				
				Intent intent = new Intent();
				intent.putExtra("ls_Inspector", (Serializable)ls_Inspector);
				setResult(1, intent);
				finish();

			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		topbar_inspter_add.setTitle(insptertitle);
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_inspter_add=(TopBarView) findViewById(R.id.topbar_inspter_add);
		
		et_ck_inspter_name1=(EditText) findViewById(R.id.et_ck_inspter_name1);
		et_ck_inspter_name2=(EditText) findViewById(R.id.et_ck_inspter_name2);
		et_ck_inspter_name3=(EditText) findViewById(R.id.et_ck_inspter_name3);
		et_ck_inspter_name4=(EditText) findViewById(R.id.et_ck_inspter_name4);
		et_ck_inspter_name5=(EditText) findViewById(R.id.et_ck_inspter_name5);
		et_ck_inspter_name6=(EditText) findViewById(R.id.et_ck_inspter_name6);
		et_ck_inspter_position1=(EditText) findViewById(R.id.et_ck_inspter_position1);
		et_ck_inspter_position2=(EditText) findViewById(R.id.et_ck_inspter_position2);
		et_ck_inspter_position3=(EditText) findViewById(R.id.et_ck_inspter_position3);
		et_ck_inspter_position4=(EditText) findViewById(R.id.et_ck_inspter_position4);
		et_ck_inspter_position5=(EditText) findViewById(R.id.et_ck_inspter_position5);
		et_ck_inspter_position6=(EditText) findViewById(R.id.et_ck_inspter_position6);
		
		tv_ck_inspter_position1=(TextView) findViewById(R.id.tv_ck_inspter_position1);
		tv_ck_inspter_position2=(TextView) findViewById(R.id.tv_ck_inspter_position2);
		tv_ck_inspter_position3=(TextView) findViewById(R.id.tv_ck_inspter_position3);
		tv_ck_inspter_position4=(TextView) findViewById(R.id.tv_ck_inspter_position4);
		tv_ck_inspter_position5=(TextView) findViewById(R.id.tv_ck_inspter_position5);
		tv_ck_inspter_position6=(TextView) findViewById(R.id.tv_ck_inspter_position6);
	}
	
	
}





