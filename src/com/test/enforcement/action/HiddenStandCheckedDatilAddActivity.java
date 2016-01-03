package com.sunnyit.enforcement.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.activity.SelectPhotoActivity;
import com.sunnyit.common.async.HttpClientNpAsyncPost;
import com.sunnyit.common.dialog.CustomDialog;
import com.sunnyit.common.sqlite.SqlOperate;
import com.sunnyit.common.view.TabDialogView;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.data.Standard_CK_Table_ItemCheckedAdapter;
import com.sunnyit.enforcement.data.Standard_CK_Table_ItemCheckedAdapter.RepairTypeListener;
import com.sunnyit.enforcement.model.Standard_CK_Table;
import com.sunnyit.enforcement.model.Standard_CK_Table_Item;
import com.sunnyit.synchronous.model.UpInfo;

/**
 * @Title: HiddenList.java
 * @Package com.sunnyit.hiddencheck.action
 * @Description: TODO
 * @author yk
 * @date 2015年9月7日 下午3:27:12
 * @version V1.0
 */
public class HiddenStandCheckedDatilAddActivity extends BaseActivity {

	private TopBarView topbar_endatil_standcheck_add;
	private ListView lv_stand_table_item;
	private String imagePath;
	private int position;
	private Standard_CK_Table standTable;
	private String ck_id;
	private static final int TAKE_REQUEST_CODE = 1;
	private List<Standard_CK_Table_Item> mData;
	private Standard_CK_Table_ItemCheckedAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_endatil_standcheck_add);
         Toast.makeText(HiddenStandCheckedDatilAddActivity.this, "sdjshdkjshd", Toast.LENGTH_LONG).show();
		ck_id = getIntent().getStringExtra("ck_id");
		String conditionStr = "  where ck_id='" + ck_id + "' ";
		SqlOperate<Standard_CK_Table> operaterStandard_CK_Table = new SqlOperate<Standard_CK_Table>(
				HiddenStandCheckedDatilAddActivity.this,
				Standard_CK_Table.class);
		List<Standard_CK_Table> ls_st = operaterStandard_CK_Table
				.SelectEntitysByCondition(conditionStr);
		operaterStandard_CK_Table.close();

		if (ls_st != null && ls_st.size() > 0) {
			standTable = ls_st.get(0);
		}

		initComponent();

		initAdapter();

		topbar_endatil_standcheck_add.setTopBarClick(new ITopBarClick() {

			@Override
			public void onClickRightBut() {
				// TODO Auto-generated method stub
				final CustomDialog cusdialog = new CustomDialog(
						HiddenStandCheckedDatilAddActivity.this);
				cusdialog.setViewAndAlpha(R.layout.progressbar_rw_blue, 0);
				cusdialog.setOutCancel(false);
				cusdialog.show();

				new Thread(new Runnable() {

					public void run() {

						int qcount = 0;
						for (Standard_CK_Table_Item scti : mData) {
							if (scti.getItem_repairType() != null
									&& !scti.getItem_repairType().equals("")) {
								SqlOperate<Standard_CK_Table_Item> operaterStandard_CK_Table_Item = new SqlOperate<Standard_CK_Table_Item>(
										HiddenStandCheckedDatilAddActivity.this,
										Standard_CK_Table_Item.class);
								operaterStandard_CK_Table_Item.upContent(scti);
								operaterStandard_CK_Table_Item.close();
								qcount++;
							}
						}

						for (Standard_CK_Table_Item scti : mData) {
							if (scti.getItem_repairType() != null
									&& !scti.getItem_repairType().equals("")) {
								if (scti.getItem_repairType().equals("L")) {
									if (standTable.getCk_deadLine() == null
											|| standTable.getCk_deadLine()
													.equals("")) {
										standTable.setCk_deadLine(scti
												.getItem_repairLimit());
									} else {
										DateFormat df = new SimpleDateFormat(
												"yyyy-MM-dd");
										try {
											Date dt1 = df.parse(standTable
													.getCk_deadLine());
											Date dt2 = df.parse(scti
													.getItem_repairLimit());
											if (dt1.getTime() < dt2.getTime()) {
												standTable.setCk_deadLine(scti
														.getItem_repairLimit());
											}
										} catch (Exception exception) {
											exception.printStackTrace();
										}
									}
								}
							}
						}

						if (qcount == mData.size()) {
							standTable.setCk_state("NOYET");
						} else {
							standTable.setCk_state("DOING");
						}

						SqlOperate<Standard_CK_Table> operaterStandard_CK_Table = new SqlOperate<Standard_CK_Table>(
								HiddenStandCheckedDatilAddActivity.this,
								Standard_CK_Table.class);
						operaterStandard_CK_Table.upContent(standTable);
						operaterStandard_CK_Table.close();

						Message msg = Message.obtain();
						msg.what = 0;
						msg.obj = standTable.getCk_id();
						mHandler.sendMessage(msg);

						cusdialog.dismiss();
					}
				}).start();
			}

			@Override
			public void onClickLeftBut() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				setResult(100, intent);
				finish();
			}
		});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(final Message msg) {
			int flag = msg.what;
			if (flag == 0) {
				if (standTable.getCk_state().equals("NOYET")) {
					String wifi = sp.getString("wifi", null);
					if (wifi.equals("yes")) {
						UpInfo info = new UpInfo();
						info.setU_Id(UUID.randomUUID().toString());
						info.setInfoTable("Standard_CK_Table");
						info.setInfoId(standTable.getCk_id());
						info.setOperateType("add");
						info.setRemark("");

						SqlOperate<UpInfo> opetaterUpInfo = new SqlOperate<UpInfo>(
								HiddenStandCheckedDatilAddActivity.this,
								UpInfo.class);
						opetaterUpInfo.saveContent(info);
						opetaterUpInfo.close();
						// 本地保存
						Toast.makeText(HiddenStandCheckedDatilAddActivity.this,
								"数据本地保存成功！", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						setResult(1, intent);
						finish();
					} else {

						final CustomDialog cusdialogt = new CustomDialog(
								HiddenStandCheckedDatilAddActivity.this);
						cusdialogt.setViewAndAlpha(R.layout.dialog_custom, 0);
						cusdialogt.initProgressBar(R.id.id_progressbarh);
						cusdialogt.setOutCancel(false);
						cusdialogt.setText(R.id.tv_dg_title, "数据上传中");
						cusdialogt.findViewById(R.id.rel_cb_divider)
								.setVisibility(View.GONE);
						cusdialogt.findViewById(R.id.lin_but_divider)
								.setVisibility(View.GONE);
						cusdialogt.show();

						final float minp = 100.0f / (1 + mData.size());
						new Thread(new Runnable() {
							public void run() {
								String addStandUrl = getBaseUrl()
										+ "/appWebSave/saveStandCheck";
								HttpClientNpAsyncPost postStand = new HttpClientNpAsyncPost(
										HiddenStandCheckedDatilAddActivity.this,
										standTable);
								postStand.execute(addStandUrl);
								cusdialogt.setProgress((int) minp);

								String addStandItemUrl = getBaseUrl()
										+ "/appWebSave/saveStandCheckItem";
								for (int i = 0; i < mData.size(); i++) {
									HttpClientNpAsyncPost postStandItem = new HttpClientNpAsyncPost(
											HiddenStandCheckedDatilAddActivity.this,
											mData.get(i));
									postStandItem.execute(addStandItemUrl);

									cusdialogt
											.setProgress((int) (minp * (i + 2)));
								}
								cusdialogt.dismiss();
								Message msg = Message.obtain();
								msg.what = 1;
								mHandler.sendMessage(msg);
							}
						}).start();

					}
				} else {
					Toast.makeText(HiddenStandCheckedDatilAddActivity.this,
							"保存成功", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					setResult(1, intent);
					finish();
				}
			} else if (flag == 1) {
				Toast.makeText(HiddenStandCheckedDatilAddActivity.this,
						"同步保存成功", Toast.LENGTH_SHORT).show();
			}
		};
	};

	private void initAdapter() {
		String conStr = " where ck_id='" + ck_id
				+ "' and item_isQualified='NO' ";
		
		SqlOperate<Standard_CK_Table_Item> operaterStandard_CK_Table_Item = new SqlOperate<Standard_CK_Table_Item>(
				HiddenStandCheckedDatilAddActivity.this,
				Standard_CK_Table_Item.class);
		mData = operaterStandard_CK_Table_Item.SelectEntitysByCondition(conStr);
		operaterStandard_CK_Table_Item.close();
		mAdapter = new Standard_CK_Table_ItemCheckedAdapter(this, mData,
				R.layout.hidden_endatil_standchecked_item);
		lv_stand_table_item.setAdapter(mAdapter);
		lv_stand_table_item
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						Toast.makeText(HiddenStandCheckedDatilAddActivity.this, "changan", Toast.LENGTH_LONG).show();
						Intent intent = new Intent(
								HiddenStandCheckedDatilAddActivity.this,
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
						return false;
					}
				});

		lv_stand_table_item.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				/*
				 * Standard_CK_Table_Item ct=mData.get(arg2);
				 * AlertDialog.Builder builder=new
				 * AlertDialog.Builder(HiddenStandCheckedDatilAddActivity.this);
				 * //先得到构造器 builder.setTitle("依据说明"); //设置标题
				 * builder.setMessage(ct.getItem_basis()); //设置内容
				 * builder.setIcon(R.drawable.note_stand48);//设置图标，图片id即可
				 * builder.setNegativeButton("取消", new
				 * DialogInterface.OnClickListener() { //设置取消按钮
				 * 
				 * @Override public void onClick(DialogInterface dialog, int
				 * which) { dialog.dismiss(); } }); //参数都设置完成了，创建并显示出来
				 * builder.create().show();
				 */
				Toast.makeText(HiddenStandCheckedDatilAddActivity.this, "duanan", Toast.LENGTH_LONG).show();

				Standard_CK_Table_Item ct = mData.get(arg2);
				final CustomDialog cusdialog = new CustomDialog(
						HiddenStandCheckedDatilAddActivity.this);
				TabDialogView tabDialogView = new TabDialogView(
						HiddenStandCheckedDatilAddActivity.this);
				tabDialogView.addContent("依据说明", ct.getItem_basis());
				tabDialogView.addContent("罚则", ct.getItem_punishment());
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

		mAdapter.setRepairTypeListener(new RepairTypeListener() {

			@Override
			public void setItemRepairTypeListener(
					Standard_CK_Table_Item standard_Table_Item, boolean checked) {
				// TODO Auto-generated method stub
				for (int i = 0; i < mData.size(); i++) {
					if (mData.get(i).getId()
							.equals(standard_Table_Item.getId())) {
						if (checked) {
							final int iTemp = i;
							mData.get(i).setItem_repairType("L");
							final StringBuffer sb_date = new StringBuffer();
							Calendar cal = Calendar.getInstance();
							// 获取年月日时分秒的信息
							int year = cal.get(Calendar.YEAR);
							int month = cal.get(Calendar.MONTH) + 1;
							int day = cal.get(Calendar.DAY_OF_MONTH);
							new DatePickerDialog(
									HiddenStandCheckedDatilAddActivity.this,
									new OnDateSetListener() {

										@Override
										public void onDateSet(DatePicker view,
												int year, int monthOfYear,
												int dayOfMonth) {
											// TODO Auto-generated method stub
											if ("".equals(sb_date.toString())) {
												if ((monthOfYear + 1) < 10) {
													if ((dayOfMonth + 1) < 10) {
														sb_date.append(year
																+ "-"
																+ "0"
																+ (monthOfYear + 1)
																+ "-" + "0"
																+ dayOfMonth);
													} else {
														sb_date.append(year
																+ "-"
																+ "0"
																+ (monthOfYear + 1)
																+ "-"
																+ dayOfMonth);
													}
												} else {
													if ((dayOfMonth + 1) < 10) {
														sb_date.append(year
																+ "-"
																+ (monthOfYear + 1)
																+ "-" + "0"
																+ dayOfMonth);
													} else {
														sb_date.append(year
																+ "-"
																+ (monthOfYear + 1)
																+ "-"
																+ dayOfMonth);
													}
												}
												mData.get(iTemp)
														.setItem_repairLimit(
																sb_date.toString());
											}
										}
									}, year, cal.get(Calendar.MONTH), day)
									.show();
						} else {
							mData.get(i).setItem_repairType("N");
						}
						break;
					}
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
