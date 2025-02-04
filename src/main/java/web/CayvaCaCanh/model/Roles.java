package web.CayvaCaCanh.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "Role")
public class Roles {

    @Id
    @Column(name = "MACHUCVU",length = 10)
    private String id;

    @Column(name="TENCHUCVU",length= 50)
    private String name;


    @OneToMany (mappedBy = "role")
    private List<Account> listAccount;
    // Constructors, Getters, and Setters (Omitted for brevity)

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

    public List<Account> getListAccount() {
        return listAccount;
    }

    public void setListAccount(List<Account> listAccount) {
        this.listAccount = listAccount;
    }
}
