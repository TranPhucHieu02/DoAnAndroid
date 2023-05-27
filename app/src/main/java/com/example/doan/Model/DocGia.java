package com.example.doan.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DocGia implements Serializable {
    private String username;
    private String password;
    private String tenDg;
    private String email;
    private String sdt;
    private boolean gioiTinh;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenDg() {
        return tenDg;
    }

    public void setTenDg(String tenDg) {
        this.tenDg = tenDg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}
