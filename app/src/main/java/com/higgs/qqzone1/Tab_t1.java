package com.higgs.qqzone1;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Tab_t1 extends Activity {
	private TextView textView;
	private Button cancal;
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.t1);
		super.onCreate(savedInstanceState);
		textView=(TextView) findViewById(R.id.textView1);
		cancal=(Button) findViewById(R.id.cancal);
		textView.setText("全部动态");
		cancal.setText("筛选");
	}
	public void cancal(View view) {
		
	}
	public void shuaxin(View view) {
		
	}
}
