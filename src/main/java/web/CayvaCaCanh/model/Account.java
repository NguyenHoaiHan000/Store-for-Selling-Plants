package web.CayvaCaCanh.model;

import jakarta.persistence.*;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Set;


@Entity
@Table(name = "TAIKHOAN")
public class Account {
    @Id
    @Column(name = "TENTAIKHOAN",length = 50)
    private String username;

    @Column(name ="MATKHAU", length = 2000)
    private String password;

    @Column(name = "TRANGTHAI")
    private boolean status;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @ManyToOne
    @JoinColumn(name = "MACHUCVU")
    private Roles role;

    @OneToOne(mappedBy = "account",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Staffs staff;



    // Constructors, Getters, and Setters (Omitted for brevity)


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Staffs getStaff() {
        return staff;
    }

    public void setStaff(Staffs staff) {
        this.staff = staff;
    }
}
