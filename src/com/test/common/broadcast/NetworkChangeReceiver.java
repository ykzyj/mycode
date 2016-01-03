package com.sunnyit.common.broadcast;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.dataOperate.dOperate;
import com.sunnyit.common.network.NetworkDetector;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.remind.model.InformationRemind;
import com.sunnyit.synchronous.action.SynchronousActivity;
import com.sunnyit.synchronous.model.UpInfo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**   
* @Title: NetworkChangeReceiver.java 
* @Package com.sunnyit.common.broadcast 
* @Description: TODO
* @author yk
* @date 2015年9月16日 下午4:25:43 
* @version V1.0   
*/
public class NetworkChangeReceiver extends BroadcastReceiver {
	
	private SharedPreferences sp;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		sp = context.getSharedPreferences("SP", Context.MODE_PRIVATE);
		
		String  networType=NetworkDetector.getCurrentNetType(context);
		if (networType.equals("3g")||networType.equals("4g")||networType.equals("wifi")) 
		{
			SqlOperate<UpInfo> opetaterInfo=new SqlOperate<UpInfo>(context, UpInfo.class);
			List<UpInfo> ls_UpInfo=opetaterInfo.SelectEntitys();
			opetaterInfo.close();
			if(ls_UpInfo.size()>0)
			{
				int spHour=sp.getInt("perUpHour", -1);
				Date d = new Date();
				int hours = d.getHours();
				if(-1==spHour)
				{
					notifyUpdate(context, networType, ls_UpInfo);
					Editor edit=sp.edit();
					edit.putInt("perUpHour", hours);
					edit.commit();
				}
				else
				{
					if(Math.abs(hours-spHour)>2)
					{
						notifyUpdate(context, networType, ls_UpInfo);
						Editor edit=sp.edit();
						edit.putInt("perUpHour", hours);
						edit.commit();
					}
				}
			}
		} 
		
	}

	private void notifyUpdate(Context context, String networType, List<UpInfo> ls_UpInfo) {
		
		InformationRemind info_up=new InformationRemind();
		info_up.setI_ID(UUID.randomUUID().toString());
		info_up.setI_Title("移动安监数据上传提醒");
		info_up.setI_Content("您当前有"+String.valueOf(ls_UpInfo.size())+"条数据需要上传");
		info_up.setI_Time(dOperate.getCurrentLongDate());
		info_up.setI_HaveLook("0");
		
		Intent mIntent=new Intent(context, SynchronousActivity.class);
		mIntent.putExtra("info_up", info_up);
		PendingIntent pi = PendingIntent.getActivity(context, 0, mIntent,PendingIntent.FLAG_CANCEL_CURRENT);
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable. anjian_huizhang_xian_ok_200, "当前网络为"+networType+"连接，移动安监可以进行数据同步上传", System.currentTimeMillis());
		notification.setLatestEventInfo(context, "移动安监数据上传提醒", "您当前有"+String.valueOf(ls_UpInfo.size())+"条数据需要上传", pi);
		manager.notify(12311, notification);
		
		saveInformationRemind(context,info_up);
	}
	
	private void saveInformationRemind(Context context,InformationRemind informationRemind) {
		 SqlOperate<InformationRemind> opetaterInformationRemind=new SqlOperate<InformationRemind>(context, InformationRemind.class);
		 opetaterInformationRemind.saveContent(informationRemind);
		 opetaterInformationRemind.close();
	}

}


