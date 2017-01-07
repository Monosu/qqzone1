 package com.higgs.qqzone1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelectPhotoActicity extends Activity implements
		View.OnClickListener {
	public static final String KEY_PHOTO_PATH = "photo_path";
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	public static final int SELECT_PIC_BY_TACK_NoBig_PHOTO = 3;
	private static final String TAG = "SelectPicActivity";
	private Button cancelBtn;
	private LinearLayout dialogLayout;
	private Intent lastIntent;
	private Uri photoUri;
	private String picPath;
	private Button pickPhotoBtn;
	private Button takePhotoBtn;
	private TextView titleTextView;
	private int isBig;

	private void doPhoto(int paramInt, Intent paramIntent) {
		if (paramInt == SELECT_PIC_BY_TACK_NoBig_PHOTO) {
			File oFile = new File(Environment.getExternalStorageDirectory(),
					"test2.jpg");
			// 获取到照相后返回的缩略图
			Bitmap oBitmap = (Bitmap) paramIntent.getExtras().getParcelable(
					"data");
			if (saveBit(oBitmap, oFile)) {
				lastIntent.putExtra("photo_path", oFile.getPath());
				setResult(Activity.RESULT_OK, lastIntent);
				finish();
				return;
			}
		} else {
			if (paramInt == SELECT_PIC_BY_PICK_PHOTO) {
				if (paramIntent == null) {
					Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_SHORT).show();
					return;
				}
				this.photoUri = paramIntent.getData();
				if (this.photoUri == null) {
					Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
					return;
				}
			}

			String[] arrayOfString = { "_data" };
			Cursor localCursor = managedQuery(this.photoUri, arrayOfString,
					null, null, null);
			if (localCursor != null) {
				int i = localCursor.getColumnIndexOrThrow(arrayOfString[0]);
				localCursor.moveToFirst();
				this.picPath = localCursor.getString(i);
				localCursor.close();
			}
			Log.i("SelectPicActivity", "imagePath = " + this.picPath);
			if ((this.picPath != null)
					&& (((this.picPath.endsWith(".png"))
							|| (this.picPath.endsWith(".PNG"))
							|| (this.picPath.endsWith(".jpg")) || (this.picPath
								.endsWith(".JPG"))))) {
				this.lastIntent.putExtra("photo_path", this.picPath);
				setResult(Activity.RESULT_OK, this.lastIntent);
				finish();
				return;
			}
		}

		Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_SHORT).show();
	}

	private void initView() {
		this.dialogLayout = ((LinearLayout) findViewById(R.id.dialog_layout));
		this.dialogLayout.setOnClickListener(this);
		this.takePhotoBtn = ((Button) findViewById(R.id.btn_take_photo));
		this.takePhotoBtn.setOnClickListener(this);
		this.pickPhotoBtn = ((Button) findViewById(R.id.btn_pick_photo));
		this.pickPhotoBtn.setOnClickListener(this);
		this.cancelBtn = ((Button) findViewById(R.id.btn_cancel));
		this.cancelBtn.setOnClickListener(this);
		this.lastIntent = getIntent();
		isBig = lastIntent.getIntExtra("isbig", 0);
	}

	private void pickPhoto() {
		Intent localIntent = new Intent();
		localIntent.setType("image/*");
		localIntent.setAction("android.intent.action.GET_CONTENT"); 
		startActivityForResult(localIntent, SELECT_PIC_BY_PICK_PHOTO);
	}

	private void takePhoto() {
		if (Environment.getExternalStorageState().equals("mounted")) {
			if (isBig == 0) {
				Intent localIntent = new Intent(
						"android.media.action.IMAGE_CAPTURE");
				ContentValues localContentValues = new ContentValues();
				this.photoUri = getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						localContentValues);
				localIntent.putExtra("output", this.photoUri);
				startActivityForResult(localIntent, SELECT_PIC_BY_TACK_PHOTO);
				return;
			} else {
				Intent localIntent = new Intent(
						"android.media.action.IMAGE_CAPTURE");
				startActivityForResult(localIntent,
						SELECT_PIC_BY_TACK_NoBig_PHOTO);
				return;
			}

		}
		Toast.makeText(this, "内存卡不存在", Toast.LENGTH_SHORT).show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK) {
			doPhoto(requestCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.btn_take_photo:
			takePhoto();
			break;
		case R.id.btn_pick_photo:
			pickPhoto();
			break;
		case R.id.btn_cancel:
		default:
			finish();
			return;
		}

	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);

		setContentView(R.layout.select_pic_layout);
		this.titleTextView = ((TextView) findViewById(R.id.upimg_title));
		this.titleTextView.setText("照片选择");
		initView();
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		finish();
		return super.onTouchEvent(paramMotionEvent);
	}

	/**
	 * 将BitMap类型保存到本地
	 */
	public boolean saveBit(Bitmap oBitmap, File oFile) {
		boolean result = false;
		// 如果文件不存在。则创建文件。注意。需要sd卡写入权限
		if (!oFile.exists()) {
			try {
				// 创建一个新文件
				oFile.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		try {
			// oFile.createNewFile();
			// 定义文件输出流
			FileOutputStream outputStream = new FileOutputStream(oFile);
			// 将bitmap的内容使用compress方法复制到file文件中
			oBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
			// 释放文件写入
			outputStream.flush();
			// 关闭文件源
			outputStream.close();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}