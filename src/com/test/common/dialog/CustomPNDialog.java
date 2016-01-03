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
* @date 2015年8月19日 下午3:55:15 
* @version V1.0   
*/
public class CustomPNDialog extends Dialog {
    private TextView tv_dg_content;
    private Button positiveButton, negativeButton;
    private TextView tv_dg_title;
 
    /**
    * @author yk
    * @date 2015年8月19日 下午5:17:15 
    * @param context    设定文件
     */
    public CustomPNDialog(Context context) {
        super(context,R.style.newdialog);
        setCustomDialog();
    }
 
    /**
    * @author yk 
    * @date 2015年8月19日 下午5:14:54 
    * @Title: setCustomDialog 
    * @Description: 初始化界面元素
    * @return void    返回类型 
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
    * @date 2015年8月19日 下午5:15:16 
    * @Title: setTitleText 
    * @Description: 设置标题
    * @param titletext    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void setTitleText(String titletext) {
    	tv_dg_title.setText(titletext);
	}
    
    /**
    * @author yk 
    * @date 2015年8月19日 下午5:15:30 
    * @Title: setContentText 
    * @Description: 设置提示内容
    * @param contenttext    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void setContentText(String contenttext) {
    	tv_dg_content.setText(contenttext);
	}
    
    /**
    * @author yk 
    * @date 2015年8月19日 下午5:15:42 
    * @Title: setPositiveButText 
    * @Description: 设置积极but的名称
    * @param buttext    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void setPositiveButText(String buttext) {
    	positiveButton.setText(buttext);
	}
    
    /**
    * @author yk 
    * @date 2015年8月19日 下午5:16:08 
    * @Title: setNegativeButText 
    * @Description: 设计消极but的名称
    * @param buttext    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void setNegativeButText(String buttext) {
    	negativeButton.setText(buttext);
	}
     
    /**
    * @author yk 
    * @date 2015年8月19日 下午5:16:41 
    * @Title: setOnPositiveListener 
    * @Description: 积极监听
    * @param listener    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void setOnPositiveListener(View.OnClickListener listener){ 
        positiveButton.setOnClickListener(listener); 
    } 
    
    /**
    * @author yk 
    * @date 2015年8月19日 下午5:16:58 
    * @Title: setOnNegativeListener 
    * @Description: 消极监听
    * @param listener    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void setOnNegativeListener(View.OnClickListener listener){ 
        negativeButton.setOnClickListener(listener); 
    }
}


