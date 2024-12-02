package com.example.btlandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Library.db";
    private static final int DATABASE_VERSION = 1;
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng LoaiSach
        db.execSQL("CREATE TABLE IF NOT EXISTS LoaiSach (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten TEXT NOT NULL)");

        // Tạo bảng Sach
        db.execSQL("CREATE TABLE IF NOT EXISTS Sach (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSach TEXT NOT NULL," +
                "tacGia TEXT NOT NULL," +
                "soTrang INTEGER," +
                "anh TEXT," +
                "moTa TEXT," +
                "loaiSachId INTEGER," +
                "FOREIGN KEY(loaiSachId) REFERENCES LoaiSach(id))");

        // Tạo bảng DanhGia
        db.execSQL("CREATE TABLE IF NOT EXISTS DanhGia (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "danhGia TEXT NOT NULL," +
                "sachId INTEGER," +
                "userId INTEGER," +
                "rate INTEGER NOT NULL," +
                "FOREIGN KEY(sachId) REFERENCES Sach(id)," +
                "FOREIGN KEY(userId) REFERENCES User(id))");

        // Tạo bảng TrichDan
        db.execSQL("CREATE TABLE IF NOT EXISTS TrichDan (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cauNoi TEXT NOT NULL," +
                "sachId INTEGER," +
                "FOREIGN KEY(sachId) REFERENCES Sach(id))");

        // Tạo bảng User
        db.execSQL("CREATE TABLE IF NOT EXISTS User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "name TEXT NOT NULL)");

        // Tạo bảng ThongKe
        db.execSQL("CREATE TABLE IF NOT EXISTS ThongKe (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "soSachDaDoc INTEGER," +
                "soCauNoiDaTrich INTEGER," +
                "userId INTEGER," +
                "FOREIGN KEY(userId) REFERENCES User(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Xóa bảng nếu đã tồn tại (cập nhật DB schema)
        db.execSQL("DROP TABLE IF EXISTS ThongKe");
        db.execSQL("DROP TABLE IF EXISTS TrichDan");
        db.execSQL("DROP TABLE IF EXISTS DanhGia");
        db.execSQL("DROP TABLE IF EXISTS Sach");
        db.execSQL("DROP TABLE IF EXISTS LoaiSach");
        db.execSQL("DROP TABLE IF EXISTS User");

        // Tạo lại bảng
        onCreate(db);
    }
}
