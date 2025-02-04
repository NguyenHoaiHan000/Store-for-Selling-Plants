package web.CayvaCaCanh.model;

public class ProductStatistic {

    private String tenSanPham;
    private double giaNhap;
    private double giaBan;
    private int soLuongNhap;
    private int soLuongBan;
    private int soLuongTon; // Thêm thuộc tính này
    private double thanhTienNhap;
    private double thanhTienBan;
    private double doanhThu;

    // Constructor, Getters, Setters, toString
    public ProductStatistic(String tenSanPham, double giaNhap, double giaBan, int soLuongNhap,
                            int soLuongBan, int soLuongTon, double thanhTienNhap, double thanhTienBan, double doanhThu) {
        this.tenSanPham = tenSanPham;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuongNhap = soLuongNhap;
        this.soLuongBan = soLuongBan;
        this.soLuongTon = soLuongTon; // Khởi tạo giá trị
        this.thanhTienNhap = thanhTienNhap;
        this.thanhTienBan = thanhTienBan;
        this.doanhThu = doanhThu;
    }

    public ProductStatistic() {

    }

    // Getters and Setters
    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    public int getSoLuongTon() { // Thêm getter
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) { // Thêm setter
        this.soLuongTon = soLuongTon;
    }

    public double getThanhTienNhap() {
        return thanhTienNhap;
    }

    public void setThanhTienNhap(double thanhTienNhap) {
        this.thanhTienNhap = thanhTienNhap;
    }

    public double getThanhTienBan() {
        return thanhTienBan;
    }

    public void setThanhTienBan(double thanhTienBan) {
        this.thanhTienBan = thanhTienBan;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }

    @Override
    public String toString() {
        return "ProductStatistic [tenSanPham=" + tenSanPham + ", giaNhap=" + giaNhap + ", giaBan=" + giaBan
                + ", soLuongNhap=" + soLuongNhap + ", soLuongBan=" + soLuongBan + ", soLuongTon=" + soLuongTon
                + ", thanhTienNhap=" + thanhTienNhap + ", thanhTienBan=" + thanhTienBan + ", doanhThu=" + doanhThu + "]";
    }
}


