package test.action;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.dialog.CustomDialog.CancelDialogListener;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**   
* @Title: NoteAddActivity.java 
* @Package com.sunnyit.menu.action 
* @Description: TODO
* @author yk
* @date 2015��8��10�� ����10:35:01 
* @version V1.0   
*/
public class DialogShowActivity extends BaseActivity {
	
	private Button but01;
	private Button but02;
	private Button but03;
	private Button but04;
	private Button but05;
	private Button but06;
	private Button but07;
	private Button but08;
	private Button but09;
	private Button but10;
	private Button but11;
	private Button but12;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogtest);
		initComponent();
		
		but01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click01();
			}
		});
		
		but02.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click02();
			}
		});

		but03.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click03();
			}
		});

		but04.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click04();
			}
		});
		
		but05.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click05();
			}
		});
		
		but06.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click06();
			}
		});
		
		but07.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click07();
			}
		});
		
		but08.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click08();
			}
		});
		
		but09.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				click09();
			}
		});
		
		but10.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					click10();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		but11.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					click11();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		but12.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					click12();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initComponent() {
		// TODO Auto-generated method stub
		but01=(Button)findViewById(R.id.but01);
		but02=(Button)findViewById(R.id.but02);
		but03=(Button)findViewById(R.id.but03);
		but04=(Button)findViewById(R.id.but04);
		but05=(Button)findViewById(R.id.but05);
		but06=(Button)findViewById(R.id.but06);
		but07=(Button)findViewById(R.id.but07);
		but08=(Button)findViewById(R.id.but08);
		but09=(Button)findViewById(R.id.but09);
		but10=(Button)findViewById(R.id.but10);
		but11=(Button)findViewById(R.id.but11);
		but12=(Button)findViewById(R.id.but12);
	}

	private void click01()
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(this);  //�ȵõ�������  
        builder.setTitle("��ʾ"); //���ñ���  
        builder.setMessage("�Ƿ�ȷ���˳�?"); //��������  
        builder.setIcon(R.drawable.info32);//����ͼ�꣬ͼƬid����  
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { //����ȷ����ť  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss(); //�ر�dialog  
                Toast.makeText(DialogShowActivity.this, "ȷ��" + which, Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { //����ȡ����ť  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(DialogShowActivity.this, "ȡ��" + which, Toast.LENGTH_SHORT).show();  
            }  
        });  
        //��������������ˣ���������ʾ����  
        builder.create().show(); 
	}
	
	private void click02()
	{
		final String items[]={"����","����","����"};  
        //dialog��������  
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //�ȵõ�������  
        builder.setTitle("��ʾ"); //���ñ���  
        //builder.setMessage("�Ƿ�ȷ���˳�?"); //��������  
        builder.setIcon(R.drawable.info32);//����ͼ�꣬ͼƬid����  
        //�����б���ʾ��ע���������б���ʾ�Ͳ�Ҫ����builder.setMessage()�ˣ������б������á�  
        builder.setItems(items,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(DialogShowActivity.this, items[which], Toast.LENGTH_SHORT).show();  
  
            }  
        });  
        builder.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(DialogShowActivity.this, "ȷ��", Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.create().show(); 
	}
	
	
	
	private void click03()
	{
		final String items[]={"��","Ů"};  
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //�ȵõ�������  
        builder.setTitle("��ʾ"); //���ñ���  
        builder.setIcon(R.drawable.info32);//����ͼ�꣬ͼƬid����  
        builder.setSingleChoiceItems(items,0,new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                //dialog.dismiss();  
                Toast.makeText(DialogShowActivity.this, items[which], Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(DialogShowActivity.this, "ȷ��", Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.create().show();  
	}
	
	private void click04()
	{
		final String items[]={"����","����","����"};  
        final boolean selected[]={true,false,true};  
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //�ȵõ�������  
        builder.setTitle("��ʾ"); //���ñ���  
        builder.setIcon(R.drawable.info32);//����ͼ�꣬ͼƬid����  
        builder.setMultiChoiceItems(items,selected,new DialogInterface.OnMultiChoiceClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {  
               // dialog.dismiss();  
                Toast.makeText(DialogShowActivity.this, items[which]+isChecked, Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(DialogShowActivity.this, "ȷ��", Toast.LENGTH_SHORT).show();  
                //android���Զ�������ѡ��ĸı�selected�����ֵ��  
                for (int i=0;i<selected.length;i++){  
                    Log.e("hongliang",""+selected[i]);  
                }  
            }  
        });  
        builder.create().show();  
	}
	
	private void click05()
	{
		LayoutInflater lay=LayoutInflater.from(this);
		final View view=lay.inflate(R.layout.dialoglayout, null);
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);  //�ȵõ�������  
        builder.setTitle("��ʾ"); //���ñ���  
        builder.setIcon(R.drawable.info32);//����ͼ�꣬ͼƬid����  
        builder.setView(view);
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { //����ȷ����ť  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss(); //�ر�dialog 
                EditText et=(EditText)view.findViewById(R.id.etUserName);
                Toast.makeText(DialogShowActivity.this, et.getText().toString() + which, Toast.LENGTH_SHORT).show();  
            }  
        });  
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { //����ȡ����ť  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                Toast.makeText(DialogShowActivity.this, "ȡ��" + which, Toast.LENGTH_SHORT).show();  
            }  
        });  
        //��������������ˣ���������ʾ����  
        builder.create().show(); 
	}
	
	ProgressDialog dialog;
	private void click06()
	{
		dialog = ProgressDialog.show(DialogShowActivity.this, "","Loading. Please wait...", true);
		dialog.show();
		//dialog.dismiss();
	}
	
	int m_count;
	ProgressDialog m_pDialog;
	private void click07()
	{
		// TODO Auto-generated method stub
		m_count=0;
		//����ProgressDialog����
		m_pDialog=new  ProgressDialog(DialogShowActivity.this);
		//���ñ���
		m_pDialog.setTitle("����������Ϣ");
		   //���ý��������
		m_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		//������ʾ��Ϣ
		//m_pDialog.setMessage("����һ����������");
		//���ñ���ͼ��
		m_pDialog.setIcon(R.drawable.info32);
		//���ý������Ƿ���ȷ
		m_pDialog.setIndeterminate(false);
		//�Ƿ���԰����˻ؼ�ȡ��
		m_pDialog.setCancelable(true);
		//�����̣߳���ʾ����
		new Thread() {       
             public void run() {     
                try {     
                   while(m_count <= 100) {     
                   // ���߳������ƽ���     
                  m_pDialog.setProgress(m_count++);     
                    Thread.sleep(100);     
                 }     
                   m_pDialog.cancel();     
                 } catch (Exception e) {     
                m_pDialog.cancel();     
                 }     
              }     
          }.start(); 
		//չʾProgressDialog ����
		m_pDialog.show();
	}
	
	private void click08()
	{
		m_count=0;
		//����ProgressDialog����
		m_pDialog=new  ProgressDialog(DialogShowActivity.this);
		//���ñ���
		m_pDialog.setTitle("Բ�ν�������Ϣ");
		//���ý��������
		m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//������ʾ��Ϣ
		m_pDialog.setMessage("����һ��Բ�ν�����");
		//���ñ���ͼ��
		m_pDialog.setIcon(R.drawable.info32);
		//���ý������Ƿ���ȷ
		m_pDialog.setIndeterminate(false);
		//�Ƿ���԰����˻ؼ�ȡ��
		m_pDialog.setCancelable(true);
		//չʾProgressDialog ����
		new Thread() {       
             public void run() {     
                try {     
                   while(m_count <= 100) {     
                   // ���߳������ƽ���     
                  m_pDialog.setProgress(m_count++);     
                    Thread.sleep(100);     
                 }     
                   m_pDialog.cancel();     
                 } catch (Exception e) {     
                m_pDialog.cancel();     
                 }     
              }     
          }.start();     
		m_pDialog.show();
	}
	
	private void click09()
	{
		final CustomDialog cusdialog=new CustomDialog(this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_r,0);
		cusdialog.initProgressBar(R.id.id_progressbar);
		cusdialog.show();
		
		new Thread(new Runnable(){  
            
            public void run(){  
            	
            	for(int i=0;i<=100;i++)
        		{
        			cusdialog.setProgress(i);
        			try {  
                        Thread.sleep(100);  
                    } catch (InterruptedException e) {  
                        // TODO Auto-generated catch block  
                        e.printStackTrace();  
                    } 
        		}
            	cusdialog.dismiss();
            }  
        }).start();  
	}
	
	private void click10() throws InterruptedException
	{
        /*Dialog dialog = new Dialog(this,R.style.dialog);
        View mView = LayoutInflater.from(this).inflate(R.layout.rprogressbar, null);
        //�ص�������仰���ѱ�����͸������Ϊ��ȫ͸�����Ϳ����������Ǹ��Դ�һ�����ͼ�ˡ�
        mView.getBackground().setAlpha(0);
        dialog.setContentView(mView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        
        mProgressBarR = (RoundNumberProgressBar) mView.findViewById(R.id.id_progressbar);  
        //mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE_R);  
        
        Message message=Message.obtain();
		message.obj=MSG_PROGRESS_UPDATE_R;
		mHandler.sendMessage(message);*/
		
		final CustomDialog cusdialog=new CustomDialog(this);
		cusdialog.setViewAndAlpha(R.layout.dialog_custom,0);
		cusdialog.initProgressBar(R.id.id_progressbarh);
		cusdialog.setCancelDialogLiatener(new CancelDialogListener() {
			@Override
			public void onCancelDialog() {
				// TODO Auto-generated method stub
				Toast.makeText(DialogShowActivity.this, "ȡ��", Toast.LENGTH_SHORT).show();  
			}
		});
		cusdialog.show();
		
		new Thread(new Runnable(){  
            
            public void run(){  
            	
            	for(int i=0;i<=100;i++)
        		{
        			cusdialog.setProgress(i);
        			try {  
                        Thread.sleep(100);  
                    } catch (InterruptedException e) {  
                        // TODO Auto-generated catch block  
                        e.printStackTrace();  
                    } 
        		}
            	//cusdialog.dismiss();
            }  
        }).start();  
		
	}
	
	private void click11() throws InterruptedException
	{
        /*Dialog dialog = new Dialog(this,R.style.dialog);
        View mView = LayoutInflater.from(this).inflate(R.layout.rprogressbar, null);
        //�ص�������仰���ѱ�����͸������Ϊ��ȫ͸�����Ϳ����������Ǹ��Դ�һ�����ͼ�ˡ�
        mView.getBackground().setAlpha(0);
        dialog.setContentView(mView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        
        mProgressBarR = (RoundNumberProgressBar) mView.findViewById(R.id.id_progressbar);  
        //mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE_R);  
        
        Message message=Message.obtain();
		message.obj=MSG_PROGRESS_UPDATE_R;
		mHandler.sendMessage(message);*/
		
		final CustomDialog cusdialog=new CustomDialog(this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_v,0);
		cusdialog.initProgressBar(R.id.id_progressbarv);
		cusdialog.setOutCancel(true);
		cusdialog.setCancelDialogLiatener(new CancelDialogListener() {
			@Override
			public void onCancelDialog() {
				// TODO Auto-generated method stub
				Toast.makeText(DialogShowActivity.this, "ȡ��", Toast.LENGTH_SHORT).show();  
			}
		});
		cusdialog.show();
		
		new Thread(new Runnable(){  
            
            public void run(){  
            	
            	for(int i=0;i<=100;i++)
        		{
        			cusdialog.setProgress(i);
        			try {  
                        Thread.sleep(100);  
                    } catch (InterruptedException e) {  
                        // TODO Auto-generated catch block  
                        e.printStackTrace();  
                    } 
        		}
            	//cusdialog.dismiss();
            }  
        }).start();  
		
		/*final CustomDialog cusdialog=new CustomDialog(this);
		cusdialog.setViewAndAlpha(R.layout.hprogressbar,0);
		cusdialog.setText(R.id.tv_dg_context, "��ʾ����");
		cusdialog.show();*/
		
	}
	
	
	private void click12() throws InterruptedException
	{
		final CustomDialog cusdialog=new CustomDialog(this);
		cusdialog.setViewAndAlpha(R.layout.progressbar_rw_orange,0);
		cusdialog.show();
	}
	
}


