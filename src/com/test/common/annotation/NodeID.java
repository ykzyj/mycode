package com.sunnyit.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
* @Title: NodeID.java 
* @Package com.sunnyit.common.annotation 
* @Description: TODO
* @author yk
* @date 2015��8��22�� ����10:14:28 
* @version V1.0   
*/

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NodeID {
}


