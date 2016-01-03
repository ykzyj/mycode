package com.sunnyit.enforcement.action;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.ActivityCollector;
import com.sunnyit.common.async.AllDataGetAsync;
import com.sunnyit.common.async.CheckDataOfUserGetAsync;
import com.sunnyit.common.async.HttpClientNpAsyncPost;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.model.CheckConditionItem;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.enforcement.model.Inspector;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;
import com.sunnyit.hiddencheck.model.SelfCheck;
import com.sunnyit.synchronous.action.SynchronousActivity;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenGovernChoiceTabActivity extends TabActivity {

	
	private TopBarView topbar_en_tab_choice;
	private TabHost tabHost;
	RadioGroup radioGroup;
	private SharedPreferences sp;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.en_tab_choice);
		ActivityCollector.addActivity(this);
		
		sp = this.getSharedPreferences("SP", MODE_PRIVATE);
		user=new User();
		String UserId=sp.getString("UserId", null);
		if(UserId!=null)
		{
			SqlOperate<User> uop=new SqlOperate<User>(this, User.class);
			user=uop.SelectEntityByID(UserId);
			uop.close();
		}
		
		initComponent();
		
		topbar_en_tab_choice.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				SynchronousEnforceMentdata();
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		topbar_en_tab_choice.setRightButText("同步");
		
        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        intent=new Intent().setClass(this, ReviewCheckChoiceActivity.class);
        spec=tabHost.newTabSpec("检查信息").setIndicator("检查信息").setContent(intent);
        tabHost.addTab(spec);

        intent=new Intent().setClass(this, YearMounthCheckActivity.class);
        spec=tabHost.newTabSpec("检查统计").setIndicator("检查统计").setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(0);
        
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				switch (checkedId) 
				{
					case R.id.rt_en_review:
						tabHost.setCurrentTabByTag("检查信息");
						break;
					case R.id.rt_en_check:
						tabHost.setCurrentTabByTag("检查统计");
						break;
					default:
						break;
				}
			}
		});
        
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_en_tab_choice=(TopBarView) findViewById(R.id.topbar_en_tab_choice);
		radioGroup=(RadioGroup) this.findViewById(R.id.en_tab_group);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	
	private void SynchronousEnforceMentdata() {
		
		upCheckInfo();
	}
	
	private void downCheckInfo() {
		// TODO Auto-generated method stub
		CheckDataOfUserGetAsync checkDataOfUserGetAsync=new CheckDataOfUserGetAsync(HiddenGovernChoiceTabActivity.this);
		String DailyCheckOfUserUrl=getBaseUrl()+"/appWebGet/getDailyCheckOfUser?ck_userId="+user.getUserId();
		String SpecialCheckOfUserUrl=getBaseUrl()+"/appWebGet/getSpecialCheckOfUser?ck_userId="+user.getUserId();
		String InspectorOfUserUrl=getBaseUrl()+"/appWebGet/getInspectorOfUser?ck_userId="+user.getUserId();
		String StandardCkOfUserUrl=getBaseUrl()+"/appWebGet/getStandardCkOfUser?ck_userId="+user.getUserId();
		String StandardCkItemOfUserUrl=getBaseUrl()+"/appWebGet/getStandardCkItemOfUser?ck_userId="+user.getUserId();
		String CheckConditionItemOfUserUrl=getBaseUrl()+"/appWebGet/getCheckConditionItemOfUser?ck_userId="+user.getUserId();
		checkDataOfUserGetAsync.execute(DailyCheckOfUserUrl,SpecialCheckOfUserUrl,
				InspectorOfUserUrl,StandardCkOfUserUrl,StandardCkItemOfUserUrl,CheckConditionItemOfUserUrl);
	}

	private void upCheckInfo() {
		// TODO Auto-generated method stub
		String conditionStr_dc=" where InfoTable='DailyCheck' ";
		SqlOperate<UpInfo> opetaterInfo_dc=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		final List<UpInfo> ls_UpInfo_dc=opetaterInfo_dc.SelectEntitysByCondition(conditionStr_dc);
		opetaterInfo_dc.close();
		
		String conditionStr_sc=" where InfoTable='SpecialCheck' ";
		SqlOperate<UpInfo> opetaterInfo_sc=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		final List<UpInfo> ls_UpInfo_sc=opetaterInfo_sc.SelectEntitysByCondition(conditionStr_sc);
		opetaterInfo_sc.close();
		
		String conditionStr_st=" where InfoTable='Standard_CK_Table' ";
		SqlOperate<UpInfo> opetaterInfo_st=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		final List<UpInfo> ls_UpInfo_st=opetaterInfo_st.SelectEntitysByCondition(conditionStr_st);
		opetaterInfo_st.close();
		
		String conditionStr_Simple=" where InfoTable='SimpleEnterprise' ";
		SqlOperate<UpInfo> opetaterInfo_Simple=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		final List<UpInfo> ls_UpInfo_Simple=opetaterInfo_Simple.SelectEntitysByCondition(conditionStr_Simple);
		opetaterInfo_Simple.close();
		
		if((ls_UpInfo_dc.size()+ls_UpInfo_sc.size()+ls_UpInfo_st.size()+ls_UpInfo_Simple.size())==0)
		{
			Toast.makeText(HiddenGovernChoiceTabActivity.this, "当前没有数据需要提交", Toast.LENGTH_SHORT).show();
			downCheckInfo();
		}
		else
		{
			final CustomDialog cusdialogt=new CustomDialog(HiddenGovernChoiceTabActivity.this);
			cusdialogt.setViewAndAlpha(R.layout.dialog_custom,0);
			cusdialogt.initProgressBar(R.id.id_progressbarh);
			cusdialogt.setOutCancel(false);
			cusdialogt.setText(R.id.tv_dg_title, "数据上传中");
			cusdialogt.findViewById(R.id.rel_cb_divider).setVisibility(View.GONE);
			cusdialogt.findViewById(R.id.lin_but_divider).setVisibility(View.GONE);
			cusdialogt.show();
			
			final float minp=100.0f/(ls_UpInfo_dc.size()+ls_UpInfo_sc.size()+ls_UpInfo_st.size());
			new Thread(new Runnable(){  
	            public void run(){
	            	
	            	upDailyCheck(ls_UpInfo_dc, cusdialogt, minp);
	            	
	            	upSpecialCheck(ls_UpInfo_dc, ls_UpInfo_sc, cusdialogt, minp);
	            	
	            	upStandCheck(ls_UpInfo_dc, ls_UpInfo_sc, ls_UpInfo_st, cusdialogt, minp);
	            	
	            	upSimple(ls_UpInfo_dc, ls_UpInfo_sc, ls_UpInfo_st, ls_UpInfo_Simple, cusdialogt, minp);
	            	
	            	String conditionStr="  where InfoTable='DailyCheck' or InfoTable='SpecialCheck' "
	            			+ " or InfoTable='Standard_CK_Table' or InfoTable='SimpleEnterprise' ";
	            	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		        	opetaterUpInfo.DeleteByCondition(conditionStr);
		        	opetaterUpInfo.close();
		        	
	            	cusdialogt.dismiss();
	            	handler.sendEmptyMessage(0);
	            }

	        }).start();  
		}
	}
	
	/**
	* @author yk 
	* @date 2015年10月17日 上午10:29:55 
	* @Title: upSimple 
	* @Description: 企业信息上传
	* @param ls_UpInfo_dc
	* @param ls_UpInfo_sc
	* @param ls_UpInfo_st
	* @param ls_UpInfo_Simple
	* @param cusdialogt
	* @param minp    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void upSimple(final List<UpInfo> ls_UpInfo_dc, final List<UpInfo> ls_UpInfo_sc,
			final List<UpInfo> ls_UpInfo_st, final List<UpInfo> ls_UpInfo_Simple,
			final CustomDialog cusdialogt, final float minp) {
		for(int i=0;i<ls_UpInfo_Simple.size();i++)
		{
    		if(ls_UpInfo_Simple.get(i).getOperateType().equals("add"))
    		{
    			SqlOperate<SimpleEnterprise> opetaterSimpleEnterprise=new SqlOperate<SimpleEnterprise>(HiddenGovernChoiceTabActivity.this, SimpleEnterprise.class);
				List<SimpleEnterprise> ls_opetaterSimpleEnterprise=opetaterSimpleEnterprise.
						SelectEntitysByCondition(" where e_Id='"+ls_UpInfo_Simple.get(i).getInfoId()+"'");
				opetaterSimpleEnterprise.close();
				
				String addEnUrl=getBaseUrl()+"/appWebSave/saveEnterprise";
				
				if(ls_opetaterSimpleEnterprise.size()>0)
				{
					HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, ls_opetaterSimpleEnterprise.get(0));
		        	post.execute(addEnUrl);
		        	
		        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		        	opetaterUpInfo.DeleteContent(ls_UpInfo_Simple.get(i).getU_Id());
		        	opetaterUpInfo.close();
				}
    		}
    		
			cusdialogt.setProgress((int)(minp*(i+1+ls_UpInfo_dc.size()+ls_UpInfo_sc.size()+ls_UpInfo_st.size())));
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
	private void upStandCheck(final List<UpInfo> ls_UpInfo_dc, final List<UpInfo> ls_UpInfo_sc,
			final List<UpInfo> ls_UpInfo_st, final CustomDialog cusdialogt, final float minp) {
		for(int i=0;i<ls_UpInfo_st.size();i++)
		{
    		if(ls_UpInfo_st.get(i).getOperateType().equals("add"))
    		{
    			String conditionStr=" where ck_id='"+ls_UpInfo_st.get(i).getInfoId()+"' ";
        		SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(HiddenGovernChoiceTabActivity.this, Standard_CK_Table.class);
				final List<Standard_CK_Table> ls_Standard_CK_Table=opetaterStandard_CK_Table.SelectEntitysByCondition(conditionStr);
				opetaterStandard_CK_Table.close();
				
				String addScUrl=getBaseUrl()+"/appWebSave/saveStandCheck";
				
				if(ls_Standard_CK_Table.size()>0)
				{
					HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, ls_Standard_CK_Table.get(0));
		        	post.execute(addScUrl);
		        	
		        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		        	opetaterUpInfo.DeleteContent(ls_UpInfo_st.get(i).getU_Id());
		        	opetaterUpInfo.close();
		        	
		        	String conditionStrIn=" where ck_id='"+ls_Standard_CK_Table.get(0).getCk_id()+"' ";
            		SqlOperate<Standard_CK_Table_Item> opetaterStandard_CK_Table_Item=
            				new SqlOperate<Standard_CK_Table_Item>(HiddenGovernChoiceTabActivity.this, Standard_CK_Table_Item.class);
    				final List<Standard_CK_Table_Item> ls_Standard_CK_Table_Item=opetaterStandard_CK_Table_Item.SelectEntitysByCondition(conditionStrIn);
    				opetaterStandard_CK_Table_Item.close();
					
					String addInUrl=getBaseUrl()+"/appWebSave/saveStandCheckItem";
					
					for(Standard_CK_Table_Item item:ls_Standard_CK_Table_Item)
					{
						HttpClientNpAsyncPost postIn=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, item);
						postIn.execute(addInUrl);
					}
				}
    		}
    		else if(ls_UpInfo_st.get(i).getOperateType().equals("addreview"))
    		{
    			String conditionStr=" where ck_id='"+ls_UpInfo_st.get(i).getInfoId()+"' ";
        		SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(HiddenGovernChoiceTabActivity.this, Standard_CK_Table.class);
				final List<Standard_CK_Table> ls_Standard_CK_Table=opetaterStandard_CK_Table.SelectEntitysByCondition(conditionStr);
				opetaterStandard_CK_Table.close();
				
				String addScUrl=getBaseUrl()+"/appWebSave/upStandCheckReview";
				
				if(ls_Standard_CK_Table.size()>0)
				{
					HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, ls_Standard_CK_Table.get(0));
		        	post.execute(addScUrl);
		        	
		        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		        	opetaterUpInfo.DeleteContent(ls_UpInfo_st.get(i).getU_Id());
		        	opetaterUpInfo.close();
		        	
		        	String conditionStrIn=" where ck_id='"+ls_Standard_CK_Table.get(0).getCk_id()+"' ";
            		SqlOperate<Standard_CK_Table_Item> opetaterStandard_CK_Table_Item=
            				new SqlOperate<Standard_CK_Table_Item>(HiddenGovernChoiceTabActivity.this, Standard_CK_Table_Item.class);
    				final List<Standard_CK_Table_Item> ls_Standard_CK_Table_Item=opetaterStandard_CK_Table_Item.SelectEntitysByCondition(conditionStrIn);
    				opetaterStandard_CK_Table_Item.close();
					
					String addInUrl=getBaseUrl()+"/appWebSave/upStandCheckItemReview";
					
					for(Standard_CK_Table_Item item:ls_Standard_CK_Table_Item)
					{
						HttpClientNpAsyncPost postIn=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, item);
						postIn.execute(addInUrl);
					}
				}
    		}
    		else if(ls_UpInfo_st.get(i).getOperateType().equals("addsupervise"))
    		{
    			String conditionStr=" where ck_id='"+ls_UpInfo_st.get(i).getInfoId()+"' ";
        		SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(HiddenGovernChoiceTabActivity.this, Standard_CK_Table.class);
				final List<Standard_CK_Table> ls_Standard_CK_Table=opetaterStandard_CK_Table.SelectEntitysByCondition(conditionStr);
				opetaterStandard_CK_Table.close();
				
				String addScUrl=getBaseUrl()+"/appWebSave/upStandCheckSupervise";
				
				if(ls_Standard_CK_Table.size()>0)
				{
					HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, ls_Standard_CK_Table.get(0));
		        	post.execute(addScUrl);
		        	
		        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		        	opetaterUpInfo.DeleteContent(ls_UpInfo_st.get(i).getU_Id());
		        	opetaterUpInfo.close();
				}
    		}
    		
			cusdialogt.setProgress((int)(minp*(i+1+ls_UpInfo_dc.size()+ls_UpInfo_sc.size())));
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
	private void upDailyCheck(final List<UpInfo> ls_UpInfo_dc, final CustomDialog cusdialogt,
			final float minp) {
		for(int i=0;i<ls_UpInfo_dc.size();i++)
		{
    		if(ls_UpInfo_dc.get(i).getOperateType().equals("add"))
    		{
    			String conditionStr=" where c_id='"+ls_UpInfo_dc.get(i).getInfoId()+"' ";
        		SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(HiddenGovernChoiceTabActivity.this, DailyCheck.class);
				final List<DailyCheck> ls_DailyCheck=opetaterDailyCheck.SelectEntitysByCondition(conditionStr);
		        opetaterDailyCheck.close();
				
				String addDcUrl=getBaseUrl()+"/appWebSave/saveDailyCheck";
				
				if(ls_DailyCheck.size()>0)
				{
					HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, ls_DailyCheck.get(0));
		        	post.execute(addDcUrl);
		        	
		        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		        	opetaterUpInfo.DeleteContent(ls_UpInfo_dc.get(i).getU_Id());
		        	opetaterUpInfo.close();
		        	
		        	String conditionStrItem=" where checkCkId='"+ls_DailyCheck.get(0).getC_id()+"' ";
            		SqlOperate<CheckConditionItem> opetaterCheckConditionItem=
            				new SqlOperate<CheckConditionItem>(HiddenGovernChoiceTabActivity.this, CheckConditionItem.class);
    				final List<CheckConditionItem> ls_CheckConditionItem=opetaterCheckConditionItem.SelectEntitysByCondition(conditionStrItem);
    				opetaterCheckConditionItem.close();
					
					String additemUrl=getBaseUrl()+"/appWebSave/saveCheckConditionItem";
					
					for(CheckConditionItem item:ls_CheckConditionItem)
					{
						HttpClientNpAsyncPost postIn=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, item);
						postIn.execute(additemUrl);
					}
				}
    		}
    		else if(ls_UpInfo_dc.get(i).getOperateType().equals("addreview"))
    		{
    			String conditionStr=" where c_id='"+ls_UpInfo_dc.get(i).getInfoId()+"' ";
        		SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(HiddenGovernChoiceTabActivity.this, DailyCheck.class);
				final List<DailyCheck> ls_DailyCheck=opetaterDailyCheck.SelectEntitysByCondition(conditionStr);
		        opetaterDailyCheck.close();
				
				String addDcUrl=getBaseUrl()+"/appWebSave/upDailyCheckReview";
				
				if(ls_DailyCheck.size()>0)
				{
					HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, ls_DailyCheck.get(0));
		        	post.execute(addDcUrl);
		        	
		        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		        	opetaterUpInfo.DeleteContent(ls_UpInfo_dc.get(i).getU_Id());
		        	opetaterUpInfo.close();
				}
    		}
    		else if(ls_UpInfo_dc.get(i).getOperateType().equals("addsupervise"))
    		{
    			String conditionStr=" where c_id='"+ls_UpInfo_dc.get(i).getInfoId()+"' ";
        		SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(HiddenGovernChoiceTabActivity.this, DailyCheck.class);
				final List<DailyCheck> ls_DailyCheck=opetaterDailyCheck.SelectEntitysByCondition(conditionStr);
		        opetaterDailyCheck.close();
				
				String addDcUrl=getBaseUrl()+"/appWebSave/upDailyCheckSupervise";
				
				if(ls_DailyCheck.size()>0)
				{
					HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, ls_DailyCheck.get(0));
		        	post.execute(addDcUrl);
		        	
		        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		        	opetaterUpInfo.DeleteContent(ls_UpInfo_dc.get(i).getU_Id());
		        	opetaterUpInfo.close();
				}
    		}
			cusdialogt.setProgress((int)(minp*(i+1)));
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
	private void upSpecialCheck(final List<UpInfo> ls_UpInfo_dc, final List<UpInfo> ls_UpInfo_sc,
			final CustomDialog cusdialogt, final float minp) {
		for(int i=0;i<ls_UpInfo_sc.size();i++)
		{
    		if(ls_UpInfo_sc.get(i).getOperateType().equals("add"))
    		{
    			String conditionStr=" where c_id='"+ls_UpInfo_sc.get(i).getInfoId()+"' ";
        		SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(HiddenGovernChoiceTabActivity.this, SpecialCheck.class);
				final List<SpecialCheck> ls_SpecialCheck=opetaterSpecialCheck.SelectEntitysByCondition(conditionStr);
				opetaterSpecialCheck.close();
				
				String addScUrl=getBaseUrl()+"/appWebSave/saveSpecialCheck";
				
				if(ls_SpecialCheck.size()>0)
				{
					HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, ls_SpecialCheck.get(0));
		        	post.execute(addScUrl);
		        	
		        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		        	opetaterUpInfo.DeleteContent(ls_UpInfo_sc.get(i).getU_Id());
		        	opetaterUpInfo.close();
		        	
		        	String conditionStrIn=" where ck_id='"+ls_SpecialCheck.get(0).getC_id()+"' ";
            		SqlOperate<Inspector> opetaterInspector=new SqlOperate<Inspector>(HiddenGovernChoiceTabActivity.this, Inspector.class);
    				final List<Inspector> ls_opetaterInspector=opetaterInspector.SelectEntitysByCondition(conditionStrIn);
    				opetaterInspector.close();
					
					String addInUrl=getBaseUrl()+"/appWebSave/saveInspector";
					
					for(Inspector in:ls_opetaterInspector)
					{
						HttpClientNpAsyncPost postIn=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, in);
						postIn.execute(addInUrl);
					}
					
					String conditionStrItem=" where checkCkId='"+ls_SpecialCheck.get(0).getC_id()+"' ";
            		SqlOperate<CheckConditionItem> opetaterCheckConditionItem=
            				new SqlOperate<CheckConditionItem>(HiddenGovernChoiceTabActivity.this, CheckConditionItem.class);
    				final List<CheckConditionItem> ls_CheckConditionItem=opetaterCheckConditionItem.SelectEntitysByCondition(conditionStrItem);
    				opetaterCheckConditionItem.close();
					
					String additemUrl=getBaseUrl()+"/appWebSave/saveCheckConditionItem";
					
					for(CheckConditionItem item:ls_CheckConditionItem)
					{
						HttpClientNpAsyncPost postIn=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, item);
						postIn.execute(additemUrl);
					}
				}
				
    		}
    		else if(ls_UpInfo_sc.get(i).getOperateType().equals("addreview"))
    		{
    			String conditionStr=" where c_id='"+ls_UpInfo_sc.get(i).getInfoId()+"' ";
        		SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(HiddenGovernChoiceTabActivity.this, SpecialCheck.class);
				final List<SpecialCheck> ls_SpecialCheck=opetaterSpecialCheck.SelectEntitysByCondition(conditionStr);
				opetaterSpecialCheck.close();
				
				String addScUrl=getBaseUrl()+"/appWebSave/upSpecialCheckReview";
				
				if(ls_SpecialCheck.size()>0)
				{
					HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, ls_SpecialCheck.get(0));
		        	post.execute(addScUrl);
		        	
		        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		        	opetaterUpInfo.DeleteContent(ls_UpInfo_sc.get(i).getU_Id());
		        	opetaterUpInfo.close();
		        	
				}
				
    		}
    		else if(ls_UpInfo_sc.get(i).getOperateType().equals("addsupervise"))
    		{
    			String conditionStr=" where c_id='"+ls_UpInfo_sc.get(i).getInfoId()+"' ";
        		SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(HiddenGovernChoiceTabActivity.this, SpecialCheck.class);
				final List<SpecialCheck> ls_SpecialCheck=opetaterSpecialCheck.SelectEntitysByCondition(conditionStr);
				opetaterSpecialCheck.close();
				
				String addScUrl=getBaseUrl()+"/appWebSave/upSpecialCheckSupervise";
				
				if(ls_SpecialCheck.size()>0)
				{
					HttpClientNpAsyncPost post=new HttpClientNpAsyncPost(HiddenGovernChoiceTabActivity.this, ls_SpecialCheck.get(0));
		        	post.execute(addScUrl);
		        	
		        	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenGovernChoiceTabActivity.this, UpInfo.class);
		        	opetaterUpInfo.DeleteContent(ls_UpInfo_sc.get(i).getU_Id());
		        	opetaterUpInfo.close();
		        	
				}
				
    		}
    		
			cusdialogt.setProgress((int)(minp*(i+1+ls_UpInfo_dc.size())));
		}
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Toast.makeText(HiddenGovernChoiceTabActivity.this, "数据上传完成", Toast.LENGTH_SHORT).show();
			downCheckInfo();
		}
		
	};
	
	protected String getBaseUrl() {
		String url=sp.getString("url", null);
		if(url!=null)
		{
			url="http://"+url;
		}
		return url;
	}

}





