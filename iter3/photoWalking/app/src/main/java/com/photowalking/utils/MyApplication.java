package com.photowalking.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.photowalking.FragmentsActity;
import com.photowalking.R;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lionel on 2017/8/20.
 */

public class MyApplication extends Application {

    private static String TAG = "MyApplication";

    private NotificationManager manager;
    private static NotificationCompat.Builder builder;

    // Starts as true in order to be notified on first launch
    private boolean isBackground = true;

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
        JPushInterface.init(this);
        if(JPushInterface.isPushStopped(this))
            JPushInterface.resumePush(this);
        initImageLoader(getApplicationContext());

        manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        listenActivity();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    private void listenActivity() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.e(TAG, activity.getClass().getSimpleName()+" is created");
                if (isBackground) {
                    notifyForeground();
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.e(TAG, activity.getClass().getSimpleName()+" is started");
                if (isBackground) {
                    notifyForeground();
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.e(TAG, activity.getClass().getSimpleName()+" is resumed");
                if (isBackground) {
                    notifyForeground();
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.e(TAG, activity.getClass().getSimpleName()+" is paused");
                isBackground = true;
                notifyBackground(activity);
            }



            @Override
            public void onActivityStopped(Activity activity) {
                Log.e(TAG, activity.getClass().getSimpleName()+" is stopped");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                manager.cancel(1);
//                Log.e(TAG, activity.getClass().getSimpleName()+" is destroyed");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
        });
    }

//    private void listenForScreenTurningOff(Context context) {
//        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                isBackground = true;
//                notifyBackground(context);
//            }
//        }, screenStateFilter);
//    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isBackground = true;
            notifyBackground(this);
        }
    }

    private void notifyForeground() {
        isBackground = false;
        manager.cancel(1);
    }

    private void notifyBackground(Context context) {
        if(builder==null){
            builder = new NotificationCompat.Builder(context);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                builder.setSmallIcon(R.drawable.ic_white_launcher);
                builder.setColor(0xFFCC1D1D);
            }
            else {
                builder.setSmallIcon(R.mipmap.ic_launcher);
            }
            builder.setContentText("正在后台运行");
//            builder.setOngoing(true);
        }
        if(isBackground){
            String uid = UserInfoSharedPreference.getUid(this);
            String uname = UserInfoSharedPreference.getUname(this);
            Intent intent = new Intent(context, FragmentsActity.class);
            intent.putExtra("me",uid);
            intent.putExtra("uname",uname);
            PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,0);
            builder.setContentIntent(pIntent);
            Bitmap bmp = BitmapUtil.decodeSampledBitmapFromFd(UrlPath.avatarPath+"/"+uid+".jpg",24,24);
            builder.setLargeIcon(bmp);
            builder.setContentTitle(uname);
            Notification notification = builder.build();
            manager.notify(1,notification);
        }
    }

    public boolean isBackground() {
        return isBackground;
    }
}
