package com.higgs.qqzone1;

import com.higgs.qqzone1.biz.UserInfoBiz;
import com.higgs.qqzone1.common.Code;
import com.higgs.qqzone1.model.Mood;
import com.higgs.qqzone1.model.UserInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private EditText zhucename,zhucepsd,zhucesecpsd;
	private Handler oHandler;
	private String user, pass,secpass,result,realCode;
	private ProgressDialog oDialog ;
	private EditText et_typeCode;
	private ImageView iv_showCodes;

	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.register_activity_main);
		zhucename=(EditText) findViewById(R.id.zhuceName);
		zhucepsd=(EditText) findViewById(R.id.zhucepsd);
		et_typeCode= (EditText) findViewById(R.id.et_code);
		iv_showCodes= (ImageView) findViewById(R.id.iv_showCode);

		oHandler=new Handler(){
			public void handleMessage(Message msg) {
				String result=(String) msg.obj;
				if (result.length()>0&&result!=null&&!(result.equals(""))) {
					AppApplication.toaskMessage(result);
					oDialog.dismiss();
				}else {
					//editor.putBoolean("islogin", true);
					//editor.putString("username", user);
					//editor.putString("userpsd", pass);
					//editor.commit();
					AppApplication.toaskMessage(result);
					oDialog.dismiss();
//					Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
//					startActivity(intent);
//					RegisterActivity.this.finish();
				}
				super.handleMessage(msg);
			}
		};
		super.onCreate(savedInstanceState);
	}

	public void getCodes(View view){
		iv_showCodes.setImageBitmap(Code.getInstance().createBitmap());
	}

	public void regist(View view) {
		user = zhucename.getText().toString();
		pass = zhucepsd.getText().toString();
		secpass=et_typeCode.getText().toString();
		realCode=Code.getInstance().getCode().toLowerCase();
		if (user.equals("") || user == null) {
			result = "请输入账号";
		} else if (pass.equals("") || pass == null) {
			result = "请输入密码";
		}else if (!(realCode.equals(secpass))) {
			result = "验证码不正确";
		}
		dialog();
		new Thread(){
			public void run() {
					UserInfo oInfo=new UserInfo();
					oInfo.setLoginName(user);
					oInfo.setPassWord(pass);
					boolean flay=false;
					flay=UserInfoBiz.userRegister(oInfo);
					if (flay) {

						result="注册成功,返回登录";
						try {
							sleep(2000);
						}catch (Exception e){
							e.printStackTrace();
						}
						Intent intent=new Intent();
						intent.setClass(RegisterActivity.this, LoginActivity.class);
						startActivity(intent);
						finish();
					}
					Message oMessage=new Message();
					oMessage.obj=result;
					oMessage.what=0x123;
					oHandler.sendMessage(oMessage);
				};
			
			}.start();
		
	}
	public void dialog() {
		oDialog = new ProgressDialog(RegisterActivity.this);
		oDialog.setMessage("正在注册，请稍后....");
		oDialog.setCancelable(false);
		oDialog.show();
		
	}
}
