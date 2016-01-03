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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.action.DailyReviewListActivity;
import com.sunnyit.enforcement.action.SpecialReviewListActivity;
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
* @date 2015年9月27日 上午10:19:16 
* @version V1.0   
*/
public class PoiSpecialCheckYesOperate {
	
	private Context mContext;
	private SpecialCheck mSpecialCheck;
	private SimpleEnterprise mSimpleEnterprise;
	private List<Inspector> mls_Inspector;
	
	public PoiSpecialCheckYesOperate(Context context,SpecialCheck specialCheck,
			SimpleEnterprise simpleEnterprise,List<Inspector> ls_Inspector) {
		// TODO Auto-generated constructor stub
		mContext=context;
		mSpecialCheck=specialCheck;
		mSimpleEnterprise=simpleEnterprise;
		mls_Inspector=ls_Inspector;
	}
	
	public void doScanofDailyCheck() throws ParseException{
		haveModel();
		//获取模板文件
		File demoFile=new File(Environment.getExternalStorageDirectory().toString()+"/anjianju/zfmbdsyes.doc");
		//创建生成的文件
		String newFileName=String.valueOf(System.currentTimeMillis());
		String newfile=Environment.getExternalStorageDirectory().toString()+"/anjianju/"+newFileName+".doc";
		File newFile=new File(newfile);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date_ckTime = sdf.parse(mSpecialCheck.getCk_time());
		String str_ckTime = sdf.format(new Date());
		mSpecialCheck.setCk_time(str_ckTime);
		
		Date date_TableTime = sdf.parse(mSpecialCheck.getCk_completechecktabletime());
		String str_TableTime = sdf.format(new Date());
		mSpecialCheck.setCk_completechecktabletime(str_TableTime);
		
		StringBuffer inspterStrs=new StringBuffer();
		for(Inspector in:mls_Inspector)
		{
			inspterStrs.append(in.getInspectorname());
			inspterStrs.append("、");
		}
		
		String inspterStr="";
		if(inspterStrs!=null&&inspterStrs.length()>0)
		{
			inspterStr=inspterStrs.substring(0, inspterStrs.length()-1);
		}
		
		String Y=mSpecialCheck.getCk_time().substring(0, 4);
		String M=mSpecialCheck.getCk_time().substring(5, 7);
		String D=mSpecialCheck.getCk_time().substring(8, 10);
		
		String ck_startime=mSpecialCheck.getCk_startime();
		String ck_endtime=mSpecialCheck.getCk_endtime();
		
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
		
		String checkingdepartment=mSpecialCheck.getCk_checkingdepartment();
		String w_checkingdepartment;
		if(checkingdepartment.contains("市"))
		{
			 w_checkingdepartment = checkingdepartment.substring(0,checkingdepartment.indexOf("市")+1);
		}
		else if(checkingdepartment.contains("区"))
		{
			w_checkingdepartment = checkingdepartment.substring(0,checkingdepartment.indexOf("区")+1);
		}
		else if(checkingdepartment.contains("办"))
		{
			w_checkingdepartment = checkingdepartment.substring(0,checkingdepartment.indexOf("办"));
		}
		else
		{
			w_checkingdepartment = checkingdepartment;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("$QYMC$", mSimpleEnterprise.getE_companyName());
		map.put("$QYDZ$", mSimpleEnterprise.getE_companyAddress());
		map.put("$FRDB$", mSimpleEnterprise.getE_legal_representative());
		map.put("$XCFZ$", mSpecialCheck.getCk_sceneresponsible());
		map.put("$FRZW$", mSpecialCheck.getCk_duty());
		map.put("$FRLX$", mSpecialCheck.getCk_telephone());
		map.put("$CKCS$", mSpecialCheck.getCk_site());
		
		map.put("$Y$", Y);
		map.put("$M$", M);
		map.put("$D$", D);
		map.put("$SM$", SM);
		map.put("$SS$", SS);
		map.put("$EM$", EM);
		map.put("$ES$", ES);
		
		map.put("$CKING$", w_checkingdepartment);
		map.put("$CKIP$", inspterStr);
		
		writeDoc(demoFile,newFile,map);
		//查看
		doOpenWordofDailyCheck(newfile);
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
			//检测到系统尚未安装OliveOffice的apk程序
			Toast.makeText(mContext, "未找到软件", Toast.LENGTH_LONG).show();
			//请先到www.olivephone.com/e.apk下载并安装
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
			 String filePath = Environment.getExternalStorageDirectory().toString()+"/anjianju/zfmbdsyes.doc";
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
		
		/*String filePath = Environment.getExternalStorageDirectory().toString()+"/zfmbdsyes.doc";
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
        myInput = mContext.getAssets().open("zfmbdsyes.doc");  
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
	 * demoFile 模板文件
	 * newFile 生成文件
	 * map 要填充的数据
	 * */
	public void writeDoc(File demoFile ,File newFile ,Map<String, String> map)
	{
		try
		{	
			FileInputStream in = new FileInputStream(demoFile);
			HWPFDocument hdt = new HWPFDocument(in);
			// Fields fields = hdt.getFields();
			// 读取word文本内容
			Range range = hdt.getRange();
			// System.out.println(range.text());
			
			// 替换文本内容
			for(Map.Entry<String, String> entry : map.entrySet())
			{
				range.replaceText(entry.getKey(), entry.getValue());
			}
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			FileOutputStream out = new FileOutputStream(newFile, true);
			hdt.write(ostream);
			// 输出字节流
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





