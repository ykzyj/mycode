package com.sunnyit.common.treeview;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.annotation.NodeDes;
import com.sunnyit.common.annotation.NodeID;
import com.sunnyit.common.annotation.NodeName;
import com.sunnyit.common.annotation.NodePid;

/**   
* @Title: TreeHelper.java 
* @Package com.sunnyit.common.view.tree 
* @Description: TODO
* @author yk
* @date 2015��8��22�� ����9:27:38 
* @version V1.0   
*/
public class TreeHelper {
	
	/**
	* @author yk 
	* @date 2015��8��24�� ����2:36:57 
	* @Title: convertDataToNode 
	* @Description: ������ת��Ϊ���Բ˵�������
	* @param datas ��������ݶ���
	* @return
	* @throws IllegalAccessException
	* @throws IllegalArgumentException    �趨�ļ� 
	* @return List<TreeNode>    �������� 
	* @throws
	 */
	private static <T> List<TreeNode> convertDataToNode(List<T> datas,List<Integer> icons) throws IllegalAccessException, IllegalArgumentException
	{
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		
		/*
		 * ����ת��
		 */
		for(T t:datas)
		{
			Class<?> mclass=t.getClass();
			Field[] fieldes=mclass.getDeclaredFields();
			String id="-1";
			String pid="-1";
			String name="";
			String des="";
			for(Field field:fieldes)
			{
				field.setAccessible(true);
				
				if(field.getAnnotation(NodeID.class)!=null)
				{
					id=(String) field.get(t);
				}
				
				if(field.getAnnotation(NodePid.class)!=null)
				{
					pid=(String) field.get(t);
				}
				
				if(field.getAnnotation(NodeName.class)!=null)
				{
					name=(String) field.get(t);
				}
				
				if(field.getAnnotation(NodeDes.class)!=null)
				{
					des=(String) field.get(t);
				}
				
			}
			TreeNode node=new TreeNode(id, pid, name,des);
			nodes.add(node);
		}
		
		/*
		 * ��ʼ���ڵ�����¼���ϵ
		 */
		for(int i=0;i<nodes.size();i++)
		{
			TreeNode tn=nodes.get(i);
			for(int j=0;j<nodes.size();j++)
			{
				TreeNode tm=nodes.get(j);
				if(tn.getId().equals(tm.getPid()))
				{
					tm.setParent(tn);
					tn.getChildren().add(tm);
				}
			}
		}
		
		/*
		 * ���ýڵ��ͼ����ʽ
		 */
		for(int i=0;i<nodes.size();i++)
		{
			setNodeIcon(nodes.get(i),icons);
		}
		
		return nodes;
	}


	/**
	* @author yk 
	* @date 2015��8��24�� ����2:37:31 
	* @Title: setNodeIcon 
	* @Description: ���ýڵ��ͼ����ʽ
	* @param node    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private static void setNodeIcon(TreeNode node,List<Integer> icons) {
		
		if(node.getChildren().size()>0&&node.isExpand())
		{
			if(icons==null)
			{
				node.setIcon(R.drawable.tree_parent_open);
			}
			else
			{
				if(icons.size()>0&&icons.get(0)!=null)
				{
					node.setIcon(icons.get(0));
				}
				else
				{
					node.setIcon(R.drawable.tree_parent_open);
				}
			}
		}
		else if(node.getChildren().size()>0&&!node.isExpand())
		{
			if(icons==null)
			{
				node.setIcon(R.drawable.tree_parent_close);
			}
			else
			{
				if(icons.size()>1&&icons.get(1)!=null)
				{
					node.setIcon(icons.get(1));
				}
				else
				{
					node.setIcon(R.drawable.tree_parent_close);
				}
			}
		}
		else
		{
			if(icons==null)
			{
				node.setIcon(R.drawable.note_stand48);
			}
			else
			{
				if(icons.size()>2&&icons.get(2)!=null)
				{
					node.setIcon(icons.get(2));
				}
				else
				{
					node.setIcon(R.drawable.note_stand48);
				}
			}	
		}
	}
	
	/**
	* @author yk 
	* @date 2015��8��24�� ����2:57:57 
	* @Title: getSortedTreeNode 
	* @Description: �õ�����õ�listnode
	* @param datas	���������
	* @param defaultExpandLevel Ĭ��չ���ĵȼ�
	* @return
	* @throws IllegalAccessException
	* @throws IllegalArgumentException    �趨�ļ� 
	* @return List<TreeNode>    �������� 
	* @throws
	 */
	public static <T> List<TreeNode> getSortedTreeNode(List<T> datas,int defaultExpandLevel,List<Integer> icons) throws IllegalAccessException, IllegalArgumentException
	{
		int currentLevel=1;
		List<TreeNode> result=new ArrayList<TreeNode>();
		List<TreeNode> nodes=convertDataToNode(datas,icons);
		List<TreeNode> roots=getRootNodes(nodes);
		
		for(TreeNode node:roots)
		{
			addNode(result,node,defaultExpandLevel,currentLevel);
		}
		
		return result;
	}
	
	/**
	* @author yk 
	* @date 2015��8��24�� ����2:59:46 
	* @Title: addNode 
	* @Description: ˳�����ӽڵ�
	* @param result
	* @param node
	* @param defaultExpandLevel
	* @param currentLevel    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private static void addNode(List<TreeNode> result, TreeNode node, int defaultExpandLevel, int currentLevel) {
		result.add(node);
		if(defaultExpandLevel>=currentLevel)
		{
			node.setExpand(true);
		}
		if(node.isLeaf())
		{
			return;
		}
		for(int i=0;i<node.getChildren().size();i++)
		{
			TreeNode n=node.getChildren().get(i);
			addNode(result, n, defaultExpandLevel, currentLevel+1);
		}
	}


	/**
	* @author yk 
	* @date 2015��8��24�� ����3:00:48 
	* @Title: getRootNodes 
	* @Description: ������и��ڵ�
	* @param nodes
	* @return    �趨�ļ� 
	* @return List<TreeNode>    �������� 
	* @throws
	 */
	private static List<TreeNode> getRootNodes(List<TreeNode> nodes) {
		List<TreeNode> root=new ArrayList<TreeNode>();
		for(TreeNode node:nodes)
		{
			if(node.isRoot())
			{
				root.add(node);
			}
		}
		return root;
	}
	
	/**
	* @author yk 
	* @date 2015��8��24�� ����2:58:48 
	* @Title: filterVisibleNodes 
	* @Description: ��ýڵ���������Ҫ����չʾ�����ݵ�list
	* @param datas ����node��list
	* @return    �趨�ļ� 
	* @return List<TreeNode>    �������� 
	* @throws
	 */
	public static List<TreeNode> filterVisibleNodes(List<TreeNode> datas,List<Integer> icons)
	{
		List<TreeNode> result=new ArrayList<TreeNode>();
		for(TreeNode node:datas)
		{
			if(node.isRoot()||node.isParentExpand())
			{
				result.add(node);
				setNodeIcon(node,icons);
			}
		}
		return result;
	}
}


