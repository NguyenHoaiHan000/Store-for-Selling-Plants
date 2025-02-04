package web.CayvaCaCanh.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReviewId implements Serializable {
    @Column(name = "MASANPHAM")
    private String productId;

    @Column(name = "MAKHACHHANG")
    private String userId;

    public ReviewId() {}

    public ReviewId(String productId, String userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId that = (ReviewId) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, userId);
    }
}
