package com.sunnyit.hiddencheck.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.activity.ImgShowActivity;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemClickListener;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemLongClickListener;
import com.sunnyit.common.treeview.TreeNode;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import test.action.DialogShowActivity;
import test.data.TreeViewInitAdapter;
import test.model.FileBean;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenStandCheckaddActivity extends BaseActivity {
	
	private TopBarView topbar_standcheck_add;
	
	private ListView mListView;
	private List<FileBean> lf;
	private TreeViewInitAdapter<FileBean> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_standcheck_add);
		
		initComponent();
		initDate();
		
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
		
		
		try {
			mAdapter=
					new TreeViewInitAdapter<FileBean>(this, lf, mListView, R.layout.tree_item,true,1,null);
			mListView.setAdapter(mAdapter);
			
			mAdapter.setOnTreeItenClickListener(new TreeItemClickListener() {

				@Override
				public void onItemClick(TreeNode node, int position) {
					// TODO Auto-generated method stub
					if(node.isLeaf())
					{
						//Toast.makeText(HiddenStandCheckaddActivity.this, node.getName(), Toast.LENGTH_SHORT).show();
						AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckaddActivity.this);  //先得到构造器  
				        builder.setTitle("信息展示"); //设置标题  
				        builder.setMessage(node.getDes()); //设置内容  
				        builder.setIcon(R.drawable.note_stand48);//设置图标，图片id即可  
				        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				                dialog.dismiss();  
				            }  
				        });  
				        //参数都设置完成了，创建并显示出来  
				        builder.create().show(); 
					}
				}
			});
			
			mAdapter.setOnTreeItenLongClickListener(new TreeItemLongClickListener() {

				@Override
				public void onItemLongClick(TreeNode node, int position) {
					// TODO Auto-generated method stub
					if(node.isLeaf())
					{
						Intent intent=new Intent(HiddenStandCheckaddActivity.this, ImgShowActivity.class);
			       		startActivity(intent);
					}
				}
			});
			
		} catch (IllegalAccessException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_standcheck_add=(TopBarView) findViewById(R.id.topbar_standcheck_add);
		mListView=(ListView) findViewById(R.id.list_standcheck_add);
	}
	
	private void initDate() {
		lf=new ArrayList<FileBean>();
		FileBean fb1=new FileBean("1", "0", "基础管理类隐患","");
		lf.add(fb1);
		FileBean fb2=new FileBean("2", "0", "现场管理类隐患","");
		lf.add(fb2);
		FileBean fb3=new FileBean("3", "1", "重大危险源管理","");
		lf.add(fb3);
		FileBean fb4=new FileBean("4", "1", "职业健康","");
		lf.add(fb4);
		FileBean fb5=new FileBean("5", "2", "作业场所","");
		lf.add(fb5);
		FileBean fb6=new FileBean("6", "2", "个体防护","");
		lf.add(fb6);
		
		FileBean fb7=new FileBean("7", "3", "登记建档备案缺陷","");
		lf.add(fb7);
		FileBean fb8=new FileBean("8", "4", "职业健康检查缺陷","");
		lf.add(fb8);
		FileBean fb9=new FileBean("9", "4", "其他","");
		lf.add(fb9);
		FileBean fb10=new FileBean("10", "5", "其他","");
		lf.add(fb10);
		FileBean fb11=new FileBean("11", "6", "其他","");
		lf.add(fb11);
		
		FileBean fb12=new FileBean("12", "7", "未对辨识确认的重大危险源及时、逐项进行登记建档、备案",
				"《危险化学品重大危险源监督管理暂行规定》（总局令第40号）第二十四条　危险化学品单位新建、改建和扩建危险化学品建设项目，应当在建设项目竣工验收前完成重大危险源的辨识、安全评估和分级、登记建档工作，并向所在地县级人民政府安全生产监督管理部门备案。");
		lf.add(fb12);
		FileBean fb13=new FileBean("13", "8", "未按规定建立职业健康档案",
				"《工作场所职业卫生监督管理规定》（总局令第47号）第三十一条: 职业健康监护档案应当包括劳动者的职业史、职业病危害接触史、职业健康检查结果、处理结果和职业病诊疗等有关个人健康资料。");
		lf.add(fb13);
		FileBean fb14=new FileBean("14", "9", "未按相关规定将职业病患者调离原岗位等。",
				"《职业病防治法》(主席令第52号)第五十七条：对不适宜继续从事原工作的职业病病人，应当调离原岗位，并妥善安置。");
		lf.add(fb14);
		FileBean fb15=new FileBean("15", "9", "未建立健全职业卫生档案资料。",
				"《工作场所职业卫生监督管理规定》（总局令第47号）第三十四条：职业卫生档案资料包括：职业病防治责任制文件；职业卫生管理规章制度、操作规程；工作场所职业病危害因素种类清单、岗位分布以及作业人员接触情况等资料；等十二条要求。	");
		lf.add(fb15);
		FileBean fb16=new FileBean("16", "10", "消防应急照明和疏散指示不符合要求。",
				"《消防应急照明和疏散指示系统》（GB17945-2010）6。");
		lf.add(fb16);
		FileBean fb17=new FileBean("17", "10", "加油站作业区内种植有油性植物。",
				"《汽车加油加气站设计与施工规范》（GB50156-2012）12.3.1：加油站作业区内不得种植油性植物。");
		lf.add(fb17);
		FileBean fb18=new FileBean("18", "11", "未将佩戴使用后的防护用品未进行维护和保管",
				"《个体防护装备配备基本要求》（GB/T29510-2013）8.17：经佩戴使用后的防护用品未按照产品要求和特性进行维护和保管，对可能造成环境污染的有毒有害护品未集中管理，未定期收回、统一处理。");
		lf.add(fb18);
		FileBean fb19=new FileBean("19", "11", "未将佩戴使用后的防护用品未进行维护和保管",
				"《个体防护装备配备基本要求》（GB/T29510-2013）8.17：经佩戴使用后的防护用品未按照产品要求和特性进行维护和保管，对可能造成环境污染的有毒有害护品未集中管理，未定期收回、统一处理。");
		lf.add(fb19);
		
	}
	
}


