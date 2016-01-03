package com.sunnyit.hiddencheck.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.company.data.EnterpriseAdapter;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.action.HiddenGovernListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015年9月7日 下午3:27:12 
* @version V1.0   
*/
public class HiddenCompanyActivity extends BaseActivity {
	
	private TopBarView topbar_hidden_check;
	
	private ListView mlistview;
	private List<SimpleEnterprise> lse;
	
	private EnterpriseAdapter mAdapter;
	private int img=R.drawable.qita96;
	
	private String mHiddenType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_list);
		
		Intent intent=getIntent();
		mHiddenType=intent.getStringExtra("HiddenType");
		
		initComponent();
		
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
		topbar_hidden_check.setRightButVisibility(View.GONE);
		
		initDate();
		mlistview.setAdapter(mAdapter);
		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				SimpleEnterprise sime=lse.get(arg2);
				Bundle bundle = new Bundle(); 
				bundle.putSerializable("SimpleEnterprise", sime); 
				Intent intent;
				if(mHiddenType.equals("Company"))
				{
					intent=new Intent(HiddenCompanyActivity.this, HiddenTabListActivity.class);
				}
				else
				{
					intent=new Intent(HiddenCompanyActivity.this, HiddenGovernListActivity.class);
				}
				
	       		intent.putExtras(bundle);
	       		startActivity(intent);
			}
		});
		
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_hidden_check=(TopBarView) findViewById(R.id.topbar_com_industry);
		mlistview=(ListView) findViewById(R.id.ListView_company_info);
	}
	
	private void initDate() {
		// TODO Auto-generated method stub
		lse=new ArrayList<SimpleEnterprise>();
		for(int i=1;i<15;i++)
		{
			SimpleEnterprise sime=new SimpleEnterprise();
			sime.setE_companyName("陕西山利科技发展有限责任公司");
			sime.setE_companyAddress("西安市科技二路软件园秦风阁");
			sime.setE_managerlayer("市级监管");
			sime.setE_localName("高新区");
			sime.setE_departmentName("西安市安监局");
			sime.setE_legal_representative("黄笑笑");
			sime.setE_contact_phone("18165412563");
			sime.setE_belongIndustry("建筑施工");
			sime.setE_mangementMethod("生产（制造）");
			sime.setE_businessCode("XIAN99999999999999");
			sime.setRemark(String.valueOf(img));
			
			lse.add(sime);
		}
		mAdapter=new EnterpriseAdapter(this, lse, R.layout.company_list_item,img);
	}

}


