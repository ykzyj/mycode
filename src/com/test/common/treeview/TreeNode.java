package com.sunnyit.common.treeview;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;

/**   
* @Title: TreeNode.java 
* @Package com.sunnyit.common.view.tree 
* @Description: TODO
* @author yk
* @date 2015��8��21�� ����6:49:15 
* @version V1.0   
*/
public class TreeNode {
	private String id;
	private String pid="0";
	private String name;
	private String des;
	private int level;
	private boolean isExpand=false;
	private int icon;
	private boolean ckecked=false;
	/*
	 * ���ڵ�
	 */
	private TreeNode parent;
	/*
	 * �ӽڵ�
	 */
	private List<TreeNode> children=new ArrayList<TreeNode>();
	
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isCkecked() {
		return ckecked;
	}
	public void setCkecked(boolean ckecked) {
		this.ckecked = ckecked;
	}
	/*
	 * ��õ��ڵ�ĵȼ�
	 */
	public int getLevel() {
		if(parent==null)
		{
			return 0;
		}
		else
		{
			return parent.getLevel()+1;
		}
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isExpand() {
		return isExpand;
	}
	/*
	 * ���ýڵ��չ��
	 */
	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
		if(!isExpand)
		{
			for(TreeNode node:children)
			{
				node.setExpand(isExpand);
			}
		}
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public TreeNode getParent() {
		return parent;
	}
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	/*
	 * �ж��Ƿ�Ϊ���ڵ�
	 */
	public boolean isRoot()
	{
		return parent==null;
	}
	
	/*
	 * �жϸ��ڵ��Ƿ�չ��
	 */
	public boolean isParentExpand()
	{
		if(parent==null)
		{
			return false;
		}
		else
		{
			return parent.isExpand;
		}
	}
	
	/*
	 * �жϵ�ǰ�ڵ��Ƿ�ΪҶ�ӽڵ�
	 */
	public boolean isLeaf()
	{
		return children.size()==0;
	}
	public TreeNode(String id, String pid, String name,String des) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.des = des;
	}
}


