package web.CayvaCaCanh.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="HINHTHUCTHANHTOAN")
public class Pay {
    @Id
    @Column(name ="MATHANHTOAN")
    private String id;

    @Column(name = "KIEUTHANHTOAN", length = 100)
    private String typePay;

    public Pay() {
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypePay() {
        return typePay;
    }

    public void setTypePay(String typePay) {
        this.typePay = typePay;
    }




}
