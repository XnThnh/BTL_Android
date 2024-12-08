package com.example.btlandroid;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Random;

public class NotificationReceiver extends BroadcastReceiver {
    DBHelper dbHelper;
    SQLiteDatabase db;
    @Override
    public void onReceive(Context context, Intent intent) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getReadableDatabase();
        ArrayList<String> listQuote = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT cauNoi FROM TrichDan", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String cauNoi = cursor.getString(cursor.getColumnIndex("cauNoi"));
                listQuote.add(cauNoi);
            }
            cursor.close();
        }

        db.close();
        if (listQuote.isEmpty()) {
            return;
        }

        int randomIndex = new Random().nextInt(listQuote.size());
        String message = listQuote.get(randomIndex);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "daily_notification_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Thông báo hằng ngày", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("Thông báo mới")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .build();

        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }
}
