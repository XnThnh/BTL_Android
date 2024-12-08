package com.example.btlandroid;

public class Sach {
    private int id;
    private String tenSach;
    private String tacGia;
    private int soTrang;
    private String anh;
    private String mota;
    private LoaiSach loaiSach;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public LoaiSach getLoaiSach() {
        return loaiSach;
    }

    public void setLoaiSach(LoaiSach loaiSach) {
        this.loaiSach = loaiSach;
    }

    public Sach() {
    }

    public Sach(int id, String tenSach, String tacGia, int soTrang, String anh, String mota, LoaiSach loaiSach) {
        this.id = id;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.soTrang = soTrang;
        this.anh = anh;
        this.mota = mota;
        this.loaiSach = loaiSach;
    }
    @Override
    public String toString() {
        return tenSach;
    }
}
