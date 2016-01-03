package com.sunnyit.enforcement.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TabDialogView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.data.Standard_CK_Table_ItemIngAdapter;
import com.sunnyit.enforcement.data.Standard_CK_Table_ItemIngAdapter.QualifiedListener;
import com.sunnyit.enforcement.data.Standard_CK_Table_ItemShowAdapter;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenStandCheckShowDatilActivity extends BaseActivity {


	private TopBarView topbar_endatil_standcheck_add;
	private ListView lv_stand_table_item;
	
	private String Ck_id;
	
	private List<Standard_CK_Table_Item> mData;
	private Standard_CK_Table_ItemShowAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_endatil_standcheck_add);
		
		Ck_id = getIntent().getStringExtra("Ck_id");
		
		initComponent();
		
		initAdapter();
		
		
		topbar_endatil_standcheck_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {}
			
			@Override
			public void onClickLeftBut() {
				finish();
			}
		});
		topbar_endatil_standcheck_add.setRightButVisibility(View.GONE);
	}
	
	private void initAdapter() {
		String conStr=" where ck_id='"+Ck_id+"' ";
		SqlOperate<Standard_CK_Table_Item> operaterStandard_CK_Table_Item=new SqlOperate<Standard_CK_Table_Item>(HiddenStandCheckShowDatilActivity.this, Standard_CK_Table_Item.class);
		mData=operaterStandard_CK_Table_Item.SelectEntitysByCondition(conStr);
		operaterStandard_CK_Table_Item.close();
		mAdapter=new Standard_CK_Table_ItemShowAdapter(this, mData, R.layout.hidden_enshow_standcheck_item);
		lv_stand_table_item.setAdapter(mAdapter);
		lv_stand_table_item.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				/*AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckShowDatilActivity.this);  //先得到构造器  
		        builder.setTitle("依据说明"); //设置标题  
		        builder.setMessage(mData.get(arg2).getItem_basis()); //设置内容  
		        builder.setIcon(R.drawable.note_stand48);//设置图标，图片id即可  
		        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
		            @Override  
		            public void onClick(DialogInterface dialog, int which) {  
		                dialog.dismiss();  
		            }  
		        });  
		        //参数都设置完成了，创建并显示出来  
		        builder.create().show();*/
				Standard_CK_Table_Item ct=mData.get(arg2);
				final CustomDialog cusdialog=new CustomDialog(HiddenStandCheckShowDatilActivity.this);
				TabDialogView tabDialogView=new TabDialogView(HiddenStandCheckShowDatilActivity.this);
				tabDialogView.addContent("依据说明", ct.getItem_basis());
				tabDialogView.addContent("罚则", ct.getItem_punishment());
				tabDialogView.addBut("取消").setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						cusdialog.dismiss();
					}
				});
				cusdialog.setView(tabDialogView,0);
				cusdialog.show();
			}
		});
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_endatil_standcheck_add=(TopBarView) findViewById(R.id.topbar_endatil_standcheck_add);
		lv_stand_table_item=(ListView) findViewById(R.id.lv_stand_table_item);
	}
}


