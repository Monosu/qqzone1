package com.higgs.qqzone1;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.higgs.qqzone1.biz.UserFilesBiz;
import com.higgs.qqzone1.model.UserFiles;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class XiangCe extends Activity {
private Handler mHandler;
private List<UserFiles> oList=null;
private List<Bitmap> bitmaps=new ArrayList<Bitmap>();
private ListView mylistView;
private Bitmap bitmap;
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.list_image_layout);
		super.onCreate(savedInstanceState);
		mylistView=(ListView) findViewById(R.id.mylistview);
		init();
		mHandler=new Handler(){
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what==0x12) {
					bitmaps.add(bitmap);
					adapter.notifyDataSetChanged();
					mylistView.setAdapter(adapter);
				}
			}
		};
	}
	
public void init() {
	new Thread(){
		public void run() {
		oList=UserFilesBiz.getFilesPath();
		if (oList!=null&&oList.size()>0) {
//			HashMap<String, String> oHashMap = null;
//			for (int i = 0; i < oList.size(); i++) {
//				oHashMap = new HashMap<String, String>();
//				oHashMap.put("img", oList.get(i).getFilePath());
//				dataList.add(oHashMap);
//			}
//			mHandler.sendEmptyMessage(0x12);
			for (int i = 0; i < oList.size(); i++) {
					final int j=i;
						try {
							URL url=new URL(oList.get(j).getFilePath());
							InputStream is=url.openStream();
							bitmap=BitmapFactory.decodeStream(is);
							is.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						mHandler.sendEmptyMessage(0x12);
					}
				}
			};
		}.start();
	}
	
	BaseAdapter adapter=new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=getLayoutInflater().inflate(R.layout.xiangce_image, null);
			ImageView image=(ImageView) view.findViewById(R.id.image_xiangce);
			image.setImageBitmap(bitmaps.get(position));
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
			return bitmaps.get(position);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bitmaps.size();
		}
	};
	
}
