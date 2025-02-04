package web.CayvaCaCanh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CHITIETGIOHANG")
public class CartDetail {

    @EmbeddedId
    private CartDetailPk id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("product")
    @JoinColumn(name ="MASANPHAM")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cart")
    @JoinColumn(name = "MAGIOHANG")
    private Cart cart;

    @Column(name = "SOLUONG")
    private int quantity;

    public CartDetail() {
        super();
    }

    public CartDetail(CartDetailPk id, int quantity, Product product, Cart cart) {
        super();
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.cart = cart;
    }

    public CartDetail( Cart cart,Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }


    public CartDetail(CartDetailPk id, Product product, Cart cart, int quantity) {
        super();
        this.id = id;
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
    }


    public CartDetailPk getId() {
        return id;
    }

    public void setId(CartDetailPk id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartDetail{" +
                "cartId=" + cart.getId() +
                ", productId=" + product.getId() +
                ", quantity=" + quantity +
                // Thêm các thuộc tính khác mà bạn muốn in ra
                '}';
    }

}
