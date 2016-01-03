package com.sunnyit.law.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.dialog.CustomDialog.CancelDialogListener;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemClickListener;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemLongClickListener;
import com.sunnyit.common.treeview.TreeNode;
import com.sunnyit.common.view.TabDialogView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.model.CheckTables;
import com.sunnyit.law.model.ExcelCell;
import com.sunnyit.law.model.HiddenStandardFile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import test.data.TreeViewInitAdapter;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class StandShowActivity extends BaseActivity {
	
	private TopBarView topbar_standcheck_add;
	
	private ListView mListView;
	private List<ExcelCell> mdatas;
	private TreeViewInitAdapter<ExcelCell> mAdapter;
	private HiddenStandardFile mHiddenStandardFile;
	
	private String searchInfo;
	private String c_rootid;
	private String searchtitle;
	private int showLevel;
	
	private HashSet<String> set_parentID= new HashSet<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_standcheck_add);
		
		mdatas=new ArrayList<ExcelCell>();
		mHiddenStandardFile = (HiddenStandardFile) getIntent().getSerializableExtra("HiddenStandardFile"); 
		
		initComponent();
		
		topbar_standcheck_add.setRightButVisibility(View.GONE);
		topbar_standcheck_add.setTopBarClick(new ITopBarClick() {
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
		
		final CustomDialog cusdialog=new CustomDialog(StandShowActivity.this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		cusdialog.setOutCancel(false);
		cusdialog.show();
		
		new Thread(new Runnable() {
			public void run() {
				if(mHiddenStandardFile!=null)
				{
					showLevel=1;
					topbar_standcheck_add.setTitle(mHiddenStandardFile.getHf_name());
					initNormalData();
				}
				else
				{
					showLevel=5;
		       		searchInfo = getIntent().getStringExtra("searchInfo");
		       		c_rootid = getIntent().getStringExtra("c_rootid");
		       		searchtitle = getIntent().getStringExtra("searchtitle");
		       		topbar_standcheck_add.setTitle(searchtitle);
					initSearchlDate();
				}
				mHandler.sendEmptyMessage(0);
				cusdialog.dismiss();
			}
		}).start();
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg) {
			try 
			{
				mAdapter=new TreeViewInitAdapter<ExcelCell>(StandShowActivity.this, mdatas, mListView, R.layout.tree_item,false,showLevel,null);
				mListView.setAdapter(mAdapter);
				initNormalShow();
				if(mdatas.size()==0)
				{
					Toast.makeText(StandShowActivity.this, "查询结果为空", Toast.LENGTH_SHORT).show();
				}
			} 
			catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	private void initSearchlDate() {
		// TODO Auto-generated method stub
		
		String targetMinExcel=" select * from ExcelCell where c_content like '%"+searchInfo+"%' "
										+"  and c_rootid ='"+c_rootid+"' and c_title in ("
										+ "  select min(c_title) from ExcelCell where c_content "
										+ " like '%"+searchInfo+"%' and c_rootid ='"+c_rootid+"' "
										+ ")";
		
		List<ExcelCell> ls_minExcel=new ArrayList<ExcelCell>();
		SqlOperate<ExcelCell> operaterMaxCell=new SqlOperate<ExcelCell>(StandShowActivity.this, ExcelCell.class);
		ls_minExcel=operaterMaxCell.SelectEntitysBySqlCondition(targetMinExcel);
		operaterMaxCell.close();
		
		if(ls_minExcel!=null&&ls_minExcel.size()>0)
		{
			for(ExcelCell cell:ls_minExcel)
			{
				mdatas.add(cell);
				addParentCell(cell);
				addChildrenCell(cell);
			}
		}
										
		
	}
	
	
	private void addChildrenCell(ExcelCell excelCell) {
		// TODO Auto-generated method stub
		String childExcel=" select * from ExcelCell where c_parentId='"+excelCell.getC_id()+"' and c_title<>6  ";
		List<ExcelCell> childExcelCell=new ArrayList<ExcelCell>();
		SqlOperate<ExcelCell> operaterMaxCell=new SqlOperate<ExcelCell>(StandShowActivity.this, ExcelCell.class);
		childExcelCell=operaterMaxCell.SelectEntitysBySqlCondition(childExcel);
		operaterMaxCell.close();
		for(ExcelCell cell:childExcelCell)
		{
			mdatas.add(cell);
			addChildrenCell(cell);
		}
	}

	private void addParentCell(ExcelCell excelCell) {
		// TODO Auto-generated method stub
		if(!havedAdd(excelCell))
		{
			String parentExcel=" select * from ExcelCell where c_id='"+excelCell.getC_parentId()+"' ";
			List<ExcelCell> parentExcelCell=new ArrayList<ExcelCell>();
			SqlOperate<ExcelCell> operaterMaxCell=new SqlOperate<ExcelCell>(StandShowActivity.this, ExcelCell.class);
			parentExcelCell=operaterMaxCell.SelectEntitysBySqlCondition(parentExcel);
			operaterMaxCell.close();
			for(ExcelCell cell:parentExcelCell)
			{
				mdatas.add(cell);
				addParentCell(cell);
			}
		}
	}
	
	private boolean havedAdd(ExcelCell excelCell) {
		int sp_front=set_parentID.size();
		set_parentID.add(excelCell.getC_parentId());
		int sp_post=set_parentID.size();
		if(sp_front==sp_post)
		{
			return true;
		}
		return false;
	}

	private void initNormalShow() {
		try {
			
			mAdapter.setOnTreeItenClickListener(new TreeItemClickListener() {
				@Override
				public void onItemClick(TreeNode node, int position) {
					// TODO Auto-generated method stub
					if(node.isLeaf())
					{
						CheckTables checkTables=new CheckTables();
						getLevelContent(checkTables,node.getId());
						
						final CustomDialog cusdialog=new CustomDialog(StandShowActivity.this);
						TabDialogView tabDialogView=new TabDialogView(StandShowActivity.this);
						tabDialogView.addContent("依据说明", checkTables.getH_basis());
						tabDialogView.addContent("罚则", checkTables.getH_punishment());
						tabDialogView.addBut("取消").setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								cusdialog.dismiss();
							}
						});
						cusdialog.setView(tabDialogView,0);
						cusdialog.show();
						
						/*if(ls_excelCell!=null&&ls_excelCell.size()>0)
						{
							AlertDialog.Builder builder=new AlertDialog.Builder(StandShowActivity.this);  //先得到构造器  
					        builder.setTitle("依据说明"); //设置标题  
					        builder.setMessage(ls_excelCell.get(0).getC_content()); //设置内容  
					        builder.setIcon(R.drawable.note_stand48);//设置图标，图片id即可  
					        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
					            @Override  
					            public void onClick(DialogInterface dialog, int which) {  
					                dialog.dismiss();  
					            }  
					        });  
					        //参数都设置完成了，创建并显示出来  
					        builder.create().show(); 
						}*/
					}
				}
			});
			
			mAdapter.setOnTreeItenLongClickListener(new TreeItemLongClickListener() {

				@Override
				public void onItemLongClick(TreeNode node, final int position) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder=new AlertDialog.Builder(StandShowActivity.this);  //先得到构造器  
			        builder.setTitle("提示"); //设置标题  
			        builder.setMessage("确定要删除当前节点吗?"); //设置内容  
			        builder.setIcon(R.drawable.note_stand48);//设置图标，图片id即可  
			        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			            	mAdapter.deleteTreeNode(position);
			                dialog.dismiss(); //关闭dialog  
			            }  
			        });  
			        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
			            @Override  
			            public void onClick(DialogInterface dialog, int which) {  
			                dialog.dismiss();  
			            }  
			        });  
			        //参数都设置完成了，创建并显示出来  
			        builder.create().show(); 
				}
			});
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void getLevelContent(CheckTables checkTables,String parentID) {
		String conStr=" where c_parentId='"+parentID+"' ";
		SqlOperate<ExcelCell> operaterExcelCell=new SqlOperate<ExcelCell>(StandShowActivity.this, ExcelCell.class);
		List<ExcelCell> ls_excelCell=operaterExcelCell.SelectEntitysByCondition(conStr);
		operaterExcelCell.close();
		if(ls_excelCell!=null&&ls_excelCell.size()>0)
		{
			if(ls_excelCell.get(0).getC_title().equals("6"))
			{
				checkTables.setH_basis(ls_excelCell.get(0).getC_content());
				getLevelContent(checkTables,ls_excelCell.get(0).getC_id());
			}
			else if(ls_excelCell.get(0).getC_title().equals("7"))
			{
				checkTables.setH_dangerLevel(ls_excelCell.get(0).getC_content());
				getLevelContent(checkTables,ls_excelCell.get(0).getC_id());
			}
			else if(ls_excelCell.get(0).getC_title().equals("8"))
			{
				checkTables.setH_checkCircle(ls_excelCell.get(0).getC_content());
				getLevelContent(checkTables,ls_excelCell.get(0).getC_id());
			}
			else if(ls_excelCell.get(0).getC_title().equals("9"))
			{
				checkTables.setH_checkResult(ls_excelCell.get(0).getC_content());
				getLevelContent(checkTables,ls_excelCell.get(0).getC_id());
			}
			else if(ls_excelCell.get(0).getC_title().equals("10"))
			{
				checkTables.setH_punishment(ls_excelCell.get(0).getC_content());
			}
		}
	}
	
	private void initNormalData() {
		
		String conditionStr=" where c_rootId='"+mHiddenStandardFile.getHf_id()+"' and c_title<>6 and c_title<>7 and c_title<>8 and c_title<>9 and c_title<>10 ";
		
		SqlOperate<ExcelCell> operaterExcelCell=new SqlOperate<ExcelCell>(StandShowActivity.this, ExcelCell.class);
		mdatas=operaterExcelCell.SelectEntitysByCondition(conditionStr);
		operaterExcelCell.close();
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_standcheck_add=(TopBarView) findViewById(R.id.topbar_standcheck_add);
		mListView=(ListView) findViewById(R.id.list_standcheck_add);
	}
	
}


