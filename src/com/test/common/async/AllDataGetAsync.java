package com.sunnyit.common.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.sunnyit.R;
import com.sunnyit.chemicals.action.ChemicalsDirectoryActivity;
import com.sunnyit.chemicals.model.Chemicals;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.dialog.CustomDialog.CancelDialogListener;
import com.sunnyit.common.http.HttpClientGetOperate;
import com.sunnyit.common.json.JsonToData;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.company.model.SimpleEnterprise;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.enforcement.model.Inspector;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;
import com.sunnyit.hiddencheck.model.SelfCheck;
import com.sunnyit.hiddencheck.model.SelfStandCheck;
import com.sunnyit.hiddencheck.model.StandcheckDetail;
import com.sunnyit.law.model.ExcelCell;
import com.sunnyit.law.model.HiddenStandardFile;
import com.sunnyit.law.model.PublishInfo;
import com.sunnyit.synchronous.model.CountyArea;
import com.sunnyit.synchronous.model.CountyDepartment;
import com.sunnyit.synchronous.model.Industry;
import com.sunnyit.synchronous.model.UpInfo;
import com.sunnyit.system.model.User;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;
import jxl.Sheet;
import jxl.Workbook;

/**   
* @Title: HttpClientFileUpload.java 
* @Package com.sunnyit.common.async 
* @Description: TODO
* @author yk
* @date 2015年9月1日 上午9:48:59 
* @version V1.0   
 * @param <T>
*/
public class AllDataGetAsync extends AsyncTask<String, Long, String> {
	
	private CustomDialog cusdialog;//网络进度条
	private CustomDialog cusdialogt;//数据写入进度条
	
	private Context mContext;
	
	private List<Object> ls_SimpleEnterprise;
	private List<Object> ls_Industry;
	private List<Object> ls_CountyArea;
	private List<Object> ls_CountyDepartment;
	private List<Object> ls_SelfCheck;
	private List<Object> ls_SelfStandCheck;
	private List<Object> ls_StandcheckDetail;
	private List<Object> ls_User;
	private List<Object> ls_PublishInfo;
	private List<Object> ls_ExcelCell;
	private List<Object> ls_HiddenStandardFile;
	
	private List<Object> ls_DailyCheck;
	private List<Object> ls_SpecialCheck;
	private List<Object> ls_Inspector;
	private List<Object> ls_Standard_CK_Table;
	private List<Object> ls_Standard_CK_Table_Item;
	
	private List<Object> ls_Chemicals;
	
	private float minp;//进度条每一个最小进度
	private int basecount=0;//进度条显示数据
	private int pro_max=0;
	
