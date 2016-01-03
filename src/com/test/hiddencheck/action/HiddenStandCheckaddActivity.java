package com.sunnyit.hiddencheck.action;

import java.util.ArrayList;
import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.activity.ImgShowActivity;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemClickListener;
import com.sunnyit.common.treeview.TreeListViewAdapter.TreeItemLongClickListener;
import com.sunnyit.common.treeview.TreeNode;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import test.action.DialogShowActivity;
import test.data.TreeViewInitAdapter;
import test.model.FileBean;

/**   
* @Title: HiddenList.java 
* @Package com.sunnyit.hiddencheck.action 
* @Description: TODO
* @author yk
* @date 2015��9��7�� ����3:27:12 
* @version V1.0   
*/
public class HiddenStandCheckaddActivity extends BaseActivity {
	
	private TopBarView topbar_standcheck_add;
	
	private ListView mListView;
	private List<FileBean> lf;
	private TreeViewInitAdapter<FileBean> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_standcheck_add);
		
		initComponent();
		initDate();
		
		topbar_standcheck_add.setTopBarClick(new ITopBarClick() {
			
			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		try {
			mAdapter=
					new TreeViewInitAdapter<FileBean>(this, lf, mListView, R.layout.tree_item,true,1,null);
			mListView.setAdapter(mAdapter);
			
			mAdapter.setOnTreeItenClickListener(new TreeItemClickListener() {

				@Override
				public void onItemClick(TreeNode node, int position) {
					// TODO Auto-generated method stub
					if(node.isLeaf())
					{
						//Toast.makeText(HiddenStandCheckaddActivity.this, node.getName(), Toast.LENGTH_SHORT).show();
						AlertDialog.Builder builder=new AlertDialog.Builder(HiddenStandCheckaddActivity.this);  //�ȵõ�������  
				        builder.setTitle("��Ϣչʾ"); //���ñ���  
				        builder.setMessage(node.getDes()); //��������  
				        builder.setIcon(R.drawable.note_stand48);//����ͼ�꣬ͼƬid����  
				        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { //����ȡ����ť  
				            @Override  
				            public void onClick(DialogInterface dialog, int which) {  
				                dialog.dismiss();  
				            }  
				        });  
				        //��������������ˣ���������ʾ����  
				        builder.create().show(); 
					}
				}
			});
			
			mAdapter.setOnTreeItenLongClickListener(new TreeItemLongClickListener() {

				@Override
				public void onItemLongClick(TreeNode node, int position) {
					// TODO Auto-generated method stub
					if(node.isLeaf())
					{
						Intent intent=new Intent(HiddenStandCheckaddActivity.this, ImgShowActivity.class);
			       		startActivity(intent);
					}
				}
			});
			
		} catch (IllegalAccessException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_standcheck_add=(TopBarView) findViewById(R.id.topbar_standcheck_add);
		mListView=(ListView) findViewById(R.id.list_standcheck_add);
	}
	
	private void initDate() {
		lf=new ArrayList<FileBean>();
		FileBean fb1=new FileBean("1", "0", "��������������","");
		lf.add(fb1);
		FileBean fb2=new FileBean("2", "0", "�ֳ�����������","");
		lf.add(fb2);
		FileBean fb3=new FileBean("3", "1", "�ش�Σ��Դ����","");
		lf.add(fb3);
		FileBean fb4=new FileBean("4", "1", "ְҵ����","");
		lf.add(fb4);
		FileBean fb5=new FileBean("5", "2", "��ҵ����","");
		lf.add(fb5);
		FileBean fb6=new FileBean("6", "2", "�������","");
		lf.add(fb6);
		
		FileBean fb7=new FileBean("7", "3", "�Ǽǽ�������ȱ��","");
		lf.add(fb7);
		FileBean fb8=new FileBean("8", "4", "ְҵ�������ȱ��","");
		lf.add(fb8);
		FileBean fb9=new FileBean("9", "4", "����","");
		lf.add(fb9);
		FileBean fb10=new FileBean("10", "5", "����","");
		lf.add(fb10);
		FileBean fb11=new FileBean("11", "6", "����","");
		lf.add(fb11);
		
		FileBean fb12=new FileBean("12", "7", "δ�Ա�ʶȷ�ϵ��ش�Σ��Դ��ʱ��������еǼǽ���������",
				"��Σ�ջ�ѧƷ�ش�Σ��Դ�ල�������й涨�����ܾ����40�ţ��ڶ�ʮ������Σ�ջ�ѧƷ��λ�½����Ľ�������Σ�ջ�ѧƷ������Ŀ��Ӧ���ڽ�����Ŀ��������ǰ����ش�Σ��Դ�ı�ʶ����ȫ�����ͷּ����Ǽǽ����������������ڵ��ؼ�����������ȫ�����ල�����ű�����");
		lf.add(fb12);
		FileBean fb13=new FileBean("13", "8", "δ���涨����ְҵ��������",
				"����������ְҵ�����ල����涨�����ܾ����47�ţ�����ʮһ��: ְҵ�����໤����Ӧ�������Ͷ��ߵ�ְҵʷ��ְҵ��Σ���Ӵ�ʷ��ְҵ�������������������ְҵ�����Ƶ��йظ��˽������ϡ�");
		lf.add(fb13);
		FileBean fb14=new FileBean("14", "9", "δ����ع涨��ְҵ�����ߵ���ԭ��λ�ȡ�",
				"��ְҵ�����η���(��ϯ���52��)����ʮ�������Բ����˼�������ԭ������ְҵ�����ˣ�Ӧ������ԭ��λ�������ư��á�");
		lf.add(fb14);
		FileBean fb15=new FileBean("15", "9", "δ������ȫְҵ�����������ϡ�",
				"����������ְҵ�����ල����涨�����ܾ����47�ţ�����ʮ������ְҵ�����������ϰ�����ְҵ�������������ļ���ְҵ������������ƶȡ�������̣���������ְҵ��Σ�����������嵥����λ�ֲ��Լ���ҵ��Ա�Ӵ���������ϣ���ʮ����Ҫ��	");
		lf.add(fb15);
		FileBean fb16=new FileBean("16", "10", "����Ӧ����������ɢָʾ������Ҫ��",
				"������Ӧ����������ɢָʾϵͳ����GB17945-2010��6��");
		lf.add(fb16);
		FileBean fb17=new FileBean("17", "10", "����վ��ҵ������ֲ������ֲ�",
				"���������ͼ���վ�����ʩ���淶����GB50156-2012��12.3.1������վ��ҵ���ڲ�����ֲ����ֲ�");
		lf.add(fb17);
		FileBean fb18=new FileBean("18", "11", "δ�����ʹ�ú�ķ�����Ʒδ����ά���ͱ���",
				"���������װ���䱸����Ҫ�󡷣�GB/T29510-2013��8.17�������ʹ�ú�ķ�����Ʒδ���ղ�ƷҪ������Խ���ά���ͱ��ܣ��Կ�����ɻ�����Ⱦ���ж��к���Ʒδ���й���δ�����ջء�ͳһ����");
		lf.add(fb18);
		FileBean fb19=new FileBean("19", "11", "δ�����ʹ�ú�ķ�����Ʒδ����ά���ͱ���",
				"���������װ���䱸����Ҫ�󡷣�GB/T29510-2013��8.17�������ʹ�ú�ķ�����Ʒδ���ղ�ƷҪ������Խ���ά���ͱ��ܣ��Կ�����ɻ�����Ⱦ���ж��к���Ʒδ���й���δ�����ջء�ͳһ����");
		lf.add(fb19);
		
	}
	
}


