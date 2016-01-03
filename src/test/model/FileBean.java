package test.model;

import com.sunnyit.common.annotation.NodeDes;
import com.sunnyit.common.annotation.NodeID;
import com.sunnyit.common.annotation.NodeName;
import com.sunnyit.common.annotation.NodePid;

/**   
* @Title: FileBean.java 
* @Package com.sunnyit.system.data 
* @Description: TODO
* @author yk
* @date 2015年8月21日 下午5:48:39 
* @version V1.0   
*/
public class FileBean {
	@NodeID
	private String id;
	@NodePid
	private String pid;
	@NodeName
	private String label;
	@NodeDes
	private String desc;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public FileBean(String id, String pid, String label
			, String desc) {
		this.id = id;
		this.pid = pid;
		this.label = label;
		this.desc=desc;
	}
}


