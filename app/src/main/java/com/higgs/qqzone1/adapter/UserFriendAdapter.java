package com.higgs.qqzone1.adapter;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.higgs.qqzone1.R;
import com.higgs.qqzone1.biz.UserFriendBiz;
import com.higgs.qqzone1.common.SysConfig;
import com.higgs.qqzone1.common.UtiyCommon;
import com.higgs.qqzone1.model.UserFriend;
import com.higgs.qqzone1.model.UserInfo;

/**
 * 用户好友适配器
 * 
 * @author Administrator
 * 
 */
public class UserFriendAdapter extends BaseAdapter {
	// 用户列表
	private List<UserInfo> userList;
	// 窗体容器
	private Context oContext;
	// 用户好友列表
	private List<UserFriend> userFriends;
	// 模式对话框
	private ProgressDialog oDialog;

	// 初始化操作.传入3个参数。第一个为用户数据集合。第二个为好友数据集合,第三个为绑定容器对象
	public UserFriendAdapter(List<UserInfo> oInfos, List<UserFriend> dataList,
			Context dataContext) {
		userList = oInfos;
		this.oContext = dataContext;
		userFriends = dataList;
		oDialog = new ProgressDialog(dataContext);
	}

	// 获取当前总数
	public int getCount() {
		// TODO 自动生成的方法存根
		//三目运算符比赋值运算符优先级高，a?b:c a为真则返回b的值，若a为假则返回c的值，为什么用三目运算符？
		return userList == null ? 0 : userList.size();
	}
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return position;
	}
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	/**
	 * 获取窗体
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		View oView = null;
		// 如果当前的子项没有绑定界面
		if (oView == null) {

			// 获取到当前要绑定的用户数据
			final UserInfo userInfo = userList.get(position);
			// 如果数据不为空。并且数据不是本身用户则加载用户信息
			if (userInfo != null) {
				// 加载布局
				oView = LayoutInflater.from(oContext).inflate(
						R.layout.add_user_firend_item, null);
				// 初始化控件
				TextView txtName = (TextView) oView.findViewById(R.id.txtname);
				TextView txtSex = (TextView) oView.findViewById(R.id.txtsex);
				TextView txtTime = (TextView) oView
						.findViewById(R.id.txtregisterTime);
				Button btnAdd = (Button) oView.findViewById(R.id.btnaddfriend);
				Button btnDelete = (Button) oView
						.findViewById(R.id.btndeletefriend);
				// 给控件赋值
				txtName.setText(userInfo.getNikeName());
				txtSex.setText(userInfo.getSex());
				txtTime.setText(UtiyCommon.dataFormt(userInfo.getRegisterTime()));
				// 判断当前用户是不是当前登录用户的好友。如果是当前登录用户的好友需要显示删除按钮。否则显示添加好友按钮
				boolean isFriend = false;
				// 记录好友ID
				int friendID = 0;
				// 循环遍历好友集合
				if (userFriends != null && userFriends.size() > 0) {
					for (int i = 0; i < userFriends.size(); i++) {
						// 如果好友中的id == 当前用户的id。说明是当前用户
						if (userFriends.get(i).getFriendId() == userInfo
								.getId()) {
							isFriend = true;
							// 记录好友表中的ID。用于删除数据
							friendID = userFriends.get(i).getId();
							break;
						}

					}
				}
				// 如果当前加载的用户是登录用户好友
				if (isFriend) {
					// 隐藏掉添加好友按钮
					btnAdd.setVisibility(View.GONE);
					// 显示删除好友按钮
					btnDelete.setVisibility(View.VISIBLE);
				} else {
					// 否则显示添加好友按钮
					btnAdd.setVisibility(View.VISIBLE);
					// 隐藏删除好友按钮
					btnDelete.setVisibility(View.GONE);
				}
				// 为添加好友按钮赋值事件
				btnAdd.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// 实例化好友对象
						UserFriend oFriend = new UserFriend();
						// 将当前本项的用户ID赋值给好友ID
						oFriend.setFriendId(userInfo.getId());
						// 将当前用户的用户ID赋值给添加用户
						oFriend.setUserId(SysConfig.loginUserInfo.getId());
						oFriend.setRemarkName(userInfo.getNikeName());
						// 启动模式窗口
						oDialog.setMessage("正在添加好友");
						oDialog.setCancelable(false);
						oDialog.show();
						// 调用异步添加用户好友方法
						new AddUserFriendAys().execute(oFriend);
					}
				});
				// 删除好友按钮
				btnDelete.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						// 将当前用户ID封装到对象传递
						UserFriend oFriend = new UserFriend();
						oFriend.setFriendId(userInfo.getId());
						oFriend.setUserId(SysConfig.loginUserInfo.getId());
						oDialog.setMessage("正在删除好友");
						oDialog.setCancelable(false);
						oDialog.show();
						// 执行删除好友方法
						new DeleteUserFriendAsy().execute(oFriend);
					}
				});
			}
		}
		return oView;
	}

	/**
	 * 删除好友异步处理类
	 * 
	 * @author Administrator
	 * 
	 */
	private class DeleteUserFriendAsy extends
			AsyncTask<UserFriend, Void, Boolean> {
		private UserFriend oFriend;
		protected Boolean doInBackground(UserFriend... params) {
			// TODO 自动生成的方法存根
			oFriend = params[0];
			// 调用删除方法
			return UserFriendBiz.deleteUserFriend(params[0]);
		}
		protected void onPostExecute(Boolean result) {
			// TODO 自动生成的方法存根
			// 如果服务端删除成功。则对象删除当前集合的值
			if (result) {
				// 匹配当前集合等于删除的数据
				for (int i = 0; i < userFriends.size(); i++) {
					if (oFriend.getFriendId() == userFriends.get(i)
							.getFriendId()) {
						// 移除掉这个项目
						userFriends.remove(i);
					}
				}
				// 刷新界面。让界面从新绑定值
				UserFriendAdapter.this.notifyDataSetChanged();
			}
			// 关闭模式进度框
			oDialog.dismiss();
			super.onPostExecute(result);
		}

	}

	/**
	 * 添加好友异步类
	 * 
	 * @author Administrator
	 * 
	 */
	private class AddUserFriendAys extends AsyncTask<UserFriend, Void, Boolean> {
		private UserFriend oFriend;
		protected Boolean doInBackground(UserFriend... params) {
			// TODO 自动生成的方法存根
			// 获取到参数并保存给中转对象
			oFriend = params[0];
			// 调用服务端添加用户方法
			return UserFriendBiz.addUserFriend(params[0]);
		}

		protected void onPostExecute(Boolean result) {
			// TODO 自动生成的方法存根
			// 如果服务端添加成功则把当前对象添加到好友集合中。并刷新界面
			if (result) {
				userFriends.add(oFriend);
				UserFriendAdapter.this.notifyDataSetChanged();
			}
			oDialog.dismiss();
			super.onPostExecute(result);
		}
	}

}
