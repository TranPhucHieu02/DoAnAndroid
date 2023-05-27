package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.doan.API.Methods;
import com.example.doan.Model.DocGia;
import com.example.doan.Model.Truyens;
import com.example.doan.Model.TuongTac;


import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TTTruyenActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageTruyen;
    private Button btnDoc;
    private CheckBox yeuThich,sao1,sao2,sao3,sao4,sao5;
    private TextView tieuDe,tacGia,theLoai,luotDoc,luotYeuThich,danhGia;
    private Truyens truyen;
    private DocGia docGia ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tttruyen);
        AnhXa();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//ẩn tên
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//hiện back trên toolbar

        Intent intent = getIntent();
        docGia = (DocGia) intent.getSerializableExtra("docgia");

        LoadTT();
        LoadTuongTac();
        ActionYeuThich();
        ActionDanhGia();
        ActionDoc();
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
    private void AnhXa(){
        toolbar = findViewById(R.id.toolbarTT);
        imageTruyen = findViewById(R.id.imageTruyen);
        tieuDe = findViewById(R.id.textTitle);
        btnDoc = findViewById(R.id.buttonDoc);
        yeuThich = findViewById(R.id.cbYeuThich);
        tacGia = findViewById(R.id.textTacGia);
        theLoai = findViewById(R.id.textTheloai);
        luotDoc = findViewById(R.id.textLuotdoc);
        luotYeuThich = findViewById(R.id.textYeuthich);
        danhGia = findViewById(R.id.textDanhgia);
        sao1 = findViewById(R.id.cb1star);
        sao2 = findViewById(R.id.cb2star);
        sao3 = findViewById(R.id.cb3star);
        sao4 = findViewById(R.id.cb4star);
        sao5 = findViewById(R.id.cb5star);
    }
    private void LoadTT(){
    Intent intent = getIntent();
    truyen = (Truyens) intent.getSerializableExtra("Truyen");
    tieuDe.setText(truyen.getTenTruyen());
    Glide.with(TTTruyenActivity.this)
            .load(truyen.getImageUrl())
            .into(imageTruyen);
    tacGia.setText(truyen.getTacGia());
    theLoai.setText(truyen.getTheLoai());
    luotDoc.setText(truyen.getLuotDoc()+"");
    luotYeuThich.setText(truyen.getLuotDanhGia()+"");
    danhGia.setText(truyen.getSoSao()+"/5");
}
    private void ActionDoc(){
        btnDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Methods.methods.UpdateLuotDoc(truyen.getMaTruyen()).enqueue(new Callback<Truyens>() {
                    @Override
                    public void onResponse(Call<Truyens> call, Response<Truyens> response) {

                    }

                    @Override
                    public void onFailure(Call<Truyens> call, Throwable t) {

                    }
                });
                Intent intent = new Intent(TTTruyenActivity.this, NoiDungActivity.class);
                intent.putExtra("Truyen",truyen);
                startActivity(intent);
            }
        });
    }
    private void ActionYeuThich(){
        yeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TuongTac tuongTac = new TuongTac();
                tuongTac.setUsername(docGia.getUsername());
                tuongTac.setMaTruyen(truyen.getMaTruyen().toLowerCase(Locale.ROOT));
                if(yeuThich.isChecked()){
                    tuongTac.setYeuThich(true);
                    tuongTac.setDanhGia(0);
                    APIYeuthich(tuongTac);
                }else{
                    tuongTac.setYeuThich(false);
                    tuongTac.setDanhGia(0);
                    APIYeuthich(tuongTac);
                }
            }
        });
    }

    private void ActionDanhGia(){
        TuongTac tuongTac = new TuongTac();
        tuongTac.setUsername(docGia.getUsername());
        tuongTac.setMaTruyen(truyen.getMaTruyen().toLowerCase(Locale.ROOT));
        sao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sao1.isChecked()==true){
                    tuongTac.setDanhGia(1);
                    APIDanhGia(tuongTac);
                }else{
                    resetDanhGia();
                }
            }
        });
        sao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sao2.isChecked()){
                    sao1.setChecked(true);
                    tuongTac.setDanhGia(2);
                    APIDanhGia(tuongTac);

                }else{
                    resetDanhGia();
                }
            }
        });
        sao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sao3.isChecked()){
                    sao1.setChecked(true);
                    sao2.setChecked(true);
                    tuongTac.setDanhGia(3);
                    APIDanhGia(tuongTac);
                }else{
                    resetDanhGia();
                }
            }
        });
        sao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sao4.isChecked()){
                    sao1.setChecked(true);
                    sao2.setChecked(true);
                    sao3.setChecked(true);
                    tuongTac.setDanhGia(4);
                    APIDanhGia(tuongTac);
                }else{
                    resetDanhGia();
                }
            }
        });
        sao5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sao5.isChecked()){
                    sao1.setChecked(true);
                    sao2.setChecked(true);
                    sao3.setChecked(true);
                    sao4.setChecked(true);
                    tuongTac.setDanhGia(5);
                    APIDanhGia(tuongTac);
                }
                else{
                    resetDanhGia();
                }
            }
        });
    }
    private void LoadTuongTac() {
        Methods.methods.GetTuongTacTruyen(docGia.getUsername(), truyen.getMaTruyen()).enqueue(new Callback<TuongTac>() {
            @Override
            public void onResponse(Call<TuongTac> call, Response<TuongTac> response) {
                if(response.body()!= null){
                    yeuThich.setChecked(response.body().getYeuThich());
                    if(response.body().getDanhGia()!=0){
                        switch (response.body().getDanhGia()){
                            case 1:
                                sao1.setChecked(true);
                                return;
                            case 2:
                                sao1.setChecked(true);
                                sao2.setChecked(true);
                                return;
                            case 3:
                                sao1.setChecked(true);
                                sao2.setChecked(true);
                                sao3.setChecked(true);
                                return;
                            case 4:
                                sao1.setChecked(true);
                                sao2.setChecked(true);
                                sao3.setChecked(true);
                                sao4.setChecked(true);
                                return;
                            case 5:
                                sao1.setChecked(true);
                                sao2.setChecked(true);
                                sao3.setChecked(true);
                                sao4.setChecked(true);
                                sao5.setChecked(true);
                                return;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TuongTac> call, Throwable t) {

            }
        });
    }
    private void APIYeuthich(TuongTac tuongTac){
        Methods.methods.UpdateYeuThich(tuongTac).enqueue(new Callback<TuongTac>() {
            @Override
            public void onResponse(Call<TuongTac> call, Response<TuongTac> response) {

            }

            @Override
            public void onFailure(Call<TuongTac> call, Throwable t) {

            }
        });
    }
    private void APIDanhGia(TuongTac tuongTac){
        Methods.methods.UpdateSao(tuongTac).enqueue(new Callback<TuongTac>() {
            @Override
            public void onResponse(Call<TuongTac> call, Response<TuongTac> response) {

            }

            @Override
            public void onFailure(Call<TuongTac> call, Throwable t) {

            }
        });
    }

    private void resetDanhGia(){
        TuongTac tuongTac = new TuongTac();
        tuongTac.setUsername(docGia.getUsername());
        tuongTac.setMaTruyen(truyen.getMaTruyen().toLowerCase(Locale.ROOT));
        tuongTac.setDanhGia(0);
        APIDanhGia(tuongTac);

        sao1.setChecked(false);
        sao2.setChecked(false);
        sao3.setChecked(false);
        sao4.setChecked(false);
        sao5.setChecked(false);

    }

}