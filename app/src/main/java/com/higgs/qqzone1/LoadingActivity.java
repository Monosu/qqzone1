package com.higgs.qqzone1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.higgs.qqzone1.biz.UserInfoBiz;
import com.higgs.qqzone1.common.SysConfig;

public class LoadingActivity extends Activity {
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	private Handler oHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0X12) {
				//更改为tabhost
				Intent oIntent = new Intent(LoadingActivity.this,
						Tab_MainActivity.class);
				startActivity(oIntent);
			}
			if (msg.what == 0X13) {
				Intent oIntent = new Intent(LoadingActivity.this,
						LoginActivity.class);
				startActivity(oIntent);
			}
			finish();
		}
	};
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		new Thread() {
			public void run() {
				try {
					UserInfoBiz.getLoginUserInfo();
					Thread.sleep(2000);
					shared=getSharedPreferences("qq", MODE_PRIVATE);
					//登录信息为空，并且本地记录已登陆为假，则跳转至loginativity
					if (SysConfig.loginUserInfo == null
							&&!(shared.getBoolean("islogin", true))) {

						oHandler.sendEmptyMessage(0X13);
						//oHandler.sendEmptyMessage(0x12);
					} else {
						oHandler.sendEmptyMessage(0X12);
						//oHandler.sendEmptyMessage(0x13);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			};
		}.start();
	}
}
