package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.doan.API.Methods;
import com.example.doan.Model.DocGia;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyActivity extends AppCompatActivity {
    private EditText name,username,password,confirmPassword,email,phone;
    private Button dangKy,thoat;
    private boolean passVisible;
    private RadioButton rdoNam,rdoNu;
    private DocGia docGia =new DocGia();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        rdoNam = findViewById(R.id.rdoNam);
        rdoNu = findViewById(R.id.rdoNu);
        name = findViewById(R.id.textName);
        email = findViewById(R.id.textEmail);
        phone = findViewById(R.id.textSDT);
        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        confirmPassword = findViewById(R.id.textConfirmPassword);
        dangKy = findViewById(R.id.buttonDangKy);
        thoat = findViewById(R.id.buttonThoat);

        ActionDangKy();
        ActionHuy();
        ActionShowHide();
    }
    private void ActionDangKy(){
        dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()||username.getText().toString().isEmpty()||
                        password.getText().toString().isEmpty()||email.getText().toString().isEmpty()||
                        phone.getText().toString().isEmpty()||confirmPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(DangKyActivity.this, "Vui lòng điền đủ thông tin đăng ký.", Toast.LENGTH_SHORT).show();
                }
                else{
                    docGia.setTenDg(name.getText().toString().trim());
                    if(rdoNam.isChecked()){
                        docGia.setGioiTinh(true);
                    }else{
                        docGia.setGioiTinh(false);
                    }
                    docGia.setEmail(email.getText().toString().trim());
                    docGia.setSdt(phone.getText().toString().trim());
                    docGia.setUsername(username.getText().toString().trim());
                    docGia.setPassword(password.getText().toString().trim());
                    if(docGia.getPassword().trim().equals(confirmPassword.getText().toString().trim())){
                        Methods.methods.CheckUser(docGia.getUsername()).enqueue(new Callback<DocGia>() {
                            @Override
                            public void onResponse(Call<DocGia> call, Response<DocGia> response) {
                                if(response.body()==null){
                                    Methods.methods.insertDcGia(docGia).enqueue(new Callback<DocGia>() {
                                        @Override
                                        public void onResponse(Call<DocGia> call, Response<DocGia> response) {
                                            Toast.makeText(DangKyActivity.this,"Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                            DangKyActivity.super.onBackPressed();
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<DocGia> call, Throwable t) {
                                            Toast.makeText(DangKyActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else{
                                    Toast.makeText(DangKyActivity.this, "Username đã tồn tại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<DocGia> call, Throwable t) {
                                Toast.makeText(DangKyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(DangKyActivity.this, "Xác nhận password không đúng", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });


    }
    private void ActionHuy(){
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangKyActivity.super.onBackPressed();
            }
        });
    }

    private void ActionShowHide(){
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right=2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    if(motionEvent.getRawX()>=password.getRight()-password.getCompoundDrawables()[right].getBounds().width()){
                        int selection = password.getSelectionEnd();
                        if(passVisible){
                            password.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.eye_off,0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible =false;
                        }else{
                            password.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.eye,0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible =true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                return false;
            }
        });
        confirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int right=2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    if(motionEvent.getRawX()>=confirmPassword.getRight()-confirmPassword.getCompoundDrawables()[right].getBounds().width()){
                        int selection = confirmPassword.getSelectionEnd();
                        if(passVisible){
                            confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.eye_off,0);
                            confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible =false;
                        }else{
                            confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.eye,0);
                            confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible =true;
                        }
                        confirmPassword.setSelection(selection);
                        return true;
                    }
                return false;
            }
        });
    }
}