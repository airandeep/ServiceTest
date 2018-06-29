package com.example.a11084919.servicetest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button btnStartService;
    private Button btnStopService;
    private Button btnBindService;
    private Button btnUnbindService;

    private MyService.DownloadBinder downloadBinder;

    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    //以这个为媒介调用服务中的方法
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyService.DownloadBinder)iBinder;

            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }

        btnStartService = findViewById(R.id.btnStartService);
        btnStopService = findViewById(R.id.btnStopService);
        btnBindService = findViewById(R.id.btnBindService);
        btnUnbindService = findViewById(R.id.btnUnbindService);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyService.class);
                startService(intent);

            }
        });


        btnStopService.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,MyService.class);
                stopService(intent);
            }
        });

        btnBindService.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent bindIntent = new Intent(MainActivity.this,MyService.class);
                //回到那个服务的实例化对象，然后调用其中的方法
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
            }
        });

        btnUnbindService.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                unbindService(connection);
            }
        });

    }
}
