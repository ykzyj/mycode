package com.sunnyit.common.poi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.action.DailyReviewListActivity;
import com.sunnyit.enforcement.action.SpecialReviewListActivity;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.enforcement.model.Inspector;
import com.sunnyit.enforcement.model.SpecialCheck;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

/**   
* @Title: operate.java 
* @Package com.sunnyit.common.poi 
* @Description: TODO
* @author yk
* @date 2015��9��27�� ����10:19:16 
* @version V1.0   
*/
public class PoiSpecialRecordOperate {

	
	private Context mContext;
	private SpecialCheck mSpecialCheck;
	
	private String fileName;
	
	public PoiSpecialRecordOperate(Context context,SpecialCheck specialCheck) {
		// TODO Auto-generated constructor stub
		mContext=context;
		mSpecialCheck=specialCheck;
		
		if(mSpecialCheck.getCk_state().equals("NONEED"))
		{
			fileName="zxyhjl_n.doc";
		}
		else
		{
			fileName="zxyhjl_y.doc";
		}
	}
	
	public void doScanofDailyCheck() throws ParseException{
		haveModel();
		//��ȡģ���ļ�
		File demoFile=new File(Environment.getExternalStorageDirectory().toString()+"/anjianju/"+fileName);
		//�������ɵ��ļ�
		String newFileName=String.valueOf(System.currentTimeMillis());
		String newfile=Environment.getExternalStorageDirectory().toString()+"/anjianju/"+newFileName+".doc";
		File newFile=new File(newfile);
		
		if(!mSpecialCheck.getCk_state().equals("NONEED"))
		{
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date fixstart_time = sdf.parse(mSpecialCheck.getCk_fixstart_time());
				String str_fixstart_time = sdf.format(fixstart_time);
				mSpecialCheck.setCk_fixstart_time(ChDataConvert(str_fixstart_time));
				
				Date fixend_time = sdf.parse(mSpecialCheck.getCk_fixend_time());
				String str_fixend_time = sdf.format(fixend_time);
				mSpecialCheck.setCk_fixend_time(ChDataConvert(str_fixend_time));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("$specialcheckname$", mSpecialCheck.getCk_specialcheckname());
		map.put("$checkingdepartment$", mSpecialCheck.getCk_checkingdepartment());
		map.put("$groupid$", mSpecialCheck.getCk_checkgroupid());
		map.put("$leader$", mSpecialCheck.getCk_leader());
		map.put("$position$", mSpecialCheck.getCk_position());
		map.put("$cktime$", mSpecialCheck.getCk_time());
		map.put("$checkeddepartment$", mSpecialCheck.getCk_checkeddepartment());
		map.put("$site$", mSpecialCheck.getCk_site());
		map.put("$responsible$", mSpecialCheck.getCk_sceneresponsible());
		map.put("$duty$", mSpecialCheck.getCk_duty());
		map.put("$telephone$", mSpecialCheck.getCk_telephone());
		map.put("$scenecondition$", mSpecialCheck.getCk_scenecondition());
		map.put("$fixnowing$", mSpecialCheck.getCk_fixnowing());
		map.put("$fixdeadline$", mSpecialCheck.getCk_fixdeadline());
		map.put("$fixstart$", mSpecialCheck.getCk_fixstart_time());
		map.put("$fixend$", mSpecialCheck.getCk_fixend_time());
		map.put("$tablepeople$", mSpecialCheck.getCk_completechecktablepeople());
		map.put("$tabletime$", mSpecialCheck.getCk_completechecktabletime());
		
		map.put("$x_n1$", "");
		map.put("$x_n2$", "");
		map.put("$x_n3$", "");
		map.put("$x_p1$", "");
		map.put("$x_p2$", "");
		map.put("$x_p3$", "");
		map.put("$i_n1$", "");
		map.put("$i_n2$", "");
		map.put("$i_n3$", "");
		map.put("$i_p1$", "");
		map.put("$i_p2$", "");
		map.put("$i_p3$", "");
		
		List<Inspector> dataofInspector=new ArrayList<Inspector>();
		String sqlInspectorCondition=" where ck_id='"+mSpecialCheck.getCk_id()+"' and inspectortype='�����Ա' ";
		SqlOperate<Inspector> opetaterInspector=new SqlOperate<Inspector>(mContext, Inspector.class);
		dataofInspector=opetaterInspector.SelectEntitysByCondition(sqlInspectorCondition);
		opetaterInspector.close();
		
		for(int i=0;i<dataofInspector.size()&&i<3;i++)
		{
			String n_str="$x_n"+String.valueOf(i+1)+"$";
			String p_str="$x_p"+String.valueOf(i+1)+"$";
			map.remove(n_str);
			map.put(n_str, dataofInspector.get(i).getInspectorname());
			map.remove(p_str);
			map.put(p_str, dataofInspector.get(i).getInspectorduty());
		}
		
		List<Inspector> dataofExpert=new ArrayList<Inspector>();
		String sqlExpertCondition=" where ck_id='"+mSpecialCheck.getCk_id()+"' and inspectortype='ר��' ";
		SqlOperate<Inspector> dataofExpertOperate=new SqlOperate<Inspector>(mContext, Inspector.class);
		dataofExpert=dataofExpertOperate.SelectEntitysByCondition(sqlExpertCondition);
		dataofExpertOperate.close();
		
		for(int i=0;i<dataofExpert.size()&&i<3;i++)
		{
			String n_str="$x_n"+String.valueOf(i+1)+"$";
			String p_str="$x_p"+String.valueOf(i+1)+"$";
			map.remove(n_str);
			map.put(n_str, dataofExpert.get(i).getInspectorname());
			map.remove(p_str);
			map.put(p_str, dataofExpert.get(i).getInspectorduty());
		}
		
		writeDoc(demoFile,newFile,map);
		//�鿴
		doOpenWordofDailyCheck(newfile);
	}
	
	private String ChDataConvert(String mData) {
		String year=mData.substring(0, 4);
		String mounth=mData.substring(5, 7);
		String data=mData.substring(8, 10);
		return year+"��"+mounth+"��"+data+"��";
	}
	
	
	public void doOpenWordofDailyCheck(String newfile){
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW"); 
	      intent.addCategory("android.intent.category.DEFAULT");
		String fileMimeType = "application/msword";
		intent.setDataAndType(Uri.fromFile(new File(newfile)), fileMimeType);
		try{
			((SpecialReviewListActivity)mContext).startActivity(intent);
		} catch(ActivityNotFoundException e) {
			//��⵽ϵͳ��δ��װOliveOffice��apk����
			Toast.makeText(mContext, "δ�ҵ����", Toast.LENGTH_LONG).show();
			//���ȵ�www.olivephone.com/e.apk���ز���װ
		}
	}
	
	
	private void haveModel() {
		
		String outPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/anjianju";
		File mFile = new File(outPath);
		 if(!mFile.exists())  
		 {
			 mFile.mkdir();
			 try {
				copyBigDataToSD(outPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 else
		 {
			 String filePath = Environment.getExternalStorageDirectory().toString()+"/anjianju/"+fileName;
			 File file = new File(filePath);
			 if(!file.exists())  
			 {
				 try {
					copyBigDataToSD(filePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 }
		
		/*String filePath = Environment.getExternalStorageDirectory().toString()+"/"+fileName;
		 File file = new File(filePath);
		 if(!file.exists())  
		 {
			 try {
				copyBigDataToSD(filePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 else
		 {
			 file.delete();
			 try {
					copyBigDataToSD(filePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }*/
	}
	
	private void copyBigDataToSD(String strOutFileName) throws IOException 
    {  
        InputStream myInput;  
        OutputStream myOutput = new FileOutputStream(strOutFileName);  
        myInput = mContext.getAssets().open(fileName);  
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
	/**
	 * demoFile ģ���ļ�
	 * newFile �����ļ�
	 * map Ҫ��������
	 * */
	public void writeDoc(File demoFile ,File newFile ,Map<String, String> map)
	{
		try
		{	
			FileInputStream in = new FileInputStream(demoFile);
			HWPFDocument hdt = new HWPFDocument(in);
			// Fields fields = hdt.getFields();
			// ��ȡword�ı�����
			Range range = hdt.getRange();
			// System.out.println(range.text());
			
			// �滻�ı�����
			for(Map.Entry<String, String> entry : map.entrySet())
			{
				range.replaceText(entry.getKey(), entry.getValue());
			}
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			FileOutputStream out = new FileOutputStream(newFile, true);
			hdt.write(ostream);
			// ����ֽ���
			out.write(ostream.toByteArray());
			out.close();
			ostream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}





