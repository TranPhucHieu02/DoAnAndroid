package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.API.Methods;
import com.example.doan.Adaptor.DSTruyenAdapter;
import com.example.doan.Model.DocGia;
import com.example.doan.Model.Truyens;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachActivity extends AppCompatActivity {
    private DSTruyenAdapter dsTruyenAdapter;
    Toolbar toolbar;
    TextView titleTheLoai;
    RecyclerView recyclerView;
    String theLoai;
    DocGia docGia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);
        toolbar =findViewById(R.id.toolbarDS);
        titleTheLoai = findViewById(R.id.textTitleTheLoai);
        recyclerView = findViewById(R.id.rcvDSTruyen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//ẩn tên
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        titleTheLoai.setText(intent.getStringExtra("tieude"));
        theLoai = intent.getStringExtra("theloai");
        docGia = (DocGia) intent.getSerializableExtra("docgia");
        LoadListTruyen(theLoai);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @NonNull
    private void LoadListTruyen( String theLoai){
        List<Truyens> listTruyen = new ArrayList<>();
        Methods.methods.GetTruyenTL(theLoai).enqueue(new Callback<List<Truyens>>() {
            @Override
            public void onResponse(Call<List<Truyens>> call, Response<List<Truyens>> response) {
                if(response.body()!=null){
                    for(int i=0;i<response.body().size();i++){
                        listTruyen.add(i,response.body().get(i));
                    }
                    dsTruyenAdapter = new DSTruyenAdapter(DanhSachActivity.this,listTruyen,docGia);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(DanhSachActivity.this, 2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(dsTruyenAdapter);
                }else{
                    Toast.makeText(DanhSachActivity.this, response.code()+"", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Truyens>> call, Throwable t) {
                Toast.makeText(DanhSachActivity.this, t.hashCode(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}