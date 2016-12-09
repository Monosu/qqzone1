package com.higgs.qqzone1;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.higgs.qqzone1.biz.JournalBiz;
import com.higgs.qqzone1.common.SysConfig;
import com.higgs.qqzone1.common.UtiyCommon;
import com.higgs.qqzone1.model.Journal;
public class Journal_Activity extends Activity {
	private List<Journal> oList=null;
	private Handler myHandler;
	private ListView myListView;
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.journal_layout);
		super.onCreate(savedInstanceState);
		myListView=(ListView) findViewById(R.id.myjournallist);
		init();
		myHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if (msg.what==0x123) {
					
					adapter.notifyDataSetChanged();
				}
				super.handleMessage(msg);
			}
		};
		myListView.setAdapter(adapter);
		myListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				setContentView(R.layout.journal_layout_context);

				TextView username=(TextView) findViewById(R.id.username);
				TextView posttime2=(TextView) findViewById(R.id.posttime2);
				TextView title2=(TextView) findViewById(R.id.title2);
				TextView conText=(TextView) findViewById(R.id.context);

				username.setText(SysConfig.loginUserInfo.getNikeName());
				posttime2.setText(UtiyCommon.dataFormt(oList.get(position).getPostTime()));
				title2.setText(oList.get(position).getTitle());
				conText.setText(oList.get(position).getContext());
				
			}
		});
	}
	BaseAdapter adapter=new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=getLayoutInflater().inflate(R.layout.list_journal_list_item, null);
			TextView title=(TextView)view.findViewById(R.id.title);
			TextView posttime=(TextView)view.findViewById(R.id.posttime);
			TextView readcount=(TextView)view.findViewById(R.id.readcount);

			title.setText(oList.get(position).getTitle());
			posttime.setText(UtiyCommon.dataFormt(oList.get(position).getPostTime()));
			readcount.setText(oList.get(position).getReadCount());
			return view;
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return oList.get(position);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return oList.size();
		}
	};
	public void init() {
		new Thread(){
			public void run() {
					JournalBiz oBiz=new JournalBiz();
					oList=oBiz.getList();
					if (oList!=null&oList.size()>0) {
						myHandler.sendEmptyMessage(0x123);
					}
			};
		}.start();
	}
}
