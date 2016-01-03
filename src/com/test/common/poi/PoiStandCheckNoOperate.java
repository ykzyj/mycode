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
import java.util.List;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import com.sunnyit.common.convert.DigitalConvert;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.action.HiddenStandCheckListActivity;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;

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
public class PoiStandCheckNoOperate {
	
	private Context mContext;
	private Standard_CK_Table mStandard_CK_Table;
	private SimpleEnterprise mSimpleEnterprise;
	
	public PoiStandCheckNoOperate(Context context,Standard_CK_Table standard_CK_Table,SimpleEnterprise simpleEnterprise) {
		// TODO Auto-generated constructor stub
		mContext=context;
		mStandard_CK_Table=standard_CK_Table;
		mSimpleEnterprise=simpleEnterprise;
	}
	
	public void doScanofDailyCheck() throws ParseException{
		haveModel();
		//获取模板文件
		File demoFile=new File(Environment.getExternalStorageDirectory().toString()+"/anjianju/zfbzmbno.doc");
		//创建生成的文件
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
		map.put("$FRZW$", "单位法定代表人");
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
		map.put("$CKIP$", mStandard_CK_Table.getCk_people());
		
		String conStrN=" where ck_id='"+mStandard_CK_Table.getCk_id()+"' and item_isQualified='NO' and item_repairType='N' ";
		SqlOperate<Standard_CK_Table_Item> operaterStandard_CK_Table_Item_N=new SqlOperate<Standard_CK_Table_Item>(mContext, Standard_CK_Table_Item.class);
		List<Standard_CK_Table_Item> mData_N=operaterStandard_CK_Table_Item_N.SelectEntitysByCondition(conStrN);
		operaterStandard_CK_Table_Item_N.close();
		
		int datacount = 0;
		StringBuffer sb_now=new StringBuffer();
		if(mData_N!=null&&mData_N.size()>0)
		{
			/*String key_N="$CKCD"+datacount+"$";
			StringBuffer contentStr_N=new StringBuffer();
			contentStr_N.append("立即整改项为：");
			map.put(key_N, contentStr_N.toString());*/
			datacount++;
			
			for(int i=0;i<mData_N.size()&&i<60;i++)
			{
				//sb_now.append(String.valueOf(i+1)).append(",");
				sb_now.append(DigitalConvert.digital2China(i+1)).append(",");
				String key="$CKCD"+String.valueOf(i+1)+"$";
				StringBuffer contentStr=new StringBuffer();
				//contentStr.append(String.valueOf(i+1));
				contentStr.append(DigitalConvert.digital2China(i+1));
				contentStr.append("、");
				contentStr.append(mData_N.get(i).getItem_description()+"。");
				map.put(key, contentStr.toString());
				datacount++;
			}
		}

		String conStrL=" where ck_id='"+mStandard_CK_Table.getCk_id()+"' and item_isQualified='NO' and item_repairType='L' ";
		SqlOperate<Standard_CK_Table_Item> operaterStandard_CK_Table_Item_L=new SqlOperate<Standard_CK_Table_Item>(mContext, Standard_CK_Table_Item.class);
		List<Standard_CK_Table_Item> mData_L=operaterStandard_CK_Table_Item_L.SelectEntitysByCondition(conStrL);
		operaterStandard_CK_Table_Item_L.close();
		
		StringBuffer sb_limit=new StringBuffer();
		if(mData_L!=null&&mData_L.size()>0)
		{
			/*String key_L="$CKCD"+datacount+"$";
			StringBuffer contentStr_L=new StringBuffer();
			contentStr_L.append("限期整改项为：");
			map.put(key_L, contentStr_L.toString());
			datacount++;*/
			
			int baseCount=datacount;
			for(int i=0;i<mData_L.size()&&i<61;i++)
			{
				//sb_limit.append(String.valueOf(i+baseCount)).append(",");
				sb_limit.append(DigitalConvert.digital2China(i+baseCount)).append(",");
				String key="$CKCD"+String.valueOf(i+baseCount)+"$";
				StringBuffer contentStr=new StringBuffer();
				contentStr.append(DigitalConvert.digital2China(i+baseCount));
				contentStr.append("、");
				contentStr.append(mData_L.get(i).getItem_description());
				contentStr.append("(截止日期"+mData_L.get(i).getItem_repairLimit()+")。");
				map.put(key, contentStr.toString());
				datacount++;
			}
		}
		
		if((mData_L.size()+mData_N.size())>60)
		{
			StringBuffer contentStr=new StringBuffer();
			for(int i=61;i<mData_L.size()+60;i++)
			{
				//sb_limit.append(String.valueOf(i)).append(",");
				sb_limit.append(DigitalConvert.digital2China(i)).append(",");
				//contentStr.append(String.valueOf(i));
				contentStr.append(DigitalConvert.digital2China(i));
				contentStr.append("、");
				contentStr.append(mData_L.get(i).getItem_description());
				contentStr.append("(截止日期"+mData_L.get(i).getItem_repairLimit()+")。");
			}
			map.put("$CKCD61$", contentStr.toString());
			map.put("$CKCD62$", "立即整改项为第"+sb_now.substring(0, sb_now.length()-1)+"项。");
			map.put("$CKCD63$", "限期整改项为第"+sb_limit.substring(0, sb_limit.length()-1)+"项。");
		}
		else
		{
			if(sb_now.length()>0)
			{
				String key_n="$CKCD"+String.valueOf(datacount)+"$";
				datacount++;
				map.put(key_n, "立即整改项为第"+sb_now.substring(0, sb_now.length()-1)+"项。");
			}
			if(sb_limit.length()>0)
			{
				String key_l="$CKCD"+String.valueOf(datacount)+"$";
				datacount++;
				map.put(key_l, "限期整改项为第"+sb_limit.substring(0, sb_limit.length()-1)+"项。");
			}
			
			for(int i=datacount;i<64;i++)
			{
				String key="$CKCD"+String.valueOf(i)+"$";
				map.put(key, "");
			}
		}
		
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
			((HiddenStandCheckListActivity)mContext).startActivity(intent);
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
			 String filePath = Environment.getExternalStorageDirectory().toString()+"/anjianju/zfbzmbno.doc";
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
		
		/*String filePath = Environment.getExternalStorageDirectory().toString()+"/zfbzmbno.doc";
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
        myInput = mContext.getAssets().open("zfbzmbno.doc");  
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
	
	/*private static final String[] DIGIT ={"零","一","二","三","四","五","六","七","八","九"}; 
	private static final String[] UNIT = {"","十","百","千","万"};
	private static String digital2China(int c) {
		StringBuffer result=new StringBuffer();
		int i=0;
		int temp=c;
		do {
			int m=c%10;
			if(m!=0||result.length()!=0)
			{
				result.insert(0, UNIT[i]);
				result.insert(0, DIGIT[m]);
			}
			c=c/10;
			i++;
		} while (c>0);
		if(temp>9&&temp<20)
		{
			return result.substring(1);
		}
		return result.toString();
	}
	*/
}





