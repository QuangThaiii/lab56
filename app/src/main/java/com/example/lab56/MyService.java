package com.example.lab56;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyService extends Service {


    public MyService() {
    }

    DatabaseReference node;
    FirebaseDatabase database;
    int i = 0;
    //    NotificationManagerCompat notificationManager;
    public static String NOTIFICATION_CHANNEL = "Thông báo của app";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        database=FirebaseDatabase.getInstance("https://fir-d0684-default-rtdb.asia-southeast1.firebasedatabase.app/");
        node=database.getReference("Images");

//        notificationManager = NotificationManagerCompat.from(TaskService.this);
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(MyService.this);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="kenh thong bao";
            String description="mo ta";

            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel(NOTIFICATION_CHANNEL,name,importance);
            channel.setDescription(description);


            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        node.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded( DataSnapshot snapshot,  String previousChildName) {
                NotificationCompat.Builder builder=new NotificationCompat.Builder(MyService.this,
                        NOTIFICATION_CHANNEL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Thong bao")
                        .setContentText("Thêm một sản phẩm mới")
//                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                //
                notificationManagerCompat.notify(1,builder.build());
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved( DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved( DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
//        Toast.makeText(this, "Khởi tạo service", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this.getApplicationContext(), "Đóng service", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}