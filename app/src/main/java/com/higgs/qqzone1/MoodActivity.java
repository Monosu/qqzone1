package com.higgs.qqzone1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.ToggleButton;

import com.higgs.qqzone1.biz.MoodBiz;
import com.higgs.qqzone1.biz.UserFilesBiz;
import com.higgs.qqzone1.common.SysConfig;
import com.higgs.qqzone1.model.Mood;
import com.higgs.qqzone1.model.UserFiles;

/**
 * 发布说说
 * 
 * @author Administrator
 * 
 */
public class MoodActivity extends Activity {
	// GridView 如果在ScrollView中包含GridView或者ListView。那么需要对GridView和ListView进行处理
	private GridView imageGridView;
	// 用户上传图片适配器
	private SimpleAdapter oAdapter;
	// 上传图片数据源
	private List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
	// 是否上传大图
	private ToggleButton isBigButton;
	// 添加图片请求码
	private int addPhoto = 10000;
	// 说说内容
	private EditText txtMoodContext;
	// 说说对象
	private Mood oMood = null;
	// 弹出模式进度框
	private ProgressDialog oDialog;
	// 从照相或相册获得的图片地址
	private List<String> filePathList = new ArrayList<String>();
	// 保存已经上传好的图片信息
	private List<UserFiles> uploadFiles = new ArrayList<UserFiles>();
	// 保存上传失败后的图片信息
	private List<UserFiles> errorUploadFiles = new ArrayList<UserFiles>();

	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		setContentView(R.layout.mood_activity);
		super.onCreate(savedInstanceState);
		initDate();
		initView();
		initLinear();
	}

	/**
	 * 初始化控件信息
	 */
	public void initView() {
		imageGridView = (GridView) findViewById(R.id.gridview);
		oAdapter = new SimpleAdapter(getApplicationContext(), dataList,
				R.layout.photo_item, new String[] { "img" },
				new int[] { R.id.photo_item_img });
		imageGridView.setAdapter(oAdapter);
		isBigButton = (ToggleButton) findViewById(R.id.toggleButton1);
		txtMoodContext = (EditText) findViewById(R.id.sendinfo);
	}

	/**
	 * 初始化事件
	 */
	public void initLinear() {
		// 为gridview添加子项点击事件
		imageGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (isBigButton.isChecked()) {
				// 如果点击的是最后一个。也就是添加图片项
				if (arg2 == (dataList.size() - 1)) {
					// 启动添加图片activity
					Intent oIntent = new Intent(MoodActivity.this,
							SelectPhotoActicity.class);
					// 判断当前是否选中上传大图。如果选中isbig为0,如果没有选中上传大图。isbig为1.小图
						oIntent.putExtra("isbig", 1);
					// 启动拍照窗体
					startActivityForResult(oIntent, addPhoto);

					}
				// TODO 自动生成的方法存根
				}
			}
		});
	}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		/**
		 * 判断请求码为添加图片请求码。并且返回码为正常返回码
		 */
		if (requestCode == addPhoto && resultCode == Activity.RESULT_OK) {
			// 获取到返回的图片
			String path = data.getStringExtra("photo_path");
			// 如果返回的图片不为空
			if (!TextUtils.isEmpty(path)) {
				// 添加一个HashMap项。
				HashMap<String, Object> oHashMap = new HashMap<String, Object>();
				oHashMap.put("img", path);
				// 将图片添加到加号前面
				dataList.add(dataList.size() > 0 ? dataList.size() - 1 : 0,
						oHashMap);
				// 将此图片添加到上传文件路径中
				filePathList.add(path);
				// 通知界面刷新
				oAdapter.notifyDataSetChanged();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 初始化图片列表中的添加图片项
	 */
	public void initDate() {
		HashMap<String, Object> oHashMap = new HashMap<String, Object>();
		oHashMap.put("img", R.drawable.addphotoselector);
		dataList.add(oHashMap);
	}
	public void cancal(View view) {
		MoodActivity.this.finish();
	}
	/**
	 * 发布说说事件
	 * 
	 * @param oView
	 */
	public void sendmood(View oView) {
		// 判断是否有输入内容
		if (TextUtils.isEmpty(txtMoodContext.getText())) {
			AppApplication.toaskMessage("请输入要发布的说说内容!");
			return;
		}
		// 判断用户是否已经登录
		if (SysConfig.loginUserInfo == null) {
			AppApplication.toaskMessage("请先登录");
			return;
		}
		// 实例化弹窗进度条
		oDialog = new ProgressDialog(MoodActivity.this);
		oDialog.setMessage("发布中,请稍后...");
		// oDialog.setCancelable(false);
		oDialog.show();
		// 实例化mood对象。并赋值
		oMood = new Mood();
		oMood.setFid("");
		oMood.setMoodContext(txtMoodContext.getText().toString());
		oMood.setUserId(SysConfig.loginUserInfo.getId());
		// 判断用户是否添加了照片。如果添加了照片。需要先上传照片。否则就直接发布说说
		if (filePathList != null && filePathList.size() > 0) {
			// 循环遍历所有文件。为每个文件添加一个上传异步线程
			for (int i = 0; i < filePathList.size(); i++) {
				// 实例化一个UserFiles对象
				UserFiles oFiles = new UserFiles();
				// 将文件路径存放到UserFiles对象中
				oFiles.setFilePath(filePathList.get(i));
				// 实例化一个线程.上传当前文件
				new UploadFiles().execute(oFiles);
			}
		} else {
			// 直接发布说说
			new PostMoodAsy().execute(oMood);
		}


	}

	/**
	 * 文件上传后调用此方法。判断当前是否上传完所有文件。如果上传完则发布说说
	 */
	public synchronized void postMood() {
		// 如果文件已经全部上传成功.就是上传文件总数=文件总数。那么就可以添加说说了
		if ((uploadFiles.size() + errorUploadFiles.size()) == filePathList
				.size()) {
			// 判断当前是否有上传失败的图片。如果有则提示用户是否继续上传
			if (errorUploadFiles.size() > 0) {
				// 实例化对话框
				Builder oBuilder = new AlertDialog.Builder(
MoodActivity.this);

				oBuilder.setCancelable(false);
				oBuilder.setMessage("总共有" + errorUploadFiles.size()
						+ "个文件上传失败,是否继续上传文件?");
				oBuilder.setPositiveButton("继续上传", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// 如果是继续上传则从新实例化一个上传文件线程
						for (int i = 0; i < errorUploadFiles.size(); i++) {
							new UploadFiles().execute(errorUploadFiles.get(i));
						}
					}
				});
				oBuilder.setNegativeButton("直接发表", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// for (int i = 0; i < errorUploadFiles.size(); i++) {
						// for (int j = 0; j < filePathList.size(); j++) {
						// if (errorUploadFiles.get(i).getFilePath()
						// .equals(uploadFiles.get(j))) {
						// filePathList.remove(j);
						// }
						// }
						// }
						// 否则直接发表
						addMood();
					}
				});
				oBuilder.show();
			} else {
				// 发表说说
				addMood();
			}

		}
	}

	private void addMood() {
		// 循环将上传后的文件ID追加到fidString变量中
		String fidString = "";
		for (int i = 0; i < uploadFiles.size(); i++) {
			fidString += uploadFiles.get(i).getId();
			if (i < (uploadFiles.size() - 1)) {
				fidString += ",";
			}
		}
		// 将上传后的文件ID赋值给mood
		oMood.setFid(fidString);
		// 发布说说
		new PostMoodAsy().execute(oMood);
	}

	/**
	 * 异步上传文件类
	 * 
	 * @author Administrator
	 * 
	 */
	private class UploadFiles extends AsyncTask<UserFiles, Void, UserFiles> {
		// 将传入的参数存储中转。用于一会判断
		private UserFiles oFiles = null;
		protected UserFiles doInBackground(UserFiles... params) {
			// TODO 自动生成的方法存根
			oFiles = params[0];
			// 调用文件上传方法
			return UserFilesBiz.uploadFiles(params[0]);
		}

		protected void onPostExecute(UserFiles result) {
			// TODO 自动生成的方法存根
			// 如果上传失败。就将刚才上传的文件信息存放到errorUploadFiles里面。否则就存放到uploadFiles里面
			if (result == null) {
				// 如果上传失败。判断当前上传失败文件是否已经包含了这个文件。如果没有则添加
				if (!errorUploadFiles.contains(oFiles)) {
					errorUploadFiles.add(oFiles);
				}
			} else {
				// 如果上传成功。判断上传失败集合是否有包含这个文件。如果有就删除
				if (errorUploadFiles.contains(oFiles)) {
					errorUploadFiles.remove(oFiles);
				}
				// 把文件添加到上传成功集合
				uploadFiles.add(result);
			}
			// 调用发布说说方法
			postMood();
			super.onPostExecute(result);
		}

	}

	private class PostMoodAsy extends AsyncTask<Mood, Void, Boolean> {
		protected Boolean doInBackground(Mood... params) {
			// TODO 自动生成的方法存根
			return MoodBiz.addMood(params[0]);
		}

		protected void onPostExecute(Boolean result) {
			// TODO 自动生成的方法存根
			if (result) {
				AppApplication.toaskMessage("发布成功!");
			}
			oDialog.dismiss();
			super.onPostExecute(result);
		}

	}
}
