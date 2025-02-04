package web.CayvaCaCanh.model;

import jakarta.persistence.*;

@Entity
@Table(name ="DANHMUC")
public class Category {
    @Id
    @Column(name = "MADANHMUC",length = 50)
    private String id;

    @Column(name ="TENDANHMUC", length = 50)
    private String name;

    @Column(name = "TRANGTHAI")
    private boolean status;

    private long productCount;

    public Category() {
        // Không có tham số
    }
    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }
// DÙNG ĐỂ XEM SỐ LƯỢNG CÓ TRONG DANH MỤC
    public Category(String id, String name, long productCount) {
        this.id = id;
        this.name = name;
        this.productCount = productCount;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }
}
