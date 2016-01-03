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
* @date 2015��10��26�� ����8:58:57 
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
		mTopbar.setTitle("ִ������");
		
	}
	
	private void initListview() {
		
		getData();
		
		SimpleAdapter saImageItems = new SimpleAdapter(this, 
				mData,// ����Դ
                R.layout.document_show_item,// ��ʾ����
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
					Toast.makeText(EnforcementDocumentActivity.this, "δ�ҵ����", Toast.LENGTH_LONG).show();
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
		mHs.put("����������", "01laspl.doc");
		mHs.put("ѯ��֪ͨ��", "02xwtzs.doc");
		mHs.put("ѯ�ʱ�¼", "03xwbl.doc");
		mHs.put("�����¼", "04kybl.doc");
		mHs.put("����ȡ֤ƾ֤", "05cyzqpz.doc");
		mHs.put("���еǼǱ���֤��������", "06zjspb.doc");
		mHs.put("���еǼǱ���֤��֪ͨ��", "07zjtzs.doc");
		mHs.put("���еǼǱ���֤�ݴ���������", "08zjspb.doc");
		mHs.put("���еǼǱ���֤�ݴ��������", "09zjcljds.doc");
		mHs.put("�ֳ�����¼", "10xcjcjl.doc");
		mHs.put("�ֳ������ʩ������", "11xccljds.doc");
		mHs.put("������������ָ����", "12zlxqzg.doc");
		mHs.put("���ĸ��������", "13zgfcyjs.doc");
		mHs.put("ǿ�ƴ�ʩ������", "14qzcsjds.doc");
		mHs.put("����ί����", "15jdwts.doc");
		mHs.put("����������֪��", "16xzcftzs.doc");
		mHs.put("�����˳�������¼", "17cssbbl.doc");
		mHs.put("��֤��֪��", "18tzgzs.doc");
		mHs.put("��֤��֪ͨ�� ", "19tztzs.doc");
		mHs.put("��֤��¼", "20tzbl.doc");
		mHs.put("��֤�ᱨ����", "21tzhbgs.doc");
		mHs.put("�������������", "22ajclcps.doc");
		mHs.put("���������������ۼ�¼", "23xzcftljl.doc");
		mHs.put("���������������������飨��λ��", "24xzcfdwxc.doc");
		mHs.put("���������������������飨���ˣ�", "25xzcfgrxc.doc");
		mHs.put("�������������飨��λ��", "26xzcfdw.doc");
		mHs.put("�������������飨���ˣ�", "27xzcfgr.doc");
		mHs.put("����߽�֪ͨ��", "28fkcjtzs.doc");
		mHs.put("���ڣ����ڣ����ɷ���������", "29yqfqspb.doc");
		mHs.put("���ڣ����ڣ����ɷ�����׼��", "30xqfqpzs.doc");
		mHs.put("�����ʹ��ִ", "31wssdhz.doc");
		mHs.put("ǿ��ִ��������", "32qzzxsqs.doc");
		mHs.put("�᰸������", "33jaspb.doc");
		mHs.put("��������������", "34ajysspb.doc");
		mHs.put("����������", "35ajyss.doc");
		mHs.put("������ҳ", "36ajsy.doc");
		mHs.put("����Ŀ¼", "37jnml.doc");
		
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


