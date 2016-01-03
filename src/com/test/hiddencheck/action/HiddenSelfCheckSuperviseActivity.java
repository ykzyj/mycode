package com.sunnyit.hiddencheck.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.async.HttpClientAsyncPost;
import com.sunnyit.common.async.HttpClientAsyncPost.onHttpClientReturnListener;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.hiddencheck.model.SelfCheck;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenSelfCheckSuperviseActivity extends BaseActivity {
	
	private TopBarView topbar_selfcheck_add;
	
	private EditText sc_id,sc_checkTime,sc_checkingDepartment,sc_inspector;
	private EditText sc_checkedDepartment,sc_checkedJob,sc_superviseOpinion;
	
	private SelfCheck sc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_seftcheck_supervise);
		
		
		
		sc = (SelfCheck) getIntent().getSerializableExtra("SelfCheck"); 
		
		initComponent();
		
		topbar_selfcheck_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				//提交督促信息
				
				if(sc_superviseOpinion.getText().toString().equals(""))
				{
					Toast.makeText(HiddenSelfCheckSuperviseActivity.this, "督促信息不能为空！", Toast.LENGTH_SHORT).show();
				}
				else
				{
					User user=getCurrentUser();
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					
					sc.setSc_superviseOpinion(sc_superviseOpinion.getText().toString());
					sc.setSc_superviseDept(user.getDepartmentName());
					sc.setSc_superviseTime(df.format(new Date()));
					
					
					String wifi=sp.getString("wifi", null);
					if(wifi.equals("yes"))
					{
						//本地保存
						SqlOperate<SelfCheck> opetaterSelfCheck=new SqlOperate<SelfCheck>(HiddenSelfCheckSuperviseActivity.this, SelfCheck.class);
						opetaterSelfCheck.upContent(sc);
						opetaterSelfCheck.close();
						
						UpInfo info=new UpInfo();
						info.setU_Id(UUID.randomUUID().toString());
						info.setInfoTable("SelfCheck");
						info.setInfoId(sc.getSc_uuId());
						info.setOperateType("update");
						info.setRemark("");
						
				    	SqlOperate<UpInfo> opetaterUpInfo=new SqlOperate<UpInfo>(HiddenSelfCheckSuperviseActivity.this, UpInfo.class);
				    	opetaterUpInfo.saveContent(info);
				    	opetaterUpInfo.close();
						
						Toast.makeText(HiddenSelfCheckSuperviseActivity.this, "本地保存成功", Toast.LENGTH_SHORT).show();
				    	finish();
					}
					else
					{
						//直接上传
						String addEnUrl=getBaseUrl()+"/appWebSave/upSuperviseOfSelfCheck";
						
						HttpClientAsyncPost post=new HttpClientAsyncPost(HiddenSelfCheckSuperviseActivity.this, sc);
			        	post.execute(addEnUrl);
			        	post.setHttpClientReturnListener(new onHttpClientReturnListener() {
							
							@Override
							public void returnPostExecute(boolean isSuccess, String msg, List<Object> lo) {
								// TODO Auto-generated method stub
								Toast.makeText(HiddenSelfCheckSuperviseActivity.this, msg, Toast.LENGTH_SHORT).show();
								if(isSuccess)
								{
									finish();
								}
							}
						});
					}
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
		topbar_selfcheck_add=(TopBarView) findViewById(R.id.topbar_selfcheck_add);
		
		sc_id=(EditText) findViewById(R.id.sc_id);
		sc_checkTime=(EditText) findViewById(R.id.sc_checkTime);
		sc_checkingDepartment=(EditText) findViewById(R.id.sc_checkingDepartment);
		sc_inspector=(EditText) findViewById(R.id.sc_inspector);
		sc_checkedDepartment=(EditText) findViewById(R.id.sc_checkedDepartment);
		sc_checkedJob=(EditText) findViewById(R.id.sc_checkedJob);
		sc_superviseOpinion=(EditText) findViewById(R.id.sc_superviseOpinion);
		
		sc_id.setText(sc.getSc_id());
		sc_checkTime.setText(sc.getSc_checkTime());
		sc_checkingDepartment.setText(sc.getSc_checkingDepartment());
		sc_inspector.setText(sc.getSc_inspector());
		sc_checkedDepartment.setText(sc.getSc_checkedDepartment());
		sc_checkedJob.setText(sc.getSc_checkedJob());
		
		topbar_selfcheck_add.setTitle("企业隐患督促");
		topbar_selfcheck_add.setRightButText("提交");
	}
	
}


