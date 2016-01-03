package com.sunnyit.enforcement.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemClickListener;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemLongClickListener;
import com.sunnyit.common.treeview.TreeNode;
import com.sunnyit.common.view.TabDialogView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.model.CheckTables;
import com.sunnyit.law.action.StandShowActivity;
import com.sunnyit.law.model.ExcelCell;
import com.sunnyit.law.model.HiddenStandardFile;
import com.sunnyit.system.model.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
public class EnforceStandChoiceActivity extends BaseActivity {
	
	private TopBarView topbar_standcheck_add;
	private LinearLayout lin_table_name;
	private EditText et_table_name;
	
	private ListView mListView;
	private List<ExcelCell> mdatas;
	private TreeViewInitAdapter<ExcelCell> mAdapter;
	private HiddenStandardFile mHiddenStandardFile;
	
	private int showLevel;
	private User user;
	
	Date currentTime = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String dateString = formatter.format(currentTime);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_standcheck_add);
		
		user=getCurrentUser();
		
		mdatas=new ArrayList<ExcelCell>();
		mHiddenStandardFile = (HiddenStandardFile) getIntent().getSerializableExtra("HiddenStandardFile"); 
		
		initComponent();
		
		topbar_standcheck_add.setTopBarClick(new ITopBarClick() {
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				final String tableName=et_table_name.getText().toString().trim();
				if("".equals(tableName))
				{
					Toast.makeText(EnforceStandChoiceActivity.this, "表名不能为空", Toast.LENGTH_SHORT).show();
				}
				else
				{
					final List<TreeNode> ltn=mAdapter.getCheckLeafNodes();
					if(ltn!=null&&ltn.size()>0)
					{
						final CustomDialog cusdialog=new CustomDialog(EnforceStandChoiceActivity.this);
						cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
						cusdialog.setOutCancel(false);
						cusdialog.show();
						
						new Thread(new Runnable() {
							public void run() {
								
								String tableID=UUID.randomUUID().toString();
								
								//List<CheckTables> lct=new ArrayList<CheckTables>();
								for(TreeNode tn:ltn)
								{
									String conStr=" where c_parentId='"+tn.getId()+"' ";
									SqlOperate<ExcelCell> operaterExcelCell=new SqlOperate<ExcelCell>(EnforceStandChoiceActivity.this, ExcelCell.class);
									List<ExcelCell> ls_excelCell=operaterExcelCell.SelectEntitysByCondition(conStr);
									operaterExcelCell.close();
									if(ls_excelCell!=null&&ls_excelCell.size()>0)
									{
										CheckTables ct=new CheckTables();
										
										ct.setSc_uuId(UUID.randomUUID().toString());
										ct.setSc_tableId(tableID);
										ct.setSc_tableName(tableName);
										ct.setHf_id(mHiddenStandardFile.getHf_id());
										
										List<TreeNode> parentsNodes=new ArrayList<TreeNode>();
										getParents(tn,parentsNodes);
										for(TreeNode tnP:parentsNodes)
										{
											switch (tnP.getDes()) {
											case "0":
												ct.setH_content_one(tnP.getName());
												break;
											case "1":
												ct.setH_content_two(tnP.getName());
												break;
											case "2":
												ct.setH_content_three(tnP.getName());
												break;
											case "3":
												ct.setH_content_four(tnP.getName());
												break;
											case "4":
												ct.setH_content_five(tnP.getName());
												break;
											default:
												break;
											}
										}
										
										ct.setH_description(tn.getName());
										ct.setH_basis(ls_excelCell.get(0).getC_content());
										
										getLevelContent(ct,ls_excelCell.get(0).getC_id());
										
										ct.setSc_userId(user.getUserId());
										ct.setSc_createTime(dateString);
										ct.setH_seq(ls_excelCell.get(0).getC_row());
										
										SqlOperate<CheckTables> operaterCheckTables=new SqlOperate<CheckTables>(EnforceStandChoiceActivity.this, CheckTables.class);
										operaterCheckTables.saveContent(ct);
										operaterCheckTables.close();
									}
								}
								
								Message msg=Message.obtain();
								msg.what=1;
								mHandler.sendMessage(msg);
								cusdialog.dismiss();
							}
						}).start();
					}
					else
					{
						Toast.makeText(EnforceStandChoiceActivity.this, "新建检查表内容不能为空", Toast.LENGTH_SHORT).show();
					}
					
				}
			}
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
                setResult(0, intent);
				finish();
			}
		});
		
		final CustomDialog cusdialog=new CustomDialog(EnforceStandChoiceActivity.this);
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
				Message msg=Message.obtain();
				msg.what=0;
				mHandler.sendMessage(msg);
				cusdialog.dismiss();
			}
		}).start();
	}
	
	private void getLevelContent(CheckTables checkTables,String parentID) {
		String conStr=" where c_parentId='"+parentID+"' ";
		SqlOperate<ExcelCell> operaterExcelCell=new SqlOperate<ExcelCell>(EnforceStandChoiceActivity.this, ExcelCell.class);
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
	
	private void getParents(TreeNode currentNode,List<TreeNode> parentsNodes) {
		if(currentNode.getParent()!=null)
		{
			parentsNodes.add(currentNode.getParent());
			getParents(currentNode.getParent(),parentsNodes);
		}
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==0)
			{
				try 
				{
					mAdapter=new TreeViewInitAdapter<ExcelCell>(EnforceStandChoiceActivity.this, mdatas, mListView, R.layout.tree_item,true,showLevel,null);
					mListView.setAdapter(mAdapter);
					initNormalShow();
					if(mdatas.size()==0)
					{
						Toast.makeText(EnforceStandChoiceActivity.this, "查询结果为空", Toast.LENGTH_SHORT).show();
					}
				} 
				catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(msg.what==1)
			{
				Toast.makeText(EnforceStandChoiceActivity.this, "检查表新建成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
                setResult(1, intent);
				finish();
			}
		};
	};

	private void initNormalShow() {
		try {
			
			mAdapter.setOnTreeItenClickListener(new TreeItemClickListener() {
				@Override
				public void onItemClick(TreeNode node, int position) {
					// TODO Auto-generated method stub
					if(node.isLeaf())
					{
						/*String conStr=" where c_parentId='"+node.getId()+"' ";
						SqlOperate<ExcelCell> operaterExcelCell=new SqlOperate<ExcelCell>(EnforceStandChoiceActivity.this, ExcelCell.class);
						List<ExcelCell> ls_excelCell=operaterExcelCell.SelectEntitysByCondition(conStr);
						operaterExcelCell.close();
						
						if(ls_excelCell!=null&&ls_excelCell.size()>0)
						{
							AlertDialog.Builder builder=new AlertDialog.Builder(EnforceStandChoiceActivity.this);  //先得到构造器  
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
						
						CheckTables checkTables=new CheckTables();
						getLevelContent(checkTables,node.getId());
						
						final CustomDialog cusdialog=new CustomDialog(EnforceStandChoiceActivity.this);
						TabDialogView tabDialogView=new TabDialogView(EnforceStandChoiceActivity.this);
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
					}
				}
			});
			
			mAdapter.setOnTreeItenLongClickListener(new TreeItemLongClickListener() {

				@Override
				public void onItemLongClick(TreeNode node, final int position) {
					
				}
			});
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initNormalData() {
		
		String conditionStr=" where c_rootId='"+mHiddenStandardFile.getHf_id()+"' and c_title<>6 and c_title<>7 and c_title<>8 and c_title<>9 and c_title<>10 ";
		
		SqlOperate<ExcelCell> operaterExcelCell=new SqlOperate<ExcelCell>(EnforceStandChoiceActivity.this, ExcelCell.class);
		mdatas=operaterExcelCell.SelectEntitysByCondition(conditionStr);
		operaterExcelCell.close();
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_standcheck_add=(TopBarView) findViewById(R.id.topbar_standcheck_add);
		mListView=(ListView) findViewById(R.id.list_standcheck_add);
		
		lin_table_name=(LinearLayout) findViewById(R.id.lin_table_name);
		et_table_name=(EditText) findViewById(R.id.et_table_name);
		lin_table_name.setVisibility(View.VISIBLE);
		
	}
	
}


