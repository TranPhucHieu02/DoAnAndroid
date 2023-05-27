package com.example.doan.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.API.Methods;
import com.example.doan.Adaptor.DSTruyenAdapter;
import com.example.doan.Adaptor.PhotoAdapter;
import com.example.doan.Adaptor.SearchAdapter;
import com.example.doan.DanhSachActivity;
import com.example.doan.Model.DocGia;
import com.example.doan.Model.Truyens;
import com.example.doan.R;
import com.example.doan.SearchActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private TextView truyenTinhYeu,truyenGiaDinh,truyenKinhDi;
    private CircleIndicator circleIndicator;
    private RecyclerView rcvTinhYeu,rcvGiaDinh,rcvKinhDi;
    private Timer timer;
    private PhotoAdapter photoAdapter;
    private SearchAdapter searchAdapter;
    private DocGia docGia;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        Intent intent = getActivity().getIntent();
        docGia = (DocGia) intent.getSerializableExtra("user");
        AnhXa(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LoadListPhoto();
        LoadListTinhYeu();
        LoadListGiaDinh();
        LoadListKinhDi();
        ActionTieuDe();
    }

    private void AnhXa(View view){
        viewPager = view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circle_indicator);
        truyenGiaDinh = view.findViewById(R.id.lbTruyenGiaDinh);
        truyenTinhYeu = view.findViewById(R.id.lbTinhYeu);
        truyenKinhDi = view.findViewById(R.id.lbTruyenKinhDi);
        rcvTinhYeu = view.findViewById(R.id.rcvTinhYeu);
        rcvGiaDinh = view.findViewById(R.id.rcvGiaDinh);
        rcvKinhDi = view.findViewById(R.id.rcvKinhDi);
    }
    private void LoadListPhoto(){
        List<Truyens> listTruyen = new ArrayList<>();
        Methods.methods.GetTruyen().enqueue(new Callback<List<Truyens>>() {
            @Override
            public void onResponse(Call<List<Truyens>> call, Response<List<Truyens>> response) {
                if(response.body()!=null){
                    for(int i=0;i<5;i++){
                        listTruyen.add(i,response.body().get(i));
                    }
                    photoAdapter = new PhotoAdapter(getActivity(),listTruyen,docGia);
                    viewPager.setAdapter(photoAdapter);
                    circleIndicator.setViewPager(viewPager);
                    photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

                    if(listTruyen == null||listTruyen.isEmpty()||viewPager==null){
                        return;
                    }

                    if(timer == null){
                        timer = new Timer();
                    }

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    int currentItem = viewPager.getCurrentItem();
                                    int totalItem = listTruyen.size()-1;
                                    if(currentItem<totalItem){
                                        currentItem++;
                                        viewPager.setCurrentItem(currentItem);
                                    }else{
                                        viewPager.setCurrentItem(0);
                                    }
                                }
                            });
                        }
                    },500,3000);
                }else{
                    Toast.makeText(getActivity(), response.code()+"", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Truyens>> call, Throwable t) {

            }
        });
    }

    private void LoadListTinhYeu(){
        List<Truyens> listTruyen = new ArrayList<>();
        Methods.methods.GetTruyenTL("Tình yêu").enqueue(new Callback<List<Truyens>>() {
            @Override
            public void onResponse(Call<List<Truyens>> call, Response<List<Truyens>> response) {
                if(response.body()!=null){
                    for(int i=0;i<5;i++){
                        listTruyen.add(i,response.body().get(i));
                    }
                    searchAdapter = new SearchAdapter(getActivity(),listTruyen,docGia);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                    rcvTinhYeu.setLayoutManager(gridLayoutManager);
                    rcvTinhYeu.setAdapter(searchAdapter);
                }else{
                    Toast.makeText(getActivity(), response.code()+"", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Truyens>> call, Throwable t) {
                Toast.makeText(getActivity(), t.hashCode(), Toast.LENGTH_SHORT).show();

            }
        });
    }
private void LoadListKinhDi(){
    List<Truyens> listTruyen = new ArrayList<>();
    Methods.methods.GetTruyenTL("Kinh dị").enqueue(new Callback<List<Truyens>>() {
        @Override
        public void onResponse(Call<List<Truyens>> call, Response<List<Truyens>> response) {
            if(response.body()!=null){
                for(int i=0;i<5;i++){
                    listTruyen.add(i,response.body().get(i));
                }
                searchAdapter = new SearchAdapter(getActivity(),listTruyen,docGia);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                rcvKinhDi.setLayoutManager(gridLayoutManager);
                rcvKinhDi.setAdapter(searchAdapter);
            }else{
                Toast.makeText(getActivity(), response.code()+"", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFailure(Call<List<Truyens>> call, Throwable t) {
            Toast.makeText(getActivity(), t.hashCode(), Toast.LENGTH_SHORT).show();

        }
    });

}
    private void LoadListGiaDinh(){
        List<Truyens> listTruyen = new ArrayList<>();
        Methods.methods.GetTruyenTL("Gia đình").enqueue(new Callback<List<Truyens>>() {
            @Override
            public void onResponse(Call<List<Truyens>> call, Response<List<Truyens>> response) {
                if(response.body()!=null){
                    for(int i=0;i<5;i++){
                        listTruyen.add(i,response.body().get(i));
                    }
                    searchAdapter = new SearchAdapter(getActivity(),listTruyen,docGia);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
                    rcvGiaDinh.setLayoutManager(gridLayoutManager);
                    rcvGiaDinh.setAdapter(searchAdapter);
                }else{
                    Toast.makeText(getActivity(), response.code()+"", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Truyens>> call, Throwable t) {
                Toast.makeText(getActivity(), t.hashCode(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
            timer= null;
        }
    }

    private void ActionTieuDe(){
        truyenTinhYeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadData("Tình Yêu","Truyện tình yêu",docGia);
            }
        });
        truyenGiaDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadData("Gia đình","Truyện gia đình",docGia);
            }
        });
        truyenKinhDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadData("Kinh dị","Truyện kinh dị",docGia);
            }
        });
    }

    public void LoadData(String theLoai,String tieuDe,DocGia docGia){
        Intent intent = new Intent(getActivity(), DanhSachActivity.class);
        intent.putExtra("theloai",theLoai);
        intent.putExtra("tieude", tieuDe);
        intent.putExtra("docgia",docGia);
        startActivity(intent);

    }


}