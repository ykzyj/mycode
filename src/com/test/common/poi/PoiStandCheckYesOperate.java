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
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.action.HiddenStandCheckListActivity;
import com.sunnyit.enforcement.model.Standard_CK_Table;

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
public class PoiStandCheckYesOperate {
	
	private Context mContext;
	private Standard_CK_Table mStandard_CK_Table;
	private SimpleEnterprise mSimpleEnterprise;
	
	public PoiStandCheckYesOperate(Context context,Standard_CK_Table standard_CK_Table,SimpleEnterprise simpleEnterprise) {
		// TODO Auto-generated constructor stub
		mContext=context;
		mStandard_CK_Table=standard_CK_Table;
		mSimpleEnterprise=simpleEnterprise;
	}
	
	public void doScanofDailyCheck() throws ParseException{
		haveModel();
		//��ȡģ���ļ�
		File demoFile=new File(Environment.getExternalStorageDirectory().toString()+"/anjianju/zfbzmbyes.doc");
		//�������ɵ��ļ�
		String newFileName=String.valueOf(System.currentTimeMillis());
		String newfile=Environment.getExternalStorageDirectory().toString()+"/anjianju/"+newFileName+".doc";
		File newFile=new File(newfile);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		String Y;
		String M;
		String D;
		try {
			Y=mStandard_CK_Table.getCk_time().substring(0, 4);
			M=mStandard_CK_Table.getCk_time().substring(5, 7);
			D=mStandard_CK_Table.getCk_time().substring(8, 10);
		} catch (Exception e) {
			Y="";
			M="";
			D="";
		}
		
		String ck_startime=mStandard_CK_Table.getCk_startime();
		String ck_endtime=mStandard_CK_Table.getCk_endtime();
		
		String SM;
		String SS;
		try {
			String[] starttimestrs=ck_startime.split(":");
			SM=starttimestrs[0];
			SS=starttimestrs[1];
		} catch (Exception e) {
			SM="";
			SS="";
		}
		String EM;
		String ES;
		try {
			String[] endtimestrs=ck_endtime.split(":");
			EM=endtimestrs[0];
			ES=endtimestrs[1];
		} catch (Exception e) {
			EM="";
			ES="";
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("$QYMC$", mSimpleEnterprise.getE_companyName());
		map.put("$QYDZ$", mSimpleEnterprise.getE_companyAddress());
		//map.put("$FRDB$", mSimpleEnterprise.getE_legal_representative());
		map.put("$XCFZ$", mSimpleEnterprise.getE_legal_representative());
		map.put("$FRZW$", "��λ����������");
		map.put("$FRLX$", mSimpleEnterprise.getE_contact_phone());
		map.put("$CKCS$", mStandard_CK_Table.getCk_site());
		/*map.put("$CKY$", mDailyCheck.getCk_time().substring(0, 4));
		map.put("$CKM$", mDailyCheck.getCk_time().substring(5, 7));
		map.put("$CKD$", mDailyCheck.getCk_time().substring(8, 10));*/
		
		map.put("$Y$", Y);
		map.put("$M$", M);
		map.put("$D$", D);
		map.put("$SM$", SM);
		map.put("$SS$", SS);
		map.put("$EM$", EM);
		map.put("$ES$", ES);
		String checkingdepartment=mStandard_CK_Table.getCk_dept();
		String w_checkingdepartment;
		if(checkingdepartment.contains("��"))
		{
			 w_checkingdepartment = checkingdepartment.substring(0,checkingdepartment.indexOf("��")+1);
		}
		else if(checkingdepartment.contains("��"))
		{
			w_checkingdepartment = checkingdepartment.substring(0,checkingdepartment.indexOf("��")+1);
		}
		else if(checkingdepartment.contains("��"))
		{
			w_checkingdepartment = checkingdepartment.substring(0,checkingdepartment.indexOf("��"));
		}
		else
		{
			w_checkingdepartment = checkingdepartment;
		}
		
		
		map.put("$CKING$", w_checkingdepartment);
		map.put("$CKIP$", mStandard_CK_Table.getCk_people());
		
		writeDoc(demoFile,newFile,map);
		//�鿴
		doOpenWordofDailyCheck(newfile);
	}
	
	
	public void doOpenWordofDailyCheck(String newfile){
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW"); 
	      intent.addCategory("android.intent.category.DEFAULT");
		String fileMimeType = "application/msword";
		intent.setDataAndType(Uri.fromFile(new File(newfile)), fileMimeType);
		try{
			((HiddenStandCheckListActivity)mContext).startActivity(intent);
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
			 String filePath = Environment.getExternalStorageDirectory().toString()+"/anjianju/zfbzmbyes.doc";
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
		
		/*String filePath = Environment.getExternalStorageDirectory().toString()+"/zfbzmbyes.doc";
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
        myInput = mContext.getAssets().open("zfbzmbyes.doc");  
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





