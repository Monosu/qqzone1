package com.higgs.qqzone1;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.higgs.qqzone1.dao.UserInfoDao;
import com.higgs.qqzone1.dao.impl.UserInfoDaoImpl;
import com.higgs.qqzone1.model.UserInfo;

public class Find_Psd_Activity extends Activity {
	private EditText updatename,updatenikename,updatepsd;
	private Handler oHandler;
	private String user,nike,pass,result;
	private ProgressDialog oDialog ;
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.find_psd_activity_main);
		updatename=(EditText) findViewById(R.id.updateName);
		updatenikename=(EditText) findViewById(R.id.updatenikename);
		updatepsd=(EditText) findViewById(R.id.updatepsd);
		oHandler=new Handler(){
			public void handleMessage(Message msg) {
				String result=(String) msg.obj;
				if (result.length()>0&&result!=null&&!(result.equals(""))) {
					Toast.makeText(Find_Psd_Activity.this, result, Toast.LENGTH_SHORT).show();
					oDialog.dismiss();
					Intent intent=new Intent(Find_Psd_Activity.this, LoginActivity.class);
					startActivity(intent);
					Find_Psd_Activity.this.finish();
				}
				super.handleMessage(msg);
			}
		};
		super.onCreate(savedInstanceState);
	}
	public void updatepsd_user(View view) {
//		user = updatename.getText().toString();
//		nike = updatenikename.getText().toString();
//		pass=updatepsd.getText().toString();
//		if (user.equals("") || user == null) {
//			result = "请输入账号";
//		} else if (nike.equals("") || nike == null) {
//			result = "请输入用户昵称";
//		}else if (pass.equals("") || pass == null) {
//			result = "请输入要更改的密码";
//		}
//		dialog();
//		new Thread(){
//			public void run() {
//					UserInfo oInfo=new UserInfo();
//					oInfo.setLoginName(user);
//					oInfo.setNikeName(nike);
//					oInfo.setPassWord(pass);
//					UserInfoDao userInfoDao=new UserInfoDaoImpl();
//					result=userInfoDao.updatepsd(oInfo);
//					Message oMessage=new Message();
//					oMessage.obj=result;
//					oMessage.what=0x123;
//					oHandler.sendMessage(oMessage);
//				};
//			
//			}.start();
		
	}
	public void dialog() {
		oDialog = new ProgressDialog(Find_Psd_Activity.this);
		oDialog.setMessage("密码修改中...");
		oDialog.setCancelable(false);
		oDialog.show();
		
	}
}
