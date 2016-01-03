package com.sunnyit.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
* @Title: NodeName.java 
* @Package com.sunnyit.common.annotation 
* @Description: TODO
* @author yk
* @date 2015年8月22日 上午10:16:36 
* @version V1.0   
*/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NodeDes {

}


