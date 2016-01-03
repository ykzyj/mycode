package com.sunnyit.document.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.CustomFAB;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**   
* @Title: EnforcementDocumentActivity.java 
* @Package com.sunnyit.Document.action 
* @Description: TODO
* @author yk
* @date 2015年10月26日 上午8:58:57 
* @version V1.0   
*/
public class EnforcementDocumentActivity extends BaseActivity {
	
	private ListView listview_company_industry;
	private TopBarView  mTopbar;
	private CustomFAB  company_industry_search;
	
	private ArrayList<HashMap<String, Object>> mData;
	private HashMap<String, String>  mHs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_industry);
		
		initComponent();
		initListview();
		
		mTopbar.setTopBarClick(new ITopBarClick() {
			
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
		mTopbar.setRightButVisibility(View.GONE);
		mTopbar.setTitle("执法文书");
		
	}
	
	private void initListview() {
		
		getData();
		
		SimpleAdapter saImageItems = new SimpleAdapter(this, 
				mData,// 数据源
                R.layout.document_show_item,// 显示布局
                new String[] { "itemImage", "itemText" }, 
                new int[] { R.id.img_industry_type, R.id.tv_industry_type }); 
		 
		 listview_company_industry.setAdapter(saImageItems);
		 listview_company_industry.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW"); 
			      intent.addCategory("android.intent.category.DEFAULT");
				String fileMimeType = "application/msword";
				HashMap<String, Object> map=mData.get(arg2);
				//String path = "file:///android_asset/"+map.get("itemfile");
				String fileName=(String)map.get("itemfile");
				haveFile(fileName);
				String filePath = Environment.getExternalStorageDirectory().toString()+"/anjianju/"+fileName;
				intent.setDataAndType(Uri.fromFile(new File(filePath)), fileMimeType);
				try{
					startActivity(intent);
				} catch(ActivityNotFoundException e) {
					Toast.makeText(EnforcementDocumentActivity.this, "未找到软件", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	private void haveFile(String filename) {
		
		String filePath = Environment.getExternalStorageDirectory().toString()+"/anjianju/"+filename;
		 File file = new File(filePath);
		 if(!file.exists())  
		 {
			 try {
				copyBigDataToSD(filePath,filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
	
	private void copyBigDataToSD(String strOutFileName,String filename) throws IOException 
    {  
        InputStream myInput;  
        OutputStream myOutput = new FileOutputStream(strOutFileName);  
        myInput = getAssets().open(filename);  
        byte[] buffer = new byte[1024];  
        int length = myInput.read(buffer);
        while(length > 0)
        {
            myOutput.write(buffer, 0, length); 
            length = myInput.read(buffer);
        }
        
        myOutput.flush();  
        myInput.close();  
        myOutput.close();        
    }  
	
	private void getData(){
		
		mData= new ArrayList<HashMap<String, Object>>();
		
		mHs=new HashMap<String, String>();
		mHs.put("立案审批表", "01laspl.doc");
		mHs.put("询问通知书", "02xwtzs.doc");
		mHs.put("询问笔录", "03xwbl.doc");
		mHs.put("勘验笔录", "04kybl.doc");
		mHs.put("抽样取证凭证", "05cyzqpz.doc");
		mHs.put("先行登记保存证据审批表", "06zjspb.doc");
		mHs.put("先行登记保存证据通知书", "07zjtzs.doc");
		mHs.put("先行登记保存证据处理审批表", "08zjspb.doc");
		mHs.put("先行登记保存证据处理决定书", "09zjcljds.doc");
		mHs.put("现场检查记录", "10xcjcjl.doc");
		mHs.put("现场处理措施决定书", "11xccljds.doc");
		mHs.put("责令限期整改指令书", "12zlxqzg.doc");
		mHs.put("整改复查意见书", "13zgfcyjs.doc");
		mHs.put("强制措施决定书", "14qzcsjds.doc");
		mHs.put("鉴定委托书", "15jdwts.doc");
		mHs.put("行政处罚告知书", "16xzcftzs.doc");
		mHs.put("当事人陈述申辩笔录", "17cssbbl.doc");
		mHs.put("听证告知书", "18tzgzs.doc");
		mHs.put("听证会通知书 ", "19tztzs.doc");
		mHs.put("听证笔录", "20tzbl.doc");
		mHs.put("听证会报告书", "21tzhbgs.doc");
		mHs.put("案件处理呈批表", "22ajclcps.doc");
		mHs.put("行政处罚集体讨论记录", "23xzcftljl.doc");
		mHs.put("行政（当场）处罚决定书（单位）", "24xzcfdwxc.doc");
		mHs.put("行政（当场）处罚决定书（个人）", "25xzcfgrxc.doc");
		mHs.put("行政处罚决定书（单位）", "26xzcfdw.doc");
		mHs.put("行政处罚决定书（个人）", "27xzcfgr.doc");
		mHs.put("罚款催缴通知书", "28fkcjtzs.doc");
		mHs.put("延期（分期）缴纳罚款审批表", "29yqfqspb.doc");
		mHs.put("延期（分期）缴纳罚款批准书", "30xqfqpzs.doc");
		mHs.put("文书送达回执", "31wssdhz.doc");
		mHs.put("强制执行申请书", "32qzzxsqs.doc");
		mHs.put("结案审批表", "33jaspb.doc");
		mHs.put("案件移送审批表", "34ajysspb.doc");
		mHs.put("案件移送书", "35ajyss.doc");
		mHs.put("案卷首页", "36ajsy.doc");
		mHs.put("卷内目录", "37jnml.doc");
		
		Iterator iter = mHs.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			HashMap<String, Object> map = new HashMap<String, Object>();
			String prefix=entry.getValue().toString().substring(entry.getValue().toString().lastIndexOf(".")+1);
			if(prefix.contains("doc"))
			{
				map.put("itemImage", R.drawable.word_w_48);
			}
			else if(prefix.contains("xls"))
			{
				map.put("itemImage", R.drawable.excel_x_48);
			}
			else
			{
				map.put("itemImage", R.drawable.often_question);
			}
			map.put("itemText",  entry.getKey());
			map.put("itemfile", entry.getValue());
			mData.add(map);
		}
        
    }
	
	private void initComponent()
	{
		//gridview_company_industry=(GridView) findViewById(R.id.gridview_company_industry);
		listview_company_industry=(ListView) findViewById(R.id.listview_company_industry);
		mTopbar=(TopBarView) findViewById(R.id.topbar_com_industry);
		company_industry_search=(CustomFAB) findViewById(R.id.company_industry_search);
		company_industry_search.setVisibility(View.GONE);
    }
	
}


