package com.example.btlandroid;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DanhGiaActivity extends AppCompatActivity {
    private EditText edtDanhGia;
    private RatingBar ratingBar;
    private Button btnSubmit;
    private DBHelper dbHelper;
    private int sachId;
    private RecyclerView rvDanhGia;
    private DanhGiaAdapter adapter;
    private ArrayList<DanhGia> danhGiaList;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_gia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rvDanhGia = findViewById(R.id.rvDanhGia);
        db = new DBHelper(this);
        int sachId = getIntent().getIntExtra("sachId", -1);

        if (sachId != -1) {
            danhGiaList = db.layDanhSachDanhGia(sachId);
        } else {
            danhGiaList = new ArrayList<>();
        }

        adapter = new DanhGiaAdapter(this, danhGiaList);
        rvDanhGia.setAdapter(adapter);
        rvDanhGia.setLayoutManager(new LinearLayoutManager(this));


//        // Lấy ID sách được chọn từ Intent
//        sachId = getIntent().getIntExtra("sachId", -1);

        edtDanhGia = findViewById(R.id.edtDanhGia);
        ratingBar = findViewById(R.id.ratingBar);
        btnSubmit = findViewById(R.id.btnSubmit);

        dbHelper = new DBHelper(this);

        btnSubmit.setOnClickListener(v -> {
            String danhGia = edtDanhGia.getText().toString();
            int rate = (int) ratingBar.getRating();

            if (sachId != -1 && !danhGia.isEmpty() && rate > 0) {
                dbHelper.insertDanhGia(sachId, danhGia, rate);
                Toast.makeText(this, "Đánh giá đã được lưu!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
