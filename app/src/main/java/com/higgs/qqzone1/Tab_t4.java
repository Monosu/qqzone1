package com.higgs.qqzone1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class Tab_t4 extends Activity {
	//private SimpleAdapter oAdapter ;
	//private List<HashMap<String, String	>> dataList = new ArrayList<HashMap<String,String>>();	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.t4);
		super.onCreate(savedInstanceState);
		//myGridView=(GridView) findViewById(R.id.gridview);
		//init();
		//oAdapter = new SimpleAdapter(getApplicationContext(), dataList, R.layout.xiangce_image, new String[]{"img"}, new int[]{R.id.image_xiangce});
	}
	public void zhaopian(View View) {
		Intent intent=new Intent(Tab_t4.this, XiangCe.class);
		startActivity(intent);
	}
	public void journal(View view){
		Intent intent=new Intent(Tab_t4.this, Journal_Activity.class);
		startActivity(intent);
	}
	
}
