package com.higgs.qqzone1;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ZhongActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhong);
	}
	public void mood(View view) {
		Intent intent=new Intent(ZhongActivity.this, MoodActivity.class);
		startActivity(intent);
		finish();
	}
	public void image(View view) {
		Intent intent=new Intent(ZhongActivity.this, ImageActivity.class);
		startActivity(intent);
		finish();
	}
	public void zhong(View view) { 
		finish();
	}
}
