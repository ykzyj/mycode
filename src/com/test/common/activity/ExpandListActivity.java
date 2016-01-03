package com.sunnyit.common.activity;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemClickListener;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemLongClickListener;
import com.sunnyit.common.treeview.TreeNode;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.law.action.StandShowActivity;
import com.sunnyit.synchronous.model.CountyArea;
import com.sunnyit.synchronous.model.CountyDepartment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;
import test.data.TreeViewInitAdapter;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class ExpandListActivity extends BaseActivity  {
	
	private TopBarView topbar_common_exlist_choice;
	
	private int choiceflag;
	
	private ListView exlist_common_choice;
	private List<CountyArea> ls_CountyArea;
	private List<CountyDepartment> ls_CountyDepartment;
	
	private TreeViewInitAdapter<CountyArea> mCountyAreaAdapter;
	private TreeViewInitAdapter<CountyDepartment> mCountyDepartmentAdapter;
	
	private List<Integer> iconList;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_expandlist_choice);
		
		Intent intent=getIntent();
		choiceflag=intent.getIntExtra("choiceflag", -1);
		
		initView();
		
		topbar_common_exlist_choice.setTopBarClick(new TopBarView.ITopBarClick() {
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		if(choiceflag==0)
		{
			topbar_common_exlist_choice.setTitle("区域选择");
		}
		else if(choiceflag==1)
		{
			topbar_common_exlist_choice.setTitle("部门选择");
		}
		
		final CustomDialog cusdialog=new CustomDialog(ExpandListActivity.this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		cusdialog.setOutCancel(false);
		cusdialog.show();
		
		new Thread( new Runnable() {
			public void run() {
				
				iconList=new ArrayList<Integer>();
				iconList.add(R.drawable.reduce_recycle_blue44);
				iconList.add(R.drawable.add_recycle_blue44);
				iconList.add(R.drawable.often_question);
				
				if(choiceflag==0)
				{
					initAeraExpandListData();
					Message msg=Message.obtain();
					msg.what=0;
					mHandler.sendMessage(msg);
				}
				else if(choiceflag==1)
				{
					initDepExpandListData();
					Message msg=Message.obtain();
					msg.what=1;
					mHandler.sendMessage(msg);
				}
				cusdialog.dismiss();
			}
		}).start();;
		
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==0)
			{
				exlist_common_choice.setAdapter(mCountyAreaAdapter);
				initAeraExpandShow();
			}
			else if(msg.what==1)
			{
				exlist_common_choice.setAdapter(mCountyDepartmentAdapter);
				initDepExpandListshow();
			}
		};
	};

	private void initAeraExpandListData() {
		// TODO Auto-generated method stub
		SqlOperate<CountyArea> opetaterIn=new SqlOperate<CountyArea>(this, CountyArea.class);
		ls_CountyArea=opetaterIn.SelectEntitys();
		opetaterIn.close();
		
		try {
			mCountyAreaAdapter=
					new TreeViewInitAdapter<CountyArea>(ExpandListActivity.this, 
							ls_CountyArea, exlist_common_choice, R.layout.tree_item,false,1,null);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initAeraExpandShow() {
		mCountyAreaAdapter.setOnTreeItenClickListener(new TreeItemClickListener() {

			@Override
			public void onItemClick(TreeNode node, int position) {
				// TODO Auto-generated method stub
				if(node.isLeaf())
				{
					//Toast.makeText(ExpandListActivity.this, node.getName(), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
	                intent.putExtra("localname", node.getName());
	                ExpandListActivity.this.setResult(1, intent);
	                ExpandListActivity.this.finish();
				}
			}
		});
		
		mCountyAreaAdapter.setOnTreeItenLongClickListener(new TreeItemLongClickListener() {

			@Override
			public void onItemLongClick(TreeNode node, int position) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
                intent.putExtra("localname", node.getName());
                ExpandListActivity.this.setResult(1, intent);
                ExpandListActivity.this.finish();
			}
			
		});
	}

	private void initDepExpandListData() {
		SqlOperate<CountyDepartment> opetaterdep=new SqlOperate<CountyDepartment>(this, CountyDepartment.class);
		ls_CountyDepartment=opetaterdep.SelectEntitysByCondition(" where d_parentdepartmentid!='-2'");
		opetaterdep.close();
		
		try {
			mCountyDepartmentAdapter=
					new TreeViewInitAdapter<CountyDepartment>(ExpandListActivity.this, 
							ls_CountyDepartment, exlist_common_choice, R.layout.tree_item,false,1,null);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void initDepExpandListshow() {
		mCountyDepartmentAdapter.setOnTreeItenClickListener(new TreeItemClickListener() {

			@Override
			public void onItemClick(TreeNode node, int position) {
				// TODO Auto-generated method stub
				if(node.isLeaf())
				{
					Intent intent = new Intent();
	                intent.putExtra("depname", node.getName());
	                ExpandListActivity.this.setResult(2, intent);
	                ExpandListActivity.this.finish();
				}
			}
		});
		mCountyDepartmentAdapter.setOnTreeItenLongClickListener(new TreeItemLongClickListener() {

			@Override
			public void onItemLongClick(TreeNode node, int position) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
                intent.putExtra("depname", node.getName());
                ExpandListActivity.this.setResult(2, intent);
                ExpandListActivity.this.finish();
			}
			
		});
	}

	private void initView() {
		topbar_common_exlist_choice = (TopBarView)findViewById(R.id.topbar_common_exlist_choice);
		exlist_common_choice=(ListView) findViewById(R.id.exlist_common_choice);
	}
	
}


