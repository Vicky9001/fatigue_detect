package com.example.myapplication.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class BackService extends Service {
    private static final String TAG = "BackService";
    private static final String BASE_URL = "ws://139.9.116.183:9090/WebSocket/okok";
    private static final String CHANNEL_ID = "wakeup_channel_id";
    private static final String CHANNEL_NAME = "Wakeup Notification Channel";
    private static final int NOTIFICATION_ID = 1;

    private OkHttpClient client;
    private WebSocket mWebSocket;
    private Handler mHandler = new Handler();
    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");

        // 创建通知管理器
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 创建网络请求线程
        new Thread(new InitSocketThread()).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");

        // 关闭 WebSocket 长连接
        if (mWebSocket != null) {
            mWebSocket.close(1000, "WebSocket closed by client");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initSocket() {
        Request request = new Request.Builder().url(BASE_URL).build();
        client = new OkHttpClient.Builder().build();

        mWebSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                Log.d(TAG, "onOpen() executed");
                mWebSocket = webSocket;
//                mHandler.post(heartBeatRunnable); // 启动心跳检测任务
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                Log.d(TAG, "onMessage() executed: " + text);
                // 在这里处理服务器发送过来的消息
                if(text.equals("wakeup!")){
                    // 发送通知
                    sendWakeupNotification();
                    notificationWakeupPlayVideo();
                } else if(text.equals("focus!")){
                    sendFocusNotification();
                    notificationFocusPlayVideo();
                } else if(text.equals("call!")){
                    // 打电话
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + "18810862301"); // 将电话号码替换成你要拨打的号码
                    intent.setData(data);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 设置FLAG_ACTIVITY_NEW_TASK标志
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                Log.d(TAG, "onFailure() executed: " + t.getMessage());
                // WebSocket 连接失败，进行重连操作
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initSocket();
                    }
                }, 5000);
            }
        });
    }

    // 心跳检测任务
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (mWebSocket != null) {
                boolean isSendSuccess = mWebSocket.send("HeartBeat");
                if (!isSendSuccess) {
                    Log.d(TAG, "send heart beat message failed");
                    // WebSocket 发送消息失败，进行重连操作
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initSocket();
                        }
                    }, 5000);
                } else {
                    Log.d(TAG, "send heart beat message success");
                    // WebSocket 发送消息成功，继续进行心跳检测
                    mHandler.postDelayed(this, 10000);
                }
            }
        }
    };

    // 创建网络请求线程
    private class InitSocketThread implements Runnable {
        @Override
        public void run() {
            initSocket();
        }
    }

    // 发送通知
    private void sendWakeupNotification() {
        // 创建通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
        }

        // 创建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Wake Up!")
                .setContentText("你清醒一点！！！！！！！")
                .setSmallIcon(R.drawable.baseline_doorbell_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // 发送通知
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void sendFocusNotification() {
        // 创建通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
        }

        // 创建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Focus!")
                .setContentText("开车不专心，亲人两行泪！！！！！！！")
                .setSmallIcon(R.drawable.baseline_doorbell_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // 发送通知
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void notificationWakeupPlayVideo() {
        //自定义声音
        Uri uri  = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.wakeup);
        Ringtone rt = RingtoneManager.getRingtone(getApplicationContext(), uri);
        rt.play();
    }

    public void notificationFocusPlayVideo() {
        //自定义声音
        Uri uri  = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.focus);
        Ringtone rt = RingtoneManager.getRingtone(getApplicationContext(), uri);
        rt.play();
    }
}