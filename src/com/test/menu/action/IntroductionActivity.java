package com.sunnyit.menu.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.view.TopBarView;

import android.os.Bundle;
import android.widget.TextView;


public class IntroductionActivity extends BaseActivity 
{
	private TopBarView topbar_pwup ;
	
	TextView tv_introduce01,tv_introduce02,tv_introduce03,tv_introduce04;
	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftmenu_introduction); 
		initComponent();
		
		topbar_pwup.setTopBarClick(new TopBarView.ITopBarClick() {
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
        
        tv_introduce01.setText("        ɽ���Ƽ���Ϊһ�Ҹ�ֲ�����ĸ߿Ƽ���Ӫ��ҵ��"
        		+ "һֱ������������ҵ������ϵͳ���ɡ��������ܹ��̡���������ͼ������񡣾�������������"
        		+ "ƾ�������ۺ�ļ���ʵ������ȫ���ʽ����������õ��������Ѿ���չ��Ϊ����IT��ҵ����ͷ����"
        		+ "�����γɡ�������������������������ȫ�����ķ�չ��֣��ɹ��е�������������ҵ��"
        		+ "����Ժ�������ڡ���Դ������������Ķ�����Ϣ�����̣���������ʡ�г�ռ���ʾ�ͬ��ҵǰ�С�");
        tv_introduce02.setText("        ��˾ӵ�й�ҵ����Ϣ����ϵͳ���ɶ������ʣ�˫���϶������ұ��ܾ��������ʡ�"
        		+ "CMMI���������϶���ɽ��������ʡ�ص������ҵ��ӵ�ж�������֪ʶ��Ȩ�������Ʒ�����ж����Ʒ���"
        		+ "��ҵ����Ϣ�����Ƽ��ɹ������ҹ�˾��ҵ����������2013��������ʡ�϶���ҵ��������������"
        		+ "ɽ���Ƽ�������ʡ������ҵ����������Ϊ�����С����塱����ʮ�塱������¼�����ҵ����á������������Ʋ�ҵ100ǿ����"
        		+ "��������ҵ���㼯���̡���ʡ��óί������ʡ����ҵ��Ϣ���������λ�������ƺţ��������������ܿ�������Ϊȫ��"
        		+ "�ɳ�����100����СIT��ҵ����˾����������ϵ��ȫ��ͨ��ISO9001��֤��");
        tv_introduce03.setText("        ɽ����˾�ķ�չ���������ڲ�Ʒ�������������һ�廯�������������������ӵ�з����г���չ��רҵ"
        		+ "���˲š�����ľ�Ӫ����ͳ�Զ��ս�Է�չĿ�꣬���������г����ԣ��˽�ͻ������ṩ���ʷ���"
        		+ "ע�ؽ�����������մﵽ�û����⡣");
        tv_introduce04.setText("        �ͻ��������Ƿ�չ���г��������ǻ����������������Ƕ����������Ϣ��ҵ��������ķ�"
        		+ "չ��ɽ���Ƽ�����ץ��ʷ�������������С�ע��Ʒ�ʣ�ȫ�ķ��񡱵ľ�Ӫ����ش��£�Ը�����"
        		+ "����ҵ�����Ѿ��Ϻ�������ͬ�ɳ���������Բ��õ�δ����");
        
	}
	
    /**
	 * ��ʼ���û����
	 */
	private void initComponent()
	{
		topbar_pwup = (TopBarView)findViewById(R.id.topbar_pwup);
		tv_introduce01=(TextView)findViewById(R.id.tv_introduce01);
		tv_introduce02=(TextView)findViewById(R.id.tv_introduce02);
		tv_introduce03=(TextView)findViewById(R.id.tv_introduce03);
		tv_introduce04=(TextView)findViewById(R.id.tv_introduce04);
    }
}