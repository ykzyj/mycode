package com.sunnyit.enforcement.action;

import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dataOperate.dOperate;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.synchronous.model.UpInfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenSuperviseAddActivity extends BaseActivity {
	
	private TopBarView topbar_check_supervise;
	private EditText et_check_supervise;
	
	private String addtype;
	private String ID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_supervise_add);
		
		initComponent();
		
		addtype=getIntent().getStringExtra("addtype");
		ID=getIntent().getStringExtra("ID");
		
		if(addtype!=null)
		{
			if(addtype.equals("Daily"))
			{
				topbar_check_supervise.setTitle("日常隐患检查督促信息添加");
			}
			else if(addtype.equals("Special"))
			{
				topbar_check_supervise.setTitle("专项检查督促信息添加");
			}
			else if(addtype.equals("StandCheck"))
			{
				topbar_check_supervise.setTitle("对照标准检查督促信息添加");
			}
		}
		
		topbar_check_supervise.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				String supervise=et_check_supervise.getText().toString().trim();
				if("".equals(supervise))
				{
					Toast.makeText(HiddenSuperviseAddActivity.this, "督促信息不能为空！", Toast.LENGTH_SHORT).show();
				}
				else
				{
					UpInfo info=new UpInfo();
					
					if(addtype.equals("Daily"))
					{
						String sqlStr=" update DailyCheck set ck_superviseOpinion='"+supervise+"',"
								+ "ck_supervisePerson='"+getCurrentUser().getDepartmentName()+"',"
								+ "ck_superviseTime='"+dOperate.getCurrentDate()+"'  where c_id='"+ID+"' ";
						SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(HiddenSuperviseAddActivity.this, DailyCheck.class);
						opetaterDailyCheck.execMySQL(sqlStr);
				        opetaterDailyCheck.close();
				        
				        info.setInfoTable("DailyCheck");
				        
					}
					else if(addtype.equals("Special"))
					{
						String sqlStr=" update SpecialCheck set ck_duinfo='"+supervise+"' where c_id='"+ID+"' ";
						SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(HiddenSuperviseAddActivity.this, SpecialCheck.class);
						opetaterSpecialCheck.execMySQL(sqlStr);
						opetaterSpecialCheck.close();
				        
				        info.setInfoTable("SpecialCheck");
					}
					else if(addtype.equals("StandCheck"))
					{
						String sqlStr=" update Standard_CK_Table set ck_duinfo='"+supervise+"',ck_duTime='"+dOperate.getCurrentDate()+"'  where ck_id='"+ID+"' ";
						SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(HiddenSuperviseAddActivity.this, Standard_CK_Table.class);
						opetaterStandard_CK_Table.execMySQL(sqlStr);
						opetaterStandard_CK_Table.close();
				        
				        info.setInfoTable("StandCheck");
					}
					
					info.setU_Id(UUID.randomUUID().toString());
					info.setInfoId(ID);
					info.setOperateType("addsupervise");
					info.setRemark("");
					
			    	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenSuperviseAddActivity.this, UpInfo.class);
			    	opetaterUpInfo.saveContent(info);
			    	opetaterUpInfo.close();
					//本地保存
			    	Toast.makeText(HiddenSuperviseAddActivity.this, "督促信息本地保存成功！", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_check_supervise=(TopBarView) findViewById(R.id.topbar_check_supervise);
		et_check_supervise=(EditText) findViewById(R.id.et_check_supervise);
	}
	
}


