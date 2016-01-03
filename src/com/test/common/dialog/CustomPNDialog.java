package com.sunnyit.common.dialog;

import com.sunnyit.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**   
* @Title: CustomPNDialog.java 
* @Package com.sunnyit.common.dialog 
* @Description: TODO
* @author yk
* @date 2015��8��19�� ����3:55:15 
* @version V1.0   
*/
public class CustomPNDialog extends Dialog {
    private TextView tv_dg_content;
    private Button positiveButton, negativeButton;
    private TextView tv_dg_title;
 
    /**
    * @author yk
    * @date 2015��8��19�� ����5:17:15 
    * @param context    �趨�ļ�
     */
    public CustomPNDialog(Context context) {
        super(context,R.style.newdialog);
        setCustomDialog();
    }
 
    /**
    * @author yk 
    * @date 2015��8��19�� ����5:14:54 
    * @Title: setCustomDialog 
    * @Description: ��ʼ������Ԫ��
    * @return void    �������� 
    * @throws
     */
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pn, null);
        tv_dg_title = (TextView) mView.findViewById(R.id.tv_dg_title);
        tv_dg_content = (TextView) mView.findViewById(R.id.tv_dg_content);
        positiveButton = (Button) mView.findViewById(R.id.but_dg_positiveButton);
        negativeButton = (Button) mView.findViewById(R.id.but_dg_negativeButton);
        super.setContentView(mView);
    }
    
    public void setTitleVisible(int v) {
    	tv_dg_title.setVisibility(v);
	}
    
    /**
    * @author yk 
    * @date 2015��8��19�� ����5:15:16 
    * @Title: setTitleText 
    * @Description: ���ñ���
    * @param titletext    �趨�ļ� 
    * @return void    �������� 
    * @throws
     */
    public void setTitleText(String titletext) {
    	tv_dg_title.setText(titletext);
	}
    
    /**
    * @author yk 
    * @date 2015��8��19�� ����5:15:30 
    * @Title: setContentText 
    * @Description: ������ʾ����
    * @param contenttext    �趨�ļ� 
    * @return void    �������� 
    * @throws
     */
    public void setContentText(String contenttext) {
    	tv_dg_content.setText(contenttext);
	}
    
    /**
    * @author yk 
    * @date 2015��8��19�� ����5:15:42 
    * @Title: setPositiveButText 
    * @Description: ���û���but������
    * @param buttext    �趨�ļ� 
    * @return void    �������� 
    * @throws
     */
    public void setPositiveButText(String buttext) {
    	positiveButton.setText(buttext);
	}
    
    /**
    * @author yk 
    * @date 2015��8��19�� ����5:16:08 
    * @Title: setNegativeButText 
    * @Description: �������but������
    * @param buttext    �趨�ļ� 
    * @return void    �������� 
    * @throws
     */
    public void setNegativeButText(String buttext) {
    	negativeButton.setText(buttext);
	}
     
    /**
    * @author yk 
    * @date 2015��8��19�� ����5:16:41 
    * @Title: setOnPositiveListener 
    * @Description: ��������
    * @param listener    �趨�ļ� 
    * @return void    �������� 
    * @throws
     */
    public void setOnPositiveListener(View.OnClickListener listener){ 
        positiveButton.setOnClickListener(listener); 
    } 
    
    /**
    * @author yk 
    * @date 2015��8��19�� ����5:16:58 
    * @Title: setOnNegativeListener 
    * @Description: ��������
    * @param listener    �趨�ļ� 
    * @return void    �������� 
    * @throws
     */
    public void setOnNegativeListener(View.OnClickListener listener){ 
        negativeButton.setOnClickListener(listener); 
    }
}


