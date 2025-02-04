package web.CayvaCaCanh.model;


import jakarta.persistence.*;


@Entity
@Table(name = "CHITIETPHIEUNHAP")
public class ReceiptDetail {
    @EmbeddedId
    private ReceiptDetailPK id;

    @ManyToOne
    @MapsId("receipt")
    @JoinColumn(name  = "MAPHIEUNHAP")
    private Receipt receipt;

    @ManyToOne
    @MapsId("product")
    @JoinColumn(name = "MASANPHAM")
    private Product product;

    @Column(name = "SOLUONG")
    private int quantity;

    @Column(name = "GIANHAP")
    private double price;

    @Transient
    private double totalCost;

    public ReceiptDetail() {
        // TODO Auto-generated constructor stub
    }

    public ReceiptDetailPK getId() {
        return id;
    }

    public void setId(ReceiptDetailPK id) {
        this.id = id;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Transient
    public double getTotalCost() {
        return this.quantity * this.price;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    private void calculateTotalCost() {
        this.totalCost = this.quantity * this.price;
    }
//    @Override
//    public String toString() {
//        return "ReceiptDetail [id=" + id + ", receipt=" + receipt + ", product=" + product + ", quantity=" + quantity
//                + ", price=" + price + "]";
//    }

    @Override
    public String toString() {
        return "ReceiptDetail [id=" + id + ", receipt=" + receipt + ", product=" + product + ", quantity=" + quantity
                + ", price=" + price + ", totalCost=" + totalCost + "]";
    }

}
