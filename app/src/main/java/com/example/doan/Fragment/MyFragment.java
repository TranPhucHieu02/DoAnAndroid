package com.example.doan.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.API.Methods;
import com.example.doan.Adaptor.DSTruyenAdapter;
import com.example.doan.DangNhapActivity;
import com.example.doan.Model.DocGia;
import com.example.doan.Model.Truyens;
import com.example.doan.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFragment extends Fragment {
    private TextView name,email,sdt,gioitinh;
    private ImageView exit;
    private DocGia docGia;
    private DSTruyenAdapter dsTruyenAdapter;
    private RecyclerView rcvYeuThich;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_my, container, false);
        Intent  intent = getActivity().getIntent();
        docGia = (DocGia) intent.getSerializableExtra("user");
        AnhXa(view);
        ActionExit();
        LoadYeuThich(docGia);
        LoadTT(docGia.getUsername());
        return view;
    }


    private void AnhXa(View view){
        name = view.findViewById(R.id.textName);
        email = view.findViewById(R.id.textEmail);
        sdt = view.findViewById(R.id.textSDT);
        gioitinh = view.findViewById(R.id.textGioiTinh);
        exit = view.findViewById(R.id.exit);
        rcvYeuThich = view.findViewById(R.id.rcvYeuThich);

    }
    private void LoadTT(String Username){

        Methods.methods.CheckUser(Username).enqueue(new Callback<DocGia>() {
            @Override
            public void onResponse(Call<DocGia> call, Response<DocGia> response) {
                if(response!=null){
                    name.setText(response.body().getTenDg());
                    email.setText(response.body().getEmail());
                    sdt.setText(response.body().getSdt());
                    if(response.body().getGioiTinh()==true){
                        gioitinh.setText("Nam");
                    }else{
                        gioitinh.setText("Nữ");
                    }
                }

            }

            @Override
            public void onFailure(Call<DocGia> call, Throwable t) {

            }
        });

    }
    private void LoadYeuThich(DocGia docGia) {
        List<Truyens> listTruyen = new ArrayList<>();
        Methods.methods.GetTuongTacUser(docGia.getUsername()).enqueue(new Callback<List<Truyens>>() {
            @Override
            public void onResponse(Call<List<Truyens>> call, Response<List<Truyens>> response) {
                if (response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        listTruyen.add(i, response.body().get(i));
                    }
                    dsTruyenAdapter = new DSTruyenAdapter(getActivity(), listTruyen,docGia);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                    rcvYeuThich.setLayoutManager(gridLayoutManager);
                    rcvYeuThich.setAdapter(dsTruyenAdapter);
                }else{
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Truyens>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ActionExit(){
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

}