	public AllDataGetAsync(Context context) {
		this.mContext=context;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		cusdialog=new CustomDialog(mContext);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue,0);
		cusdialog.setOutCancel(false);
		cusdialog.setCancelDialogLiatener(new CancelDialogListener() {
			@Override
			public void onCancelDialog() {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "取消", Toast.LENGTH_SHORT).show();  
			}
		});
		cusdialog.show();
		
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		String resultStr="";
		try {
			/**
			 * 获取新数据
			 */
			String SimpleEnterpriseStr=HttpClientGetOperate.httpClientGet(params[0]);
			String IndustryStr=HttpClientGetOperate.httpClientGet(params[1]);
			String CountyAreaStr=HttpClientGetOperate.httpClientGet(params[2]);
			String CountyDepartmentStr=HttpClientGetOperate.httpClientGet(params[3]);
			String SelfCheckStr=HttpClientGetOperate.httpClientGet(params[4]);
			String SelfStandCheckStr=HttpClientGetOperate.httpClientGet(params[5]);
			String StandcheckDetailStr=HttpClientGetOperate.httpClientGet(params[6]);
			String UserStr=HttpClientGetOperate.httpClientGet(params[7]);
			String PublishInfoStr=HttpClientGetOperate.httpClientGet(params[8]);
			String ExcelCellStr=HttpClientGetOperate.httpClientGet(params[9]);
			String HiddenStandardFileStr=HttpClientGetOperate.httpClientGet(params[10]);
			
			String DailyCheckStr=HttpClientGetOperate.httpClientGet(params[11]);
			String SpecialCheckStr=HttpClientGetOperate.httpClientGet(params[12]);
			String InspectorStr=HttpClientGetOperate.httpClientGet(params[13]);
			String Standard_CK_TableStr=HttpClientGetOperate.httpClientGet(params[14]);
			String Standard_CK_Table_ItemStr=HttpClientGetOperate.httpClientGet(params[15]);
			
			/**
			 * 数据处理，得到数据的list
			 */
			ls_SimpleEnterprise = getObjectList(SimpleEnterpriseStr,SimpleEnterprise.class);
			ls_Industry = getObjectList(IndustryStr,Industry.class);
			ls_CountyArea = getObjectList(CountyAreaStr,CountyArea.class);
			ls_CountyDepartment = getObjectList(CountyDepartmentStr,CountyDepartment.class);
			ls_SelfCheck = getObjectList(SelfCheckStr,SelfCheck.class);
			ls_SelfStandCheck = getObjectList(SelfStandCheckStr,SelfStandCheck.class);
			ls_StandcheckDetail = getObjectList(StandcheckDetailStr,StandcheckDetail.class);
			ls_User = getObjectList(UserStr,User.class);
			ls_PublishInfo = getObjectList(PublishInfoStr,PublishInfo.class);
			ls_ExcelCell = getObjectList(ExcelCellStr,ExcelCell.class);
			ls_HiddenStandardFile = getObjectList(HiddenStandardFileStr,HiddenStandardFile.class);
			
			ls_DailyCheck = getObjectList(DailyCheckStr,DailyCheck.class);
			ls_SpecialCheck = getObjectList(SpecialCheckStr,SpecialCheck.class);
			ls_Inspector = getObjectList(InspectorStr,Inspector.class);
			ls_Standard_CK_Table = getObjectList(Standard_CK_TableStr,Standard_CK_Table.class);
			ls_Standard_CK_Table_Item = getObjectList(Standard_CK_Table_ItemStr,Standard_CK_Table_Item.class);
			
			getChemicalsList();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			resultStr="connect_error";
		}
		
		return resultStr;
	}
	
	@Override
	protected void onProgressUpdate(Long... values) {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void onPostExecute(String result) {
		if(result.equals("connect_error"))
		{
			cusdialog.dismiss();
			Toast.makeText(mContext, "连接失败，请确认网络和服务器是否可用！", Toast.LENGTH_SHORT).show();
		}
		else
		{
			/*
			 * 删除上传记录数据
			 */
			deleteTable(new UpInfo());
			/**
			 * 删除本地已有数据
			 */
			deleteTable(new SimpleEnterprise());
			deleteTable(new Industry());
			deleteTable(new CountyArea());
			deleteTable(new CountyDepartment());
			deleteTable(new SelfCheck());
			deleteTable(new SelfStandCheck());
			deleteTable(new StandcheckDetail());
			deleteTable(new User());
			deleteTable(new PublishInfo());
			deleteTable(new ExcelCell());
			deleteTable(new HiddenStandardFile());
			
			deleteTable(new DailyCheck());
			deleteTable(new SpecialCheck());
			deleteTable(new Inspector());
			deleteTable(new Standard_CK_Table());
			deleteTable(new Standard_CK_Table_Item());
			
			deleteTable(new Chemicals());
			
			cusdialogt=new CustomDialog(mContext);
			cusdialogt.setViewAndAlpha(R.layout.dialog_custom,0);
			cusdialogt.initProgressBar(R.id.id_progressbarh);
			cusdialogt.setOutCancel(false);
			cusdialogt.setText(R.id.tv_dg_title, "数据写入中");
			cusdialogt.findViewById(R.id.rel_cb_divider).setVisibility(View.GONE);
			cusdialogt.findViewById(R.id.lin_but_divider).setVisibility(View.GONE);
			cusdialogt.show();
			cusdialog.dismiss();
			
			/*int max=ls_SimpleEnterprise.size()+ls_Industry.size()+ls_CountyArea.size()+ls_CountyDepartment.size();
			max=max+ls_SelfCheck.size()+ls_SelfStandCheck.size()+ls_StandcheckDetail.size()+ls_User.size();
			max=max+ls_PublishInfo.size();*/
			minp=100.0f/pro_max;
			
			new Thread(new Runnable(){  
	            
	            public void run(){  
	            	
	            	/**
	            	 * 数据写书
	            	 */
	            	
	            	dateSave(ls_SimpleEnterprise,new SimpleEnterprise());
	            	dateSave(ls_Industry,new Industry());
	            	dateSave(ls_CountyArea,new CountyArea());
	            	dateSave(ls_CountyDepartment,new CountyDepartment());
	            	dateSave(ls_SelfCheck,new SelfCheck());
	            	dateSave(ls_SelfStandCheck,new SelfStandCheck());
	            	dateSave(ls_StandcheckDetail,new StandcheckDetail());
	            	dateSave(ls_User,new User());
	            	dateSave(ls_PublishInfo,new PublishInfo());
	            	dateSave(ls_ExcelCell,new ExcelCell());
	            	dateSave(ls_HiddenStandardFile,new HiddenStandardFile());
	            	
	            	dateSave(ls_DailyCheck,new DailyCheck());
	            	dateSave(ls_SpecialCheck,new SpecialCheck());
	            	dateSave(ls_Inspector,new Inspector());
	            	dateSave(ls_Standard_CK_Table,new Standard_CK_Table());
	            	dateSave(ls_Standard_CK_Table_Item,new Standard_CK_Table_Item());
	            	
	            	dateSave(ls_Chemicals,new Chemicals());
	            	
	            	cusdialogt.dismiss();
	            	handler.sendEmptyMessage(0);
	            }  
	        }).start();  
			
		}
	}
	
	/**
	* @author yk 
	* @date 2015年9月23日 下午5:58:59 
	* @Title: dateSave 
	* @Description: 数据写入，更新progressbar
	* @param ls_o
	* @param t    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private <T> void dateSave(List<Object> ls_o,T t) {
		if(ls_o!=null)
		{
			SqlOperate<T> opetaterSim=new SqlOperate<T>(mContext, t.getClass());
			for(int i=0;i<ls_o.size();i++)
			{
	    		opetaterSim.saveContent((T)ls_o.get(i));
	    		cusdialogt.setProgress((int)Math.rint(minp*(i+basecount+1)));
			}
			opetaterSim.close();
	    	basecount=basecount+ls_o.size();
		}
	}  

	/**
	* @author yk 
	* @date 2015年9月23日 下午5:59:26 
	* @Title: deleteTable 
	* @Description: 本地数据删除
	* @param t    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private <T> void deleteTable(T t) {
		SqlOperate<T> opetater=new SqlOperate<T>(mContext, t.getClass());
		opetater.DeleteAllContent();
		opetater.close();
	}
	
	/**
	* @author yk 
	* @date 2015年9月23日 下午5:59:41 
	* @Title: getObjectList 
	* @Description: 获取list数据
	* @param jsonStr
	* @param Cla
	* @return    设定文件 
	* @return List<Object>    返回类型 
	* @throws
	 */
	private List<Object> getObjectList(String jsonStr,Class<?> Cla) {
		JsonToData jd=new JsonToData();
		boolean isSuccess;
		String msg;
		List<Object> ls= new ArrayList<>();
		try {
			isSuccess=jd.initJsonSuccess(jsonStr);
			msg=jd.initJsonMsg(jsonStr);
			if(isSuccess)
			{
				ls= jd.initJsonData(jsonStr, Cla);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pro_max=pro_max+ls.size();
		return ls;
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "更新完成", Toast.LENGTH_SHORT).show();
		}
	};
	
	private void getChemicalsList() {
		// TODO Auto-generated method stub
		ls_Chemicals=new ArrayList<>();
		Workbook mBook;
		Sheet mSheet;
		int mRows;
		try {
            mBook = Workbook.getWorkbook(mContext.getAssets().open("whp.xls"));
            mSheet = mBook.getSheet(0); 
            mRows = mSheet.getRows(); 
            for (int i = 0; i < mRows; i++) { 
            	Chemicals ch=new Chemicals();
            	ch.setC_no(mSheet.getCell(0, i).getContents());
            	ch.setC_name(mSheet.getCell(1, i).getContents());
            	ch.setC_aliasname(mSheet.getCell(2, i).getContents());
            	ch.setC_cas(mSheet.getCell(3, i).getContents());
            	ch.setC_isvirulen(mSheet.getCell(4, i).getContents());
            	ch.setC_showname(mSheet.getCell(5, i).getContents());
            	/*ch.setC_physical(mSheet.getCell(6, i).getContents());
            	ch.setC_danger(mSheet.getCell(7, i).getContents());
            	ch.setC_aidmeasures(mSheet.getCell(8, i).getContents());
            	ch.setC_firecontrol(mSheet.getCell(9, i).getContents());
            	ch.setC_leak(mSheet.getCell(10, i).getContents());
            	ch.setC_operation(mSheet.getCell(11, i).getContents());
            	ch.setC_contact(mSheet.getCell(12, i).getContents());
            	ch.setC_stabreact(mSheet.getCell(13, i).getContents());
            	ch.setC_toxicological(mSheet.getCell(14, i).getContents());
            	ch.setC_transport(mSheet.getCell(15, i).getContents());
            	ch.setC_treatment(mSheet.getCell(16, i).getContents());*/
            	ls_Chemicals.add(ch);
            } 

        } catch (Exception e) { 
            System.out.println(e); 
        }
		pro_max=pro_max+ls_Chemicals.size();
	}
	
}


