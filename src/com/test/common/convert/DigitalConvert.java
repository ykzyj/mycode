package com.sunnyit.common.convert;
/**   
* @Title: DigitalConvert.java 
* @Package com.sunnyit.common.convert 
* @Description: TODO
* @author yk
* @date 2015年11月27日 下午2:15:03 
* @version V1.0   
*/
public class DigitalConvert {
	private static final String[] DIGIT ={"零","一","二","三","四","五","六","七","八","九"}; 
	private static final String[] UNIT = {"","十","百","千","万"};
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


