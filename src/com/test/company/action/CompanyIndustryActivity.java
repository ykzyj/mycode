package com.sunnyit.company.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.async.SimpleEnterpriseDataGetAsync;
import com.sunnyit.common.async.SimpleEnterpriseDataGetAsync.EndOperateListener;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.synchronous.model.Industry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**   
* @Title: WelcomeActivity.java 
* @Package com.sunnyit.system.action 
* @Description: TODO
* @author yk
* @date 2015��8��1�� ����9:47:36 
* @version V1.0   
*/
public class CompanyIndustryActivity extends BaseActivity  {
	
	//private GridView gridview_company_industry;
	private ListView listview_company_industry;
	private TopBarView  mTopbar;
	private CustomFAB  company_industry_search;
	
	/*private String industryStrs[] = new String[]{ 
			"����ȼ��", "����ʩ��","Σ�ջ�ѧƷ",
            "�̻�����", "��ͨ����","����ó", 
            "��ú��ɽ","����"};*/
	
    private int gridimages[] = new int[]{
    		R.drawable.ranqi96,R.drawable.jianzhu96,R.drawable.danger96,
    		R.drawable.yanhua96,R.drawable.jiaotong96,R.drawable.gongshang96,
    		R.drawable.feimei96,R.drawable.qita96};
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_industry);
		
		initComponent();
		initGridview();
		
		mTopbar.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				/*Intent intent=new Intent(CompanyIndustryActivity.this, CompanyInfoAddActivity.class);
	       		startActivity(intent);*/
				downAllSimpleEnterpriseInfo();
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mTopbar.setRightButText("ͬ��");
		
		company_industry_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CompanySearch.searchCompanyInfo(CompanyIndustryActivity.this,0);
			}
		});
		
	}
	
	private void downAllSimpleEnterpriseInfo() {
		SimpleEnterpriseDataGetAsync SimpleEnterpriseDataGetAsync=new SimpleEnterpriseDataGetAsync(CompanyIndustryActivity.this);
		String baseURL=getBaseUrl();
		if(baseURL!=null)
		{
			String compamyUrl=getBaseUrl()+"/appWebGet/getAllEnterprise";
			String inDustryUrl=getBaseUrl()+"/appWebGet/getAllIndustry";
			String CountyAreaUrl=getBaseUrl()+"/appWebGet/getAllCountyArea";
			String CountyDepartmentUrl=getBaseUrl()+"/appWebGet/getAllCountyDepartment";
			
			SimpleEnterpriseDataGetAsync.execute(compamyUrl,inDustryUrl,CountyAreaUrl,CountyDepartmentUrl);
			SimpleEnterpriseDataGetAsync.setEndOperateListener(new EndOperateListener() {
				
				@Override
				public void setEndOperate() {
					// TODO Auto-generated method stub
					Intent intent=new Intent(CompanyIndustryActivity.this, CompanyIndustryActivity.class);
		       		startActivity(intent);
		       		finish();
				}
			});
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(resultCode){
        case 1:
        	CompanySearch.et_search_localName.setText(data.getStringExtra("localname"));
        	break;
        case 2:
        	CompanySearch.et_search_departmentName.setText(data.getStringExtra("depname"));
        	break;
        case 3:
        	break;
     }
	}
	
	private void initComponent()
	{
		//gridview_company_industry=(GridView) findViewById(R.id.gridview_company_industry);
		listview_company_industry=(ListView) findViewById(R.id.listview_company_industry);
		mTopbar=(TopBarView) findViewById(R.id.topbar_com_industry);
		company_industry_search=(CustomFAB) findViewById(R.id.company_industry_search);
    }
	
	private void initGridview() {
		// TODO Auto-generated method stub
		/*ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < industryStrs.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", gridimages[i]);
            map.put("itemText", industryStrs[i]);
            lstImageItem.add(map);
        }
        
        SimpleAdapter saImageItems = new SimpleAdapter(this, 
                lstImageItem,// ����Դ
                R.layout.night_item,// ��ʾ����
                new String[] { "itemImage", "itemText" }, 
                new int[] { R.id.itemImage, R.id.itemText }); 
        gridview_company_industry.setAdapter(saImageItems);
        gridview_company_industry.setOnItemClickListener(new gridClickListener());*/
		
		/*mAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, industryStrs);*/
		
		SqlOperate<Industry> opetaterIn=new SqlOperate<Industry>(this, Industry.class);
		List<Industry> ls_Industry=opetaterIn.SelectEntitys();
    	opetaterIn.close();
    	
    	HashSet<String> hs = new HashSet<String>();
    	for(int i=0;i<ls_Industry.size();i++)
    	{
    		if(!ls_Industry.get(i).getIndustryType().equals("����"))
    		{
    			hs.add(ls_Industry.get(i).getIndustryType());
    		}
    	}
    	
    	final String[] IndustryStr = new String[hs.size()+1]; 
    	int in=0;
    	for(String s:hs)
    	{
    		IndustryStr[in]=s;
    		in++;
    	}
    	IndustryStr[in]="����";
    	
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < IndustryStr.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", R.drawable.often_question);
            map.put("itemText", IndustryStr[i]);
            lstImageItem.add(map);
        }
		
        SimpleAdapter saImageItems = new SimpleAdapter(this, 
                lstImageItem,// ����Դ
                R.layout.company_industry_item,// ��ʾ����
                new String[] { "itemImage", "itemText" }, 
                new int[] { R.id.img_industry_type, R.id.tv_industry_type }); 
		 
		 listview_company_industry.setAdapter(saImageItems);
		 //listview_company_industry.setOnItemClickListener(new listClickListener());
		 listview_company_industry.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(CompanyIndustryActivity.this, CompanyListActivity.class);
	       		intent.putExtra("industry", getIndustryImg(IndustryStr[arg2]) );
	       		intent.putExtra("industryName", IndustryStr[arg2]);
	       		startActivity(intent);
			}
		});
	}
	
	  private int getIndustryImg(String industryName) {
		  int img;
			switch (industryName) {
			case "����ȼ��":
				img=R.drawable.ranqi96;
				break;
			case "�̻�����":
				img=R.drawable.yanhua96;
				break;
			case "��ú��ɽ":
				img=R.drawable.feimei96;
				break;
			case "����ó":
				img=R.drawable.gongshang96;
				break;
			case "��ͨ����":
				img=R.drawable.jiaotong96;
				break;
			case "Σ�ջ�ѧƷ":
				img=R.drawable.danger96;
				break;
			case "����ʩ��":
				img=R.drawable.jianzhu96;
				break;
			default:
				img=R.drawable.qita96;
				break;
			}
		return img;
	}
	
}


