package web.CayvaCaCanh.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SANPHAM")
public class Product {
    @Id
    @Column(name ="MASANPHAM", length=10)
    private String id;
    @Column(name = "TENSANPHAM",length = 200)
    private String name;
    @Column(name = "MOTA",length = 5000)
    private String description;
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", image="
                + image + ", status=" + status + ", number=" + number + ", category=" + category + "]";
    }

    @Column(name = "GIA")
    private double price;
    @Column(name = "HINHANH",length = 200)
    private String image;
    @Column(name = "TRANGTHAI")
    private boolean status;
    @Column(name = "SOLUONGTON")
    private int number;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "MADANHMUC")
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ReceiptDetail> listReceiptDetail;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CartDetail> listCartDetail;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Review> listReview;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlantAttributes> plantAttributes = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FishAttributes> fishAttributes = new ArrayList<>();

    public List<PlantAttributes> getPlantAttributes() {
        return plantAttributes;
    }

    public void setPlantAttributes(List<PlantAttributes> plantAttributes) {
        this.plantAttributes = plantAttributes;
    }

    public List<FishAttributes> getFishAttributes() {
        return fishAttributes;
    }

    public void setFishAttributes(List<FishAttributes> fishAttributes) {
        this.fishAttributes = fishAttributes;
    }

    public List<Review> getListReview() {
        return listReview;
    }

    public void setListReview(List<Review> listReview) {
        this.listReview = listReview;
    }

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CartDetail> getListCartDetail() {
        return listCartDetail;
    }


    public void setListCardDetail(List<CartDetail> listCartDetail) {
        this.listCartDetail = listCartDetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getReviewCount() {
        if (listReview != null) {
            return listReview.size();
        }
        return 0;
    }

    // Tính toán số sao trung bình
    public double getAverageRating() {
        if (listReview != null && !listReview.isEmpty()) {
            double sum = 0;
            for (Review review : listReview) {
                sum += review.getRating(); // giả sử thuộc tính 'rating' trong Review là kiểu double hoặc int
            }
            return sum / listReview.size();
        }
        return 0.0; // trả về 0 nếu không có đánh giá nào
    }

}
