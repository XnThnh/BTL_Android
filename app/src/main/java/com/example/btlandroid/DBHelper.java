package com.example.btlandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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


        db.execSQL("INSERT INTO User (username, password, name) VALUES ('user1', 'password1', 'Nguyễn Văn A')");
        db.execSQL("INSERT INTO User (username, password, name) VALUES ('user2', 'password2', 'Trần Thị B')");
        db.execSQL("INSERT INTO User (username, password, name) VALUES ('user3', 'password3', 'Lê Văn C')");

        db.execSQL("INSERT INTO DanhGia (danhGia, sachId, userId, rate) VALUES ('Hay', 1, 1, 5)");
        db.execSQL("INSERT INTO DanhGia (danhGia, sachId, userId, rate) VALUES ('Bình thường', 2, 2, 3)");
        db.execSQL("INSERT INTO DanhGia (danhGia, sachId, userId, rate) VALUES ('Không hay', 3, 3, 2)");


        db.execSQL("INSERT INTO TrichDan (cauNoi, sachId) VALUES ('Câu nói hay 1', 1)");
        db.execSQL("INSERT INTO TrichDan (cauNoi, sachId) VALUES ('Câu nói hay 2', 2)");
        db.execSQL("INSERT INTO TrichDan (cauNoi, sachId) VALUES ('Câu nói hay 3', 3)");
        db.execSQL("INSERT INTO TrichDan (cauNoi, sachId) VALUES ('Câu nói hay 4', 4)");
        db.execSQL("INSERT INTO TrichDan (cauNoi, sachId) VALUES ('Câu nói hay 5', 5)");
        db.execSQL("INSERT INTO TrichDan (cauNoi, sachId) VALUES ('Câu nói hay 6', 6)");


        db.execSQL("INSERT INTO ThongKe (soSachDaDoc, soCauNoiDaTrich, userId) VALUES (10, 5, 1)");
        db.execSQL("INSERT INTO ThongKe (soSachDaDoc, soCauNoiDaTrich, userId) VALUES (7, 3, 2)");
        db.execSQL("INSERT INTO ThongKe (soSachDaDoc, soCauNoiDaTrich, userId) VALUES (15, 10, 3)");
        db.execSQL("INSERT INTO LoaiSach (ten) VALUES ('Văn học')");
        db.execSQL("INSERT INTO LoaiSach (ten) VALUES ('Cổ tích')");
        db.execSQL("INSERT INTO LoaiSach (ten) VALUES ('Văn học thiếu nhi')");
        db.execSQL("INSERT INTO LoaiSach (ten) VALUES ('Văn học hiện thực')");

        db.execSQL("INSERT INTO Sach (tenSach, tacGia, soTrang, anh, moTa, loaiSachId) VALUES\n" +
                "('Truyện Kiều', 'Nguyễn Du', 150, 'https://bvhttdl.mediacdn.vn/documents/491966/0/truyen+kieu.jpg', " +
                "'Tấm Cám là một câu chuyện cổ tích Việt Nam thuộc thể loại truyện cổ tích thần kì.', '1'),\n" +
                "('Tấm Cám', 'Không biết', 150, 'https://vanchuongphuongnam.vn/wp-content/uploads/2021/10/tam-cam.jpg', 'Truyện Kiều (chữ Nôm: 傳翹), là một truyện thơ của đại thi hào Nguyễn Du. Đây được xem là truyện thơ nổi tiếng nhất và xét vào hàng kinh điển trong văn học Việt Nam, tác phẩm được viết bằng chữ Nôm, theo thể lục bát, gồm 3.254 câu.', '2'),\n" +
                "('Dế Mèn phiêu lưu ký', 'Tô Hoài', 200, 'https://isach.info/images/story/cover/de_men_phieu_luu_ky__to_hoai.jpg', 'Dế Mèn phiêu lưu ký là một tác phẩm nổi tiếng của nhà văn Tô Hoài. Câu chuyện kể về những cuộc phiêu lưu của Dế Mèn và những bài học về tình bạn, lòng dũng cảm.', '3'),\n" +
                "('Chí Phèo', 'Nam Cao', 120, 'https://product.hstatic.net/200000017360/product/chi-pheo_72e3f1370e484cf49b0fc94ee4ce0f80_master.jpg', 'Chí Phèo là một tác phẩm tiêu biểu của Nam Cao. Tác phẩm miêu tả bi kịch của một người nông dân nghèo bị tha hóa và hành trình tìm lại bản chất lương thiện của con người.', '4'),\n" +
                "('Lão Hạc', 'Nam Cao', 130, 'https://bizweb.dktcdn.net/100/370/339/products/z4529778288710-9a538b8bcac451561af81cd240d963a1.jpg?v=1689758099500', 'Lão Hạc là truyện ngắn của nhà văn Nam Cao, kể về cuộc đời khốn khó của một lão nông giàu lòng tự trọng, phải bán đi chú chó mà ông hết mực yêu thương.', '4');");

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
    public ArrayList<Sach> layDSSach(){
        ArrayList<Sach> list = new ArrayList<>();
        String sql = "SELECT * FROM Sach";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            Sach s = new Sach(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6));
            list.add(s);
        }
        cursor.close();
        return list;
    }
    public boolean themSach(Sach s){
        SQLiteDatabase db = this.getWritableDatabase();
        //String sql = "SELECT * FROM Sach WHERE tenSach = ?";
        //if(){
            ContentValues values = new ContentValues();
            values.put("tenSach",s.getTenSach());
            values.put("tacGia",s.getTacGia());
            values.put("soTrang",s.getSoTrang());
            values.put("anh",s.getUrlAnh());
            values.put("moTa",s.getMoTa());
            values.put("loaiSachId",s.getLoaiSachId());
            db.insert("Sach",null,values);
            db.close();
            return true;
//        }
//        else return false;
    }
    public void xoaSach(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Sach","tenSach = ?",new String[]{String.valueOf(s)});
        db.close();
    }
}
