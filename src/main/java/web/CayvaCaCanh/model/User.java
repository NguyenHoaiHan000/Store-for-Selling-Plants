package web.CayvaCaCanh.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "KHACHHANG")
public class User {
    @Id
    @Column(name = "MAKHACHHANG", length = 10)
    private String id;
    @Column(name = "HO", length = 20)
    private String surname;
    @Column(name = "TEN", length = 20)
    private String name;
    @Column(name = "NGAYSINH", length = 20)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dayOfBirth;
    @Column(name = "DIACHI", length = 255)
    private String address;
    @Column(name = "SDT", length = 20)
    private String sdt;
    @Column(name = "ANHDAIDIEN", length = 20)
    private String image;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TENTAIKHOAN")
    private Account account;

    @OneToMany(mappedBy = "user")
    private List<Cart> listCart;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    // Danh sách đánh giá của khách hàng
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Cart> getListCart() {
        return listCart;
    }

    public void setListCart(List<Cart> listCart) {
        this.listCart = listCart;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
