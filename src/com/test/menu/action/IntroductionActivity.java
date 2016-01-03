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
        
        tv_introduce01.setText("        山利科技作为一家根植本土的高科技民营企业，"
        		+ "一直致力于面向行业的网络系统集成、建筑智能工程、软件开发和技术服务。经过多年历练，"
        		+ "凭借自身雄厚的技术实力、安全的资金运作及良好的信誉，已经发展成为西北IT行业的排头兵，"
        		+ "基本形成“立足陕西、面向西北、辐射全国”的发展格局，成功承担西北地区企事业、"
        		+ "科研院所、金融、能源、政府等领域的多项信息化工程，在西北五省市场占有率居同行业前列。");
        tv_introduce02.setText("        公司拥有工业和信息化部系统集成二级资质，双软认定，国家保密局涉密资质、"
        		+ "CMMI三级资质认定。山利是陕西省重点软件企业，拥有多项自主知识产权的软件产品，并有多项产品获得"
        		+ "工业和信息化厅科技成果奖，我公司企业技术中心于2013年获得陕西省认定企业技术中心荣誉。"
        		+ "山利科技是陕西省信用企业，连续被评为西安市“九五”、“十五”优秀高新技术企业，获得“西部开发优势产业100强”、"
        		+ "“金融行业优秀集成商”、省经贸委“陕西省企事业信息化优秀服务单位”荣誉称号，曾被《互联网周刊》评比为全国"
        		+ "成长最快的100家中小IT企业。公司质量管理体系已全面通过ISO9001认证。");
        tv_introduce03.setText("        山利公司的发展，不仅在于产品、技术、服务和一体化解决方案，更在于我们拥有符合市场发展的专业"
        		+ "化人才、成熟的经营理念和长远的战略发展目标，并且明晰市场特性，了解客户需求，提供优质服务，"
        		+ "注重解决方案，最终达到用户满意。");
        tv_introduce04.setText("        客户给了我们发展，市场给了我们机遇，竞争给了我们动力。面对信息产业日新月异的发"
        		+ "展，山利科技将紧抓历史机遇，继续秉承“注重品质，全心服务”的经营理念开拓创新，愿与广大客"
        		+ "户及业界朋友精诚合作，共同成长，共创光辉灿烂的未来！");
        
	}
	
    /**
	 * 初始化用户组件
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