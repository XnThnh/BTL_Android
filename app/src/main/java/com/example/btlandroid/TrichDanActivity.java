package com.example.btlandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import java.io.FileOutputStream;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class TrichDanActivity extends AppCompatActivity {
    Spinner spinner;
    Cursor cursor = null;
    Button btnAdd, btnSave, btnNotify;
    EditText editTextCauNoi;
    TrichDanAdapter adapter;
    ArrayList<TrichDan> listTrichDan;
    ListView listView;
    DBHelper dbHelper;
    SQLiteDatabase db;
    Button btnUpdate;
    ArrayAdapter<String> adapterSpinner;
    ArrayList<String> listTenSach;
    private int selectedQuoteId = -1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trichdan);
         dbHelper = new DBHelper(this);
         db = dbHelper.getReadableDatabase();
        listTenSach = new ArrayList<>();
        cursor = db.rawQuery("SELECT * FROM Sach", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String tenSach = cursor.getString(cursor.getColumnIndex("tenSach"));
                listTenSach.add(tenSach);
            }
        }
        spinner = findViewById(R.id.spinner);
        adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listTenSach);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                int bookId = getBookPositionById(i, db);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        listView = findViewById(R.id.listView);
        getListCauNoi(db);
        adapter.notifyDataSetChanged();
        adapterSpinner.notifyDataSetChanged();

        btnAdd = findViewById(R.id.btnAddQuote);
        editTextCauNoi = findViewById(R.id.input_caunoi);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quote = editTextCauNoi.getText().toString();
                String selectedBook = spinner.getSelectedItem().toString();
                if (quote.isEmpty()) {
                    Toast.makeText(TrichDanActivity.this, "Vui lòng nhập câu nói", Toast.LENGTH_SHORT).show();
                    return;
                }
                int bookId = getBookIdByName(selectedBook, db);
                if (bookId != -1) {
                    addCauNoi(quote, bookId, db);
                    Toast.makeText(TrichDanActivity.this, "Câu nói đã được thêm!", Toast.LENGTH_SHORT).show();
                    getListCauNoi(db);
                    editTextCauNoi.setText("");
                    

                } else {
                    Toast.makeText(TrichDanActivity.this, "Không tìm thấy quyển sách", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setVisibility(View.INVISIBLE);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQuote = editTextCauNoi.getText().toString();
                String selectedBook = spinner.getSelectedItem().toString();

                if (selectedQuoteId != -1 && !newQuote.isEmpty()) {
                    int bookId = getBookIdByName(selectedBook, db);
                    if (bookId != -1) {
                        updateQuote(newQuote, selectedQuoteId, bookId);
                        Toast.makeText(TrichDanActivity.this, "Cập nhật câu nói thành công!", Toast.LENGTH_SHORT).show();
                        getListCauNoi(db);
                        btnUpdate.setVisibility(View.INVISIBLE);

                        editTextCauNoi.setText("");
                        selectedQuoteId = -1;
                    }
                } else {
                    Toast.makeText(TrichDanActivity.this, "Vui lòng nhập câu nói", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
                    String formattedDate = now.format(formatter);
                    String fileName = "DanhSach_" + formattedDate + ".  ";
                    saveToFile(fileName, listTrichDan);
                }
            }
        });
        btnNotify = findViewById(R.id.btnNotify);
        btnNotify.setOnClickListener(v -> {
            requestNotificationPermission();
            scheduleDailyNotification();
            Toast.makeText(this, "Đã đặt lịch thông báo hằng ngày!", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_trichdan, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TrichDan selectedQuote = listTrichDan.get(info.position);

        int itemId = item.getItemId();

        if (itemId == R.id.menu_edit) {

            editTextCauNoi.setText(selectedQuote.getCauNoi());
            Sach sach = selectedQuote.getSach();
            if (sach != null) {
                int position = getBookPositionById(sach.getId(), db);
                spinner.setSelection(position);
            }
            selectedQuoteId = selectedQuote.getId();
            btnUpdate.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Sửa câu nói: " + selectedQuote.getCauNoi(), Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemId == R.id.menu_delete) {
            deleteQuote(selectedQuote);
            Toast.makeText(this, "Đã xoá câu nói", Toast.LENGTH_SHORT).show();

            getListCauNoi(db);
            return true;
        }

        return super.onContextItemSelected(item);
    }
    @SuppressLint("Range")
    private int getBookPositionById(int bookId, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM Sach", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                if (id == bookId) {
                    int position = cursor.getPosition();
                    cursor.close();
                    return position;
                }
            }
            cursor.close();
        }
        return -1;
    }
    @SuppressLint("Range")
    private int getBookIdByName(String bookName, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT id FROM Sach WHERE tenSach = ?", new String[]{bookName});
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("id"));
        }
        return -1;
    }
    @SuppressLint("Range")
    private Sach getSachById(int sachId, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM Sach WHERE id = ?", new String[]{String.valueOf(sachId)});
        if (cursor.moveToFirst()) {
            Sach sach = new Sach(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("tenSach")),
                    cursor.getString(cursor.getColumnIndex("tacGia")),
                    cursor.getInt(cursor.getColumnIndex("soTrang")),
                    cursor.getString(cursor.getColumnIndex("anh")),
                    cursor.getString(cursor.getColumnIndex("moTa")),
                    cursor.getInt(cursor.getColumnIndex("loaiSachId"))
            );
            cursor.close();
            return sach;
        }
        cursor.close();
        return null;
    }

    private void addCauNoi(String quote, int bookId, SQLiteDatabase db) {
        String insertQuery = "INSERT INTO TrichDan (cauNoi, sachId) VALUES (?, ?)";
        db.execSQL(insertQuery, new Object[]{quote, bookId});
    }
    private void deleteQuote(TrichDan trichDan) {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String deleteQuery = "DELETE FROM TrichDan WHERE id = ?";
        db.execSQL(deleteQuery, new Object[]{trichDan.getId()});
        db.close();
    }
    private void updateQuote(String newQuote, int quoteId, int bookId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String updateQuery = "UPDATE TrichDan SET cauNoi = ?, sachId = ? WHERE id = ?";
        db.execSQL(updateQuery, new Object[]{newQuote, bookId, quoteId});
        db.close();
    }
    @SuppressLint("Range")
    private void getListCauNoi(SQLiteDatabase db) {
        listTrichDan = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM TrichDan", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                 int id = cursor.getInt(cursor.getColumnIndex("id"));
                 String cauNoi = cursor.getString(cursor.getColumnIndex("cauNoi"));
                 int sachId = cursor.getInt(cursor.getColumnIndex("sachId"));
                Sach sach = getSachById(sachId, db);
                TrichDan trichDan = new TrichDan(id, cauNoi, sach);
                listTrichDan.add(trichDan);
                //Log.d("TrichDanActivity", "Trích dẫn: " + cauNoi + " - Sách: " + (sach != null ? sach.getTenSach() : "null"));
            }
        }
        cursor.close();
        listView = findViewById(R.id.listView);
        adapter = new TrichDanAdapter(this, listTrichDan);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    private void saveToFile(String fileName, ArrayList<TrichDan> data) {
        int index = 0;
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            for (TrichDan item : data) {
                fos.write(((index++) + ". " + item.getCauNoi() + " - "
                        + item.getSach().getTenSach() + " - "
                        + item.getSach().getTacGia() + "\n").getBytes());
            }
            Toast.makeText(TrichDanActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(TrichDanActivity.this, "Lỗi khi lưu file: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }
    }
    private void sendNotification(String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "daily_notification_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Thông báo hằng ngày", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Thông báo mới")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .build();

        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }
//    private void scheduleDailyNotification() {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, NotificationReceiver.class);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        long currentTime = System.currentTimeMillis();
//        long randomTime = currentTime + (long) (Math.random() * 24 * 60 * 60 * 1000); // Thời gian ngẫu nhiên trong 24 giờ
//
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, randomTime, AlarmManager.INTERVAL_DAY, pendingIntent);
//    }
//private void scheduleDailyNotification() {
//    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//    Intent intent = new Intent(this, NotificationReceiver.class);
//    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//    // Đặt thời gian là 9h10 tối
//    Calendar calendar = Calendar.getInstance();
//    calendar.set(Calendar.HOUR_OF_DAY, 21); // 9h tối
//    calendar.set(Calendar.MINUTE, 12);     // 10 phút
//    calendar.set(Calendar.SECOND, 0);      // 0 giây
//    calendar.set(Calendar.MILLISECOND, 0);
//
//    // Nếu thời gian đã trôi qua trong ngày, đặt cho ngày mai
//    long currentTime = System.currentTimeMillis();
//    if (calendar.getTimeInMillis() <= currentTime) {
//        calendar.add(Calendar.DAY_OF_MONTH, 1);
//    }
//
//    long notificationTime = calendar.getTimeInMillis();
//
//    // Đặt thông báo lặp lại mỗi ngày
//    alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            notificationTime,
//            AlarmManager.INTERVAL_DAY,
//            pendingIntent
//    );
//}
private void scheduleDailyNotification() {
    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(this, NotificationReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE);


    // Thời gian hiện tại
    long currentTime = System.currentTimeMillis();
    long oneMinuteInterval = 60 * 1000;

    // Đặt thông báo lặp lại mỗi phút
    alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            currentTime + oneMinuteInterval,
            oneMinuteInterval,
            pendingIntent
    );
}



}