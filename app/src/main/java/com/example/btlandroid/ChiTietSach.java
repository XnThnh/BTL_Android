package com.example.btlandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class ChiTietSach extends AppCompatActivity {
    private TextView txtCTSTenSach,txtCTSSoTrang,txtCTSTacGia,txtCTSMoTa;
    private ImageView ctsImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_sach);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtCTSTenSach = findViewById(R.id.txtCTSTenSach);
        txtCTSSoTrang = findViewById(R.id.txtCTSSoTrang);
        txtCTSTacGia = findViewById(R.id.txtCTSTacGia);
        txtCTSMoTa = findViewById(R.id.txtCTSMoTa);
        ctsImageView = findViewById(R.id.ctsImageView);
        Intent i = getIntent();
        txtCTSTenSach.setText(i.getStringExtra("tenSach"));
        txtCTSSoTrang.setText(String.valueOf(i.getIntExtra("soTrang",0)));
        txtCTSTacGia.setText(i.getStringExtra("tacGia"));
        txtCTSMoTa.setText(i.getStringExtra("moTa"));
        Glide.with(this).asBitmap().load(i.getStringExtra("anh")).into(ctsImageView);
    }

}