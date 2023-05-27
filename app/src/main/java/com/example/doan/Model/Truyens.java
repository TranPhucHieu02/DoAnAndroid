package com.example.doan.Model;

import android.app.DatePickerDialog;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Truyens implements Serializable {
    private String maTruyen;
    private String tenTruyen;
    private String theLoai;
    private String tacGia;
    private String imageUrl;
    private String ngayXb;
    private String noiDung;
    private int luotDoc;
    private int luotDanhGia;
    private float soSao;

    public Truyens(String maTruyen, String tenTruyen, String theLoai, String tacGia, String imageUrl, String ngayXb, String noiDung, int luotDoc, int luotDanhGia, float soSao) {
        this.maTruyen = maTruyen;
        this.tenTruyen = tenTruyen;
        this.theLoai = theLoai;
        this.tacGia = tacGia;
        this.imageUrl = imageUrl;
        this.ngayXb = ngayXb;
        this.noiDung = noiDung;
        this.luotDoc = luotDoc;
        this.luotDanhGia = luotDanhGia;
        this.soSao = soSao;
    }

    public String getMaTruyen() {
        return maTruyen;
    }

    public void setMaTruyen(String maTruyen) {
        this.maTruyen = maTruyen;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNgayXb() {
        return ngayXb;
    }

    public void setNgayXb(String ngayXb) {
        this.ngayXb = ngayXb;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getLuotDoc() {
        return luotDoc;
    }

    public void setLuotDoc(int luotDoc) {
        this.luotDoc = luotDoc;
    }

    public int getLuotDanhGia() {
        return luotDanhGia;
    }

    public void setLuotDanhGia(int luotDanhGia) {
        this.luotDanhGia = luotDanhGia;
    }

    public float getSoSao() {
        return soSao;
    }

    public void setSoSao(float soSao) {
        this.soSao = soSao;
    }
}
