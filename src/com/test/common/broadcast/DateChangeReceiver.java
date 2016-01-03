package com.sunnyit.common.broadcast;

import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.dataOperate.dOperate;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.enforcement.model.DailyCheck;
import com.sunnyit.enforcement.model.SpecialCheck;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.hiddencheck.model.CompanyMin;
import com.sunnyit.remind.action.InformationRemindActivity;
import com.sunnyit.remind.model.InformationRemind;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**   
* @Title: NetworkChangeReceiver.java 
* @Package com.sunnyit.common.broadcast 
* @Description: TODO
* @author yk
* @date 2015��9��16�� ����4:25:43 
* @version V1.0   
*/
public class DateChangeReceiver extends BroadcastReceiver {
	
	public static final String mRectificationStr="select e_Id,e_companyName from SimpleEnterprise "+
			" where e_Id in "+
				"(select sc_companyId from SelfCheck"
				+ " where sc_deadline<date('now') and sc_state='ING' UNION "+
				" select sc_companyId from SelfStandCheck "
				+ "where sc_deadline<date('now') and (sc_state='ing' or sc_state='noyet' ))";
	
	public static final String mDailyExtendedStr="select * from DailyCheck where ck_fixend_time<date('now') "
			+ " and (ck_state='NOTYET' or ck_state='ING' ) order by ck_time desc ";
	
	public static final String mSpecialExtendedStr="select * from SpecialCheck where ck_fixend_time<date('now') "
			+ " and (ck_state='NOYET' or ck_state='ING' ) order by ck_time desc ";
	
	public static final String mStandExtendedStr="select * from Standard_CK_Table where ck_deadLine<date('now') "
			+ " and (ck_state='NOYET' or ck_state='ING' ) order by ck_time desc ";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		 SqlOperate<CompanyMin> opetaterCompanyName=new SqlOperate<CompanyMin>(context, CompanyMin.class);
		 List<CompanyMin> ls_CompanyMin=opetaterCompanyName.SelectEntitysBySqlCondition(mRectificationStr);
		 opetaterCompanyName.close();
		 
		 if(ls_CompanyMin.size()!=0)
		 {
			 InformationRemind info_com=new InformationRemind();
			 info_com.setI_ID(UUID.randomUUID().toString());
			 info_com.setI_Title("��ҵ�Բ鳬������");
			 info_com.setI_Content("��ǰ��"+ls_CompanyMin.size()+"����ҵ������ҵ�Բ鳬��δ���ĵ����");
			 info_com.setI_Time(dOperate.getCurrentLongDate());
			 info_com.setI_HaveLook("0");
			 
			 saveInformationRemind(context,info_com);
		 }
		 
		 SqlOperate<DailyCheck> opetaterDailyCheck=new SqlOperate<DailyCheck>(context, DailyCheck.class);
		 List<DailyCheck> ls_DailyCheck=opetaterDailyCheck.SelectEntitysBySqlCondition(mDailyExtendedStr);
		 opetaterDailyCheck.close();
		 
		 if(ls_DailyCheck.size()!=0)
		 {
			 InformationRemind info_daily=new InformationRemind();
			 info_daily.setI_ID(UUID.randomUUID().toString());
			 info_daily.setI_Title("�ճ���鳬������");
			 info_daily.setI_Content("��ǰ��"+ls_DailyCheck.size()+"���ճ������ڳ���δ���ĵ����");
			 info_daily.setI_Time(dOperate.getCurrentLongDate());
			 info_daily.setI_HaveLook("0");
			 
			 saveInformationRemind(context,info_daily);
		 }
		 
		 SqlOperate<SpecialCheck> opetaterSpecialCheck=new SqlOperate<SpecialCheck>(context, SpecialCheck.class);
		 List<SpecialCheck> ls_SpecialCheck=opetaterSpecialCheck.SelectEntitysBySqlCondition(mSpecialExtendedStr);
		 opetaterSpecialCheck.close();
		 
		 if(ls_SpecialCheck.size()!=0)
		 {
			 InformationRemind info_SpecialCheck=new InformationRemind();
			 info_SpecialCheck.setI_ID(UUID.randomUUID().toString());
			 info_SpecialCheck.setI_Title("ר���鳬������");
			 info_SpecialCheck.setI_Content("��ǰ��"+ls_SpecialCheck.size()+"��ר������ڳ���δ���ĵ����");
			 info_SpecialCheck.setI_Time(dOperate.getCurrentLongDate());
			 info_SpecialCheck.setI_HaveLook("0");
			 
			 saveInformationRemind(context,info_SpecialCheck);
		 }
		 
		 SqlOperate<Standard_CK_Table> opetaterStandard_CK_Table=new SqlOperate<Standard_CK_Table>(context, Standard_CK_Table.class);
		 List<Standard_CK_Table> ls_Standard_CK_Table=opetaterStandard_CK_Table.SelectEntitysBySqlCondition(mStandExtendedStr);
		 opetaterStandard_CK_Table.close();
		 
		 if(ls_Standard_CK_Table.size()!=0)
		 {
			 InformationRemind info_stand=new InformationRemind();
			 info_stand.setI_ID(UUID.randomUUID().toString());
			 info_stand.setI_Title("���ձ�׼��鳬������");
			 info_stand.setI_Content("��ǰ��"+ls_Standard_CK_Table.size()+"�����ձ�׼�����ڳ���δ���ĵ����");
			 info_stand.setI_Time(dOperate.getCurrentLongDate());
			 info_stand.setI_HaveLook("0");
			 
			 saveInformationRemind(context,info_stand);
		 }
		 
		 
		 int com_count=ls_CompanyMin.size();
		 int industry_count=ls_DailyCheck.size()+ls_SpecialCheck.size()+ls_Standard_CK_Table.size();
		 
		 if(com_count!=0&&industry_count!=0)
		 {
			 notifyUpdate(context,String.valueOf(com_count),String.valueOf(industry_count));
		 }
	}
	
	private void saveInformationRemind(Context context,InformationRemind informationRemind) {
		 SqlOperate<InformationRemind> opetaterInformationRemind=new SqlOperate<InformationRemind>(context, InformationRemind.class);
		 opetaterInformationRemind.saveContent(informationRemind);
		 opetaterInformationRemind.close();
	}

	
	private void notifyUpdate(Context context, String com_Count,String industry_count) {
		Intent mIntent=new Intent(context, InformationRemindActivity.class);
		PendingIntent pi = PendingIntent.getActivity(context, 0, mIntent,PendingIntent.FLAG_CANCEL_CURRENT);
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable. anjian_huizhang_xian_ok_200, "�ƶ���������ڳ���δ�������", System.currentTimeMillis());
		if(com_Count.equals("0"))
		{
			notification.setLatestEventInfo(context, "�ƶ�����ִ���������", "��ǰ��"+industry_count+"�������ڳ���δ���ĵ����", pi);
		}
		else if(industry_count.equals("0"))
		{
			notification.setLatestEventInfo(context, "�ƶ�������ҵ�Բ�����", "��ǰ��"+com_Count+"����ҵ������ҵ�Բ鳬��δ���ĵ����", pi);
		}
		else
		{
			notification.setLatestEventInfo(context, "�ƶ�����������",  "��ǰ��"+industry_count+"�������ڳ���δ����,"+com_Count+"����ҵ������ҵ�Բ鳬��δ���ĵ����", pi);
		}
			
		manager.notify(12312, notification);
	}
}


