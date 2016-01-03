package test.action;
/**   
* @Title: methodTest.java 
* @Package test.action 
* @Description: TODO
* @author yk
* @date 2015年11月13日 下午5:49:23 
* @version V1.0   
*/
public class methodTest {
	
	private static final String[] DIGIT ={"零","一","二","三","四","五","六","七","八","九"}; 
	private static final String[] UNIT = {"","十","百","千","万"};
	private static String digital2China(int c) {
		StringBuffer result=new StringBuffer();
		int i=0;
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
		if(c>9&&c<20)
		{
			return result.substring(1);
		}
		return result.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(digital2China(10)); 
	}

}


