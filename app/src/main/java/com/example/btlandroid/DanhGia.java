package com.example.btlandroid;

public class DanhGia {
    private int id;
    private String danhGia;
    private int sachId;
    private int userId;
    private int rate;

    public DanhGia() {}

    public DanhGia(int id, String danhGia, int sachId, int userId, int rate) {
        this.id = id;
        this.danhGia = danhGia;
        this.sachId = sachId;
        this.userId = userId;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(String danhGia) {
        this.danhGia = danhGia;
    }

    public int getSachId() {
        return sachId;
    }

    public void setSachId(int sachId) {
        this.sachId = sachId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
