package com.higgs.qqzone1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imgtest);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 10000 && resultCode == 10000) {
			String str2 = data.getStringExtra("photo_path");
			if (!TextUtils.isEmpty(str2)) {
				// BitmapDrawable oBitmapDrawable = new BitmapDrawable(str2);
				Bitmap oBitmap = BitmapFactory.decodeFile(str2);

				imageView.setImageBitmap(oBitmap);
			}
		}
	}

	public void phone(View oView) {
		Intent oIntent = new Intent(MainActivity.this,
				SelectPhotoActicity.class);
		// oIntent.putExtra("isbig", 1);
		startActivityForResult(oIntent, 10000);
	}


}
