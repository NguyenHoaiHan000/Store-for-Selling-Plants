package web.CayvaCaCanh.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartDetailPk implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "MASANPHAM",length = 10)
    private String product;
    @Column(name ="MAGIOHANG",length = 10)
    private String cart;

    public CartDetailPk() {
        // TODO Auto-generated constructor stub
    }


    public CartDetailPk(String cart, String product) {
        super();
        this.cart = cart;
        this.product = product;
    }



    public String getProduct() {
        return product;
    }


    public void setProduct(String product) {
        this.product = product;
    }

//
//    public String getCard() {
//        return cart;
//    }


//    public void setCard(String cart) {
//        this.cart = cart;
//    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cart, product);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartDetailPk other = (CartDetailPk) obj;
        return Objects.equals(cart, other.cart) && Objects.equals(product, other.product);
    }

    @Override
    public String toString() {
        return "CartDetailPk{" +
                "cart=" + cart +
                ", product='" + product + '\'' +
                '}';
    }
}