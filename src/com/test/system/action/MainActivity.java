package com.sunnyit.system.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.jauker.widget.BadgeView;
import com.sunnyit.R;
import com.sunnyit.chemicals.action.ChemicalsDirectoryActivity;
import com.sunnyit.common.activity.ActivityCollector;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.convert.PpConvert;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.network.NetworkDetector;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.LeftSlidingMenu;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.company.action.CompanyIndustryActivity;
import com.sunnyit.company.action.CompanyListActivity;
import com.sunnyit.document.action.EnforcementDocumentActivity;
import com.sunnyit.enforcement.action.HiddenGovernChoiceTabActivity;
import com.sunnyit.filetransfer.action.FileTransferTabActivity;
import com.sunnyit.hiddencheck.action.HiddenTabSortActivity;
import com.sunnyit.law.action.LawRegulationTabActivity;
import com.sunnyit.menu.action.HelpActivity;
import com.sunnyit.menu.action.NoteListActivity;
import com.sunnyit.menu.action.SystemSettingActivity;
import com.sunnyit.menu.action.UserInfoUpActivity;
import com.sunnyit.menu.action.UserPwUpActivity;
import com.sunnyit.remind.action.InformationRemindActivity;
import com.sunnyit.remind.model.InformationRemind;
import com.sunnyit.synchronous.action.SynchronousActivity;
import com.sunnyit.system.data.MenuAdapter;
import com.sunnyit.system.model.menu_item;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class MainActivity extends BaseActivity  {

	private LeftSlidingMenu mLeftSlidingMenu;
	private ListView list_menu;
	private List<menu_item> mdatas;
	private MenuAdapter madapter;
	private TopBarView topbar_pwup ;
	private TextView tv_main_network ;
	
	private TextView txt_user_name,use_duty;
	
	private RelativeLayout rel_sys_quit,rel_sys_cancel;
	
	GridView gridview_main;
	
	private String gridtexts[] = new String[]{ 
			"企业管理", "隐患自查","法律法规",
            "执法检查", "执法文书","危化品", 
            "数据同步","信息提醒","文件传输"};
    private int gridimages[] = new int[]{
    		R.drawable.companymanager160,R.drawable.hiddentrouble160,R.drawable.lawsregulations160,
    		R.drawable.lawenforcement160,R.drawable.lawinstruments160,R.drawable.hazardouschemicals160,
    		R.drawable.datasynchronization160,R.drawable.informationalert160,R.drawable.filetransfer160
    };
    
    /*R.drawable.enforce_check128,R.drawable.now_ask128,
	R.drawable.info_up128,R.drawable.info_put128,R.drawable.file_up128*/
    
    private String itemtexts[] = new String[]{ 
			"记事本", "信息修改","密码修改",
            "系统设置", "系统帮助"};//,"技术支持"
    /*private int itemimages[] = new int[]{
    		R.drawable.rember_info,R.drawable.user_info_edit,R.drawable.user_pwd_edit,
    		R.drawable.system_set,R.drawable.system_help
    };//,R.drawable.about_our*/
    
    private int itemimages[] = new int[]{
    		R.drawable.note_info,R.drawable.user_info_up,R.drawable.user_pwd_up,
    		R.drawable.system_setting,R.drawable.system_helping
    };
    
    private BadgeView badgeView;
    
    private Handler doActionHandler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            int msgId = msg.what;  
            switch (msgId) {  
                case 1:  
                	tv_main_network.setVisibility(View.GONE);
                    break;  
                default:  
                    break;  
            }  
        }  
    };  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mian);
		
		initComponent();
		initData();
		
		badgeView = new BadgeView(this);  
        badgeView.setTargetView(gridview_main);  
        badgeView.setBadgeGravity(Gravity.CENTER);  
        badgeView.setBadgeMargin(PpConvert.dp2px(this, 18), PpConvert.dp2px(this, 20), 0, 0);
		setRemaindCount(); 
        
		
		String  networType=NetworkDetector.getCurrentNetType(this);
		if (networType.equals("null")) 
		{
			tv_main_network.setText("当前没有网络连接可用,自动转换为wifi模式");
			Editor edit=sp.edit();
			edit.putString("wifi", "yes");
			edit.commit();
		} 
		else if (networType.equals("wifi")) 
		{
			tv_main_network.setText("当前网络连接为wifi网络");
		} 
		else if (networType.equals("2g")) 
		{
			tv_main_network.setText("当前网络连接为2G网络,建议将应用网络访问模式设置为为wifi模式");
		}
		else
		{
			tv_main_network.setText("当前网络连接为3G或3G以上模式");
		}
		
		tv_main_network.requestFocus();
		final Timer timer = new Timer();  
		final TimerTask task = new TimerTask() 
		{   
			@Override   
			public void run() 
			{   
				 Message message = new Message();  
                 message.what = 1;  
                 doActionHandler.sendMessage(message);  
                 timer.cancel();
			}  
		};
		timer.schedule(task, 1000*5); 
		
		list_menu.setAdapter(madapter);
		list_menu.setOnItemClickListener(new menuClickListener());
		
		initGridview();
		initQuitCancel();
		
		topbar_pwup.setTopBarClick(new TopBarView.ITopBarClick() {
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				menuSwitch();
			}
		});
		
		createDir();
	}

	private void setRemaindCount() {
		String sqlStr=" where I_HaveLook='0' ";
		SqlOperate<InformationRemind> opetaterDailyCheck=new SqlOperate<InformationRemind>(MainActivity.this, InformationRemind.class);
		List<InformationRemind> data=opetaterDailyCheck.SelectEntitysByCondition(sqlStr);
        opetaterDailyCheck.close();
		
        badgeView.setBadgeCount(data.size());
        badgeView.setTextSize(20);
	}

	private void createDir() {
		String outPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/anjianju";
		File mFile = new File(outPath);
		 if(!mFile.exists())  
		 {
			 mFile.mkdir();
		 }
	}
	
	private void initQuitCancel() {
		// TODO Auto-generated method stub
		rel_sys_quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    final CustomDialog cusdialog=new CustomDialog(MainActivity.this);
				cusdialog.setViewAndAlpha(R.layout.dialog_custom,0);
				cusdialog.setText(R.id.tv_dg_title, "提示");
				cusdialog.setText(R.id.tv_dg_context, "是否确认退出?");
				cusdialog.findViewById(R.id.but_dg_positiveButton).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						cusdialog.dismiss();
			        	ActivityCollector.finishAll();
					}
				});
				cusdialog.findViewById(R.id.but_dg_negativeButton).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						cusdialog.dismiss();
					}
				});
				cusdialog.show();
				
			}
		});
		
		rel_sys_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				final CustomDialog cusdialog=new CustomDialog(MainActivity.this);
				cusdialog.setViewAndAlpha(R.layout.dialog_custom,0);
				cusdialog.setText(R.id.tv_dg_title, "提示");
				cusdialog.setText(R.id.tv_dg_context, "是否确认注销?");
				cusdialog.findViewById(R.id.but_dg_positiveButton).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						cusdialog.dismiss();
						finish();
					}
				});
				cusdialog.findViewById(R.id.but_dg_negativeButton).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						cusdialog.dismiss();
					}
				});
				cusdialog.show();
			}
		});
	}

	private void initGridview() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < gridtexts.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", gridimages[i]);
            map.put("itemText", gridtexts[i]);
            lstImageItem.add(map);
        }
        
        SimpleAdapter saImageItems = new SimpleAdapter(this, 
                lstImageItem,// 数据源
                R.layout.night_item,// 显示布局
                new String[] { "itemImage", "itemText" }, 
                new int[] { R.id.itemImage, R.id.itemText }); 
        gridview_main.setAdapter(saImageItems);
        gridview_main.setOnItemClickListener(new gridClickListener());
	}
	
	class gridClickListener implements OnItemClickListener 
	 {
        public void onItemClick(AdapterView<?> parent, View view, int position, long rowid) 
        {
        	//HashMap<String, Object> item = (HashMap<String, Object>)parent.getItemAtPosition(position);
            //获取数据源的属性值
            //String itemText=(String)item.get("itemText");
            //Object object=item.get("itemImage");
            //Toast.makeText(MainActivity.this, itemText, Toast.LENGTH_LONG).show();
            //根据图片进行相应的跳转
            switch (gridimages[position]) {
	            case R.drawable.companymanager160:
	                startActivity(new Intent(MainActivity.this, CompanyIndustryActivity.class));//启动另一个Activity
	                break;
	            case R.drawable.hiddentrouble160:
	                Intent intentHidden=new Intent(MainActivity.this, HiddenTabSortActivity.class);
	                startActivity(intentHidden);
	                break;
	            case R.drawable.lawsregulations160:
	            	startActivity(new Intent(MainActivity.this, LawRegulationTabActivity.class));
	                break;
	            case R.drawable.lawenforcement160:
	            	Intent intentGovern=new Intent(MainActivity.this, HiddenGovernChoiceTabActivity.class);
	                startActivity(intentGovern);
	                break;
	            case R.drawable.lawinstruments160:
	                startActivity(new Intent(MainActivity.this, EnforcementDocumentActivity.class));
	                break;
	            case R.drawable.hazardouschemicals160:
	            	startActivity(new Intent(MainActivity.this, ChemicalsDirectoryActivity.class));
	                break;
	            case R.drawable.datasynchronization160:
	                startActivity(new Intent(MainActivity.this, SynchronousActivity.class));
	                break;
	            case R.drawable.informationalert160:
	            	//startActivity(new Intent(MainActivity.this, InformationRemindActivity.class));
	            	startActivityForResult(new Intent(MainActivity.this, InformationRemindActivity.class), 0);
	                break;
	            case R.drawable.filetransfer160:
	                startActivity(new Intent(MainActivity.this, FileTransferTabActivity.class));
	                break;
            }
        }
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			setRemaindCount();
			break;

		default:
			break;
		}
	}
	
	class menuClickListener implements OnItemClickListener
	 {
       public void onItemClick(AdapterView<?> parent, View view, int position, long rowid) 
       {
    	   menu_item item = (menu_item)parent.getItemAtPosition(position);
           switch (itemimages[position]) {
	           case R.drawable.note_info:
	               startActivity(new Intent(MainActivity.this, NoteListActivity.class));//启动另一个Activity
	               break;
	           case R.drawable.user_info_up:
	               startActivity(new Intent(MainActivity.this, UserInfoUpActivity.class));
	               break;
	           case R.drawable.user_pwd_up:
	               startActivity(new Intent(MainActivity.this, UserPwUpActivity.class));
	               break;
	           case R.drawable.system_setting:
	               startActivity(new Intent(MainActivity.this, SystemSettingActivity.class));//启动另一个Activity
	               break;
	           case R.drawable.system_helping:
	               startActivity(new Intent(MainActivity.this, HelpActivity.class));
	               break;
	           /*case R.drawable.about_our:
	               startActivity(new Intent(MainActivity.this, ContactActivity.class));
	               break;*/
           }
       }
   }
	
	private void initData() {
		// TODO Auto-generated method stub
		mdatas=new ArrayList<menu_item>();
		for (int i = 0; i < itemimages.length; i++) {
            menu_item item=new menu_item(itemimages[i],itemtexts[i]);
    		mdatas.add(item);
        }
		madapter=new MenuAdapter(this, mdatas, R.layout.menu_item);
		
	}

	private void initComponent()
	{
		topbar_pwup = (TopBarView)findViewById(R.id.topbar_pwup);
		mLeftSlidingMenu=(LeftSlidingMenu) findViewById(R.id.hor_scr_main);
		list_menu=(ListView) findViewById(R.id.list_menu);
		gridview_main=(GridView) findViewById(R.id.gridview_main);
		
		rel_sys_quit=(RelativeLayout) findViewById(R.id.rel_sys_quit);
		rel_sys_cancel=(RelativeLayout) findViewById(R.id.rel_sys_cancel);
		
		tv_main_network=(TextView) findViewById(R.id.tv_main_network);
		tv_main_network.setVisibility(View.GONE);
		tv_main_network.setEnabled(false);
		
		txt_user_name=(TextView) findViewById(R.id.txt_user_name);
		use_duty=(TextView) findViewById(R.id.use_duty);
		
		txt_user_name.setText(getCurrentUser().getDepartmentName());
		use_duty.setText(getCurrentUser().getRealName());
    }
	
	public void menuSwitch()
	{
		mLeftSlidingMenu.menuSwitch();
	}
}


