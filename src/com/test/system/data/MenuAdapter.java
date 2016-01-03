package com.sunnyit.system.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.system.model.menu_item;

import android.content.Context;

/**   
* @Title: MenuAdapter.java 
* @Package com.sunnyit.system.data 
* @Description: TODO
* @author yk
* @date 2015年8月7日 下午5:46:45 
* @version V1.0   
*/
public class MenuAdapter extends AdapterUtil<menu_item> {

	public MenuAdapter(Context context, List<menu_item> datas, int layoutId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, menu_item t, int position) {
		// TODO Auto-generated method stub
		viewHolder.setImageResource(R.id.menu_item_img, t.getMenuImg());
		viewHolder.setText(R.id.menu_item_txt, t.getMenuText());
	}

}


