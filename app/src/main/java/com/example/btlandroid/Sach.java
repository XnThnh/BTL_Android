public class Sach {
    private int id;
    private String tenSach;
    private String tacGia;
    private int soTrang;
    private String urlAnh;
    private String moTa;
    private LoaiSach loaiSach;

    public Sach() {
    }

    public Sach(String tenSach, String tacGia, int soTrang, String moTa, String urlAnh, LoaiSach loaiSach) {
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.soTrang = soTrang;
        this.moTa = moTa;
        this.urlAnh = urlAnh;
        this.loaiSach = loaiSach;
    }

    public Sach(int id, String tenSach, String tacGia, int soTrang, String urlAnh, String moTa, LoaiSach loaiSach) {
        this.id = id;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.soTrang = soTrang;
        this.urlAnh = urlAnh;
        this.moTa = moTa;
        this.loaiSach = loaiSach;
    }

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

    public String getUrlAnh() {
        return urlAnh;
    }

    public void setUrlAnh(String urlAnh) {
        this.urlAnh = urlAnh;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getLoaiSachId()
    {
        if(loaiSachId == 1) return "Văn học";
        else if(loaiSachId == 2) return "Cổ tích";
        else if(loaiSachId == 3) return "Văn học thiếu nhi";
        else return "Văn học hiện thực";
    }

    public void setLoaiSachId(int loaiSachId) {
        this.loaiSachId = loaiSachId;
    }
}