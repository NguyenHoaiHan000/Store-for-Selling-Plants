package web.CayvaCaCanh.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import web.CayvaCaCanh.model.Account;
import web.CayvaCaCanh.model.Staffs;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private Account user;
    private Staffs staff;
    private Collection<GrantedAuthority> authorities;

    public CustomUserDetails(Account user, Staffs staff, Collection<GrantedAuthority> authorities) {
        this.user = user;
        this.staff = staff;
        this.authorities = authorities;
    }

    // Trong CustomUserDetails, thêm phương thức này để cập nhật thông tin nhân viên
    public void updateStaffDetails(Staffs updatedStaff) {
        this.staff = updatedStaff;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // You can implement logic for account expiration here
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // You can implement logic for account locking here
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // You can implement logic for credential expiration here
    }

    @Override
    public boolean isEnabled() {
        return true; // You can implement logic for enabling/disabling accounts here
    }

    public Staffs getStaff() {
        return staff;
    }

    public void setStaff(Staffs staff) {
        this.staff = staff;
    }
}
