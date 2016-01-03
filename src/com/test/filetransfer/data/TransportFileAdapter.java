package com.sunnyit.filetransfer.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.filetransfer.model.TransportFile;

import android.content.Context;

/**   
* @Title: TransportFileAdapter.java 
* @Package com.sunnyit.filetransfer.data 
* @Description: TODO
* @author yk
* @date 2015年12月4日 上午10:27:03 
* @version V1.0   
*/
public class TransportFileAdapter extends AdapterUtil<TransportFile> {

	public TransportFileAdapter(Context context, List<TransportFile> datas, int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, TransportFile t, int position) {
		viewHolder.setText(R.id.tv_tf_title, t.getTitle());
		if(t.getSender().length()>3)
		{
			viewHolder.setText(R.id.tv_tf_sender, t.getSender().substring(0, 3));
		}
		else
		{
			viewHolder.setText(R.id.tv_tf_sender, t.getSender());
		}
		
		if(t.getReceiver().length()>3)
		{
			viewHolder.setText(R.id.tv_tf_receiver, t.getReceiver().substring(0, 3));
		}
		else
		{
			viewHolder.setText(R.id.tv_tf_receiver, t.getReceiver());
		}
		/*viewHolder.setText(R.id.tv_tf_sender, t.getSender());
		viewHolder.setText(R.id.tv_tf_receiver, t.getReceiver());*/
		viewHolder.setText(R.id.tv_tf_sendTime, t.getSendTime());
		if(t.getState().equals("0"))
		{
			viewHolder.setText(R.id.tv_tf_receiveTime, "待接收");
		}
		else
		{
			viewHolder.setText(R.id.tv_tf_receiveTime, t.getReceiveTime());
		}
	}
	
}


