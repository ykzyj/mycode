package com.sunnyit.enforcement.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.activity.SelectPhotoActivity;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TabDialogView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.data.CheckTablesNormalAdapter;
import com.sunnyit.enforcement.data.CheckTablesNormalAdapter.QualifiedListener;
import com.sunnyit.enforcement.model.CheckTables;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @Title: HiddenList.java
 * @Package com.sunnyit.hiddencheck.action
 * @Description: TODO
 * @author yk
 * @date 2015年9月7日 下午3:27:12
 * @version V1.0
 */
public class HiddenStandDatilAddActivity extends BaseActivity {

	private TopBarView topbar_endatil_standcheck_add;
	private ListView lv_stand_table_item;
	private String imagePath;
	private int position;
	private Standard_CK_Table standTable;
	private String tableid;
	private static final int TAKE_REQUEST_CODE = 1;
	private List<CheckTables> mData;
	private CheckTablesNormalAdapter mAdapter;
	private Map<String, Boolean> checkMap = new HashMap<String, Boolean>();
	private boolean haveReview = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_endatil_standcheck_add);

		standTable = (Standard_CK_Table) getIntent().getSerializableExtra(
				"Standard_CK_Table");
		tableid = getIntent().getStringExtra("tableid");

		initComponent();

		initAdapter();

		topbar_endatil_standcheck_add.setTopBarClick(new ITopBarClick() {

			@Override
			public void onClickRightBut() {
				final CustomDialog cusdialog = new CustomDialog(
						HiddenStandDatilAddActivity.this);
				cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue, 0);
				cusdialog.setOutCancel(false);
				cusdialog.show();

				new Thread(new Runnable() {

					public void run() {

						standTable.setIsExistDanger("YES");// 无隐患
						if (checkMap.size() < mData.size()) {
							standTable.setCk_state("CKING");// 检查中
							for (Map.Entry<String, Boolean> entry : checkMap
									.entrySet()) {
								if (!entry.getValue()) {
									standTable.setIsExistDanger("NO");// 有隐患
									break;
								}
							}
						} else {
							for (Map.Entry<String, Boolean> entry : checkMap
									.entrySet()) {
								if (!entry.getValue()) {
									haveReview = true;
									standTable.setCk_state("CKEND");// 未整改
									break;
								}
							}
							if (!haveReview) {
								standTable.setCk_state("NONEED");// 无需整改
								standTable.setIsExistDanger("YES");// 无隐患
							} else {
								standTable.setIsExistDanger("NO");// 有隐患
							}

						}

						SqlOperate<Standard_CK_Table> operaterStandard_CK_Table = new SqlOperate<Standard_CK_Table>(
								HiddenStandDatilAddActivity.this,
								Standard_CK_Table.class);
						operaterStandard_CK_Table.saveContent(standTable);
						operaterStandard_CK_Table.close();

						for (CheckTables ct : mData) {
							Standard_CK_Table_Item scti = new Standard_CK_Table_Item();
							scti.setId(UUID.randomUUID().toString());
							scti.setCk_id(standTable.getCk_id());
							scti.setItemId(ct.getSc_uuId());
							scti.setItem_content_one(ct.getH_content_one());
							scti.setItem_content_two(ct.getH_content_two());
							scti.setItem_content_three(ct.getH_content_three());
							scti.setItem_content_four(ct.getH_content_four());
							scti.setItem_content_five(ct.getH_content_five());
							scti.setItem_description(ct.getH_description());
							scti.setItem_basis(ct.getH_basis());
							scti.setItem_dangerLevel(ct.getH_dangerLevel());
							scti.setItem_checkCircle(ct.getH_checkCircle());
							scti.setItem_reportLevel(ct.getH_reportLevel());
							scti.setH_seq(ct.getH_seq());
							scti.setItem_punishment(ct.getH_punishment());
							Boolean ID = checkMap.get(ct.getSc_uuId());
							if (ID == null) {
								scti.setItem_isQualified("");
							} else if (ID == true) {
								scti.setItem_isQualified("YES");
								scti.setItem_repairState("NONEED");
							} else if (ID == false) {
								scti.setItem_isQualified("NO");
								scti.setItem_repairState("NOYET");
							}

							SqlOperate<Standard_CK_Table_Item> operaterStandard_CK_Table_Item = new SqlOperate<Standard_CK_Table_Item>(
									HiddenStandDatilAddActivity.this,
									Standard_CK_Table_Item.class);
							operaterStandard_CK_Table_Item.saveContent(scti);
							operaterStandard_CK_Table_Item.close();

						}

						Message msg = Message.obtain();
						msg.what = 0;
						mHandler.sendMessage(msg);

						cusdialog.dismiss();
					}
				}).start();
			}

			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				setResult(0, intent);
				finish();
			}
		});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(final Message msg) {
			int flag = msg.what;
			if (flag == 0) {
				if (haveReview) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							HiddenStandDatilAddActivity.this); // 先得到构造器
					builder.setTitle("提示"); // 设置标题
					builder.setMessage("存在不合格项，要立即处理吗?"); // 设置内容
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() { // 设置确定按钮
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss(); // 关闭dialog
									Intent intent = new Intent();
									intent.putExtra("Ck_ID",
											standTable.getCk_id());
									setResult(1, intent);
									finish();
								}
							});
					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() { // 设置取消按钮
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss(); // 关闭dialog
									Intent intent = new Intent();
									setResult(2, intent);
									finish();
								}
							});
					// 参数都设置完成了，创建并显示出来
					builder.create().show();
				} else {
					Toast.makeText(HiddenStandDatilAddActivity.this, "保存成功",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					setResult(2, intent);
					finish();
				}
			}
		};
	};

	private void initAdapter() {
		String conStr = " where sc_tableId='" + tableid + "' ";
		SqlOperate<CheckTables> operaterCheckTables = new SqlOperate<CheckTables>(
				HiddenStandDatilAddActivity.this, CheckTables.class);
		mData = operaterCheckTables.SelectEntitysByCondition(conStr);
		operaterCheckTables.close();
		mAdapter = new CheckTablesNormalAdapter(this, mData,
				R.layout.hidden_endatil_standcheck_item);
		lv_stand_table_item.setAdapter(mAdapter);
		lv_stand_table_item
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						Intent intent = new Intent(
								HiddenStandDatilAddActivity.this,
								SelectPhotoActivity.class);
						intent.putExtra("COUNT", 20);
						intent.putExtra("POSITION", arg2);
						ArrayList<String> imagePathList = new ArrayList<String>();
						String imagePath = (String) arg1
								.getTag(R.id.tag_image_path); 
						if (imagePath != null) {
							String[] imagePathArray = imagePath.split(":");
							for (int i = 0; i < imagePathArray.length; i++) {
								imagePathList.add(imagePathArray[i].replaceAll(
										" ", "").trim());
							}
							intent.putExtra("DATA", imagePathList);
						}
						startActivityForResult(intent, TAKE_REQUEST_CODE);
						return true;
					}
				});

		lv_stand_table_item.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				/*
				 * CheckTables ct=mData.get(arg2); AlertDialog.Builder
				 * builder=new
				 * AlertDialog.Builder(HiddenStandDatilAddActivity.this);
				 * //先得到构造器 builder.setTitle("依据说明"); //设置标题
				 * builder.setMessage(ct.getH_basis()); //设置内容
				 * builder.setIcon(R.drawable.note_stand48);//设置图标，图片id即可
				 * builder.setNegativeButton("取消", new
				 * DialogInterface.OnClickListener() { //设置取消按钮
				 * 
				 * @Override public void onClick(DialogInterface dialog, int
				 * which) { dialog.dismiss(); } }); //参数都设置完成了，创建并显示出来
				 * builder.create().show();
				 */

				CheckTables ct = mData.get(arg2);

				final CustomDialog cusdialog = new CustomDialog(
						HiddenStandDatilAddActivity.this);
				TabDialogView tabDialogView = new TabDialogView(
						HiddenStandDatilAddActivity.this);
				tabDialogView.addContent("依据说明", ct.getH_basis());
				tabDialogView.addContent("罚则", ct.getH_punishment());
				tabDialogView.addBut("取消").setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								cusdialog.dismiss();
							}
						});
				cusdialog.setView(tabDialogView, 0);
				cusdialog.show();
			}
		});

		mAdapter.setQualifiedListener(new QualifiedListener() {

			@Override
			public void setItenIsQualified(CheckTables checkTable,
					boolean checked) {
				// TODO Auto-generated method stub

				Boolean ID = checkMap.get(checkTable.getSc_uuId());
				if (ID == null) {
					checkMap.put(checkTable.getSc_uuId(), checked);
				} else {
					checkMap.remove(checkTable.getSc_uuId());
					checkMap.put(checkTable.getSc_uuId(), checked);
				}

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_REQUEST_CODE:
				if (data != null) {
					position = data.getIntExtra("POSITION", 0);
					ArrayList<String> arrayList = data
							.getStringArrayListExtra("DATA");
					imagePath = arrayList.toString().replaceAll(",", ":")
							.replace("[", "").replace("]", "");
					lv_stand_table_item.getChildAt(position).setTag(
							R.id.tag_image_path, imagePath);
				}
				break;
			}
		}
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		topbar_endatil_standcheck_add = (TopBarView) findViewById(R.id.topbar_endatil_standcheck_add);
		lv_stand_table_item = (ListView) findViewById(R.id.lv_stand_table_item);
	}

}
