package com.higgs.qqzone1;



import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * 可使用fragment实现Tab切换
 */

public class Tab_MainActivity extends Activity {
	private TabHost mTabHost;
	private String[] tags = new String[] { "Tab_A", "Tab_B", "zhong", "Tab_C",
			"Tab_D" };
	private int[] tImg = new int[] {
			R.drawable.friendfeed,
 R.drawable.myfeed,
			R.drawable.ic_launcher,
			R.drawable.home, R.drawable.more};
	private String[] titleStrings = new String[] { "动态", "以我相关", " ", "我的空间",
			"更多" };
	private TextView title;
	private ImageView image;
	private View tView;
	private Intent[] intents=new Intent[5];
	private Intent intent1,intent2,intent3,intent4,intent5;
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tat_activity_main);
		mTabHost = (TabHost) findViewById(R.id.myTabHost);
		LocalActivityManager mLocalActivityManager=new LocalActivityManager(Tab_MainActivity.this, false);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		mTabHost.setup(mLocalActivityManager);
		init();
		for (int i = 0; i < intents.length; i++) {

//			getLayoutInflater().inflate(layoutXml[i],
//					mTabHost.getTabContentView());
			tView = getLayoutInflater().inflate(R.layout.tab, null);
			image = (ImageView) tView.findViewById(R.id.mimg);
			title = (TextView) tView.findViewById(R.id.mtxt);
//			image.setImageResource(tImg[i]);
//			title.setText(titleStrings[i]);
			if (i == 2) {
				//image.setImageBitmap(null);
				title.setText("");
				TabSpec tabSpec = mTabHost.newTabSpec(tags[i]);
				tabSpec.setIndicator(tView).setContent(intents[i]);
				mTabHost.addTab(tabSpec);
			} else {
				image.setImageResource(tImg[i]);
				title.setText(titleStrings[i]);
				TabSpec tabSpec = mTabHost.newTabSpec(tags[i]);
				tabSpec.setIndicator(tView).setContent(intents[i]);
				mTabHost.addTab(tabSpec);
			}
			
		}
	}
	public void init() {
		intent1=new Intent(Tab_MainActivity.this, Tab_t1.class);
		intent2=new Intent(Tab_MainActivity.this, Tab_t2.class);
		intent3=new Intent(Tab_MainActivity.this, ZhongActivity.class);
		intent4=new Intent(Tab_MainActivity.this, Tab_t4.class);
		intent5=new Intent(Tab_MainActivity.this, Tab_t5.class);
		intents[0]=intent1;
		intents[1]=intent2;
		intents[2]=intent3;
		intents[3]=intent4;
		intents[4]=intent5;
		
	}
	public void zhong(View view) {
			intent3 = new Intent(Tab_MainActivity.this,
					ZhongActivity.class);
			startActivity(intent3);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.zhuxiao:
			SharedPreferences shared=getSharedPreferences("qq", MODE_WORLD_READABLE);
			SharedPreferences.Editor editor=shared.edit();
			editor.putBoolean("islogin", false);
			editor.commit();
			Intent intent=new Intent(Tab_MainActivity.this, LoginActivity.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.tuichu:
			this.finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
