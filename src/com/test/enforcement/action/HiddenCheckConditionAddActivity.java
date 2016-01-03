package com.sunnyit.enforcement.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sunnyit.R;
import com.sunnyit.common.activity.BaseActivity;
import com.sunnyit.common.activity.GridPreViewPhotoActivity;
import com.sunnyit.common.activity.SelectPhotoActivity;
import com.sunnyit.common.convert.DigitalConvert;
import com.sunnyit.common.convert.PpConvert;
import com.sunnyit.common.view.CustomInputView;
import com.sunnyit.common.view.CustomInputView.ButtonOnClickListener;
import com.sunnyit.common.view.TopBarView;
import com.sunnyit.common.view.TopBarView.ITopBarClick;
import com.sunnyit.enforcement.model.CheckConditionItem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import test.action.DialogShowActivity;

/**
 * @Title: WelcomeActivity.java
 * @Package com.sunnyit.system.action
 * @Description: TODO
 * @author yk
 * @date 2015年8月1日 上午9:47:36
 * @version V1.0
 */
public class HiddenCheckConditionAddActivity extends BaseActivity {

	private TopBarView topbar_check_condition_add;
	private LinearLayout lin_check_condition_add;
	private Button but_add_inputview;
	private int position = -1;
	private String imagePath;
	private List<CustomInputView> ls_cunView;
	private ArrayList<Map<String, String>> imagePosition = new ArrayList<Map<String, String>>();
	private List<CheckConditionItem> ls_ckConItem;
	private final static int TAKE_REQUEST_CODE = 1;
	private final static int KEY_IMAGE_PATH = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hidden_check_condition_add);
		ls_cunView = new ArrayList<CustomInputView>();
		ls_ckConItem = new ArrayList<CheckConditionItem>();

		initView();

		ls_ckConItem = (List<CheckConditionItem>) getIntent()
				.getSerializableExtra("ls_ckConItem");

		if (ls_ckConItem == null || ls_ckConItem.size() == 0) {
			addInputView();
			addInputView();
		} else {
			for (int i = 0; i < ls_ckConItem.size(); i++) {
				for (int j = 0; i < ls_ckConItem.size(); j++) {
					if (i == (Integer.valueOf(ls_ckConItem.get(j).getCheckNo()) - 1)) {
						addInputView(i, ls_ckConItem.get(j).getCheckContent(),
								ls_ckConItem.get(j).getCheckItemState());
						break;
					}
				}
			}
		}

		topbar_check_condition_add.setTopBarClick(new ITopBarClick() {
			@Override
			public void onClickRightBut() {
				boolean haveNull = false;
				for (CustomInputView customInputView : ls_cunView) {
					if (customInputView.getInputViewText().trim().equals("")) {
						haveNull = true;

					}
				}
				if (haveNull) {
					Toast.makeText(HiddenCheckConditionAddActivity.this,
							"已添加输入项不能为空", Toast.LENGTH_SHORT).show();
				} else {
					ls_ckConItem.clear();

					for (CustomInputView customInputView : ls_cunView) {
						CheckConditionItem checkConditionItem = new CheckConditionItem();
						checkConditionItem.setId(UUID.randomUUID().toString());
						checkConditionItem.setCheckNo(String
								.valueOf((customInputView.getId() + 1)));
						checkConditionItem.setCheckContent(customInputView
								.getInputViewText());
						checkConditionItem.setCheckImg((String)customInputView.getTag(R.id.tag_image_path));
						checkConditionItem
								.setCheckItemState((String) customInputView
										.getTag());
						ls_ckConItem.add(checkConditionItem);

					}

					Intent intent = new Intent();
					intent.putExtra("ls_ckConItem", (Serializable) ls_ckConItem);
					setResult(1, intent);
					finish();
				}
			}

			@Override
			public void onClickLeftBut() {
				finish();
			}
		});

		but_add_inputview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addInputView();
			}
		});
	}

	private void addInputView(int id, String context, String state) {

		final CustomInputView customInputView = new CustomInputView(
				HiddenCheckConditionAddActivity.this);
		customInputView.setId(id);
		customInputView.setTag(state);
		customInputView.setInputTitle("第"
				+ DigitalConvert.digital2China(id + 1) + "项：");
		customInputView.setInputViewText(context);
		ls_cunView.add(customInputView);
		customInputView.setButtonOnClickListener(new ButtonOnClickListener() {
			@Override
			public void onSecondButtonOnClick() {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						HiddenCheckConditionAddActivity.this);
				builder.setMessage("是否确认删除当前输入项?");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								ls_cunView.remove(customInputView.getId());
								lin_check_condition_add
										.removeView(customInputView);
								if (customInputView.getId() != (ls_cunView
										.size())) {
									for (int i = customInputView.getId(); i < ls_cunView
											.size(); i++) {
										ls_cunView
												.get(i)
												.setInputTitle(
														"第"
																+ DigitalConvert
																		.digital2China(i + 1)
																+ "项：");
										ls_cunView.get(i).setId(i);
									}
								}

								dialog.dismiss();
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
			}

			@Override
			public void onFirstButtonOnClick() {
				Intent intent = new Intent(
						HiddenCheckConditionAddActivity.this,
						SelectPhotoActivity.class);
				intent.putExtra("COUNT", 20);
				intent.putExtra("POSITION", customInputView.getId());
				ArrayList<String> imagePathList = new ArrayList<String>();
				String imagePath =(String) customInputView.getTag(R.id.tag_image_path);
				if (imagePath != null) {
					String[] imagePathArray = imagePath.split(":");
					for (int i = 0; i < imagePathArray.length; i++) {
						imagePathList.add(imagePathArray[i].replaceAll(" ", "")
								.trim());
					}
					intent.putExtra("DATA", imagePathList);
				} 

				startActivityForResult(intent, TAKE_REQUEST_CODE);

			}
		});
		customInputView.setFirstButBackBrougd(R.drawable.img_up);
		customInputView.setSecondButBackBrougd(R.drawable.delete_imgbut48);
		customInputView.setHintText("请输入检查情况");
		RelativeLayout.LayoutParams tvParams = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tvParams.setMargins(
				PpConvert.dp2px(HiddenCheckConditionAddActivity.this, 10),
				PpConvert.dp2px(HiddenCheckConditionAddActivity.this, 5),
				PpConvert.dp2px(HiddenCheckConditionAddActivity.this, 10), 0);
		lin_check_condition_add.addView(customInputView, tvParams);

	}

	private void addInputView() {
		addInputView(ls_cunView.size(), "", "0");
	}

	private void initView() {
		topbar_check_condition_add = (TopBarView) findViewById(R.id.topbar_check_condition_add);
		lin_check_condition_add = (LinearLayout) findViewById(R.id.lin_check_condition_add);
		but_add_inputview = (Button) findViewById(R.id.but_add_inputview);
	}

	/**
	 * @MethodName:
	 * @Description:
	 * @author : zhanghr
	 * @data: 2015年12月15日 下午12:48:32
	 * @param
	 * @return：
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_REQUEST_CODE:
				if (data != null) {
					position = data.getIntExtra("POSITION", 0);
					ArrayList<String> arrayList = data.getStringArrayListExtra("DATA");
					imagePath = arrayList.toString().replaceAll(",", ":")
							.replace("[", "").replace("]", "");
					ls_cunView.get(position).setTag(R.id.tag_image_path, imagePath);

				}

				break;

			}

		}
	}

}
