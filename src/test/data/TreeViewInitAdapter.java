package test.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.common.treeview.TreeListViewAdapter;
import com.sunnyit.common.treeview.TreeNode;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

/**   
* @Title: TreeViewInitAdapter.java 
* @Package test.data 
* @Description: TODO
* @author yk
* @date 2015年8月24日 下午5:02:50 
* @version V1.0   
*/
public class TreeViewInitAdapter<T> extends TreeListViewAdapter<T> {
	
	private boolean mIsShowCheckbox;

	public TreeViewInitAdapter(Context context, List<T> datas, ListView listview,
			int layoutId,boolean isShowCheckbox,int defaultExpandLeve,List<Integer> icons)
			throws IllegalAccessException, IllegalArgumentException {
		super(context, datas, listview,layoutId,defaultExpandLeve,icons);
		// TODO Auto-generated constructor stub
		this.mIsShowCheckbox=isShowCheckbox;
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, final TreeNode item) {
		// TODO Auto-generated method stub
		viewHolder.setImageResource(R.id.tree_icon, item.getIcon());
		viewHolder.setText(R.id.tree_text,item.getName());
		if(mIsShowCheckbox)
		{
			viewHolder.setCheckBoxCkecked(R.id.tree_cb_leaf, item.isCkecked());
			viewHolder.setViewVisible(R.id.tree_cb_leaf, View.VISIBLE);
			final CheckBox checkbox=viewHolder.getView(R.id.tree_cb_leaf);
			checkbox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SetChildrenChecked(item, !item.isCkecked());
					notifyDataSetChanged();
				}
			});
		}
		else
		{
			viewHolder.setViewVisible(R.id.tree_cb_leaf, View.GONE);
		}
	}
	
}


