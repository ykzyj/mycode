package test.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import test.model.mooc;

/**   
* @Title: ImoocAdapter.java 
* @Package test.data 
* @Description: TODO
* @author yk
* @date 2015年8月3日 下午2:38:57 
* @version V1.0   
*/
public class ImoocAdapter extends AdapterUtil<mooc> {

	public ImoocAdapter(Context content, List<mooc> datas, int layoutId) {
		super(content, datas, layoutId);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void initShowDate(ViewHolderUtil viewHolder, mooc t, int position) {
		// TODO Auto-generated method stub
		viewHolder.setImageURI(R.id.img_info, t.getPicSmall());
		viewHolder.setText(R.id.txt_info_title, t.getName());
		viewHolder.setText(R.id.txt_info_time, t.getLearner());
		viewHolder.setText(R.id.txt_info_content, t.getDescription());
	}

}


