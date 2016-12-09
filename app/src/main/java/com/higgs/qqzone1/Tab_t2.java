package com.higgs.qqzone1;



import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Tab_t2 extends Activity {
	private TextView textView;
	private Button cancal;
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.t2);
		super.onCreate(savedInstanceState);
		textView=(TextView) findViewById(R.id.textView1);
		cancal=(Button) findViewById(R.id.cancal);
		textView.setText("与我相关");
		cancal.setVisibility(View.INVISIBLE);
	}
	public void shuaxin(View view) {
			
		}
}
