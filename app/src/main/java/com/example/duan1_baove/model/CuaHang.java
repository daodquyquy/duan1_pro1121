package com.example.duan1_baove.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cuahang")
public class CuaHang {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int gia;
    private String img;
    private String tinhTrang;
    private int soLuong;
    private int trongLuong;
    private String hangSanXuat;

    public CuaHang(int id, String name, int gia, String img, String tinhTrang, int soLuong, int trongLuong, String hangSanXuat) {
        this.id = id;
        this.name = name;
        this.gia = gia;
        this.img = img;
        this.tinhTrang = tinhTrang;
        this.soLuong = soLuong;
        this.trongLuong = trongLuong;
        this.hangSanXuat = hangSanXuat;
    }

    public CuaHang() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTrongLuong() {
        return trongLuong;
    }

    public void setTrongLuong(int trongLuong) {
        this.trongLuong = trongLuong;
    }

    public String getHangSanXuat() {
        return hangSanXuat;
    }

    public void setHangSanXuat(String hangSanXuat) {
        this.hangSanXuat = hangSanXuat;
    }
}
