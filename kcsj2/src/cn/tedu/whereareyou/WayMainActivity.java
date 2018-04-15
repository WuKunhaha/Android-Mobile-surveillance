package cn.tedu.whereareyou;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WayMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_way_main);
	
		
		//实现按钮的效果：1.功能选择按钮；2.功能开启/关闭按钮
		//1.功能选择按钮的效果实现：未选中/选中
		//1.先获得界面上的按钮组件；2.增加按钮组件的点击效果
		final Button fpl_btn = (Button)this.findViewById(R.id.FPL_BIN_ID);
		final Button qpl_btn = (Button)this.findViewById(R.id.QPL_BIN_ID);
		final Button msg_btn = (Button)this.findViewById(R.id.MSG_BIN_ID);
		
		fpl_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*Toast.makeText(WayMainActivity.this, "您点击了来电监控功能", Toast.LENGTH_LONG).show();*/
				if(WayInformations.isFPL){
					//表示当前是选中状态，点击后变为未选中
					WayInformations.isFPL=false;
					fpl_btn.setText("来电监控");
				}else{
					//表示当前是未选中状态，点击后变为选中
					WayInformations.isFPL=true;
					fpl_btn.setText("来电监控<√>");
				}
				
			}
			
		});
		qpl_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(WayInformations.isQPL){
					//表示当前是选中状态，点击后变为未选中
					WayInformations.isQPL=false;
					qpl_btn.setText("去电监控");
				}else{
					//表示当前是未选中状态，点击后变为选中
					WayInformations.isQPL=true;
					qpl_btn.setText("去电监控<√>");
				}
				
			}
			
		});
		msg_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(WayInformations.isMSG){
					//表示当前是选中状态，点击后变为未选中
					WayInformations.isMSG=false;
					msg_btn.setText("短信监控");
				}else{
					//表示当前是未选中状态，点击后变为选中
					WayInformations.isMSG=true;
					msg_btn.setText("短信监控<√>");
				}
				
			}
			
		});
		//2.功能开关/关闭按钮的效果实现：开关切换+开关操作
		//1.先获得界面按钮 2.增加按钮点击效果
		final Button gn_btn = (Button)this.findViewById(R.id.GN_BIN_ID);
		gn_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//获取界面上的监控者的手机号码，保存备用
				EditText et=(EditText)findViewById(R.id.ET_ID);
				WayInformations.LPPN =et.getText().toString();
				// TODO Auto-generated method stub
				/*Toast.makeText(WayMainActivity.this, "您点击了来电监控功能", Toast.LENGTH_LONG).show();*/
				if(WayInformations.isGN){
					//表示当前是开启状态，需要：开启状态——>关闭状态——>关闭操作
					WayInformations.isGN =false;
					gn_btn.setText("开启所选监控");
					//补充：解锁界面内容+还原界面内容
					et.setEnabled(true);
					fpl_btn.setEnabled(true);
					qpl_btn.setEnabled(true);
					msg_btn.setEnabled(true);
					et.setText("");
					WayInformations.LPPN = "";
					if(WayInformations.isFPL){
						fpl_btn.setText("来电监控");
						//WayInformations.isFPL = false;
					}
					if(WayInformations.isQPL){
						qpl_btn.setText("去电监控");
					}
					if(WayInformations.isMSG){
						msg_btn.setText("短信监控");
					}
					//程序跳转到Service类中进行关闭监控功能操作
					Intent it = new Intent();
					it.setClass(WayMainActivity.this, WayService.class);
					stopService(it);
				}else{
					//表示当前是关闭状态，需要：关闭状态——>开启状态——>开启操作
					//判断是否满足开启条件
					//条件：1.监控者手机号码为空；2.监控功能至少选择一项
					if(!WayInformations.LPPN.equals("")
							&&(WayInformations.isFPL||WayInformations.isQPL||WayInformations.isMSG)){
						WayInformations.isGN =true;
						gn_btn.setText("关闭所选监控");
						//补充：锁定界面内容：无法修改监控者手机号码以及选择监控功能
						et.setEnabled(false);
						fpl_btn.setEnabled(false);
						qpl_btn.setEnabled(false);
						msg_btn.setEnabled(false);
						//程序跳转到service类中进行开启操作
						Intent it = new Intent();
						it.setClass(WayMainActivity.this, WayService.class);
						startService(it);
					}else{
						Toast.makeText(WayMainActivity.this, "条件不满足，无法开启！", Toast.LENGTH_LONG).show();
						
					}
				
					
				}
				
			}
			
		});
	}


@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.way_main, menu);
	return true;
}
}