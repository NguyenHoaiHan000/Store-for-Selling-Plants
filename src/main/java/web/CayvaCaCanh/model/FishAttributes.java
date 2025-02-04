package web.CayvaCaCanh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "THUOC_TINH_CA")
public class FishAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MASANPHAM")
    private Product product;

    @Column(name = "LOAICA", length = 100)
    private String species;  // Loài cá

    @Column(name = "KICHTHUOC", length = 50)
    private String size;  // Kích thước

    @Column(name = "TUOITHO", length = 50)
    private String lifespan;  // Tuổi thọ

    @Column(name = "DOKHO")
    private int hardness;  // Độ khó

    @Column(name = "LOAITHUCAN", length = 100)
    private String foodType;  // Loại thức ăn

    // Getters và setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLifespan() {
        return lifespan;
    }

    public void setLifespan(String lifespan) {
        this.lifespan = lifespan;
    }

    public int getHardness() {
        return hardness;
    }

    public void setHardness(int hardness) {
        this.hardness = hardness;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}