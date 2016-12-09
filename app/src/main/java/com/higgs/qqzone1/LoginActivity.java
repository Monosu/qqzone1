package com.higgs.qqzone1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.higgs.qqzone1.biz.UserInfoBiz;
import com.higgs.qqzone1.model.UserInfo;

public class LoginActivity extends Activity {
	private EditText username,password;
	private ProgressDialog oDialog;
	private SharedPreferences shared;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
	}
	public void register(View view) {
		Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
		startActivity(intent);
	}
	public void returnpsd(View view) {
		Intent intent=new Intent(LoginActivity.this,Find_Psd_Activity.class);
		startActivity(intent);
	}
	public void login(View v){
		String usernameStr = username.getText().toString();
		String passwordStr = password.getText().toString();
		if(!TextUtils.isEmpty(usernameStr)&&usernameStr.length()<3){
			Toast.makeText(this, "用户名错误，请重新输入", Toast.LENGTH_SHORT).show();
		}else if(!TextUtils.isEmpty(passwordStr)&&passwordStr.length()<3){
			Toast.makeText(this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
		}else{
			UserInfo oInfo = new UserInfo();
			oInfo.setLoginName(usernameStr);
			oInfo.setPassWord(passwordStr);
			oDialog = new ProgressDialog(LoginActivity.this);
			oDialog.setMessage("正在登陆中...");
			oDialog.setCancelable(false);
			oDialog.show();
			new UserLoginAsy().execute(oInfo);

		}

	}

	private class UserLoginAsy extends AsyncTask<UserInfo, Void, Boolean> {

		@Override
		protected Boolean doInBackground(UserInfo... params) {
			// TODO 自动生成的方法存根
			return UserInfoBiz.userLogin(params[0]);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO 自动生成的方法存根
			if (result) {
				AppApplication.toaskMessage("登陆成功!");
				//跳转到tabhost界面
				shared=getSharedPreferences("qq", MODE_WORLD_READABLE);
				editor=shared.edit();
				editor.putBoolean("islogin", true);
				editor.commit();
				Intent intent = new Intent(LoginActivity.this,
						Tab_MainActivity.class);
				startActivity(intent);
				finish();
			}
			oDialog.dismiss();
			super.onPostExecute(result);
		}

	}
}
