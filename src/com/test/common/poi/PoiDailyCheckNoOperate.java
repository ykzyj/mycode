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

import com.sunnyit.common.async.HttpClientNpAsyncPost;
import com.sunnyit.common.convert.DigitalConvert;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.action.DailyReviewListActivity;
import com.sunnyit.enforcement.action.HiddenGovernChoiceTabActivity;
import com.sunnyit.enforcement.action.SpecialReviewListActivity;
import com.sunnyit.enforcement.model.CheckConditionItem;
import com.sunnyit.enforcement.model.DailyCheck;
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
public class PoiDailyCheckNoOperate {
	
	private Context mContext;
	private DailyCheck mDailyCheck;
	private SimpleEnterprise mSimpleEnterprise;
	
	public PoiDailyCheckNoOperate(Context context,DailyCheck dailyCheck,SimpleEnterprise simpleEnterprise) {
		// TODO Auto-generated constructor stub
		mContext=context;
		mDailyCheck=dailyCheck;
		mSimpleEnterprise=simpleEnterprise;
	}
	
	public void doScanofDailyCheck() throws ParseException{
		haveModel();
		//获取模板文件
		File demoFile=new File(Environment.getExternalStorageDirectory().toString()+"/anjianju/zfmbdsno.doc");
		//创建生成的文件
		String newFileName=String.valueOf(System.currentTimeMillis());
		String newfile=Environment.getExternalStorageDirectory().toString()+"/anjianju/"+newFileName+".doc";
		File newFile=new File(newfile);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		/*Date date_ckTime = sdf.parse(mDailyCheck.getCk_time());
		String str_ckTime = sdf.format(new Date());
		mDailyCheck.setCk_time(str_ckTime);*/
		
		String Y=mDailyCheck.getCk_time().substring(0, 4);
		String M=mDailyCheck.getCk_time().substring(5, 7);
		String D=mDailyCheck.getCk_time().substring(8, 10);
		
		String ck_startime=mDailyCheck.getCk_startime();
		String ck_endtime=mDailyCheck.getCk_endtime();
		
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
		
		Date date_TableTime = sdf.parse(mDailyCheck.getCk_completechecktabletime());
		String str_TableTime = sdf.format(new Date());
		mDailyCheck.setCk_completechecktabletime(str_TableTime);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("$QYMC$", mSimpleEnterprise.getE_companyName());
		map.put("$QYDZ$", mSimpleEnterprise.getE_companyAddress());
		//map.put("$FRDB$", mSimpleEnterprise.getE_legal_representative());
		map.put("$XCFZ$", mDailyCheck.getCk_sceneresponsible());
		map.put("$FRZW$", mDailyCheck.getCk_position());
		map.put("$FRLX$", mDailyCheck.getCk_telephone());
		map.put("$CKCS$", mDailyCheck.getCk_checkPlace());
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
		String checkingdepartment=mDailyCheck.getCk_checkingdepartment();
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
		
		
		map.put("$CKING$", w_checkingdepartment);
		map.put("$CKIP$", mDailyCheck.getCk_checkpeople());
		
		/*立即整改项为$CKFN$；                                                                                                                          
		限期整改项为$CKFL$，整改期限为$CKFST$至$CKFET$.*/
		
		String conditionStrItem=" where checkCkId='"+mDailyCheck.getC_id()+"' ";
		SqlOperate<CheckConditionItem> opetaterCheckConditionItem=
				new SqlOperate<CheckConditionItem>(mContext, CheckConditionItem.class);
		final List<CheckConditionItem> ls_CheckConditionItem=opetaterCheckConditionItem.SelectEntitysByCondition(conditionStrItem);
		opetaterCheckConditionItem.close();
		
		StringBuffer sb_now=new StringBuffer();
		StringBuffer sb_limit=new StringBuffer();
		int showCount=0;
		int resarchCount=0;
		for(int i=0;i<ls_CheckConditionItem.size()&&showCount<38;i++)
		{
			resarchCount++;
			if(!ls_CheckConditionItem.get(i).getCheckItemState().equals("0"))
			{
				String key="$CKCD"+String.valueOf(showCount)+"$";
				StringBuffer contentStr=new StringBuffer();
				contentStr.append(DigitalConvert.digital2China(showCount+1));
				contentStr.append("、");
				contentStr.append(ls_CheckConditionItem.get(i).getCheckContent());
				contentStr.append(".");
				map.put(key, contentStr.toString());
				
				if(ls_CheckConditionItem.get(i).getCheckItemState().equals("1"))
				{
					sb_now.append("第");
					sb_now.append(DigitalConvert.digital2China(showCount+1));
					sb_now.append("项");
					sb_now.append("、");
				}
				else if(ls_CheckConditionItem.get(i).getCheckItemState().equals("2"))
				{
					sb_limit.append("第");
					sb_limit.append(DigitalConvert.digital2China(showCount+1));
					sb_limit.append("项");
					sb_limit.append("、");
				}
				showCount++;
			}
		}
		
		if(ls_CheckConditionItem.size()>38)
		{
			StringBuffer contentStr=new StringBuffer();
			for(int i=resarchCount;i<ls_CheckConditionItem.size();i++)
			{
				if(!ls_CheckConditionItem.get(i).getCheckItemState().equals("0"))
				{
					contentStr.append(DigitalConvert.digital2China(showCount+1));
					contentStr.append("、");
					contentStr.append(ls_CheckConditionItem.get(i).getCheckContent());
					contentStr.append(".");
					
					if(ls_CheckConditionItem.get(i).getCheckItemState().equals("1"))
					{
						sb_now.append("第");
						sb_now.append(DigitalConvert.digital2China(showCount+1));
						sb_now.append("项");
						sb_now.append("、");
					}
					else if(ls_CheckConditionItem.get(i).getCheckItemState().equals("2"))
					{
						sb_limit.append("第");
						sb_limit.append(DigitalConvert.digital2China(showCount+1));
						sb_limit.append("项");
						sb_limit.append("、");
					}
				}
			}
			map.put("$CKCD"+String.valueOf(showCount)+"$", contentStr.toString());
			showCount++;
		}
		else
		{
			if(sb_now.length()>0)
			{
				String key_n="$CKCD"+String.valueOf(showCount)+"$";
				showCount++;
				map.put(key_n, "立即整改项为"+sb_now.substring(0, sb_now.length()-1)+"。");
			}
			if(sb_limit.length()>0)
			{
				String key_l="$CKCD"+String.valueOf(showCount)+"$";
				showCount++;
				map.put(key_l, "限期整改项为"+sb_limit.substring(0, sb_limit.length()-1)+","
						+ "整改期限为"+mDailyCheck.getCk_fixstart_time()+"至"+mDailyCheck.getCk_fixend_time()+".");
			}
			
			for(int i=showCount;i<41;i++)
			{
				String key="$CKCD"+String.valueOf(i)+"$";
				map.put(key, "");
			}
		}
		
		//map.put("$CKCD$", mDailyCheck.getCk_scenecondition());
		map.put("$CKFN$", mDailyCheck.getCk_fixnowing());
		map.put("$CKFL$", mDailyCheck.getCk_fixdeadline());
		map.put("$CKFST$", mDailyCheck.getCk_fixstart_time());
		map.put("$CKFET$", mDailyCheck.getCk_fixend_time());
		
		map.put("$YEAR$", mDailyCheck.getCk_completechecktabletime().substring(0, 4));
		map.put("$MOTH$", mDailyCheck.getCk_completechecktabletime().substring(5, 7));
		map.put("$DAY$", mDailyCheck.getCk_completechecktabletime().substring(8, 10));
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
			((DailyReviewListActivity)mContext).startActivity(intent);
		} catch(ActivityNotFoundException e) {
			//检测到系统尚未安装OliveOffice的apk程序
			Toast.makeText(mContext, "未找到软件", Toast.LENGTH_LONG).show();
			//请先到www.olivephone.com/e.apk下载并安装
		}
	}
	
	
	private void haveModel() {
		/*String outPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/anjianju";
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
			 String filePath = Environment.getExternalStorageDirectory().toString()+"/anjianju/zfmbdsno.doc";
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
		 }*/
		
		 String filePath = Environment.getExternalStorageDirectory().toString()+"/anjianju/zfmbdsno.doc";
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
		 }
	}
	
	private void copyBigDataToSD(String strOutFileName) throws IOException 
    {  
        InputStream myInput;  
        OutputStream myOutput = new FileOutputStream(strOutFileName);  
        myInput = mContext.getAssets().open("zfmbdsno.doc");  
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





