package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.doan.API.Methods;
import com.example.doan.Adaptor.DSTruyenAdapter;
import com.example.doan.Adaptor.SearchAdapter;
import com.example.doan.Model.DocGia;
import com.example.doan.Model.Truyens;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private Toolbar toolbar;
    private SearchView searchView;
    DocGia docGia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = findViewById(R.id.toolbarSearch);
        recyclerView = findViewById(R.id.rcvSearch);
        Intent intent = getIntent();
        docGia = (DocGia) intent.getSerializableExtra("docgia");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//ẩn tên
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//hiện back trên toolbar
        LoadListTruyen();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
        searchView.setIconified(false); // Ẩn/hiện hiện bàn phím

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void LoadListTruyen(){

        List<Truyens> listTruyen = new ArrayList<>();
        Methods.methods.GetTruyen().enqueue(new Callback<List<Truyens>>() {
            @Override
            public void onResponse(Call<List<Truyens>> call, Response<List<Truyens>> response) {
                if(response.body()!=null){
                    for(int i=0;i<response.body().size();i++){
                        listTruyen.add(i,response.body().get(i));
                    }
                    searchAdapter = new SearchAdapter(SearchActivity.this,listTruyen,docGia);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this, 1);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(searchAdapter);
                }else{
                    Toast.makeText(SearchActivity.this, response.code()+"", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<List<Truyens>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, t.hashCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}