package com.example.btlandroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Objects;

public class DanhSachSach extends AppCompatActivity {
    private RecyclerView recView;
    private ArrayList<Sach> listSach;
    private BookRecViewAdapter adapter;
    private DBHelper db ;
    private BottomNavigationView btmNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_sach_sach);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DBHelper(this);
        listSach = db.layDSSach();
        db.xoaSach("1");
        recView = findViewById(R.id.recView);
        adapter = new BookRecViewAdapter(this,listSach);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new GridLayoutManager(this,2));
        btmNav = findViewById(R.id.btmNav);
        btmNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.btmDS){
                    Intent i = new Intent(DanhSachSach.this,DanhSachSach.class);
                    startActivity(i);
                }
                if(item.getItemId() == R.id.btmTD){
                    Intent i = new Intent(DanhSachSach.this,TrichDanActivity.class);
                    startActivity(i);
                }
                if(item.getItemId() == R.id.btmDG){
                    Intent intent = new Intent(DanhSachSach.this, DanhGiaActivity.class);
                    // Truyền ID sách qua Intent (ví dụ: chọn sách đầu tiên trong danh sách)
                    if (!listSach.isEmpty()) {
                        intent.putExtra("sachId", listSach.get(0).getId()); // Thay 0 bằng vị trí sách được chọn
                        startActivity(intent);
                    } else {
                        Toast.makeText(DanhSachSach.this, "Không có sách để đánh giá!", Toast.LENGTH_SHORT).show();
                    }
                }
                if(item.getItemId() == R.id.btmDS){
                    Intent i = new Intent(DanhSachSach.this,DanhSachSach.class);
                    startActivity(i);
                }
                return false;
            }
        });
        db.xoaSach("b");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnuThemSach){
            Intent i = new Intent(this,ThemSach.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}