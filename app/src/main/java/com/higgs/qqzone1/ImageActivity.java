package com.higgs.qqzone1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class ImageActivity extends Activity {
	public static final String KEY_PHOTO_PATH = "photo_path";
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	public static final int SELECT_PIC_BY_TACK_NoBig_PHOTO = 3;
	private Intent lastIntent;
	private Uri photoUri;
	private String picPath;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pickPhoto();
	}
	private void pickPhoto() {
		Intent localIntent = new Intent();
		localIntent.setType("image/*");
		localIntent.setAction("android.intent.action.GET_CONTENT"); 
		startActivityForResult(localIntent, SELECT_PIC_BY_PICK_PHOTO);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK) {
			doPhoto(requestCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
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
					Toast.makeText(this, "选择图片文件出错", 1).show();
					return;
				}
				this.photoUri = paramIntent.getData();
				if (this.photoUri == null) {
					Toast.makeText(this, "选择图片文件出错", 1).show();
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

		Toast.makeText(this, "选择图片文件不正确", 1).show();
	}
	
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
