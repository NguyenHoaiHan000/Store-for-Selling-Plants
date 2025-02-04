package web.CayvaCaCanh.model;

import jakarta.persistence.*;


@Entity
@Table(name = "CHITIETPHIEUDAT")
public class OrderDetail {

    @EmbeddedId
    private OrderDetailPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "MAPHIEUDAT")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "MASANPHAM")
    private Product product;

    @Column(name = "SOLUONG")
    private int quantity;

    @Column(name = "GIA")
    private double price;


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public OrderDetailPK getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(OrderDetailPK id) {
        this.id = id;
    }
    @Transient
    private double productTotal;

    // Getter và Setter cho productTotal
    public double getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(double productTotal) {
        this.productTotal = productTotal;
    }


    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", order=" + (order != null ? order.getId() : "N/A") +
                ", product=" + (product != null ? product.getName() : "N/A") +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

}
