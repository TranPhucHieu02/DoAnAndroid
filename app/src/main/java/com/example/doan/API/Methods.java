package com.example.doan.API;

import com.example.doan.Model.DocGia;
import com.example.doan.Model.ThongTinDG;
import com.example.doan.Model.Truyens;
import com.example.doan.Model.TuongTac;
import com.example.doan.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Methods {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    Methods methods = new Retrofit.Builder()
            .baseUrl("http://192.168.244.1:8086")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Methods.class);
//============================== User ===========================
    @GET("/api/DocGias/check-user")
    Call<DocGia> CheckUser (@Query("Username") String Username);
    @GET("/api/DocGias/get-user")
    Call<DocGia> getDocGia (@Query("Username") String Username,@Query("Password") String Password);
    @GET("/api/DocGias/check-user-email")
    Call<DocGia> CheckUserEmail (@Query("Username") String Username,@Query("Email") String Email);
    @POST("/api/DocGias")
    Call<DocGia> insertDcGia(@Body DocGia docGia);
    @PUT("/api/DocGias/update-password")
    Call<DocGia> UpdatePassword (@Query("Username") String Username,@Query("Password") String Password, @Body User user);
    @PUT("/api/DocGias/update-password-quenmk")
    Call<DocGia> LayMatKhau (@Query("Username") String Username, @Query("Email") String Email, @Body User user);
    @PUT("/api/DocGias/update-docgia")
    Call<DocGia> CapNhatTT (@Query("Username") String Username, @Body ThongTinDG thongTinDG);
//===========================   Truyen  ==================================

    @GET("/api/truyens")
    Call<List<Truyens>> GetTruyen ();
    @GET("/api/truyens/get-truyen-tl")
    Call<List<Truyens>> GetTruyenTL (@Query("theLoai") String theLoai);
    @GET("/api/truyens/get-truyen-id")
    Call<Truyens> GetTruyenId (@Query("maTruyen") String maTruyen);
    @PUT("/api/truyens/update-luotdoc")
    Call<Truyens> UpdateLuotDoc(@Query("maTruyen") String maTruyen);

//=========================  Tuong Tac  ==================================

    @GET("/api/tuongtacs/get-tuongtac")
    Call<TuongTac> GetTuongTacTruyen (@Query("Username") String Username,@Query("maTruyen") String maTruyen);
    @GET("/api/tuongtacs/get-tuongtac-user")
    Call<List<Truyens>> GetTuongTacUser (@Query("Username") String Username);
    @POST("/api/tuongtacs/post-yeuthich")
    Call<TuongTac> UpdateYeuThich(@Body TuongTac tuongTac);
    @POST("/api/tuongtacs/post-sao")
    Call<TuongTac> UpdateSao(@Body TuongTac tuongTac);

}