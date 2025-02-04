package web.CayvaCaCanh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "THUOC_TINH_CAY")
public class PlantAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MASANPHAM")
    private Product product;

    @Column(name = "CHIEUCAO", length = 50)
    private String height;  // Chiều cao

    @Column(name = "DOKHO", length = 50)
    private String difficulty;  // Độ khó

    @Column(name = "YEUCAUANHSANG", length = 100)
    private String lightRequirement;  // Yêu cầu ánh sáng

    @Column(name = "NHUCAUNUOC", length = 100)
    private String waterRequirement;  // Nhu cầu nước

    // Getters và setters...
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getLightRequirement() {
        return lightRequirement;
    }

    public void setLightRequirement(String lightRequirement) {
        this.lightRequirement = lightRequirement;
    }

    public String getWaterRequirement() {
        return waterRequirement;
    }

    public void setWaterRequirement(String waterRequirement) {
        this.waterRequirement = waterRequirement;
    }
}