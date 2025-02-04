package web.CayvaCaCanh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "NHACUNGCAP")
public class Supplier {
    @Id
    @Column(name ="MANHACUNGCAP",length = 10)
    private String id;
    @Column(name = "TENNHACUNGCAP" ,length = 50)
    private String name;

    @Column(name = "SDT" ,length = 11)
    private String sdt;

    @Column(name = "DIACHI" ,length = 100)
    private String address;

    @Column(name = "TRANGTHAI" ,length = 50)
    private boolean status;

    public Supplier() {
        // Không có tham số
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
