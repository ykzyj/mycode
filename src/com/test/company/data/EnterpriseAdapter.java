package com.sunnyit.company.data;

import java.util.List;

import com.sunnyit.R;
import com.sunnyit.common.adapter.AdapterUtil;
import com.sunnyit.common.adapter.ViewHolderUtil;
import com.sunnyit.company.model.SimpleEnterprise;

import android.content.Context;
import android.view.View;

public class EnterpriseAdapter extends AdapterUtil<SimpleEnterprise> {
	
	private int mImg;

	public EnterpriseAdapter(Context context, List<SimpleEnterprise> datas, int layoutId,int imgId) {
		super(context, datas, layoutId);
		// TODO Auto-generated constructor stub
		this.mImg=imgId;
	}

	@Override
	public void initShowDate(ViewHolderUtil viewHolder, SimpleEnterprise t, int position) {
		// TODO Auto-generated method stub
		if(mImg==-1)
		{
			switch (t.getE_belongIndustry()) {
			case "����ȼ��":
				viewHolder.setImageResource(R.id.img_info,R.drawable.ranqi96);
				break;
			case "�̻�����":
				viewHolder.setImageResource(R.id.img_info,R.drawable.yanhua96);
				break;
			case "��ú��ɽ":
				viewHolder.setImageResource(R.id.img_info,R.drawable.feimei96);
				break;
			case "����ó":
				viewHolder.setImageResource(R.id.img_info,R.drawable.gongshang96);
				break;
			case "��ͨ����":
				viewHolder.setImageResource(R.id.img_info,R.drawable.jiaotong96);
				break;
			case "Σ�ջ�ѧƷ":
				viewHolder.setImageResource(R.id.img_info,R.drawable.danger96);
				break;
			case "����ʩ��":
				viewHolder.setImageResource(R.id.img_info,R.drawable.jianzhu96);
				break;
			default:
				viewHolder.setImageResource(R.id.img_info,R.drawable.qita96);
				break;
			}
		}
		else
		{
			viewHolder.setImageResource(R.id.img_info,Integer.valueOf(mImg));
		}
		
		viewHolder.setText(R.id.txt_company_name, t.getE_companyName());
		if(t.getE_departmentName()!=null)
		{
			if(!t.getE_departmentName().equals("other"))
			{
				viewHolder.setText(R.id.txt_regulatory_unit, t.getE_departmentName());
			}
			else
			{
				viewHolder.setText(R.id.txt_regulatory_unit, "����");
			}
		}
		viewHolder.setText(R.id.txt_company_legalperson, t.getE_legal_representative());
		if(t.getE_contact_phone()==null||t.getE_contact_phone().equals(""))
		{
			viewHolder.setViewVisible(R.id.txt_company_phone, View.GONE);
		}
		else
		{
			viewHolder.setViewVisible(R.id.txt_company_phone, View.VISIBLE);
			viewHolder.setText(R.id.txt_company_phone, t.getE_contact_phone());
		}
	}

	
	
}
