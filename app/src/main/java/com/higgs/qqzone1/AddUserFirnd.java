package com.higgs.qqzone1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.higgs.qqzone1.adapter.UserFriendAdapter;
import com.higgs.qqzone1.biz.UserFriendBiz;
import com.higgs.qqzone1.biz.UserInfoBiz;
import com.higgs.qqzone1.common.SysConfig;
import com.higgs.qqzone1.model.UserFriend;
import com.higgs.qqzone1.model.UserInfo;
import com.higgs.qqzone1.pulltorefresh.library.PullToRefreshBase;
import com.higgs.qqzone1.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.higgs.qqzone1.pulltorefresh.library.PullToRefreshListView;

/**
 * 好友管理
 * 
 * @author Administrator
 * 
 */
public class AddUserFirnd extends Activity {
	
	// 定义界面控件ListView
	private PullToRefreshListView dataView;
	private EditText selectText;
	private Button oButton;
	private Handler oHandler;
	// 定义当前加载的页数
	private int page = 1;
	// 定义每次加载多少条信息
	private int pagesize = 10;
	private int isTop = 0;
	// 定义绑定适配器
	private UserFriendAdapter userFriendAdapter;
	// 定义绑定的数据源
	private List<UserInfo> userList = new ArrayList<UserInfo>();
	// 用户好友集合
	private List<UserFriend> userFriends = new ArrayList<UserFriend>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user_firnd);
		InitView();
		InitLinear();
	}

	/**
	 * 初始化界面及控件
	 */
	private void InitView() {
		// 从界面中获取控件
		dataView = (PullToRefreshListView) findViewById(R.id.dataList);
		selectText = (EditText) findViewById(R.id.txtname);
		oButton = (Button) findViewById(R.id.btnselect);
		// 初始化适配器
		userFriendAdapter = new UserFriendAdapter(userList, userFriends,
				AddUserFirnd.this);
		dataView.setAdapter(userFriendAdapter);
		// 从服务端获取好友列表
		oHandler = new Handler() {
			public void handleMessage(Message msg) {
				// TODO 自动生成的方法存根
				// 如果请求是1234代表加载好友信息。刷新界面
				if (msg.what == 1234) {
					// 从对象中取到发送过来的值
					List<List<?>> parmList = (List<List<?>>) msg.obj;
					// 获取用户集合
					List<UserInfo> userDataList = (List<UserInfo>) parmList
							.get(0);
					// 获取好友集合
					List<UserFriend> userFriendsList = (List<UserFriend>) parmList
							.get(1);
					// 如果好友集合不为空。添加到好友集合中
					if (userFriendsList != null && userFriendsList.size() > 0) {
						userFriends.clear();
						userFriends.addAll(userFriendsList);
					}
					// 判断返回的数据是否为空。如果不为空说明有获取到值
					if (userDataList != null && userDataList.size() > 0) {
						// 如果取到值。说明数据已经加载到。判断当前是不是头部刷新
						if (isTop == 0) {
							// 如果是头部刷新。说明从新加载。需要清除当前集合的项
							userList.clear();
							// 把当前页数赋值为第1页
							page = 1;
						} else {
							// 否则是底部刷新。把page值更新为当前页数。刚才++时是用临时变量来存储。为了是防止加载失败还需要--。在这里刷新成功后+1即可
							page++;
						}
						// 将取到的数据加载到数据源中
						userList.addAll(userDataList);
						// 发送消息通知界面刷新
						userFriendAdapter.notifyDataSetChanged();
						// loadListViewLinear();
					}

					// 调用刷新框架的回收方法。收回底部和头部
					dataView.onRefreshComplete();
				}
			}
		};
		getUserInfo();
	}
	
	/**
	 * 绑定事件
	 */
	private void InitLinear() {
		// 设置搜索事件
		oButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				getUserInfo();
			}
		});
		dataView.setOnRefreshListener(new OnRefreshListener2() {
			/**
			 * 下拉更新
			 */
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO 自动生成的方法存根
				// 设置为头部刷新
				isTop = 0;
				// 调用获取用户方法
				getUserInfo();
			}

			/**
			 * 上拉加载
			 */
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO 自动生成的方法存根
				// 设置为底部刷新
				isTop = 1;
				// 调用获取用户方法
				getUserInfo();
			}
		});

	}

	// private void loadListViewLinear() {
	// ListView oListView = dataView.getRefreshableView();
	// for (int i = 0; i < oListView.getChildCount(); i++) {
	// View oView = oListView.getChildAt(i);
	// Button addButton = (Button) oView.findViewById(R.id.btnaddfriend);
	// Button deleteButton = (Button) oView
	// .findViewById(R.id.btndeletefriend);
	// if (addButton != null && deleteButton != null) {
	// if (i % 2 == 0) {
	// addButton.setVisibility(View.VISIBLE);
	// deleteButton.setVisibility(View.GONE);
	// } else {
	// addButton.setVisibility(View.GONE);
	// deleteButton.setVisibility(View.VISIBLE);
	// }
	// }
	//
	// }
	// }
	/**
	 * 窗体获取好友信息
	 */
	private void getUserInfo() {

		// final String selectString = selectText.getText().toString();
		new Thread(){
			public void run() {
				// 定义中转变量获取当前页数
				int indexPage = page;
				// 判断是不是头部刷新。如果是=0代表头部刷新。需要从新加载数据
				if (isTop == 0) {
					// 如果为头部刷新。把当前应该为第一页
					indexPage = 1;
				} else {
					// 如果是底部刷新。应该当前页+1
					indexPage++;
				}
				Message oMessage = new Message();
				// 从服务端取值。并把这个对象发送给消息。
				// 获取用户列表
				List<UserInfo> oInfos = UserInfoBiz.getUserInfos(
selectText
						.getText().toString(), indexPage, pagesize);
				// 获取好友列表
				List<UserFriend> userFriendList = UserFriendBiz
						.getFriends(SysConfig.loginUserInfo.getId());
				// 将2个对象封装到List里面。并发给消息
				List<List<?>> oList = new ArrayList<List<?>>();
				oList.add(oInfos);
				oList.add(userFriendList);
				oMessage.obj = oList;
				oMessage.what = 1234;
				oHandler.sendMessage(oMessage);
				
			};
		}.start();

	}


}
