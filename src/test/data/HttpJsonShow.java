package test.data;

import java.util.List;

import com.sunnyit.common.thread.JsonListUtil;
import com.sunnyit.common.thread.MoreDateJsonList;
import com.sunnyit.common.view.ClickLoadListview;
import com.sunnyit.common.view.ClickLoadListview.IClickLoadListListener;

import android.content.Context;
import android.os.Handler;
import test.model.mooc;

/**   
* @Title: HttpJsonShow.java 
* @Package test.data 
* @Description: TODO
* @author yk
* @date 2015年8月3日 下午3:42:54 
* @version V1.0   
*/
public class HttpJsonShow extends JsonListUtil<mooc> implements IClickLoadListListener {
	
	//private ScrollLoadListview mlistview;
	private ClickLoadListview mlistview;
	private Context mcontext;
	private int mlayoutId;
	private ImoocAdapter mAdapter=null;
	private List<mooc> mdata;
	private Class mclass;

	public HttpJsonShow(Context context, String url, ClickLoadListview listview, int layoutId) {
		super(url);
		// TODO Auto-generated constructor stub
		this.mlistview=listview;
		this.mcontext=context;
		this.mlayoutId=layoutId;
		mlistview.setIClickLoadListListener(this);
	}

	@Override
	public void initShowData(List<mooc> datas, Class cla) {
		// TODO Auto-generated method stub
		mdata=datas;
		this.mclass=cla;
		mAdapter=new ImoocAdapter(mcontext, datas, mlayoutId);
		mlistview.setAdapter(mAdapter);
	}
	
	@Override
	public void onLoad(Handler handler) {
		// TODO Auto-generated method stub
		String urls="http://www.imooc.com/api/teacher?type=4&num=3";
		if(mAdapter!=null)
		{
			new MoreDateJsonList<mooc>(urls,mAdapter, mdata, mlistview,mclass).start();
		}
	}

}


