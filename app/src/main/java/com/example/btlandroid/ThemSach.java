package com.example.btlandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ThemSach extends AppCompatActivity {
    private EditText edtTenSach, edtTacGia, edtSoTrang, edtURLAnh, edtMoTa;;
    private Button btnThem, btnHuy;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_sach);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtTenSach = findViewById(R.id.edtTenSach);
        edtTacGia = findViewById(R.id.edtTacGia);
        edtSoTrang = findViewById(R.id.edtSoTrang);
        edtURLAnh = findViewById(R.id.edtURLAnh);
        edtMoTa = findViewById(R.id.edtMoTa);

        btnThem = findViewById(R.id.btnThem);
        btnHuy = findViewById(R.id.btnHuy);
        spinner = findViewById(R.id.spinner);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Văn học");list.add("Cổ tích");list.add("Văn học thiếu nhi");list.add("Văn học hiện thực");
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list));


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(ThemSach.this);
                Sach s = new Sach(edtTenSach.getText().toString(),edtTacGia.getText().toString(),Integer.parseInt(edtSoTrang.getText().toString()),edtMoTa.getText().toString(),edtURLAnh.getText().toString(),getLoaiSachId(spinner.getSelectedItem().toString()));
                if(db.themSach(s)){;
                    Intent i = new Intent(ThemSach.this, DanhSachSach.class);
                    startActivity(i);
                }
                else Toast.makeText(ThemSach.this, "Them sach khong thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ThemSach.this, DanhSachSach.class);
                startActivity(i);
            }
        });
    }
        private int getLoaiSachId(String loaiSach){
        switch (loaiSach){
            case "Văn học":
                return 1;
            case "Cổ tích":
                return 2;
            case "Văn học thiếu nhi":
                return 3;
            case "Văn học hiện thực":
                return 4;
        }
        return 0;
        }
}