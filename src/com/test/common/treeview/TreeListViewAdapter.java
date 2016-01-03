package com.sunnyit.common.treeview;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sunnyit.common.adapter.ViewHolderUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**   
* @Title: TreeListViewAdapter.java 
* @Package com.sunnyit.common.view.tree 
* @Description: TODO
* @author yk
* @date 2015年8月24日 下午3:14:07 
* @version V1.0   
*/
public abstract class TreeListViewAdapter<T> extends BaseAdapter {
	
	private Context mcontext;
	private LayoutInflater mlInflater;
	private ListView mListView;
	
	private List<TreeNode> allNodes;
	private List<TreeNode> visibleNodes;
	
	private TreeItemClickListener mTreeItemClickListener;
	private TreeItemLongClickListener mTreeItemLongClickListener;
	
	private int mlayoutId;
	
	private List<Integer> mIcons;
	
	public TreeListViewAdapter(Context context,List<T> datas,ListView listview,
			int layoutId,int defaultExpandLevel,List<Integer> icons) throws IllegalAccessException, IllegalArgumentException {
		// TODO Auto-generated constructor stub
		this.mcontext=context;
		this.mlInflater=LayoutInflater.from(mcontext);
		this.mListView=listview;
		this.allNodes=TreeHelper.getSortedTreeNode(datas, defaultExpandLevel,icons);
		this.visibleNodes=TreeHelper.filterVisibleNodes(allNodes,icons);
		this.mlayoutId=layoutId;
		this.mIcons=icons;
		
		this.mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				setExpandOrClose(arg2);
				if(mTreeItemClickListener!=null)
				{
					mTreeItemClickListener.onItemClick(visibleNodes.get(arg2),arg2);
				}
			}
		});
		
		this.mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(mTreeItemLongClickListener!=null)
				{
					mTreeItemLongClickListener.onItemLongClick(visibleNodes.get(arg2),arg2);
				}
				return true;
			}
		});
	}
	
	private void setExpandOrClose(int position) {
		TreeNode node=visibleNodes.get(position);
		if(node.isLeaf())
		{
			return;
		}
		else
		{
			node.setExpand(!node.isExpand());
			visibleNodes=TreeHelper.filterVisibleNodes(allNodes,mIcons);
			this.notifyDataSetChanged();
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return visibleNodes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return visibleNodes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		/*TreeNode node=visibleNodes.get(position);
		
		convertView=getShowView(node,position,convertView,parent);
		convertView.setPadding(node.getLevel()*30, 0, 0, 0);*/
		
		ViewHolderUtil viewHolder=ViewHolderUtil.getViViewHolder(mcontext, position, convertView, parent, mlayoutId,null,null);
		TreeNode node=visibleNodes.get(position);
		initShowDate(viewHolder, node);
		viewHolder.getMconvertView().setPadding(node.getLevel()*50, 0, 0, 0);
		return viewHolder.getMconvertView();
	}
	
	public abstract void initShowDate(ViewHolderUtil viewHolder, TreeNode item);

	public void setOnTreeItenClickListener(TreeItemClickListener treeItenClickListener) {
		this.mTreeItemClickListener=treeItenClickListener;
	}
	
	public void setOnTreeItenLongClickListener(TreeItemLongClickListener treeItenLongClickListener) {
		this.mTreeItemLongClickListener=treeItenLongClickListener;
	}
	
	/**
	* @author yk 
	* @date 2015年8月25日 下午3:48:57 
	* @Title: SetChildrenChecked 
	* @Description: 设置子节点的选中事件
	* @param node
	* @param isChecked    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	protected void SetChildrenChecked(final TreeNode node, boolean isChecked) {
		node.setCkecked(isChecked);
		if(!node.isLeaf())
		{
			for(TreeNode cn:node.getChildren())
			{
				SetChildrenChecked(cn,isChecked);
			}
		}
	}
	
	/**
	* @author yk 
	* @date 2015年8月25日 下午4:39:19 
	* @Title: addTreeNode 
	* @Description: 添加节点
	* @param position
	* @param newNode    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void addTreeNode(int position,TreeNode newNode)
	{
		TreeNode parentNode=visibleNodes.get(position);
		int indexof=allNodes.indexOf(parentNode);
		newNode.setParent(parentNode);
		parentNode.getChildren().add(newNode);
		parentNode.setExpand(true);
		
		allNodes.add(indexof+1, newNode);
		visibleNodes=TreeHelper.filterVisibleNodes(allNodes,mIcons);
		notifyDataSetChanged();
	}
	
	/**
	* @author yk 
	* @date 2015年8月25日 下午5:01:37 
	* @Title: deleteTreeNode 
	* @Description: 删除节点
	* @param position    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteTreeNode(int position)
	{
		TreeNode currentNode=visibleNodes.get(position);
		if(!currentNode.isRoot())
		{
			TreeNode parentNode=currentNode.getParent();
			if(parentNode.getChildren().size()==1)
			{
				parentNode.setExpand(false);
			}
			parentNode.getChildren().remove(currentNode);
		}
		if(!currentNode.isLeaf())
		{
			removeChildrenNode(currentNode);
		}
		allNodes.remove(currentNode);
		visibleNodes=TreeHelper.filterVisibleNodes(allNodes,mIcons);
		notifyDataSetChanged();
	}
	
	/**
	* @author yk 
	* @date 2015年8月25日 下午5:05:12 
	* @Title: removeChildrenNode 
	* @Description: 递归删除节点
	* @param currentNode    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void removeChildrenNode(TreeNode currentNode) {
		allNodes.remove(currentNode);
		for(TreeNode node:currentNode.getChildren())
		{
			removeChildrenNode(node);
		}
	}


	/**
	* @author yk 
	* @date 2015年8月25日 下午5:05:33 
	* @Title: getCheckNodes 
	* @Description: 获取选中的节点
	* @return    设定文件 
	* @return List<TreeNode>    返回类型 
	* @throws
	 */
	public List<TreeNode> getCheckNodes() {
		List<TreeNode> result=new ArrayList<TreeNode>();
		for(TreeNode node:allNodes)
		{
			if(node.isCkecked())
			{
				result.add(node);
			}
		}
		return result;
	}
	
	/**
	* @author yk 
	* @date 2015年8月25日 下午5:05:48 
	* @Title: getCheckLeafNodes 
	* @Description: 获取选中的叶子节点
	* @return    设定文件 
	* @return List<TreeNode>    返回类型 
	* @throws
	 */
	public List<TreeNode> getCheckLeafNodes() {
		List<TreeNode> result=new ArrayList<TreeNode>();
		for(TreeNode node:allNodes)
		{
			if(node.isCkecked()&&node.isLeaf())
			{
				result.add(node);
			}
		}
		return result;
	}

	/**
	 * item点击事件
	 */
	public interface TreeItemClickListener{
		public void onItemClick(TreeNode node,int position);
	}
	
	/**
	 * item长按事件
	 */
	public interface TreeItemLongClickListener{
		public void onItemLongClick(TreeNode node,int position);
	}

}


