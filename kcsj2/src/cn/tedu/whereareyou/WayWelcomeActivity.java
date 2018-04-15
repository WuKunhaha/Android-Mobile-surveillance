package cn.tedu.whereareyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WayWelcomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.welcome);
		new Handler().postDelayed(new Runnable(){
			public void run() {
				//创建跳转意图对象
				Intent it = new Intent();
				it.setClass(WayWelcomeActivity.this, WayMainActivity.class);
				startActivity(it);
				finish();
			};
		},3*1000);
	}
}
