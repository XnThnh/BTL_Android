package com.example.btlandroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Thongke extends AppCompatActivity {
    public static final int menu_thong_ke_sach = 0x7f080001;
    public static final int menu_thong_ke_trich_dan = 0x7f080002;
    private DBHelper dbHelper;
    private TextView txtTitle,txtTongSoSach, txtTongSoCauTrichDan, txtSachDuocTrichDanNhieuNhat, txtSoLuongSachTheoTacGia, txtSoLuongSachTheoLoai, txtDanhSachSachTheoLoai, txtSoLuongTrichDanTheoSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);

        txtTitle = findViewById(R.id.txtTitle);
        txtTongSoSach = findViewById(R.id.txtTongSoSach);
        txtTongSoCauTrichDan = findViewById(R.id.txtTongSoCauTrichDan);
        txtSachDuocTrichDanNhieuNhat = findViewById(R.id.txtSachDuocTrichDanNhieuNhat);
        txtSoLuongSachTheoTacGia = findViewById(R.id.txtSoLuongSachTheoTacGia);
        txtSoLuongSachTheoLoai = findViewById(R.id.txtSoLuongSachTheoLoai);
        txtSoLuongTrichDanTheoSach = findViewById(R.id.txtSoLuongTrichDanTheoSach);

        dbHelper = new DBHelper(this);

        hienThiThongKeSach();
        findViewById(R.id.btnBackToMain).setOnClickListener(v -> {
            Intent intent = new Intent(Thongke.this, DanhSachSach.class);
            startActivity(intent);
            finish();
        });
        Button btnExportFile = findViewById(R.id.btnExportFile);
        btnExportFile.setOnClickListener(v -> exportFile());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thongke, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_thong_ke_sach) {
            hienThiThongKeSach();
            return true;
        } else if (id == R.id.menu_thong_ke_trich_dan) {
            hienThiThongKeTrichDan();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void hienThiThongKeSach() {
        txtTitle.setText("Thống kê sách");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Sach", null);
        if (cursor.moveToFirst()) {
            txtTongSoSach.setText("Tổng số sách: " + cursor.getInt(0));
        }
        cursor.close();

        cursor = db.rawQuery("SELECT tenSach, COUNT(*) as soLan FROM TrichDan JOIN Sach ON TrichDan.sachId = Sach.id GROUP BY tenSach ORDER BY soLan DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            txtSachDuocTrichDanNhieuNhat.setText("Sách được trích dẫn nhiều nhất: " + cursor.getString(0));
        } else {
            txtSachDuocTrichDanNhieuNhat.setText("Sách được trích dẫn nhiều nhất: Không có dữ liệu");
        }
        cursor.close();

        cursor = db.rawQuery("SELECT tacGia, COUNT(*) FROM Sach GROUP BY tacGia", null);
        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()) {
            sb.append("- ").append(cursor.getString(0)).append(": ").append(cursor.getInt(1)).append(" sách\n");
        }
        txtSoLuongSachTheoTacGia.setText("Số lượng sách theo tác giả:\n" +  sb.toString());
        cursor.close();

        // Số lượng sách theo loại
        cursor = db.rawQuery("SELECT LoaiSach.ten, COUNT(*) FROM Sach JOIN LoaiSach ON Sach.loaiSachId = LoaiSach.id GROUP BY LoaiSach.ten", null);
        sb = new StringBuilder();
        while (cursor.moveToNext()) {
            sb.append("- ").append(cursor.getString(0)).append(": ").append(cursor.getInt(1)).append(" sách\n");
        }
        txtSoLuongSachTheoLoai.setText("Số lượng sách theo thể loại:\n" + sb.toString());
        cursor.close();


        db.close();
        txtTongSoCauTrichDan.setVisibility(View.GONE);
        findViewById(R.id.cardViewTrichDan).setVisibility(View.GONE);
        findViewById(R.id.cardViewTrichDan1).setVisibility(View.GONE);
        txtSoLuongTrichDanTheoSach.setVisibility(View.GONE);
        txtSoLuongSachTheoLoai.setVisibility(View.VISIBLE);
        txtTongSoSach.setVisibility(View.VISIBLE);
        txtSachDuocTrichDanNhieuNhat.setVisibility(View.VISIBLE);
        txtSoLuongSachTheoTacGia.setVisibility(View.VISIBLE);
        findViewById(R.id.cardViewSach).setVisibility(View.VISIBLE);
        findViewById(R.id.cardViewSach1).setVisibility(View.VISIBLE);
        findViewById(R.id.cardViewSach2).setVisibility(View.VISIBLE);
        findViewById(R.id.cardViewSach3).setVisibility(View.VISIBLE);
    }
    private void hienThiThongKeTrichDan() {
        txtTitle.setText("Thống kê trích dẫn");
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor;
        StringBuilder sb;

        cursor = db.rawQuery("SELECT COUNT(*) FROM TrichDan", null);
        if (cursor.moveToFirst()) {
            txtTongSoCauTrichDan.setText("Tổng số câu trích dẫn: " + cursor.getInt(0));
        }
        cursor.close();

        cursor = db.rawQuery("SELECT Sach.tenSach, COUNT(TrichDan.id) FROM Sach LEFT JOIN TrichDan ON Sach.id = TrichDan.sachId GROUP BY Sach.tenSach", null);
        sb = new StringBuilder();
        while (cursor.moveToNext()) {
            sb.append("- ").append(cursor.getString(0))
                    .append(": ").append(cursor.getInt(1))
                    .append(" câu trích dẫn\n");
        }
        txtSoLuongTrichDanTheoSach.setText("Danh sách số lượng câu trích dẫn theo sách:\n" + sb.toString());
        cursor.close();

        db.close();
        txtTongSoCauTrichDan.setVisibility(View.VISIBLE);
        txtSoLuongTrichDanTheoSach.setVisibility(View.VISIBLE);
        findViewById(R.id.cardViewTrichDan).setVisibility(View.VISIBLE);
        findViewById(R.id.cardViewTrichDan1).setVisibility(View.VISIBLE);
        txtTongSoSach.setVisibility(View.GONE);
        txtSoLuongSachTheoTacGia.setVisibility(View.GONE);
        txtSachDuocTrichDanNhieuNhat.setVisibility(View.GONE);
        txtSoLuongSachTheoLoai.setVisibility(View.GONE);
        findViewById(R.id.cardViewSach).setVisibility(View.GONE);
        findViewById(R.id.cardViewSach1).setVisibility(View.GONE);
        findViewById(R.id.cardViewSach2).setVisibility(View.GONE);
        findViewById(R.id.cardViewSach3).setVisibility(View.GONE);

    }

    private void exportFile() {
        // Lấy đường dẫn thư mục lưu trữ
        File folder = new File(getExternalFilesDir(null), "ThongKeThongBao");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, "ThongKe_Sach.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("THỐNG KÊ SÁCH VÀ TRÍCH DẪN\n\n");

            writer.write("Tổng số sách: " + txtTongSoSach.getText().toString() + "\n");
            writer.write("Sách được trích dẫn nhiều nhất: " + txtSachDuocTrichDanNhieuNhat.getText().toString() + "\n");
            writer.write("Số lượng sách theo tác giả:\n" + txtSoLuongSachTheoTacGia.getText().toString() + "\n");
            writer.write("Số lượng sách theo thể loại:\n" + txtSoLuongSachTheoLoai.getText().toString() + "\n");

            writer.write("\nTHỐNG KÊ TRÍCH DẪN\n\n");
            writer.write("Tổng số câu trích dẫn: " + txtTongSoCauTrichDan.getText().toString() + "\n");
            writer.write("Danh sách số lượng câu trích dẫn theo sách:\n" + txtSoLuongTrichDanTheoSach.getText().toString());

            writer.flush();
            Toast.makeText(this, "File đã được xuất thành công!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi xuất file!", Toast.LENGTH_SHORT).show();
        }
    }

}