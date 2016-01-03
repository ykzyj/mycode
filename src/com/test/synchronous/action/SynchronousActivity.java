package com.sunnyit.synchronous.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.async.AllDataGetAsyncOOM;
import com.sunnyit.common.async.HttpClientNpAsyncPost;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.enforcement.model.Inspector;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;
import com.sunnyit.hiddencheck.model.SelfCheck;
import com.sunnyit.hiddencheck.model.SelfStandCheck;
import com.sunnyit.remind.model.InformationRemind;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

import android.app.NotificationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015年8月1日 上午9:47:36 
* @version V1.0   
*/
public class SynchronousActivity extends BaseActivity  {
	
	private TopBarView topbar_hidden_check;
	private Button but_allinfo_downup;
	private Button but_allinfo_down;
	private Button but_allinfo_up;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.synchronous);
		initComponent();
		
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(12311);
		
		InformationRemind informationRemind=(InformationRemind) getIntent().getSerializableExtra("info_up");
		if(informationRemind!=null)
		{
			informationRemind.setI_HaveLook("1");
			SqlOperate<InformationRemind> opetaterInformationRemind=new SqlOperate<InformationRemind>(SynchronousActivity.this, InformationRemind.class);
			opetaterInformationRemind.upContent(informationRemind);
			opetaterInformationRemind.close();
		}

		
		topbar_hidden_check.setTopBarClick(new ITopBarClick() {
			
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
		
		but_allinfo_downup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				allinfo_up();
				allinfo_down();
			}
		});
		
		but_allinfo_down.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				allinfo_down();
			}

		});
		
		but_allinfo_up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				allinfo_up();
			}

		});
	}
	
	private void allinfo_up() {
		SqlOperate<UpInfo> opetaterInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
		final List<UpInfo> ls_UpInfo=opetaterInfo.SelectEntitys();
		opetaterInfo.close();
		if(ls_UpInfo.size()==0)
		{
			Toast.makeText(SynchronousActivity.this, "当前没有数据需要提交", Toast.LENGTH_SHORT).show();
		}
		else
		{
			final CustomDialog cusdialogt=new CustomDialog(SynchronousActivity.this);
			cusdialogt.setViewAndAlpha(R.layout.dialog_custom,0);
			cusdialogt.initProgressBar(R.id.id_progressbarh);
			cusdialogt.setOutCancel(false);
			cusdialogt.setText(R.id.tv_dg_title, "数据上传中");
			cusdialogt.findViewById(R.id.rel_cb_divider).setVisibility(View.GONE);
			cusdialogt.findViewById(R.id.lin_but_divider).setVisibility(View.GONE);
			cusdialogt.show();
			
			final float minp=100.0f/ls_UpInfo.size();
			new Thread(new Runnable(){  
	            public void run(){
	            	for(int i=0;i<ls_UpInfo.size();i++)
					{
						if(ls_UpInfo.get(i).getInfoTable().equals("SimpleEnterprise"))
						{
							saveSimple(ls_UpInfo, i);
						}
						else if(ls_UpInfo.get(i).getInfoTable().equals("SelfCheck")&&
								ls_UpInfo.get(i).getOperateType().equals("update"))
						{
							saveSelfCheckSupervise(ls_UpInfo, i);
						}
						else if(ls_UpInfo.get(i).getInfoTable().equals("SelfStandCheck")&&
								ls_UpInfo.get(i).getOperateType().equals("update"))
						{
							saveSelfStandCheckSupervise(ls_UpInfo, i);
						}
						else if(ls_UpInfo.get(i).getInfoTable().equals("DailyCheck"))
						{
							upDailyCheck(ls_UpInfo, i);
						}
						else if(ls_UpInfo.get(i).getInfoTable().equals("SpecialCheck"))
						{
							upSpecialCheck(ls_UpInfo, i);
						}
						else if(ls_UpInfo.get(i).getInfoTable().equals("Standard_CK_Table"))
						{
							upStandCheck(ls_UpInfo, i);
						}
						else if(ls_UpInfo.get(i).getInfoTable().equals("User"))
						{
							upUser(ls_UpInfo, i);
						}
						else
						{
							SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
				        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
				        	opetaterUpInfo.close();
						}
						cusdialogt.setProgress((int)(minp*(i+1)));
					}
	            	cusdialogt.dismiss();
	            	handler.sendEmptyMessage(0);
	            }
	        }).start();  
		}
	}
	
	private void upUser(List<UpInfo> ls_UpInfo, int i) {
		if(ls_UpInfo.get(i).getOperateType().equals("upUserInfo"))
		{
			String conditionStr=" where userId='"+ls_UpInfo.get(i).getInfoId()+"' ";
    		SqlOperate<User> opetaterUser=new SqlOperate<User>(SynchronousActivity.this, User.class);
			final List<User> ls_User=opetaterUser.SelectEntitysByCondition(conditionStr);
			opetaterUser.close();
			
			String modifyUserUrl=getBaseUrl()+"/appWebSave/modifyUserInfo";
			
			if(ls_User.size()>0)
			{
				HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_User.get(0));
	        	post.execute(modifyUserUrl);
	        	
	        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
	        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
	        	opetaterUpInfo.close();
			}
		}
		else if(ls_UpInfo.get(i).getOperateType().equals("upUserPw"))
		{
			String conditionStr=" where userId='"+ls_UpInfo.get(i).getInfoId()+"' ";
    		SqlOperate<User> opetaterUser=new SqlOperate<User>(SynchronousActivity.this, User.class);
			final List<User> ls_User=opetaterUser.SelectEntitysByCondition(conditionStr);
			opetaterUser.close();
			
			String addDcUrl=getBaseUrl()+"/appWebSave/resetPassword";
			
			if(ls_User.size()>0)
			{
				HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_User.get(0));
	        	post.execute(addDcUrl);
	        	
	        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
	        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
	        	opetaterUpInfo.close();
			}
		}
	}
	
	/**
	* @author yk 
	* @date 2015年10月16日 下午3:31:51 
	* @Title: upStandCheck 
	* @Description: 日常检查数据上传
	* @param ls_UpInfo_dc
	* @param ls_UpInfo_sc
	* @param ls_UpInfo_st
	* @param cusdialogt
	* @param minp    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void upStandCheck(final List<UpInfo> ls_UpInfo, final int i) {

		if(ls_UpInfo.get(i).getOperateType().equals("add"))
		{
			String conditionStr=" where ck_id='"+ls_UpInfo.get(i).getInfoId()+"' ";
    		SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(SynchronousActivity.this, Standard_CK_Table.class);
			final List<Standard_CK_Table> ls_Standard_CK_Table=opetaterStandard_CK_Table.SelectEntitysByCondition(conditionStr);
			opetaterStandard_CK_Table.close();
			
			String addScUrl=getBaseUrl()+"/appWebSave/saveStandCheck";
			
			if(ls_Standard_CK_Table.size()>0)
			{
				HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_Standard_CK_Table.get(0));
	        	post.execute(addScUrl);
	        	
	        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
	        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
	        	opetaterUpInfo.close();
	        	
	        	String conditionStrIn=" where ck_id='"+ls_Standard_CK_Table.get(0).getCk_id()+"' ";
        		SqlOperate<Standard_CK_Table_Item> opetaterStandard_CK_Table_Item=
        				new SqlOperate<Standard_CK_Table_Item>(SynchronousActivity.this, Standard_CK_Table_Item.class);
				final List<Standard_CK_Table_Item> ls_Standard_CK_Table_Item=opetaterStandard_CK_Table_Item.SelectEntitysByCondition(conditionStrIn);
				opetaterStandard_CK_Table_Item.close();
				
				String addInUrl=getBaseUrl()+"/appWebSave/saveStandCheckItem";
				
				for(Standard_CK_Table_Item item:ls_Standard_CK_Table_Item)
				{
					HttpClientNpAsyncPost postIn=new HttpClientNpAsyncPost(SynchronousActivity.this, item);
					postIn.execute(addInUrl);
				}
			}
		}
		else if(ls_UpInfo.get(i).getOperateType().equals("addreview"))
		{
			String conditionStr=" where ck_id='"+ls_UpInfo.get(i).getInfoId()+"' ";
    		SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(SynchronousActivity.this, Standard_CK_Table.class);
			final List<Standard_CK_Table> ls_Standard_CK_Table=opetaterStandard_CK_Table.SelectEntitysByCondition(conditionStr);
			opetaterStandard_CK_Table.close();
			
			String addScUrl=getBaseUrl()+"/appWebSave/upStandCheckReview";
			
			if(ls_Standard_CK_Table.size()>0)
			{
				HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_Standard_CK_Table.get(0));
	        	post.execute(addScUrl);
	        	
	        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
	        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
	        	opetaterUpInfo.close();
	        	
	        	String conditionStrIn=" where ck_id='"+ls_Standard_CK_Table.get(0).getCk_id()+"' ";
        		SqlOperate<Standard_CK_Table_Item> opetaterStandard_CK_Table_Item=
        				new SqlOperate<Standard_CK_Table_Item>(SynchronousActivity.this, Standard_CK_Table_Item.class);
				final List<Standard_CK_Table_Item> ls_Standard_CK_Table_Item=opetaterStandard_CK_Table_Item.SelectEntitysByCondition(conditionStrIn);
				opetaterStandard_CK_Table_Item.close();
				
				String addInUrl=getBaseUrl()+"/appWebSave/upStandCheckItemReview";
				
				for(Standard_CK_Table_Item item:ls_Standard_CK_Table_Item)
				{
					HttpClientNpAsyncPost postIn=new HttpClientNpAsyncPost(SynchronousActivity.this, item);
					postIn.execute(addInUrl);
				}
			}
		}
		else if(ls_UpInfo.get(i).getOperateType().equals("addsupervise"))
		{
			String conditionStr=" where ck_id='"+ls_UpInfo.get(i).getInfoId()+"' ";
    		SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(SynchronousActivity.this, Standard_CK_Table.class);
			final List<Standard_CK_Table> ls_Standard_CK_Table=opetaterStandard_CK_Table.SelectEntitysByCondition(conditionStr);
			opetaterStandard_CK_Table.close();
			
			String addScUrl=getBaseUrl()+"/appWebSave/upStandCheckSupervise";
			
			if(ls_Standard_CK_Table.size()>0)
			{
				HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_Standard_CK_Table.get(0));
	        	post.execute(addScUrl);
	        	
	        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
	        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
	        	opetaterUpInfo.close();
			}
		}
	}
	
	/**
	* @author yk 
	* @date 2015年10月16日 下午3:32:06 
	* @Title: upDailyCheck 
	* @Description: 专项检查数据上传
	* @param ls_UpInfo_dc
	* @param cusdialogt
	* @param minp    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void upDailyCheck(final List<UpInfo> ls_UpInfo, final int i) {
		if(ls_UpInfo.get(i).getOperateType().equals("add"))
		{
			String conditionStr=" where c_id='"+ls_UpInfo.get(i).getInfoId()+"' ";
    		SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(SynchronousActivity.this, DailyCheck.class);
			final List<DailyCheck> ls_DailyCheck=opetaterDailyCheck.SelectEntitysByCondition(conditionStr);
	        opetaterDailyCheck.close();
			
			String addDcUrl=getBaseUrl()+"/appWebSave/saveDailyCheck";
			
			if(ls_DailyCheck.size()>0)
			{
				HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_DailyCheck.get(0));
	        	post.execute(addDcUrl);
	        	
	        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
	        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
	        	opetaterUpInfo.close();
			}
		}
		else if(ls_UpInfo.get(i).getOperateType().equals("addreview"))
		{
			String conditionStr=" where c_id='"+ls_UpInfo.get(i).getInfoId()+"' ";
    		SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(SynchronousActivity.this, DailyCheck.class);
			final List<DailyCheck> ls_DailyCheck=opetaterDailyCheck.SelectEntitysByCondition(conditionStr);
	        opetaterDailyCheck.close();
			
			String addDcUrl=getBaseUrl()+"/appWebSave/upDailyCheckReview";
			
			if(ls_DailyCheck.size()>0)
			{
				HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_DailyCheck.get(0));
	        	post.execute(addDcUrl);
	        	
	        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
	        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
	        	opetaterUpInfo.close();
			}
		}
		else if(ls_UpInfo.get(i).getOperateType().equals("addsupervise"))
		{
			String conditionStr=" where c_id='"+ls_UpInfo.get(i).getInfoId()+"' ";
    		SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(SynchronousActivity.this, DailyCheck.class);
			final List<DailyCheck> ls_DailyCheck=opetaterDailyCheck.SelectEntitysByCondition(conditionStr);
	        opetaterDailyCheck.close();
			
			String addDcUrl=getBaseUrl()+"/appWebSave/upDailyCheckSupervise";
			
			if(ls_DailyCheck.size()>0)
			{
				HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_DailyCheck.get(0));
	        	post.execute(addDcUrl);
	        	
	        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
	        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
	        	opetaterUpInfo.close();
			}
		}
	}  
	
	/**
	* @author yk 
	* @date 2015年10月16日 下午3:32:17 
	* @Title: upSpecialCheck 
	* @Description: 对照标准数据上传
	* @param ls_UpInfo_dc
	* @param ls_UpInfo_sc
	* @param cusdialogt
	* @param minp    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void upSpecialCheck(final List<UpInfo> ls_UpInfo, final int i)
	{
		if(ls_UpInfo.get(i).getOperateType().equals("add"))
		{
			String conditionStr=" where c_id='"+ls_UpInfo.get(i).getInfoId()+"' ";
    		SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(SynchronousActivity.this, SpecialCheck.class);
			final List<SpecialCheck> ls_SpecialCheck=opetaterSpecialCheck.SelectEntitysByCondition(conditionStr);
			opetaterSpecialCheck.close();
			
			String addScUrl=getBaseUrl()+"/appWebSave/saveSpecialCheck";
			
			if(ls_SpecialCheck.size()>0)
			{
				HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_SpecialCheck.get(0));
	        	post.execute(addScUrl);
	        	
	        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
	        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
	        	opetaterUpInfo.close();
	        	
	        	String conditionStrIn=" where ck_id='"+ls_SpecialCheck.get(0).getC_id()+"' ";
        		SqlOperate<Inspector> opetaterInspector=new SqlOperate<Inspector>(SynchronousActivity.this, Inspector.class);
				final List<Inspector> ls_opetaterInspector=opetaterInspector.SelectEntitysByCondition(conditionStrIn);
				opetaterInspector.close();
				
				String addInUrl=getBaseUrl()+"/appWebSave/saveInspector";
				
				for(Inspector in:ls_opetaterInspector)
				{
					HttpClientNpAsyncPost postIn=new HttpClientNpAsyncPost(SynchronousActivity.this, in);
					postIn.execute(addInUrl);
				}
			}
			
		}
		else if(ls_UpInfo.get(i).getOperateType().equals("addreview"))
		{
			String conditionStr=" where c_id='"+ls_UpInfo.get(i).getInfoId()+"' ";
    		SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(SynchronousActivity.this, SpecialCheck.class);
			final List<SpecialCheck> ls_SpecialCheck=opetaterSpecialCheck.SelectEntitysByCondition(conditionStr);
			opetaterSpecialCheck.close();
			
			String addScUrl=getBaseUrl()+"/appWebSave/upSpecialCheckReview";
			
			if(ls_SpecialCheck.size()>0)
			{
				HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_SpecialCheck.get(0));
	        	post.execute(addScUrl);
	        	
	        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
	        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
	        	opetaterUpInfo.close();
	        	
			}
		}
		else if(ls_UpInfo.get(i).getOperateType().equals("addsupervise"))
		{
			String conditionStr=" where c_id='"+ls_UpInfo.get(i).getInfoId()+"' ";
    		SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(SynchronousActivity.this, SpecialCheck.class);
			final List<SpecialCheck> ls_SpecialCheck=opetaterSpecialCheck.SelectEntitysByCondition(conditionStr);
			opetaterSpecialCheck.close();
			
			String addScUrl=getBaseUrl()+"/appWebSave/upSpecialCheckSupervise";
			
			if(ls_SpecialCheck.size()>0)
			{
				HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_SpecialCheck.get(0));
	        	post.execute(addScUrl);
	        	
	        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
	        	opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
	        	opetaterUpInfo.close();
	        	
			}
		}
	}
	
	
	private void saveSelfStandCheckSupervise(final List<UpInfo> ls_UpInfo, int i) {
		
		SqlOperate<SelfStandCheck> opetaterSelfStandCheck=new SqlOperate<SelfStandCheck>(SynchronousActivity.this, SelfStandCheck.class);
		List<SelfStandCheck> ls_SelfStandCheck=opetaterSelfStandCheck.
				SelectEntitysByCondition(" where sc_uuid='"+ls_UpInfo.get(i).getInfoId()+"'");
		opetaterSelfStandCheck.close();
		
		String addEnUrl=getBaseUrl()+"/appWebSave/upSuperviseOfSelfStandCheck";
		
		if(ls_SelfStandCheck.size()>0)
		{
			HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_SelfStandCheck.get(0));
			post.execute(addEnUrl);
			
			SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
			opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
			opetaterUpInfo.close();
		}
	}
	
	/**
	* @author yk 
	* @date 2015年10月22日 上午11:59:15 
	* @Title: saveSelfCheckSupervise 
	* @Description: 上传隐患自查督促信息
	* @param ls_UpInfo
	* @param i    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void saveSelfCheckSupervise(final List<UpInfo> ls_UpInfo, int i) {
		SqlOperate<SelfCheck> opetaterSelfCheck=new SqlOperate<SelfCheck>(SynchronousActivity.this, SelfCheck.class);
		List<SelfCheck> ls_SelfCheck=opetaterSelfCheck.
				SelectEntitysByCondition(" where sc_uuId='"+ls_UpInfo.get(i).getInfoId()+"'");
		opetaterSelfCheck.close();
		
		String addEnUrl=getBaseUrl()+"/appWebSave/upSuperviseOfSelfCheck";
		
		if(ls_SelfCheck.size()>0)
		{
			HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_SelfCheck.get(0));
			post.execute(addEnUrl);
			
			SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
			opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
			opetaterUpInfo.close();
		}
	}
	
	/**
	* @author yk 
	* @date 2015年10月22日 上午11:58:06 
	* @Title: saveSimple 
	* @Description: 上传企业信息
	* @param ls_UpInfo
	* @param i    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void saveSimple(final List<UpInfo> ls_UpInfo, int i) {
		SqlOperate<SimpleEnterprise> opetaterSimpleEnterprise=new SqlOperate<SimpleEnterprise>(SynchronousActivity.this, SimpleEnterprise.class);
		List<SimpleEnterprise> ls_opetaterSimpleEnterprise=opetaterSimpleEnterprise.
				SelectEntitysByCondition(" where e_Id='"+ls_UpInfo.get(i).getInfoId()+"'");
		opetaterSimpleEnterprise.close();
		
		String addEnUrl=getBaseUrl()+"/appWebSave/saveEnterprise";
		
		if(ls_opetaterSimpleEnterprise.size()>0)
		{
			HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(SynchronousActivity.this, ls_opetaterSimpleEnterprise.get(0));
			post.execute(addEnUrl);
			
			SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
			opetaterUpInfo.DeleteContent(ls_UpInfo.get(i).getU_Id());
			opetaterUpInfo.close();
		}
	}  
	
	private void allinfo_down() {
		SqlOperate<UpInfo> opetaterInfo=new SqlOperate<UpInfo>(SynchronousActivity.this, UpInfo.class);
		List<UpInfo> ls_UpInfo=opetaterInfo.SelectEntitys();
		opetaterInfo.close();
		if(ls_UpInfo.size()>0)
		{
			final CustomDialog cusdialog=new CustomDialog(SynchronousActivity.this);
			cusdialog.setViewAndAlpha(R.layout.dialog_custom,0);
			cusdialog.setText(R.id.tv_dg_title, "提示");
			cusdialog.setText(R.id.tv_dg_context, "当前有数据未提交,确认要更新吗?");
			cusdialog.findViewById(R.id.but_dg_positiveButton).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					downInfo();
					cusdialog.dismiss();
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
			Toast.makeText(SynchronousActivity.this, "您还有数据没有提交", Toast.LENGTH_SHORT).show();
		}
		else
		{
			downInfo();
		}
	}
	
	
	
	private void downInfo() {
		AllDataGetAsyncOOM AllDataGetAsync=new AllDataGetAsyncOOM(SynchronousActivity.this);
		String baseURL=getBaseUrl();
		if(baseURL!=null)
		{
			User user=getCurrentUser();
			
			String compamyUrl=getBaseUrl()+"/appWebGet/getAllEnterprise";
			String inDustryUrl=getBaseUrl()+"/appWebGet/getAllIndustry";
			String CountyAreaUrl=getBaseUrl()+"/appWebGet/getAllCountyArea";
			String CountyDepartmentUrl=getBaseUrl()+"/appWebGet/getAllCountyDepartment";
			String SelfCheckUrl=getBaseUrl()+"/appWebGet/getAllSelfCheck";
			String SelfStandCheckUrl=getBaseUrl()+"/appWebGet/getAllSelfStandCheck";
			String StandcheckDetailUrl=getBaseUrl()+"/appWebGet/getAllStandCheckDetail";
			String UserUrl=getBaseUrl()+"/appWebGet/getAllUser";
			String PublishInfoUrl=getBaseUrl()+"/appWebGet/getAllPublishInfo";
			String ExcelCellUrl=getBaseUrl()+"/appWebGet/getAllExcelCell";
			String HiddenStandardFileUrl=getBaseUrl()+"/appWebGet/getAllStandardFile";
			
			String DailyCheckOfUserUrl=getBaseUrl()+"/appWebGet/getDailyCheckOfUser?ck_userId="+user.getUserId();
			String SpecialCheckOfUserUrl=getBaseUrl()+"/appWebGet/getSpecialCheckOfUser?ck_userId="+user.getUserId();
			String InspectorOfUserUrl=getBaseUrl()+"/appWebGet/getInspectorOfUser?ck_userId="+user.getUserId();
			String StandardCkOfUserUrl=getBaseUrl()+"/appWebGet/getStandardCkOfUser?ck_userId="+user.getUserId();
			String StandardCkItemOfUserUrl=getBaseUrl()+"/appWebGet/getStandardCkItemOfUser?ck_userId="+user.getUserId();
			
			String ChemicalsUrl=getBaseUrl()+"/appWebGet/getAllChemicals";
			String CheckConditionItemUrl=getBaseUrl()+"/appWebGet/getCheckConditionItemOfUser?ck_userId="+user.getUserId();
			
			AllDataGetAsync.execute(compamyUrl,inDustryUrl,
					CountyAreaUrl,CountyDepartmentUrl,SelfCheckUrl,
					SelfStandCheckUrl,StandcheckDetailUrl,UserUrl,
					PublishInfoUrl,ExcelCellUrl,HiddenStandardFileUrl,
					DailyCheckOfUserUrl,SpecialCheckOfUserUrl,InspectorOfUserUrl,
					StandardCkOfUserUrl,StandardCkItemOfUserUrl,ChemicalsUrl,CheckConditionItemUrl);
		}
	};
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Toast.makeText(SynchronousActivity.this, "更新上传完成", Toast.LENGTH_SHORT).show();
		}
		
	};
	
    /**
    * @author yk 
    * @date 2015年8月6日 上午10:18:24 
    * @Title: initComponent 
    * @Description: 初始化用户组件 
    * @return void    返回类型 
    * @throws
     */
	private void initComponent()
	{
		topbar_hidden_check=(TopBarView )findViewById(R.id.topbar_hidden_check);
		
		but_allinfo_downup=(Button)findViewById(R.id.but_allinfo_downup);
		but_allinfo_down=(Button)findViewById(R.id.but_allinfo_down);
		but_allinfo_up=(Button)findViewById(R.id.but_allinfo_up);
		
		but_allinfo_down.setKeepScreenOn(true);
    }

}


