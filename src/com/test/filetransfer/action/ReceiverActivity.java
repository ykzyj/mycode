package com.sunnyit.filetransfer.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.record.PageBreakRecord.Break;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemLongClickListener;
import com.sunnyit.common.treeview.TreeNode;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.filetransfer.model.TransportFile;
import com.sunnyit.filetransfer.model.UserContacts;
import com.sunnyit.synchronous.model.CountyArea;
import com.sunnyit.synchronous.model.CountyDepartment;
import com.sunnyit.system.model.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import test.action.DialogShowActivity;
import test.data.TreeViewInitAdapter;
import test.model.FileBean;

/**   
* @Title: DailyCheckList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:20:13 
* @version V1.0   
*/
public class ReceiverActivity extends BaseActivity {
	
	private TopBarView topbar_Receiver_add;
	
	private LinearLayout lin_table_name;
	private ListView mListView;
	
	private String type[] = { "常用联系人" , "收发专员" ,"市安委会成员单位" , "区县安监局用户" , "区县用户" , "安监局直管企业" };
	private String areaType[] = {"区县安委会成员单位" , "乡镇街办" };
	
	private static List<FileBean> lf;
	
	private CustomDialog cusdialog;
	private TreeViewInitAdapter<FileBean> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_standcheck_add);
		
		initComponent();
		
		topbar_Receiver_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				List<TreeNode> checkedNodes=mAdapter.getCheckLeafNodes();
				
				List<TransportFile> ls_tf=new ArrayList<TransportFile>();
				
				StringBuffer sbShow=new StringBuffer();
				for(TreeNode node:checkedNodes)
				{
					TreeNode nodePar=node.getParent();
					sbShow.append(node.getName());
					sbShow.append("(");
					sbShow.append(nodePar.getName());
					sbShow.append(");");
					
					TransportFile tf=new TransportFile();
					tf.setReceiver(node.getName());
					tf.setReceiverId(node.getId());
					ls_tf.add(tf);
					
				}
				//Toast.makeText(ReceiverActivity.this, sbShow.toString(), Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent();
				intent.putExtra("sbShow", sbShow.toString());
				intent.putExtra("ls_tf", (Serializable)ls_tf);
                setResult(1, intent);
				finish();
			}
			
			@Override
			public void onClickLeftBut() {
				List<TransportFile> ls_tf=new ArrayList<TransportFile>();
				Intent intent = new Intent();
				intent.putExtra("sbShow", "");
				intent.putExtra("ls_tf", (Serializable)ls_tf);
                setResult(1, intent);
				finish();
			}
		});
		topbar_Receiver_add.setTitle("收件人选择");
		topbar_Receiver_add.setKeepScreenOn(true);
		
		initData();
		
	}
	
	private void initData() {
		if(lf==null)
		{
			lf=new ArrayList<FileBean>();
			for(int i=0;i<type.length;i++)
			{
				FileBean fb=new FileBean(String.valueOf(i), "000", type[i],"");
				lf.add(fb);
			}
			
			cusdialog=new CustomDialog(ReceiverActivity.this);
			cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
			cusdialog.setOutCancel(false);
			cusdialog.show();
			
			new Thread(new Runnable() {
				public void run() {
					
					getCommonContact();
					getTransceiver();
					getMemberUnit();
					getCountySafe();
					getCountyUser();
					getCountyCompany();
					
					Message msg=Message.obtain();
	        		msg.what=0;
	        		handler.sendMessage(msg);
	        		cusdialog.dismiss();
					
				}
			}).start();
			
			/*new Thread(new Runnable() {
				public void run() {
					
					getCommonContact();
					changeState();
					
					if(flag==6)
					{
						Message msg=Message.obtain();
		        		msg.what=0;
		        		msg.obj="1";
		        		handler.sendMessage(msg);
		        		cusdialog.dismiss();
					}
				}
			}).start();
			
			new Thread(new Runnable() {
				public void run() {
					
					getTransceiver();
					changeState();
					if(flag==6)
					{
						Message msg=Message.obtain();
		        		msg.what=0;
		        		msg.obj="2";
		        		handler.sendMessage(msg);
		        		cusdialog.dismiss();
					}
				}
			}).start();
			
			new Thread(new Runnable() {
				public void run() {
					
					getMemberUnit();
					changeState();
					if(flag==6)
					{
						Message msg=Message.obtain();
		        		msg.what=0;
		        		msg.obj="3";
		        		handler.sendMessage(msg);
		        		cusdialog.dismiss();
					}
				}
			}).start();
			
			new Thread(new Runnable() {
				public void run() {
					
					getCountySafe();
					changeState();
					if(flag==6)
					{
						Message msg=Message.obtain();
		        		msg.what=0;
		        		msg.obj="4";
		        		handler.sendMessage(msg);
		        		cusdialog.dismiss();
					}
				}
			}).start();
			
			new Thread(new Runnable() {
				public void run() {
					
					getCountyUser();
					changeState();
					if(flag==6)
					{
						Message msg=Message.obtain();
		        		msg.what=0;
		        		msg.obj="5";
		        		handler.sendMessage(msg);
		        		cusdialog.dismiss();
					}
				}
			}).start();
			
			
			new Thread(new Runnable() {
				public void run() {
					
					getCountyCompany();
					changeState();
					if(flag==6)
					{
						Message msg=Message.obtain();
		        		msg.what=0;
		        		msg.obj="6";
		        		handler.sendMessage(msg);
		        		cusdialog.dismiss();
					}
				}
			}).start();*/
		}
		else
		{
			try {
				mAdapter=new TreeViewInitAdapter<FileBean>(ReceiverActivity.this, lf, mListView, R.layout.tree_item,true,0,null);
				initAdapter();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			mListView.setAdapter(mAdapter);
		}
	}
	
	int flag=0;
	private synchronized void changeState() {
		flag++;
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			//Toast.makeText(ReceiverActivity.this, String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
			if(msg.what==0)
			{
				try {
					mAdapter=new TreeViewInitAdapter<FileBean>(ReceiverActivity.this, lf, mListView, R.layout.tree_item,true,0,null);
					initAdapter();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mListView.setAdapter(mAdapter);
				//cusdialog.dismiss();
			}
		}

	};
	
	private void initAdapter() {
		mAdapter.setOnTreeItenLongClickListener(new TreeItemLongClickListener() {

			@Override
			public void onItemLongClick(final TreeNode node, final int position) {
				if(node.isLeaf())
				{
					if(node.getPid().equals("0"))
					{
						AlertDialog.Builder builder=new AlertDialog.Builder(ReceiverActivity.this);  //先得到构造器  
				        builder.setTitle("提示"); //设置标题  
				        builder.setMessage("确认删除当前常用联系人?"); //设置内容  
				        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {
				            	
				            	String conStr=" where userId='"+getCurrentUser().getUserId()+"'";
								SqlOperate<UserContacts> operaterUserContacts=new SqlOperate<UserContacts>(ReceiverActivity.this, UserContacts.class);
								List<UserContacts> ls_uc=operaterUserContacts.SelectEntitysByCondition(conStr);
								
								if(ls_uc.size()!=0)
								{
									UserContacts uc=ls_uc.get(0);
									
									String ContactId=uc.getContactId();
									String nodeId=node.getId();/*.substring(0, node.getId().length()-1);*/
									ContactId=ContactId.replace(nodeId+";", "");
									ContactId=ContactId.replace(";"+nodeId, "");
									ContactId=ContactId.replace(nodeId, "");
									
									uc.setContactId(ContactId);
									operaterUserContacts.upContent(uc);
								}
								
								for(FileBean fb:lf)
								{
									if(fb.getId().equals(node.getId()))
									{
										lf.remove(fb);
										break;
									}
								}
								
								operaterUserContacts.close();
				            	
								mAdapter.deleteTreeNode(position);
				            	dialog.dismiss(); 
				            }  
				        });  
				        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				                dialog.dismiss();  
				            }  
				        });  
				        builder.create().show(); 
					}
					else
					{
						AlertDialog.Builder builder=new AlertDialog.Builder(ReceiverActivity.this);  //先得到构造器  
				        builder.setTitle("提示"); //设置标题  
				        builder.setMessage("确认添加当前用户为常用联系人?"); //设置内容  
				        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				            	
				            	String conStr=" where userId='"+getCurrentUser().getUserId()+"'";
								SqlOperate<UserContacts> operaterUserContacts=new SqlOperate<UserContacts>(ReceiverActivity.this, UserContacts.class);
								List<UserContacts> ls_uc=operaterUserContacts.SelectEntitysByCondition(conStr);
								operaterUserContacts.close();
								
								UserContacts uc;
								SqlOperate<UserContacts> operaterUserContacts_add=new SqlOperate<UserContacts>(ReceiverActivity.this, UserContacts.class);
								if(ls_uc.size()==0)
								{
									uc=new UserContacts();
									uc.setId(UUID.randomUUID().toString());
									uc.setUserId(getCurrentUser().getUserId());
									uc.setContactId(node.getId());
									operaterUserContacts_add.saveContent(uc);
								}
								else
								{
									uc=ls_uc.get(0);
									if(uc.getContactId().indexOf(node.getId())!=-1)
									{
										dialog.dismiss(); 
						                Toast.makeText(ReceiverActivity.this, "该用户已经为常用联系人", Toast.LENGTH_SHORT).show();  
						                operaterUserContacts.close();
						                return;
									}
									else
									{
										uc.setContactId(uc.getContactId()+";"+node.getId());
										operaterUserContacts_add.upContent(uc);
									}
									
								}
								
								String sqlStr=" where userId='"+node.getId()+"' ";
								SqlOperate<User> operaterUsers=new SqlOperate<User>(ReceiverActivity.this, User.class);
								List<User> ls_user=operaterUsers.SelectEntitysByCondition(sqlStr);
								operaterUsers.close();
								
								if(ls_user.size()!=0)
								{
									FileBean fb=new FileBean(ls_user.get(0).getUserId(), "0", ls_user.get(0).getRealName(),"");
									lf.add(1, fb);
									
									TreeNode newnode=new TreeNode(ls_user.get(0).getUserId(), "0", ls_user.get(0).getRealName(), "");
									mAdapter.addTreeNode(0, newnode);
									
									//mAdapter.notifyDataSetChanged();
								}
				            	
				                dialog.dismiss(); 
				                Toast.makeText(ReceiverActivity.this, "添加成功", Toast.LENGTH_SHORT).show();  
				            }  
				        });  
				        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				                dialog.dismiss();  
				            }  
				        });  
				        builder.create().show(); 
					}
				}
			}
		});
	}
	
	private void getCountyCompany() {
		String conUserStr=" where departmentId='19d24acf-86f8-4e7a-a4cc-3d49b5b17fe1' and realAuditState='1' ";
		SqlOperate<User> operaterUser=new SqlOperate<User>(ReceiverActivity.this, User.class);
		List<User> ls_user=operaterUser.SelectEntitysByCondition(conUserStr);
		operaterUser.close();
		
		for(User user:ls_user)
		{
			FileBean fb_user=new FileBean(user.getUserId(), "5", user.getRealName(),"");
			lf.add(fb_user);
		}
	}

	private void getCountyUser() {
		String conStr=" where a_level='1' ";
		SqlOperate<CountyArea> operaterCountyArea=new SqlOperate<CountyArea>(ReceiverActivity.this, CountyArea.class);
		List<CountyArea> ls_ca=operaterCountyArea.SelectEntitysByCondition(conStr);
		operaterCountyArea.close();
		
		for(CountyArea countyArea:ls_ca)
		{
			FileBean fb_dep=new FileBean(countyArea.getA_id(), "4", countyArea.getA_areaname(),"");
			lf.add(fb_dep);
			
			String id;
			for(int i=0;i<areaType.length;i++)
			{
				id=UUID.randomUUID().toString();
				FileBean fb_qx=new FileBean(id, countyArea.getA_id(), areaType[i],"");
				lf.add(fb_qx);
				
				if(i==0)
				{
					String conDepStr=" where d_level='1' and d_belongareaid='"+countyArea.getA_id()+"' and d_parentdepartmentid!='-2' ";
					SqlOperate<CountyDepartment> operaterCountyDepartment=new SqlOperate<CountyDepartment>(ReceiverActivity.this, CountyDepartment.class);
					List<CountyDepartment> ls_cd=operaterCountyDepartment.SelectEntitysByCondition(conDepStr);
					operaterCountyDepartment.close();
					
					String qx_aid;
					for(CountyDepartment cd:ls_cd)
					{
						String conUserStr=" where departmentId='"+cd.getD_id()+"' and usertype='区县用户' ";
						SqlOperate<User> operaterUser=new SqlOperate<User>(ReceiverActivity.this, User.class);
						List<User> ls_user=operaterUser.SelectEntitysByCondition(conUserStr);
						operaterUser.close();
						
						if(ls_user.size()>0)
						{
							qx_aid=UUID.randomUUID().toString();
							FileBean fb_qz=new FileBean(qx_aid, id, cd.getD_departmentname(),"");
							lf.add(fb_qz);
							
							for(User user:ls_user)
							{
								FileBean fb_user=new FileBean(user.getUserId(), qx_aid, user.getRealName(),"");
								lf.add(fb_user);
							}
						}
					}
				}
				else if(i==1)
				{
					String conStr_jb=" where a_parentareaid='"+countyArea.getA_id()+"' ";
					SqlOperate<CountyArea> operaterCountyArea_jb=new SqlOperate<CountyArea>(ReceiverActivity.this, CountyArea.class);
					List<CountyArea> ls_ca_jb=operaterCountyArea_jb.SelectEntitysByCondition(conStr_jb);
					operaterCountyArea_jb.close();
					
					for(CountyArea ca_jb:ls_ca_jb)
					{
						String conDepStr_jb=" where d_belongareaid='"+ca_jb.getA_id()+"' ";
						SqlOperate<CountyDepartment> operaterCountyDepartment_jb=new SqlOperate<CountyDepartment>(ReceiverActivity.this, CountyDepartment.class);
						List<CountyDepartment> ls_cd=operaterCountyDepartment_jb.SelectEntitysByCondition(conDepStr_jb);
						operaterCountyDepartment_jb.close();
						
						String jb_aid;
						for(CountyDepartment cd:ls_cd)
						{
							String conUserStr=" where departmentId='"+cd.getD_id()+"' and usertype='乡镇(街办)用户' ";
							SqlOperate<User> operaterUser=new SqlOperate<User>(ReceiverActivity.this, User.class);
							List<User> ls_user=operaterUser.SelectEntitysByCondition(conUserStr);
							operaterUser.close();
							
							if(ls_user.size()>0)
							{
								jb_aid=UUID.randomUUID().toString();
								FileBean fb_qz=new FileBean(jb_aid, id, cd.getD_departmentname(),"");
								lf.add(fb_qz);
								
								for(User user:ls_user)
								{
									FileBean fb_user=new FileBean(user.getUserId(), jb_aid, user.getRealName(),"");
									lf.add(fb_user);
								}
							}
						}
					}
				}
				
			}
		}
	}

	private void getCountySafe() {
		String conStr=" where d_level='1' and d_writerdepid='19d24acf-86f8-4e7a-a4cc-3d49b5b17fe1' ";
		SqlOperate<CountyDepartment> operaterCountyDepartment=new SqlOperate<CountyDepartment>(ReceiverActivity.this, CountyDepartment.class);
		List<CountyDepartment> ls_cd=operaterCountyDepartment.SelectEntitysByCondition(conStr);
		operaterCountyDepartment.close();
		
		for(CountyDepartment cd:ls_cd)
		{
			String conUserStr=" where departmentId='"+cd.getD_id()+"' and realAuditState!='1' ";
			SqlOperate<User> operaterUser=new SqlOperate<User>(ReceiverActivity.this, User.class);
			List<User> ls_user=operaterUser.SelectEntitysByCondition(conUserStr);
			operaterUser.close();
			
			if(ls_user.size()>0)
			{
				FileBean fb_dep=new FileBean(cd.getD_id(), "3", cd.getD_departmentname(),"");
				lf.add(fb_dep);
				
				for(User user:ls_user)
				{
					FileBean fb_user=new FileBean(user.getUserId(), cd.getD_id(), user.getRealName(),"");
					lf.add(fb_user);
				}
			}
		}
	}

	private void getMemberUnit() {
		String conStr=" where d_level='0'";
		SqlOperate<CountyDepartment> operaterCountyDepartment=new SqlOperate<CountyDepartment>(ReceiverActivity.this, CountyDepartment.class);
		List<CountyDepartment> ls_cd=operaterCountyDepartment.SelectEntitysByCondition(conStr);
		operaterCountyDepartment.close();
		
		for(CountyDepartment cd:ls_cd)
		{
			String conUserStr=" where departmentId='"+cd.getD_id()+"' and realAuditState!='1' ";
			SqlOperate<User> operaterUser=new SqlOperate<User>(ReceiverActivity.this, User.class);
			List<User> ls_user=operaterUser.SelectEntitysByCondition(conUserStr);
			operaterUser.close();
			
			if(!cd.getD_id().equals("19d24acf-86f8-4e7a-a4cc-3d49b5b17fe1"))
			{
				getNotSafe(cd,ls_user);
			}
			else
			{
				getIsSafe(cd,ls_user);
			}
		}
		
	}

	private void getIsSafe(CountyDepartment cd,List<User> ls_user) {
		
		FileBean fb_dep=new FileBean(cd.getD_id(), "2", cd.getD_departmentname(),"");
		lf.add(fb_dep);
		
		Map<String, String> map = new HashMap<String, String>();
		
		for(User user:ls_user)
		{
			FileBean fb_user=new FileBean(user.getUserId(), user.getOfficeId(), user.getRealName(),"");
			lf.add(fb_user);
			
			if(map.get(user.getOfficeId())==null)
			{
				if(user.getOfficeName().equals(""))
				{
					map.put(user.getOfficeId(), user.getRoleName());
				}
				else
				{
					map.put(user.getOfficeId(), user.getOfficeName());
				}
			}
		}
		
		for (String key : map.keySet()) 
		{
		   FileBean fb_office=new FileBean(key, cd.getD_id(), map.get(key),"");
		   lf.add(fb_office);
		}
	}

	private void getNotSafe(CountyDepartment cd,List<User> ls_user) {
		if(ls_user.size()>0)
		{
			FileBean fb=new FileBean(cd.getD_id(), "2", cd.getD_departmentname(),"");
			lf.add(fb);
			for(User user:ls_user)
			{
				FileBean fb_user=new FileBean(user.getUserId(), user.getDepartmentId(), user.getRealName(),"");
				lf.add(fb_user);
			}
		}
	}

	private void getTransceiver() {
		String conStr=" where isRecSend='1'";
		SqlOperate<User> operaterUsers=new SqlOperate<User>(ReceiverActivity.this, User.class);
		List<User> ls_user=operaterUsers.SelectEntitysByCondition(conStr);
		operaterUsers.close();
		
		Map<String, String> map = new HashMap<String, String>();
		
		String id;
		for(User user:ls_user)
		{
			if(map.get(user.getDepartmentName())==null)
			{
				id=UUID.randomUUID().toString();
				map.put(user.getDepartmentName(), id);
			}
			else
			{
				id=map.get(user.getDepartmentName());
			}
			
			FileBean fb=new FileBean(user.getUserId(), id, user.getRealName(),"");
			lf.add(fb);
		}
		
		for (String key : map.keySet()) 
		{
		   FileBean fb=new FileBean(map.get(key), "1", key,"");
		   lf.add(fb);
		}
	}

	/**
	* @author yk 
	* @date 2015年12月9日 下午1:44:58 
	* @Title: getCommonContact 
	* @Description: 常用联系人
	* @return void    返回类型 
	* @throws
	 */
	private void getCommonContact() {
		String conStr=" where userId='"+getCurrentUser().getUserId()+"'";
		SqlOperate<UserContacts> operaterUserContacts=new SqlOperate<UserContacts>(ReceiverActivity.this, UserContacts.class);
		List<UserContacts> ls_uc=operaterUserContacts.SelectEntitysByCondition(conStr);
		operaterUserContacts.close();
		
		if(ls_uc.size()!=0)
		{
			StringBuffer sbSql=new StringBuffer();
			sbSql.append(" where ");
			String[] userIds=ls_uc.get(0).getContactId().split(";");
			for(String s:userIds)
			{
				sbSql.append(" userId='");
				sbSql.append(s);
				sbSql.append("' or ");
			}
			String sqlStr=sbSql.substring(0, sbSql.length()-3);
			SqlOperate<User> operaterUsers=new SqlOperate<User>(ReceiverActivity.this, User.class);
			List<User> ls_user=operaterUsers.SelectEntitysByCondition(sqlStr);
			operaterUsers.close();
			if(ls_user.size()==0)
			{
				//lf.remove(0);
			}
			else
			{
				for(User user:ls_user)
				{
					FileBean fb=new FileBean(user.getUserId(), "0", user.getRealName(),"");
					lf.add(fb);
				}
			}
		}
	}

	private void initComponent() {
		lin_table_name=(LinearLayout) findViewById(R.id.lin_table_name);
		lin_table_name.setVisibility(View.GONE);
		mListView=(ListView) findViewById(R.id.list_standcheck_add);
		
		topbar_Receiver_add=(TopBarView) findViewById(R.id.topbar_standcheck_add);
	}
	
}


