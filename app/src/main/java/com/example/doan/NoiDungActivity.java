package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.doan.Model.Truyens;

public class NoiDungActivity extends AppCompatActivity {
    private TextView tenTruyen,noiDung,tieuDe;
    private ImageView imageTruyen;
    private int image;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung);
        tenTruyen = findViewById(R.id.textTenTruyen);
        tieuDe = findViewById(R.id.textTitle);
        noiDung = findViewById(R.id.textNoiDung);
        imageTruyen = findViewById(R.id.imageTruyen);
        toolbar = findViewById(R.id.toolbarND);

        Intent intent = getIntent();
        Truyens truyen = (Truyens) intent.getSerializableExtra("Truyen");
        Glide.with(NoiDungActivity.this)
                .load(truyen.getImageUrl())
                .into(imageTruyen);
        tenTruyen.setText(truyen.getTenTruyen());
        tieuDe.setText(truyen.getTenTruyen());
        imageTruyen.setImageResource(image);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//ẩn tên
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//hiện back trên toolbar

        noiDung.setText(truyen.getNoiDung());
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
}