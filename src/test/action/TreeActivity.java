package test.action;


import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemClickListener;
import com.sunnyit.common.treeview.TreeNode;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import test.data.TreeViewInitAdapter;
import test.model.FileBean;

public class TreeActivity extends BaseActivity {
	
	private ListView mListView;
	private List<FileBean> lf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tree_list);
		
		initView();
		
		initDate();
		
		try {
			final TreeViewInitAdapter<FileBean> mAdapter=
					new TreeViewInitAdapter<FileBean>(this, lf, mListView, R.layout.tree_item,true,1,null);
			mListView.setAdapter(mAdapter);
			
			mAdapter.setOnTreeItenClickListener(new TreeItemClickListener() {

				@Override
				public void onItemClick(TreeNode node, int position) {
					// TODO Auto-generated method stub
					if(node.isLeaf())
					{
						Toast.makeText(TreeActivity.this, node.getName(), Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					/**
					 * 添加节点
					 */
					/*final EditText et = new EditText(TreeActivity.this);
					new AlertDialog.Builder(TreeActivity.this).setTitle("添加子节点")
							.setView(et)
							.setPositiveButton("确认", new OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{

									if (TextUtils.isEmpty(et.getText().toString()))
										return;
									TreeNode node=new TreeNode(-1, 0, et.getText().toString());
									mAdapter.addTreeNode(arg2, node);
								}
							}).setNegativeButton("取消", null).show();*/
					
					/**
					 * 删除节点
					 */
					/*mAdapter.deleteTreeNode(arg2);*/
					
					/**
					 * 获取点击的节点数
					 */
					List<TreeNode> checkleafnodes=mAdapter.getCheckLeafNodes();
					Toast.makeText(TreeActivity.this, String.valueOf(checkleafnodes.size()), Toast.LENGTH_SHORT).show();
					return false;
				}
			});
			
		} catch (IllegalAccessException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void initView() {
		mListView=(ListView) findViewById(R.id.list_tree_test);
	}

	private void initDate() {
		lf=new ArrayList<FileBean>();
		FileBean fb1=new FileBean("1", "0", "1","");
		lf.add(fb1);
		FileBean fb2=new FileBean("2", "0", "2","");
		lf.add(fb2);
		FileBean fb3=new FileBean("3", "0", "3","");
		lf.add(fb3);
		FileBean fb4=new FileBean("4", "1", "1-1","");
		lf.add(fb4);
		FileBean fb5=new FileBean("5", "1", "1-2","");
		lf.add(fb5);
		FileBean fb6=new FileBean("6", "2", "2-1","");
		lf.add(fb6);
		FileBean fb7=new FileBean("7", "2", "2-2","");
		lf.add(fb7);
		FileBean fb8=new FileBean("8", "3", "3-1","");
		lf.add(fb8);
		FileBean fb9=new FileBean("9", "4", "1-1-1","");
		lf.add(fb9);
	}

}
