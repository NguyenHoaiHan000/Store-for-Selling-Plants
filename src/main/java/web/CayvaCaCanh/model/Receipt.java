package web.CayvaCaCanh.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PHIEUNHAP")
public class Receipt {
    @Id
    @Column(name ="MAPHIEUNHAP",length = 10)
    private String id;

    @Column(name ="NGAYLAP")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "MANV")
    private Staffs staff;


    @ManyToOne
    @JoinColumn(name="MANHACUNGCAP")
    private Supplier supplier;


    @OneToMany(mappedBy = "receipt",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ReceiptDetail> listReceiptDetail;

    public List<ReceiptDetail> getListReceiptDetail() {
        return listReceiptDetail;
    }

    public void setListReceiptDetail(List<ReceiptDetail> listReceiptDetail) {
        this.listReceiptDetail = listReceiptDetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }


    public Staffs getStaff() {
        return staff;
    }

    public void setStaff(Staffs staff) {
        this.staff = staff;
    }


    public Receipt() {

        this.listReceiptDetail = new ArrayList<>();
    }
}
