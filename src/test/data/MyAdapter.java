package test.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import test.model.test_info;

public class MyAdapter extends AdapterUtil<test_info> {

	public MyAdapter(Context content, List<test_info> datas,int layoutId) {
		super(content, datas,layoutId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, final test_info t, int position) {
		// TODO Auto-generated method stub
		/*((ImageView)viewHolder.getView(R.id.img_info)).setImageResource(t.getImageid());;
		((TextView)viewHolder.getView(R.id.txt_info_title)).setText(t.getInfotitle());
		((TextView)viewHolder.getView(R.id.txt_info_time)).setText(t.getInfotime());
		((TextView)viewHolder.getView(R.id.txt_info_content)).setText(t.getInfocontent());*/
		
		viewHolder.setImageResource(R.id.img_info, t.getImageid());
		viewHolder.setText(R.id.txt_info_title, t.getInfotitle());
		viewHolder.setText(R.id.txt_info_time, t.getInfotime());
		viewHolder.setText(R.id.txt_info_content, t.getInfocontent());
		
		final CheckBox checkbox=viewHolder.getView(R.id.ck_info_choice);
		checkbox.setChecked(t.getIscheck());
		
		checkbox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				t.setIscheck(checkbox.isChecked());
			}
		});
		
	}

}
