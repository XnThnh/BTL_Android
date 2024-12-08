package com.example.btlandroid;

public class TrichDan {
    private int id;
    private String cauNoi;
    private Sach sach;

    public TrichDan() {
    }

    public TrichDan(int id, String cauNoi, Sach sach) {
        this.id = id;
        this.cauNoi = cauNoi;
        this.sach = sach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCauNoi() {
        return cauNoi;
    }

    public void setCauNoi(String cauNoi) {
        this.cauNoi = cauNoi;
    }

    public Sach getSach() {
        return sach;
    }

    public void setSach(Sach sach) {
        this.sach = sach;
    }
}
