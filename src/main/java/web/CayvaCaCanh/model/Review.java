package web.CayvaCaCanh.model;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "DANHGIA")
public class Review {

    @EmbeddedId
    private ReviewId reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASANPHAM", referencedColumnName = "MASANPHAM", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAKHACHHANG", referencedColumnName = "MAKHACHHANG", insertable = false, updatable = false)
    private User user;

    @Column(name = "RATING", nullable = false)
    private int rating;


    public Review() {}

    public Review(Product product, User user, int rating) {
        this.reviewId = new ReviewId(product.getId(), user.getId());
        this.product = product;
        this.user = user;
        this.rating = rating;

    }

    // Getters and setters

    public ReviewId getReviewId() {
        return reviewId;
    }

    public void setReviewId(ReviewId reviewId) {
        this.reviewId = reviewId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return rating == review.rating &&

                Objects.equals(reviewId, review.reviewId) &&
                Objects.equals(product, review.product) &&
                Objects.equals(user, review.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, product, user, rating);
    }
}
