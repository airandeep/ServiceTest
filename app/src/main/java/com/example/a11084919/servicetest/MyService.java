package com.example.a11084919.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.os.strictmode.DiskWriteViolation;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {

    private static String TAG = "MyService";

    private DownloadBinder mBinder = new DownloadBinder();



    public MyService() {
    }

    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d(TAG, "startDownload: 已执行");
        }

        public int getProgress(){
            Log.d(TAG, "getProgress: 已执行");
            return 0;
        }
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "onCreate: 已经执行");

        String CHANNEL_ONE_ID = "com.primedu.cn";

//        String CHANNEL_ONE_NAME = "Channel One";
//        NotificationChannel notificationChannel = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
//                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.setShowBadge(true);
//            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            manager.createNotificationChannel(notificationChannel);
//        }



        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        Notification notification = new NotificationCompat.Builder(this)
                .setChannelId("chat")
                .setContentTitle("这是标题")
                .setContentText("这是内容")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        
        startForeground(1,notification);
    }


    public int onStartCommand(Intent intent,int flags,int startId){
        Log.d(TAG, "onStartCommand: 已经执行");
        return super.onStartCommand(intent,flags,startId);
    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy: 已经执行");
    }
}
