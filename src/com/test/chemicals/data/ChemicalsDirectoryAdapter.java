package com.sunnyit.chemicals.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.chemicals.model.Chemicals;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;

import android.content.Context;
import android.graphics.Color;

/**   
* @Title: ChemicalsDirectoryActivity.java 
* @Package com.sunnyit.chemicals.action 
* @Description: TODO
* @author yk
* @date 2015年11月10日 下午2:22:11 
* @version V1.0   
*/
public class ChemicalsDirectoryAdapter extends AdapterUtil<Chemicals> {
	
	public ChemicalsDirectoryAdapter(Context context, List<Chemicals> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, Chemicals t, int position) {
		// TODO Auto-generated method stub
		viewHolder.setText(R.id.tv_Chemicals_name,t.getC_no()+"、"+t.getC_showname());
		if(t.getC_isvirulen()!=null)
		{
			if(t.getC_isvirulen().equals("剧毒"))
			{
				viewHolder.setTextColor(R.id.tv_Chemicals_name, Color.RED);
			}
			else
			{
				viewHolder.setTextColor(R.id.tv_Chemicals_name, Color.rgb(128, 128, 128));
			}
		}
	}
	
}


