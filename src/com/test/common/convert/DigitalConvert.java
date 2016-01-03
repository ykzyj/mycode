package com.sunnyit.common.convert;
/**   
* @Title: DigitalConvert.java 
* @Package com.sunnyit.common.convert 
* @Description: TODO
* @author yk
* @date 2015��11��27�� ����2:15:03 
* @version V1.0   
*/
public class DigitalConvert {
	private static final String[] DIGIT ={"��","һ","��","��","��","��","��","��","��","��"}; 
	private static final String[] UNIT = {"","ʮ","��","ǧ","��"};
	public static String digital2China(int c) {
		StringBuffer result=new StringBuffer();
		int i=0;
		int temp=c;
		do {
			int m=c%10;
			if(m!=0||result.length()!=0)
			{
				result.insert(0, UNIT[i]);
				result.insert(0, DIGIT[m]);
			}
			c=c/10;
			i++;
		} while (c>0);
		if(temp>9&&temp<20)
		{
			return result.substring(1);
		}
		return result.toString();
	}
}


