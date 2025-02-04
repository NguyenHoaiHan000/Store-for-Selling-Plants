package web.CayvaCaCanh.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "NHANVIEN")
public class Staffs {
    @Id
    @Column(name = "MANHANVIEN", length = 10)
    private String id;

    @Column(name = "HO", length = 30)
    private String Ho;

    @Column(name = "TEN", length = 30)
    private String name;

    @Column(name = "CMND", length = 12)
    private String cmnd;

    @Column(name = "DIACHI", length = 30)
    private String address;

    @Column(name = "GIOITINH", length = 10)
    private String gender;

    @Column(name = "SDT", length = 11)
    private String sdt;

    @Column(name = "ANH", length = 200)
    private String image;

    @Column(name = "NGAYSINH", length = 30)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TENTAIKHOAN")
    private Account account;

    public String getHo() {
        return Ho;
    }

    public void setHo(String ho) {
        Ho = ho;
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

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    //TÌM TÊN TÀI KHOẢN
    public String getAccountTenTaiKhoan() {
        return account != null ? account.getUsername() : null;
    }


}
