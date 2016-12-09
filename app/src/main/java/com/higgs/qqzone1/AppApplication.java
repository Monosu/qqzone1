package com.higgs.qqzone1;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.higgs.qqzone1.common.DBHelper;
import com.higgs.qqzone1.common.SysConfig;
import com.higgs.qqzone1.common.UtiyCommon;

/**
 * 全局application对象.从activitie启动到结束一直存在。隐式的一个窗体。可以使用它来做初始化及全局消息弹送操作
 * 
 * @author Administrator
 * 
 */
public class AppApplication extends Application {
	// 定义handler。接受消息
	private static Handler oHandler = new Handler(){
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// 如果等于0123代表是要Toast一条消息
			if (msg.what==0123) {
				Toast.makeText(oContext, msg.obj.toString(), Toast.LENGTH_SHORT).show();
			}
			super.handleMessage(msg);
		}
	};
	public static Context oContext =null;
	public void onCreate() {
		// TODO Auto-generated method stub
		oContext = getApplicationContext();
		SysConfig.dbHelper = new DBHelper(oContext);
		super.onCreate();
		
	}

	// 定义一个全局静态toaskMessage方法。所有的类都可以通过这个方法弹出消息提示。而不需要通过传递activitie来实现
	public static void toaskMessage(String message) {
		if (!TextUtils.isEmpty(message)) {
			Message oMessage = new Message();
			oMessage.what = 0123;
			oMessage.obj = message;
			oHandler.sendMessage(oMessage);
		}
	}

	// 检测是否有网络连接。true为有。false为没有。
	public static boolean isWork() {
		if (UtiyCommon.isWorkConnection(oContext)) {
			return true;
		} else {
			toaskMessage("网络连接失败,请检查网络");
			return false;
		}
	}

}
