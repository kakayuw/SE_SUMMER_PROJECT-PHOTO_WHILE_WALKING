package com.photowalking.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.photowalking.FragmentsActity;
import com.photowalking.fragment.FriendFragment;
import com.photowalking.friends.AddFriendActivity;
import com.photowalking.model.Friend;
import com.photowalking.model.SendMsg;
import com.photowalking.share.ViewOthersDetailActivity;

import net.sf.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "MyReceiver";
	private static String FRI_APPLY_TITLE = "好友申请";
	private static String FRI_ADD_TITLE = "ADDFRIEND";
	private static String FRI_AVATAR_TITLE = "CHGAVATAR";
	private static String BEST_SHARE_TITLE = "最佳分享";
	private static String APP_TITLE = "边走边拍";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Log.d(TAG, "接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
				String title = bundle.getString(JPushInterface.EXTRA_TITLE);
				if(title.equals(FRI_ADD_TITLE)){
					if(FragmentsActity.isfriFragOpend()){
						String info = bundle.getString(JPushInterface.EXTRA_EXTRA);
						Gson gson = new Gson();
						Friend friend = gson.fromJson(info,Friend.class);
						FriendFragment.getAdapter().addItem(friend);
						FriendFragment.getAdapter().notifyDataSetChanged();
					}
				}else{
					String info = bundle.getString(JPushInterface.EXTRA_EXTRA);
					Gson gson = new Gson();
					SendMsg sendMsg = gson.fromJson(info, SendMsg.class);
					if(sendMsg != null && sendMsg.getTitle().equals(FRI_AVATAR_TITLE)){
						Log.d(TAG, "更新好友头像");
						if(FragmentsActity.isfriFragOpend()){
							FriendFragment.getAdapter().notifyAvatarChange(sendMsg.getUid());
						}
					}
				}
			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Log.d(TAG, "接收到推送下来的通知");
//				int notifactionId = bu	ndle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//				Log.d(TAG, "接收到推送下来的通知的ID: " + notifactionId);
			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
				Log.d(TAG, "用户点击打开了通知: "+title);
				if(title.equals(FRI_APPLY_TITLE)){
					String str = bundle.getString(JPushInterface.EXTRA_EXTRA);
					//打开自定义的Activity
					Intent i = new Intent(context, AddFriendActivity.class);
					i.putExtra("applyInfo",str);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
					context.startActivity(i);
				}else if(title.equals(BEST_SHARE_TITLE)){
					String str = bundle.getString(JPushInterface.EXTRA_EXTRA);
					Intent i = new Intent(context, ViewOthersDetailActivity.class);
					JSONObject jsonObject = JSONObject.fromObject(str);
					String sid = jsonObject.getString("sid");
					Log.e("get sid>>>", sid);
					i.putExtra("sid",sid);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
					context.startActivity(i);
				}else if(title.equals(APP_TITLE)){
					Toast.makeText(context,"Just for fun",Toast.LENGTH_SHORT).show();
				}

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Log.d(TAG, "用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Log.w(TAG, intent.getAction() +" connected state change to "+connected);
			} else {
				Log.d(TAG, "Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){
			e.printStackTrace();
		}

	}
}
