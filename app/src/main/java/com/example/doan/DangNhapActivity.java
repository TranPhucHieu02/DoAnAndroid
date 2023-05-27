package com.example.doan;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.API.Methods;
import com.example.doan.Model.DocGia;
import com.example.doan.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhapActivity extends AppCompatActivity {

    private EditText username, password;
    private TextView signup,quenMK;
    private Button login;
    private DocGia docGia = new DocGia();
    private boolean passVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        login = findViewById(R.id.buttonLogin);
        signup = findViewById(R.id.textDangKy);
        quenMK = findViewById(R.id.textQuenMK);
        ActionDangNhap();
        ActionDangKy();
        ActionQuenMK();
        ActionShowHide(password);
    }
    private void ActionDangNhap() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(DangNhapActivity.this, "Vui lòng điền đủ thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }
                else{
                    Methods.methods.getDocGia(username.getText().toString().trim(),password.getText().toString().trim()).enqueue(new Callback<DocGia>() {
                        @Override
                        public void onResponse(Call<DocGia> call, Response<DocGia> response) {
                            if(response.body() == null){
                                Toast.makeText(DangNhapActivity.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();

                            }else{
                                docGia = new DocGia();
                                docGia.setUsername(response.body().getUsername());
                                docGia.setPassword(response.body().getPassword());
                                docGia.setTenDg(response.body().getTenDg());
                                docGia.setEmail(response.body().getEmail());
                                docGia.setSdt(response.body().getSdt());
                                docGia.setGioiTinh(response.body().getGioiTinh());

                                Intent intent = new Intent(DangNhapActivity.this,MainActivity.class);
                                intent.putExtra("user", docGia);
                                startActivity(intent);
                                finish();

                            }
                        }

                        @Override
                        public void onFailure(Call<DocGia> call, Throwable t) {
                            Toast.makeText(DangNhapActivity.this,"Lỗi Server!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

        });
    }
    private void ActionDangKy(){
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhapActivity.this,DangKyActivity.class);
                startActivity(intent);

            }
        });
    }
    private void ActionQuenMK(){
        quenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialogQuenMK(Gravity.CENTER);
            }
        });
    }
    private void OpenDialogQuenMK(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_quen_mat_khau);
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
        EditText editEmail = dialog.findViewById(R.id.textEmail);
        Button buttonLayMK = dialog.findViewById(R.id.buttonLayMK);

        buttonLayMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Methods.methods.CheckUserEmail(editUsername.getText().toString().trim(),editEmail.getText().toString().trim()).enqueue(new Callback<DocGia>() {
                    @Override
                    public void onResponse(Call<DocGia> call, Response<DocGia> response) {
                        if(response.body() == null){
                            Toast.makeText(DangNhapActivity.this, "Username hoặc email không đúng", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String username,email;
                            username =response.body().getUsername();
                            email=response.body().getEmail();
                            dialog.dismiss();
                            OpenDialogDatMK(gravity,username,email);

                        }
                    }

                    @Override
                    public void onFailure(Call<DocGia> call, Throwable t) {

                    }
                });

            }
        });
        dialog.show();
    }
    private void OpenDialogDatMK(int gravity, String username, String email){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dat_mat_khau);
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
        EditText editPassword = dialog.findViewById(R.id.textPassword);
        EditText editConfirmPassword = dialog.findViewById(R.id.confirmPassword);
        Button buttonXacNhan = dialog.findViewById(R.id.buttonXacnhan);

        ActionShowHide(editPassword);
        ActionShowHide(editConfirmPassword);

        buttonXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(editPassword.getText().toString().trim());
                if(editPassword.getText().toString().trim().equals(editConfirmPassword.getText().toString().trim())){
                    Methods.methods.LayMatKhau(username,email,user).enqueue(new Callback<DocGia>() {
                        @Override
                        public void onResponse(Call<DocGia> call, Response<DocGia> response) {
                            if(response.body()!=null){
                                dialog.dismiss();
                                Toast.makeText(DangNhapActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            }else{
//                                Toast.makeText(DangNhapActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                Toast.makeText(DangNhapActivity.this, email + "  "+ username + "  "+response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<DocGia> call, Throwable t) {
                            Toast.makeText(DangNhapActivity.this, "Lỗi server"+t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    Toast.makeText(DangNhapActivity.this, "Xác nhận mật khẩu không đúng.", Toast.LENGTH_SHORT).show();
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