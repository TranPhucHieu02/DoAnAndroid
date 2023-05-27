package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.doan.API.Methods;
import com.example.doan.Model.DocGia;
import com.example.doan.Model.ThongTinDG;
import com.example.doan.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private boolean passVisible;
    private ThongTinDG thongTinDG = new ThongTinDG();
    DocGia docGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent=getIntent();
        docGia = (DocGia) intent.getSerializableExtra("user");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        getMenuInflater().inflate(R.menu.menu_search,menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
        searchView.setIconified(true);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(true);
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                intent.putExtra("docgia",docGia);
                startActivity(intent);
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.doimatkhau:
                openDialogDoiMK(Gravity.CENTER);
                return true;
            case R.id.doithongtin:
                openDialogDoiTT(Gravity.CENTER);
                return true;
            case R.id.dangxuat:
                Intent intent = new Intent(MainActivity.this,DangNhapActivity.class);
                startActivity(intent);
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    private void openDialogDoiMK(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_doi_mat_khau);
        Window window =dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttrbuter = window.getAttributes();
        windowAttrbuter.gravity =gravity;
        window.setAttributes(windowAttrbuter);
        if (Gravity.CENTER==gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }
        EditText editUsername = dialog.findViewById(R.id.textUsername);
        EditText editPassword = dialog.findViewById(R.id.textPassword);
        EditText editNewPassword = dialog.findViewById(R.id.textNewPassword);
        Button buttonXacNhan = dialog.findViewById(R.id.buttonXacnhan);

        ActionShowHide(editPassword);
        ActionShowHide(editNewPassword);

        buttonXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUsername.getText().toString().isEmpty()||editPassword.getText().toString().isEmpty()||editNewPassword.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User();
                    user.setUsername(editUsername.getText().toString().trim());
                    user.setPassword(editNewPassword.getText().toString().trim());
                    Methods.methods.UpdatePassword(editUsername.getText().toString().trim(),
                            editPassword.getText().toString().trim(),user).enqueue(new Callback<DocGia>() {
                        @Override
                        public void onResponse(Call<DocGia> call, Response<DocGia> response) {
                            if(response.body()!=null){
                                Toast.makeText(MainActivity.this, "Đổi thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(MainActivity.this,DangNhapActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Username hoặc password không chính xác. ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DocGia> call, Throwable t) {

                        }
                    });
                }
            }
        });
        dialog.show();
    }
    private void openDialogDoiTT(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_doi_thong_tin);
        Window window =dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttrbuter = window.getAttributes();
        windowAttrbuter.gravity =gravity;
        window.setAttributes(windowAttrbuter);
        if (Gravity.CENTER==gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }


        EditText editName = dialog.findViewById(R.id.textNamedoi);
        EditText editEmail = dialog.findViewById(R.id.textEmaildoi);
        EditText editSDT = dialog.findViewById(R.id.textSDTdoi);
        RadioButton rdoNam = dialog.findViewById(R.id.rdoNamdoi);
        RadioButton rdoNu = dialog.findViewById(R.id.rdoNudoi);
        Button buttonXacNhan = dialog.findViewById(R.id.buttonXacnhandoi);

        Intent  intent = getIntent();
        DocGia docGia = (DocGia) intent.getSerializableExtra("user");


        buttonXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editName.getText().toString().isEmpty()||editEmail.getText().toString().isEmpty()||editSDT.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    thongTinDG.setTenDg(editName.getText().toString().trim());
                    thongTinDG.setEmail(editEmail.getText().toString().trim());
                    thongTinDG.setSdt(editSDT.getText().toString().trim());
                    if(rdoNam.isChecked()){
                        thongTinDG.setGioiTinh(true);
                    }else{
                        thongTinDG.setGioiTinh(false);
                    }
                    Methods.methods.CapNhatTT(docGia.getUsername(),thongTinDG).enqueue(new Callback<DocGia>() {
                        @Override
                        public void onResponse(Call<DocGia> call, Response<DocGia> response) {
                            if(response.body()!=null){
                                Toast.makeText(MainActivity.this, "Doi Thanh Cong", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else{
                                Toast.makeText(MainActivity.this, response.code()+"", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DocGia> call, Throwable t) {

                        }
                    });
                }


            }
        });
        dialog.show();
    }
    private void ActionShowHide(EditText editText){
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right=2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    if(motionEvent.getRawX()>=editText.getRight()-editText.getCompoundDrawables()[right].getBounds().width()){
                        int selection = editText.getSelectionEnd();
                        if(passVisible){
                            editText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.eye_off,0);
                            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible =false;
                        }else{
                            editText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.eye,0);
                            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible =true;
                        }
                        editText.setSelection(selection);
                        return true;
                    }
                return false;
            }
        });

    }
}