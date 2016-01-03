package com.sunnyit.chemicals.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.sunnyit.R;
import com.sunnyit.chemicals.data.ChemicalsShowAdapter;
import com.sunnyit.chemicals.model.Chemicals;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.data.ExAdapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

/**   
* @Title: ChemicalsDirectoryActivity.java 
* @Package com.sunnyit.chemicals.action 
* @Description: TODO
* @author yk
* @date 2015年11月10日 下午2:22:11 
* @version V1.0   
*/
public class ChemicalsDatilShowActivity extends BaseActivity {
	
	private TopBarView topbar_com_name;
	
	private EditText et_c_name;
	private EditText et_c_aliasname;
	private EditText et_c_cas;
	private EditText et_c_isvirulen;
	private ExpandableListView exlist_chenical_info;
	
	private List<BasicNameValuePair> fatherList;
	private List<List<BasicNameValuePair>> childList;
	
	private ChemicalsShowAdapter mAdapter;
	
	private Chemicals mChemicals;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chemical_datil_show);
		
		mChemicals=(Chemicals) getIntent().getSerializableExtra("Chemicals");
		
		fatherList = new ArrayList<BasicNameValuePair>();
		childList = new ArrayList<List<BasicNameValuePair>>();
		
		initComponent();
		
		initExpandableListView();
		
		mAdapter=new ChemicalsShowAdapter(ChemicalsDatilShowActivity.this, fatherList, childList);
		exlist_chenical_info.setAdapter(mAdapter);
		exlist_chenical_info.setGroupIndicator(null);
		exlist_chenical_info.setDivider(null);
		//exlist_chenical_info.expandGroup(0);  
		
		et_c_name.setText(mChemicals.getC_name());
		et_c_aliasname.setText(mChemicals.getC_aliasname());
		et_c_cas.setText(mChemicals.getC_cas());
		if(mChemicals.getC_isvirulen()!=null)
		{
			if(mChemicals.getC_isvirulen().equals("剧毒"))
			{
				et_c_isvirulen.setText("是");
				et_c_isvirulen.setTextColor(Color.RED);
			}
			else
			{
				et_c_isvirulen.setText("否");
				et_c_isvirulen.setTextColor(Color.GREEN);
			}
		}
		
		topbar_com_name.setTopBarClick(new ITopBarClick() {
			
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
		topbar_com_name.setRightButVisibility(View.GONE);
		
	}
	
	private void initExpandableListView()
	{
		fatherList.add(new BasicNameValuePair("father", "理化特征"));
		List<BasicNameValuePair> ls_ph = new ArrayList<BasicNameValuePair>();
		ls_ph.add(new BasicNameValuePair("child", mChemicals.getC_physical()));
		childList.add(ls_ph);
		
		fatherList.add(new BasicNameValuePair("father", "危险性描述"));
		List<BasicNameValuePair> ls_da = new ArrayList<BasicNameValuePair>();
		ls_da.add(new BasicNameValuePair("child", mChemicals.getC_danger()));
		childList.add(ls_da);
		
		fatherList.add(new BasicNameValuePair("father", "急救措施"));
		List<BasicNameValuePair> ls_ai = new ArrayList<BasicNameValuePair>();
		ls_ai.add(new BasicNameValuePair("child", mChemicals.getC_aidmeasures()));
		childList.add(ls_ai);
		
		fatherList.add(new BasicNameValuePair("father", "消防措施"));
		List<BasicNameValuePair> ls_fc = new ArrayList<BasicNameValuePair>();
		ls_fc.add(new BasicNameValuePair("child", mChemicals.getC_firecontrol()));
		childList.add(ls_fc);
		
		fatherList.add(new BasicNameValuePair("father", "泄露应急处理"));
		List<BasicNameValuePair> ls_le = new ArrayList<BasicNameValuePair>();
		ls_le.add(new BasicNameValuePair("child", mChemicals.getC_leak()));
		childList.add(ls_le);
		
		fatherList.add(new BasicNameValuePair("father", "操作设置"));
		List<BasicNameValuePair> ls_op = new ArrayList<BasicNameValuePair>();
		ls_op.add(new BasicNameValuePair("child", mChemicals.getC_operation()));
		childList.add(ls_op);
		
		fatherList.add(new BasicNameValuePair("father", "接触控制"));
		List<BasicNameValuePair> ls_con = new ArrayList<BasicNameValuePair>();
		ls_con.add(new BasicNameValuePair("child", mChemicals.getC_contact()));
		childList.add(ls_con);
		
		fatherList.add(new BasicNameValuePair("father", "稳定性和反应活性"));
		List<BasicNameValuePair> ls_st = new ArrayList<BasicNameValuePair>();
		ls_st.add(new BasicNameValuePair("child", mChemicals.getC_stabreact()));
		childList.add(ls_st);
		
		fatherList.add(new BasicNameValuePair("father", "毒理学资料"));
		List<BasicNameValuePair> ls_tox = new ArrayList<BasicNameValuePair>();
		ls_tox.add(new BasicNameValuePair("child", mChemicals.getC_toxicological()));
		childList.add(ls_tox);
		
		fatherList.add(new BasicNameValuePair("father", "运输信息"));
		List<BasicNameValuePair> ls_tr = new ArrayList<BasicNameValuePair>();
		ls_tr.add(new BasicNameValuePair("child", mChemicals.getC_transport()));
		childList.add(ls_tr);
		
		fatherList.add(new BasicNameValuePair("father", "处理方案"));
		List<BasicNameValuePair> ls_tm = new ArrayList<BasicNameValuePair>();
		ls_tm.add(new BasicNameValuePair("child", mChemicals.getC_treatment()));
		childList.add(ls_tm);
		
    }
	
	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_com_name=(TopBarView) findViewById(R.id.topbar_chemiacl_show);
		
		et_c_name=(EditText) findViewById(R.id.et_c_name);
		et_c_aliasname=(EditText) findViewById(R.id.et_c_aliasname);
		et_c_cas=(EditText) findViewById(R.id.et_c_cas);
		et_c_isvirulen=(EditText) findViewById(R.id.et_c_isvirulen);
		exlist_chenical_info=(ExpandableListView) findViewById(R.id.exlist_chenical_info);
	}
	
}


